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
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <%
	 String memberCardno="",memberPhone="",randomCode="";  
	 if(session.getAttribute(Constants.MEMBER_KEY) == null)
	 {
   		globa.closeCon();   		
      response.getWriter().println("<script>alert('您还未登录！请先登录！');window.opener=null;window.close();</script>");
	 }
	 memberCardno = globa.memberSession.getStrCardNo(); 
	 memberPhone = 	globa.memberSession.getStrMobileNo(); 
	 String strId = ParamUtil.getString(request,"strid");
	 if(strId.equals(""))
	    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
	 String flag = ParamUtil.getString(request,"flag"," "); 
	 Coupon coupobj = new Coupon(globa);
	 Coupon obj1 = coupobj.show(" where strid='"+strId+"' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime ");
	 if(obj1!=null)
	 {
	 	 CouponPrint couponPrint = new CouponPrint(globa);
		 int limitNum = obj1.getIntPrintLimit();
		 int printNum = couponPrint.getCount(" where strcouponid='"+strId+"'");
		 Shop shop = new Shop(globa);
		 Shop objshop = shop.show(" where strid = '"+obj1.getStrShopId()+"'");
		 String strimg = "web/images/temp.jpg",info = " ",instruction = "",phone = " ",addr = " ";
		 if(obj1.getStrSmallImg()!=null&&!obj1.getStrSmallImg().equals(""))
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
		 else if(limitNum !=0 && printNum >= limitNum)
		 {
		 	coupobj.setDtExpireTime(Format.getDateTime());
		 	coupobj.updateExpireTime(strId);
		    response.getWriter().println("<script>alert('对不起！该优惠券打印次数已达上限！');window.opener=null;window.close();</script>");
		 }	 
		 else if(couponPrice!=0 && couponPrice > balance)
		 {
		    response.getWriter().println("<script>alert('您的会员卡余额不足！请即使充值');window.opener=null;window.close();</script>");
		 }	 
	 	 if(flag.equals("print"))
	 	 {	  
	 	    CouponPrint obj = new CouponPrint(globa);
	 	    String strCouponCode2 = ParamUtil.getString(request,"code");
	 	    obj.setStrCreator("system");
	 	    obj.setStrCouponCode(strCouponCode2);
	 	    obj.setStrCouponId(strId);
	 	    obj.setStrMemberCardNo(memberCardno);
	 	    obj.setStrTerminalId("system");
		  	boolean result = obj.add();		
		  	response.getWriter().println("<script>setTimeout('window.opener=null;window.close();',300);</script>");	
	 	 	
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
<script src="../include/js/chkFrm.js"></script>
<script type="text/javascript">
function getYzm()
{
	if(confirm("确定短信获取验证码？"))
    {
	 	var randomyazm=window.showModalDialog("../web/print_act.jsp?memberPhone="+<%=memberPhone%>+"&random="+Math.random(), "", "dialogWidth=200px;dialogHeight:150px;dialogTop:400px;dialogLeft:550px;scrollbars=yes;status=yes;center=yes;");
		frm.randomYzm.value=randomyazm;
	}
}
function chkFrm()
{
	if(trim(frm.yzm.value)=="") {
        alert("请输入验证码！！！");
        frm.yzm.focus();
        return false;
    }else
    {
    	  if(trim(frm.yzm.value)!=(trim(frm.randomYzm.value)))
    	  {
    	  	    alert("您输入的验证码错误！");
                frm.yzm.focus();         
    		    return false;
    	  }
    	  if(confirm("确定打印该优惠券？"))
    	  {
    	    document.getElementById("txt").innerHTML="<%=strCouponCode3%>";
    	    document.getElementById("flag").value="print" ;
    	    window.print();
    	    frm.submit();
    	  }
    }
}
</script>
  </head>
  <body>
  <form action="web/coupon_print.jsp" method=post id=frm >
  <input type=hidden name=code value="<%=strCouponCode%>" />
  <input type=hidden name=strid value="<%=strId%>" />
  <input type=hidden name=flag value=" " />
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
				   	<td><img src="web/images/show_line.gif" width=240 height=5 /></td>
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
				   	<td><img src="web/images/show_line.gif" width=240 height=5 /></td>
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
				   	<td align="center"><img src="web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
				</td>
			   </tr>
			   <tr>
			   	<td height=50 align="center" >
				<table align="center">	    
					   <tr>
					   	<td align="center"><h3><div id="txt"></div></h3> </td>
				   	</tr>
				  </table>			    	  	
			   	</td>
			   </tr>
			    <tr>
			    <td align="center" >
				   <table align="center">	    
				   <tr>
					   	<td align=center><img src="web/images/show_line.gif" width=240 height=5 /></td>
				   	</tr>
				  </table>
				</td>
			   </tr>
			   <tr>
				   <td align="center" >
					   <table align="center">	    
					   <tr>
					   	<td align="center"> <h3><%=TerminalParamVector.getPrintBottom()%><br/><%=TerminalParamVector.getPhone()%></h3></td>
					   	</tr>
					   </table>
				  </td>
			  </tr> 
			   <tr>
			       <td align="center" >
			       <table align="center" >	    
				   <tr>
				   	<td><input class=Noprint style="width=110;height=25;" name="yzm" value="" type="text"/>
			     	 <input class=Noprint name="randomYzm"  type="hidden"/>
			         <input class=Noprint style="width=110;height=25; background-color:#FFFFFF; border:1px solid #999999;" type="button" name="botton3" onclick="getYzm();" value="短信获取验证码" />
			         </td>
				   	</tr>
				  </table>
			      </td>
			    </tr> 
			    
			     <tr>
				   <td>
				    <table width=100%>	    
					   <tr align="center">
					   <td><input class=Noprint style="width=115;height=27; background-color:#FFFFFF; border:1px solid #999999;" type=button name=button1 onclick="chkFrm();" value="打     印"/>
	   					<input class=Noprint style="width=115;height=27; background-color:#FFFFFF; border:1px solid #999999;" type=button name=button2 onclick="window.close();" value="关     闭"/></td>
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
    <%
     }
	 else
	 {
	 		response.getWriter().println("<script>alert('对不起！该优惠券已过有效期！无法打印！');window.opener=null;window.close();</script>");
	 }
	 
 	 	
    globa.closeCon(); %>
  </body>
</html>