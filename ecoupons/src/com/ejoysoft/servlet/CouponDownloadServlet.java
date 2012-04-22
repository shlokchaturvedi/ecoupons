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
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Terminal;

public class CouponDownloadServlet extends HttpServlet implements Servlet
{
	public CouponDownloadServlet()
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
		Globa globa = new Globa();
//		 String strTerminalNo = "002";
		String strTerminalNo = req.getParameter("strTerminalNo");
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		Terminal terminal2 = new Terminal(globa);
		if (terminal != null)
		{
			String strId = terminal.getStrId();
			Coupon coupon = new Coupon(globa);
			Vector<Coupon> vctCoupons = coupon.list("where strterminalids like '%" + strId + "%'", 0, 0);
			sbReturn.append("<info>");
			sbReturn.append("<coupons>");
			if (vctCoupons != null)
			{
				for (int i = 0; i < vctCoupons.size(); i++)
				{
					sbReturn.append(returnSbContent(vctCoupons.get(i)));
				}
			} else
			{
				sbReturn.append("<return>coupon_error</return>");
			}
			sbReturn.append("</coupons>");
			sbReturn.append("</info>");
			terminal2.updateState2(strId,"t_bz_coupon");
		} else
		{
			sbReturn.append("<return>terminal_error</return>");
		}
		// 关闭数据库连接对象
		resp.getWriter().println(sbReturn.toString());
		System.out.println(sbReturn.toString());
		globa.closeCon();

	}

	/**
	 * 根据条件返回具体内容
	 * 
	 * @param tempTerminal
	 * @return
	 */
	private StringBuffer returnSbContent(Coupon tempCoupon)
	{
		StringBuffer sbReturn = new StringBuffer();
		if (tempCoupon != null)
		{
			sbReturn.append("<coupon>");
			sbReturn.append("<strId>" + tempCoupon.getStrId() + "</strId>");
			sbReturn.append("<strName>" + tempCoupon.getStrName() + "</strName>");
			sbReturn.append("<dtActiveTime>" + tempCoupon.getDtActiveTime() + "</dtActiveTime>");
			sbReturn.append("<dtExpireTime>" + tempCoupon.getDtExpireTime() + "</dtExpireTime>");
			sbReturn.append("<strShopId>" + tempCoupon.getStrShopId() + "</strShopId>");
			sbReturn.append("<intVip>" + tempCoupon.getIntVip() + "</intVip>");
			sbReturn.append("<intRecommend>" + tempCoupon.getIntRecommend() + "</intRecommend>");
			sbReturn.append("<flaPrice>" + tempCoupon.getFlaPrice() + "</flaPrice>");
			sbReturn.append("<strSmallImg>" + tempCoupon.getStrSmallImg() + "</strSmallImg>");
			sbReturn.append("<strLargeImg>" + tempCoupon.getStrLargeImg() + "</strLargeImg>");
			sbReturn.append("<strPrintImg>" + tempCoupon.getStrPrintImg() + "</strPrintImg>");
			sbReturn.append("<strInstruction>" + tempCoupon.getStrInstruction() + "</strInstruction>");
			sbReturn.append("<strIntro>" + tempCoupon.getStrIntro() + "</strIntro>");
			sbReturn.append("</coupon>");
		}
		return sbReturn;
	}

}
