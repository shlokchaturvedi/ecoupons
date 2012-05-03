package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class ShopComment
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_shop_comment";
	public ShopComment()
	{
		// TODO Auto-generated constructor stub
	}

	public ShopComment(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public ShopComment(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}
	private String strId;
	private String strMemberCardNo;
	private String strShopId;
	private String strComment;
	private String strShopName;
	private String strBizName;
	private String strCreator;
	private String dtCreateTime;
	
	/**
	 * 详细显示单条记录
	 */
	public ShopComment show(String where)
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
	public Vector<ShopComment> list(String where, int startRow, int rowCount)
	{
		Vector<ShopComment> beans = new Vector<ShopComment>();
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
					ShopComment theBean = new ShopComment();
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
	
	public ShopComment load(ResultSet rs, boolean isView)
	{
		ShopComment theBean = new ShopComment();
		try
		{
			Shop objShop = new Shop(globa);
			theBean.setStrId(rs.getString("strid"));
			theBean.setDtCreateTime(rs.getString("dtcreatetime"));
			theBean.setStrComment(rs.getString("strcomment"));
			theBean.setStrShopId(rs.getString("strshopid"));
			theBean.setStrCreator(rs.getString("strcreator"));
			theBean.setStrMemberCardNo(rs.getString("strmembercardno"));
			Shop objShop2 = objShop.show(" where strid='"+rs.getString("strshopid")+"'");
			theBean.setStrShopName(objShop2.getStrShopName());
			theBean.setStrBizName(objShop2.getStrBizName());
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
			strSql = "insert into " + strTableName + "  (strid, strmembercardno,strshopid, strcomment,"
					+ "strcreator, dtcreatetime) values(?,?,?,?,?,?)";			
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strMemberCardNo);
			db.setString(3, strShopId);
			db.setString(4, strComment);
			db.setString(5, strCreator);
			db.setString(6, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("添加商家留言信息", globa.memberSession.getStrCardNo(), globa.loginIp, strSql, "网站/商家留言","system");
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加商家留言异常");
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

	public String getStrShopId() {
		return strShopId;
	}

	public void setStrShopId(String strShopId) {
		this.strShopId = strShopId;
	}

	public String getStrComment() {
		return strComment;
	}

	public void setStrComment(String strComment) {
		this.strComment = strComment;
	}

	public String getStrShopName() {
		return strShopName;
	}

	public void setStrShopName(String strShopName) {
		this.strShopName = strShopName;
	}

	public String getStrBizName() {
		return strBizName;
	}

	public void setStrBizName(String strBizName) {
		this.strBizName = strBizName;
	}

	public String getStrCreator() {
		return strCreator;
	}

	public void setStrCreator(String strCreator) {
		this.strCreator = strCreator;
	}

	public String getDtCreateTime() {
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}

}
