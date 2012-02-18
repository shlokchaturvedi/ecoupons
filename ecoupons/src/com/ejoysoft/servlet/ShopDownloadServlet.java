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
		req.setCharacterEncoding("utf-8");
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
			Shop shop = new Shop(globa);
			String strWhere = "where strDataType='t_bz_shop' and intState=0 and strTerminalId='" + strId + "'";
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
							try
							{
								sbReturn.append(returnSbContent(globa, tempShop));
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
								try
								{
									resp.getWriter().print("<?xml version='1.0' encoding='utf-8'?> ");
								} catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
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
							try
							{
								sbReturn.append(returnSbContent(globa, tempShop));
							} catch (SQLException e)
							{
								try
								{
									resp.getWriter().print("<?xml version='1.0' encoding='utf-8'?> ");
								} catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								// TODO Auto-generated catch block
								e.printStackTrace();

							}
							// sbReturn.append(returnSbContent(tempShop,
							// strImagAddr));
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
						}
					}
					if (!flagDelete)
					{
						sbReturn.append("</shops>");
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
		if (!terminal2.updateState(strId, "t_bz_shop"))
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
	 * 根据条件返回具体内容
	 * 
	 * @param tempTerminal
	 * @return
	 * @throws SQLException
	 */
	private StringBuffer returnSbContent(Globa globa, Shop tempShop) throws SQLException
	{
		SysPara sysPara = new SysPara(globa);

		StringBuffer sbReturn = new StringBuffer();
		sbReturn.append("<shop>");
		sbReturn.append("<strId>" + tempShop.getStrId() + "</strId>");
		sbReturn.append("<strBizName>" + tempShop.getStrBizName() + "</strBizName>");
		sbReturn.append("<strShopName>" + tempShop.getStrShopName() + "</strShopName>");
		sbReturn.append("<strTrade>" + sysPara.show("where strid='" + tempShop.getStrTrade() + "'").getStrName() + "</strTrade>");
		sbReturn.append("<strAddr>" + tempShop.getStrAddr() + "</strAddr>");
		sbReturn.append("<strIntro>" + tempShop.getStrIntro() + "</strIntro>");
		sbReturn.append("<strSmallImg>" + tempShop.getStrSmallImg() + "</strSmallImg>");
		sbReturn.append("<strLargeImg>" + tempShop.getStrLargeImg() + "</strLargeImg>");
		sbReturn.append("</shop>");
		return sbReturn;
	}

}