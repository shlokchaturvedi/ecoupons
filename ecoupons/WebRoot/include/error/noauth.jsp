<%@ page import="com.ejoysoft.common.exception.NoAuthException"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isErrorPage="true" %>
<jsp:useBean id="globa" scope="page" class="com.ejoysoft.common.Globa" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="<%=application.getServletContextName()%>/images/css1.css">
</head>
<%
    NoAuthException ae = (NoAuthException) exception;
     //初始系统变量
    globa.initialize(application,request,response);
%>
<body class="right_bgcolor" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="97%" border="0" cellspacing="0" cellpadding="0" align="center">
   <tr>
   <td class="right_img04" width="6" height="230">&nbsp;</td>
   <td class="right_centerbgcolor" height="230" align="center" valign="top">
    <table width="100" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10"></td>
      </tr>
    </table>
    <table width="97%" border="0" cellspacing="0" cellpadding="0" class="td_color_1">
      <tr>
      <td height="26" class="td_color_imgbg">
        <table width="100%" border="0">
          <tr>
            <td width="53%" >&nbsp;<img src="<%=application.getServletContextName()%>/images/ico_08.gif" width="10" height="13" align="absmiddle">
              <b class="top_navigation_home_font">权限错误处理页面</b> </td>
            <td align="right" width="47%">&nbsp; </td>
          </tr>
        </table>
      </td>
      </tr>
      <tr>
        <td width="20%" height="109" valign="center">
          <table width="100" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="20"></td>
        </tr>
      </table>
      <table width="49%" border="0" align="center" cellspacing="8" bgcolor="#CCCCCC">
            <tr>
                <td height="160">
        <table width="500" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                <tr>
                    <td height="160" align="right" width="199"><img
                            src="<%=application.getServletContextName()%>/images/sw_advise.gif" width="168"
                            height="145"></td>
                    <td height="160" width="301" class="text14"><b>警告：</b><br>
                        <%=ae.getMessage()%> <br>
                        <b>建议：</b><br>
                        <%
                            if (ae.getAdvice() != null) {
                                out.print(ae.getAdvice());
                            } else {
                                out.print("请联系系统管理员");
                            }
                        %>
                        <br>
                        <br> <label style="cursor:pointer;" onclick="history.back();"> <img src="<%=application.getServletContextName()%>/images/sw_zh_002.gif" width="168"
                                  height="29"></label></td>
                </tr>
            </table>
              </td>
            </tr>
        </table>
             <br>
        <table width="49%" border="0" align="center" cellspacing="8" bgcolor="#ffffff">
            <tr>
                <td height="160">
                    <table width="500" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                        <tr>
                            <td height="160" width="100%" class="text14" valign="top">
                                <label style="cursor:pointer;" onclick="msg.style.display=(msg.style.display==''?'none':'')"><font color="#CC0000"><b>&nbsp;参考信息↓：</b></font><br></label>
                                <label id="msg" style="display:none;" >
                                    <%
                                        StringBuffer msg = new StringBuffer();
                                        StackTraceElement[] ste = exception.getStackTrace();
                                        for(int i = 0; ste != null && i < ste.length; i++){
                                            msg.append(ste[i].toString());
                                        }
                                    %>
                                <%=msg%><br>
                                <br></label>

                                <br> </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        </td>
      </tr>
    </table>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="<%=application.getServletContextName()%>/images/img_005.gif" height="4"></td>
        </tr>
      </table>
      <table width="100" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
      </table>
      <table width="100" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
      </table>
    </td>
       <td class="right_img05" width="6" height="230">&nbsp;</td>
        </tr>
        <tr>
          <td class="right_img06" width="6" height="6">&nbsp;</td>
          <td height="6" class="right_img08">&nbsp;</td>
          <td class="right_img07" width="6" height="6">&nbsp;</td>
        </tr>
      </table>
</body>
</html>
