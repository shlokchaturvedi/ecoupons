<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    Terminal obj=new Terminal(globa,false);
    String strUrl="terminal_list.jsp";
    String  strId=ParamUtil.getString(request,"strId","");
	         System.out.println(action);
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} else {
	         String strNo=ParamUtil.getString(request,"strNo"," ");
	         String dtActiveTime=ParamUtil.getString(request,"dtActiveTime"," ");
	         String strLocation=ParamUtil.getString(request,"strLocation"," ");
	         String strAroundShops=ParamUtil.getString(request,"strAroundShops"," ");
	         String strProducer=ParamUtil.getString(request,"strProducer"," ");
	         String strType=ParamUtil.getString(request,"strType"," ");
	         String strResolution=ParamUtil.getString(request,"strResolution"," ");
	         String strResolution2=ParamUtil.getString(request,"strResolution2"," ");
	         String strResolution3=ParamUtil.getString(request,"strResolution3"," ");
	         obj.setStrNo(strNo);
	         obj.setDtActiveTime(dtActiveTime);
	         obj.setStrLocation(strLocation);
	         obj.setStrAroundShops(strAroundShops);
	         obj.setStrProducer(strProducer);
	         obj.setStrType(strType);
	         obj.setStrResolution(strResolution);
	         obj.setStrResolution2(strResolution2);
	         obj.setStrResolution3(strResolution3);
	         obj.setIntState(0);
	         obj.setStrCreator(globa.fullRealName);
		
	    if(action.equals(Constants.ADD_STR)) {
             if(obj.getCount(" where strNo='" + strNo + "'")>0){
                globa.closeCon();
                out.print("<script>alert('已经存在"+strNo+"编号的终端机, 请添加其他编号终端机');</script>");
             }else{
               globa.dispatch(obj.add(),strUrl);
             }	    
		}
		else if(action.equals(Constants.UPDATE_STR)){
                 globa.dispatch(obj.update(strId),strUrl);
      }
	}
    //关闭数据库连接对象
    globa.closeCon();
%>