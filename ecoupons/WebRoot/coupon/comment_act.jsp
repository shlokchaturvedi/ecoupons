<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.TerminalPara,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponComment"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    CouponComment obj=new CouponComment(globa,false);
    String strUrl="comment_list.jsp";
    String  strId=ParamUtil.getString(request,"strId","");
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete(" where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} 
    //关闭数据库连接对象
    globa.closeCon();
%>