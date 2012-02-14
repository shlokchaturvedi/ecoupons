package com.ejoysoft.ecoupons.web;

import java.sql.ResultSet;
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
		public HashMap<SysPara, Integer> returnTradeForCoup()
		{
			HashMap<SysPara, Integer> hmTrade = new HashMap<SysPara, Integer>();
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
				String tradeid = vctParas.get(i).getStrId();
				String sql = "select count(a.strid) from t_bz_coupon a left join t_bz_shop b on a.strshopid=b.strid where b.strtrade='"+tradeid+"'";
//				System.out.println(sql+":Index.returnTradeForCoup()");
				ResultSet re = db.executeQuery(sql);
				int num=0;
				try {
					if(re.next())
						num = re.getInt("count(a.strid)");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				hmTrade.put(vctParas.get(i), num);			
			}
			return hmTrade;
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


	/**
	 * 根据商家行业id返回行业下的商家和商家下的优惠券
	 */
	public HashMap<String, String> returnShopCoupon(String strtrade)
	{
		HashMap<String, String>hmShopCoupon=new HashMap<String, String>();
		Shop shop=new Shop(globa);
		Coupon coupon=new Coupon(globa);
		Vector<Coupon>vctCoupons=new Vector<Coupon>();
		String strWhere="";
		if (strtrade!=null)
		{
			strWhere="where strtrade='"+strtrade+"' order by dtcreatetime  desc";
		}
		Vector<Shop>vctShops=shop.list(strWhere, 0, 0);
		for (int i = 0; i < vctShops.size(); i++)
		{
			vctCoupons.addAll(coupon.list("where strshopid='"+vctShops.get(i).getStrId()+"' and intRecommend=1 order by dtcreatetime  desc ", 0, 0));
		}
		if (vctCoupons.size()>12)
		{
			for (int i = 0; i < 12; i++)
			{
				hmShopCoupon.put(shop.returnBizShopName("where strid='"+vctCoupons.get(i).getStrShopId()+"' order by dtcreatetime  desc "), vctCoupons.get(i).getStrName());
				
			}
		}else {
			for (int i = 0; i < vctCoupons.size(); i++)
			{
				hmShopCoupon.put(shop.returnBizShopName("where strid='"+vctCoupons.get(i).getStrShopId()+"' order by dtcreatetime  desc "), vctCoupons.get(i).getStrName());
				
			}
		}
		
		return hmShopCoupon;
	}
	
	
	/**
	 * 返回商家行业和行业中商家的总数
	 * 
	 * @return
	 */
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
	public Vector<String> returnTopCoupons(String flag)
	{
		String strSql="";
		Vector<String>vctCoupons=new Vector<String>();
		Coupon coupon=new Coupon(globa);
		if ("日".equals(flag))
		{
			strSql="select strcouponid from t_bz_coupon_print where date(dtprinttime)=date(now())group by strcouponid";
		} else if ("周".equals(flag))
		{
            strSql="select strcouponid from t_bz_coupon_print where month(dtprinttime) =month(curdate()) and week(dtprinttime) = week(curdate())group by strcouponid";
		} else if ("月".equals(flag))
		{
			strSql = "select strcouponid from t_bz_coupon_print where month(dtprinttime) =month(curdate()) and year(dtprinttime) = year(curdate())group by strcouponid";
		}
		ResultSet resultSet=db.executeQuery(strSql);
		try
		{
			while (resultSet.next())
			{
				Coupon couponTemp=coupon.show("where strid='"+resultSet.getString("strcouponid")+"'");
				if (couponTemp!=null)
				{
					vctCoupons.add(couponTemp.getStrName());
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
