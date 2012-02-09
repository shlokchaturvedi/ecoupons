package com.ejoysoft.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.CouponComment;
import com.ejoysoft.ecoupons.business.CouponFavourite;
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

	private void execute(HttpServletRequest req, HttpServletResponse resp)
	{
//		globa.initialize(getServletContext(),req,resp); 
		Globa globa = new Globa();
		try
		{
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CouponFavourite couponFavourite = new CouponFavourite(globa);
		CouponComment couponComment = new CouponComment(globa);
		resp.setCharacterEncoding("utf-8");
		String strCardNo = req.getParameter("strCardNo");
//		String strCardNo = "3897";
		
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		sbReturn.append("<info>");
		Member member = new Member(globa);
		Vector<CouponFavourite> vctCouponFavourites = new Vector<CouponFavourite>();
		Vector<CouponComment> vctCouponComments = new Vector<CouponComment>();
		if (member.getCount(" where strcardno='" + strCardNo+"'") > 0)
		{
			
			Member memberTemp=member.show(" where strcardno='" + strCardNo+"'");
			sbReturn.append("<auth>yes</auth>");
			sbReturn.append("<intType>"+memberTemp.getIntType()+"</intType>");
			sbReturn.append("<strMobileNo>"+memberTemp.getStrMobileNo()+"</strMobileNo>");
			//2012131增加手机号码字段
			vctCouponFavourites = couponFavourite.list("where strmembercardno=" + strCardNo, 0, 0);
			sbReturn.append("<favourite>");
			for (int i = 0; i < vctCouponFavourites.size(); i++)
			{
				sbReturn.append("<couponid>");
				sbReturn.append(vctCouponFavourites.get(i).getStrCouponId());
				sbReturn.append("</couponid>");
			}
			sbReturn.append("</favourite>");
			sbReturn.append("<history>");
			vctCouponComments = couponComment.list("where strMemberCardNo=" + strCardNo, 0, 0);
			for (int i = 0; i < vctCouponComments.size(); i++)
			{
				sbReturn.append("<couponid>");
				sbReturn.append(vctCouponComments.get(i).getStrCouponId());
				sbReturn.append("</couponid>");
			}
			sbReturn.append("</history>");
		} else
		{
			sbReturn.append("<auth>no</auth>");
		}
		try
		{
			sbReturn.append("</info>");
			System.out.println(sbReturn.toString());
			
			resp.getWriter().print(sbReturn.toString());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//关闭数据库连接对象
	    globa.closeCon();
	}
}