<%@ page contentType="text/html; charset=GBK"%>
<%@ page isErrorPage="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>������Ϣ</title>
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
              <a onclick="history.go(-1)"><span id="remainSecond" >������һҳ</span></a>
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
				����404����<br>�����������������ļ�������ԭ��������<br>�������Ժ����ԣ���������������ϵ����Ա��
			<%
					} else {
						out.println(exception.getMessage());
					}
			    } else if(500==status_code||505==status_code||503==status_code){
			    	if (exception==null||exception.getMessage() == null || exception.getMessage().equals("")) {
			%>
				����500����<br>����������Ƿ������������ԭ��������<br>�������Ժ����ԣ���������������ϵ����Ա��
			<%
					} else {
						if (exception.getMessage().indexOf("NoRightException") > 0) {
							out.println("�û���session�Ѿ����ڣ������µ�½��");
						} else if (exception.getMessage().indexOf("IdObjectException") > 0) {
							out.println("���������Ϣ����Ϊ�գ����������Ϣ�������Ϣ");
						} else {
							out.println(exception.getMessage());
						}
					}
				} else {
					out.println("δ֪��������ϵ����Ա");
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
