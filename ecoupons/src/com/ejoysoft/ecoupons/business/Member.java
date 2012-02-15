package com.ejoysoft.ecoupons.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ejoysoft.auth.MD5;
import com.ejoysoft.common.Constants;
import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.ejoysoft.util.ParamUtil;

public class Member
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_member";
	String strRechargeTableName = "t_bz_member_recharge";
	String strCouponPrintTableName = "t_bz_coupon_Print";
	String strCouponInputTableName = "t_bz_coupon_input";
	String strGiftExchangeTableName = "t_bz_gift_exchange";
	String strCouponCommentTableName = "t_bz_coupon_comment";
	String strCouponFavouriteTableName = "t_bz_coupon_favourite";
	private String strStartId;
	private String strEndId;

	/**
	 * 导出手机列表
	 */
	public boolean returnMobilNos(Vector<Member> vecMembers)
	{
		strStartId = ParamUtil.getString(globa.request, "strStartId", "");
		strEndId = ParamUtil.getString(globa.request, "strEndId", "");
		String strFilePath = globa.application.getRealPath("") + "/member/expertTel/" + strStartId + "_" + strEndId + "_"
				+ com.ejoysoft.common.Format.getDateTime().replace(" ", "").replace("-", "").replace(":", "") + ".xls";
		File file = new File(strFilePath);
		PrintStream printStream = null;
		try
		{
			printStream = new PrintStream(new FileOutputStream(file));
			printStream.print("<table><tr><td>手机</td><td>姓名</td><td>卡号</td></tr>");
			if (vecMembers.size() != 0)
			{
				for (int i = 0; i < vecMembers.size(); i++)
				{
					printStream.print("<tr><td>" + vecMembers.get(i).getStrMobileNo() + "</td><td>" + vecMembers.get(i).getStrName() + "</td><td>"
							+ vecMembers.get(i).getStrCardNo() + "</td>");

				}
			}
			printStream.println("</table>");
			printStream.flush();
			printStream.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(strFilePath);
		return true;
	}

	/**
	 * 修改会员信息
	 */
	public boolean update(String tStrId)
	{
		System.out.println(strCardNo + strName);
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET strCardNo = ?, strName = ?, intType = ?,  "
					+ "dtExpireTime = ?, strSalesman = ?  WHERE strId=? ";
			db.prepareStatement(strSql);
			db.setString(1, strCardNo);
//			db.setString(2, strSalesman);
			db.setString(2, strName);
			db.setInt(3, intType);
			db.setString(4, dtExpireTime);
			db.setString(5, strSalesman);
			db.setString(6, tStrId);
			db.executeUpdate();
			Globa.logger0("修改会员信息", globa.loginName, globa.loginIp, strSql, "会员管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("修改会员信息时出错：" + e);
			return false;
		}
	}

	/**
	 * 删除会员信息
	 */
	public boolean delete(String where)
	{
		try
		{
			String strSql = "DELETE FROM " + strTableName + "  ".concat(where);
			String strCard=show(where).getStrCardNo();
			String strSql2 = "DELETE FROM " + strRechargeTableName + " where strMemberCardNo='" + strCard+"'";
			String strSql3 = "DELETE FROM " + strCouponPrintTableName + " where strMemberCardNo='" + strCard+"'";
			String strSql4 = "DELETE FROM " + strCouponInputTableName + " where strMemberCardNo='" + strCard+"'";
			String strSql5 = "DELETE FROM " + strGiftExchangeTableName + " where strMemberCardNo='" + strCard+"'";
			String strSql6 = "DELETE FROM " + strCouponCommentTableName + " where strMemberCardNo='" + strCard+"'";
			String strSql7 = "DELETE FROM " + strCouponFavouriteTableName + " where strMemberCardNo='" + strCard+"'";
			db.setAutoCommit(false);
			if (show(where).getStrCardNo() == null)
			{
				db.executeUpdate(strSql);
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("删除会员信息", globa.loginName, globa.loginIp, strSql, "会员管理", globa.unitCode);
				return true;

			} else
			{
				db.executeUpdate(strSql);
				db.executeUpdate(strSql2);
				db.executeUpdate(strSql3);
				db.executeUpdate(strSql4);
				db.executeUpdate(strSql5);
				db.executeUpdate(strSql6);
				db.executeUpdate(strSql7);
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("删除会员信息", globa.loginName, globa.loginIp, strSql, "会员管理", globa.unitCode);
				Globa.logger0("删除会员消费记录，在删除会员时候。", globa.loginName, globa.loginIp, strSql2, "会员管理", globa.unitCode);
				Globa.logger0("删除优惠券打印记录，在删除会员时候。", globa.loginName, globa.loginIp, strSql3, "会员管理", globa.unitCode);
				Globa.logger0("删除优惠券录入记录，在删除会员的时候", globa.loginName, globa.loginIp, strSql4, "会员管理", globa.unitCode);
				Globa.logger0("删除礼品兑换记录，在删除会员的时候", globa.loginName, globa.loginIp, strSql5, "会员管理", globa.unitCode);
				Globa.logger0("删除和会员卡号相关的优惠券评价记录", globa.loginName, globa.loginIp, strSql6, "会员管理", globa.unitCode);
				Globa.logger0("删除和会员卡号相关的优惠券收藏记录", globa.loginName, globa.loginIp, strSql7, "会员管理", globa.unitCode);
				return true;
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
			db.rollback();
			return false;
		}

	}

	/**
	 * 详细显示单条记录
	 */
	public Member show(String where)
	{
		try
		{
			String strSql = "select * from  " + strTableName + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
				return load(rs, true);
			else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	/**
	 * 查询符合条件的记录总数
	 */
	public int getCount(String where)
	{
		int count = 0;
		try
		{
			String sql = "SELECT count(strId) FROM " + strTableName + "  ";
			if (where.length() > 0)
			{
				where = where.toLowerCase();
				if (where.indexOf("order") > 0)
					where = where.substring(0, where.lastIndexOf("order"));
				sql = String.valueOf(sql) + String.valueOf(where);
			}
			ResultSet rs = db.executeQuery(sql);
			if (rs.next())
				count = rs.getInt(1);
			rs.close();
			return count;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return count;
		}
	}

	/**
	 * 增加会员信息
	 * 
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String sql = "insert into " + strTableName + " (strId,strCardNo,strMobileNo,strName,intType"
				+ ",strSalesman,strCreator,dtCreateTime,flaBalance,intPoint) " + "values (?,?,?,?,?,?,?,?,?,?) ";
		try
		{
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strCardNo);
			db.setString(3, strMobileNo);
			db.setString(4, strName);
			db.setInt(5, intType);
			// db.setString(6, dtExpireTime);
			db.setString(6, strSalesman);
			db.setString(7, strUserName);
			db.setString(8, com.ejoysoft.common.Format.getDateTime());
			db.setInt(9, 0);
			db.setInt(10, 0);
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("增加会员信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据卡号起始点导出会员手机号码
	 */

	public List<Member> returnNewMembers(String startId, String endId)
	{
		Vector<Member> members = new Vector<Member>();

		members = list("ORDER BY strCardNo", 0, 0);
		List<Member> newMembers = new ArrayList<Member>();
		int firstIndex = 0;
		int lastIndex = 0;
		for (int i = 0; i < members.size(); i++)
		{
			if (Integer.parseInt(members.get(i).getStrCardNo()) == Integer.parseInt(startId))
			{
				firstIndex = i;
			} else if (Integer.parseInt(members.get(i).getStrCardNo()) == Integer.parseInt(endId))
			{
				lastIndex = i;
			}
		}
		newMembers = members.subList(firstIndex, lastIndex);
		for (int i = 0; i < newMembers.size(); i++)
		{
			System.out.println(newMembers.get(i).getStrCardNo());
		}
		return newMembers;
	}

	/**
	 * 根据条件返回会员的集合
	 */

	public Vector<Member> list(String where, int startRow, int rowCount)
	{
		Vector<Member> beans = new Vector<Member>();
		try
		{
			String sql = "SELECT *  FROM  " + strTableName + " ";
			if (where.length() > 0)
				sql = String.valueOf(sql) + String.valueOf(where);
			Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (startRow != 0 && rowCount != 0)
				s.setMaxRows((startRow + rowCount) - 1);
			ResultSet rs = s.executeQuery(sql);
			if (rs != null && rs.next())
			{
				if (startRow != 0 && rowCount != 0)
					rs.absolute(startRow);
				do
				{
					Member theBean = new Member();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
			rs.close();
			s.close();
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	/**
	 * 根据卡号返回余额
	 */
	public float getFlaBalance(String strMemberCardNo)
	{
		float a = 0f;
		String sql = "select flabalance from " + strTableName + " where strCardNo='" + strMemberCardNo + "' ";
		ResultSet rs = db.executeQuery(sql);
		try
		{
			while (rs.next())
			{
				a = rs.getFloat("flabalance");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0f;
		}
		return a;
	}
	/**
	 * 根据卡号更新余额
	 */
	public int setFlaBalance(String strMemberCardNo,float newbalance)
	{
		int a=0;
		String sql = "update " + strTableName + " set flabalance="+newbalance+" where strCardNo='" + strMemberCardNo + "' ";
	
		try {
			a=db.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return a;
	}
	/**
	 * 根据卡号更新积分
	 */
	public int setIntPoint(String strMemberCardNo,int newbalance)
	{
		int a=0;
		String sql = "update " + strTableName + " set intpoint='"+newbalance+"' where strCardNo='" + strMemberCardNo + "' ";
		try {
			a=db.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return a;
	}

	/**
	 * 激活会员
	 */
	public boolean setAudit(String strId)
	{
		String sql = "UPDATE  " + strTableName + "  SET dtActiveTime=? ,dtExpireTime = ? WHERE strId=? ";
		try
		{
			db.prepareStatement(sql);
			db.setString(1, dtActiveTime);
			db.setString(2, dtExpireTime);
			db.setString(3, strId);
			db.executeUpdate();
			Globa.logger0("激活会员", globa.loginName, globa.loginIp, sql, "会员管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("修改用户信息时出错：" + e);
			return false;
		}
	}

	/**
	 * 返回审核状态
	 * 
	 * @param num
	 * @return
	 */
	public String getAudit(int num)
	{
		String audit = null;
		if (num == 0)
		{
			audit = "未审核";
		} else if (num == 1)
		{
			audit = "已审核";

		} else if (num == -1)
		{
			audit = "审核不通过";
		}
		return audit;
	}

	/**
	 * 返回会员类型
	 */
	public String getType(int num)
	{
		if (num == 1)
		{
			return "VIP会员";
		} else if (num == 0)
		{
			return "普通会员";
		}
		return "出现异常！";
	}

	public Member load(ResultSet rs, boolean isView)
	{
		Member theBean = new Member();
		try
		{
			theBean.setStrId(rs.getString("strId"));
			theBean.setDtActiveTime(rs.getString("dtActiveTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setDtExpireTime(rs.getString("dtExpireTime"));
			theBean.setFlaBalance(rs.getFloat("flaBalance"));
			// theBean.setIntAudit(rs.getInt("intAudit"));
			theBean.setIntPoint(rs.getInt("intPoint"));
			theBean.setIntType(rs.getInt("intType"));
			theBean.setStrCardNo(rs.getString("strCardNo"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrMobileNo(rs.getString("strMobileNo"));
			theBean.setStrName(rs.getString("strName"));
			theBean.setStrPwd(rs.getString("strPwd"));
			theBean.setStrSalesman(rs.getString("strSalesman"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public Member()
	{
		// TODO Auto-generated constructor stub
	}

	public Member(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Member(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}
	/**
	 * 修改会员手机号
	 */
	public boolean updateMobileNo(String strCardNo,String strMobileNo)
	{
		try
		{
			String strSql = "update  " + strTableName + "  set strmobileno = ? WHERE strcardno=? ";
			db.prepareStatement(strSql);
			db.setString(1, strMobileNo);
			db.setString(2, strCardNo);
			db.executeUpdate();
			return true;
		} catch (Exception e)
		{
				return false;
		}
	}

	private String strId;// 自动生成
	private String strCardNo;// 卡号
	private String strMobileNo;// 手机号
	private String strName;
	private int intType;// 会员类型0普通会员1VIP会员
	private String dtActiveTime;// 激活操作的系统日期
	private float flaBalance;
	private int intPoint;
	private String dtExpireTime;
	private String strSalesman;// 销售员姓名
	private String strPwd;
	// private int intAudit;// 审核状态0未审核1审核通过-1审核不通过
	private String strCreator;// insert操作的用户名
	private String dtCreateTime;// insert操作的系统时间

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrCardNo()
	{
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo)
	{
		this.strCardNo = strCardNo;
	}

	public String getStrMobileNo()
	{
		return strMobileNo;
	}

	public void setStrMobileNo(String strMobileNo)
	{
		this.strMobileNo = strMobileNo;
	}

	public String getStrName()
	{
		return strName;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

	public int getIntType()
	{
		return intType;
	}

	public void setIntType(int intType)
	{
		this.intType = intType;
	}

	public String getDtActiveTime()
	{
		if (dtActiveTime != null && dtActiveTime != "")
		{

			return dtActiveTime.substring(0, dtActiveTime.length() - 2);
		} else
		{
			return dtActiveTime;
		}
	}

	public void setDtActiveTime(String dtActiveTime)
	{
		this.dtActiveTime = dtActiveTime;
	}

	public float getFlaBalance()
	{
		return flaBalance;
	}

	public void setFlaBalance(float flaBalance)
	{
		this.flaBalance = flaBalance;
	}

	public int getIntPoint()
	{
		return intPoint;
	}

	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}

	public String getDtExpireTime()
	{
		if (dtExpireTime != null && dtExpireTime != "")
		{

			return dtExpireTime.substring(0, dtExpireTime.length() - 2);
		} else
		{
			return dtExpireTime;
		}
		
	}

	public void setDtExpireTime(String dtExpireTime)
	{
		this.dtExpireTime = dtExpireTime;
	}

	public String getStrSalesman()
	{
		return strSalesman;
	}

	public void setStrSalesman(String strSalesman)
	{
		this.strSalesman = strSalesman;
	}

	public String getStrPwd()
	{
		return strPwd;
	}

	public void setStrPwd(String strPwd)
	{
		this.strPwd = strPwd;
	}

	public String getStrCreator()
	{
		return strCreator;
	}

	public void setStrCreator(String strCreator)
	{
		this.strCreator = strCreator;
	}

	public String getDtCreateTime()
	{
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

	public String getStrStartId()
	{
		return strStartId;
	}

	public void setStrStartId(String strStartId)
	{
		this.strStartId = strStartId;
	}

	public String getStrEndId()
	{
		return strEndId;
	}

	public void setStrEndId(String strEndId)
	{
		this.strEndId = strEndId;
	}

}
