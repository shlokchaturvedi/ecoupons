<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.auth.MD5"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strName = ParamUtil.getString(request, "strName");
	String strPhone = ParamUtil.getString(request, "strPhone");
	if (session.getAttribute(Constants.MEMBER_KEY) == null && globa.memberSession==null) {
%>
<script type="text/javascript">
alert("您还未登录！请先登录！！！");
history.back();
</script>
<%
		return;
	}
	globa.db.executeUpdate("update t_bz_member set strname='" +strName + "',strmobileno='"+strPhone+"' where strid='"+globa.memberSession.getStrId()+"'");
   	session.setAttribute(Constants.MEMBER_KEY, null);
    //关闭数据库连接对象
    globa.closeCon();
%>
<script type="text/javascript">
alert("个人信息修改成功，请重新登录使之生效！");
window.location.href="index.jsp";
</script>