package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class SalesmanAnalysis
{
	private Globa globa;
	private DbConnect db;
	public SalesmanAnalysis()
	{
		// TODO Auto-generated constructor stub
	}

	public SalesmanAnalysis(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public SalesmanAnalysis(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	String strTableName = "t_bz_member";
	String strTableName2 = "t_bz_member_recharge";
	String strTableName3 = "t_bz_coupon_print";

	//封装代理员统计结果
	public SalesmanAnalysis loadSalesman(ResultSet re,boolean isView)
	{
		SalesmanAnalysis thebean = new SalesmanAnalysis();
		String salesman;
		try {
			salesman = re.getString("strsalesman");
			int membernum = this.getMemberNumBySalesman(salesman, this.stime, this.etime);
			int account = this.getTotalAccountBySalesman(salesman,this.stime,this.etime);
			int printnum = this.getPrintNumBySalesman(salesman,this.stime,this.etime);	
			thebean.setStrSalesman(salesman);
			thebean.setMembernum(membernum);
			thebean.setAccount(account);
			thebean.setPrintnum(printnum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thebean;
	}
	//查询代理员新增会员数量
	public int getMemberNumBySalesman(String salesman,String stime,String etime)
	{
		int num = 0;
		Member obj = new Member(globa);
		String where ="where strsalesman='"+salesman+"'";
		if(stime.equals("")||stime.equals(null))
		{
			where +=" and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			where +=" and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			where +=" and  dtcreatetime between '"+stime+"' and '"+etime+"'";
	    }
		num = obj.getCount(where);		
		return num;
	}
	//获取代理员的会员的充值金额
	public int getTotalAccountBySalesman(String salesman,String stime,String etime)
	{
		int account=0;
		String sql= "select a.intmoney from "+strTableName2+" a left join "+strTableName+" b on a.strmembercardno = b.strcardno where b.strsalesman='"+salesman+"'";
		if(stime.equals("")||stime.equals(null))
		{
			sql +=" and a.dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql +=" and a.dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql +=" and a.dtcreatetime between '"+stime+"' and '"+etime+"'";
	    }
		ResultSet re = db.executeQuery(sql);		
		try {
			if(re!=null)
			{
				while(re.next()){
					int intmoney = re.getInt("a.intmoney");
					account += intmoney;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	//获取代理员的会员的消费次数
	public int getPrintNumBySalesman(String salesman,String stime,String etime)
	{
		int printnum=0;
		String sql= "select count(a.strid) from "+strTableName3+" a left join "+strTableName+" b on a.strmembercardno = b.strcardno where b.strmobileno<>'' and b.strmobileno is not null and b.strpwd<>'' and b.strpwd is not null and b.strsalesman='"+salesman+"'";
		if(stime.equals("")||stime.equals(null))
		{
			sql +=" and a.dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql +=" and a.dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql +=" and a.dtcreatetime between '"+stime+"' and '"+etime+"'";
	    }
		ResultSet re = db.executeQuery(sql);	
		try {
			if(re!=null&&re.next())
			{
				printnum = re.getInt("count(a.strid)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return printnum;
	}
	
	public Vector<SalesmanAnalysis> getSalesmanAnaResult(String where)
	{
		Vector<SalesmanAnalysis> vector = new Vector<SalesmanAnalysis>();
		Member obj = new Member(globa);
		String sql = "select distinct(strsalesman) from " +strTableName;
		try {
			if (where.length() > 0)
	            sql = String.valueOf(sql) + String.valueOf(where);
			System.err.println(sql);
			ResultSet re = db.executeQuery(sql);
			if(re!=null && re.next())
			{
				do{
					vector.addElement(this.loadSalesman(re, true));
				}while(re.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vector;
	}
	
	private String strId;// 自动生成
	private String strSalesman;// 销售员姓名
	private int membernum;//代理员新增会员数量
	private int account;//代理员充值金额
	private int printnum;//代理员的会员消费记录数
	private String etime;
	private String stime;
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

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(String strSalesman) {
		this.strSalesman = strSalesman;
	}

	public int getMembernum() {
		return membernum;
	}

	public void setMembernum(int membernum) {
		this.membernum = membernum;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getPrintnum() {
		return printnum;
	}

	public void setPrintnum(int printnum) {
		this.printnum = printnum;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

}
