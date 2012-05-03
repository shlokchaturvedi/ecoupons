package com.ejoysoft.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.business.Terminal;
import com.ejoysoft.ecoupons.system.SysPara;

@SuppressWarnings("serial")
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
		String strTerminalNo = req.getParameter("strTerminalNo");
//		strTerminalNo = "000";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		Globa globa = new Globa();		
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		try
		{
			if (terminal != null)
			{				
				Shop shop = new Shop(globa);
				Vector<Shop> vctShops = new Vector<Shop>();
				vctShops = shop.list("", 0, 0);
			    Shop tempShop = new Shop();
				sbReturn.append("<info>");
				
				if (vctShops.size() > 0)
				{
					sbReturn.append("<shops>");
					for (int i = 0; i < vctShops.size(); i++)
					{
						tempShop = vctShops.get(i);
						if (tempShop!=null)
						{							
								sbReturn.append(returnSbContent(globa, tempShop));
						}
					}
					sbReturn.append("</shops>");
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
//			System.out.println(sbReturn.toString());
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