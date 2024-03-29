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
import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.ecoupons.business.Terminal;

@SuppressWarnings("serial")
public class MemberMobileServlet extends HttpServlet {
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
		String strCardNo = req.getParameter("strCardNo");
		String strCode = req.getParameter("strCode");
		String strMobileNo = req.getParameter("strMobileNo");
		String strTerminalId="";
//		strTerminalNo="000";
//		String strTerminalId="1325052084765002";
//		strCode="sdfe";
//		strMobileNo="15155963350";
//		strCardNo="80000002";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		if(terminal!=null)
		{
			strTerminalId = terminal.getStrId();
			Terminal obj = new Terminal(globa);
			//obj.updateState(strTerminalId);//更新终端状态
			String strName="";
			String nowdate = Format.getDateTime();
			Member obj2 = new Member(globa);
			if(strCode!=null && !strCode.trim().equals(""))
			{
				if(strCardNo!=null && !strCardNo.trim().equals(""))
				{
					Member obj1 = obj2.show(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
					strName = obj1.getStrName();
				}
				else {
					sbReturn.append("<return>cardno_erro</return>");
				}
				//欢迎语
				String strWelcome = TerminalParamVector.getWelcome();
				String messege = "亲爱的乐购会员您好！您此次于券打机上操作的验证码为："+strCode+"，请及时使用！" + strWelcome;
				if(strMobileNo!=null && !strMobileNo.trim().equals(""))
				{
					int standardNum = Integer.parseInt(String.valueOf(application.getAttribute("MOBILE_BIND_CARD_NUM")));
					int mobileBindNum = obj2.getCount(" where strmobileno='"+strMobileNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
					if (mobileBindNum>=standardNum) {
						sbReturn.append("<return>strmobileno_erro</return>");
					}else
					{
						String PostData;
						try {
							PostData = "sname="+application.getAttribute("SNAME")+"&spwd="+application.getAttribute("SPWD")+"&scorpid="+application.getAttribute("SCORPID")+"&sprdid="+application.getAttribute("SPRDID")+"&sdst="+strMobileNo+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
						    String ret = SendSms.SMS(PostData, String.valueOf(application.getAttribute("SMS_WEBSERVICE_ADDR")));
						    if(!ret.equals(""))
					    	{
					    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
								int endIdx = ret.indexOf("</MsgState>");
								String retMsgState = ret.substring(beginIdx, endIdx);
								if(retMsgState.equals("提交成功"))
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
					}
					
				}
				else {
					sbReturn.append("<return>phone_erro</return>");
				}
			}
			else
			{
				if(strCardNo!=null && !strCardNo.trim().equals("") && strMobileNo!=null && !strMobileNo.trim().equals(""))
				{
					int standardNum = Integer.parseInt(String.valueOf(application.getAttribute("MOBILE_BIND_CARD_NUM")));
					int mobileBindNum = obj2.getCount(" where strmobileno='"+strMobileNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
					if (mobileBindNum>=standardNum) {
						sbReturn.append("<return>strmobileno_erro</return>");
					}
					else {
						boolean result = obj2.updateMobileNo(strCardNo,strMobileNo);
						if(result)
						{
							sbReturn.append("<return>OK</return>");
						}
						else
						{
							sbReturn.append("<return>update_erro</return>");
						}
					}
				}
				else {
					sbReturn.append("<return>cardorphone_erro</return>");
				}
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
