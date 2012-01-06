package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
//import javax.servlet.ServletContext;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-1
 * Time: 9:12:43
 * To change this template use Options | File Templates.
 */
public class CouponTop {
    private Globa globa;
    private DbConnect db;
    public CouponTop() {
    }

    public CouponTop(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public CouponTop(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName1 = "t_bz_coupon";
    String strTableName3 = "t_bz_coupon_print";
    
    
    /**
     * 获得一种优惠券的打印次数
     */
    public int getPerNumofPrintByCoupon(String couponId)
    {
    	int perprintnum = 0;
    	String sql = "select count(strid)  from "+ strTableName3+" where strcouponid='"+couponId+"' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null&&re.next())
			  perprintnum = Integer.parseInt(re.getString(1));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	return perprintnum;
    }
 
    //根据id获取优惠券Name
    public String getCouponNameById(String strid)
    {
    	String name="";
    	Coupon obj = new Coupon(globa);
    	Coupon obj0 = new Coupon();
    	obj0 = obj.show("where strid='"+strid+"'");
    	name = obj0.getStrName();
    	return name;
    }
    //封装结果
    public CouponTop loadCT(ResultSet re)
    {
    	Shop obj = new Shop(globa);
    	CouponTop thebean = new CouponTop();
    	try {
			thebean.setCouponId(re.getString("strid"));
			thebean.setCouponName(re.getString("strname"));
			thebean.setShopname(obj.returnBizShopName(" where strid='"+re.getString("strshopid")+"'"));
			thebean.setPerCouponPrintNum(this.getPerNumofPrintByCoupon(re.getString("strid")));
			thebean.setCouponTime(re.getString("dtactivetime")+"至"+re.getString("dtexpiretime"));
			thebean.setCouponPrice(re.getFloat("flaprice"));
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return thebean;
    }
  //获取优惠券消费排行结果
    public Vector<CouponTop> getCouponTopResultList(String where)
    {
    	Vector<CouponTop> vector = new Vector<CouponTop>();
    	String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null&&re.next())
			{
				do{
					vector.addElement(this.loadCT(re));
				}while (re.next()) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		vector = this.resortCouponTop(vector);
    	return vector;
    }
    //重新排序
    public Vector<CouponTop> resortCouponTop(Vector<CouponTop> vector)
    {
    	for(int i=0;i<vector.size();i++)
    	{
    		for(int j=i+1;j<vector.size();j++)
    		{
    			CouponTop obj = vector.get(i);
    			CouponTop obj1 = vector.get(j);
	    		if(obj.getPerCouponPrintNum() < obj1.getPerCouponPrintNum())
	    		{
	    			CouponTop temp = obj;
	    		     vector.set(i, obj1);  
	    		     vector.set(j, temp);  			
	    		}
    		}
    	}
    	return vector;
    }
    private String couponId;//优惠券id；
    private String couponName;//优惠券名称
    private String couponTime;//优惠券开始结束时间
    private float couponPrice;//优惠券价格
    private int perCouponPrintNum ;//商家每种优惠券打印总量
    private String stime;//统计开始日期
    private String etime;//统计截止日期
	private String shopname;//商家名称
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

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getPerCouponPrintNum() {
		return perCouponPrintNum;
	}

	public void setPerCouponPrintNum(int perCouponPrintNum) {
		this.perCouponPrintNum = perCouponPrintNum;
	}

	public String getStime() {
		return stime;
	}

	public float getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(float couponPrice) {
		this.couponPrice = couponPrice;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getCouponTime() {
		return couponTime;
	}

	public void setCouponTime(String couponTime) {
		this.couponTime = couponTime;
	}
	
}
