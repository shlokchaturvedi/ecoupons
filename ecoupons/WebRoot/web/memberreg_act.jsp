 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*,
				com.ejoysoft.common.SendSms,
				java.net.UnknownHostException" %>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@ include file="../include/jsp/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员注册处理</title>
<link href="css/member.css" rel="stylesheet" type="text/css" />
<%
	String flag = ParamUtil.getString(request,"flag","");
	String strCardNo = ParamUtil.getString(request,"strCardNo","");
	String strPhone = ParamUtil.getString(request,"strPhone","");
	Member obj = new Member(globa); 
	String nowdate = Format.getDateTime();
	Member member = obj.show(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
	if(flag.equals("getyzm"))
	{
	     if(member==null)
		 {
		 	 response.getWriter().println("<script>alert('对不起！您输入的卡号无效，请重新输入！');window.returnValue='';window.close();</script>");
		 }
		 else if(member.getStrPwd()!=null&&!member.getStrPwd().equals(""))
	 	 {
	 		 response.getWriter().println("<script>alert('对不起！您输入的卡号已经被注册！');window.returnValue='';window.close();</script>");
	 	 }		 
	 	 else{	
	 	     Random randGen = new Random();
			 char[] randBuffer = new char[6];
			 char[] numbersAndLetters =("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
			 for (int i=0; i<randBuffer.length; i++) {
		             randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		         }
			 String randomCode = new String(randBuffer);
			 System.out.println(randomCode);
			 String messege="亲爱的会员您好，您于乐购网注册的验证码为："+randomCode+"！请根据此验证码完成注册过程，祝你购物愉快！";
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
		     				 response.getWriter().print("<script>alert('短信发送验证码成功！请查收短信');window.returnValue='"+randomCode+"';window.close();</script>");
						else
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
	 	 }			 	
	}			
	else
	{
		String strName = ParamUtil.getString(request,"strName","");
		String strPwd = ParamUtil.getString(request,"strPwd","");
		obj.setStrCardNo(strCardNo);
		obj.setStrName(strName);
		obj.setStrPwd(strPwd);
		obj.setStrMobileNo(strPhone);
		obj.setIntType(member.getIntType());
		obj.setDtActiveTime(member.getDtActiveTime());
		obj.setFlaBalance(member.getFlaBalance());
		obj.setIntPoint(member.getIntPoint());
		obj.setDtExpireTime(member.getDtExpireTime());
		obj.setStrSalesman(member.getStrSalesman());
		obj.setStrCreator(member.getStrCreator());
		obj.setDtCreateTime(member.getDtCreateTime());
		globa.dispatch(obj.update(member.getStrId()), "index.jsp");		 
	}
	globa.closeCon();   		
   
 %>
</head>
<body>
。<iframe height="167" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<form action="memberreg_act.jsp" method="post" name="frm"  >
<div class="member-content">
<div class="member_tit">会员注册</div>
<div class="member_box">
<div class="member_left">
  <table width="96%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="17%" class="member_td_wz">卡&nbsp;&nbsp;&nbsp;号：&nbsp;&nbsp;</td>
      <td width="83%"><input type="text" name="strCardNo" class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">手机号：&nbsp;&nbsp;</td>
      <td><input  name="strPhone" type="text"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">真实姓名：&nbsp;&nbsp;</td>
      <td><input name="strName" type="text"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">密&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
      <td><input name="strPwd" type="password"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">重复密码：&nbsp;&nbsp;</td>
      <td><input name="strPwd2" type="password" class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">验证码：&nbsp;&nbsp;</td>
      <td><input name="yanzm"  type="text"  class="yzm"/>
        <input type="button" name="botton" onclick="" value="获取验证码" /></td>
    </tr>
    <tr>
      <td height="40" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;<img src="images/tjzc.jpg" onclick="chkFrm();" /></td>
    </tr>
  </table>
</div>
<div class="member_right">
<span>&nbsp;温馨提示：</span>
<textarea name="wxts" cols="" rows="" class="ts_nr">1. 注册前沿生活会员完全免费， 前沿生活承诺绝不会向任何第三方透露您的手机号， 也不会在未经您许可的情况下向您的手机发送其他信息；
 
2. 目前暂不支持小灵通用户注册。</textarea>
</div>
</div>

</div>
</form>
<!--正文部分结束-->
<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=300 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
