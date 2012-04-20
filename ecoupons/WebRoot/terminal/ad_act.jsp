<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.business.Terminal,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.common.ApacheUpload,com.ejoysoft.common.UID,java.io.File"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	if (action.equals(Constants.DELETE_STR))
	{
		Terminal obj = new Terminal(globa, false);
		String strUrl = "ad_list.jsp";
		String strFilePath = application.getRealPath("") + "/terminal/advertisement/";
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			Terminal obj0 = obj.showAd(" where strId='" + aryStrId[i] + "'");
			if (obj0.getStrContent() != null && obj0.getStrContent().length() > 0)
			{
				String pic[] = obj0.getStrContent().trim().split(",");
				if (pic != null)
				{
					for (int j = 0; j < pic.length; j++)
					{
						System.out.println(pic[j]);
						File f = new File(strFilePath + pic[j]);
						if (f != null)
							f.delete();
					}

				}
			}
			obj.deleteAd(" where strId ='" + aryStrId[i] + "'", aryStrId[i]);
		}
		globa.dispatch(true, strUrl);
	} else
	{
		Terminal obj = new Terminal(globa, false);
		String strUrl = "ad_list.jsp";
		ApacheUpload au = new ApacheUpload(request);
		String strId = au.getString("strId");
		String intType = au.getString("intType");
		action = au.getString(Constants.ACTION_TYPE);
		String filename = UID.getID();
		//上传文件
		String strFilePath = application.getRealPath("") + "/terminal/advertisement/";
		File path = new File(strFilePath);
		if (!path.exists())
		{
			path.mkdirs();
		}
		String strcontent = " ";
		Terminal obj0 = null;
		if (action.equals(Constants.UPDATE_STR))
		{
			obj.setStrId(au.getString("strId"));
			obj0 = obj.showAd(" where strId='" + obj.getStrId() + "'");
		}
		if (intType.trim().equals("1") || intType.trim().equals("2"))
		{
			for (int i = 0; i < au.getFileCount(); i++)
			{
				if (au.getFileName(i).length() > 0)
				{
					if (action.equals(Constants.UPDATE_STR) && obj0.getStrContent() != null && obj0.getStrContent().length() > 0)
					{
						String files[] = obj0.getStrContent().trim().split(",");
						if (files != null)
						{
							for (int j = 0; j < files.length; j++)
							{
								File f = new File(strFilePath + files[j]);
								if (f != null)
									f.delete();
							}
						}
						filename = strId;
					} else if (action.equals(Constants.ADD_STR))
					{
						obj.setStrId(filename);
					}
					break;
				}
			}
			for (int i = 0; i < au.getFileCount(); i++)
			{
				if (au.getFileName(i).length() > 0)
				{
					String name = au.saveFile(strFilePath, filename + "_" + (i + 1), i);
					if (i != au.getFileCount())
						strcontent += name + ",";

				}
			}
			if (strcontent.length() >= 2)
				strcontent = strcontent.trim().substring(0, strcontent.length() - 2);
		} else
		{
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrContent() != null && obj0.getStrContent().length() > 0)
			{
				String files[] = obj0.getStrContent().trim().split(",");
				if (files != null)
				{
					for (int j = 0; j < files.length; j++)
					{
						File f = new File(strFilePath + files[j]);
						if (f != null)
							f.delete();
					}
				}
				filename = strId;
			} else if (action.equals(Constants.ADD_STR))
			{
				obj.setStrId(filename);
			}
			strcontent = au.getString("strContent");
		}

		//赋值		       
		obj.setStrName(au.getString("strName"));
		obj.setIntType(au.getString("intType"));
		obj.setStrContent(strcontent);
		obj.setStrTerminals(au.getString("strTerminals"));
		obj.setDtStartTime(au.getString("dtStartTime"));
		obj.setDtEndTime(au.getString("dtEndTime"));
		obj.setStrCreator(globa.loginName);
		//obj.setStrCreator(globa.userSession.getStrName());
		if (action.equals(Constants.ADD_STR))
		{
			globa.dispatch(obj.addAd(), strUrl);
		} else if (action.equals(Constants.UPDATE_STR))
		{
			globa.dispatch(obj.updateAd(strId), strUrl);
		}

	}
	//关闭数据库连接对象
	globa.closeCon();
%>