<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<jsp:useBean id="globa" class="com.ejoysoft.common.Globa" />

<%
	//��ʼϵͳ����
    globa.initialize(application,request,response);
 %>

<title>����</title>

<body>

       <%
           if(exception!=null||((exception=(Throwable)request.getAttribute("com.framwork.action.ERROR"))!=null)){

           if(exception instanceof com.ejoysoft.common.exception.NoAuthException){%>

          <font size="2">
          <br> <li>��¼ʧ��!��<a href=javascript:top.location="../login.jsp">�뷵��</a>��
          <br> <li><%=exception.toString()%>
          </font>

        <%} if(exception instanceof java.lang.NullPointerException){%>

          <font size="2">
          <br> <li>�Բ���,���������쳣
          <br> <li><%=exception.toString()%>
          </font>


	   <%}

       }    else if(globa.loginName==null){%>
       <font size="2">
      <br> <li>�Բ������������ܱ�������¼�Ѿ�ʧЧ��
      <br> <li>��<a href=javascript:top.location="../login.jsp">���µ�¼</a>��<a href="javascript:window.history.back()">����</a>��

     <%
          } else{
     %>
        <font size="2">
      <br> <li>�Բ�����û��Ȩ�޽����ҳ�档��
      <br> <li>��<a href=javascript:top.location="../login.jsp">���µ�¼</a>��<a href="javascript:window.history.back()">����</a>��
     <%}%>
</body>