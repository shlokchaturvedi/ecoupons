package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class CouponFavourite
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_coupon_favourite";
	public CouponFavourite()
	{
		// TODO Auto-generated constructor stub
	}

	public CouponFavourite(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public CouponFavourite(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}
	private String strId;
	private String strMemberCardNo;
	private String strCouponId;
	private String dtFavouriteTime;
	private String strCreator;
	private String dtCreateTime;
	
	
	public boolean delete(String where) {
        try {
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            Globa.logger0("删除优惠券收藏记录", globa.loginName, globa.loginIp, sql, "网站", "");
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }
	
	

	/**
	 * 详细显示单条记录
	 */
	public CouponFavourite show(String where)
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
	 * 根据条件返回会员收藏的集合
	 */

	public Vector<CouponFavourite> list(String where, int startRow, int rowCount)
	{
		Vector<CouponFavourite> beans = new Vector<CouponFavourite>();
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
					CouponFavourite theBean = new CouponFavourite();
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
	
	public CouponFavourite load(ResultSet rs, boolean isView)
	{
		CouponFavourite theBean = new CouponFavourite();
		try
		{
			theBean.setStrId(rs.getString("strId"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setDtFavouriteTime(rs.getString("dtFavouriteTime"));
			theBean.setStrCouponId(rs.getString("strCouponId"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}
	// 添加优惠券收藏记录
	public boolean add()
	{
		String strSql = "";
		strId = UID.getID();
		try
		{
			// 添加券打机信息
			strSql = "insert into " + strTableName + "  (strid, strmembercardno, strcouponid, dtfavouritetime , strcreator, dtcreatetime)"
					+ " values(?,?,?,?,?,?)";
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strMemberCardNo);
			db.setString(3, strCouponId);
			db.setString(4, com.ejoysoft.common.Format.getDateTime());
			db.setString(5, strCreator);
			db.setString(6, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("添加优惠券收藏记录", strMemberCardNo, globa.loginIp, strSql, "优惠券收藏", "system");
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加优惠券收藏记录异常");
			e.printStackTrace();
			return false;
		}
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

	public String getDtFavouriteTime()
	{
		if (dtFavouriteTime!=null&&!dtFavouriteTime.equals(""))
		{
			return dtFavouriteTime.substring(0, dtFavouriteTime.length()-2);
		}
		return "";
	}

	public void setDtFavouriteTime(String dtFavouriteTime)
	{
		this.dtFavouriteTime = dtFavouriteTime;
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
