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
	 String flag = ParamUtil.getString(request,"flag"," "); 
	 Coupon coupobj = new Coupon(globa);
	 Coupon obj1 = coupobj.show(" where strid='"+strId+"'");
	 Shop shop = new Shop(globa);
	 Shop objshop = shop.show(" where strid = '"+obj1.getStrShopId()+"'");
	 String strimg = "images/temp.jpg",info = " ",instruction = "",phone = " ",addr = " ";
	 if(obj1.getStrSmallImg()!=null)
	 {
	 	strimg = "../coupon/images/"+obj1.getStrSmallImg();
	 }
	 if(obj1.getStrIntro()!=null)
	 {
	 	info = obj1.getStrIntro();
	 }
	 if(obj1.getStrInstruction()!=null)
	 {
	 	instruction = obj1.getStrInstruction();
	 } 	
	 if(objshop!=null && objshop.getStrPhone()!=null)
	 {
	 	phone = objshop.getStrPhone();
	 } 	
	 if(objshop!=null && objshop.getStrAddr()!=null)
	 {
	 	addr = objshop.getStrAddr();
	 } 
	 if(info.length()>27)
	 {
	 	info = info.substring(0,25)+"...";
	 }
	 if(instruction.length()>81)
	 {
	 	instruction = instruction.substring(0,80)+"<br/>...";
	 }
	 System.out.println();
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
  <input type=hidden name=strid value="<%=strId%>" />
  <input type=hidden name=flag value=" " />
   <table width=100% border=0> 
 	<tr>
 		<td>
 			<table width=100%  height="540" >
 			   <tr>
				   <td align="center">
				    <table width=100%>	    
					   <tr align="center">
					   	<td><img src="<%=strimg%>" width=100 height=50 /></td>
					   </tr> 
				    </table>
				   </td>
			   </tr>
			   <tr>
			   	<td width=100% ><img src="web/images/show_line.gif" width=240 height=5 /></td>
			   </tr>  
			   <tr>
			   	 <td><h2><%=info%></h2></td>
			   </tr>
			   <tr>
			   	<td width=100% ><img src="web/images/show_line.gif" width=240 height=5 /></td>
			   </tr>  
			   <tr>
			  	<td width=100% >
			   		<table width=100%>	    
					   <tr align="center">
					   		<td><h5>【使用说明】</h5></td>
					   </tr> 
					   <tr align="left">
					   		<td><font style="font-size: 13px;font-weight: 300;">电 话：</font><font size="2"><%=phone%></font></td>
					   </tr> 
					   <tr align="left"> 
					   		<td><font style="font-size: 13px;font-weight: 300;">地 址：</font><font size="2"><%=addr%></font></td>
					   </tr> 
					   <tr align="left">
					   		<td><font style="font-size: 13px;font-weight: 300;">说 明：<br/></font><font size="2"><%=instruction.replace("\n","<br/>")%></font></td>
					   </tr> 
				    </table>
			    </td>
			   </tr>
			   <tr>
			   	<td width=100% ><img src="web/images/show_line.gif" width=240 height=5 /></td>
			   </tr>
			   <tr>
			   	<td height=50>
			    	<h3><div id="txt"></div></h3>   	
			   	</td>
			   </tr>
			    <tr>
			   	<td width=100% ><img src="web/images/show_line.gif" width=240 height=5 /></td>
			   </tr>
			   <tr>
				   <td align="center">
				    <table width=100%>	    
					   <tr>
					   	<td align="center" ><h3><%=TerminalParamVector.getPrintBottom()%><br/><%=TerminalParamVector.getPhone()%></h3></td>
					   </tr>
				    </table>
				   </td>
			   </tr> 
 			</table>
 		</td>
 	</tr>   
   <tr>
   <td>
   		<table>
   			<tr>	
	   			<td><input class=Noprint style="width=110;height=30" type=button name=button1 onclick="if(confirm('确定打印该优惠吗？')){document.getElementById('txt').innerHTML='<%=strCouponCode3%>';document.getElementById('flag').value='print' ;window.print();document.getElementById('frm').submit();}" value="打     印"/></td>
	   			<td><input class=Noprint style="width=110;height=30" type=button name=button2 onclick="window.close();" value="关     闭"/></td>
  			</tr>
   		</table>
   	</td>
     </tr>
   </table>
   </form> 
    <%globa.closeCon(); %>
  </body>
</html>