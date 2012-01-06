<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.business.Point"%>
<%@page import="com.ejoysoft.ecoupons.business.PointPresent"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Point obj = new Point(globa, true);
	Member member = new Member(globa, true);
	String strUrl = "pointbuy_list.jsp";
	String strPresentUrl = "pointpresent_list.jsp";

	if (action.equals(Constants.DELETE_STR))
	{

		String strId = ParamUtil.getString(request, "strId", "");
		String intPoint = ParamUtil.getString(request, "intPoint", "");
		globa.dispatch(obj.delete(strId, intPoint), strUrl);
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

	} else if (action.equals(Constants.PRESENT_STR))
	{
		PointPresent pointPresent = new PointPresent(globa, true);
		String strId = ParamUtil.getString(request, "strId", "");
		String strCardNo = ParamUtil.getString(request, "strMemberCardNo", "");
		if (member.getCount(" where strCardNo='" + strCardNo + "'") == 0)
		{
			
			out.print("<script>alert('不存在会员卡号：" + strCardNo + ", 请输入其他会员卡号');</script>");
			globa.dispatch(false, strPresentUrl,"不存在会员卡号："+ strCardNo + ", 请输入其他会员卡号,转赠");
		} else
		{
			globa.dispatch(pointPresent.add(), strPresentUrl);
		}

	}
	//关闭数据库连接对象
	globa.closeCon();
%>