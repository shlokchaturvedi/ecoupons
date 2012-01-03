<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    CouponInput obj=new CouponInput(globa,true);
    String strUrl="couponinput_list.jsp";
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} else {
	    if(action.equals(Constants.ADD_STR)) {
	    	 String  strCouponCode=ParamUtil.getString(request,"strCouponCode","");
	    	 String  dtPrintTime=ParamUtil.getString(request,"dtPrintTime","");
	    	CouponPrint couponPrint=new CouponPrint(globa,true);
             if(!couponPrint.isEffective(dtPrintTime,strCouponCode)){
                globa.closeCon();
                out.print("<script>alert('录入错误：券面代码"+strCouponCode+"无效或者已经录入, 请输入其他有价券！');</script>");
                globa.dispatch(false,strUrl,"录入错误：券面代码"+strCouponCode+"无效或者已经录入, 请输入其他有价券！");
             }else{
            globa.dispatch(obj.add(),strUrl);
             }	    
		}
		else if(action.equals(Constants.UPDATE_STR)){
			
                 globa.dispatch(obj.update(ParamUtil.getString(request,"strId","")),strUrl);
      }
	}
    //关闭数据库连接对象
    globa.closeCon();
%>