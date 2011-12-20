<%@ page import="com.ejoysoft.ecoupons.system.User"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ejoysoft.common.exception.CommonException"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    String strOldPwd = request.getParameter("strOldPwd");
    String strNewPwd = request.getParameter("strNewPwd");
    if (!globa.userSession.getStrPWD().equals(strOldPwd)) {
    	out.println("<script>alert('原密码不对，请重新输入');history.back();</script>");
    } else {
    	User u = new User(globa, false);
    	u.setStrPWD(strNewPwd);
    	u.doSetPwd(globa.loginName, application, session);
    	out.println("<script>alert('密码修改成功！');history.back();</script>");
    }
    //关闭数据库连接对象
    globa.closeCon();
%>