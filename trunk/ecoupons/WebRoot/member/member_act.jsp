<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@page import="java.util.Vector"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Recharge recharge = new Recharge();
	Member obj = new Member(globa, true);
	String strUrl = "member_list.jsp";

	if (action.equals(Constants.DELETE_STR))
	{

		String[] aryStrId = ParamUtil.getStrArray(request, "strId");

		for (int i = 0; i < aryStrId.length; i++)
		{
			obj.delete("where strId ='" + aryStrId[i] + "'");

		}
		globa.dispatch(true, strUrl);
	} else if (action.equals(Constants.UPDATE_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");

		globa.dispatch(obj.update(strId), strUrl);

	} else if (action.equals(Constants.AUDIT_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");
		globa.dispatch(obj.setAudit(strId), strUrl);

	} else if (action.equals(Constants.ADD_STR))
	{
		String strCardNo = ParamUtil.getString(request, "strCardNo", "");
		String strMobileNo = ParamUtil.getString(request, "strMobileNo", "");
		if (strMobileNo != null&&!strMobileNo.equals(""))
		{

			if (obj.getCount(" where strcardno='" + strCardNo + "' or strmobileno='" + strMobileNo + "'") > 0)
			{
				globa.dispatch(false, strUrl, "会员卡号或者手机号已经存在, 请输入其他会员卡号或手机号，增加");
			} else
			{
				globa.dispatch(obj.add(), strUrl);
			}
		} else
		{
			if (obj.getCount(" where strcardno='"+strCardNo+"'") > 0)
			{
				globa.dispatch(false, strUrl, "会员卡号或者手机号已经存在, 请输入其他会员卡号或手机号，增加");
			} else
			{
				globa.dispatch(obj.add(), strUrl);
			}

		}
	} else if (action.equals(Constants.EXPORT_STR))
	{
		String strStartId = ParamUtil.getString(request, "strStartId", "");
		String strEndId = ParamUtil.getString(request, "strEndId", "");

	}
	//关闭数据库连接对象
	globa.closeCon();
%>