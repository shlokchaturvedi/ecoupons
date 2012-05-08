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

import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.DownLoadAlert;
import com.ejoysoft.ecoupons.business.Shop;
import com.ejoysoft.ecoupons.business.Terminal;
import com.ejoysoft.ecoupons.business.TerminalPara;
import com.ejoysoft.ecoupons.business.TerminalTemplate;

public class TemplateParamServlet extends HttpServlet
{
	public TemplateParamServlet()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.execute(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.execute(req, resp);
	}
	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		StringBuffer sbReturn = new StringBuffer("<?xml version='1.0' encoding='utf-8'?> ");
		Globa globa = new Globa();
		try{
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			 String strTerminalNo = req.getParameter("strTerminalNo");
//			strTerminalNo = "000";
			HashMap<String, Terminal> hmTerminal = Terminal.hmTerminal;
			if (hmTerminal.get(strTerminalNo) != null)
			{
//				String strId = hmTerminal.get(strTerminalNo).getStrId();
				TerminalTemplate objTemplate0 = new TerminalTemplate(globa);
				Vector<TerminalTemplate> vctTempls = new Vector<TerminalTemplate>();
				Vector<String> vctModules = new Vector<String>();
				vctModules = objTemplate0.listAllModules();
				vctTempls = objTemplate0.list(" order by intsort", 0, 0);				
				sbReturn.append("<templates>");
				if (vctTempls.size() > 0)
				{
					for(int k=0;k<vctModules.size();k++)
					{
						String tempname = vctModules.get(k);
						if(tempname.trim().equals("top") || tempname.trim().equals("bottom"))
						{
							sbReturn.append("<"+tempname+">");
							for (int i = 0; i < vctTempls.size(); i++)
							{
								if(vctTempls.get(i).getStrModuleOfTempl().equals(tempname))
									sbReturn.append(returnTemplates(vctTempls.get(i),tempname));
							}
							sbReturn.append("</"+tempname+">");
						}
						else {
							
						}
					}					
					sbReturn.append("<middle>");
					for(int k=0;k<vctModules.size();k++)
					{
						String tempname = vctModules.get(k);
						if(!tempname.equals("top")&& !tempname.equals("bottom"))
						{
							sbReturn.append("<"+tempname+">");
							for (int i = 0; i < vctTempls.size(); i++)
							{
								if(vctTempls.get(i).getStrModuleOfTempl().equals(tempname))
									sbReturn.append(returnTemplates(vctTempls.get(i),tempname));
							}
							sbReturn.append("</"+tempname+">");
						}
						else {
							
						}
					}		
					sbReturn.append("</middle>");					
				}
				else {
					sbReturn.append("<params>notemplate</params>");
				}
				sbReturn.append("</templates>");
			}
			else {
				sbReturn.append("<params>terminal_erro</params>");
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sbReturn.append("<params>erro</params>");
		}finally {			
			globa.closeCon();
			resp.getWriter().println(sbReturn.toString());
		}
	}
	/**
	 * 封装
	 * @param template
	 * @return
	 */
	private StringBuffer returnContent(TerminalTemplate template)
	{
		StringBuffer sbReturn = new StringBuffer();
		if (template!=null) 
		{
			sbReturn.append("<strLocation>"+template.getStrLocation()+"</strLocation>");
			sbReturn.append("<strSize>"+template.getStrSize()+"</strSize>");
			sbReturn.append("<strBgImage>"+template.getStrBgImage()+"</strBgImage>");
			sbReturn.append("<strFontFamily>"+template.getStrFontFamily()+"</strFontFamily>");
			sbReturn.append("<strContent>"+template.getStrContent()+"</strContent>");
			sbReturn.append("<intFontSize>"+template.getIntFontSize()+"</intFontSize>");
			sbReturn.append("<strFontColor>"+template.getStrFontColor()+"</strFontColor>");
			sbReturn.append("<intSort>"+template.getIntSort()+"</intSort>");
		}
		return sbReturn;
	}
	
