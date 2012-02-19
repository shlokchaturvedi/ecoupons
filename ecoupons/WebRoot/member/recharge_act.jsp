<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Recharge obj = new Recharge(globa, true);
	String  strMemberCardNo=ParamUtil.getString(request,"strMemberCardNo","");
		String strId = ParamUtil.getString(request, "strId", "");
	String strUrl = "recharge_list.jsp?strMemberCardNo="+strMemberCardNo+"&strId="+strId;
	if (action.equals(Constants.UPDATE_STR))
	{
		int intMoney = Integer.parseInt(ParamUtil.getString(request, "intMoney", ""));
		if (obj.update(intMoney))
		{
			globa.dispatch(true, strUrl);
		} else
		{
			globa.dispatch(false, strUrl,"修改出现错误：请检查减少金额是否大于该卡余额,修改");
		}

	} else if (action.equals(Constants.ADD_STR))
	{
		String intMoney = ParamUtil.getString(request, "intMoney", "");
		globa.dispatch(obj.add(intMoney), strUrl);

	}
	//关闭数据库连接对象
	globa.closeCon();
%>