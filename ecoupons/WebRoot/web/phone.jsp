<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.util.ParamUtil,
				com.ejoysoft.common.exception.IdObjectException,
				com.ejoysoft.ecoupons.business.Coupon,
				com.ejoysoft.auth.MD5,
				com.ejoysoft.common.Format,
				com.ejoysoft.ecoupons.business.CouponPrint,
				com.ejoysoft.ecoupons.business.Member,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@page import="java.net.UnknownHostException"%>
<%@page import="com.ejoysoft.common.SendSms"%>
<%@include file="../include/jsp/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

 <title>确认接收短信的手机号码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<%
String memberPhone="";
memberPhone = 	globa.memberSession.getStrMobileNo(); 

%>

<body>
   <form action="print_act.jsp" name="f1" method="postt">

   		<table align="center">
   		<tr align="center">
   			<td style="font-size: 18px; font-weight: bold;font-family: 宋体;">确认接收短信的手机号码</td>
   		</tr>
   		<tr><td></td></tr>
   		<tr><td></td></tr>
   		<tr><td></td></tr>
   		<tr style="font-size: 14px;">
   		<td>发送至手机：&nbsp;&nbsp;<input type="text" name="phone" id="phone" value="<%=memberPhone %>"/></td>
   		</tr>
   		<tr><td></td></tr>
   		<tr align="center">
   		<td>
   		<input type="submit" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   		<input type="button" value="关闭" onclick="window.close();"/>
   		<!--  <input type="button" value="重置" onclick="document.getElementById('phone').value='';" /> -->
   		</td>
   		</tr>
   </table>
   </form>
  </body>
</html>