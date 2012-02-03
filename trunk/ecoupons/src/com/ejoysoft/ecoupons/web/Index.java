package com.ejoysoft.ecoupons.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.system.SysPara;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Index
{
	SysPara sysPara;
	private Globa globa;
	private DbConnect db;

	/**
	 * 返回商家行业和行业中商家的总数
	 * 
	 * @return
	 */
	public HashMap<String, Integer> returnTrade()
	{
		HashMap<String, Integer> hmTrade = new HashMap<String, Integer>();
		Vector<SysPara> vctParas = new Vector<SysPara>();
		Shop shop = new Shop(globa);
		sysPara = new SysPara(globa);
		try
		{
			vctParas = sysPara.list("where strtype='商家行业'", 0, 0);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < vctParas.size(); i++)
		{
			hmTrade.put(vctParas.get(i).getStrName(), shop.getCount("where strtrade='" + vctParas.get(i).getStrId() + "'"));
		}
		return hmTrade;
	}

	/**
	 * 根据下载排行返回top8优惠券名称
	 * 
	 * @return
	 */
	public String[] returnTopCoupons()
	{
		String[]strCouponNames=null;
       Coupon coupon=new Coupon(globa);
      return strCouponNames;
	}

	public Index()
	{
	}
	/**
	 * 分类返回所有行业的优惠券的记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String,Vector<Coupon>> getCouponsByClassfiction()
	{
		HashMap<String,Vector<Coupon>> allcoupons = new HashMap<String,Vector<Coupon>>() ;
		List<SysPara> listParas = new ArrayList <SysPara>();
		Coupon coupon = new Coupon(globa);
		Vector<Coupon> vctcoupon = new Vector<Coupon>();
		sysPara = new SysPara(globa);
		listParas = sysPara.list("商家行业");
		if(listParas!=null)
		{
			for (int i = 0; i < listParas.size(); i++)
			{
				vctcoupon = coupon.getPerTradeCoupons(listParas.get(i).getStrId());
				allcoupons.put( listParas.get(i).getStrName(),vctcoupon);
			}
		}		
		return allcoupons;
	}

	public Index(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Index(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}
}
