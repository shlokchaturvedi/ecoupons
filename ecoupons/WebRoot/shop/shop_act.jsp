<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.common.Constants,
				com.ejoysoft.util.ParamUtil,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.common.ApacheUpload,
				java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	if(action.equals(Constants.DELETE_STR)){
		Shop obj=new Shop(globa,false);
	    String strUrl="shop_list.jsp";
	    String strFilePath = application.getRealPath("") + "/shop/images/";
     	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
    		Shop obj0 = obj.show(" where strId='" + aryStrId[i] + "'");
    		if (obj0.getStrSmallImg()!=null&&obj0.getStrSmallImg().length() > 0) {
    			File f = new File(strFilePath + obj0.getStrSmallImg());
        		f.delete();
    		}
    		if (obj0.getStrSmallImg()!=null&&obj0.getStrLargeImg().length() > 0) {
        		File f = new File(strFilePath + obj0.getStrLargeImg());
        		f.delete();
        	}
	    	obj.delete("where strId ='"+aryStrId[i]+"'","where strshopid ='"+aryStrId[i]+"'"); 	
	    	globa.dispatch(true, strUrl);
		} 
	}
	else{
	
	    Shop obj=new Shop(globa,false);	
	    String strUrl="shop_list.jsp";
		ApacheUpload au = new ApacheUpload(request);
		action = au.getString(Constants.ACTION_TYPE);
	 	//上传文件
	    String strFilePath = application.getRealPath("") + "/shop/images/";
	    File path = new File(strFilePath);
	    if (!path.exists()) {
	    	path.mkdirs();
	    }
	    String strSmallImg = "", strLargeImg = "";
	    Shop obj0 = null;
	   if(action.equals(Constants.UPDATE_STR)){
			obj.setStrId(au.getString("strId"));
	    	obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
	    }
	    if (au.getFileName(0).length() > 0) {  
	    	strSmallImg = au.saveFile(strFilePath, 0);
	    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrSmallImg()!=null&&obj0.getStrSmallImg().length() > 0) {
	    		File f = new File(strFilePath + obj0.getStrSmallImg());
	    		f.delete();
	    	}
	    }
	    if (au.getFileName(1).length() > 0) {
	    	strLargeImg = au.saveFile(strFilePath, 1);
	    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrSmallImg()!=null&&obj0.getStrLargeImg().length() > 0) {
	    		File f = new File(strFilePath + obj0.getStrLargeImg());
	    		f.delete();
	    	}
	    }
	    //赋值
	    obj.setStrBizName(au.getString("strBizName"));
	    obj.setStrShopName(au.getString("strShopName"));
	    obj.setStrTrade(au.getString("strTrade"));
	    obj.setStrAddr(au.getString("strAddr"));
	    obj.setStrPhone(au.getString("strPhone"));
	    obj.setStrPerson(au.getString("strPerson"));
	    obj.setStrIntro(au.getString("strIntro"));
	    obj.setStrSmallImg(strSmallImg);
	    obj.setStrLargeImg(strLargeImg);
		obj.setIntPoint(Integer.parseInt(au.getString("intPoint")));
		System.out.println(Integer.parseInt(au.getString("intPoint")));
		obj.setStrCreator(globa.fullRealName);
	   // String  strUnitId=ParamUtil.getString(request,"strUnitId","");
	    if(action.equals(Constants.ADD_STR)) {
		    String strbizname=au.getString("strBizName");
		    String strshopname=au.getString("strShopName");
	        if(obj.getCount(" where strbizname='"+strbizname+"' and strshopname='"+strshopname+"'")>0){
	           globa.closeCon();
	           out.print("<script language='javascript'>alert('已经存在"+strshopname+'-'+strbizname+"商家, 请输入其他商家');window.location.href='javascript:history.go(-1)';</script>");
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