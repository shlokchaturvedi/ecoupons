package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ejoysoft.common.Constants;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.SendSms;
import com.ejoysoft.common.UserSession;
import com.ejoysoft.ecoupons.business.Member;

@SuppressWarnings("serial")
public class CouponAuthServlet extends HttpServlet
{
	public CouponAuthServlet()
	{
		// TODO Auto-generated constructor stub
	}

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
		doGet(req, resp);
	}

	private void execute(HttpServletRequest req, HttpServletResponse resp)
	{
		Globa globa = new Globa();
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		ServletContext application = getServletContext();
		globa.initialize(application, req, resp);
		String strTerminalNo = req.getParameter("strTerminalNo");
		String strCode = req.getParameter("strCode");
		String strCardNo = req.getParameter("strCardNo");
		String strPhone="";
		if(strCardNo!=null && !strCardNo.trim().equals(""))
		{
			Member obj = new Member(globa);
			Member obj1 = obj.show(" where strcardno='"+strCardNo+"'");
			strPhone = obj1.getStrMobileNo();
		}		
		String messege = strCode;
		if(strPhone!=null && !strPhone.trim().equals("") && messege!=null && !messege.trim().equals(""))
		{
			String PostData;
			try {
				PostData = "sname=dlqdcs02&spwd=SjkQ35RS&scorpid=&sprdid=1012818&sdst="+strPhone+"&smsg="+java.net.URLEncoder.encode(messege,"utf-8");
			    String ret = SendSms.SMS(PostData, "http://chufa.lmobile.cn/submitdata/service.asmx/g_Submit");
			    if(!ret.equals(""))
		    	{
		    		int beginIdx = ret.indexOf("<MsgState>") + "<MsgState>".length();
					int endIdx = ret.indexOf("</MsgState>");
					String retMsgState = ret.substring(beginIdx, endIdx);
					if(retMsgState.equals("审查"))
				        sbReturn.append("<return>OK</return>");
				    else 
				    	sbReturn.append("<return>NO</return>");
		    	}
			} catch (UnsupportedEncodingException e) {
		    	sbReturn.append("<return>NO</return>");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			sbReturn.append("<return>NO</return>");
		}		
		try {
			resp.getWriter().println(sbReturn.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

