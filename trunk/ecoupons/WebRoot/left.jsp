<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.conf.SysModule,
				 com.ejoysoft.conf.Module,
				 com.ejoysoft.common.exception.ApplicationException,
				 java.util.Vector" %>
<%@ include file="include/jsp/head.jsp"%>
<%
	String root = ParamUtil.getString(request, "root", "日常管理");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=application.getAttribute("APP_TITLE")%></title>
<script src="js/prototype.lite.js" type="text/javascript"></script>
<script src="js/moo.fx.js" type="text/javascript"></script>
<script src="js/moo.fx.pack.js" type="text/javascript"></script>
<style>
body {
	font: 12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #EEF2FB;
	margin: 0px;
}

#container {
	width: 182px;
	margin-top: 2px;
}

H1 {
	font-size: 12px;
	margin: 0px;
	width: 182px;
	cursor: pointer;
	height: 30px;
	line-height: 20px;
}

H1 a {
	display: block;
	width: 182px;
	color: #000;
	height: 30px;
	text-decoration: none;
	moz-outline-style: none;
	background-image: url(images/menu_bgS.gif);
	background-repeat: no-repeat;
	line-height: 30px;
	text-align: center;
	margin: 0px;
	padding: 0px;
}

.content {
	width: 182px;
	height: 26px;
}

.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}

.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 182px;
	padding-left: 0px;
}

.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px, 0px, 0px, 0px);
}

.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.tb {
	border: 1px solid #90A2AE;
}

.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}

.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
	background-image: url(images/menu_bg2.gif);
	background-repeat: no-repeat;
	text-align: center;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}

a:link,a:hover,a:active,a:visited {
	color: #333333;
	text-decoration: none;
}
</style>
</head>

<body>
	<table width="100%" height="280" border="0" cellpadding="0"
		cellspacing="0" bgcolor="#EEF2FB">
		<tr>
			<td width="182" valign="top">
				<table width="182" border="0" cellpadding="0" cellspacing="0"
					style="background: url(images/manage_r2_c1.jpg)" height="50"
					class="tb">
					<tr>
						<td width="53" rowspan="2"></td>
						<td width="129">
							欢迎您，<%=globa.fullRealName.split("/")[2] %>
						</td>
					</tr>
					<tr>
						<td>
							<a href="right1.html" target="main"><%=root %></a>
						</td>
					</tr>
				</table>
				<%
				Vector vctRoot = SysModule.getModules();
				Module modRoot = null;
				for (int i = 0; i < vctRoot.size(); i++) {
					if (((Module)vctRoot.get(i)).getName().equals(root)) {
						modRoot = (Module)vctRoot.get(i);
						break;
					}
				}
				if (modRoot == null) {
					throw new ApplicationException("系统配置错误：不存在" + root + "顶级模块！");
				}
				%>
				<div id="container">
				<%
				Vector vctModule = modRoot.getChildren();
				for (int i = 0; i < vctModule.size(); i++) {
					Module mod2 = (Module)vctModule.get(i);
				%>
					<h1 class="type">
						<a href="javascript:void(0)"><%=mod2.getName() %></a>
					</h1>
					<%
					Vector vctMod2 = mod2.getChildren();
					if (vctMod2 != null && vctMod2.size() > 0) {
					%>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="images/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<%
						for (int j = 0; j < vctMod2.size(); j++) {
							Module mod3 = (Module)vctMod2.get(j);
						%>
							<li>
								<a href="<%=mod3.getUrl() %>" target="main"><%=mod3.getName() %></a>
							</li>
						<%
						}
						%>
						</ul>
					</div>
				<%
					}
				}
				%>
				</div>
				<script type="text/javascript">
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('type');

	var myAccordion = new fx.Accordion(
		toggles, contents, {opacity: true, duration: 400}
	);
	myAccordion.showThisHideOpen(contents[0]);
</script>
			</td>
		</tr>
	</table>
</body>
</html>