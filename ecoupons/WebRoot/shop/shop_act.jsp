<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.common.Constants,
				com.ejoysoft.util.ParamUtil,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.common.ApacheUpload,
				java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
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
  		System.out.println("2342333333333323333333333333333");
    	strSmallImg = au.saveFile(strFilePath, 0);
    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrSmallImg().length() > 0) {
    		File f = new File(strFilePath + obj0.getStrSmallImg());
    		f.delete();
    	}
    }
    if (au.getFileName(1).length() > 0) {
    	strLargeImg = au.saveFile(strFilePath, 1);
    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrLargeImg().length() > 0) {
    		File f = new File(strFilePath + obj0.getStrLargeImg());
    		f.delete();
    	}
    }
    //赋值
    obj.setStrBizName(au.getString("strName"));
    obj.setStrShopName(au.getString("strShopName"));
    obj.setStrTrade(au.getString("strTrade"));
    obj.setStrAddr(au.getString("strAddr"));
    obj.setStrPhone(au.getString("strPhone"));
    obj.setStrPerson(au.getString("strPerson"));
    obj.setStrIntro(au.getString("strIntro"));
    obj.setStrSmallImg("strSmallImg");
    obj.setStrLargeImg("strLargeImg");
	obj.setIntPoint(Integer.parseInt(au.getString("intPoint")));
	obj.setStrCreator(globa.fullRealName);
   // String  strUnitId=ParamUtil.getString(request,"strUnitId","");
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	}
	 else if(action.equals(Constants.ADD_STR)) {
	    String strId=ParamUtil.getString(request,"strId","");
		String[] arryUnitId=ParamUtil.getStrArray(request,"arryUnitId");
        if(obj.getCount(" where strId='" + strId + "'")>0){
           globa.closeCon();
           out.print("<script>alert('已经存在"+au.getString("strName")+"商家, 请输入其他商家名');</script>");
        }else{                
        	globa.dispatch(obj.add(),"shop_list.jsp");
        }	   
	}
	else if(action.equals(Constants.UPDATE_STR)) {
	    String strId=ParamUtil.getString(request,"strId","");            
        	globa.dispatch(obj.update(strId),strUrl);
        }	   
		
    //关闭数据库连接对象
    globa.closeCon();
%>