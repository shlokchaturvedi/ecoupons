package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class CouponComment
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_coupon_comment";
	public CouponComment()
	{
		// TODO Auto-generated constructor stub
	}

	public CouponComment(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public CouponComment(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}
	private String strId;
	private String strMemberCardNo;
	private String StrCouponId;
	private String StrComment;
	private String strShopName;
	private String strCouponName;
	private String strCreator;
	private String dtCreateTime;
	
	/**
	 * 详细显示单条记录
	 */
	public CouponComment show(String where)
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
	 * 删除评论信息
	 * @param where
	 * @param where2
	 * @param strid
	 * @return
	 */
	public boolean delete(String where)
	{
		String sql1 = "delete from " + strTableName + "  ".concat(where);
		try
		{
			db.executeUpdate(sql1);// 删除终端模版
			Globa.logger0("删除优惠券评论信息", globa.loginName, globa.loginIp, sql1, "优惠券管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据条件返回会员评论的集合
	 * @param where
	 * @param startRow
	 * @param rowCount
	 * @return
	 */
	public Vector<CouponComment> list(String where, int startRow, int rowCount)
	{
		Vector<CouponComment> beans = new Vector<CouponComment>();
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
					CouponComment theBean = new CouponComment();
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
	
	public CouponComment load(ResultSet rs, boolean isView)
	{
		CouponComment theBean = new CouponComment();
		try
		{
			Coupon objCoupon  = new Coupon(globa);
			Shop objShop = new Shop(globa);
			theBean.setStrId(rs.getString("strId"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setStrComment(rs.getString("strComment"));
			theBean.setStrCouponId(rs.getString("strCouponId"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
			Coupon objCoupon2 = objCoupon.show(" where strid='"+rs.getString("strCouponId")+"'");
			if(objCoupon2 !=null)
			{
				Shop objShop2 = objShop.show(" where strid='"+objCoupon2.getStrShopId()+"'");
				if(objShop2 !=null)
				{
					theBean.setStrShopName(objShop2.getStrBizName());
					theBean.setStrCouponName(objCoupon2.getStrName());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}
	
	// 添加评论
	public boolean addComment()
	{
		String strSql = "";
		strId = UID.getID();
		try
		{

			strSql = "insert into " + strTableName + "  (strid, strmembercardno,strcouponid, strcomment,"
					+ "strcreator, dtcreatetime) values(?,?,?,?,?,?)";			
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strMemberCardNo);
			db.setString(3, StrCouponId);
			db.setString(4, StrComment);
			db.setString(5, strCreator);
			db.setString(6, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("添加优惠券评论信息", globa.memberSession.getStrCardNo(), globa.loginIp, strSql, "网站/优惠券评论","system");
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加优惠券评论异常");
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
		return StrCouponId;
	}

	public void setStrCouponId(String strCouponId)
	{
		StrCouponId = strCouponId;
	}

	public String getStrComment()
	{
		return StrComment;
	}

	public void setStrComment(String strComment)
	{
		StrComment = strComment;
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

	public Globa getGloba() {
		return globa;
	}

	public void setGloba(Globa globa) {
		this.globa = globa;
	}

	public DbConnect getDb() {
		return db;
	}

	public void setDb(DbConnect db) {
		this.db = db;
	}

	public String getStrShopName() {
		return strShopName;
	}

	public void setStrShopName(String strShopName) {
		this.strShopName = strShopName;
	}

	public String getStrCouponName() {
		return strCouponName;
	}

	public void setStrCouponName(String strCouponName) {
		this.strCouponName = strCouponName;
	}
	
	
}
