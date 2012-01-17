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

	private void execute(HttpServletRequest req, HttpServletResponse resp)
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
//		 String strTerminalNo = "23";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		Terminal terminal2 = new Terminal(globa);// 用于刷新终端状态
		String strId = terminal.getStrId();
		// System.out.println(strId);
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
		String strFileAddr = req.getSession().getServletContext().getRealPath(req.getRequestURI());
		strFileAddr = strFileAddr.replace("\\ecoupons\\servlet\\AdDownload", "\\terminal\\advertisement\\");

		String strWhere = "where strDataType='t_bz_advertisement' and intState=0 and strTerminalId='" + strId + "'";
		Terminal tempTerminal = new Terminal();
		vctAlerts = downLoadAlert.list(strWhere, 0, 0);
		boolean flagAdd = true;
		boolean flagUpdate = true;
		boolean flagDelete = true;
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
					sbReturn.append(returnSbContent(tempTerminal, strFileAddr));

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
					sbReturn.append(returnSbContent(tempTerminal, strFileAddr));
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
		}
		if (terminal2.updateState(strId, "t_bz_advertisement"))
		{
			 
			try
			{
				resp.getWriter().print(sbReturn.toString());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		// 关闭数据库连接对象
		globa.closeCon();
	}

	/**
	 * 根据条件返回具体内容
	 * 
	 * @param tempTerminal
	 * @return
	 */
	private StringBuffer returnSbContent(Terminal tempTerminal, String strFileAddr)
	{

//		String strContent = "";
		StringBuffer sbContent=new StringBuffer();
		StringBuffer sbReturnContent = new StringBuffer();

		if ("1".equals(tempTerminal.getIntType()) && tempTerminal.getStrContent() != null && tempTerminal.getStrContent() != "")//视频文件
		{
			sbContent.append(Base64.getPicBASE64(strFileAddr + tempTerminal.getStrContent()));
		} else
		if ("2".equals(tempTerminal.getIntType()) && tempTerminal.getStrContent() != null && tempTerminal.getStrContent() != "")//图片文件,可能有多个
		{
			String[]strContentNames=tempTerminal.getStrContent().split(",");
			for (int i = 0; i < strContentNames.length; i++)
			{
				sbContent.append(Base64.getPicBASE64(strFileAddr + strContentNames[i]));
			}
		}
		sbReturnContent.append("<ad>");
		sbReturnContent.append("<strId>" + tempTerminal.getStrId() + "</strId>");
		sbReturnContent.append("<strName>" + tempTerminal.getStrName() + "</strName>");
		sbReturnContent.append("<intType>" + tempTerminal.getIntType() + "</intType>");
		sbReturnContent.append("<strContent>" + tempTerminal.getStrContent() + "</strContent>");
		sbReturnContent.append("<strFileContent>" + sbContent.toString() + "</strFileContent>");
		sbReturnContent.append("<dtStartTime>" + tempTerminal.getDtStartTime() + "</dtStartTime>");
		sbReturnContent.append("<strEndTime>" + tempTerminal.getDtEndTime() + "</strEndTime>");
		sbReturnContent.append("</ad>");

		return sbReturnContent;
	}

}
