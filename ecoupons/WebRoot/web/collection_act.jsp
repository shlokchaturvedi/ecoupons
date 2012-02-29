<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponFavourite"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	CouponFavourite obj = new CouponFavourite(globa, true);
	String strUrl = "collection.jsp";

	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
	for (int i = 0; i < aryStrId.length; i++)
	{
		CouponFavourite cf = obj.show("where strId ='" + aryStrId[i] + "'");

		obj.delete("where strcouponid='" + cf.getStrCouponId() + "' and strmembercardno='" + cf.getStrMemberCardNo() + "'");
	}
	globa.dispatch(true, strUrl);

	//关闭数据库连接对象
	globa.closeCon();
%>