<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.TerminalPara,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@page import="com.ejoysoft.ecoupons.business.ShopComment"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    ShopComment obj=new ShopComment(globa,false);
    String strUrl="shopcomment_list.jsp";
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