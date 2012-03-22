 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*,
				com.ejoysoft.common.SendSms,
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
<title>获取打印验证码</title>
<link href="css/member.css" rel="stylesheet" type="text/css" />
<%
	 String strPhone = ParamUtil.getString(request,"memberPhone","");	
	 Random randGen = new Random();
	 char[] randBuffer = new char[4];
	 char[] numbersAndLetters =("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
	 for (int i=0; i<randBuffer.length; i++) {
	            randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
	        }
	 String randomCode = new String(randBuffer);
	 String strWelcome = TerminalParamVector.getWelcome();
	 String messege="亲爱的会员您好，您于乐购网打印优惠券的验证码为："+randomCode+"！请根据此验证码完成打印过程，祝您购物愉快！"+strWelcome;
	 String PostData;
	    try {
			PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strPhone+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
		    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
		    if(!ret.equals(""))
	    	{
	    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
				int endIdx = ret.indexOf("</MsgState>");
				String retMsgState = ret.substring(beginIdx, endIdx);
				if(retMsgState.equals("审查"))		
				{		      
						 response.getWriter().print("<script>alert('短信发送验证码成功！请查收短信');window.returnValue='"+randomCode+"';window.close();</script>");
				}else
				{
	    				 response.getWriter().print("<script>alert('短信发送验证码失败1！');window.returnValue='"+randomCode+"';window.close();</script>");
	    		}
	    	}
		    else {			    	
	     			 response.getWriter().print("<script>alert('短信发送验证码失败2！');window.returnValue='';window.close();</script>");
			}
	   }catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }	
	globa.closeCon();   
 %>
</head>
<body>
</body>
</html>
