<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				com.ejoysoft.common.ApacheUpload,
				com.ejoysoft.common.UID,
				java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    Terminal obj=new Terminal(globa,false);
    String strUrl="ad_list.jsp";
    if(action.equals(Constants.DELETE_STR)){
	    String strFilePath = application.getRealPath("") + "/terminal/advertisement/";
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");    	
    	for (int i = 0; i < aryStrId.length; i++) {
    		Terminal obj0 = obj.showAd(" where strId='" + aryStrId[i] + "'");
    		if (obj0.getStrContent()!=null && obj0.getStrContent().length() > 0) 
    		{
	    		String pic[] = obj0.getStrContent().trim().split(",");
	        	if(pic!=null)
	        	{
        			for(int j=0; j< pic.length;j++)
        			{
        			    System.out.println(pic[j]);
	        			File f = new File(strFilePath + pic[j]);
	    			    if(f!=null) 
	        			  f.delete();
        			}
        			
        		}
    		}
    		
	    	obj.deleteAd(" where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} 
	else{
		ApacheUpload au = new ApacheUpload(request);
        String  strId=au.getString("strId"); 
	    String intType=au.getString("intType");
		action = au.getString(Constants.ACTION_TYPE);
	   	String filename = UID.getID();
	 	//上传文件
	    String strFilePath = application.getRealPath("") + "/terminal/advertisement/";
	    File path = new File(strFilePath);
	    if (!path.exists()) {
	    	path.mkdirs();
	    }
	    String strcontent = " ";
	    Terminal obj0 = null;
	   if(action.equals(Constants.UPDATE_STR)){
			obj.setStrId(au.getString("strId"));
	    	obj0 = obj.showAd(" where strId='" + obj.getStrId() + "'");
	    }
	  
	   if(intType.trim().equals("1"))
	   {
	    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrContent()!=null&&obj0.getStrContent().length() > 0) 
	    	{
	    		String pic[] = obj0.getStrContent().trim().split(",");
	        	if(pic!=null)
	        	{
        			for(int j=0; j< pic.length;j++)
        			{
        			    System.out.println(pic[j]);
	        			File f = new File(strFilePath + pic[j]);
	    			    if(f!=null) 
	        			  f.delete();
        			}
        			
        		}
        		filename = strId;
	    	}
	    	else if(action.equals(Constants.ADD_STR) )
	    	{
	    		obj.setStrId(filename);
	    	}
	   		strcontent = au.saveFile2(strFilePath, 0, filename+"_"+intType);
	   }
	    else if(intType.trim().equals("2"))
	   {
	   	  for(int i=0;i<10;i++)
	   	  {
	   	  	if (action.equals(Constants.UPDATE_STR) && obj0.getStrContent()!=null&&obj0.getStrContent().length() > 0) 
	    	{
	    		String pic[] = obj0.getStrContent().trim().split(",");
	        	if(pic!=null)
	        	{
        			for(int j=0; j< pic.length;j++)
        			{
        			    System.out.println(pic[j]);
	        			File f = new File(strFilePath + pic[j]);
	    			    if(f!=null) 
	        			  f.delete();
        			}
        			
        		}
        		filename = strId;
	    	}
	    	else if(action.equals(Constants.ADD_STR) )
	    	{
	    		obj.setStrId(filename);
	    	}
	    	String name = au.saveFile2(strFilePath, i, filename+"_"+intType+(i+1));
	    	if(name!=null&& !(name.trim().equals("")))
	   			strcontent += name+",";
	   	  }
	   	  if(strcontent.length()>1)
	   	    strcontent = strcontent.substring(1,strcontent.length()-1);
	   }
	   else
	   {
	      obj.setStrId(filename);
	      strcontent=au.getString("strContent");	   
	   }
	    
	    //赋值	    
         obj.setStrName(au.getString("strName"));
         obj.setIntType(au.getString("intType"));
         obj.setStrContent(strcontent);
         obj.setStrTerminals(au.getString("strTerminals"));
         obj.setDtStartTime(au.getString("dtStartTime"));
         obj.setDtEndTime(au.getString("dtEndTime"));
         obj.setStrCreator(globa.loginName);
		 obj.setStrCreator(globa.fullRealName);
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