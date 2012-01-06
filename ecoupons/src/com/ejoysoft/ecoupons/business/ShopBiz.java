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
public class ShopBiz {
    private Globa globa;
    private DbConnect db;
    public ShopBiz() {
    }

    public ShopBiz(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public ShopBiz(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName1 = "t_bz_shop";
    String strTableName2 = "t_bz_coupon_print";
    String strTableName3 = "t_bz_coupon";   
   
    /**
     * 获得会员打印优惠券id
     */
    
    public Vector<ShopBiz> getCouponsByMember(String flag,String shopid,String stime,String etime)
    {
    	ShopBiz obj = new ShopBiz(globa);
    	Vector<ShopBiz> vector = new Vector<ShopBiz>();
    	if(flag.equals("bymember"))
    	{
			String sql = "select distinct(a.strmembercardno) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"'";
			if(stime.equals("")||stime.equals(null))
			{
				sql ="select distinct(a.strmembercardno) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and a.dtcreatetime between '1000-01-01' and '"+etime+"'";
	    	}
			if(etime.equals("")||etime.equals(null))
			{
				sql ="select distinct(a.strmembercardno) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and a.dtcreatetime between  '"+stime+"' and '9999-12-30'";
	    	}
			if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
			{
				sql ="select distinct(a.strmembercardno) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and  a.dtcreatetime between '"+stime+"' and '"+etime+"'";
			}
			System.err.println(sql);
	    	ResultSet re = db.executeQuery(sql);
	    	try {
				if(re!=null)
				{			    
					while(re.next())
					{	
						String name = re.getString("strmembercardno");	
						int num = obj.getPerNumofPrintByMember(shopid,name);
						vector.addElement(loadByFlag(shopid,name,num));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(flag.equals("byterminal"))
    	{
    		String sql = "select distinct(a.strterminalid) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"'";
			if(stime.equals("")||stime.equals(null))
			{
				sql ="select distinct(a.strterminalid) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and a.dtcreatetime between '1000-01-01' and '"+etime+"'";
	    	}
			if(etime.equals("")||etime.equals(null))
			{
				sql ="select distinct(a.strterminalid) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and a.dtcreatetime between  '"+stime+"' and '9999-12-30'";
	    	}
			if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
			{
				sql ="select distinct(a.strterminalid) from "+strTableName2+" a left join "+strTableName3+" b on a.strcouponid=b.strid where b.strshopid='"+shopid+"' and  a.dtcreatetime between '"+stime+"' and '"+etime+"'";
			}
	    	ResultSet re = db.executeQuery(sql);
	    	try {
				if(re!=null)
				{			    
					while(re.next())
					{	
						String terminalid = re.getString("strterminalid");	
						String name = obj.getTerminalNoById(terminalid);
						int num = obj.getPerNumofPrintByTerminal(shopid,terminalid);
						vector.addElement(loadByFlag(shopid,name, num));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }		
    	return vector;        
    }
  
    /**
     * 获得一个会员打印的每个终端次数
     */
    public int getPerNumofPrintByTerminal(String shopid,String terminalid)
    {
    	int perprintnum = 0;
    	String sql =  "select count(b.strid) from "+strTableName2+" a left join "+strTableName3+" b on b.strid=a.strcouponid where a.strterminalid='"+terminalid+"' and b.strshopid='"+shopid+"'";
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
    /**
     * 获得一个商家每个会员消费次数
     */
    public int getPerNumofPrintByMember(String strshopid,String membercardno)
    {
    	int perprintnum = 0;
    	String sql = "select count(b.strid) from "+strTableName2+" a left join "+strTableName3+" b on b.strid=a.strcouponid where a.strmembercardno='"+membercardno+"' and b.strshopid='"+strshopid+"'";
    	System.err.println(sql);
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

    //根据id获取终端No
    public String getTerminalNoById(String strid)
    {
    	String name="";
    	Terminal obj = new Terminal(globa);
    	Terminal obj0 = new Terminal();
    	obj0 = obj.show("where strid='"+strid+"'");
    	name = obj0.getStrNo();
    	return name;
    }

    //封装基于会员消费统计报表结果
    public ShopBiz loadByFlag(String shopid,String terminalno,int num)
    {
    	Shop obj = new Shop(globa);
    	ShopBiz thebean = new ShopBiz();
    	thebean.setShopname(obj.returnBizShopName("where strid='"+shopid+"'"));
    	thebean.setFlagName(terminalno);
    	thebean.setPerCouponPrintNum(num);
    	return thebean;
    }
//获取一个商家消费统计的报表信息
    public Vector<ShopBiz> getShopBizList(String where)
    {
    	Vector<ShopBiz> vector = new Vector<ShopBiz>();
    	ShopBiz obj = new ShopBiz(globa); 
    	String sql = "select * from "+strTableName1;
    	try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
              ResultSet re = s.executeQuery(sql);
		 	if(re!=null&&re.next())
			{
		 		do{
					String shopid = re.getString("strid");
					Vector<ShopBiz> vector2 = obj.getCouponsByMember(this.flag,shopid,this.stime,this.etime);
					if(vector2!=null&&vector2.size()!=0)
					{
						for(int i=0;i<vector2.size();i++)
						{
							vector.addElement(vector2.get(i));
						}
					}
					else
					{
						vector.addElement(loadByFlag(shopid,"无优惠券发布记录" , 0));
					}
									
				}while (re.next()) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        	
    	return vector;
    }
    //查询记录数
    public int getCountSA(String where) {
    	ShopBiz obj= new ShopBiz(globa);
        int count = 0;
        String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null)
			{
				while (re.next()) {
					String shopid = re.getString("strid");
					Vector<ShopBiz> vector2 = obj.getCouponsByMember(this.flag,shopid,this.stime,this.etime);
					count += vector2.size();
					if(vector2.size()==0)
						count+=1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
        return count;
    }
    private String flag;//优惠券id；
    private String flagName;//优惠券名称
    private String shopname;//会员卡号
    private int perCouponPrintNum ;//商家每种优惠券打印总量
    private String stime;//统计开始日期
    private String etime;//统计截止日期
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

	public String getFlagName() {
		return flagName;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
