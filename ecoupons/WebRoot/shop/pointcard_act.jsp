<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.business.PointCard"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	PointCard obj = new PointCard(globa, true);
	String strUrl = "pointcard_list.jsp";

	if (action.equals(Constants.DELETE_STR))
	{
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} else if (action.equals(Constants.UPDATE_STR))
	{
		String strId = ParamUtil.getString(request, "strId", "");
		globa.dispatch(obj.update(strId), strUrl);

	} else if (action.equals(Constants.ADD_STR))
	{
		String strPointCardNo = ParamUtil.getString(request, "strPointCardNo", "");

		if (obj.getCount(" where strPointCardNo='" + strPointCardNo + "'") > 0)
		{
			globa.closeCon();
			out.print("<script>alert('已经存在" + strPointCardNo + "纸质积分卡, 请输入其他纸质积分卡');</script>");
		} else
		{

			globa.dispatch(obj.add(), strUrl);
		}
	} 
	//关闭数据库连接对象
	globa.closeCon();
%>