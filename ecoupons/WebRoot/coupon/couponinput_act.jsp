<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	CouponInput obj = new CouponInput(globa, true);
	String strUrl = "couponinput_list.jsp";
	if (action.equals(Constants.DELETE_STR))
	{
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			obj.delete("where strId ='" + aryStrId[i] + "'");
		}
		globa.dispatch(true, strUrl);
	} else
	{
		if (action.equals(Constants.ADD_STR))
		{
			Shop shop=new Shop();
			String strCouponCode = ParamUtil.getString(request, "strCouponCode", "");
			String strMemberCardNo = ParamUtil.getString(request, "strMemberCardNo", "");
			CouponPrint couponPrint = new CouponPrint(globa, true);
			if (couponPrint.getCount("where strCouponCode='" + strCouponCode + "' and strMemberCardNo='"+strMemberCardNo+"'") > 0)
			{
				globa.dispatch(obj.add(), strUrl);
			} else
			{
				out.print("<script>alert('录入错误：券面代码" + strCouponCode + "无效或者已经录入, 请输入其他有价券！');</script>");
				globa.dispatch(false, strUrl, "录入错误：券面代码" + strCouponCode + "无效或者已经录入, 请输入其他有价券！");
				globa.closeCon();
			}
		} else if (action.equals(Constants.UPDATE_STR))
		{

			globa.dispatch(obj.update(ParamUtil.getString(request, "strId", "")), strUrl);
		}
	}
	//关闭数据库连接对象
	globa.closeCon();
%>