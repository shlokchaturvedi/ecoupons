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
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.business.Terminal;

@SuppressWarnings("serial")
public class ShopAroundServlet extends HttpServlet
{
	public ShopAroundServlet()
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
		doGet(req, resp);
	}

	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		Globa globa = new Globa();
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		try {
			ServletContext application = getServletContext();
			globa.initialize(application, req, resp);
			String strTerminalNo = req.getParameter("strTerminalNo");
			//String strTerminalNo ="34ewer23";
			Terminal obj0 = new Terminal(globa);
			HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
			Terminal terminal = hmTerminal.get(strTerminalNo);
			if(terminal!=null)
			{
				String strTerminalId = terminal.getStrId();
				obj0.updateState(strTerminalId);//更新终端状态
				Terminal objTerminal = new Terminal(globa);
				Terminal objTerminal2 = objTerminal.show(" where strno='"+strTerminalNo+"'");
				String shopids = objTerminal2.getStrAroundShopIds();
				sbReturn.append("<shops>");
				if(shopids!=null&&!shopids.equals(""))
				{
					String[] shops = shopids.split(",");
					for(int i=0;i<shops.length;i++)
					{
						sbReturn.append("<shop><strId>");
						sbReturn.append(shops[i]);
						sbReturn.append("</strId></shop>");
					}
				}
				sbReturn.append("</shops>");
			} else {
				sbReturn.append("<shops></shops>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
    }	
}

