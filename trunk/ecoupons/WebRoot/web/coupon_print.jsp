<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.util.ParamUtil,
				com.ejoysoft.common.exception.IdObjectException,
				com.ejoysoft.ecoupons.business.Coupon,
				com.ejoysoft.auth.MD5,
				com.ejoysoft.common.Format,
				com.ejoysoft.ecoupons.business.CouponPrint,
				com.ejoysoft.ecoupons.business.Member,
				com.ejoysoft.common.Constants"%>
<%@include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <%
	 String memberCardno="";  
	 if(session.getAttribute(Constants.MEMBER_KEY) == null)
	 {
   		globa.closeCon();   		
      response.getWriter().println("<script>alert('您还未登录！请先登录！');window.opener=null;window.close();</script>");
	 }
	 memberCardno = globa.memberSession.getStrCardNo(); 	 
	 String strId = ParamUtil.getString(request,"strid");
	 if(strId.equals(""))
	    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
	 String strimg = ParamUtil.getString(request,"strimg","temp.jpg");
	 String flag = ParamUtil.getString(request,"flag"," ");
	 Coupon coupobj = new Coupon(globa);
	 Coupon obj1 = coupobj.show(" where strid='"+strId+"'");
	 String codeString = memberCardno.trim()+strId.trim()+Format.getDateTime();
	 String md5Code = MD5.getMD5ofString(codeString);
	 String strCouponCode ="",strCouponCode3 ="";
 	 float couponPrice = obj1.getFlaPrice();
 	 if(couponPrice>0)
 	 {
 		 strCouponCode = md5Code.substring(0,4)+md5Code.substring(md5Code.length()-5,md5Code.length()-1);
 	 	 strCouponCode3="验证码："+strCouponCode;
 	 }
     int couponvip= obj1.getIntVip();	    
	 Member member = new Member(globa);
	 Member memberobj = member.show(" where strcardno='"+memberCardno+"'");
     int membervip = memberobj.getIntType();
	 float balance = memberobj.getFlaBalance();
	 if(couponvip==1 && membervip ==0)
	 {
	 	response.getWriter().println("<script>alert('您非VIP会员，无法打印Vip优惠券！');window.opener=null;window.close();</script>");
	 }
	 if(couponPrice > balance)
	 {
	    response.getWriter().println("<script>alert('您的会员卡余额不足！请即使充值');window.opener=null;window.close();</script>");
	 }	 
 	 if(flag.equals("print"))
 	 {	  
 	       int k = member.setFlaBalance(memberCardno,balance-couponPrice);
		   CouponPrint obj = new CouponPrint(globa);
	 	   String strCouponCode2 = ParamUtil.getString(request,"code");
	 	   obj.setStrCreator("system");
	 	   obj.setStrCouponCode(strCouponCode2);
	 	   obj.setStrCouponId(strId);
	 	   obj.setStrMemberCardNo(memberCardno);
	 	   obj.setStrTerminalId("system");
 	  	   boolean result = obj.add();		
 	  	   response.getWriter().println("<script>setTimeout('window.opener=null;window.close();',400);</script>");	
 	 }
 %>
    <base href="<%=basePath%>">
    
    <title><%=obj1.getStrName() %>(优惠券)</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style media=print>
.Noprint{display:none;}
</style>

  </head>
  <body>
  <form name=frm action="web/coupon_print.jsp" method=post id=frm >
  <input type=hidden name=code value="<%=strCouponCode%>" />
  <input type=hidden name=strimg value="<%=strimg%>" />
  <input type=hidden name=strid value="<%=strId%>" />
  <input type=hidden name=flag value=" " />
   <table width=100% border=0> 
   <tr>
	   <td colspan="2">
	    <table>	    
		   <tr>
		   	<td width=90 height=50 align="left"><img src="web/images/xinjiutian.jpg" width=100 height=50 /></td>
		   	<td align="left"><font size="5">&nbsp;&nbsp;联系电话：0558-2282609</font></td>
		   </tr> 
	    </table>
	   </td>
   </tr>
    <tr>
   	<td colspan="2" width=100% ><img src="web/images/show_line.gif" width=400 height=5 /></td>
   </tr>
   <tr>
   	<td colspan="2"> <img src=<%="coupon/images/"+strimg%> width=<%=application.getAttribute("COUPON_PRINT_IMG_WIDTH") %> height=<%=application.getAttribute("COUPON_PRINT_IMG_HEIGHT") %>/></td>
   </tr>
   <tr>
   	<td colspan="2" width=100% ><img src="web/images/show_line.gif" width=400 height=5 /></td>
   </tr>
   <tr>
   	<td height=50 colspan="2">
   	<font size="5">
   	<div id="txt"></div>
   	
   	</font></td>
   </tr>
   <tr>
   	<td>
	   <input class=Noprint style="width=200;height=30" type=button name=button1 onclick="if(confirm('确定打印该优惠吗？')){document.getElementById('txt').innerHTML='<%=strCouponCode3%>';document.getElementById('flag').value='print' ;window.print();document.getElementById('frm').submit();}" value="打     印"/></td>
   	<td><input class=Noprint style="width=200;height=30" type=button name=button2 onclick="window.close();" value="关     闭"/></td>
   </tr>
   </table>
   </form> 
    <%globa.closeCon(); %>
  </body>
</html>