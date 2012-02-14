<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.common.Constants,
				com.ejoysoft.util.ParamUtil,
				com.ejoysoft.ecoupons.business.Activity,
				com.ejoysoft.common.ApacheUpload,
				java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	if(action.equals(Constants.DELETE_STR)){
		Activity obj=new Activity(globa,false);
	    String strUrl="activity_list.jsp";
     	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
    		obj.delete("where strId ='"+aryStrId[i]+"'","where strshopid ='"+aryStrId[i]+"'",aryStrId[i]); 	
	    	globa.dispatch(true, strUrl);
		} 
	}
	else{	
	    Activity obj=new Activity(globa,false);	
	    String strUrl="activity_list.jsp";
		ApacheUpload au = new ApacheUpload(request);
		action = au.getString(Constants.ACTION_TYPE);	 
	    //赋值
	    obj.setStrName(au.getString("strName"));
	    obj.setStrContent(au.getString("strContent"));
	    obj.setDtActiveTime(au.getString("dtActiveTime"));
		obj.setStrCreator(globa.loginName);
	    if(action.equals(Constants.ADD_STR)) {
		    String strName=au.getString("strName");
	        if(obj.getCount(" where strname='"+strName+"'")>0){
	           globa.closeCon();
	           out.print("<script language='javascript'>alert('已经存在"+strName+"活动, 请输入其他活动名');window.location.href='javascript:history.go(-1)';</script>");
	        }else{                
	        	globa.dispatch(obj.add(),strUrl);
	        }	   
		}
		else if(action.equals(Constants.UPDATE_STR)) {
		        String strId=au.getString("strId");            
	        	globa.dispatch(obj.update(strId),strUrl);
	        }	
   }   
		
    //关闭数据库连接对象
    globa.closeCon();
%>