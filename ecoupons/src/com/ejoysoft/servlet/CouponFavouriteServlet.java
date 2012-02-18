package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.common.SendSms;
import com.ejoysoft.ecoupons.business.CouponFavourite;
import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.ecoupons.business.Terminal;

public class CouponFavouriteServlet extends HttpServlet {
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
private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
{
	StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
	ServletContext application = getServletContext();
	Globa globa = new Globa();
	try {
		resp.setCharacterEncoding("utf-8");
		String strTerminalNo = req.getParameter("strTerminalNo");
		String strCardNo = req.getParameter("strCardNo");
		String strCouponId = req.getParameter("strCouponId");
		String strTerminalId="";
	//	strTerminalNo="242342";
	//	strCardNo = "1325052084765002";
	//	strCouponId="1326253170109014";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		if(terminal!=null)
		{
			strTerminalId = terminal.getStrId();
			Terminal obj = new Terminal(globa);
			//obj.updateState(strTerminalId);//更新终端状态
			CouponFavourite obj2 = new CouponFavourite(globa);
			if(strCardNo!=null&&!strCardNo.trim().equals("") && strCouponId!=null && !strCouponId.trim().equals(""))
			{
				obj2.setStrCouponId(strCouponId);
				obj2.setStrMemberCardNo(strCardNo);
				obj2.setStrCreator("system");
				boolean result = obj2.add();
				if(result)
				{
					sbReturn.append("<return>OK</return>");	
				}
				else {
					sbReturn.append("<return>add_erro</return>");	
				}
			} else {
				sbReturn.append("<return>cardorcouponid_erro</return>");
			}
		} else {
			sbReturn.append("<return>terminal_erro</return>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		sbReturn.append("<return>error</return>");	
	}finally {			
		globa.closeCon();
		resp.getWriter().println(sbReturn.toString());
	}	
 }
}
