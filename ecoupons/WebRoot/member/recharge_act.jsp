<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Recharge obj = new Recharge(globa, true);
	String strUrl = "recharge_list.jsp";

	if (action.equals(Constants.UPDATE_STR))
	{
		int intMoney=Integer.parseInt(ParamUtil.getString(request, "intMoney", ""));
		System.out.println(intMoney+"--8978778899999");
		String strId = ParamUtil.getString(request, "strId", "");
		if (obj.update(intMoney))
		{
	globa.dispatch(true, strUrl);
		}else{
	out.print("<script>alert('修改出现错误：请检查减少金额是否大于该卡余额！！');</script>");
	globa.dispatch(true, strUrl);
		}

	} else if (action.equals(Constants.ADD_STR))
	{
		String intMoney = ParamUtil.getString(request, "intMoney", "");
		globa.dispatch(obj.add(intMoney), strUrl);

	}
	//关闭数据库连接对象
	globa.closeCon();
%>