<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.business.GiftExchange,com.ejoysoft.auth.LogonForm,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.business.Gift,com.ejoysoft.ecoupons.business.Member,com.ejoysoft.common.Constants,com.ejoysoft.common.SendSms,java.net.UnknownHostException"%>
<%@page import="com.ejoysoft.ecoupons.business.PointCard"%>
<%@page import="com.ejoysoft.ecoupons.business.PointCardInput"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strPointCardInputTable = "t_bz_pointcard_input";
	if (session.getAttribute(Constants.MEMBER_KEY) == null)
	{
		globa.closeCon();
		response.getWriter().print(
				"<script>alert('您还未登录！请先登录！');top.location = '" + application.getServletContextName() + "/web/index.jsp';</script>");
	}
	String strMemberCardNo = globa.getMember().getStrCardNo();
	String strPointCardNo = ParamUtil.getString(request, "strPointCardNo", "");
	String strPointCardPwd = ParamUtil.getString(request, "strPointCardPwd", "");
	Member member=new Member(globa);
	PointCard pointCard = new PointCard(globa,true);
	if(pointCard.getCount("where strpointcardno='" + strPointCardNo + "'")>0)
	{
	PointCard pCard = pointCard.show("where strpointcardno='" + strPointCardNo + "'");
	if (pCard.getStrPointCardPwd().equals(strPointCardPwd))
	{
		if(pCard.getIntType()==0){
		PointCardInput pointCardInput = new PointCardInput(globa, true);
		pointCardInput.setStrMemberCardNo(strMemberCardNo);
		pointCardInput.setIntPoint(member.show("where strCardNo='"+strMemberCardNo+"'").getIntPoint());
		globa.dispatch(pointCardInput.add(), "integral.jsp");
		}else
		{
			response.getWriter().print("<script>alert('该积分卡卡号已经输入，请重新输入！');top.location = '" + application.getServletContextName() + "/web/integral.jsp';</script>");
		}
	} else
	{
		response.getWriter().print("<script>alert('积分卡卡号和密码不正确，请重新输入！');top.location = '" + application.getServletContextName() + "/web/integral.jsp';</script>");
	}
	}else{
	response.getWriter().print("<script>alert('该输入正确的积分卡卡号！');top.location = '" + application.getServletContextName() + "/web/integral.jsp';</script>");
	}
	//关闭数据库连接对象
	globa.closeCon();
%>