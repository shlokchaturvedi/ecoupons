<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    Terminal obj=new Terminal(globa,false);
    String strUrl="ad_list.jsp";
    String  strId=ParamUtil.getString(request,"strId","");
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.deleteAd("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} else {
	         String strName=ParamUtil.getString(request,"strName"," ");
	         String intType=ParamUtil.getString(request,"intType"," ");
	         String strContent=ParamUtil.getString(request,"strContent"," ");
	         String strTerminals=ParamUtil.getString(request,"strTerminals"," ");
	         String dtStartTime=ParamUtil.getString(request,"dtStartTime"," ");
	         String dtEndTime=ParamUtil.getString(request,"dtEndTime"," ");
	         obj.setStrName(strName);
	         obj.setIntType(intType);
	         obj.setStrContent(strContent);
	         obj.setStrTerminals(strTerminals);
	         obj.setDtStartTime(dtStartTime);
	         obj.setDtEndTime(dtEndTime);
	         obj.setStrCreator(globa.loginName);
		
	    if(action.equals(Constants.ADD_STR)) {
             globa.dispatch(obj.addAd(),strUrl);                
		}
		else if(action.equals(Constants.UPDATE_STR)){
                 globa.dispatch(obj.updateAd(strId),strUrl);
      }
	}
    //关闭数据库连接对象
    globa.closeCon();
%>