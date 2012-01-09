<%@ page contentType="text/html;charset=GBK" isErrorPage="true" %>
<jsp:useBean id="globa" class="com.ejoysoft.common.Globa" />

<%
	//初始系统变量
    globa.initialize(application,request,response);
 %>

<title>错误</title>

<body>

       <%
           if(exception!=null||((exception=(Throwable)request.getAttribute("com.framwork.action.ERROR"))!=null)){

           if(exception instanceof com.ejoysoft.common.exception.NoAuthException){%>

          <font size="2">
          <br> <li>登录失败!　<a href=javascript:top.location="../login.jsp">请返回</a>。
          <br> <li><%=exception.toString()%>
          </font>

        <%} if(exception instanceof java.lang.NullPointerException){%>

          <font size="2">
          <br> <li>对不起,发生操作异常
          <br> <li><%=exception.toString()%>
          </font>


	   <%}

       }    else if(globa.loginName==null){%>
       <font size="2">
      <br> <li>对不起，您的请求不能被处理或登录已经失效。
      <br> <li>请<a href=javascript:top.location="../login.jsp">重新登录</a>或<a href="javascript:window.history.back()">返回</a>。

     <%
          } else{
     %>
        <font size="2">
      <br> <li>对不起，您没有权限进入该页面。。
      <br> <li>请<a href=javascript:top.location="../login.jsp">重新登录</a>或<a href="javascript:window.history.back()">返回</a>。
     <%}%>
</body>