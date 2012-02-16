 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@ include file="../include/jsp/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>会员注册</title>
<link href="css/member.css" rel="stylesheet" type="text/css" /></head>

<body>
<iframe height="167" marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="member-content">
<div class="member_tit">会员注册</div>


<div class="member_box">
<div class="member_left">
  <table width="96%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="17%" class="member_td_wz">卡&nbsp;&nbsp;&nbsp;号：&nbsp;&nbsp;</td>
      <td width="83%"><input type="text" name="textfield" class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">手机号：&nbsp;&nbsp;</td>
      <td><input type="text" name="textfield2"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">真实姓名：&nbsp;&nbsp;</td>
      <td><input type="text" name="textfield3"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">密&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
      <td><input type="text" name="textfield4"  class="member_ipt"/></td>
    </tr>
    <tr>
      <td class="member_td_wz">验证码：&nbsp;&nbsp;</td>
      <td><input type="text" name="textfield5"  class="yzm"/>
        <input type="submit" name="Submit" value="获取验证码" /></td>
    </tr>
    <tr>
      <td height="40" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;<img src="images/tjzc.jpg" /></td>
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
<!--正文部分结束-->
<iframe style="HEIGHT: 140px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
