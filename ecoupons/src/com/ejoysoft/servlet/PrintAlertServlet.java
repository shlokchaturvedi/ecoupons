package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Terminal;

public class PrintAlertServlet extends HttpServlet
{
public PrintAlertServlet()
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
		this.execute(req, resp);
	}
private void execute(HttpServletRequest req, HttpServletResponse resp)
{
	StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
	try
	{
		req.setCharacterEncoding("utf-8");
	} catch (UnsupportedEncodingException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
		try
		{
			resp.getWriter().print(sbReturn.toString());
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	Globa globa = new Globa();
	resp.setCharacterEncoding("utf-8");
	String strTerminalNo = req.getParameter("strTerminalNo");
	HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
	Terminal terminal = hmTerminal.get(strTerminalNo);
	if(terminal!=null)
	{
		Terminal terminal2 = new Terminal(globa);
		String strId = terminal.getStrId();
		if (terminal2.updatePrintPaperState(strId,1))//1代表该终端现在缼纸
		{			
			sbReturn.append("<return>OK</return>");		
		}
	}	
	try
	{
		resp.getWriter().println(sbReturn.toString());
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();

	}
	
	// 关闭数据库连接对象
	globa.closeCon();
	
}
}
