<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.ecoupons.business.CouponFavourite,
				 com.ejoysoft.auth.LogonForm,
				 com.ejoysoft.common.exception.IdObjectException" %>
<%@ include file="../include/jsp/head.jsp" %>
<%
	String membercardno="";  
	if(session.getAttribute(Constants.MEMBER_KEY) == null)
	{
   		globa.closeCon();
	    response.getWriter().println("<script>alert('您还未登录！请先登录！');window.close();</script>");
	}
	else
	{
		membercardno = globa.memberSession.getStrCardNo(); 
		CouponFavourite obj=new CouponFavourite(globa); 
	    String  strId=ParamUtil.getString(request,"strid","");    
		if(strId.equals(""))
		{
			response.getWriter().println("<script>alert('操作失败！');window.close();</script>");
			//throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
		}
		CouponFavourite obj2 = obj.show(" where strmembercardno='"+membercardno+"' and strcouponid='"+strId+"'");
		if(obj2!=null)
		{
			response.getWriter().println("<script>alert('你已经收藏过该优惠券！');window.close();</script>");
		}
		else{
			obj.setStrCreator("system");
		    obj.setStrCouponId(strId);
		    obj.setStrMemberCardNo(membercardno);
		    boolean result = obj.add();
		    if(result)
		    {
		       response.getWriter().println("<script>alert('收藏成功！');window.returnValue='success';window.close();</script>");
		    }
		    else
		    {
		       response.getWriter().println("<script>alert('收藏失败!');window.close();</script>");
		    }	
		}
	    
	    //关闭数据库连接对象
	    globa.closeCon(); 
	}
    
%>