	 /** 分模块
	 * @param template
	 * @return
	 */
	private StringBuffer returnTemplates(TerminalTemplate objTemplate,String templname)
	{
		StringBuffer sbReturn = new StringBuffer();
		if (objTemplate!=null) 
		{
			if(templname.equals("top"))
			{
				if(objTemplate.getStrName().equals("frame"))
				{
					sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
				}
				if(objTemplate.getStrName().equals("marquee"))
				{
					sbReturn.append("<marquee>" + this.returnContent(objTemplate) + "</marquee>");
				}
				if(objTemplate.getStrName().equals("timer_CountDown"))
				{
					sbReturn.append("<timer_CountDown>" + this.returnContent(objTemplate) + "</timer_CountDown>");
				}
				if(objTemplate.getStrName().equals("btn_Hidden"))
				{
					sbReturn.append("<btn_Hidden>" + this.returnContent(objTemplate) + "</btn_Hidden>");
				}
			}else if(templname.equals("bottom")){
				if(objTemplate.getStrName().equals("frame"))
				{
					sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
				}
				if(objTemplate.getStrName().equals("home"))
				{
					sbReturn.append("<home>"+this.returnContent(objTemplate)+"</home>");
				}
				if(objTemplate.getStrName().equals("shop"))
				{
					sbReturn.append("<shop>" + this.returnContent(objTemplate) + "</shop>");
				}
				if(objTemplate.getStrName().equals("coupon"))
				{
					sbReturn.append("<coupon>" + this.returnContent(objTemplate) + "</coupon>");
				}if(objTemplate.getStrName().equals("homeP"))
				{
					sbReturn.append("<homeP>"+this.returnContent(objTemplate)+"</homeP>");
				}
				if(objTemplate.getStrName().equals("shopP"))
				{
					sbReturn.append("<shopP>" + this.returnContent(objTemplate) + "</shopP>");
				}
				if(objTemplate.getStrName().equals("couponP"))
				{
					sbReturn.append("<couponP>" + this.returnContent(objTemplate) + "</couponP>");
				}
				if(objTemplate.getStrName().equals("myInfo"))
				{
					sbReturn.append("<myInfo>" + this.returnContent(objTemplate) + "</myInfo>");
				}
				if(objTemplate.getStrName().equals("nearShop"))
				{
					sbReturn.append("<nearShop>"+this.returnContent(objTemplate)+"</nearShop>");
				}				if(objTemplate.getStrName().equals("myInfoP"))
				{
					sbReturn.append("<myInfoP>" + this.returnContent(objTemplate) + "</myInfoP>");
				}
				if(objTemplate.getStrName().equals("nearShopP"))
				{
					sbReturn.append("<nearShopP>"+this.returnContent(objTemplate)+"</nearShopP>");
				}
				if(objTemplate.getStrName().equals("vip"))
				{
					sbReturn.append("<vip>" + this.returnContent(objTemplate) + "</vip>");
				}if(objTemplate.getStrName().equals("vipP"))
				{
					sbReturn.append("<vipP>" + this.returnContent(objTemplate) + "</vipP>");
				}
				if(objTemplate.getStrName().equals("btnLastPage"))
				{
					sbReturn.append("<btnLastPage>" + this.returnContent(objTemplate) + "</btnLastPage>");
				}
				if(objTemplate.getStrName().equals("btnNextPage"))
				{
					sbReturn.append("<btnNextPage>" + this.returnContent(objTemplate) + "</btnNextPage>");
				}
				if(objTemplate.getStrName().equals("btnLastPageP"))
				{
					sbReturn.append("<btnLastPageP>" + this.returnContent(objTemplate) + "</btnLastPageP>");
				}
				if(objTemplate.getStrName().equals("btnNextPageP"))
				{
					sbReturn.append("<btnNextPageP>" + this.returnContent(objTemplate) + "</btnNextPageP>");
				}
			}else{
				if(templname.equals("home"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("recShopL"))
					{
						sbReturn.append("<recShopL>" + this.returnContent(objTemplate) + "</recShopL>");
					}
					if(objTemplate.getStrName().equals("shopName"))
					{
						sbReturn.append("<shopName>" + this.returnContent(objTemplate) + "</shopName>");
					}
					if(objTemplate.getStrName().equals("shopLastPage"))
					{
						sbReturn.append("<shopLastPage>" + this.returnContent(objTemplate) + "</shopLastPage>");
					}
					if(objTemplate.getStrName().equals("shopNextPage"))
					{
						sbReturn.append("<shopNextPage>"+this.returnContent(objTemplate)+"</shopNextPage>");
					}
					if(objTemplate.getStrName().equals("shopInfo"))
					{
						sbReturn.append("<shopInfo>" + this.returnContent(objTemplate) + "</shopInfo>");
					}
					if(objTemplate.getStrName().equals("recCouponL"))
					{
						sbReturn.append("<recCouponL>" + this.returnContent(objTemplate) + "</recCouponL>");
					}
					if(objTemplate.getStrName().equals("recCouponS"))
					{
						sbReturn.append("<recCouponS>" + this.returnContent(objTemplate) + "</recCouponS>");
					}
					if(objTemplate.getStrName().equals("recShopIcon"))
					{
						sbReturn.append("<recShopIcon>"+this.returnContent(objTemplate)+"</recShopIcon>");
					}
					if(objTemplate.getStrName().equals("recCouponIcon"))
					{
						sbReturn.append("<recCouponIcon>" + this.returnContent(objTemplate) + "</recCouponIcon>");
					}
					if(objTemplate.getStrName().equals("favIcon"))
					{
						sbReturn.append("<favIcon>" + this.returnContent(objTemplate) + "</favIcon>");
					}
					if(objTemplate.getStrName().equals("printIcon"))
					{
						sbReturn.append("<printIcon>" + this.returnContent(objTemplate) + "</printIcon>");
					}
					if(objTemplate.getStrName().equals("shopLastPageP"))
					{
						sbReturn.append("<shopLastPageP>"+this.returnContent(objTemplate)+"</shopLastPageP>");
					}if(objTemplate.getStrName().equals("shopNextPageP"))
					{
						sbReturn.append("<shopNextPageP>"+this.returnContent(objTemplate)+"</shopNextPageP>");
					}if(objTemplate.getStrName().equals("shopInfoP"))
					{
						sbReturn.append("<shopInfoP>"+this.returnContent(objTemplate)+"</shopInfoP>");
					}
//					sbReturn.append("</home>");
				}
				else if(templname.equals("shopInfo"))
				{
//					sbReturn.append("<shopinfo>");
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("shopL"))
					{
						sbReturn.append("<shopL>" + this.returnContent(objTemplate) + "</shopL>");
					}
					if(objTemplate.getStrName().equals("shopName"))
					{
						sbReturn.append("<shopName>" + this.returnContent(objTemplate) + "</shopName>");
					}
					if(objTemplate.getStrName().equals("shopAdd"))
					{
						sbReturn.append("<shopAdd>" + this.returnContent(objTemplate) + "</shopAdd>");
					}
					if(objTemplate.getStrName().equals("shopIntro"))
					{
						sbReturn.append("<shopIntro>"+this.returnContent(objTemplate)+"</shopIntro>");
					}
					if(objTemplate.getStrName().equals("btnReturn"))
					{
						sbReturn.append("<btnReturn>" + this.returnContent(objTemplate) + "</btnReturn>");
					}
					if(objTemplate.getStrName().equals("couponL"))
					{
						sbReturn.append("<couponL>" + this.returnContent(objTemplate) + "</couponL>");
					}
					if(objTemplate.getStrName().equals("favIcon"))
					{
						sbReturn.append("<favIcon>" + this.returnContent(objTemplate) + "</favIcon>");
					}
					if(objTemplate.getStrName().equals("printIcon"))
					{
						sbReturn.append("<printIcon>" + this.returnContent(objTemplate) + "</printIcon>");
					}
				}else if(templname.equals("shop"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("shopTopS"))
					{
						sbReturn.append("<shopTopS>" + this.returnContent(objTemplate) + "</shopTopS>");
					}
					if(objTemplate.getStrName().equals("shopMiddleS"))
					{
						sbReturn.append("<shopMiddleS>" + this.returnContent(objTemplate) + "</shopMiddleS>");
					}
					if(objTemplate.getStrName().equals("shopBottomS"))
					{
						sbReturn.append("<shopBottomS>" + this.returnContent(objTemplate) + "</shopBottomS>");
					}
					if(objTemplate.getStrName().equals("shopTradeLab"))
					{
						sbReturn.append("<shopTradeLab>"+this.returnContent(objTemplate)+"</shopTradeLab>");
					}
					if(objTemplate.getStrName().equals("shopLastPageTop"))
					{
						sbReturn.append("<shopLastPageTop>" + this.returnContent(objTemplate) + "</shopLastPageTop>");
					}
					if(objTemplate.getStrName().equals("shopNextPageTop"))
					{
						sbReturn.append("<shopNextPageTop>" + this.returnContent(objTemplate) + "</shopNextPageTop>");
					}
					if(objTemplate.getStrName().equals("shopLastPageMiddle"))
					{
						sbReturn.append("<shopLastPageMiddle>" + this.returnContent(objTemplate) + "</shopLastPageMiddle>");
					}
					if(objTemplate.getStrName().equals("shopNextPageMiddle"))
					{
						sbReturn.append("<shopNextPageMiddle>"+this.returnContent(objTemplate)+"</shopNextPageMiddle>");
					}
					if(objTemplate.getStrName().equals("shopLastPageBottom"))
					{
						sbReturn.append("<shopLastPageBottom>" + this.returnContent(objTemplate) + "</shopLastPageBottom>");
					}
					if(objTemplate.getStrName().equals("shopNextPageBottom"))
					{
						sbReturn.append("<shopNextPageBottom>" + this.returnContent(objTemplate) + "</shopNextPageBottom>");
					}
					if(objTemplate.getStrName().equals("shopLastPageTopP"))
					{
						sbReturn.append("<shopLastPageTopP>" + this.returnContent(objTemplate) + "</shopLastPageTopP>");
					}
					if(objTemplate.getStrName().equals("shopNextPageTopP"))
					{
						sbReturn.append("<shopNextPageTopP>" + this.returnContent(objTemplate) + "</shopNextPageTopP>");
					}
					if(objTemplate.getStrName().equals("shopLastPageMiddleP"))
					{
						sbReturn.append("<shopLastPageMiddleP>" + this.returnContent(objTemplate) + "</shopLastPageMiddleP>");
					}
					if(objTemplate.getStrName().equals("shopNextPageMiddleP"))
					{
						sbReturn.append("<shopNextPageMiddleP>"+this.returnContent(objTemplate)+"</shopNextPageMiddleP>");
					}
					if(objTemplate.getStrName().equals("shopLastPageBottomP"))
					{
						sbReturn.append("<shopLastPageBottomP>" + this.returnContent(objTemplate) + "</shopLastPageBottomP>");
					}
					if(objTemplate.getStrName().equals("shopNextPageBottomP"))
					{
						sbReturn.append("<shopNextPageBottomP>" + this.returnContent(objTemplate) + "</shopNextPageBottomP>");
					}	
				}else if(templname.equals("coupon"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("couponL"))
					{
						sbReturn.append("<couponL>" + this.returnContent(objTemplate) + "</couponL>");
					}
					if(objTemplate.getStrName().equals("favIcon"))
					{
						sbReturn.append("<favIcon>" + this.returnContent(objTemplate) + "</favIcon>");
					}
					if(objTemplate.getStrName().equals("printIcon"))
					{
						sbReturn.append("<printIcon>" + this.returnContent(objTemplate) + "</printIcon>");
					}
					if(objTemplate.getStrName().equals("couponS"))
					{
						sbReturn.append("<couponS>"+this.returnContent(objTemplate)+"</couponS>");
					}
					if(objTemplate.getStrName().equals("btnLastPage"))
					{
						sbReturn.append("<btnLastPage>" + this.returnContent(objTemplate) + "</btnLastPage>");
					}
					if(objTemplate.getStrName().equals("btnNextPage"))
					{
						sbReturn.append("<btnNextPage>" + this.returnContent(objTemplate) + "</btnNextPage>");
					}
					if(objTemplate.getStrName().equals("btnLastPageP"))
					{
						sbReturn.append("<btnLastPageP>" + this.returnContent(objTemplate) + "</btnLastPageP>");
					}
					if(objTemplate.getStrName().equals("btnNextPageP"))
					{
						sbReturn.append("<btnNextPageP>" + this.returnContent(objTemplate) + "</btnNextPageP>");
					}
					if(objTemplate.getStrName().equals("rankList"))
					{
						sbReturn.append("<rankList>" + this.returnContent(objTemplate) + "</rankList>");
					}
					if(objTemplate.getStrName().equals("newCoupon"))
					{
						sbReturn.append("<newCoupon>"+this.returnContent(objTemplate)+"</newCoupon>");
					}
					if(objTemplate.getStrName().equals("recCoupon"))
					{
						sbReturn.append("<recCoupon>" + this.returnContent(objTemplate) + "</recCoupon>");
					}		
				}else if(templname.equals("myInfo"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("myFavLab"))
					{
						sbReturn.append("<myFavLab>" + this.returnContent(objTemplate) + "</myFavLab>");
					}
					if(objTemplate.getStrName().equals("myFavLastPage"))
					{
						sbReturn.append("<myFavLastPage>" + this.returnContent(objTemplate) + "</myFavLastPage>");
					}
					if(objTemplate.getStrName().equals("myFavNextPage"))
					{
						sbReturn.append("<myFavNextPage>" + this.returnContent(objTemplate) + "</myFavNextPage>");
					}if(objTemplate.getStrName().equals("myFavLastPageP"))
					{
						sbReturn.append("<myFavLastPageP>" + this.returnContent(objTemplate) + "</myFavLastPageP>");
					}
					if(objTemplate.getStrName().equals("myFavNextPageP"))
					{
						sbReturn.append("<myFavNextPageP>" + this.returnContent(objTemplate) + "</myFavNextPageP>");
					}
					if(objTemplate.getStrName().equals("favCouponL"))
					{
						sbReturn.append("<favCouponL>"+this.returnContent(objTemplate)+"</favCouponL>");
					}
					if(objTemplate.getStrName().equals("favCouponS"))
					{
						sbReturn.append("<favCouponS>" + this.returnContent(objTemplate) + "</favCouponS>");
					}
					if(objTemplate.getStrName().equals("printIconTop"))
					{
						sbReturn.append("<printIconTop>" + this.returnContent(objTemplate) + "</printIconTop>");
					}
					if(objTemplate.getStrName().equals("lastCostIcon"))
					{
						sbReturn.append("<lastCostIcon>" + this.returnContent(objTemplate) + "</lastCostIcon>");
					}
					if(objTemplate.getStrName().equals("lastCostIcon"))
					{
						sbReturn.append("<lastCostIcon>"+this.returnContent(objTemplate)+"</lastCostIcon>");
					}
					if(objTemplate.getStrName().equals("CostLastPage"))
					{
						sbReturn.append("<CostLastPage>" + this.returnContent(objTemplate) + "</CostLastPage>");
					}	
					if(objTemplate.getStrName().equals("ContNextPage"))
					{
						sbReturn.append("<ContNextPage>" + this.returnContent(objTemplate) + "</ContNextPage>");
					}if(objTemplate.getStrName().equals("CostLastPageP"))
					{
						sbReturn.append("<CostLastPageP>" + this.returnContent(objTemplate) + "</CostLastPageP>");
					}	
					if(objTemplate.getStrName().equals("ContNextPageP"))
					{
						sbReturn.append("<ContNextPageP>" + this.returnContent(objTemplate) + "</ContNextPageP>");
					}
					if(objTemplate.getStrName().equals("couponCostL"))
					{
						sbReturn.append("<couponCostL>" + this.returnContent(objTemplate) + "</couponCostL>");
					}
					if(objTemplate.getStrName().equals("couponCostS"))
					{
						sbReturn.append("<couponCostS>" + this.returnContent(objTemplate) + "</couponCostS>");
					}
					if(objTemplate.getStrName().equals("favIconBottom"))
					{
						sbReturn.append("<favIconBottom>"+this.returnContent(objTemplate)+"</favIconBottom>");
					}
					if(objTemplate.getStrName().equals("printIconBottom"))
					{
						sbReturn.append("<printIconBottom>" + this.returnContent(objTemplate) + "</printIconBottom>");
					}			
				}else if(templname.equals("nearshop"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("nearShopL"))
					{
						sbReturn.append("<nearShopL>" + this.returnContent(objTemplate) + "</nearShopL>");
					}
					if(objTemplate.getStrName().equals("shopName"))
					{
						sbReturn.append("<shopName>" + this.returnContent(objTemplate) + "</shopName>");
					}
					if(objTemplate.getStrName().equals("shopInfo"))
					{
						sbReturn.append("<shopInfo>" + this.returnContent(objTemplate) + "</shopInfo>");
					}if(objTemplate.getStrName().equals("shopInfoP"))
					{
						sbReturn.append("<shopInfoP>" + this.returnContent(objTemplate) + "</shopInfoP>");
					}
					if(objTemplate.getStrName().equals("nearShopS"))
					{
						sbReturn.append("<nearShopS>"+this.returnContent(objTemplate)+"</nearShopS>");
					}
				}else if(templname.equals("ad"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
					if(objTemplate.getStrName().equals("elementTop"))
					{
						sbReturn.append("<elementTop>" + this.returnContent(objTemplate) + "</elementTop>");
					}
					if(objTemplate.getStrName().equals("elementBottom"))
					{
						sbReturn.append("<elementBottom>" + this.returnContent(objTemplate) + "</elementBottom>");
					}
					if(objTemplate.getStrName().equals("contentMiddle"))
					{
						sbReturn.append("<contentMiddle>" + this.returnContent(objTemplate) + "</contentMiddle>");
					}		
				}else if(templname.equals("waitdownload"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}			
				}else if(templname.equals("waitlogin"))
				{
					if(objTemplate.getStrName().equals("frame"))
					{
						sbReturn.append("<frame>"+this.returnContent(objTemplate)+"</frame>");
					}
				}					
			}
		}
		return sbReturn;
	}
}