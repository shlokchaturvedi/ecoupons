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
function getYzm()
{
	/*   if(trim(document.getElementById('strCardNo').value)==""){
	 *       alert("请输入您的卡号！！！");
	 *       document.getElementById('strCardNo').focus();
	 *       return false;
	 *   }
	 *	else
	 */
	
	 if(trim(document.getElementById('strPhone').value)==""){        
	        alert("请输入您的手机号！！！");
	        document.getElementById('strPhone').focus();
	        return false;
	    }	
	else{
		var telphone = trim(document.getElementById('strPhone').value);
        var tel=new Array();
        tel = telphone.split("、"); 
        var TelPhoneParn =/^0{0,1}1(3|5|8)[0-9]{9}$/; 
        var reParn = new RegExp(TelPhoneParn);
        for(i=0;i<tel.length;i++)
    	  {
    	 	if(!reParn.test(tel[i]))
    		{
              alert(tel[i]+"请输入正确的联系电话！！！如13258886888");
              document.getElementById('strPhone').focus();         
    		  return false;
    		}      		
    	  }
    	  var iTop = (window.screen.availHeight-85)/2; //获得窗口的垂直位置;
		  var iLeft = (window.screen.availWidth-100)/2; //获得窗口的水平位置;
		  var randomyazm=window.open("memberreg_act.jsp?flag=getyzm&strPhone="+trim(document.getElementById('strPhone').value)+"&random="+Math.random(), "", "width=5,height=5,top="+iTop+",left="+iLeft+",scrollbars=no,status=no,resizable=no");
		
	}
} 
function chkFrm()
{
	/*
	 * if(trim(document.getElementById('strCardNo').value)==""){
     *   alert("请输入您的卡号！！！");
     *   document.getElementById('strCardNo').focus();
     *   return false;
   	 * }else
	 */
	
     if(trim(document.getElementById('strPhone').value)==""){        
        alert("请输入您的手机号！！！");
        document.getElementById('strPhone').focus();
        return false;
    }else if(trim(document.getElementById('strName').value)==""){        
        alert("请输入您的姓名！！！");
        document.getElementById('strName').focus();
        return false;
    }else if(trim(document.getElementById('strPwd').value)=="") {
        alert("请设置您的密码！！！");
        document.getElementById('strPwd').focus();
        return false;
    } else if(trim(document.getElementById('strPwd2').value)=="") {
        alert("请重复输入密码！！！");
        document.getElementById('strPwd2').focus();
        return false;
    } else if(trim(document.getElementById('yanzm').value)=="") {
        alert("请输入验证码！！！");
        document.getElementById('yanzm').focus();
        return false;
    }else {           
        var telphone = trim(document.getElementById('strPhone').value);
        var tel=new Array();
        tel = telphone.split("、"); 
        var TelPhoneParn =/^0{0,1}1(3|5|8)[0-9]{9}$/; 
        var reParn = new RegExp(TelPhoneParn);
        for(i=0;i<tel.length;i++)
    	  {
    	 	if(!reParn.test(tel[i]))
    		{
              alert(tel[i]+"请输入正确的联系电话！！！如13258886888");
              document.getElementById('strPhone').focus();         
    		  return false;
    		}      		
    	  }
    	  if(trim(document.getElementById('strPwd').value)!=(trim(document.getElementById('strPwd2').value)))
    	  {
    	  	    alert("两次输入的密码不一致");
              document.getElementById('strPwd').focus();         
    		    return false;
    	  }
    	// trim(document.getElementById('randomYazm').value))
    	  if(trim(document.getElementById('yanzm').value)!='123')
    	  { 
    		 
    	  	    alert("您输入的验证码错误！");
                document.getElementById('yanzm').focus();         
    		    return false;
    	  }
    	  if(confirm("确定提交注册信息？"))
    	  {
    		  frm.submit();
    	  }
    }
}



</script>
</head>
<body>
<iframe height="164" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="member-content">
<div class="member_tit">会员注册</div>
<div class="member_box">
<div class="member_left">

<form action="memberreg_act.jsp" method="post" name="frm"  >
  <table width="96%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="17%" class="member_td_wz">卡  &nbsp;&nbsp;&nbsp;&nbsp;号：&nbsp;&nbsp;</td>
      <td width="83%" class="member_td_wz1"><input type="text" id="strCardNo" name="strCardNo" class="member_ipt"/>&nbsp;&nbsp;</td>
    </tr>
    <tr>
      <td class="member_td_wz">手 机 号：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPhone" id="strPhone" type="text"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">姓  &nbsp;&nbsp;&nbsp;&nbsp;名：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strName" id="strName" type="text"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">密  &nbsp;&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPwd" id="strPwd" type="password"  class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">重复密码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="strPwd2"  id="strPwd2"type="password" class="member_ipt"/>&nbsp;&nbsp;（*必填项）</td>
    </tr>
    <tr>
      <td class="member_td_wz">验 证 码：&nbsp;&nbsp;</td>
      <td class="member_td_wz1"><input name="yanzm"  id="yanzm" type="text"  class="yzm"/>
     	 <input name="randomYazm"  id="randomYazm" type="hidden"  class="yzm"/>
         <input type="button" name="botton" onclick="getYzm();" value="获取验证码" />
      </td>
    </tr>
    <tr>
      <td height="40" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td>&nbsp;<input name="flag"  id="flag" type="hidden"  value="" /></td>
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
<iframe style="HEIGHT: 260px" marginwidth=0 marginheight=300 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
