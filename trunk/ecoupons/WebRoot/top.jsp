<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.common.Constants" %>
<html>
<head>
<title><%=application.getAttribute("APP_TITLE")%></title>
<script language=JavaScript>
function logout(){
	if (confirm("您确定要退出吗？"))
		top.location = "<%=application.getServletContextName()%>/Auth?actiontype=<%=Constants.LOGOFF%>";
	return false;
}
</script>
<style>
a:link,a:hover,a:active,a:visited{
	color: #ffffff;
	text-decoration: none;
}
.admin_txt {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #FFFFFF;
	text-decoration: none;
	height: 38px;
	width: 100%;
	position: 固定;
	line-height: 38px;
}
.admin_topbg {
	background-image: url(top-right.gif);
	background-repeat: repeat-x;
}
.body {
	background-color:  #F8F9FAs;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
}
</style>
<meta http-equiv=Content-Type content=text/html;charset=gb2312>
<script language=JavaScript1.2>
function showsubmenu(sid) {
	var whichEl = eval("submenu" + sid);
	var menuTitle = eval("menuTitle" + sid);
	if (whichEl.style.display == "none"){
		eval("submenu" + sid + ".style.display=\"\";");
	}else{
		eval("submenu" + sid + ".style.display=\"none\";");
	}
}
</script>
</head>
<body leftmargin="0" topmargin="0">
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB" class="admin_topbg">
  <tr>
    <td width="61%" height="64" background="images/top-right.gif" bgcolor="#EEF2FB"><img src="images/logo.gif" width="262" height="64"></td>
    <td width="39%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/top-right.gif">
      <tr>
        <td width="74%" height="38" class="admin_txt" align="right">
          <a href="#" onclick="top.leftFrame.location='left.jsp?root=日常管理';top.main.location='right.jsp';">日常管理</a> | 
          <a href="#" onclick="top.leftFrame.location='left.jsp?root=系统管理';top.main.location='right.jsp';">系统管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td width="22%"><a href="#" target="_self" onClick="logout();"><img src="images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>