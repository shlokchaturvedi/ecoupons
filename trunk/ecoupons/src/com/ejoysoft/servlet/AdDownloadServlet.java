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

import com.ejoysoft.auth.Base64;
import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.business.Terminal;

public class AdDownloadServlet extends HttpServlet implements Servlet
{
	public AdDownloadServlet()
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

	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// TODO Auto-generated method stub
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		String strTerminalNo = req.getParameter("strTerminalNo");
//		strTerminalNo = "0004";
		Terminal terminal = hmTerminal.get(strTerminalNo);
		String strId = terminal.getStrId();
//		System.err.println(strId+"dddddddddddddddd");
		Globa globa = new Globa();
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");		
		resp.setCharacterEncoding("utf-8");		
		try
		{
			if (terminal != null)
			{
				Terminal objAd = new Terminal(globa);
				Terminal tempAd = new Terminal();
				Vector<Terminal> vctAd = new Vector<Terminal>();
				vctAd = objAd.listAd(" where strterminalids like '%"+strId+"%'", 0, 0);
				sbReturn.append("<info>");
				if (vctAd!=null && vctAd.size() > 0)
				{
					sbReturn.append("<ads>");
					for (int i = 0; i < vctAd.size(); i++)
					{
						tempAd = vctAd.get(i);
						if (tempAd!=null)
						{
							sbReturn.append(returnSbContent(tempAd));
						}
					}
					sbReturn.append("</ads>");
				}
				sbReturn.append("</info>");
			} else
			{
				sbReturn.append("<return>update_error</return>");
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			// 关闭数据库连接对象
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
	}

	/**
	 * 根据条件返回具体内容 20120117删除文件内容，返回空
	 * 
	 * @param tempTerminal
	 * @return
	 */

	private StringBuffer returnSbContent(Terminal tempTerminal)
	{
		StringBuffer sbReturnContent = new StringBuffer();
		if (tempTerminal!=null) {
			sbReturnContent.append("<ad>");
			sbReturnContent.append("<strId>" + tempTerminal.getStrId() + "</strId>");
			sbReturnContent.append("<strName>" + tempTerminal.getStrName() + "</strName>");
			sbReturnContent.append("<intType>" + tempTerminal.getIntType() + "</intType>");
			sbReturnContent.append("<strContent>" + tempTerminal.getStrContent() + "</strContent>");
			sbReturnContent.append("<dtStartTime>" + tempTerminal.getDtStartTime() + "</dtStartTime>");
			sbReturnContent.append("<dtEndTime>" + tempTerminal.getDtEndTime() + "</dtEndTime>");
			sbReturnContent.append("</ad>");
		}
		return sbReturnContent;
	}

}
