<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title><%=application.getAttribute("APP_TITLE")%></title>
<meta http-equiv=Content-Type content=text/html;charset=UTF-8>
</head>
<frameset rows="64,*"  frameborder="NO" border="0" framespacing="0">
	<frame src="top.jsp" noresize="noresize" frameborder="NO" name="topFrame" scrolling="no" marginwidth="0" marginheight="0"/>
  <frameset cols="200,*" id="frame">
	<frame src="left.jsp?root=日常管理" name="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="no"/>
	<frame src="right.jsp" name="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"/>
  </frameset>
<noframes>
  <body></body>
    </noframes>
</html>