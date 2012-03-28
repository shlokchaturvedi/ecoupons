<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.ApacheUpload,java.io.File,com.ejoysoft.common.*
,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Gift"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Coupon obj = new Coupon(globa, false);
	String strUrl = "coupon_list.jsp";
	
	String strFilePath = application.getRealPath("") + "/coupon/images/";
	if (action.equals(Constants.DELETE_STR))
	{
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			Coupon obj0 = obj.show(" where strId='" + aryStrId[i] + "'");
    		if (obj0.getStrSmallImg()!=null&&obj0.getStrSmallImg().length() > 0) {
    			File f = new File(strFilePath + obj0.getStrSmallImg());
        		f.delete();
    		}
    		if (obj0.getStrLargeImg()!=null&&obj0.getStrLargeImg().length() > 0) {
        		File f = new File(strFilePath + obj0.getStrLargeImg());
        		f.delete();
        	}
    		if (obj0.getStrPrintImg()!=null&&obj0.getStrPrintImg().length() > 0) {
        		File f = new File(strFilePath + obj0.getStrPrintImg());
        		f.delete();
        	}
    		
			obj.delete(aryStrId[i]);
		}
		globa.dispatch(true, strUrl);
	} else
	{
		//String filename = UID.getID();
		ApacheUpload au = new ApacheUpload(request);
		action = au.getString(Constants.ACTION_TYPE);
		 String  strId=au.getString("strId"); 
		//上传文件
		File path = new File(strFilePath);
		String strTempFileName;
		if (!path.exists())
		{
			path.mkdirs();
		}
		String strSmallImg = "", strLargeImg = "", strPrintImg = "";
		Coupon obj0 = null;
		if (action.equals(Constants.UPDATE_STR))
		{
			
			obj.setStrId(au.getString("strId"));
			obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
		}else if(action.equals(Constants.ADD_STR)){
			strId=UID.getID();
			
		}
	    String strTempId=UID.getID();
		if (au.getFileName(0).length() > 0)
		{
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrSmallImg() != null && obj0.getStrSmallImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrSmallImg());
				f.delete();
			}
			strSmallImg =au.saveFile(strFilePath,strTempId+"_"+"2", 0);
		}
		if (au.getFileName(1).length() > 0)
		{
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrLargeImg()!= null && obj0.getStrLargeImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrLargeImg());
				f.delete();
				
			}
			strLargeImg =au.saveFile(strFilePath,strTempId+"_"+"1", 1);
		}
		
		if (au.getFileName(2).length() > 0)
		{
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrPrintImg()!= null && obj0.getStrPrintImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrPrintImg());
				f.delete();
				
			}
			strPrintImg =au.saveFile(strFilePath,strTempId+"_"+"3", 2);
		}
		
		//赋值
		obj.setStrId(strId);
		obj.setDtActiveTime(au.getString("dtActiveTime"));
		obj.setDtExpireTime(au.getString("dtExpireTime"));
		obj.setFlaPrice(Float.parseFloat(au.getString("flaPrice")));
		obj.setIntPrintLimit(au.getInt("intPrintLimit"));
	    obj.setStrLargeImg(strLargeImg);
	    obj.setStrSmallImg(strSmallImg);
	    obj.setStrPrintImg(strPrintImg);
	   obj.setStrInstruction(au.getString("strInstruction"));
	   obj.setStrIntro(au.getString("strIntro"));
	    obj.setStrName(au.getString("strName"));
	    obj.setStrShopId(au.getString("strShopId"));
	    obj.setStrTerminals(au.getString("strTerminals"));
	    obj.setIntRecommend(au.getInt("intRecommend"));
	    obj.setIntVip(au.getInt("intVip"));
	    obj.setStrName(au.getString("strName"));
		if (action.equals(Constants.ADD_STR))
		{
			
			globa.dispatch(obj.add(), strUrl);

		} else if (action.equals(Constants.UPDATE_STR))
		{
			strId = au.getString("strId");
			globa.dispatch(obj.update(strId), strUrl);
		}
	}

	//关闭数据库连接对象
	globa.closeCon();
%>