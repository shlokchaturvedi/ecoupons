package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Statement;


public class TerminalTemplate
{
	private Globa globa;
	private DbConnect db;

	public TerminalTemplate()
	{
	}

	public TerminalTemplate(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public TerminalTemplate(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	String strTableName = "t_bz_terminal_template";

	/**
	 *  添加终端模版信息
	 * @return
	 */
	public boolean add()
	{
		String strSql = "";
		strId = UID.getID(); 
		// 添加终端模版信息
		strSql = "insert into " + strTableName + "  (strid, strname, strlocation, strsize, strbgimage, strfontfamily, "
				+ "intfontsize,strfontcolor, strcreator, dtcreatetime,strmoduleoftempl,strcontent,strintro) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strName);
			db.setString(3, strLocation);
			db.setString(4, strSize);
			db.setString(5, strBgImage);
			db.setString(6, strFontFamily);
			db.setFloat(7, intFontSize);
			db.setString(8, strFontColor);
			db.setString(9, strCreator);
			db.setString(10, Format.getDateTime());
			db.setString(11, strModuleOfTempl);
			db.setString(12, strContent);
			db.setString(13, strIntro);
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("添加终端模版信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加终端模版信息异常");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除终端模版信息
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
			Globa.logger0("删除终端模版信息", globa.loginName, globa.loginIp, sql1, "终端模版管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  终端模版更新信息
	 * @param strId2
	 * @return
	 */
	public boolean update(String strId)
	{
		try
		{
			String strSql = "update " + strTableName + "  set strname=?, strlocation=?, strfontfamily=?, "
					+ "intfontsize=?,strfontcolor=?, strmoduleoftempl=?,";
			if (this.strBgImage != null && this.strBgImage.trim().length() > 0)
			{
				strSql += "strbgimage = '" + strBgImage + "',";
			}			
			strSql += "strsize=?, strcontent=?,strintro=? where strid=? ";
			db.prepareStatement(strSql);
			db.setString(1, strName);
			db.setString(2, strLocation);
			db.setString(3, strFontFamily);
			db.setInt(4, intFontSize);
			db.setString(5, strFontColor);
			db.setString(6, strModuleOfTempl);
			db.setString(7, strSize);
			db.setString(8, strContent);
			db.setString(9, strIntro);
			db.setString(10, strId);
			db.executeUpdate();
			Globa.logger0("更新终端模版信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("更新终端模版信息异常" + e);
			return false;
		}
	}

	/**
	 *  查询
	 * @param where
	 * @return
	 */
	public TerminalTemplate show(String where)
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
	 * 封装终端模版信息结果集
	 * @param rs
	 * @param isView
	 * @return
	 */
	public TerminalTemplate load(ResultSet rs, boolean isView)
	{
		TerminalTemplate theBean = new TerminalTemplate();
		try
		{
			theBean.setStrId(rs.getString("strid"));
			theBean.setStrName(rs.getString("strname"));
			theBean.setStrLocation(rs.getString("strlocation"));
			theBean.setStrSize(rs.getString("strsize"));
			theBean.setStrBgImage(rs.getString("strbgimage"));
			theBean.setIntFontSize(rs.getInt("intfontsize"));
			theBean.setStrFontColor(rs.getString("strfontcolor"));
			theBean.setStrFontFamily(rs.getString("strfontfamily"));
			theBean.setStrModuleOfTempl(rs.getString("strmoduleoftempl"));
			theBean.setStrContent(rs.getString("strcontent"));
			theBean.setStrCreator(rs.getString("strcreator"));
			theBean.setDtCreateTime(rs.getString("dtcreatetime"));
			theBean.setStrIntro(rs.getString("strintro"));
			theBean.setIntSort(rs.getInt("intsort"));
			String fontString = theBean.getStrModuleOfTempl();
			theBean.setStrModuleOfTemplName(theBean.returnModuleName(fontString));
			String fontcolor = theBean.getStrFontColor();
			theBean.setStrFontColorName(theBean.returnColorName(fontcolor));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}
	public String returnModuleName(String fontString)
	{
		TerminalTemplate theBean = new TerminalTemplate();
		if (fontString.equals("top")) { 
			theBean.setStrModuleOfTemplName("界面顶部");
		}else if (fontString.equals("home")) {
			theBean.setStrModuleOfTemplName("界面中间首页");
		}else if (fontString.equals("shopInfo")) {
			theBean.setStrModuleOfTemplName("商家详细");
		}else if (fontString.equals("shop")) {
			theBean.setStrModuleOfTemplName("商家二级");
		}else if (fontString.equals("coupon")) {
			theBean.setStrModuleOfTemplName("优惠劵二级和VIP专区");
		}else if (fontString.equals("myInfo")) {
			theBean.setStrModuleOfTemplName("我的专区");
		}else if (fontString.equals("nearshop")) {
			theBean.setStrModuleOfTemplName("周边商家");
		}else if (fontString.equals("ad")) {
			theBean.setStrModuleOfTemplName("广告");
		}else if (fontString.equals("waitlogin")) {
			theBean.setStrModuleOfTemplName("验证用户登录时显示的等待界面");
		}else if (fontString.equals("waitdownload")) {
			theBean.setStrModuleOfTemplName("下载时显示的等待界面");
		}else if (fontString.equals("bottom")) {
			theBean.setStrModuleOfTemplName("界面底部");
		}else {
			theBean.setStrModuleOfTemplName("");
		}
		return theBean.getStrModuleOfTemplName();
	}
	public String returnColorName(String fontcolor)
	{
		TerminalTemplate theBean = new TerminalTemplate();
		if (fontcolor.equals("white")) {
			theBean.setStrFontColorName("白色");
		}else if(fontcolor.equals("red")) {
			theBean.setStrFontColorName("红色");
		}else if(fontcolor.equals("black")) {
			theBean.setStrFontColorName("黑色");
		}else if(fontcolor.equals("blue")) {
			theBean.setStrFontColorName("蓝色");
		}else if(fontcolor.equals("gray")) {
			theBean.setStrFontColorName("灰色");
		}else if(fontcolor.equals("green")) {
			theBean.setStrFontColorName("绿色");
		}else if(fontcolor.equals("yellow")) {
			theBean.setStrFontColorName("黄色");
		}else if(fontcolor.equals("purple")) {
			theBean.setStrFontColorName("紫色");
		}else {
			theBean.setStrFontColorName("");
		}
		return theBean.getStrFontColorName();
	}
	/**
	 * 分页整理
	 * @param where
	 * @param startRow
	 * @param rowCount
	 * @return
	 */
	public Vector<TerminalTemplate> list(String where, int startRow, int rowCount)
	{
		Vector<TerminalTemplate> beans = new Vector<TerminalTemplate>();
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
					TerminalTemplate theBean = new TerminalTemplate();
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
	public Vector<String> listAllModules()
	{
		Vector<String> beans = new Vector<String>();
		try
		{
			TerminalTemplate template = new TerminalTemplate(globa);
			String sql = "SELECT distinct(strmoduleoftempl)  FROM  " + strTableName;
			ResultSet rs = db.executeQuery(sql);
			if (rs != null && rs.next())
			{
				do
				{
					beans.add(rs.getString("strmoduleoftempl"));
				} while (rs.next());
			}
			rs.close();
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}
	

	/**
	 * 查询影响记录数
	 * @param where
	 * @return
	 */
	public int getCount(String where)
	{
		int count = 0;
		try
		{
			String sql = "select count(strId) from " + strTableName + "  ";
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
	 *  根据终端模版名 返回终端模版id
	 */
	public String getShopIdByName(String strName)
	{
		try
		{
			String sql = "select * FROM  " + strTableName + " ";
			if (!strName.equals(""))
			{
				sql +=" where strbizname='" + strName + "'";
			}
			ResultSet rs = db.executeQuery(sql);
			if (rs != null && rs.next())
			{
				return rs.getString("strid");
			} else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	private String strId;// 终端模版信息Id
	private String strName;// 终端模版名称
	private String strLocation;
	private String strSize;
	private String strBgImage;
	private String strFontFamily;
	private String strModuleOfTemplName;
	private int intFontSize;
	private String strFontColor;
	private String strFontColorName;
	private String strModuleOfTempl;
	private String strContent;
	private String strIntro;
	private String strCreator;
	private String dtCreateTime;
	private int intSort;
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
	public int getIntSort() {
		return intSort;
	}

	public void setIntSort(int intSort) {
		this.intSort = intSort;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrLocation() {
		return strLocation;
	}

	public void setStrLocation(String strLocation) {
		this.strLocation = strLocation;
	}

	public String getStrSize() {
		return strSize;
	}

	public void setStrSize(String strSize) {		
		this.strSize = strSize;
	}

	public String getStrBgImage() {
		return strBgImage;
	}

	public void setStrBgImage(String strBgImage) {
		this.strBgImage = strBgImage;
	}

	public String getStrFontFamily() {
		return strFontFamily;
	}

	public void setStrFontFamily(String strFontFamily) {
		this.strFontFamily = strFontFamily;
	}

	public int getIntFontSize() {
		return intFontSize;
	}

	public void setIntFontSize(int intFontSize) {
		this.intFontSize = intFontSize;
	}

	public String getStrFontColor() {
		return strFontColor;
	}

	public void setStrFontColor(String strFontColor) {
		this.strFontColor = strFontColor;
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

	public String getStrModuleOfTempl() {
		return strModuleOfTempl;
	}

	public void setStrModuleOfTempl(String strModuleOfTempl) {
		this.strModuleOfTempl = strModuleOfTempl;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public String getStrModuleOfTemplName() {
		return strModuleOfTemplName;
	}

	public void setStrModuleOfTemplName(String strModuleOfTemplName) {
		this.strModuleOfTemplName = strModuleOfTemplName;
	}

	public String getStrFontColorName() {
		return strFontColorName;
	}

	public void setStrFontColorName(String strFontColorName) {
		this.strFontColorName = strFontColorName;
	}

	public String getStrIntro() {
		return strIntro;
	}

	public void setStrIntro(String strIntro) {
		this.strIntro = strIntro;
	}
	
}
