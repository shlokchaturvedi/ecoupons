<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="java.util.Vector,com.ejoysoft.common.Constants,com.ejoysoft.ecoupons.business.CouponPrint,
	com.ejoysoft.common.exception.NoRightException,com.ejoysoft.ecoupons.business.ShopAnalysis"%>
<%@page import="com.ejoysoft.ecoupons.business.MemberAnalysis"%>
<%@ page import="java.util.*,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.Constants,java.util.*" %>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看当天新增会员卡号</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <%
  	String stime= ParamUtil.getString(request, "stime", "");
  //	System.out.println(stime);
  	String etime= ParamUtil.getString(request, "etime", "");
//	System.out.println(etime);
	String day=stime.replaceAll("00:00:00","");
	MemberAnalysis memberAnalysis=new MemberAnalysis(globa);
	List<MemberAnalysis> memberAnalysiss=memberAnalysis.getCardNo(stime,etime);
	//System.out.println("couponPrints.size():"+couponPrints.size());

	//for(int i=0;i<couponPrints.size();i++){
	//	System.out.println(couponPrints.get(i).getStrMemberCardNo());
	//}
	
  %>
  
  <body>
    
   		<h4>查看<%=day %>新增会员的卡号：</h4>
   		<div style="overflow: auto; height: 300px;width:1170px;">
   		<%
		for(int i=0;i<memberAnalysiss.size();i++){
			String CardNo=memberAnalysiss.get(i).getStrCardNo();
			if(CardNo==""||CardNo==null){
				CardNo="无 卡 会 员";
			}
		%>
			
			
			<%=CardNo %>;&nbsp;&nbsp;&nbsp;&nbsp;
		<%				
			if(i!=0&&(i+1)%4==0){
				
				%>
				<br/>
				<%
			}
		}
   		%>
   		
   	
  </body>
</html>
