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
    if(trim(frm.strCardNo.value)==""){
        alert("请输入您的卡号！！！");
        frm.strCardNo.focus();
        return false;
    }else if(trim(frm.strPhone.value)==""){        
        alert("请输入您的手机号！！！");
        frm.strPhone.focus();
        return false;
    }else if(trim(frm.strName.value)==""){        
        alert("请输入您的姓名！！！");
        frm.strName.focus();
        return false;
    }else if(trim(frm.strPwd.value)=="") {
        alert("请设置您的密码！！！");
        frm.strPwd.focus();
        return false;
    } else if(trim(frm.strPwd2.value)=="") {
        alert("请重复输入密码！！！");
        frm.strPwd2.focus();
        return false;
    } else if(trim(frm.yanzm.value)=="") {
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
    	  if(trim(frm.strPwd.value)!=(trim(frm.strPwd2.value)))
    	  {
    	  	    alert("两次输入的密码不一致");
              frm.strPwd.focus();         
    		    return false;
    	  }
    	  if(trim(frm.yanzm.value)!=(trim(frm.randomYazm.value)))
    	  {
    	  	    alert("您输入的验证码错误！");
                frm.yanzm.focus();         
    		    return false;
    	  }
    	  if(confirm("确定提交注册信息？"))
    	  {
    		  frm.submit();
    	  }
    }
}
function getYzm()
{
	strCardNo = document.getElementById("strCardno").value;
	strPhone = document.getElementById("strPhone").value;
	if(trim(strCardNo)=="")
	{
	  alert("请输入您的卡号！！！");
      document.getElementById("strCardno").focus();         
	  return false;
	}	
	else if(trim(strPhone)=="")
	{
	  alert("请输入联系电话！！！如13258886888");
      document.getElementById("strPhone").focus();         
	  return false;
	}	    		
	else{
		var TelPhoneParn =/^0{0,1}1(3|5|8)[0-9]{9}$/; 
	    var reParn = new RegExp(TelPhoneParn);
	   	if(!reParn.test(trim(strPhone)))
		{
	      alert("联系电话有误！请重新输入！！");
	      document.getElementById("strPhone").focus();         
		  return false;
		}  
		else
		{
			var randomyazm=window.showModalDialog("memberreg_act.jsp?flag=getyzm&strCardNo="+strCardNo+"&strPhone="+strPhone+"&random="+<%=Math.random()%>, "", "dialogWidth=200px;dialogHeight:150px;dialogTop:400px;dialogLeft:550px;scrollbars=yes;status=yes;center=yes;");
			document.getElementById("randomYazm").value=randomyazm;
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

<form action="memberreg_act.jsp" method="post" name="frm"  >
  <table width="96%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="17%" class="member_td_wz">卡  &nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;&nbsp;</td>
      <td width="83%" class="member_td_wz1"><input type="text" name="strCardNo" class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">手 机 号：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPhone" id="strPhone" type="text"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">姓  &nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strName" type="text"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">密  &nbsp;&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPwd" type="password"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">重复密码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPwd2" type="password" class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">验 证 码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="yanzm"  type="text"  class="yzm"/>
     	 <input name="randomYazm"  type="text"  class="yzm"/>
         <input type="button" name="botton" onclick="getYzm();" value="获取验证码" /></td>
    </tr>
    <tr>
      <td height="40" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td>&nbsp;<input name="flag"  type="hidden"  value="" /></td>
      <td>&nbsp;<img src="images/tjzc.jpg" onclick="chkFrm();" /></td>
    </tr>
  </table>
</form>
</div>
<div class="member_right">
<span>&nbsp;温馨提示：</span>
<textarea name="wxts" cols="" rows="" class="ts_nr">1. 注册乐购生活会员完全免费， 乐购生活承诺绝不会向任何第三方透露您的手机号， 也不会在未经您许可的情况下向您的手机发送其他信息；
 
2. 目前暂不支持小灵通用户注册。</textarea>
</div>
</div>

</div>
<!--正文部分结束-->
<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=300 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
