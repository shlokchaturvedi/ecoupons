 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员注册</title>
<link href="css/member.css" rel="stylesheet" type="text/css" />
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
function chkFrm()
{
    if(trim(frm.strPhone.value)==""){        
        alert("请输入您的手机号！！！");
        frm.strPhone.focus();
        return false;
    }else if(trim(frm.yanzm.value)=="") {
        alert("请输入验证码！！！");
        frm.yanzm.focus();
        return false;
    }else {           
        var telphone = trim(frm.strPhone.value);
        var tel=new Array();
        tel = telphone.split("、"); 
        var TelPhoneParn =/^0{0,1}1(3|5|8)[0-9]{9}$/; 
        var reParn = new RegExp(TelPhoneParn);
        for(i=0;i<tel.length;i++)
    	  {
    	 	if(!reParn.test(tel[i]))
    		{
              alert(tel[i]+"请输入正确的联系电话！！！如13258886888");
              frm.strPhone.focus();         
    		  return false;
    		}      		
    	  }
    	  
    	  if(confirm("确定获得新密码？"))
    	  {
    		  frm.submit();
    	  }
    }
}


</script>
</head>
<body>
<iframe height="167" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="member-content">
<div class="member_tit">会员注册</div>
<div class="member_box">
<div class="member_left">

<form action="forgetpwd_act.jsp" method="post" name="frm"  >
  <table width="96%" border="0" cellspacing="0" cellpadding="0">
   
    <tr>
      <td class="member_td_wz">&nbsp;&nbsp;</td>
      <td class="member_td_wz1">&nbsp;&nbsp;</td>
    </tr>
    <tr>
      <td class="member_td_wz">手机号：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPhone" id="strPhone" type="text"  class="member_ipt"/>&nbsp;&nbsp;</td>
    </tr>
    <tr>
     <td class="member_td_wz">验证码：&nbsp;&nbsp; </td>
    <td  class="member_td_wz1"><input style="WIDTH: 60px" name=yanzm class=form type=text value="" maxLength=4 size=10/> 
    <a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code2').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <img style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code2 border=0 name=checkcode src="../image.jsp"/> 
    </a></td>
    </tr>
   
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;<img src="images/sure.jpg" onclick="chkFrm();" /></td>
    </tr>
  </table>
</form>
</div>
<div class="member_right">
<span>&nbsp;温馨提示：</span>
<textarea name="wxts" cols="" rows="" class="ts_nr">1. 请输入注册时手机号码；
 
2. 请收到新密码后，立即更改新密码。</textarea>
</div>
</div>

</div>
<!--正文部分结束-->
<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=300 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
