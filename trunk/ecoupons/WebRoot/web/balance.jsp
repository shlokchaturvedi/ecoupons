<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="css/comment.css">
<title>我的余额</title>
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
  <table width="81%" border="0" cellpadding="0" cellspacing="0">
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
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="60" valign="top">可用余额：<span class="fjwz">280.00 </span> 元 &nbsp;&nbsp;&nbsp;&nbsp;<a href="alipay/payindex.jsp" /><img src="images/czban.jpg" width="56" height="24" /></a></td>
    </tr>
    <tr>
      <td height="28"><span class="bzb">余额变动情况</span></td>
    </tr>
  </table>
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="DCDCDC">
    <tr> 
      <td height="25" align="center" bgcolor="EEEEEE" class="collect_show_tit">时间</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">项目</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">支出</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">支入</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">账户余额</td>
      </tr>
    <tr>
       <td height="25" align="center" bgcolor="#FFFFFF"><span class="STYLE1">2012-02-02 <br />
        10:50:02 </span></td>
      <td align="center" bgcolor="#FFFFFF">打印有价券</td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1">60元</span></td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1">620元</span></td>
      </tr>
    <tr>
      <td height="25" align="center" bgcolor="#FFFFFF"><span class="STYLE1">2012-02-03 <br />
10:50:02 </span></td>
      <td align="center" bgcolor="#FFFFFF">充值</td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF">80元</td>
      <td align="center" bgcolor="#FFFFFF">680元</td>
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
