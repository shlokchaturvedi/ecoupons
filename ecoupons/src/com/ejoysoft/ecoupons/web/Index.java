package com.ejoysoft.ecoupons.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.system.SysPara;

public class Index
{
	SysPara sysPara;
	private Globa globa;
	private DbConnect db;

	/**
	 * 返回商家行业和行业中优惠券的总数
	 * 
	 * @return
	 */
	public HashMap<String, Integer> returnTradeForCoup()
	{
		HashMap<String, Integer> hmTrade = new HashMap<String, Integer>();
		List<SysPara> vctParas = new ArrayList<SysPara>();
		sysPara = new SysPara(globa);
		vctParas = sysPara.list("商家行业");
		for (int i = 0; i < vctParas.size(); i++)
		{
			String tradeid = vctParas.get(i).getStrId();
			String sql = "select count(a.strid) from t_bz_coupon a left join t_bz_shop b on a.strshopid=b.strid where b.strtrade='"+tradeid+"'";
			ResultSet re = db.executeQuery(sql);
			int num=0;
			try {
				if(re.next())
					num = re.getInt("count(a.strid)");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			hmTrade.put(vctParas.get(i).getStrName(), num);		
			//System.out.println(vctParas.get(i).getStrName()+":Index.returnTradeForCoup()"+num);				
		}
		return hmTrade;
	}


	/**
	 * 分类返回所有行业的优惠券的记录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Vector<Coupon>> getCouponsByClassfiction()
	{
		HashMap<String, Vector<Coupon>> allcoupons = new HashMap<String, Vector<Coupon>>();
		Coupon coupon = new Coupon(globa);
		Vector<Coupon> vctcoupon = new Vector<Coupon>();
		List<SysPara> listParas = new ArrayList<SysPara>();
		sysPara = new SysPara(globa);
		listParas = sysPara.list("商家行业");
		if (listParas != null)
		{
			for (int i = 0; i < listParas.size(); i++)
			{
				vctcoupon = coupon.getPerTradeCoupons(listParas.get(i).getStrId());
				allcoupons.put(listParas.get(i).getStrName(), vctcoupon);
			}
		}
		return allcoupons;
	}

	/**
	 * 根据商家行业id返回行业下的商家和商家下的优惠券
	 */
	public Vector<String[]> returnVctShopCoupon(String strtrade)
	{
		Vector<String[]>vctShopCoupons=new Vector<String[]>();
		Shop shop = new Shop(globa);
		Coupon coupon = new Coupon(globa);
		Vector<Coupon> vctCoupons = new Vector<Coupon>();
		String strWhere = "";
		if (strtrade != null)
		{
			strWhere = "where strtrade='" + strtrade + "' order by dtcreatetime  desc ";
		}
		Vector<Shop> vctShops = shop.list(strWhere, 0, 0);
		for (int i = 0; i < vctShops.size(); i++)
		{
			vctCoupons.addAll(coupon.list("where strshopid='" + vctShops.get(i).getStrId() + "' and intRecommend=1 order by dtcreatetime  desc LIMIT 1", 0,
					0));
		}
		for (int i = 0; i < vctCoupons.size(); i++)
		{
			String[]strShopCoupons=new String[3];
			strShopCoupons[0]=shop.returnBizShopName("where strid='" + vctCoupons.get(i).getStrShopId() + "' order by dtcreatetime  desc LIMIT 12");
			strShopCoupons[1]=vctCoupons.get(i).getStrName();
			strShopCoupons[2]=vctCoupons.get(i).getStrId();
			vctShopCoupons.add(strShopCoupons);
		}
		return vctShopCoupons;
	}
	public HashMap<String, String> returnShopCoupon(String strtrade)
	{
		HashMap<String, String> hmShopCoupon = new HashMap<String, String>();
		Shop shop = new Shop(globa);
		Coupon coupon = new Coupon(globa);
		Vector<Coupon> vctCoupons = new Vector<Coupon>();
		String strWhere = "";
		if (strtrade != null&&strtrade!="")
		{
			strWhere = "where strtrade='" + strtrade + "' order by dtcreatetime  desc LIMIT 12";
		}
		Vector<Shop> vctShops = shop.list(strWhere, 0, 0);
		for (int i = 0; i < vctShops.size(); i++)
		{
			vctCoupons.addAll(coupon.list("where strshopid='" + vctShops.get(i).getStrId() + "' and intRecommend=1 order by dtcreatetime  desc ", 0,
					0));
		}
		for (int i = 0; i < vctCoupons.size(); i++)
		{
			hmShopCoupon.put(shop.returnBizShopName("where strid='" + vctCoupons.get(i).getStrShopId() + "' order by dtcreatetime  desc "),
					vctCoupons.get(i).getStrName());

		}
		return hmShopCoupon;
	}

	/**
	 * 返回商家行业和行业中商家的总数
	 * 
	 * @return
	 */
	public Vector<String[]> returnVctTrades()
	{
		
		Vector<String[]> vctTrades=new Vector<String[]>();
		Shop shop = new Shop(globa);
		List<SysPara> listParas = new ArrayList<SysPara>();
		sysPara = new SysPara(globa);
		listParas = sysPara.list("商家行业");
		for (int i = 0; i < listParas.size(); i++)
		{
			String [] strTrades=new String[3];
			strTrades[0]=listParas.get(i).getStrId();
			strTrades[1]=listParas.get(i).getStrName();
			strTrades[2]=String.valueOf(shop.getCount("where strtrade='" + listParas.get(i).getStrId() + "'"));
			vctTrades.add(strTrades);
		}
		return vctTrades;
		
	}
	public HashMap<SysPara, Integer> returnTrade()
	{
		HashMap<SysPara, Integer> hmTrade = new HashMap<SysPara, Integer>();
		Vector<SysPara> vctParas = new Vector<SysPara>();
		Shop shop = new Shop(globa);
		sysPara = new SysPara(globa);
		try
		{
			vctParas = sysPara.list("where strtype='商家行业' order by dcreatdate  desc", 0, 0);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < vctParas.size(); i++)
		{
			hmTrade.put(vctParas.get(i), shop.getCount("where strtrade='" + vctParas.get(i).getStrId() + "'"));
		}
		return hmTrade;
	}

	/**
	 * 根据下载排行返回top8优惠券名称
	 * 
	 * @return
	 */
	public Vector<String[]> returnTopCoupons(String flag)
	{
		String strSql = "";
		Vector<String[]> vctCoupons = new Vector<String[]>();
		Coupon coupon = new Coupon(globa);
		if ("日".equals(flag))
		{
			strSql = "select strcouponid from t_bz_coupon_print where date(dtprinttime)=date(now())group by strcouponid order by count(strcouponid) desc limit 8";
		} else if ("周".equals(flag))
		{
			strSql = "select strcouponid from t_bz_coupon_print where month(dtprinttime) =month(curdate()) and week(dtprinttime) = week(curdate())group by strcouponid order by count(strcouponid) desc limit 8";
		} else if ("月".equals(flag))
		{
			strSql = "select strcouponid from t_bz_coupon_print where month(dtprinttime) =month(curdate()) and year(dtprinttime) = year(curdate())group by strcouponid order by count(strcouponid) desc limit 8";
		}
		ResultSet resultSet = db.executeQuery(strSql);
		try
		{
			while (resultSet.next())
			{
				Coupon couponTemp = coupon.show("where strid='" + resultSet.getString("strcouponid") + "'");
				if (couponTemp != null)
				{
					String [] strCoupons=new String[2];
					strCoupons[0]=couponTemp.getStrId();
					strCoupons[1]=couponTemp.getStrName();
					vctCoupons.add(strCoupons);
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vctCoupons;
	}

	public Index()
	{
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
