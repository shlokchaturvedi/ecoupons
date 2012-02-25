<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.auth.MD5"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strOldPwd = ParamUtil.getString(request, "strPwd");
	String strPwd = ParamUtil.getString(request, "newPwd");
	if (!globa.memberSession.getStrPWD().equals(strOldPwd)) {
%>
<script type="text/javascript">
alert("原密码输入错误，请重试！");
history.back();
</script>
<%
		return;
	}
	globa.db.executeUpdate("update t_bz_member set strpwd='" + MD5.getMD5ofString(strPwd) + "' where strid='"+globa.memberSession.getStrId()+"'");
   	session.setAttribute(Constants.MEMBER_KEY, null);
    //关闭数据库连接对象
    globa.closeCon();
%>
<script type="text/javascript">
alert("密码修改成功，请重新登录！");
window.location.href="index.jsp";
</script>