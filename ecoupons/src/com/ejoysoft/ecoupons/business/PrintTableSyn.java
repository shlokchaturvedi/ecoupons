package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.ejoysoft.common.*;

public class PrintTableSyn {
    private Globa globa;
    private DbConnect db;

    public PrintTableSyn() {
    }

    public PrintTableSyn(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public PrintTableSyn(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_bz_coupon_print";
    String strTableName2 = "t_bz_coupon";
    String strTableName3 = "t_bz_member";
    String strTableName4 = "t_bz_shop";

    /**
     * 同步strCouponId字段和优惠券所属商家strShop字段
     * @return
     */
    public boolean synCouponId() {
        String sql2 ="update "+strTableName+" c1 set c1.strcouponid= CONCAT(c1.strcouponid,'/',(select c2.strname from "+strTableName2+" c2 where c2.strid=c1.strcouponid)) where strcouponid not like'%/%'";
        try {
			if(db.executeUpdate(sql2)>0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    public boolean synShop() {
    	        String sql2 ="update "+strTableName+" c1 set c1.strshop= CONCAT((select c3.strshopid from "+strTableName2
    	        +" c3 where strid=SUBSTRING_INDEX(c1.strcouponid,'/',1)),'/',(select  c2.strBizName from "+strTableName4
    	        +" c2 where c2.strid=(select c3.strshopid from "+strTableName2+" c3 where strid=SUBSTRING_INDEX(c1.strcouponid,'/',1)) ))";
    	         try {
    				if(db.executeUpdate(sql2)>0)
    					return true;
    				else
    					return false;
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        return false;
    	    }
    /**
     * 同步会员所属代理员字段 strSalesman
     * @return
     */
    public boolean synSalesman() {
    	  String sql1 ="update "+strTableName+"  set strsalesman='李晓光' where strsalesman is null or strsalesman=''";
    	  String sql2 ="update "+strTableName+"  set strsalesman='张飞' where strmembercardno >= '80000001' and  strmembercardno <= '80000100'";
    	  String sql3 ="update "+strTableName+"  set strsalesman='管理员' where strmembercardno >= '89952535' and  strmembercardno <= '89953622'";
    	  String sql4 ="update "+strTableName+"  set strsalesman='测试员' where strmembercardno ='20110066' or strmembercardno = '82011006'";
    	  String sql5 ="update "+strTableName+"  set strsalesman='???' where strmembercardno ='80023960' or strmembercardno = '80033957' or strmembercardno = '89998795'";
          try {
  			db.executeUpdate(sql1);
			db.executeUpdate(sql2);
			db.executeUpdate(sql3);
			db.executeUpdate(sql4);
			db.executeUpdate(sql5);
			db.closeCon();
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    private String strCoupon;
    private String strShop;
    private String strMemberCardNo;

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

	public String getStrCoupon() {
		return strCoupon;
	}

	public void setStrCoupon(String strCoupon) {
		this.strCoupon = strCoupon;
	}

	public String getStrShop() {
		return strShop;
	}

	public void setStrShop(String strShop) {
		this.strShop = strShop;
	}

	public String getStrMemberCardNo() {
		return strMemberCardNo;
	}

	public void setStrMemberCardNo(String strMemberCardNo) {
		this.strMemberCardNo = strMemberCardNo;
	}
    
}