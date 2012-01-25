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
public class TerminalAnalysis {
    private Globa globa;
    private DbConnect db;
    public TerminalAnalysis() {
    }

    public TerminalAnalysis(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public TerminalAnalysis(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName1 = "t_bz_terminal";
    String strTableName2 = "t_bz_coupon";
    String strTableName3 = "t_bz_coupon_print";
    
    /**
     * 获得商家发布优惠券数量 
     */
    public int getNumOfCouponByTerminal(String terminalid,String stime,String etime)
    {
    	int num=0;
    	Coupon obj = new Coupon(globa);
    	String where ="where strterminalid='"+terminalid+"'";
		if(stime.equals("")||stime.equals(null))
		{
			where =" where strterminalids like'%"+terminalid+"%' and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			where =" where strterminalids like'%"+terminalid+"%' and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			where =" where strterminalids like'%"+terminalid+"%' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
	}
    	num = obj.getCount(where);
    	return num;        
    } 
    /**
     * 获得终端发布优惠券id(指定时间内)
     */    
    public Vector<TerminalAnalysis> getCouponIdsByTerminal(String terminalid,String stime,String etime)
    {
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
    	Vector<TerminalAnalysis> vector = new Vector<TerminalAnalysis>();
		String sql = "select * from "+strTableName2;
		if(stime.equals("")||stime.equals(null))
		{
			sql ="select * from "+strTableName2+" where strterminalids like'%"+terminalid+"%' and dtcreatetime between '1000-01-01' and '"+etime+"'";
    	}
		if(etime.equals("")||etime.equals(null))
		{
			sql ="select * from "+strTableName2+" where strterminalids like'%"+terminalid+"%' and dtcreatetime between  '"+stime+"' and '9999-12-30'";
    	}
		if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
		{
			sql ="select * from "+strTableName2+" where strterminalids like'%"+terminalid+"%' and  dtcreatetime between '"+stime+"' and '"+etime+"'";

		//	System.err.println(sql+"4444444444444444444422");
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
    } /**
     * 获得终端发布优惠券id(指定时间以外)
     */    
    public Vector<TerminalAnalysis> getCouponIdsByTerminal2(String terminalid,String stime,String etime)
    {
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
    	Vector<TerminalAnalysis> vector = new Vector<TerminalAnalysis>();
		String sql = "select * from "+strTableName2+" where strterminalids like'%"+terminalid+"%'";
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
    public int getPerNumofPrintByCoupon(String couponId,String terminalId,String stime,String etime)
    {
    	int perprintnum = 0;
    	String sql = "select count(strid)  from "+ strTableName3+" where strterminalid='"+terminalId+"' and strcouponid='"+couponId+"'";
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
			sql +=" and dtcreatetime between '"+stime+"' and '"+etime+"'";
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
    //获取终端优惠券总打印次数
    public int getTotalPrintNum(String terminalid,String stime,String etime)
    {
    	int totalnum=0;
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
    	Vector<TerminalAnalysis> vector = obj.getCouponIdsByTerminal(terminalid,stime,etime);
    	for(int i=0;i<vector.size();i++)
    	{
    		TerminalAnalysis coupon = vector.get(i);
    		String couponid = coupon.getCouponId();
    		totalnum += obj.getPerNumofPrintByCoupon(couponid,terminalid,stime,etime);
    	}
    	Vector<TerminalAnalysis> vector2 = obj.getCouponIdsByTerminal2(terminalid,stime,etime);
    	for(int i=0;i<vector2.size();i++)
    	{
    		TerminalAnalysis coupon = vector2.get(i);
    		String couponid = coupon.getCouponId();
    		totalnum += obj.getPerNumofPrintByCoupon(couponid,terminalid,stime,etime);
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
    public TerminalAnalysis loadCoupon(String couponid, String couponname)
    {
    	TerminalAnalysis thebean = new TerminalAnalysis();
    	thebean.setCouponId(couponid);
    	thebean.setCouponName(couponname);
    	return thebean;
    }
    //封装终端统计分析报表结果
    public TerminalAnalysis loadChart(String terminalno, String couponname,int num)
    {
    	TerminalAnalysis thebean = new TerminalAnalysis();
    	thebean.setTerminalNo(terminalno);
    	thebean.setCouponName(couponname);
    	thebean.setPerCouponPrintNum(num);
    	return thebean;
    }    
    //封装终端统计分析柱形图结果
    public TerminalAnalysis loadBar(String terminalno, int couponnum,int printnum)
    {
    	TerminalAnalysis thebean = new TerminalAnalysis();
    	thebean.setTerminalNo(terminalno);
    	thebean.setTerminalCouponNum(couponnum);    
    	thebean.setTerminalPrintNum(printnum);
    	return thebean;
    }
    //获取一个终端统计分析的柱形图信息
    public Vector<TerminalAnalysis> getTermianlAnalysisResult(String where)
    {
    	Vector<TerminalAnalysis> vector = new Vector<TerminalAnalysis>();
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
    	String sql = "select * from "+strTableName1;
    	try {
    		if (where.length() > 0)
                sql = String.valueOf(sql) + String.valueOf(where);         
    		ResultSet re = db.executeQuery(sql);
    	    if(re!=null)
			{
				while (re.next()) {
					String terminalid = re.getString("strid");
					String terminalno = re.getString("strno");
					int couponnum = obj.getNumOfCouponByTerminal(terminalid,this.stime,this.etime);
					int printnum = obj.getTotalPrintNum(terminalid,this.stime,this.etime);
					vector.addElement(loadBar(terminalno, couponnum, printnum));					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	return vector;
    }
//获取一个终端统计分析的报表信息
    public Vector<TerminalAnalysis> getTerminalAnalysisList(String where)
    {
    	Vector<TerminalAnalysis> vector = new Vector<TerminalAnalysis>();
    	TerminalAnalysis obj = new TerminalAnalysis(globa); 
    	String sql = "select * from "+strTableName1;
    	try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
              ResultSet re = s.executeQuery(sql);
		 	if(re!=null&&re.next())
			{
		 			do{
					String terminalid = re.getString("strid");
					String terminalno = re.getString("strno");
					Vector<TerminalAnalysis> vector2 = obj.getCouponIdsByTerminal(terminalid,this.stime,this.etime);
					Vector<TerminalAnalysis> vector3 = obj.getCouponIdsByTerminal2(terminalid,this.stime,this.etime);
					if((vector2!=null&&vector2.size()!=0) ||(vector3!=null&&vector3.size()!=0))
					{
						if(vector2!=null&&vector2.size()!=0)
						{
							for(int i=0;i<vector2.size();i++)
							{
								TerminalAnalysis coupon  = vector2.get(i);
								String couponname = coupon.getCouponName();
								String couponid = coupon.getCouponId();
								int num = obj.getPerNumofPrintByCoupon(couponid,terminalid,this.stime,this.etime);
								TerminalAnalysis obj0 = loadChart(terminalno, couponname, num);
								vector.addElement(obj0);
							}
						}
						if(vector3!=null&&vector3.size()!=0)
						{
							int  totalnum =0;
							for(int i=0;i<vector3.size();i++)
							{
								TerminalAnalysis coupon  = vector3.get(i);
								String couponname = coupon.getCouponName();
								String couponid = coupon.getCouponId();
								int num = obj.getPerNumofPrintByCoupon(couponid,terminalid,this.stime,this.etime);
								totalnum += num;
								/*if(num!=0)
								{
									TerminalAnalysis obj0 = loadChart(terminalno, "*（"+couponname+"）", num);
									vector.addElement(obj0);							
								}*/
							}
							if(totalnum !=0){
								TerminalAnalysis obj0 = loadChart(terminalno, "无", totalnum);
								vector.addElement(obj0);							 		
							}
						}
					}
					else
					{
						vector.addElement(loadChart(terminalno, "无优惠券发布记录" , 0));
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
    	TerminalAnalysis obj= new TerminalAnalysis(globa);
        int count = 0;
        String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null)
			{
				while (re.next()) {
					String terminalid = re.getString("strid");
					Vector<TerminalAnalysis> vector2 = obj.getCouponIdsByTerminal(terminalid,this.stime,this.etime);
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
    private String couponId;//优惠券id；
    private String couponName;//优惠券名称
    private String terminalNo;//优惠券名称
    private int terminalCouponNum ;//商家发布优惠券总量；
    private int terminalPrintNum ;//商家优惠券打印总量
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

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public int getTerminalCouponNum() {
		return terminalCouponNum;
	}

	public void setTerminalCouponNum(int terminalCouponNum) {
		this.terminalCouponNum = terminalCouponNum;
	}

	public int getTerminalPrintNum() {
		return terminalPrintNum;
	}

	public void setTerminalPrintNum(int terminalPrintNum) {
		this.terminalPrintNum = terminalPrintNum;
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
