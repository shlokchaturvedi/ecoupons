<%@ page import="com.ejoysoft.util.ParamUtil"%>
<jsp:useBean id="globa" scope="page" class="com.ejoysoft.common.Globa" />
<%
    //初始系统变量
    globa.initialize(application,request,response); 
    //获取请求操作的ACTION
    String action=ParamUtil.getAction(request);
%>