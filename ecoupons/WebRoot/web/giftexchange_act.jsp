<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.GiftExchange,
				 com.ejoysoft.auth.LogonForm,
				 com.ejoysoft.common.exception.IdObjectException,
				 com.ejoysoft.ecoupons.business.Gift,
				 com.ejoysoft.ecoupons.business.Member" %>
<%@ include file="../include/jsp/head.jsp" %>
<%
	String membercardno = "111";
	 if(membercardno==null ||membercardno.trim().equals(""))
	 {
	    out.print("<script>alert('您还未登录！请先登录！');window.close();</script>");
	 }
    GiftExchange obj=new GiftExchange(globa); 
    String  strId=ParamUtil.getString(request,"strid",""); 
    if(strId.equals(""))
	{
		out.print("<script>alert('操作失败！');window.close();</script>");
		//throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
	}   
    Gift obj1 = new Gift(globa);
    Gift giftobj = obj1.show(" where strid='"+strId+"'");
    Member memberobj = new Member(globa);
    Member member = memberobj.show(" where strcardno='"+membercardno+"'");    
    int intpoint = 0,memberpoint=0;    
   	intpoint = giftobj.getIntPoint();  
   	memberpoint = member.getIntPoint();
   	int balancepoint = memberpoint-intpoint;
    if(balancepoint<0)
   		 out.print("<script>alert('您的积分余额不足！请充值后再兑换！');window.close();</script>");
	int k= memberobj.setIntPoint(membercardno,balancepoint);
    obj.setStrCreator("system");
    obj.setStrGiftId(strId);
    obj.setStrMemberCardNo(membercardno);
    obj.setDtExchangeTime(com.ejoysoft.common.Format.getDateTime());
    obj.setStrAddr(" ");
    boolean result = obj.add();
    if(result)
    {
      out.print("<script>alert('礼品兑换成功！');window.close();</script>");
    }
    else
    {
       out.print("<script>alert('礼品兑换失败!');window.close();</script>");
    }
	

    //关闭数据库连接对象
    globa.closeCon();
%>