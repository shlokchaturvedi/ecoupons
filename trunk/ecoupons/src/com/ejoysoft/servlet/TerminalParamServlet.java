package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Terminal;
import com.ejoysoft.ecoupons.business.TerminalPara;

public class TerminalParamServlet extends HttpServlet
{
	public TerminalParamServlet()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.execute(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
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
//		String strTerminalNo = "21";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal2 = new Terminal(globa);// 用于刷新终端状态
		String strId = hmTerminal.get(strTerminalNo).getStrId();
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
		String strWhere = "where strDataType='t_bz_terminal_param' and intState=0 and strTerminalId='" + strId + "'";
		TerminalPara terminalParam = new TerminalPara(globa);
		TerminalPara tempTerminalParam = new TerminalPara();
		vctAlerts = downLoadAlert.list(strWhere, 0, 0);
		sbReturn.append("<params>");
		if (vctAlerts.size() > 0)
		{
			for (int i = 0; i < vctAlerts.size(); i++)
			{
				tempTerminalParam = terminalParam.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
				sbReturn.append("<param>");
				sbReturn.append("<strId>" + tempTerminalParam.getStrId() + "</strId>");
				sbReturn.append("<strParamName>" + tempTerminalParam.getStrParamName() + "</strParamName>");
				sbReturn.append("<strParamValue>" + tempTerminalParam.getStrParamValue() + "</strParamValue>");
				sbReturn.append("</param>");
			}
		}
		sbReturn.append("</params>");
		if (terminal2.updateState(strId, "t_bz_terminal_param"))
		{
			try
			{
				resp.getWriter().print(sbReturn.toString());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			try
			{
				resp.getWriter().print("<?xml version='1.0' encoding='utf-8'?> ");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 关闭数据库连接对象
		globa.closeCon();
	}

}