<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="css/comment.css">
<title>我的积分</title>
</head>
<body>
<iframe style="HEIGHT: 167px" border=0 marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
<!--正文部分-->
<DIV id=Main>
<DIV id=collect_Right>
<DIV class=collect_list>
<DIV class=collect_right_top>
<DIV class=collect_heatitle><h6>会员中心</h6></DIV>
</DIV>
<DIV class=collect_mid>
  <p>&nbsp;</p>
  <table width="81%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
      <td height="32" class="list_wz"><a href="collection.jsp">&nbsp;&gt;&gt; 我的收藏</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="history.jsp">&nbsp;&gt;&gt; 历史记录</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="balance.jsp" >&nbsp;&gt;&gt; 我的余额</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="integral.jsp">&nbsp;&gt;&gt; 我的积分</a></td>
    </tr>

    <tr>
      <td height="32" class="list_wz"><a href="#">&nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</DIV>
<DIV class=collect_bottom></DIV></DIV>
</DIV>




<DIV id=Left>
<DIV class=collect_left_top>
		<div class="collect_sf">我的积分</div>
</DIV>
<DIV class=collect_left_mid>
<DIV class=collect_show>
  <table width="84%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="60" colspan="2" valign="middle" class="intergal_zw">请输入积分卡卡号和密码</td>
      </tr>
    <tr>
      <td width="18%" height="45" class="intergal_zw">积分卡卡号：</td>
      <td width="82%"><input type="text" name="textfield"  class="type_k"/></td>
    </tr>
    <tr>
      <td height="45" class="intergal_zw">密&nbsp;&nbsp;码：</td>
      <td><input type="text" name="textfield2"  class="type_k"/></td>
    </tr>
    <tr>
      <td height="60">&nbsp;</td>
      <td valign="bottom"><img src="images/sure.jpg" width="80" height="32" /></td>
    </tr>
  </table>
  </DIV>
</DIV>
<DIV class=collect_show_bottom></DIV></DIV>
</DIV>

<iframe style="HEIGHT: 140px" border=0 marginwidth=0 marginheight=0 src="bottom2.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</body>
</html>