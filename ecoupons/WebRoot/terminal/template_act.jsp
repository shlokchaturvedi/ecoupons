<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.business.TerminalTemplate,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.common.ApacheUpload,com.ejoysoft.common.UID,java.io.File"
%>
<%@ include file="../include/jsp/head.jsp"%>
<%
try{
	if (action.equals(Constants.DELETE_STR))
	{
		TerminalTemplate obj = new TerminalTemplate(globa, false);
		String strUrl = "terminal_template.jsp";
		String strFilePath = application.getRealPath("") + "/terminal/template/";
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			TerminalTemplate obj0 = obj.show(" where strId='" + aryStrId[i] + "'");
			if (obj0.getStrBgImage() != null && obj0.getStrBgImage().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrBgImage());
				if (f != null)
					f.delete();				
			}
			obj.delete(" where strId ='" + aryStrId[i] + "'");
		}
		globa.dispatch(true, strUrl);
	} else
	{
		TerminalTemplate obj = new TerminalTemplate(globa, false);
		String strUrl = "terminal_template.jsp";
		ApacheUpload au = new ApacheUpload(request);
		String strId = au.getString("strId");		
		action = au.getString(Constants.ACTION_TYPE);	
		if (au.getFileName(0).length()>0 && !au.isPic(au.getFileExpName(0)))
		{
			globa.dispatch(false, strUrl, "请选择正确的图片格式如.jpg/.png/.gif,操作");
			return;
		}	 
		String filename = UID.getID();
		String diskname = "";
		String modulename = au.getString("strModuleOfTempl").trim(); 
		//上传文件
//		String strFilePath = application.getRealPath("") + "/terminal/template/"+diskname;
		String strFilePath = application.getRealPath("") + "/terminal/template/";
		File path = new File(strFilePath);
		if (!path.exists())
		{
			path.mkdirs();
		}
		String strBgImage = "";
		TerminalTemplate obj0 = null;
		if (action.equals(Constants.UPDATE_STR))
		{
			obj.setStrId(au.getString("strId"));
			obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
		}
		String strTempId=UID.getID();
	    if (au.getFileName(0).length()> 0) {
		
	       if (action.equals(Constants.UPDATE_STR) && obj0.getStrBgImage()!=null&&!obj0.getStrBgImage().trim().equals("")&&obj0.getStrBgImage().length() > 0) {
	    		File f = new File(strFilePath + obj0.getStrBgImage());
	    		f.delete();
		       //String name = obj0.getStrBgImage();
		       //filename = name.substring(0,name.lastIndexOf("."));
		       filename =String.valueOf( obj0.getIntSort());
		       System.out.println(filename);
	       } 
		   strBgImage =au.saveFile(strFilePath,filename, 0);
	      
	    }
		//赋值		        
		obj.setStrName(au.getString("strName").trim());
		if(au.getString("strLocationX")!=null && au.getString("strLocationY")!=null && !au.getString("strLocationX").trim().equals("")&& !au.getString("strLocationY").trim().equals(""))
		{
			obj.setStrLocation(au.getString("strLocationX").trim()+","+au.getString("strLocationY").trim());
		}
		else{
			obj.setStrLocation("");
		}
		obj.setStrModuleOfTempl(au.getString("strModuleOfTempl").trim());
		if(au.getString("strSizeW")!=null && au.getString("strSizeH")!=null && !au.getString("strSizeW").trim().equals("")&& !au.getString("strSizeH").trim().equals(""))
		{
			obj.setStrSize(au.getString("strSizeW").trim()+","+au.getString("strSizeH").trim());
		}else{
			obj.setStrSize("");
		}
		obj.setStrBgImage(strBgImage);
		obj.setStrContent(au.getString("strContent").trim());
		obj.setStrFontFamily(au.getString("strFontFamily").trim());
		if(au.getString("strFontColorR")!=null && au.getString("strFontColorG")!=null && au.getString("strFontColorB")!=null && !au.getString("strFontColorR").trim().equals("")&& !au.getString("strFontColorG").trim().equals("")&& !au.getString("strFontColorB").trim().equals(""))
		{
			obj.setStrFontColor(au.getString("strFontColorR").trim()+","+au.getString("strFontColorG").trim()+","+au.getString("strFontColorB").trim());
		}else{
			obj.setStrFontColor("");
		}	
		obj.setStrIntro(au.getString("strIntro").trim());		 
		if(au.getString("intFontSize")!=null && !au.getString("intFontSize").trim().equals(""))
		{
			obj.setIntFontSize(Integer.parseInt(au.getString("intFontSize").trim()));
		}
		else 
		{
			obj.setIntFontSize(0);
		}
		obj.setStrCreator(globa.loginName);
		if (action.equals(Constants.ADD_STR))
		{
			if(obj.getCount(" where strmoduleoftempl='" + au.getString("strModuleOfTempl").trim() + "' and strName='"+ au.getString("strName").trim()+"'")>0){
       		    globa.closeCon();
                out.print("<script>alert('模块‘"+au.getString("strModuleOfTempl")+"’以及存在名称为‘"+au.getString("strName")+"’的元素, 请添加其他元素');window.location.href='javascript:history.go(-1);'</script>");
             }else
             {
				globa.dispatch(obj.add(), strUrl);
             }
		} else if (action.equals(Constants.UPDATE_STR))
		{
			globa.dispatch(obj.update(strId), strUrl);
		}

	}
	//关闭数据库连接对象
	globa.closeCon();
	}catch(Exception e)
	{e.printStackTrace();
	}
%>