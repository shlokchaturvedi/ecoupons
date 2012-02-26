<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.GiftExchange,
				 com.ejoysoft.auth.LogonForm,
				 com.ejoysoft.common.exception.IdObjectException,
				 com.ejoysoft.ecoupons.business.Gift,
				 com.ejoysoft.ecoupons.business.Member,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.common.SendSms,
				 java.net.UnknownHostException" %>
<%@page import="com.ejoysoft.ecoupons.TerminalParamVector"%>
<%@ include file="../include/jsp/head.jsp" %>
<%
	String membercardno=" ";  
	if(session.getAttribute(Constants.MEMBER_KEY) == null)
	{
   		globa.closeCon();
	    response.getWriter().print("<script>alert('您还未登录！请先登录！');window.close();</script>");
	}
	membercardno = globa.memberSession.getStrCardNo(); 	 
    GiftExchange obj=new GiftExchange(globa); 
    String  strId=ParamUtil.getString(request,"strid",""); 
    if(strId.equals(""))
	{
		response.getWriter().print("<script>alert('操作失败！');window.close();</script>");
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
   	else
   	{	   	
		int k= memberobj.setIntPoint(membercardno,balancepoint);
	    obj.setStrCreator("system");
	    obj.setStrGiftId(strId);
	    obj.setStrMemberCardNo(membercardno);
	    obj.setDtExchangeTime(com.ejoysoft.common.Format.getDateTime());
	    obj.setStrAddr(" ");
	    boolean result = obj.add();
	    String strPhone =ParamUtil.getString(request,"strPhone","");
	    if(!strPhone.equals(""))
	    {
	    
			String strWelcome = TerminalParamVector.getWelcome();
		    String messege="亲爱的"+globa.memberSession.getStrName()+"会员，您此次于乐购网成功兑换礼品"+giftobj.getStrName()+"！请至XX地址凭您的会员卡领取！"+strWelcome;
		    if(result)
		    {
		      String PostData;
			  try {
				  PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strPhone+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
				    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
				    if(!ret.equals(""))
			    	{
			    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
						int endIdx = ret.indexOf("</MsgState>");
						String retMsgState = ret.substring(beginIdx, endIdx);
						if(retMsgState.equals("审查"))				        
		     				 response.getWriter().print("<script>alert('礼品兑换成功！请查收短信');window.close();</script>");
						else
						{
		     				 response.getWriter().print("<script>alert('礼品兑换成功！短信发送失败！');window.close();</script>");
			    		}
			    	}
				    else {			    	
		      			 response.getWriter().print("<script>alert('礼品兑换成功！短信发送失败2！');window.close();</script>");
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    else
		    {
		       response.getWriter().print("<script>alert('礼品兑换失败3!');window.close();</script>");
		    }		
	   	  }
	   	  else
		  {
		      response.getWriter().print("<script>alert('礼品兑换失败4!');window.close();</script>");
		  }
	    
	  }	    

    //关闭数据库连接对象
    globa.closeCon();
%>