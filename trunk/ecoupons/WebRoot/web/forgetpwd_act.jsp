
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page
	import="java.util.*,
				com.ejoysoft.common.*,com.ejoysoft.auth.MD5,
				java.net.UnknownHostException"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@page import="com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@ include file="../include/jsp/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>获得新密码</title>
		<link href="css/member.css" rel="stylesheet" type="text/css" />
		<%
//判断验证码
String rand = (String) request.getSession().getAttribute("rand");
String input = request.getParameter("yanzm");
String strPhone=request.getParameter("strPhone");
//String strPwd=request.getParameter("password");
Member member=new Member(globa);
if (rand.toLowerCase().equals(input.toLowerCase()))
{
	if(member.getCount("where strMobileNo='"+strPhone+"'")>0){
             Random randGen = new Random();
			 char[] randBuffer = new char[6];
			 char[] numbersAndLetters =("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
			 for (int i=0; i<randBuffer.length; i++) {
		             randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		         }
			 String randomCode = new String(randBuffer);
			 System.out.println(randomCode);
			 String strWelcome = TerminalParamVector.getWelcome();
			 String messege="亲爱的会员您好，您于乐购网注册的密码改为："+randomCode+"！请根据此新密码登录网站！"+strWelcome;
			 String PostData;
		     try {
					PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strPhone+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
				    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
				    if(!ret.equals(""))
			    	{
			    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
						int endIdx = ret.indexOf("</MsgState>");
						String retMsgState = ret.substring(beginIdx, endIdx);
						if(retMsgState.equals("审查"))	{			        
						    if( member.updatePwdByPhone(MD5.getMD5ofString(randomCode),strPhone))
		     				 response.getWriter().print("<script>alert('短信发送密码成功，请重新登录！');top.location = '" + application.getServletContextName() + "/web/index.jsp';</script>");
						    else
		     				 response.getWriter().print("<script>alert('操作失败，请联系管理员！');top.location = '" + application.getServletContextName() + "/web/forgetpwd.jsp';</script>");
						     }
						else
						{
		     				 response.getWriter().print("<script>alert('短信发送密码失败1！');top.location = '" + application.getServletContextName() + "/web/forgetpwd.jsp';</script>");
			    		}
			    	}
				    else {			    	
		      			 response.getWriter().print("<script>alert('短信发送密码失败2！');top.location = '" + application.getServletContextName() + "/web/forgetpwd.jsp';</script>");
					}
			   }catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			   }		     
	}else{
	 response.getWriter().println("<script>alert('该手机号码未注册，请重新输入！');top.location = '" + application.getServletContextName() + "/web/forgetpwd.jsp';</script>");
	}
	
} else
{
	 response.getWriter().println("<script>alert('验证码错误，请重新输入！');top.location = '" + application.getServletContextName() + "/web/forgetpwd.jsp';</script>");
}
	 	
	globa.closeCon();   		
   
 %>
	</head>
	<body>
	</body>
</html>
