package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class CouponInput
{
	private String strId;
	private String strCouponId;// 优惠券对应优惠券信息表strId
	private String strCouponCode;// 券面代码
	private String strMemberCardNo;// 会员卡号
	private String dtPrintTime;
	private String strShopId;
	private int intState;// 状态0未统计1已统计
	private String strCreator;
	private String strCreateTime;

	/**
	 * 统计
	 */
	public boolean State(String printtime)
	{
		
		String strSql = "update t_bz_coupon_input set intState=1 where dtprinttime like'"+printtime+"%'";
		try
		{
			db.prepareStatement(strSql);
			if (db.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 增加有价券信息
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String strSql = "update t_bz_coupon_print set intState=1 where strCouponCode='" + strCouponCode+"'";
		String sql = "insert into " + strTableName + " (strId,strCouponId,strCouponCode,strMemberCardNo,dtPrintTime,strShopId"
				+ ",strCreator,dtCreateTime,intState) " + "values (?,?,?,?,?,?,?,?,?) ";
		try
		{
			db.setAutoCommit(false);
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strCouponId);
			db.setString(3, strCouponCode);
			db.setString(4, strMemberCardNo);
			db.setString(5, dtPrintTime);
			db.setString(6, strShopId);
			db.setString(7, strUserName);
			db.setString(8, com.ejoysoft.common.Format.getDateTime());
			db.setInt(9, 0);
			if (db.executeUpdate() > 0 && db.executeUpdate(strSql) > 0)
			{
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("增加有价券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
				return true;
			} else
			{
				db.rollback();
				return false;
			}
		} catch (SQLException e)
		{
			db.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 删除有价券信息
	 */
	public boolean delete(String where)
	{
		try
		{
			String sql = "DELETE FROM " + strTableName + "  ".concat(where);
			db.executeUpdate(sql);
			Globa.logger0("删除有价券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改有价券录入信息
	 */

/*	public boolean update(String tStrId)
	{
		CouponInput couponInput = new CouponInput();
		CouponPrint couponPrint = new CouponPrint(globa);
		couponInput = show("where strId=" + tStrId);
		String strSql2 = null;
		String strSql3 = null;
		if (couponInput.getStrCouponCode().equals(strCouponCode)
				&& couponPrint.returnStrDt(couponInput.getDtPrintTime()).equals(couponPrint.returnStrDt(dtPrintTime)))
		{

		} else
		{
			if (!couponPrint.isEffective(dtPrintTime, strCouponCode))
			{
				return false;
			} else
			{
				strSql2 = "update t_bz_coupon_print set intState=1 where strCouponCode='" + strCouponCode+"'";
				strSql3 = "update t_bz_coupon_print set intState=1 where strCouponCode='" + couponInput.getStrCouponCode()+"'";
			}
		}
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET   strCouponCode = ?, strMemberCardNo = ?   WHERE strId=? ";
			db.setAutoCommit(false);
			db.prepareStatement(strSql);
			db.setString(1, strCouponCode);
			db.setString(2, strMemberCardNo);
			db.setString(3, tStrId);
			db.executeUpdate();
			if (strSql2 != null)
			{
				db.executeUpdate(strSql2);
			}
			if (strSql3 != null)
			{
				db.executeUpdate(strSql3);
			}
			db.commit();
			db.setAutoCommit(true);
			Globa.logger0("修改有价券录入信息", globa.loginName, globa.loginIp, strSql, "优惠券管理", globa.userSession.getStrDepart());
			return true;

		} catch (Exception e)
		{
			db.rollback();
			return false;
		}

	}*/
	/**
	 * 修改有价券录入信息
	 */

	public boolean update(String tStrId)
	{
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET   strCouponCode = ?, strMemberCardNo = ?   WHERE strId=? ";
			db.setAutoCommit(false);
			db.prepareStatement(strSql);
			db.setString(1, strCouponCode);
			db.setString(2, strMemberCardNo);
			db.setString(3, tStrId);
			db.executeUpdate();
			db.commit();
			db.setAutoCommit(true);
			Globa.logger0("修改有价券录入信息", globa.loginName, globa.loginIp, strSql, "优惠券管理", globa.userSession.getStrDepart());
			return true;

		} catch (Exception e)
		{
			db.rollback();
			return false;
		}

	}
	/**
	 * 详细显示单条记录
	 */
	public CouponInput show(String where)
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
	 * 根据条件返回不重复的strshopid
	 */
	public Vector<CouponInput> returnDistinctStrShopId() 
	{
		Vector<CouponInput> beans = new Vector<CouponInput>();
		String strSql="SELECT DISTINCT(strshopid) from  "+strTableName+" ";
		ResultSet rSet=null;
		rSet=db.executeQuery(strSql);
		try
		{
			while(rSet.next()){
				CouponInput theBean = new CouponInput();
				theBean.setStrShopId(rSet.getString("strshopid"));
				beans.addElement(theBean);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 根据条件，返回优惠券信息的集合
	 */
	public Vector<CouponInput> list(String where, int startRow, int rowCount)
	{
		Vector<CouponInput> beans = new Vector<CouponInput>();
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
					CouponInput theBean = new CouponInput();
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

	public CouponInput load(ResultSet rs, boolean isView)
	{
		CouponInput theBean = new CouponInput();
		try
		{
			theBean.setDtPrintTime(rs.getString("dtPrintTime"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setIntState(rs.getInt("intState"));
			theBean.setStrCouponCode(rs.getString("strCouponCode"));
			theBean.setStrCouponId(rs.getString("strCouponId"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
			theBean.setStrCreateTime(rs.getString("dtCreateTime"));
			theBean.setStrShopId(rs.getString("strShopId"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public String returnStrState(int num)
	{
		if (num == 0)
		{
			return "未统计";
		} else if (num == 1)
		{
			return "已统计";
		} else
		{
			return "出现错误！";
		}
	}

	private String strTableName = "t_bz_coupon_input";
	private Globa globa;
	private DbConnect db;

	public CouponInput()
	{
		// TODO Auto-generated constructor stub
	}

	public CouponInput(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public CouponInput(Globa globa, boolean b)
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

	public String getStrCouponId()
	{
		return strCouponId;
	}

	public void setStrCouponId(String strCouponId)
	{
		this.strCouponId = strCouponId;
	}

	public String getStrCouponCode()
	{
		return strCouponCode;
	}

	public void setStrCouponCode(String strCouponCode)
	{
		this.strCouponCode = strCouponCode;
	}

	public String getStrMemberCardNo()
	{
		return strMemberCardNo;
	}

	public void setStrMemberCardNo(String strMemberCardNo)
	{
		this.strMemberCardNo = strMemberCardNo;
	}

	public String getDtPrintTime()
	{
		return dtPrintTime;
	}

	public void setDtPrintTime(String dtPrintTime)
	{
		this.dtPrintTime = dtPrintTime;
	}

	public String getStrShopId()
	{
		return strShopId;
	}

	public void setStrShopId(String strShopId)
	{
		this.strShopId = strShopId;
	}

	public int getIntState()
	{
		return intState;
	}

	public void setIntState(int intState)
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

	public String getStrCreateTime()
	{
		return strCreateTime;
	}

	public void setStrCreateTime(String strCreateTime)
	{
		this.strCreateTime = strCreateTime;
	}

}
