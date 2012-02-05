<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.ecoupons.business.CouponComment,
				 com.ejoysoft.auth.LogonForm" %>
<%@ include file="../include/jsp/head.jsp" %>
<%
    CouponComment obj=new CouponComment(globa); 
    String  strId=ParamUtil.getString(request,"strcouponid","");
    String strUrl="couponinfo.jsp?strid="+strId;
    ServletContext application1 = getServletContext();
    
   
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
	         obj.setStrCreator(globa.loginName);
	         obj.setStrCouponId(strId);
	         obj.setStrMemberCardNo("23423423423423423");
		
	    if(action.equals(Constants.ADD_STR)) { 
	         LogonForm form = new LogonForm(application1, request, response);
		     //判断验证码
			 String rand = (String)request.getSession().getAttribute("rand");
			 String input = request.getParameter("yanzm");
		
	         if(strComment==null||strComment.trim().equals("")){
                globa.closeCon();
                out.print("<script>alert('评价内容为空！评价失败！');window.location.href='javascript:history.go(-1)';</script>");
             }
              else if(!rand.toLowerCase().equals(input.toLowerCase())){
                globa.closeCon();
                out.print("<script>alert('输入验证码错误！评价失败！');window.location.href='javascript:history.go(-1)';</script>");
             }
             else if(rand.toLowerCase().equals(input.toLowerCase())&&form.authenticate()==1){
                globa.closeCon();
			     out.print("<script>alert('"+form.getError()+"');window.location.href='javascript:history.go(-1)';</script>");
             }else{
	              globa.dispatch(obj.addComment(),strUrl);
             }
                  
		}
	}
    //关闭数据库连接对象
    globa.closeCon();
%>