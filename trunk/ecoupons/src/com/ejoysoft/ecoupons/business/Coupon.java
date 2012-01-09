package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class Coupon
{
	private String strId;
	private String strName;
	private String dtActiveTime;
	private String dtExpireTime;
	private String strShopId;
	private String strTerminalIds;
	private int intVip;
	private int intRecommend;
	private float flaPrice;
	private int intPrintLimit;
	private String strSmallImg;
	private String strLargeImg;
	private String strPrintImg;
	private String strCreator;
	private String dtCreateTime;
	private String strTerminals;// 投放终端编码

	/*
	 * 修改优惠券信息
	 */

	public boolean update(String tStrId)
	{
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET dtActiveTime = ?, ";
			if (this.strSmallImg != null && this.strSmallImg.length() > 0)
			{
				strSql += "strsmallimg = '" + strSmallImg + "',";
			}
			if (this.strLargeImg != null && this.strLargeImg.length() > 0)
			{
				strSql += "strlargeimg = '" + strLargeImg + "',";
			}
			if (this.strPrintImg != null && this.strPrintImg.length() > 0)
			{
				strSql += "strPrintImg = '" + strPrintImg + "',";
			}
			strSql += " strName = ?, strShopId = ?,strTerminalIds=?,  " + "dtExpireTime = ?,intVip=?,intRecommend=?,flaPrice=?,intPrintLimit=?"
					+ "  WHERE strId=? ";
			db.prepareStatement(strSql);
			db.setString(1, dtActiveTime);
			db.setString(2, strName);
			db.setString(3, strShopId);
			db.setString(4, strTerminalIds);
			db.setString(5, dtExpireTime);
			db.setInt(6, intVip);
			db.setInt(7, intRecommend);
			db.setFloat(8, flaPrice);
			db.setInt(9, intPrintLimit);
			db.setString(10, strId);
			db.executeUpdate();
			Globa.logger0("修改优惠券信息", globa.loginName, globa.loginIp, strSql, "优惠券管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("修改优惠券信息：" + e);
			return false;
		}
	}

	/*
	 * 增加优惠券信息
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();

		String sql = "insert into " + strTableName + " (strId,strName,strSmallImg,dtActiveTime,dtExpireTime,strShopId,strTerminalIds"
				+ ",intVip,intRecommend,flaPrice,intPrintLimit,strPrintImg,strLargeImg,strCreator,dtCreateTime) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		System.out.println(com.ejoysoft.common.Format.getDate());
		try
		{
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strName);
			db.setString(3, strSmallImg);
			db.setString(4, dtActiveTime);
			db.setString(5, dtExpireTime);
			db.setString(6, strShopId);
			System.out.println(strTerminals);
			db.setString(7, getTerminalIdsByNames(strTerminals));
			db.setInt(8, intVip);
			db.setInt(9, intRecommend);
			db.setFloat(10, flaPrice);
			db.setInt(11, intPrintLimit);
			db.setString(12, strPrintImg);
			db.setString(13, strLargeImg);
			db.setString(14, strUserName);
			db.setString(15, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("增加优惠券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
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

	/*
	 * 详细显示单条记录
	 */
	public Coupon show(String where)
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
	 * 根据条件，返回优惠券信息的集合
	 */
	public Vector<Coupon> list(String where, int startRow, int rowCount)
	{
		Vector<Coupon> beans = new Vector<Coupon>();
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
					Coupon theBean = new Coupon();
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

	// 获取投放终端IDs
	public String getTerminalIdsByNames(String terminals)
	{
		String terminalids = "";
		if (terminals != null && terminals.trim() != "")
		{
			Terminal obj0 = new Terminal(globa, false);
			String terminalnames[] = terminals.split("，");
			for (int i = 0; i < terminalnames.length; i++)
			{
				Terminal obj = new Terminal();
				obj = obj0.show("where strno='" + terminalnames[i].trim() + "'");
				if (obj != null)
				{
					terminalids += obj.getStrId();
					if (i < terminalnames.length - 1)
						terminalids += ",";
				}
			}
		}
		return terminalids;
	}

	// 获取投放终端编号s
	public String getTerminalNamesByIds(String terminalids)
	{
		String terminalnos = " ";
		if (terminalids != null && terminalids.trim() != "")
		{
			Terminal obj0 = new Terminal(globa, false);
			Terminal obj = new Terminal();
			String terminals[] = terminalids.trim().split(",");
			for (int i = 0; i < terminals.length; i++)
			{
				obj = obj0.show("where strid='" + terminals[i].trim() + "'");
				if (obj != null)
				{
					terminalnos += obj.getStrNo();
					if (i < terminals.length - 1)
						terminalnos += "，";
				}
			}
		}
		return terminalnos;
	}

	public Coupon load(ResultSet rs, boolean isView)
	{
		Coupon theBean = new Coupon();
		try
		{
			theBean.setDtActiveTime(rs.getString("dtActiveTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setDtExpireTime(rs.getString("dtExpireTime"));
			theBean.setFlaPrice(rs.getFloat("flaPrice"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setIntPrintLimit(rs.getInt("intPrintLimit"));
			theBean.setIntRecommend(rs.getInt("intRecommend"));
			theBean.setIntVip(rs.getInt("intVip"));
			theBean.setStrPrintImg(rs.getString("strPrintImg"));
			theBean.setStrLargeImg(rs.getString("strLargeImg"));
			theBean.setStrShopId(rs.getString("strShopId"));
			theBean.setStrTerminals(getTerminalNamesByIds(rs.getString("strTerminalIds")));
			theBean.setStrName(rs.getString("strName"));
			theBean.setStrSmallImg(rs.getString("strSmallImg"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	/*
	 * 返回是否推荐和vip
	 */
	public String returnStrRecommendOrVip(int num)
	{
		if (num == 0)
		{
			return "不是";
		} else if (num == 1)
		{
			return "是";
		} else
		{
			return "错误！";
		}

	}

	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_coupon";

	public Coupon()
	{
		// TODO Auto-generated constructor stub
	}

	public Coupon(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Coupon(Globa globa, boolean b)
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

	public String getStrName()
	{
		return strName;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

	public String getDtActiveTime()
	{
		if (!dtActiveTime.equals(null) && !dtActiveTime.equals(""))
		{
			return dtActiveTime.substring(0, dtActiveTime.length() - 2);
		}
		return dtActiveTime;
	}

	public void setDtActiveTime(String dtActiveTime)
	{
		this.dtActiveTime = dtActiveTime;
	}

	public String getDtExpireTime()
	{
		if (!dtExpireTime.equals(null) && !dtExpireTime.equals(""))
		{
			return dtExpireTime.substring(0, dtExpireTime.length() - 2);
		}
		return dtExpireTime;
	}

	public void setDtExpireTime(String dtExpireTime)
	{
		this.dtExpireTime = dtExpireTime;
	}

	public String getStrShopId()
	{
		return strShopId;
	}

	public void setStrShopId(String strShopId)
	{
		this.strShopId = strShopId;
	}

	public String getStrTerminalIds()
	{
		return strTerminalIds;
	}

	public void setStrTerminalIds(String strTerminalIds)
	{
		this.strTerminalIds = strTerminalIds;
	}

	public int getIntVip()
	{
		return intVip;
	}

	public void setIntVip(int intVip)
	{
		this.intVip = intVip;
	}

	public int getIntRecommend()
	{
		return intRecommend;
	}

	public void setIntRecommend(int intRecommend)
	{
		this.intRecommend = intRecommend;
	}

	public float getFlaPrice()
	{
		return flaPrice;
	}

	public void setFlaPrice(float flaPrice)
	{
		this.flaPrice = flaPrice;
	}

	public int getIntPrintLimit()
	{
		return intPrintLimit;
	}

	public void setIntPrintLimit(int intPrintLimit)
	{
		this.intPrintLimit = intPrintLimit;
	}

	public String getStrSmallImg()
	{
		return strSmallImg;
	}

	public void setStrSmallImg(String strSmallImg)
	{
		this.strSmallImg = strSmallImg;
	}

	public String getStrLargeImg()
	{
		return strLargeImg;
	}

	public void setStrLargeImg(String strLargeImg)
	{
		this.strLargeImg = strLargeImg;
	}

	public String getStrPrintImg()
	{
		return strPrintImg;
	}

	public void setStrPrintImg(String strPrintImg)
	{
		this.strPrintImg = strPrintImg;
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

	public String getStrTerminals()
	{
		return strTerminals;
	}

	public void setStrTerminals(String strTerminals)
	{
		this.strTerminals = strTerminals;
	}

}
