 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*,
				com.ejoysoft.common.SendSms,
				com.ejoysoft.ecoupons.business.Coupon,
				java.net.UnknownHostException" %>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@page import="com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发送优惠劵信息至手机</title>
<link href="css/member.css" rel="stylesheet" type="text/css" />
<%
	 String strPhone = ParamUtil.getString(request,"phone","");	
System.out.println(strPhone+"--======----===--=--=");
	 String strId = ParamUtil.getString(request,"strid");	 
		System.out.println("strId::::"+strId);
	 
		Coupon coupobj = new Coupon(globa);
		 Coupon obj1 = coupobj.show(" where strid='"+strId+"' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime ");
		 
	 String info=obj1.getStrIntro();
	 String instruction=obj1.getStrInstruction();
	 String strWelcome = TerminalParamVector.getWelcome();
	String printBottom=TerminalParamVector.getPrintBottom();
	String phone=TerminalParamVector.getPhone();
	 String messege=info +","+instruction+"。"+printBottom+"  "+phone;
	System.out.println(messege);
	 String PostData;
	    try {
			PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strPhone+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
		    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
		    if(!ret.equals(""))
	    	{
	    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
				int endIdx = ret.indexOf("</MsgState>");
				String retMsgState = ret.substring(beginIdx, endIdx);
				if(retMsgState.equals("提交成功"))		
				{		      
						 response.getWriter().print("<script>alert('短信发送验证码成功！请查收短信');window.close();</script>");
				}else
				{
	    				 response.getWriter().print("<script>alert('短信发送验证码失败1！');window.close();</script>");
	    		}
	    	}
		    else {			    	
	     			 response.getWriter().print("<script>alert('短信发送验证码失败2！');window.close();</script>");
			}
	   }catch (UnknownHostException e) {
			e.printStackTrace();
	   }	
	globa.closeCon();   
 %>
</head>
<body>
</body>
</html>
