package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.swing.internal.plaf.metal.resources.metal;
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
public class MemberBiz {
    private Globa globa;
    private DbConnect db;
    public MemberBiz() {
    }

    public MemberBiz(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public MemberBiz(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName1 = "t_bz_member";
    String strTableName2 = "t_bz_coupon_print";
    String strTableName3 = "t_bz_coupon";   
   
    /**
     * 获得会员打印优惠券id
     */
    
    public Vector<MemberBiz> getCouponsByMember(String flag,String cardno,String stime,String etime)
    {
    	MemberBiz obj = new MemberBiz(globa);
    	Vector<MemberBiz> vector = new Vector<MemberBiz>();
    	if(flag.equals("bycoupon"))
    	{
			String sql = "select distinct(strcouponid) from "+strTableName2;
			if(stime.equals("")||stime.equals(null))
			{
				sql ="select distinct(strcouponid) from "+strTableName2+" where strmembercardno='"+cardno+"' and dtcreatetime between '1000-01-01' and '"+etime+"'";
	    	}
			if(etime.equals("")||etime.equals(null))
			{
				sql ="select distinct(strcouponid) from "+strTableName2+" where strmembercardno='"+cardno+"' and dtcreatetime between  '"+stime+"' and '9999-12-30'";
	    	}
			if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
			{
				sql ="select distinct(strcouponid) from "+strTableName2+" where strmembercardno='"+cardno+"' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
			}
	    	ResultSet re = db.executeQuery(sql);
	    	try {
				if(re!=null)
				{			    
					while(re.next())
					{	
						String couponid = re.getString("strcouponid");
						String name = obj.getCouponNameById(couponid);	
						int num = obj.getPerNumofPrintByCoupon(couponid,cardno);
						vector.addElement(loadByFlag(cardno,name, num));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(flag.equals("byterminal"))
    	{
			String sql = "select distinct(strterminalid) from "+strTableName2;
			if(stime.equals("")||stime.equals(null))
			{
				sql ="select distinct(strterminalid) from "+strTableName2+" where strmembercardno='"+cardno+"' and dtcreatetime between '1000-01-01' and '"+etime+"'";
	    	}
			if(etime.equals("")||etime.equals(null))
			{
				sql ="select distinct(strterminalid) from "+strTableName2+" where strmembercardno='"+cardno+"' and dtcreatetime between  '"+stime+"' and '9999-12-30'";
	    	}
			if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
			{
				sql ="select distinct(strterminalid) from "+strTableName2+" where strmembercardno='"+cardno+"' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
			}
	    	ResultSet re = db.executeQuery(sql);
	    	try {
				if(re!=null)
				{			    
					while(re.next())
					{	
						String strterminalid = re.getString("strterminalid");
						String name = obj.getTerminalNoById(strterminalid);	
						int num = obj.getPerNumofPrintByTerminal(strterminalid,cardno);
						vector.addElement(loadByFlag(cardno,name, num));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		else if(flag.equals("byshop"))
	    {
			String sql = "select distinct a.strshopid from "+strTableName3+" a left join "+strTableName2+" b on a.strid=b.strcouponid";
			if(stime.equals("")||stime.equals(null))
			{
				sql ="select distinct a.strshopid from "+strTableName3+" a left join "+strTableName2+" b on a.strid=b.strcouponid where b.strmembercardno='"+cardno+"' and b.dtcreatetime between '1000-01-01' and '"+etime+"'";
	    	}
			if(etime.equals("")||etime.equals(null))
			{
				sql ="select distinct a.strshopid from "+strTableName3+" a left join "+strTableName2+" b on a.strid=b.strcouponid where b.strmembercardno='"+cardno+"' and b.dtcreatetime between  '"+stime+"' and '9999-12-30'";
	    	}
			if(!(stime.equals("")||stime.equals(null))&&!(etime.equals("")||etime.equals(null)))
			{
				sql ="select distinct a.strshopid from "+strTableName3+" a left join "+strTableName2+" b on a.strid=b.strcouponid where b.strmembercardno='"+cardno+"' and b.dtcreatetime between '"+stime+"' and '"+etime+"'";
			}
	    	ResultSet re = db.executeQuery(sql);
	    	try {
				if(re!=null)
				{			    
					while(re.next())
					{	
						Shop obj0 = new Shop(globa);
						String shopid = re.getString("a.strshopid");
						String name = obj0.returnBizShopName("where strid ='"+shopid+"'");	
						int num = obj.getPerNumofPrintByShop(shopid,cardno);
						vector.addElement(loadByFlag(cardno,name, num));
				
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
     * 获得一个会员打印的各种优惠券的打印次数
     */
    public int getPerNumofPrintByCoupon(String couponId,String membercardno)
    {
    	int perprintnum = 0;
    	String sql = "select count(strid)  from "+ strTableName2+" where strcouponid='"+couponId+"' and strmembercardno ='"+membercardno+"'";
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
     * 获得一个会员打印的每个终端次数
     */
    public int getPerNumofPrintByTerminal(String strterminalid,String membercardno)
    {
    	int perprintnum = 0;
    	String sql = "select count(strid)  from "+ strTableName2+" where strterminalid='"+strterminalid+"' and strmembercardno ='"+membercardno+"'";
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
     * 获得一个会员消费每个商家次数
     */
    public int getPerNumofPrintByShop(String strshopid,String membercardno)
    {
    	int perprintnum = 0;
    	String sql = "select count(b.strid) from "+strTableName2+" a left join "+strTableName3+" b on b.strid=a.strcouponid where a.strmembercardno='"+membercardno+"' and b.strshopid='"+strshopid+"'";
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
    public MemberBiz loadByFlag(String cardno,String terminalno,int num)
      {
      	MemberBiz thebean = new MemberBiz();
      	thebean.setMemberCardNo(cardno);
      	thebean.setFlagName(terminalno);
      	thebean.setPerCouponPrintNum(num);
      	return thebean;
      }
//获取一个会员消费统计的报表信息
    public Vector<MemberBiz> getMemberBizList(String where)
    {
    	Vector<MemberBiz> vector = new Vector<MemberBiz>();
    	MemberBiz obj = new MemberBiz(globa); 
    	String sql = "select * from "+strTableName1;
    	try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet re = s.executeQuery(sql);
		 	if(re!=null&&re.next())
			{
		 		do{
					String cardno = re.getString("strcardno");
					Vector<MemberBiz> vector2 = obj.getCouponsByMember(this.flag,cardno,this.stime,this.etime);
					if(vector2!=null&&vector2.size()!=0)
					{
						for(int i=0;i<vector2.size();i++)
						{
							vector.addElement(vector2.get(i));
						}
					}
					else
					{
						vector.addElement(loadByFlag(cardno,"无优惠券发布记录" , 0));
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
    	MemberBiz obj= new MemberBiz(globa);
        int count = 0;
        String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null)
			{
				while (re.next()) {
					String cardno = re.getString("strcardno");
					Vector<MemberBiz> vector2 = obj.getCouponsByMember(this.flag,cardno,this.stime,this.etime);
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
    //获取一个会员的消费总额
    public int getPerMemberNumOfPrint(String membercarno)
    {
    	int num=0;
    	String sql = "select count(strid) from "+strTableName2+" where strmembercardno='"+membercarno+"'";
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null&&re.next())
				try {
					num = re.getInt("count(strid)");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return num;
    }
    
    //商家排行信息
    public Vector<MemberBiz> getMemberTopResultList(String where)
    {
    	Vector<MemberBiz> vector = new Vector<MemberBiz>();
    	String sql ="select * from "+strTableName1;
        try {
    		if (where.length() > 0)
                 sql = String.valueOf(sql) + String.valueOf(where);
            ResultSet re = db.executeQuery(sql);
		 	if(re!=null&&re.next())
			{
				do{
					vector.addElement(this.loadMT(re));
				}while (re.next()) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		vector = this.resortMemberTop(vector);
    	return vector;
    }
    //重新排序
    public Vector<MemberBiz> resortMemberTop(Vector<MemberBiz> vector)
    {
    	for(int i=0;i<vector.size();i++)
    	{
    		for(int j=i+1;j<vector.size();j++)
    		{
	    		MemberBiz obj = vector.get(i);
	    		MemberBiz obj1 = vector.get(j);
	    		if(obj.getTotalNumofPrint() < obj1.getTotalNumofPrint())
	    		{
	    			MemberBiz temp = obj;
	    		     vector.set(i, obj1);  
	    		     vector.set(j, temp);  			
	    		}
    		}
    	}
    	return vector;
    }
    //封装排行结果
    public MemberBiz loadMT(ResultSet rs)
    {
    	MemberBiz thebean = new MemberBiz();
    	try {
			thebean.setMemberCardNo(rs.getString("strcardno"));
			thebean.setMemberName(rs.getString("strname"));
			if(rs.getInt("inttype")==0)
				thebean.setMemberType("普通会员");
			else
				thebean.setMemberType("VIP会员");
			thebean.setMemberFlaBalance(rs.getFloat("flabalance"));
			thebean.setMemberPoint(rs.getInt("intpoint"));
		    thebean.setTotalNumofPrint(this.getPerMemberNumOfPrint2(rs.getString("strcardno")));
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thebean;
    }
    //获取一个会员一定条件下的消费总额
    public int getPerMemberNumOfPrint2(String membercarno)
    {
    	int num=0;
    	String sql = "select count(strid) from "+strTableName2+" where strmembercardno='"+membercarno+"' and  dtcreatetime between '"+stime+"' and '"+etime+"'";
    	ResultSet re = db.executeQuery(sql);
    	try {
			if(re!=null&&re.next())
				try {
					num = re.getInt("count(strid)");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return num;
    }
    private String flag;//优惠券id；
    private String flagName;//优惠券名称
    private String memberCardNo;//会员卡号
    private int perCouponPrintNum ;//商家每种优惠券打印总量
    private String stime;//统计开始日期
    private String etime;//统计截止日期
    private String memberName;//会员名称
    private String memberType;//会员类型
    private int memberPoint;//会员积分
    private float memberFlaBalance;//会员余额
    private int totalNumofPrint;//总消费次数
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

	public void setMemberCardNo(String memberCardNo) {
		this.memberCardNo = memberCardNo;
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

	public String getMemberCardNo() {
		return memberCardNo;
	}


	public String getFlagName() {
		return flagName;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public int getMemberPoint() {
		return memberPoint;
	}

	public void setMemberPoint(int memberPoint) {
		this.memberPoint = memberPoint;
	}

	public float getMemberFlaBalance() {
		return memberFlaBalance;
	}

	public void setMemberFlaBalance(float memberFlaBalance) {
		this.memberFlaBalance = memberFlaBalance;
	}

	public int getTotalNumofPrint() {
		return totalNumofPrint;
	}

	public void setTotalNumofPrint(int totalNumofPrint) {
		this.totalNumofPrint = totalNumofPrint;
	}
}
