<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<html>
<head>
<title><%=globa.APP_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link  rel="stylesheet" type="text/css"  href="../images/css<%=globa.css%>.css">
</head>
<%
    String strModule=ParamUtil.getString(request,"module","系统管理");
%>
<body scroll="no" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
<tr>
    <td width="198"><iframe frameborder=0 id=frmLeft name=frmLeft src="<%=application.getServletContextName()%>/left.jsp?module=<%=strModule%>" style=" HEIGHT: 100%; VISIBILITY: inherit; WIDTH:198; Z-INDEX: 3"></iframe></td>
    <td  align="center" valign="top" ><iframe frameborder=0 id=frmRight  name=frmRight src="<%=application.getServletContextName()%>/system/onlineList.jsp" style=" HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 3"></iframe></td>
  </tr>
</table>
</body>
</html>
