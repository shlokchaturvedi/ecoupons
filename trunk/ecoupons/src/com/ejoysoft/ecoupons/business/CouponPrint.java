package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class CouponPrint
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_coupon_print";
	
	String strTableName2 = "t_bz_member";

	private String strId;
	private String strMemberCardNo;
	private String strCouponId;
	private String strTerminalId;
	private String dtPrintTime;
	private String strCouponCode;
	private String intState;// 录入状态，商家一旦录入后就为1，初始为0
	private String strCreator;
	private String dtCreateTime;

	/**
	 * 增加优惠券打印记录记录
	 */
	public boolean add()
	{
		//strCreator = globa.userSession.getStrId();
		String strId = UID.getID();
		Member obj = new Member(globa);
		Member obj1 = obj.show(" where strcardno='"+strMemberCardNo+"'");
		float memberbalance = obj1.getFlaBalance();
		Coupon coupon = new Coupon(globa);
		Coupon objCoupon = coupon.show(" where strid="+strCouponId);
		float couponprice = objCoupon.getFlaPrice();
		float balance = memberbalance - couponprice;
		String sql = "insert into " + strTableName + " (strId,strMemberCardNo,strCouponId,strTerminalId,dtPrintTime,strCouponCode,intState"
				+ ",strCreator,dtCreateTime,strterminalids) " + "values (?,?,?,?,?,?,?,?,?,?) ";
		try
		{	
			db.getConnection().setAutoCommit(false);//禁止自动提交事务
			db.executeUpdate("update "+strTableName2+" set flabalance="+balance+"  where strcardno='"+strMemberCardNo+"'");
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strMemberCardNo);
			db.setString(3, strCouponId);
			db.setString(4, strTerminalId);
			db.setString(5, com.ejoysoft.common.Format.getDateTime());
			db.setString(6, strCouponCode);
			db.setInt(7, 0);
			db.setString(8, strCreator);
			db.setString(9, com.ejoysoft.common.Format.getDateTime());
			db.setString(10, objCoupon.getStrTerminalIds());
			if (db.executeUpdate() > 0)
			{
				coupon.updateIntPrint(strId);
	            db.getConnection().commit(); //统一提交
				db.setAutoCommit(true);
				Globa.logger0("增加优惠券打印记录记录 ", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			db.rollback();
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * 删除优惠券信息
	 */
	public boolean delete(String where)
	{
		try
		{
			String sql = "DELETE FROM " + strTableName + "  ".concat(where);
			db.executeUpdate(sql);
			Globa.logger0("删除优惠券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}
	/**
	 * 对日期字符串做处理
	 */
	public String  returnStrDt(String Dt)
	{
		return Dt.replace("-", "").replace(" ", "").replace(":", "").trim().substring(0, 14);
	}

	/**
	 * 判断有价券是否有效
	 * 
	 * @param where
	 * @return
	 */
	public boolean isEffective(String dtPrintTime, String strCouponCode)
	{
		if (getCount(" where strcouponcode='"+strCouponCode+"'")==0)
		{
			return false;
		}
		String strSql="select intState,dtPrintTime from "+strTableName +" where strcouponcode='"+strCouponCode+"'";
		ResultSet resultSet=db.executeQuery(strSql);
		try
		{
			while(resultSet.next()){
				System.out.println(returnStrDt(resultSet.getString("dtPrintTime")));
				System.out.println(returnStrDt(dtPrintTime));
				if (resultSet.getInt("intState")==0&&returnStrDt(resultSet.getString("dtPrintTime")).equals(returnStrDt(dtPrintTime)))
				{
					return true;
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;

	}

	/*
	 * 详细显示单条记录
	 */
	public CouponPrint show(String where)
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
	 * 根据条件返回积分的集合
	 */

	public Vector<CouponPrint> list(String where, int startRow, int rowCount)
	{
		Vector<CouponPrint> beans = new Vector<CouponPrint>();
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
					CouponPrint theBean = new CouponPrint();
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

	public CouponPrint load(ResultSet rs, boolean isView)
	{
		CouponPrint theBean = new CouponPrint();
		try
		{
			theBean.setDtPrintTime(rs.getString("dtPrintTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setIntState(rs.getString("intState"));
			theBean.setStrCouponId(rs.getString("strCouponId"));
			theBean.setStrCouponCode(rs.getString("strCouponCode"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
			theBean.setStrTerminalId(rs.getString("strTerminalId"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public CouponPrint()
	{

	}

	public CouponPrint(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public CouponPrint(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrMemberCardNo()
	{
		return strMemberCardNo;
	}

	public void setStrMemberCardNo(String strMemberCardNo)
	{
		this.strMemberCardNo = strMemberCardNo;
	}

	public String getStrCouponId()
	{
		return strCouponId;
	}

	public void setStrCouponId(String strCouponId)
	{
		this.strCouponId = strCouponId;
	}

	public String getStrTerminalId()
	{
		return strTerminalId;
	}

	public void setStrTerminalId(String strTerminalId)
	{
		this.strTerminalId = strTerminalId;
	}

	public String getDtPrintTime()
	{
		if(dtPrintTime!=null&&dtPrintTime!=""){
			return dtPrintTime.substring(0, dtPrintTime.length()-2);
		}
		return dtPrintTime;
	}

	public void setDtPrintTime(String dtPrintTime)
	{
		this.dtPrintTime = dtPrintTime;
	}

	public String getStrCouponCode()
	{
		return strCouponCode;
	}

	public void setStrCouponCode(String strCouponCode)
	{
		this.strCouponCode = strCouponCode;
	}

	public String getIntState()
	{
		return intState;
	}

	public void setIntState(String intState)
	{
		this.intState = intState;
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
		if (dtCreateTime != null&&dtCreateTime.length()>3)
		{
			return dtCreateTime.substring(0, dtCreateTime.length()-2);

		} else
		{
			return null;
		}
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

}
