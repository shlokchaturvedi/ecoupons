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
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		try
		{
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sbReturn.append("<return>error</return>");
			resp.getWriter().print(sbReturn.toString());
		}
		Globa globa = new Globa();
		resp.setCharacterEncoding("utf-8");
		String strTerminalNo = req.getParameter("strTerminalNo");
		// String strTerminalNo = "23";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		Terminal terminal2 = new Terminal(globa);// 用于刷新终端状态
		String strId = terminal.getStrId();
		if (terminal != null)
		{
			DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
			Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
			String strWhere = "where strDataType='t_bz_advertisement' and intState=0 and strTerminalId='" + strId + "'";
			Terminal tempTerminal = new Terminal();
			vctAlerts = downLoadAlert.list(strWhere, 0, 0);
			boolean flagAdd = true;
			boolean flagUpdate = true;
			boolean flagDelete = true;
			
				sbReturn.append("<info>");
				if (vctAlerts.size() > 0)
				{
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempTerminal = terminal2.showAd("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("add".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagAdd)
							{
								sbReturn.append("<ads>");
								sbReturn.append("<operate>add</operate>");
								flagAdd = false;
							}
							sbReturn.append(returnSbContent(tempTerminal));
						}
					}
					if (!flagAdd)
					{
						sbReturn.append("</ads>");
					}
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempTerminal = terminal2.showAd("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("update".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagUpdate)
							{
								sbReturn.append("<ads>");
								sbReturn.append("<operate>update</operate>");
								flagUpdate = false;
							}
							sbReturn.append(returnSbContent(tempTerminal));
						}
					}
					if (!flagUpdate)
					{
						sbReturn.append("</ads>");
					}
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempTerminal = terminal2.showAd("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("delete".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagDelete)
							{
								sbReturn.append("<ads>");
								sbReturn.append("<operate>delete</operate>");
								flagDelete = false;
							}
							sbReturn.append("<ad>");
							sbReturn.append("<strId>" + vctAlerts.get(i).getStrDataId() + "</strId>");
							sbReturn.append("</ad>");
						}
					}
					if (!flagDelete)
					{
						sbReturn.append("</ads>");
					}
				} else
				{
					sbReturn.append("null");
				}
				sbReturn.append("</info>");
			} else
			{
				sbReturn.append("<return>update_error</return>");
			}
		if (!terminal2.updateState(strId, "t_bz_advertisement"))
		{
			sbReturn.append("<return>terminal_error</return>");
		}
		try
		{
			resp.getWriter().print(sbReturn.toString());
		} catch (IOException e)
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
		StringBuffer sbContent = new StringBuffer("");
		StringBuffer sbReturnContent = new StringBuffer();
		sbReturnContent.append("<ad>");
		sbReturnContent.append("<strId>" + tempTerminal.getStrId() + "</strId>");
		sbReturnContent.append("<strName>" + tempTerminal.getStrName() + "</strName>");
		sbReturnContent.append("<intType>" + tempTerminal.getIntType() + "</intType>");
		sbReturnContent.append("<strContent>" + tempTerminal.getStrContent() + "</strContent>");
		sbReturnContent.append("<dtStartTime>" + tempTerminal.getDtStartTime() + "</dtStartTime>");
		sbReturnContent.append("<dtEndTime>" + tempTerminal.getDtEndTime() + "</dtEndTime>");
		sbReturnContent.append("</ad>");
		return sbReturnContent;
	}

}
