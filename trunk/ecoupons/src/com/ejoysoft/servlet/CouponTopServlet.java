package com.ejoysoft.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Terminal;

public class CouponTopServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");
		Globa globa = new Globa();
		resp.setCharacterEncoding("utf-8");
		String strTerminalNo = req.getParameter("strTerminalNo");
//		 String strTerminalNo = "23";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		Vector<String> vctString = new Vector<String>();
		if (terminal != null)
		{
			String strSql = "select strid from t_bz_coupon where dtexpiretime>now() and strTerminalIds like '%"+terminal.getStrId()+"%' order by intprint desc  LIMIT 24";
			ResultSet resultSet = globa.db.executeQuery(strSql);
			try
			{
				while (resultSet.next())
				{
					vctString.add(resultSet.getString("strid"));
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				sbReturn.append("<return>sql_error</return>");
				globa.closeCon();
				resp.getWriter().println(sbReturn.toString());
			}
			sbReturn.append("<coupons>");
			for (int i = 0; i < vctString.size(); i++)
			{
					sbReturn.append("<coupon>");
					sbReturn.append(vctString.get(i));
					sbReturn.append("</coupon>");
			}
			
			sbReturn.append("</coupons>");
		} else
		{
			sbReturn.append("<return>terminal_error</return>");
		}
		// System.out.println(sbReturn.toString());
		resp.getWriter().println(sbReturn.toString());
		globa.closeCon();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
}
