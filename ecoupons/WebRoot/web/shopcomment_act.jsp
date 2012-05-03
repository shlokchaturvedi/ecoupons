<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.ecoupons.business.ShopComment,
				 com.ejoysoft.auth.LogonForm,
				 com.ejoysoft.common.exception.IdObjectException" %>
<%@ include file="../include/jsp/head.jsp" %>
<%
	String membercardno="";  
	if(session.getAttribute(Constants.MEMBER_KEY) == null)
	{
   		globa.closeCon();
	    out.print("<script>alert('您还未登录！请先登录！');window.close();</script>");	}
	else
	{
		membercardno = globa.memberSession.getStrCardNo(); 	 
	}
    ShopComment obj=new ShopComment(globa); 
    String  strId=ParamUtil.getString(request,"strshopid","");    
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    String strUrl="merchantsinfo.jsp?strid="+strId;
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	//obj.delete("where strId ='"+aryStrId[i]+"'",aryStrId[i]);
    	}
    	globa.dispatch(true, strUrl);
	} 
	else {
			
         String strComment=ParamUtil.getString(request,"strcomment"," ");	       
         obj.setStrComment(strComment);
         obj.setStrCreator("system");
         obj.setStrShopId(strId);
         obj.setStrMemberCardNo(membercardno);		
	     if(action.equals(Constants.ADD_STR)) { 
	         //判断验证码
			 String rand = (String)request.getSession().getAttribute("rand");
			 String input = request.getParameter("yanzm");
		
	         if(strComment==null||strComment.trim().equals("")){
                globa.closeCon();
                out.print("<script>alert('留言内容为空！留言失败！');window.location.href='javascript:history.go(-1)';</script>");
             }
              else if(!rand.toLowerCase().equals(input.toLowerCase())){
                globa.closeCon();
                out.print("<script>alert('输入验证码错误！留言失败！');window.location.href='javascript:history.go(-1)';</script>");
             }
             else{
	              globa.dispatch(obj.addComment(),strUrl);
             }                  
		}
		else
		{
		}
	}
    //关闭数据库连接对象
    globa.closeCon();
    
%>