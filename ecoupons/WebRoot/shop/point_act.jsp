<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.business.Point"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Point obj = new Point(globa, true);
	String strUrl = "pointbuy_list.jsp";

	if (action.equals(Constants.DELETE_STR))
	{

		String strId = ParamUtil.getString(request, "strId", "");
		String intPoint = ParamUtil.getString(request, "intPoint", "");
		globa.dispatch(obj.delete(strId,intPoint), strUrl);
	} else if (action.equals(Constants.UPDATE_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");

		globa.dispatch(obj.update(strId), strUrl);

	} else if (action.equals(Constants.AUDIT_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");
		globa.dispatch(obj.setAudit(), strUrl);

	} else if (action.equals(Constants.ADD_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");

		if (obj.getCount(" where strId='" + strId + "'") > 0)
		{
			globa.closeCon();
			out.print("<script>alert('已经存在" + strId + "用户, 请输入其他用户名');</script>");
		} else
		{

			globa.dispatch(obj.add(), strUrl);
		}
	} else if (action.equals(Constants.EXPORT_STR))
	{
		String strStartId = ParamUtil.getString(request, "strStartId", "");
		String strEndId = ParamUtil.getString(request, "strEndId", "");

	}
	//关闭数据库连接对象
	globa.closeCon();
%>