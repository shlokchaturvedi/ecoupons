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
		if (au.getFileName(0).length()>0 && !au.isPic(au.getFileExpName(0)))
		{
			globa.dispatch(false, strUrl, "请选择正确的图片格式如.jpg/.png/.gif,操作");
			return;
		}
		action = au.getString(Constants.ACTION_TYPE);
		String filename = UID.getID();
		//上传文件
		String strFilePath = application.getRealPath("") + "/terminal/template/";
		File path = new File(strFilePath);
		if (!path.exists())
		{
			path.mkdirs();
		}
		String strBgImage = " ";
		TerminalTemplate obj0 = null;
		if (action.equals(Constants.UPDATE_STR))
		{
			obj.setStrId(au.getString("strId"));
			obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
		}
		String strTempId=UID.getID();
	    if (au.getFileName(0).length() > 0) {  
		   strBgImage =au.saveFile(strFilePath,strTempId, 0);
	    	//strSmallImg = au.saveFile(strFilePath, 0);
	    	if (action.equals(Constants.UPDATE_STR) && obj0.getStrBgImage()!=null&&obj0.getStrBgImage().length() > 0) {
	    		File f = new File(strFilePath + obj0.getStrBgImage());
	    		f.delete();
	    	}
	    }
		//赋值		       
		obj.setStrName(au.getString("strName").trim());
		obj.setStrLocation(au.getString("strLocationX").trim()+","+au.getString("strLocationY").trim());
		obj.setStrModuleOfTempl(au.getString("strModuleOfTempl").trim());
		obj.setStrSize(au.getString("strSizeW").trim()+","+au.getString("strSizeH").trim());
		obj.setStrBgImage(strBgImage);
		obj.setStrContent(au.getString("strContent").trim());
		obj.setStrFontFamily(au.getString("strFontFamily").trim());
		obj.setStrFontColor(au.getString("strFontColor").trim());		
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
			globa.dispatch(obj.add(), strUrl);
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