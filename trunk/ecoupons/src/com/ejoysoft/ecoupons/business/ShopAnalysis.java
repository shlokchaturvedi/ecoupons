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
public class ShopAnalysis {
    private Globa globa;
    private DbConnect db;
    public ShopAnalysis() {
    }

    public ShopAnalysis(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public ShopAnalysis(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName1 = "t_bz_shop";
    String strTableName2 = "t_bz_coupon";
    String strTableName3 = "t_bz_coupon_print";
    String strTableName4 = "t_bz_point_buy";
    String strTableName5 = "t_bz_point_present";
    
    /**
     * 获得商家发布优惠券数量 
     */
    public int getNumOfCouponByShop(String shopid,String stime,String etime)
    {
    	int num=0;
    	Coupon obj = new Coupon(globa);
    	String where =" where strshopid='"+shopid+"'";
		if(stime.equals("")||stime.equals(null))
		{
			where =" where strshopid='"+shopid+"' and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			where =" where strshopid='"+shopid+"' and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			where =" where strshopid='"+shopid+"' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
		}
    	num = obj.getCount(where);
    	return num;        
    } 
    /**
     * 获得商家发布优惠券id(指定时间内)
     */
    
    public Vector<ShopAnalysis> getCouponIdsByShop(String shopid,String stime,String etime)
    {
    	@SuppressWarnings("unused")
		ShopAnalysis obj = new ShopAnalysis(globa);
    	Vector<ShopAnalysis> vector = new Vector<ShopAnalysis>();
		String sql = "select * from "+strTableName2+" where strshopid='"+shopid+"' ";
		if(stime.equals("")||stime.equals(null))
		{
			sql +=" and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql +=" and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql +=" and  dtcreatetime between '"+stime+"' and '"+etime+"'";
		}
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null)
			{			    
				while(re.next())
				{	
					String id = re.getString("strid");
					String name = re.getString("strname");	
					vector.addElement(loadCoupon(id, name));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return vector;        
    } 
    /**
     * 获得商家发布优惠券id(指定时间以外)
     */    
    public Vector<ShopAnalysis> getCouponIdsByShop2(String shopid,String stime,String etime)
    {
    	@SuppressWarnings("unused")
		ShopAnalysis obj = new ShopAnalysis(globa);
    	Vector<ShopAnalysis> vector = new Vector<ShopAnalysis>();
		String sql = "select * from "+strTableName2+" where strshopid='"+shopid+"'";
		if(stime.equals("")||stime.equals(null))
		{
			sql += " and dtcreatetime between '"+etime+"' and '9999-12-30' ";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql +=" and dtcreatetime between '1000-01-01' and '"+stime+"'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql +=" and  (dtcreatetime between '"+etime+"' and '9999-12-30' or dtcreatetime between '1000-01-01' and '"+stime+"')";
		}
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null)
			{			    
				while(re.next())
				{	
					String id = re.getString("strid");
					String name = re.getString("strname");	
					vector.addElement(loadCoupon(id, name));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return vector;        
    }
    /**
     * 获得一种优惠券的打印次数
     */
    public int getPerNumofPrintByCoupon(String couponId,String stime,String etime)
    {
    	int perprintnum = 0;
    	String sql = "select count(strid)  from "+ strTableName3+" where strcouponid='"+couponId+"'";
    	if(stime.equals("")||stime.equals(null))
		{
			sql +=" and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql +=" and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql +=" and  dtcreatetime between '"+stime+"' and '"+etime+"'";
		}
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
    //获取商家优惠券总打印次数
    public int getTotalPrintNum(String shopid,String stime,String etime)
    {
    	int totalnum=0;
    	ShopAnalysis obj = new ShopAnalysis(globa);
    	/*Vector<ShopAnalysis> vector = obj.getCouponIdsByShop(shopid,stime,etime);
    	for(int i=0;i<vector.size();i++)
    	{
    		ShopAnalysis coupon = vector.get(i);
    		String couponid = coupon.getCouponId();
    		totalnum += obj.getPerNumofPrintByCoupon(couponid,stime,etime);
    	}
    	Vector<ShopAnalysis> vector2 = obj.getCouponIdsByShop2(shopid,stime,etime);
    	for(int i=0;i<vector2.size();i++)
    	{
    		ShopAnalysis coupon = vector2.get(i);
    		String couponid = coupon.getCouponId();
    		totalnum += obj.getPerNumofPrintByCoupon(couponid,stime,etime);
    	}*/
    	String  sql = "select count(strid)as printnum from t_bz_coupon_print where dtPrintTime>='" + stime + "' and dtPrintTime<='" + etime + "' "+
    			      " and strshop like '%" + shopid + "%'" ;
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null && re.next())
			{
				return re.getInt("printnum");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return totalnum;
    	
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
    //封装商家统计分析柱形图结果
    public ShopAnalysis loadCoupon(String couponid, String couponname)
    {
    	ShopAnalysis thebean = new ShopAnalysis();
    	thebean.setCouponId(couponid);
    	thebean.setCouponName(couponname);
    	return thebean;
    }
    //封装商家统计分析报表结果
    public ShopAnalysis loadChart(String shopname, String couponname,int num,int i)
    {
    	ShopAnalysis thebean = new ShopAnalysis();
    	thebean.setShopName(shopname);
    	thebean.setCouponName(couponname);
    	thebean.setPerCouponPrintNum(num);
    	thebean.setIntSort(i);
    	return thebean;
    }    
    //封装商家统计分析柱形图结果
    public ShopAnalysis loadBar(String shopname, int couponnum,int printnum)
    {
    	ShopAnalysis thebean = new ShopAnalysis();
    	thebean.setShopName(shopname);
    	thebean.setShopCouponNum(couponnum);    
    	thebean.setShopPrintNum(printnum);
    	return thebean;
    }
    //获取一个商家统计分析的柱形图信息
    public Vector<ShopAnalysis> getShopAnalysisResult(String where)
    {
    	Vector<ShopAnalysis> vector = new Vector<ShopAnalysis>();
    	ShopAnalysis obj = new ShopAnalysis(globa);
    	String sql = "select * from "+strTableName1;
     	try {
    		if (where.length() > 0)
                sql = String.valueOf(sql) + String.valueOf(where);
    		ResultSet re = db.executeQuery(sql);
    		   if(re!=null)
			{
				while (re.next()) {
					String shopid = re.getString("strid");
					String bizname = re.getString("strbizname");
				    String shopname2 = re.getString("strshopname");
				    if(shopname2 !=null && !shopname2.equals(""))  
				    {
				   		bizname = bizname+"-"+shopname2;
				    }
					String shopname = bizname;
					int couponnum = obj.getNumOfCouponByShop(shopid,this.stime,this.etime);
					int printnum = obj.getTotalPrintNum(shopid,this.stime,this.etime);
					vector.addElement(loadBar(shopname, couponnum, printnum));					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	return vector;
    }
//获取一个商家统计分析的报表信息
    public Vector<ShopAnalysis> getShopAnalysisList(String where,int intStartNum,int intPageSize ,int intEndNum,int intAllCount)
    {
    	Vector<ShopAnalysis> vector = new Vector<ShopAnalysis>();
    	ShopAnalysis obj = new ShopAnalysis(globa); 
    	Shop objShop = new Shop(globa);
    	String sql = "select strcouponid,strshop,count(strcouponid) as printnum from t_bz_coupon_print   "+
		"where dtPrintTime>='"+stime+"' and dtPrintTime<='"+etime+"'";
    	try {
    		if (where.length() > 0)
			{
    			sql += " and strshop like'%"+where+"%'";
			}		
			sql += " group by strshop,strcouponid order by strshop";
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery(sql);
            Coupon objCoupon = new Coupon(globa);  
           	rs.absolute(intStartNum);
               if(rs!=null && rs.next())
               {	
               		int i=intStartNum - 1;					
				    if (intStartNum != 0 && intPageSize != 0)
						rs.absolute(intStartNum);
					do{
						String shopFullName = rs.getString("strshop");
						int printnum = rs.getInt("printnum");
						String couponid = rs.getString("strcouponid");
						String shopname = "";
						if(shopFullName!=null)
						{
							if( shopFullName.contains("/"))
							{
								String shops[] = shopFullName.split("/");
								if(shops.length==2)
								{
									shopname = shops[1];
								}
								else
								{
									shopname = " ";
								}
							}
							else
								shopname = "已删除";
						}
						String couponname="已删除";
						if(couponid!=null)
						{
							if( couponid.contains("/"))
							{
								String coupons[] = couponid.split("/");
								if(coupons.length==2)
								{
									couponname = coupons[1];
								}
								else
								{
									couponname = "已删除";
								}
							}
							else
								couponname = "已删除";
						}
               	 		i++;
               	 		vector.addElement(obj.loadChart(shopname, couponname, printnum, i));
               	 	}while(rs.next() && i<intEndNum && i<intAllCount);
               }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	return vector;
    }
    //查询记录数
    public int getCountSA(String where) {
    	ShopAnalysis obj= new ShopAnalysis(globa);
        int count = 0,count2=0;
        String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null)
			{
				while (re.next()) {
					String shopid = re.getString("strid");
					Vector<ShopAnalysis> vector2 = obj.getCouponIdsByShop(shopid,this.stime,this.etime);
					count += vector2.size();
					Vector<ShopAnalysis> vector3 = obj.getCouponIdsByShop2(shopid,this.stime,this.etime);
					for(int i=0;i<vector3.size();i++)
					{
						ShopAnalysis coupon  = vector3.get(i);
						String couponid = coupon.getCouponId();
						int num = obj.getPerNumofPrintByCoupon(couponid,this.stime,this.etime);
						if(num!=0)
							count +=1;
					}										
					if(count == count2)
						count+=1;
					count2=count;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
        return count;
    }
    //查询记录数
    public int getCount(String where) {
    	int count=0;
    	String sql = "select count(*) as allcount from(select strcouponid,strshop,count(strcouponid) as printnum from t_bz_coupon_print   "+
		"where dtPrintTime>='"+stime+"' and dtPrintTime<='"+etime+"'";
    	try {
    		if (where.length() > 0)
			{
    			sql += " and strshop like'%"+where+"%'";
			}		
			sql += " group by strshop,strcouponid order by strshop) as satable";
            ResultSet rs = db.executeQuery(sql);
            if(rs!=null && rs.next())
            	return rs.getInt("allcount");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
        return count;
    }
    private String couponId;//优惠券id；
    private String couponName;//优惠券名称
    private String shopName;//商家名称
    private int shopCouponNum ;//商家发布优惠券总量；
    private int shopPrintNum ;//商家优惠券打印总量
    private int perCouponPrintNum ;//商家每种优惠券打印总量
    private String stime;//统计开始日期
    private String etime;//统计截止日期
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

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public int getIntSort() {
		return intSort;
	}

	public void setIntSort(int intSort) {
		this.intSort = intSort;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getShopCouponNum() {
		return shopCouponNum;
	}

	public void setShopCouponNum(int shopCouponNum) {
		this.shopCouponNum = shopCouponNum;
	}

	public int getShopPrintNum() {
		return shopPrintNum;
	}

	public void setShopPrintNum(int shopPrintNum) {
		this.shopPrintNum = shopPrintNum;
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

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

   
}
