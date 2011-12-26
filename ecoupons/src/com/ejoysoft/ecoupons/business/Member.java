package com.ejoysoft.ecoupons.business;

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

public class Member
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_member";
	String strRechargeTableName = "t_bz_member_recharge";

	/*
	 * 修改会员信息
	 */
	public boolean update(String tStrId)
	{
		System.out.println(strCardNo + strName);
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET strCardNo = ?, strSalesman = ?, strName = ?, intType = ?,  "
					+ "dtExpireTime = ?, strSalesman = ?  WHERE strId=? ";
			db.prepareStatement(strSql);
			db.setString(1, strCardNo);
			db.setString(2, strSalesman);
			db.setString(3, strName);
			db.setInt(4, intType);
			// db.setString(6, dtActiveTime);
			// db.setFloat(7, flaBalance);
			// db.setInt(8, intPoint);
			db.setString(5, dtExpireTime);
			db.setString(6, strSalesman);
			// db.setString(11, strPwd);
			// db.setInt(12, intAudit);
			// db.setString(13,strCreator);
			// db.setString(14, dtCreateTime);
			db.setString(7, tStrId);
			db.executeUpdate();
			Globa.logger0("修改会员信息", globa.loginName, globa.loginIp, strSql, "会员管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("修改会员信息时出错：" + e);
			return false;
		}
	}

	/*
	 * 删除会员信息
	 */
	public boolean delete(String where)
	{
		PreparedStatement pStatement = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try
		{
			String sql = "DELETE FROM " + strTableName + "  ".concat(where);
			String sql2 = "DELETE FROM " + strRechargeTableName + " where strMemberCardNo=" + show(where).getStrCardNo();
			System.out.println(sql2);
			connection = db.getConnection();
			connection.setAutoCommit(false);
			connection.setSavepoint();
			pStatement = connection.prepareStatement(sql);
			preparedStatement = connection.prepareStatement(sql2);
			if (pStatement.executeUpdate() > 0 && preparedStatement.executeUpdate() > 0)
			{
				connection.commit();
				connection.setAutoCommit(true);
				Globa.logger0("删除会员信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
				return true;
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
			try
			{
				connection.rollback();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} finally
		{
			try
			{
				pStatement.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/*
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

	/*
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

	/*
	 * 返回yyyy-mm-dd日期
	 */
	public String returnDate(String str)
	{
		if (str != null)
		{

			return str.substring(0, 10);
		} else
		{
			return str;
		}
	}

	/*
	 * 增加会员信息
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String sql = "insert into " + strTableName + " (strId,strCardNo,strMobileNo,strName,intType" + ",strSalesman,strCreator,dtCreateTime) "
				+ "values (?,?,?,?,?,?,?,?) ";
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
			db.setString(8, com.ejoysoft.common.Format.getDate());
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
	
	
	

	public List<Member> returnNewMembers( String startId, String endId)
	{
		Vector<Member> members=new Vector<Member>();
		
		members=list("ORDER BY strCardNo", 0, 0);
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

	/*
	 * 根据卡号返回余额
	 */
	public float getFlaBalance(String strMemberCardNo)
	{
		float a = 0f;
		String sql = "select flabalance from " + strTableName + " where strCardNo='" + strMemberCardNo + "' ";
		ResultSet rs = db.executeQuery(sql);
		System.out.println("56565656565656565656");
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

	/*
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

	/*
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
		return dtActiveTime;
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
		return dtExpireTime;
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

}
