package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Format;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.SendSms;
import com.ejoysoft.ecoupons.TerminalParamVector;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.ecoupons.business.Terminal;

@SuppressWarnings("serial")
public class CouponSendBySMServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	this.execute(req, resp);
	}
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.execute(req, resp);
	}
private void execute(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
{
	StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
	ServletContext application = getServletContext();
	Globa globa = new Globa();
	try {
		resp.setCharacterEncoding("utf-8");
		String strTerminalNo = req.getParameter("strTerminalNo");
		String strCardNo=req.getParameter("strCardNo");
		Member member=new Member(globa);
		Member memberTemp=member.show("where strCardNo='"+strCardNo+"'");
		String strMobileNo = "";
		if (memberTemp!=null)
		{
			strMobileNo=memberTemp.getStrMobileNo();
		}
		String strMessege = req.getParameter("strContent");
//		strMobileNo = "15155963350";
//		strTerminalNo="000";
//		strMessege = "亲爱的乐购会员您好！" ;
		String strTerminalId="";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		if(terminal!=null)
		{
			strTerminalId = terminal.getStrId();
			Terminal obj = new Terminal(globa);
			//obj.updateState(strTerminalId);//更新终端状态
			if(strMobileNo!=null && !strMobileNo.trim().equals(""))
			{
				if(strMessege!=null && !strMessege.trim().equals(""))
				{
					//欢迎语
					String strWelcome = TerminalParamVector.getWelcome();
					strMessege = strMessege + strWelcome;
					String PostData;
					try {
						PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strMobileNo+"&smsg="+java.net.URLEncoder.encode(strMessege,"utf-8");
					    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
					    if(!ret.equals(""))
				    	{
				    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
							int endIdx = ret.indexOf("</MsgState>");
							String retMsgState = ret.substring(beginIdx, endIdx);
							if(retMsgState.equals("审查"))
						        sbReturn.append("<return>OK</return>");
				    	}
					    else {
							sbReturn.append("<return>sms_erro</return>");
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						sbReturn.append("<return>sms_erro</return>");
					}
				}else {
					sbReturn.append("<return>messege_erro</return>");
				}				
			}
			else {
				sbReturn.append("<return>phone_erro</return>");
			}
		}
		else {
			sbReturn.append("<return>terminal_erro</return>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {			
		globa.closeCon();
		resp.getWriter().println(sbReturn.toString());
	}
	
}
}
