package com.ejoysoft.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.Terminal;

@SuppressWarnings("serial")
public class CouponPrintServlet extends HttpServlet implements Servlet
{
	public CouponPrintServlet()
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

	private void execute(HttpServletRequest req, HttpServletResponse resp)
	{
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		globa = new Globa();
		String strId = UID.getID();
		db = new DbConnect();
		db = globa.db;
		String strTerminalNo = req.getParameter("strTerminalNo");
//		String strTerminalNo ="2222";
		Terminal obj = new Terminal(globa);
		String strTerminalId = obj.getTerminalIdsByNames(strTerminalNo);
		String strPrintContent = req.getParameter("strPrintContent");
//		String strPrintContent="333$111$2011-12-30 15:44:14$2343";
	    String strTableName ="t_bz_coupon_print";
		if(strPrintContent!=null)
		{
			String[] allContents = strPrintContent.split("@");
			for(int i=0;i<allContents.length;i++)
			{
				if(allContents[i]!=null)
				{
					String[] perContent = allContents[i].split("[$]"); 
					String strMemberCardNo = perContent[0];
					String strCouponId = perContent[1];
					String dtPrintTime = perContent[2];
					String strCouponCode = perContent[3];
					this.editLimitNum(strCouponId);
					String strSql = "insert into "+strTableName+"(strid,strmembercardno,strcouponid,strterminalid," +
							"dtprinttime,strcouponcode,intstate,strcreator,dtcreatetime) values(?,?,?,?,?,?,?,?,?)";
					try {
			            db.prepareStatement(strSql);
			            db.setString(1, strId);
			            db.setString(2, strMemberCardNo);
			            db.setString(3, strCouponId); 
			            db.setString(4, strTerminalId);  //strPWD
			            db.setString(5, dtPrintTime);
			            db.setString(6, strCouponCode);
			            db.setInt(7, 0);
			            db.setString(8, "system");
			            db.setString(9, com.ejoysoft.common.Format.getDateTime());
			            if (db.executeUpdate() > 0) { 	    
			                Globa.logger0("添加优惠券打印记录信息", globa.loginName, globa.loginIp, strSql, "优惠券打印", "system");
			            } 			               
					}catch (Exception e) {
			            System.out.println("添加优惠券打印记录异常");
			            e.printStackTrace();
			        }					
				}
			}	
        	sbReturn.append("<return>OK</return>");	
        	db.closeCon();
		}
		else {
			sbReturn.append("<return>no</return>");
		}
		try {
			resp.getWriter().println(sbReturn.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//修改优惠券打印次数
	public void editLimitNum(String strCouponId)
	{
		Coupon obj = new Coupon(globa);
		Coupon obj1 = obj.show(" where strid='"+strCouponId+"'");
		int LimitNum = obj1.getIntPrintLimit();	
		int newLimitNum = LimitNum - 1;
		String strSql="";
		if(newLimitNum >0)
			strSql = "update t_bz_coupon set intprintlimit='"+newLimitNum+"' where strid='"+strCouponId+"'";
		else if(newLimitNum <= 0)
			strSql = "update t_bz_coupon set intprintlimit='0' and dtexpiretime='"+com.ejoysoft.common.Format.getDateTime()+"' where strid='"+strCouponId+"'";
		try {
			db.executeUpdate(strSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Globa globa;
	DbConnect db;
}

