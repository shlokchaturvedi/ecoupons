<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@ include file="include/jsp/head.jsp"%>
<%
    Terminal obj=new Terminal(globa,false);
    String  strId=ParamUtil.getString(request,"strId","");
	obj.updatePrintPaperState(strId,1);
	globa.dispatch(obj.updatePrintPaperState(strId,0), "top.jsp");
    //关闭数据库连接对象
    globa.closeCon();
%>