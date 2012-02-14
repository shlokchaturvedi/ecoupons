<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.ShopAnalysis,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    ShopAnalysis obj=new ShopAnalysis(globa,false);
    String strUrl="shop_analyse.jsp";
   	String stime="1000-01-01";
   	String etime="9999-12-30";
   	
    String  bytime=ParamUtil.getString(request,"byTime");
    if(bytime.equals("month"))
    {
    	String month= ParamUtil.getString(request,"month","");
    	if(!month.trim().equals(""))
    	{
    	   stime = month+"-01";
    	   etime = month+"-30";
    	}    	
    }
    else if(bytime.equals("season"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	String season= ParamUtil.getString(request,"season","");
    	if(!year.trim().equals("") && !season.trim().equals(""))
    	{
    	   if(season.trim().equals("1"))
    	   {
    	   		stime=year+"-01-01";
    	   		etime=year+"-04-01";
    	   }
    	   else if(season.trim().equals("2"))
    	   {
    	   		stime=year+"-04-01";
    	   		etime=year+"-07-01";
    	   }
    	   else if(season.trim().equals("3"))
    	   {
    	   		stime=year+"-07-01";
    	   		etime=year+"-09-01";
    	   }
    	   else 
    	   {
    	   		stime=year+"-09-01";
    	   		etime=year+"-12-31";
    	   }
    	}    	
    } else if(bytime.equals("halfyear"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	String season= ParamUtil.getString(request,"season","");
    	if(!year.trim().equals("") && !season.trim().equals(""))
    	{
    	   if(season.trim().equals("1"))
    	   {
    	   		stime=year+"-01-01";
    	   		etime=year+"-07-01";
    	   }
    	   else if(season.trim().equals("2"))
    	   {
    	   		stime=year+"-07-01";
    	   		etime=year+"-12-31";
    	   }
    	}    	
    }
    else if(bytime.equals("year"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	if(!year.trim().equals(""))
    	{
    	   		stime=year+"-01-01";
    	   		etime=year+"-12-31";
    	}
    }
    else if(bytime.equals("period"))
    {
    	String smonth2[],emonth2[];
    	String stime1= ParamUtil.getString(request,"stime","");
    	String etime1= ParamUtil.getString(request,"etime","");
    	if(!stime.trim().equals(""))
    	{
    	   stime = stime1+"-01";
    	}    	
    	if(!etime.trim().equals(""))
    	{    	  
    	   String etime2[]=etime.split("-");
    	   String emonth = etime2[1];
    	   emonth = String.valueOf(Integer.parseInt(emonth)+1);
    	   etime = etime2[0]+"-"+emonth+"-01";
    	}
    	if(!stime.trim().equals("")&&!etime.trim().equals(""))
    	{
    	   smonth2 = stime.split("-");
    	   emonth2 = etime.split("-");
    	   int sy = Integer.parseInt(smonth2[0]);
    	   int sm = Integer.parseInt(smonth2[1]);
    	   int ey = Integer.parseInt(emonth2[0]);
    	   int em = Integer.parseInt(emonth2[1]);
    	   if((sy == ey && sm >= em)||(sy<ey))
    	   	   out.print("<script type='text/javascript'>alert('输入时间先后有误，请重新输入');history.go(-1);</script>");   	
    	
    	}  	
    	
    }
    obj.setStime(stime);
    obj.setEtime(etime);
	           globa.dispatch(true,strUrl);         
	 
    //关闭数据库连接对象
    globa.closeCon();
%>