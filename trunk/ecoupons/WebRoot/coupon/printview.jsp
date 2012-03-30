<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.common.ApacheUpload,
				com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@page import="java.net.UnknownHostException"%>
<%@page import="com.ejoysoft.common.SendSms"%>
<%@include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
request.setCharacterEncoding("utf-8"); 
String couponName = ParamUtil.getString(request,"couponName",""); 
String couponCode = ParamUtil.getString(request,"couponCode",""); 
String strimg = ParamUtil.getString(request,"strImg","").replace("\\","/");
String info = ParamUtil.getString(request,"strIntro","");
String instruction = ParamUtil.getString(request,"strInstruction","").replace("\n","<br>");
System.out.println(instruction);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <base href="<%=basePath%>">
    
    <title><%=couponName%>(优惠券)</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style media=print>
.Noprint{display:none;}
</style>
<script src="../include/js/chkFrm.js"></script>
 </head>
  <body>
  <form action="web/coupon_print.jsp" method=post id=frm >
  <input type=hidden name=code value="<%=couponCode%>" />
   <table align="center" width=100% border=0> 
 	<tr>
 		<td>
 			<table align="center" width=100%  height="520" >
 			   <tr>
				   <td align="center">
				    <table width=100%>	    
					   <tr align="center">
					   	<td><img src="<%=strimg%>" width=75 height=69 /></td>
					   </tr> 
				    </table>
				   </td>
			   </tr>
			   <tr>
			   	<td>
			   	  <table width=100%>	    
				   <tr align="center">
				   	<td><img src="../web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
			   	</td>
			   </tr>  
			   <tr>
			   	 <td>
			   	 <table width=100%>	    
				   <tr align="center">
				   	<td><h2><%=info%></h2></td>
				   	</tr>
				  </table></td>
			   </tr>
			   <tr>
			   	<td>
				<table width=100%>	    
				   <tr align="center">
				   	<td><img src="../web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
				</td>
			   </tr>  
			   <tr>
			  	<td height=50 align="center" >
			   		<table align="center">	    
					   <tr>
					   		<td align="center"><h5>【使用说明】</h5></td>
					   </tr> 
					  <tr align="left">
					   		<td><font size="2"><%=instruction.replace("\n","<br/>")%></font></td>
					   </tr> 
				    </table>
			    </td>
			   </tr>
			   <tr>
			   	<td  align="center">
				<table align="center">	    
				   <tr>
				   	<td align="center"><img src="../web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
				</td>
			   </tr>
			   <tr>
			   	<td height=50 align="center" >
				<table align="center">	    
					   <tr>
					   	<td align="center"><div id="txt"><h3><%=couponCode %></h3></div> </td>
				   	</tr>
				  </table>			    	  	
			   	</td>
			   </tr>
			    <tr>
			    <td align="center" >
				   <table align="center">	    
				   <tr>
					   	<td align=center><img src="../web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
				</td>
			   </tr>
			   <tr>
				   <td  align="center" >
					   <table align="center">	    
					   <tr>
					   	<td align="center"> <h3><%=TerminalParamVector.getPrintBottom()%><br/><%=TerminalParamVector.getPhone()%></h3></td>
					   	</tr>
					   </table>
				  </td>
			  </tr> 
			    <tr>
				   <td>
				    <table width=100%>	    
					   <tr align="center">
					   <td><input class=Noprint style="width=115;height=27; background-color:#FFFFFF; border:1px solid #999999;" type=button name=button2 onclick="window.close();" value="关     闭"/></td>
  		   	        </tr> 
				    </table>
				   </td>
			   </tr>
 			</table>
 		</td>
 	</tr> 	  
   <tr>
   
   </table>
   </form> 
  </body>
</html>