<%@ page import="com.ejoysoft.common.exception.NoAuthException"%>
<%@page contentType="text/html;charset=GBK"%>
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
<%
    NoAuthException ae = (NoAuthException) exception;
%>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="550" border="0" align="center"style="margin-top:23px;" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="332" valign="top" background="<%=mServerUrl %>/in[k]_r2_c2.jpg">
        <table width="90%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="25" align="right" class="errortop"><a onclick="history.back()"><span id="remainSecond" >������һҳ</span></a>
			<script language="javascript">
		       //var speed = 1000; //�ٶ�
		       //var wait = 6; //ʱ��
		       //function updateinfo(){
		          //if(wait == 0){  
		         //      history.go(-1);
		         // }
		         // else{
		             //document.getElementById("remainSecond").innerHTML= "����[<font color='red'>"+wait+"</font>]�뽫�Զ�������һҳ!";
		             //wait--;
		             //window.setTimeout("updateinfo()",speed);
		          //}
		      //}
		      //updateinfo();
			</script>
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
            <td width="10%"><div align="center"><img src="../images/collapse[1].gif" width="16" height="16" /></div></td>
            <td width="90%">
            <br>
              <font color="#CC0000">Ȩ�޴�����ҳ��<br>
			<br>��<b>���棺</b><br>
                        <%=ae.getMessage()%> <br>
                        <b>���飺</b><br>
                        <%
                            if (ae.getAdvice() != null) {
                                out.print(ae.getAdvice());
                            } else {
                                out.print("����ϵϵͳ����Ա");
                            }
                        %></font><br>
            </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
