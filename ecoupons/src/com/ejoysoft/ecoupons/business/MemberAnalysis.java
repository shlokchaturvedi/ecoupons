package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class MemberAnalysis
{
	private Globa globa;
	private DbConnect db;
	String strMemberTable = "t_bz_member";
	String strPrintTable = "t_bz_coupon_print";

	public MemberAnalysis()
	{
	}

	public MemberAnalysis(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public MemberAnalysis(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	/**
	 * 根据起止时间返回会员活动数量
	 */
	public int returnLiveNum()
	{
		return returnNum("");
	}

	public int returnNum(String where2)
	{
		String where = "";
		if (stime.equals("") || stime.equals(null))
		{
			where = " where  dtcreatetime between '1000-01-01' and '" + etime + "' " + where2;
		}
		if (etime.equals("") || etime.equals(null))
		{
			where = " where  dtcreatetime between  '" + stime + "' and '9999-12-30' " + where2;
		}
		if (!(stime.equals("") || stime.equals(null)) && !(etime.equals("") || etime.equals(null)))
		{
			where = " where  dtcreatetime between '" + stime + "' and '" + etime + "' " + where2;
		}
		return getCount(where, strPrintTable);
	}
	

	/**
	 * 根据起止时间返回沉淀会员数量
	 */
	public int returnUnActiveNum()
	{
		int num = 0;
		Member member = new Member(globa);
		Vector<Member> vctMembers = new Vector<Member>();
		String active_member = (String) globa.application.getAttribute("UNACTIVE_MEMBER");
		vctMembers = member.list("", 0, 0);
		int j=0;
		for (int i = 0; i < vctMembers.size(); i++)
		{
			j=returnNum("and strId='" + vctMembers.get(i).getStrId() + "'") ;
			if (j<= Integer.parseInt(active_member)&&j>0)
			{
				num += 1;
			}
		}

		return num;
	}
	/**
	 * 根据起止时间返回活跃会员数量
	 */
	public int returnActiveNum()
	{
		int num = 0;
		Member member = new Member(globa);
		Vector<Member> vctMembers = new Vector<Member>();
		String active_member = (String) globa.application.getAttribute("ACTIVE_MEMBER");
		vctMembers = member.list("", 0, 0);
		for (int i = 0; i < vctMembers.size(); i++)
		{
			if (returnNum("and strId='" + vctMembers.get(i).getStrId() + "'") > Integer.parseInt(active_member))
			{
				num += 1;
			}
		}

		return num;

	}

	/**
	 * 根据起止时间返回会员总数量
	 */
	public int returnTotalNum()
	{
		String where = "";
		if (stime.equals("") || stime.equals(null))
		{
			where = " where  dtcreatetime between '1000-01-01' and '" + etime + "'";
		}
		if (etime.equals("") || etime.equals(null))
		{
			where = " where  dtcreatetime between  '" + stime + "' and '9999-12-30'";
		}
		if (!(stime.equals("") || stime.equals(null)) && !(etime.equals("") || etime.equals(null)))
		{
			where = " where  dtcreatetime between '1000-01-01' and '" + etime + "'";
		}
		
		return getCount(where, strMemberTable);

	}

	/**
	 * 根据起止时间返回新增会员数量
	 * 
	 * @param where
	 * @return
	 */
	public int returnAddNum()
	{
		String where = "";
		if (stime.equals("") || stime.equals(null))
		{
			where = " where  dtcreatetime between '1000-01-01' and '" + etime + "'";
		}
		if (etime.equals("") || etime.equals(null))
		{
			where = " where  dtcreatetime between  '" + stime + "' and '9999-12-30'";
		}
		if (!(stime.equals("") || stime.equals(null)) && !(etime.equals("") || etime.equals(null)))
		{
			where = " where  dtcreatetime between '" + stime + "' and '" + etime + "'";
		}
		return getCount(where, strMemberTable);

	}

	/*
	 * 查询符合条件的记录总数
	 */
	public int getCount(String where, String strTableName)
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

	private String stime;// 统计开始日期
	private String etime;// 统计截止日期

	public String getStime()
	{
		return stime;
	}

	public void setStime(String stime)
	{
		this.stime = stime;
	}

	public String getEtime()
	{
		return etime;
	}

	public void setEtime(String etime)
	{
		this.etime = etime;
	}
}
