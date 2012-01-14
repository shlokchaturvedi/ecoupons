package com.ejoysoft.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
import com.ejoysoft.ecoupons.system.SysPara;

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

	private void execute(HttpServletRequest req, HttpServletResponse resp)
	{
		// TODO Auto-generated method stub
		// String strTerminalNo = req.getParameter("strCardNo");
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
//		String strTerminalNo = "23";
		HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
		Terminal terminal = hmTerminal.get(strTerminalNo);
		Terminal terminal2 = new Terminal(globa);
		String strId = terminal.getStrId();
//		System.out.println(strId);
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		Vector<DownLoadAlert> vctAlerts = new Vector<DownLoadAlert>();
        String strImagAddr=req.getSession().getServletContext().getRealPath(req.getRequestURI());
        strImagAddr=strImagAddr.replace("\\ecoupons\\servlet\\CouponDownload", "\\coupon\\images\\");
//        System.out.println(strImagAddr);
		Coupon coupon = new Coupon(globa);
		String strWhere = "where strDataType='t_bz_coupon' and intState=0 and strTerminalId='" + strId + "'";
		Coupon tempCoupon = new Coupon();
		vctAlerts = downLoadAlert.list(strWhere, 0, 0);
		boolean flagAdd = true;
		boolean flagUpdate = true;
		boolean flagDelete = true;
		if (vctAlerts.size() > 0)
		{

			for (int i = 0; i < vctAlerts.size(); i++)
			{
				tempCoupon = coupon.show("where strid='" + vctAlerts.get(i).getStrDataId() + "'");
				if ("add".equals(vctAlerts.get(i).getStrDataOpeType()))
				{
					String smallMageContent="";
					String LargeMageContent="";
					
					if (flagAdd)
					{
						sbReturn.append("<coupons>");
						sbReturn.append("<operate>add</operate>");
						flagAdd = false;
					}
					if (tempCoupon.getStrSmallImg()!=null&&tempCoupon.getStrSmallImg()!="")
					{
						smallMageContent=Base64.getPicBASE64(strImagAddr+tempCoupon.getStrSmallImg());
					}
					if (tempCoupon.getStrLargeImg()!=null&&tempCoupon.getStrLargeImg()!="")
					{
						LargeMageContent=Base64.getPicBASE64(strImagAddr+tempCoupon.getStrLargeImg());
					}
					sbReturn.append("<coupon>");
					sbReturn.append("<strId>" + vctAlerts.get(i).getStrDataId() + "</strId>");
					sbReturn.append("<strName>" + tempCoupon.getStrName() + "</strName>");
					sbReturn.append("<dtActiveTime>" + tempCoupon.getDtActiveTime() + "</dtActiveTime>");
					sbReturn.append("<dtExpireTime>" + tempCoupon.getDtExpireTime() + "</dtExpireTime>");
					sbReturn.append("<strShopId>" + tempCoupon.getStrShopId() + "</strShopId>");
					sbReturn.append("<intVip>" + tempCoupon.getIntVip() + "</intVip>");
					sbReturn.append("<flaPrice>" + tempCoupon.getFlaPrice() + "</flaPrice>");
					sbReturn.append("<intPrintLimit>" + tempCoupon.getIntPrintLimit() + "</intPrintLimit>");
					sbReturn.append("<strSmallImg>" + tempCoupon.getStrSmallImg() + "</strSmallImg>");
					sbReturn.append("<strSmallImgContent>" + smallMageContent + "</strSmallImgContent>");
					sbReturn.append("<strLargeImg>" + tempCoupon.getStrLargeImg() + "</strLargeImg>");
					sbReturn.append("<strLargeImgContent>" + LargeMageContent + "</strLargeImgContent>");
					sbReturn.append("</coupon>");
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
					String smallMageContent="";
					String LargeMageContent="";
					if (flagUpdate)
					{
						sbReturn.append("<coupons>");
						sbReturn.append("<operate>update</operate>");
						flagUpdate = false;
					}
					if (tempCoupon.getStrSmallImg()!=null&&tempCoupon.getStrSmallImg()!="")
					{
						smallMageContent=Base64.getPicBASE64(strImagAddr+tempCoupon.getStrSmallImg());
					}
					if (tempCoupon.getStrLargeImg()!=null&&tempCoupon.getStrLargeImg()!="")
					{
						LargeMageContent=Base64.getPicBASE64(strImagAddr+tempCoupon.getStrLargeImg());
					}
					sbReturn.append("<coupon>");
					sbReturn.append("<strId>" + vctAlerts.get(i).getStrDataId() + "</strId>");
					sbReturn.append("<strName>" + tempCoupon.getStrName() + "</strName>");
					sbReturn.append("<dtActiveTime>" + tempCoupon.getDtActiveTime() + "</dtActiveTime>");
					sbReturn.append("<dtExpireTime>" + tempCoupon.getDtExpireTime() + "</dtExpireTime>");
					sbReturn.append("<strShopId>" + tempCoupon.getStrShopId() + "</strShopId>");
					sbReturn.append("<intVip>" + tempCoupon.getIntVip() + "</intVip>");
					sbReturn.append("<flaPrice>" + tempCoupon.getFlaPrice() + "</flaPrice>");
					sbReturn.append("<intPrintLimit>" + tempCoupon.getIntPrintLimit() + "</intPrintLimit>");
					sbReturn.append("<strSmallImg>" + tempCoupon.getStrSmallImg() + "</strSmallImg>");
					sbReturn.append("<strSmallImgContent>" + smallMageContent + "</strSmallImgContent>");
					sbReturn.append("<strLargeImg>" + tempCoupon.getStrLargeImg() + "</strLargeImg>");
					sbReturn.append("<strLargeImgContent>" + LargeMageContent + "</strLargeImgContent>");
					sbReturn.append("</coupon>");
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
				}
			}
			if (!flagDelete)
			{
				sbReturn.append("</coupons>");
			}
		}
		if (terminal2.updateState(strId,"t_bz_coupon"))
		{
//			System.out.println(sbReturn.toString());
			try
			{
				resp.getWriter().print(sbReturn.toString());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		//关闭数据库连接对象
	    globa.closeCon();
	}
	
}
