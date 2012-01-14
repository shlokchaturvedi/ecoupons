package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.SendSms;
import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.ecoupons.business.Terminal;

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
		Terminal obj0 = new Terminal(globa);
		String strTerminalId = obj0.getTerminalIdsByNames(strTerminalNo);
		obj0.updateState(strTerminalId);//更新终端状态
		String strCode = req.getParameter("strCode");
		String strCardNo = req.getParameter("strCardNo");
		String strPhone="",strName="";
		if(strCardNo!=null && !strCardNo.trim().equals(""))
		{
			Member obj = new Member(globa);
			Member obj1 = obj.show(" where strcardno='"+strCardNo+"'");
			strPhone = obj1.getStrMobileNo();
			strName = obj1.getStrName();
		}		
		String messege = "亲爱的"+strName+"会员您好！您此次于"+strTerminalNo+"终端上打印优惠券的验证码为：   "+strCode+"  ，请及时使用，祝您购物愉快！";
		if(strPhone!=null && !strPhone.trim().equals("") && strCode!=null && !strCode.trim().equals(""))
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

