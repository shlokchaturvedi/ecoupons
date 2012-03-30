package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.business.Terminal;
import com.ejoysoft.ecoupons.system.SysPara;

public class ShopDownloadServlet extends HttpServlet implements Servlet
{
	public ShopDownloadServlet()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		try {
		this.execute(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// TODO Auto-generated method stub
//		String strReturn = "NO";
		String strReturn = req.getParameter("strReturn");
//		String strTerminalNo = "002";
		String strTerminalNo = req.getParameter("strTerminalNo");
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		String strId = terminal.getStrId();
		Globa globa = new Globa();
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		if("OK".equals(strReturn)||"NO".equals(strReturn))
		{
			downLoadAlert.dealDataByTerminalId(strId, strReturn,"t_bz_shop");
//			this.destroy();
			globa.closeCon();
			return;
		}
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
//		 String strTerminalNo = "110";
		Terminal terminal2 = new Terminal(globa);// 用于刷新终端状态
		try
		{
			if (terminal != null)
			{
				Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
				Shop shop = new Shop(globa);
				String strWhere = "where strDataType='t_bz_shop' and (intState=0 or intState=2) and strTerminalId='" + strId + "'";
				Shop tempShop = new Shop();
				vctAlerts = downLoadAlert.list(strWhere, 0, 0);
				boolean flagAdd = true;
				boolean flagUpdate = true;
				boolean flagDelete = true;

				sbReturn.append("<info>");
				if (vctAlerts.size() > 0)
				{
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempShop = shop.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("add".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagAdd)
							{
								sbReturn.append("<shops>");
								sbReturn.append("<operate>add</operate>");
								flagAdd = false;
							}
							
								sbReturn.append(returnSbContent(globa, tempShop));
//								terminal2.addState2(strId, "t_bz_shop", vctAlerts.get(i).getStrDataId(), "add");
							// sbReturn.append(returnSbContent(tempShop,
							// strImagAddr));

						}
					}
					if (!flagAdd)
					{
						sbReturn.append("</shops>");
					}

					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempShop = shop.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("update".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagUpdate)
							{
								sbReturn.append("<shops>");
								sbReturn.append("<operate>update</operate>");
								flagUpdate = false;
							}
							
								sbReturn.append(returnSbContent(globa, tempShop));
//								terminal2.addState2(strId, "t_bz_shop", vctAlerts.get(i).getStrDataId(), "update");
						}
					}
					if (!flagUpdate)
					{
						sbReturn.append("</shops>");
					}
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempShop = shop.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("delete".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagDelete)
							{
								sbReturn.append("<shops>");
								sbReturn.append("<operate>delete</operate>");
								flagDelete = false;
							}
							sbReturn.append("<shop>");
							sbReturn.append("<strId>" + vctAlerts.get(i).getStrDataId() + "</strId>");
							sbReturn.append("</shop>");
//							terminal2.addState2(strId, "t_bz_shop", vctAlerts.get(i).getStrDataId(), "delete");
						}
					}
					if (!flagDelete)
					{
						sbReturn.append("</shops>");
					}
				} 
				sbReturn.append("</info>");
			} else
			{
				sbReturn.append("<return>update_error</return>");
			}
			if (!terminal2.updateState2(strId,"t_bz_shop"))
			{
				sbReturn.append("<return>terminal_error</return>");
			}

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally
		{
			// 关闭数据库连接对象
			globa.closeCon();
			System.out.println(sbReturn.toString());
			resp.getWriter().println(sbReturn.toString());
		}
	}

	/**
	 * 根据条件返回具体内容
	 * 
	 * @param tempTerminal
	 * @return
	 * @throws SQLException
	 */
	private StringBuffer returnSbContent(Globa globa, Shop tempShop) 
	{
		SysPara sysPara = new SysPara(globa);

		StringBuffer sbReturn = new StringBuffer();
		if (tempShop!=null) {
			sbReturn.append("<shop>");
			sbReturn.append("<strId>" + tempShop.getStrId() + "</strId>");
			sbReturn.append("<strBizName>" + tempShop.getStrBizName() + "</strBizName>");
			sbReturn.append("<strShopName>" + tempShop.getStrShopName() + "</strShopName>");
			try
			{
				sbReturn.append("<strTrade>" + sysPara.show("where strid='" + tempShop.getStrTrade() + "'").getStrName() + "</strTrade>");
			} catch (SQLException e)
			{
				sbReturn.append("<return>strTrade_error</return>");
				e.printStackTrace();
			}
			sbReturn.append("<strAddr>" + tempShop.getStrAddr() + "</strAddr>");
			sbReturn.append("<intSort>" + tempShop.getIntSort() + "</intSort>");
			sbReturn.append("<strIntro>" + tempShop.getStrIntro() + "</strIntro>");
			sbReturn.append("<strSmallImg>" + tempShop.getStrSmallImg() + "</strSmallImg>");
			sbReturn.append("<strLargeImg>" + tempShop.getStrLargeImg() + "</strLargeImg>");
			sbReturn.append("<intType>" + tempShop.getIntType() + "</intType>");
			sbReturn.append("</shop>");
		}		
		return sbReturn;
	}

}