<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.ecoupons.business.CouponFavourite,
				 com.ejoysoft.auth.LogonForm,
				 com.ejoysoft.common.exception.IdObjectException" %>
<%@ include file="../include/jsp/head.jsp" %>
<%
	String membercardno = "111";
	 if(membercardno==null ||membercardno.trim().equals(""))
	 {
	    out.print("<script>alert('您还未登录！请先登录！');window.close();</script>");
	 }
    CouponFavourite obj=new CouponFavourite(globa); 
    String  strId=ParamUtil.getString(request,"strid","");    
	if(strId.equals(""))
	{
		out.print("<script>alert('操作失败！');window.close();</script>");
		//throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
	}
    
	System.out.println(strId);	
    obj.setStrCreator("system");
    obj.setStrCouponId(strId);
    obj.setStrMemberCardNo(membercardno);
    boolean result = obj.add();
    if(result)
    {
      out.print("<script>alert('收藏成功！');window.close();</script>");
    }
    else
    {
       out.print("<script>alert('收藏失败!');window.close();</script>");
    }
	

    //关闭数据库连接对象
    globa.closeCon();
%>