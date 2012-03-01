<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@ include file="../include/jsp/head.jsp"%>
<%



Coupon coupon=new Coupon(globa);
String strUrl="coupon_stat.jsp";
CouponInput member = null;
CouponInput obj = new CouponInput(globa);
boolean flag = false;
//查询条件
	Shop shop=new Shop(globa);
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>商家ID+名称</td><td>优惠券名称</td><td>有价券数量</td><td>总额</td><td>统计时间</td></tr>");
String dtCreateTime = ParamUtil.getString(request, "dtCreateTime", "");
String tWhere = " WHERE 1=1 ";
if (!dtCreateTime.equals(""))
{
	obj.State(dtCreateTime);	
	flag = true; 
	HashMap<String,Integer> hmCouponNo=new HashMap<String,Integer>();
	
	Vector<CouponInput>vctCouponInputs=obj.list("where  dtprinttime LIKE '" + dtCreateTime + "%'",0,0);
	for (int i = 0; i < vctCouponInputs.size(); i++)
	{
	    int sum=0;
		
		for (int j = 0; j < vctCouponInputs.size(); j++)
		{
			if(vctCouponInputs.get(i).getStrCouponId().equals(vctCouponInputs.get(j).getStrCouponId()))
			{
				sum+=1;
				
			}
		}
			hmCouponNo.put(vctCouponInputs.get(i).getStrCouponId(),sum);
	}
	if(!hmCouponNo.isEmpty())
	{
		
		
		Iterator iterator = hmCouponNo.entrySet().iterator();           
		while (iterator .hasNext()) {
		    Entry<String,Integer> entry = (Entry<String,Integer>) iterator .next();
		    Coupon couponTemp=coupon.show(" where strid='"+entry.getKey()+"'");
		    sb.append("<tr><td>");
		    sb.append( couponTemp.getStrShopId()+shop.returnBizShopName(" where strid='"+couponTemp.getStrShopId()+"'"));
		    sb.append("</td>");
		    sb.append("<td>");
		    sb.append(couponTemp.getStrName());
		    sb.append("</td>");
		    sb.append("<td>");
		    sb.append(entry.getValue());
		    sb.append("</td>");
		    sb.append("<td>");
		    sb.append(entry.getValue()*couponTemp.getFlaPrice());
		    sb.append("</td>");
		    sb.append("<td>");
		    sb.append(Format.getDateTime());
		    sb.append("</td></tr>");
		    
		   }
	}
	   
	
	
//	if (vctCouponInputs.size() != 0)
	//{
		//for (int i = 0; i < vctCouponInputs.size(); i++)
//		{ 
				//if("商家".equals(globa.userSession.getStrCssType()) && !vctCouponInputs.get(i).getStrShopId().equals(globa.userSession.getStrShopid()))
			//	{
				//	continue;
				//}
				
	//			
		//			sum=obj.getCount("where  dtCreateTime LIKE '" + dtCreateTime + "%'");
					
		  //  	    sb.append("<tr><td>" + vctCouponInputs.get(i).getStrShopId() +"-"
	//	    			+shop.returnBizShopName("where strid='"+vctCouponInputs.get(i).getStrShopId()+"'")+ "</td>"
	//	    			+"<td>"+coupon.show("where strid='"+vctCouponInputs.get(i).getStrCouponId()+"'").getStrName()   
	//	      			+ "</td><td>"+ sum
	//				+ "</td><td>"+sum*coupon.show("where strid='"+vctCouponInputs.get(i).getStrCouponId()+"'").getFlaPrice()+"</td><td>"+dtCreateTime.replace("-","年")+"月"+"</td>");
				
	//			
	//	}
	//}
	sb.append("</table>");
	String strFileName = "有价券统计表_" + dtCreateTime + ".xls";		
	response.setContentType("APPLICATION/*");
	response.setHeader("Content-Disposition", "attachment;filename=" + new String(strFileName.getBytes("gbk"), "ISO8859-1"));
	ServletOutputStream output = response.getOutputStream();		
	output.write(sb.toString().getBytes());
	
}
	
	//关闭数据库连接对象
	globa.closeCon();
%>