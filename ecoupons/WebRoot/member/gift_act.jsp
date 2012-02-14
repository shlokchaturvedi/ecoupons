<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.ApacheUpload,java.io.File,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Gift"%>
<%@page import="com.ejoysoft.ecoupons.business.GiftExchange"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	Gift obj = new Gift(globa, false);
	String strUrl = "gift_list.jsp";
	String strFilePath = application.getRealPath("") + "/member/images/";
	if (action.equals(Constants.DELETE_STR))
	{
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			Gift obj0 = obj.show(" where strId='" + aryStrId[i] + "'");
			if (obj0.getStrSmallImg() != null && obj0.getStrSmallImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrSmallImg());
				f.delete();
			}
			if (obj0.getStrSmallImg() != null && obj0.getStrLargeImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrLargeImg());
				f.delete();
			}
			obj.delete(aryStrId[i]);
		}
		globa.dispatch(true, strUrl);
	} else if (action.equals(Constants.AUDIT_STR))
	{
		GiftExchange giftExchange = new GiftExchange(globa);
		String strId = ParamUtil.getString(request, "strId", "");
		globa.dispatch(giftExchange.audit(strId), "giftexchange_list.jsp");
	}

	else
	{
		ApacheUpload au = new ApacheUpload(request);
		action = au.getString(Constants.ACTION_TYPE);
		//上传文件

		File path = new File(strFilePath);
		if (!path.exists())
		{
			path.mkdirs();
		}
		String strSmallImg = "", strLargeImg = "";
		Gift obj0 = null;
		if (action.equals(Constants.UPDATE_STR))
		{
			obj.setStrId(au.getString("strId"));
			obj0 = obj.show(" where strId='" + obj.getStrId() + "'");
		}
		if (au.getFileName(0).length() > 0)
		{
			strSmallImg = au.saveFile(strFilePath, 0);
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrSmallImg() != null && obj0.getStrSmallImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrSmallImg());
				f.delete();
			}
		}
		if (au.getFileName(1).length() > 0)
		{
			strLargeImg = au.saveFile(strFilePath, 1);
			if (action.equals(Constants.UPDATE_STR) && obj0.getStrLargeImg() != null && obj0.getStrLargeImg().length() > 0)
			{
				File f = new File(strFilePath + obj0.getStrLargeImg());
				f.delete();
			}
		}
		//赋值
		obj.setDtActiveTime(au.getString("dtActiveTime"));
		obj.setDtExpireTime(au.getString("dtExpireTime"));
		obj.setIntPoint(Integer.parseInt(au.getString("intPoint")));
		obj.setStrIntro(au.getString("strIntro"));
		obj.setStrLargeImg(strLargeImg);
		obj.setStrSmallImg(strSmallImg);
		obj.setStrName(au.getString("strName"));
		obj.setStrAttention(au.getString("strAttention"));
		obj.setFlaPrice(Float.parseFloat(au.getString("flaPrice")));
		obj.setStrCreator(globa.loginName);
		if (action.equals(Constants.ADD_STR))
		{

			globa.dispatch(obj.add(), strUrl);

		} else if (action.equals(Constants.UPDATE_STR))
		{
			String strId = au.getString("strId");
			globa.dispatch(obj.update(strId), strUrl);
		}
	}

	//关闭数据库连接对象
	globa.closeCon();
%>