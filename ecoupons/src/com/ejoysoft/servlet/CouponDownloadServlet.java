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
		Globa globa = new Globa();
//		String strReturn = "OK";
//		String strTerminalNo = "002";
		String strTerminalNo = req.getParameter("strTerminalNo");
		String strReturn = req.getParameter("strReturn");
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		String strId = terminal.getStrId();
		if("OK".equals(strReturn)||"NO".equals(strReturn))
		{
			downLoadAlert.dealDataByTerminalId(strId, strReturn,"t_bz_coupon");
//			this.destroy();
			globa.closeCon();
			return;
		}
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		Terminal terminal2 = new Terminal(globa);
		try
		{
			if (terminal != null)
			{
				Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
				Coupon coupon = new Coupon(globa);
				String strWhere = "where strDataType='t_bz_coupon' and (intState=0 or intState=2) and strTerminalId='" + strId + "'";
				Coupon tempCoupon = new Coupon();
				vctAlerts = downLoadAlert.list(strWhere, 0, 0);
				boolean flagAdd = true;
				boolean flagUpdate = true;
				boolean flagDelete = true;
				sbReturn.append("<info>");
				if (vctAlerts.size() > 0)
				{
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempCoupon = coupon.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("add".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagAdd)
							{
								sbReturn.append("<coupons>");
								sbReturn.append("<operate>add</operate>");
								
								flagAdd = false;
							}
							if (returnSbContent(tempCoupon)!=null) {
								sbReturn.append(returnSbContent(tempCoupon));
//								terminal2.addState2(strId, "t_bz_coupon", vctAlerts.get(i).getStrDataId(), "add");						
							}
						}
					}
					if (!flagAdd)
					{
						sbReturn.append("</coupons>");
					}
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempCoupon = coupon.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("update".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagUpdate)
							{
								sbReturn.append("<coupons>");
								sbReturn.append("<operate>update</operate>");
								flagUpdate = false;
							}
							if (returnSbContent(tempCoupon)!=null) {
								sbReturn.append(returnSbContent(tempCoupon));
//								terminal2.addState2(strId, "t_bz_coupon", vctAlerts.get(i).getStrDataId(), "update");							
							}
						}
					}
					if (!flagUpdate)
					{
						sbReturn.append("</coupons>");
					}
					for (int i = 0; i < vctAlerts.size(); i++)
					{
						tempCoupon = coupon.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
						if ("delete".equals(vctAlerts.get(i).getStrDataOpeType()))
						{
							if (flagDelete)
							{
								sbReturn.append("<coupons>");
								sbReturn.append("<operate>delete</operate>");
								flagDelete = false;
							}
							sbReturn.append("<coupon>");
							sbReturn.append("<strId>" + vctAlerts.get(i).getStrDataId() + "</strId>");
							sbReturn.append("</coupon>");
//							terminal2.addState2(strId, "t_bz_coupon", vctAlerts.get(i).getStrDataId(), "delete");

						}
					}
					if (!flagDelete)
					{
						sbReturn.append("</coupons>");
					}
				} 
				sbReturn.append("</info>");

			} else
			{
				sbReturn.append("<return>update_error</return>");
			}
			if (!terminal2.updateState2(strId, "t_bz_coupon"))
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
			resp.getWriter().println(sbReturn.toString());
		}
	}

	/**
	 * 根据条件返回具体内容
	 * 
	 * @param tempTerminal
	 * @return
	 */
	// private StringBuffer returnSbContent(Coupon tempCoupon, String
	// strImagAddr)
	private StringBuffer returnSbContent(Coupon tempCoupon)
	{
		// String smallMageContent = "";
		// String LargeMageContent = "";
		// if (tempCoupon.getStrSmallImg() != null &&
		// tempCoupon.getStrSmallImg() != "")
		// {
		// smallMageContent = Base64.getPicBASE64(strImagAddr +
		// tempCoupon.getStrSmallImg());
		// }
		// if (tempCoupon.getStrLargeImg() != null &&
		// tempCoupon.getStrLargeImg() != "")
		// {
		// LargeMageContent = Base64.getPicBASE64(strImagAddr +
		// tempCoupon.getStrLargeImg());
		// }
		StringBuffer sbReturn = new StringBuffer();
		if (tempCoupon!=null) {
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
			// sbReturn.append("<strSmallImgContent>" + smallMageContent +
			// "</strSmallImgContent>");
			sbReturn.append("<strLargeImg>" + tempCoupon.getStrLargeImg() + "</strLargeImg>");
			// sbReturn.append("<strLargeImgContent>" + LargeMageContent +
			// "</strLargeImgContent>");
			sbReturn.append("<strPrintImg>" + tempCoupon.getStrPrintImg() + "</strPrintImg>");
			sbReturn.append("<strInstruction>" + tempCoupon.getStrInstruction() + "</strInstruction>");
			sbReturn.append("<strIntro>" + tempCoupon.getStrIntro() + "</strIntro>");
			sbReturn.append("</coupon>");
		}		
		return sbReturn;
	}

}
