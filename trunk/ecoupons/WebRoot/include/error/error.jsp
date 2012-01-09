<%@ page contentType="text/html; charset=GBK"%>
<%@ page isErrorPage="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>错误信息</title>
<style type="text/css">
	.errortop a{color:#ff6600;font-size:12px;cursor:hand;}
	
</style>
</head>
<%
	String mHttpUrlName = request.getRequestURI();
	String mServerUrl = "http://" + request.getServerName() + ":"
			+ request.getServerPort()
			+ mHttpUrlName.substring(0, mHttpUrlName.lastIndexOf("/"));
 %>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="550" border="0" align="center"style="margin-top:23px;" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="332" valign="top" background="<%=mServerUrl %>/in[k]_r2_c2.jpg">
        <table width="90%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="25" align="right" class="errortop">
              <a onclick="history.go(-1)"><span id="remainSecond" >返回上一页</span></a>
			</td>
          </tr>
        </table>
        <table width="11" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="120">&nbsp;</td>
          </tr>
        </table>
        <table width="70%" height="102" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=mServerUrl%>/collapse[1].gif" width="16" height="16" />
              <font color="#CC0000"><b>
             <%
				int status_code = -1;
				status_code = ((Integer) request.getAttribute("javax.servlet.error.status_code")); 
				System.out.println("--------------------------" + status_code);
				if(404==status_code||400==status_code){
					if (exception==null||exception.getMessage() == null || exception.getMessage().equals("")) {
			%>
				发生404错误：<br>　　这可能所请求的文件不存在原因所导致<br>　　请稍后重试，如仍有问题请联系管理员！
			<%
					} else {
						out.println(exception.getMessage());
					}
			    } else if(500==status_code||505==status_code||503==status_code){
			    	if (exception==null||exception.getMessage() == null || exception.getMessage().equals("")) {
			%>
				发生500错误：<br>　　这可能是服务器程序出错原因所导致<br>　　请稍后重试，如仍有问题请联系管理员！
			<%
					} else {
						if (exception.getMessage().indexOf("NoRightException") > 0) {
							out.println("用户的session已经过期，请重新登陆！");
						} else if (exception.getMessage().indexOf("IdObjectException") > 0) {
							out.println("请求处理的信息对象为空！，请检查该信息的相关信息");
						} else {
							out.println(exception.getMessage());
						}
					}
				} else {
					out.println("未知错误，请联系管理员");
				} 
			%>
            </b></font><br>
            
            </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
