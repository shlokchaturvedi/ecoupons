<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*,
				com.ejoysoft.common.SendSms,
				java.net.UnknownHostException" %>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@page import="com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@ include file="../include/jsp/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员注册处理</title>
<link href="css/member.css" rel="stylesheet" type="text/css" />
<%
	
	String flag = ParamUtil.getString(request,"flag","");
	
	String strPhone = ParamUtil.getString(request,"strPhone","");
	
		strPhone=strPhone.trim();
		System.out.println(strPhone);
	
	Member obj = new Member(globa); 
	String nowdate = Format.getDateTime();
	//Member member = obj.show(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
	if(flag.equals("getyzm"))
		{
	 		 
	 	     Random randGen = new Random();
			 char[] randBuffer = new char[6];
			 char[] numbersAndLetters =("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
			 for (int i=0; i<randBuffer.length; i++) {
		             randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		         }
			 String randomCode = new String(randBuffer);
			 System.out.println(randomCode);
			 String strWelcome = TerminalParamVector.getWelcome();
			 String messege="亲爱的会员您好，您于乐购网注册的验证码为："+randomCode+"！请根据此验证码完成注册过程，祝您购物愉快！"+strWelcome;
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
							 globa.setMember(null);
							 session.setAttribute(Constants.MEMBER_KEY,null);
		     				 response.getWriter().print("<script>alert('短信发送验证码成功！请查收短信');opener.document.getElementById('randomYazm').value='"+randomCode+"';window.close();</script>");
						}else
						{
		     				 response.getWriter().print("<script>alert('短信发送验证码失败1,请稍后重试！');window.close();</script>");
			    		}
			    	}
				    else {			    	
		      			 response.getWriter().print("<script>alert('短信发送验证码失败，请稍后重试！');window.close();</script>");
					}
			   }catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			   }		     
	 	 			 	
	}	
	
	
	else{
		
	//---------------------------------------------------------------------------------------
	System.out.println("----------134355u70---------");
		String strCardNo = ParamUtil.getString(request,"strCardNo","");
		
		if(strCardNo==""){
			strCardNo="";
		}
		else if(!strCardNo.equals("")&&strCardNo.trim().length()==7)
		{
			strCardNo= "8"+strCardNo.trim();
		}
		else if(!strCardNo.equals("")&&strCardNo.trim().length()!=8)
		{
		 	 response.getWriter().println("<script>alert('对不起！您输入的卡号无效，请重新输入！');window.returnValue='';window.close();</script>");
		     return;
		}	
		
		String strName = ParamUtil.getString(request,"strName","");
		String strPwd = ParamUtil.getString(request,"strPwd","");
		
		obj.setStrName(strName);
		obj.setStrPwd(strPwd);
		obj.setStrMobileNo(strPhone);
		
		if(strCardNo!=""){
			
			
		Member member = obj.show(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
		int standardNum = Integer.parseInt(String.valueOf(application.getAttribute("MOBILE_BIND_CARD_NUM")));
		int mobileBindNum = obj.getCount(" where strmobileno='"+strPhone+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"' and strcardno<>'"+strCardNo+"'");
			
			 if(member==null)
			 {
			 	 response.getWriter().println("<script>alert('对不起！您输入的卡号无效，请重新输入！');window.returnValue='';window.close();</script>");
			 }
		//    else if(mobileBindNum>=standardNum)
		//	 {
		//	 	 response.getWriter().println("<script>alert('对不起！您输入的手机号绑定卡号数量已达到上限！请更换手机号或联系管理员！');window.returnValue='';window.close();</script>");
		//	 }  
			 else if(member.getStrPwd()!=null&&!member.getStrPwd().equals(""))
		 	 { 
		 		 response.getWriter().println("<script>alert('对不起！您输入的卡号已经被注册！');window.returnValue='';window.close();</script>");
		 	 }	
		
		//------------------------------------------------------------------------------------------------
		
		System.out.println("asdfg22222222222222222");
		obj.setStrCardNo(strCardNo);
		obj.setIntType(member.getIntType());
		obj.setDtActiveTime(member.getDtActiveTime());
		obj.setFlaBalance(member.getFlaBalance());
		obj.setIntPoint(member.getIntPoint());
		obj.setDtExpireTime(member.getDtExpireTime());
		obj.setStrSalesman(member.getStrSalesman());
		obj.setStrCreator(member.getStrCreator());
		obj.setDtCreateTime(member.getDtCreateTime());
		
	
		//globa.dispatch(obj.update(member.getStrId()), "index.jsp");	
		if(obj.update(member.getStrId()))
		{	System.out.println("_-----333-----------______");
			 response.getWriter().print("<script>alert('注册成功！点击返回首页登录！');window.location.href='index.jsp';</script>");
		}	 
		else
		{		System.out.println("_----------------______");
			response.getWriter().print("<script>alert('注册失败！点击返回重新注册！');window.history.back();</script>");
		}
	}else {
		System.out.println("asdfg");
		if(obj.add1()){
			System.out.println("_-----311113-----------______");
			 response.getWriter().print("<script>alert('注册成功！点击返回首页登录！');window.location.href='index.jsp';</script>");
		}else{
			System.out.println("_------22233----------______");
			response.getWriter().print("<script>alert('注册失败！点击返回重新注册！');window.history.back();</script>");
		}
	}
	}
	globa.closeCon();   		
   
 %>
</head>
<body>
</body>
</html>
