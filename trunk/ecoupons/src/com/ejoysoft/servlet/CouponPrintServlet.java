package com.ejoysoft.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Format;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.ejoysoft.ecoupons.business.Coupon;
import com.ejoysoft.ecoupons.business.CouponPrint;
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

	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		globa = new Globa();
		db = new DbConnect();
		db = globa.db;
		try {
//			String strTerminalNo = "0001";
			String strTerminalNo = req.getParameter("strTerminalNo");
			Terminal obj = new Terminal(globa);
			HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
			Terminal terminal = hmTerminal.get(strTerminalNo);
		    Coupon coupon=new Coupon(globa);
			if(terminal!=null)
			{
				String strTerminalId = terminal.getStrId();
				//String strTerminalId = obj.getTerminalIdsByNames(strTerminalNo);
				//obj.updateState(strTerminalId);//更新终端状态
//				String strPrintContent = "1001$1329535194953076$2012-03-01 18:34:49$PASAFEWS@1002$1329535194953076$2012-03-01 18:34:49$PASAFEWD";
				String strPrintContent = req.getParameter("strPrintContent");
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
							 Coupon coupobj = new Coupon(globa);
							 Coupon obj1 = coupobj.show(" where strid='"+strCouponId+"'");
							 CouponPrint couponPrint = new CouponPrint(globa);
							 int limitNum = obj1.getIntPrintLimit();
							 int printNum = couponPrint.getCount(" where strcouponid='"+strCouponId+"'");
							 if(limitNum!=0 && printNum >= limitNum)
							 {
								 coupobj.setDtExpireTime(Format.getDateTime());
								 coupobj.updateExpireTime(strCouponId);									
							 }
							 String strSql = "insert into "+strTableName+"(strid,strmembercardno,strcouponid,strterminalid," +
								"dtprinttime,strcouponcode,intstate,strcreator,dtcreatetime) values(?,?,?,?,?,?,?,?,?)";
							 try {
					            db.prepareStatement(strSql);
					            db.setString(1, UID.getID());
					            db.setString(2, strMemberCardNo);
					            db.setString(3, strCouponId); 
					            db.setString(4, strTerminalId);  //strPWD
					            db.setString(5, dtPrintTime);
					            db.setString(6, strCouponCode);
					            db.setInt(7, 0);
					            db.setString(8, "system");
					            db.setString(9, com.ejoysoft.common.Format.getDateTime());
					            if (db.executeUpdate() > 0&&coupon.updateIntPrint(strCouponId)) 
					            { 	
					                Globa.logger0("添加优惠券打印记录信息", globa.loginName, globa.loginIp, strSql, "优惠券打印", "system");
					            } 			               
							 }catch (Exception e) {
					            System.out.println("添加优惠券打印记录异常");
					            e.printStackTrace();
					        	sbReturn.append("<return>add_erro</return>");
					        	break;
					         }									
						}
						else {

				        	sbReturn.append("<return>theprintcontent_erro</return>");	
				        	break;
						}
					    if(i==allContents.length-1)
					    {
					    	sbReturn.append("<return>OK</return>");	
					    }
					}	
		        	db.closeCon();
				}
			}		
			else {
	        	sbReturn.append("<return>printcontent_erro</return>");					
			}
		} catch (Exception e) {
        	sbReturn.append("<return>erro</return>");	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
		
	}

	Globa globa;
	DbConnect db;
}

