<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.common.ApacheUpload,
				 java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    if(action.equals(Constants.DELETE_STR)){
		Terminal obj=new Terminal(globa,false);
	    String strUrl="terminal_list.jsp";
	    String strFilePath = application.getRealPath("") + "/terminal/images/";
     	String  strId=ParamUtil.getString(request,"strId","");
	   	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
    	    Terminal obj0 = obj.show(" where strId='" + aryStrId[i] + "'");
    		if (obj0.getStrImage()!=null&&obj0.getStrImage().length() > 0) {
    			File f = new File(strFilePath + obj0.getStrImage());
        		f.delete();
    		}
	    	obj.delete("where strId ='"+aryStrId[i]+"'",aryStrId[i]);
    	}
    	globa.dispatch(true, strUrl);
	} else {
		Terminal obj=new Terminal(globa,false);
	    String strUrl="terminal_list.jsp";		
	    ApacheUpload au = new ApacheUpload(request);
		action = au.getString(Constants.ACTION_TYPE);
		//上传文件
	    String strFilePath = application.getRealPath("") + "/terminal/images/";
	    File path = new File(strFilePath);
	    if (!path.exists()) {
	    	path.mkdirs();
	    }     
	    String strImage = "";
	    Terminal obj0 = new Terminal();
	   if(action.equals(Constants.UPDATE_STR)){
			obj.setStrId(au.getString("strId"));
	    	obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
	    }
	    if (au.getFileName(0).length() > 0) {  
	    	strImage = au.saveFile(strFilePath, 0);
	    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrImage()!=null&&obj0.getStrImage().length() > 0) {
	    		File f = new File(strFilePath + obj0.getStrImage());
	    		f.delete();
	    	}
	    }
        obj.setStrNo(au.getString("strNo"));
        obj.setDtActiveTime(au.getString("dtActiveTime"));
        obj.setStrLocation(au.getString("strLocation"));
        obj.setStrAroundShops(au.getString("strAroundShops"));
        obj.setStrProducer(au.getString("strProducer"));
        obj.setStrType(au.getString("strType"));
        obj.setStrResolution(au.getString("strResolution"));
        obj.setStrResolution2(au.getString("strResolution2"));
        obj.setStrResolution3(au.getString("strResolution3"));
        obj.setStrImage(strImage);
        obj.setIntState(0);
        obj.setStrCreator(globa.loginName);
		
	    if(action.equals(Constants.ADD_STR)) {
             if(obj.getCount(" where strNo='" + au.getString("strNo") + "'")>0){
                globa.closeCon();
                out.print("<script>alert('已经存在"+au.getString("strNo")+"编号的终端机, 请添加其他编号终端机');window.location.href='javascript:history.go(-1)'</script>");
             }else{
               globa.dispatch(obj.add(),strUrl);
             }	    
		}
		else if(action.equals(Constants.UPDATE_STR)){
				 String strId=au.getString("strId");  	
                 globa.dispatch(obj.update(strId),strUrl);
      }
	}
    //关闭数据库连接对象
    globa.closeCon();
%>