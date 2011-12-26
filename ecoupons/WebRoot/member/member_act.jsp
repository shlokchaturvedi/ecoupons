<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.system.Member"%>
<%@page import="com.ejoysoft.ecoupons.system.Recharge"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    Recharge recharge=new Recharge();
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
		String strId=ParamUtil.getString(request, "strId", "");
		globa.dispatch(obj.setAudit(strId), strUrl);

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
	}
	//关闭数据库连接对象
	globa.closeCon();
%>