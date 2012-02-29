package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Format;
import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.CouponComment;
import com.ejoysoft.ecoupons.business.CouponFavourite;
import com.ejoysoft.ecoupons.business.CouponPrint;
import com.ejoysoft.ecoupons.business.Member;

public class MemberAuthServlet extends HttpServlet implements Servlet
{
	public MemberAuthServlet()
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
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		Globa globa = new Globa();
		try
		{			
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			CouponFavourite couponFavourite = new CouponFavourite(globa);
			CouponPrint couponPrint = new CouponPrint(globa);
			resp.setCharacterEncoding("utf-8");
			String strCardNo = req.getParameter("strCardNo");
//			String strCardNo = "3897";
			
			sbReturn.append("<info>");
			Member member = new Member(globa);
			Vector<CouponFavourite> vctCouponFavourites = new Vector<CouponFavourite>();
			Vector<CouponPrint> vctCouponPrints = new Vector<CouponPrint>();
			String nowdate = Format.getDateTime();
			if (member.getCount(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'") > 0)
			{
				Member memberTemp=member.show(" where strcardno='"+strCardNo+"' and dtactivetime <='"+nowdate+"' and dtexpiretime >='"+nowdate+"'");
				sbReturn.append("<auth>yes</auth>");
				sbReturn.append("<intType>"+memberTemp.getIntType()+"</intType>");
				sbReturn.append("<strMobileNo>"+memberTemp.getStrMobileNo()+"</strMobileNo>");
				//2012131增加手机号码字段
				vctCouponFavourites = couponFavourite.list("where strmembercardno='" + strCardNo+"'", 0, 0);
				sbReturn.append("<favourite>");
				for (int i = 0; i < vctCouponFavourites.size(); i++)
				{
					sbReturn.append("<couponid>");
					sbReturn.append(vctCouponFavourites.get(i).getStrCouponId());
					sbReturn.append("</couponid>");
				}
				sbReturn.append("</favourite>");
				sbReturn.append("<history>");
				vctCouponPrints = couponPrint.list("where strMemberCardNo='" + strCardNo+"'", 0, 0);
				for (int i = 0; i < vctCouponPrints.size(); i++)
				{
					sbReturn.append("<couponid>");
					sbReturn.append(vctCouponPrints.get(i).getStrCouponId());
					sbReturn.append("</couponid>");
				}
				sbReturn.append("</history>");
			} else
			{
				sbReturn.append("<auth>no</auth>");
			}
			sbReturn.append("</info>");			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sbReturn.append("<info><auth>no</auth></info>");	
		}finally {			
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
	}
}