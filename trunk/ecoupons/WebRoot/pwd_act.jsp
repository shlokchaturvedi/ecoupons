<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.auth.MD5"%>
<%@ include file="include/jsp/head.jsp"%>
<%
	String strOldPwd = ParamUtil.getString(request, "strOldPwd");
	String strPwd = ParamUtil.getString(request, "strPwd");
	if (!globa.userSession.getStrPWD().equals(strOldPwd)) {
%>
<<script type="text/javascript">
alert("原密码输入错误，请重试！");
history.back();
</script>
<%
		return;
	}
	globa.db.executeUpdate("UPDATE t_sy_user SET strPWD='" + MD5.getMD5ofString(strPwd) + "' where struserid='"+globa.userSession.getStrUserId()+"'");
    //关闭数据库连接对象
    globa.closeCon();
%>
<script type="text/javascript">
alert("密码修改成功，请重新登录！");
top.location.href="/ecoupons";
</script>