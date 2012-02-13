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

	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		Globa globa = new Globa();
		try{
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			 String strTerminalNo = req.getParameter("strTerminalNo");
	//		String strTerminalNo = "21";
			HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
			Terminal terminal = new Terminal(globa);// 用于刷新终端状态
			if (hmTerminal.get(strTerminalNo) != null)
			{
				String strId = hmTerminal.get(strTerminalNo).getStrId();
				TerminalPara terminalParam = new TerminalPara(globa);
				Vector<TerminalPara> vctParas = new Vector<TerminalPara>();
				vctParas = terminalParam.list("", 0, 0);
				if (terminal.updateState(strId, "t_bz_terminal_param"))
				{
					sbReturn.append("<params>");
					if (vctParas.size() > 0)
					{
						for (int i = 0; i < vctParas.size(); i++)
						{
							sbReturn.append("<param>");
							sbReturn.append("<strId>" + vctParas.get(i).getStrId() + "</strId>");
							sbReturn.append("<strParamName>" + vctParas.get(i).getStrParamName() + "</strParamName>");
							sbReturn.append("<strParamValue>" + vctParas.get(i).getStrParamValue() + "</strParamValue>");
							sbReturn.append("</param>");
						}
					}
					else {
						sbReturn.append("<params>noparam</params>");
					}
					sbReturn.append("</params>");
	
				}
				else {
					sbReturn.append("<params>updatestate_erro</params>");
				}
			}
			else {
				sbReturn.append("<params>terminal_erro</params>");
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sbReturn.append("<params>erro</params>");
		}finally {			
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
	}

}