package com.ejoysoft.ecoupons.business;

import java.util.Vector;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class ShopTop {
	 private Globa globa;
	    private DbConnect db;
	    public ShopTop() {
	    }

	    public ShopTop(Globa globa) {
	        this.globa = globa;
	        db = globa.db;
	    }

	    public ShopTop(Globa globa, boolean b) {
	        this.globa = globa;
	        db = globa.db;
	        if (b) globa.setDynamicProperty(this);
	    }
	    public Vector<ShopTop> getShopTopResultList(String where)
	    {
	    	ShopTop obj0 = new ShopTop(globa);
	    	Vector<ShopTop> vector = new Vector<ShopTop>();
	    	ShopAnalysis obj = new ShopAnalysis(globa);
	    	Vector<ShopAnalysis> vector2 = new Vector<ShopAnalysis>();
	    	obj.setStime(this.stime);
	    	obj.setEtime(this.etime);
	    	vector2 = obj.getShopAnalysisResult(where);
	    	vector2 = obj0.resortVector(vector2);
	    	vector = obj0.load(vector2);
	    	return vector;
	    }
	    
	    public Vector<ShopTop> load(Vector<ShopAnalysis> vector2)
	    {
	    	Shop obj0 = new Shop(globa);
	    	Vector<ShopTop> vector = new Vector<ShopTop>();
	    	for(int i=0;i < vector2.size();i++)
	    	{
	    		ShopTop thebean = new ShopTop();
	    		ShopAnalysis shopAnalysis = vector2.get(i);
	    		String shopfullname = shopAnalysis.getShopName();
	    		int printnum = shopAnalysis.getShopCouponNum();
	    		thebean.setShopfullname(shopfullname);
	    		thebean.setTotalprintnum(printnum);	    		
	    		String shopid = obj0.getShopIdByName(shopfullname) ;
	    		String where = " where strid='"+shopid+"'";
	    		Shop obj1 = obj0.show(where);
	    		thebean.setIntpoint(obj1.getIntPoint());
	    		thebean.setShopaddr(obj1.getStrAddr());
	    		thebean.setShopinfo(obj1.getStrIntro());
	    		thebean.setShopperson(obj1.getStrPerson());
	    		thebean.setShopphone(obj1.getStrPhone());
	    		thebean.setShoptop(i+1);
	    		thebean.setShoptrade(obj1.getStrTradeName());
	    		
	    		vector.add(thebean);
	    	}
	    	
	    	
	    	return vector;
	    }
	    public Vector<ShopAnalysis> resortVector(Vector<ShopAnalysis> vector)
		{
	    	for(int i=0;i<vector.size();i++)
	    	{
	    		for(int j =i+1;j<vector.size();j++)
	    		{
		    		ShopAnalysis obj = vector.get(i);
		    		ShopAnalysis obj1 = vector.get(j);
		    		if(obj.getShopPrintNum() < obj1.getShopPrintNum())
		    		{
		    			 ShopAnalysis temp = obj;
		    		     vector.set(j, obj1);  
		    		     vector.set(i, temp);  			 
		    		}
	    		}
	    	}
	    	return vector;
	    }
	    private String shopfullname;//商家名称
	    private int shoptop;//消费排行
	    private int totalprintnum;//消费次数
	    private int intpoint;//商家积分
	    private String shopaddr;//商家地址
	    private String shopphone;//商家电话
	    private String shopperson;//商家联系人
	    private String shopinfo;//商家简介
	    private String stime;//统计开始日期
	    private String etime;//统计截止日期
	    private String shoptrade;//行业
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

		public String getShopfullname() {
			return shopfullname;
		}

		public String getShoptrade() {
			return shoptrade;
		}

		public void setShoptrade(String shoptrade) {
			this.shoptrade = shoptrade;
		}

		public void setShopfullname(String shopfullname) {
			this.shopfullname = shopfullname;
		}

		

		public int getShoptop() {
			return shoptop;
		}

		public void setShoptop(int shoptop) {
			this.shoptop = shoptop;
		}

		public int getTotalprintnum() {
			return totalprintnum;
		}

		public void setTotalprintnum(int totalprintnum) {
			this.totalprintnum = totalprintnum;
		}

		public int getIntpoint() {
			return intpoint;
		}

		public void setIntpoint(int intpoint) {
			this.intpoint = intpoint;
		}

		public String getShopaddr() {
			return shopaddr;
		}

		public void setShopaddr(String shopaddr) {
			this.shopaddr = shopaddr;
		}

		public String getShopphone() {
			return shopphone;
		}

		public void setShopphone(String shopphone) {
			this.shopphone = shopphone;
		}

		public String getShopperson() {
			return shopperson;
		}

		public void setShopperson(String shopperson) {
			this.shopperson = shopperson;
		}

		public String getShopinfo() {
			return shopinfo;
		}

		public void setShopinfo(String shopinfo) {
			this.shopinfo = shopinfo;
		}
	    
	    
}
