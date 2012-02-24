<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@ include file="../include/jsp/head.jsp"%>
<%




String strUrl="coupon_stat.jsp";
CouponInput member = null;
CouponInput obj = new CouponInput(globa);
boolean flag = false;
//查询条件
String dtCreateTime = ParamUtil.getString(request, "dtCreateTime", "");
String tWhere = " WHERE 1=1";
if (!dtCreateTime.equals(""))
{
	obj.State(dtCreateTime);	
	flag = true; 	
	Vector<CouponInput>vctCouponInputs=obj.returnDistinctStrShopId();
	Shop shop=new Shop(globa);
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>商家ID+名称</td><td>有价券数量</td><td>统计时间</td></tr>");
	if (vctCouponInputs.size() != 0)
	{
		for (int i = 0; i < vctCouponInputs.size(); i++)
		{ 
				if("商家".equals(globa.userSession.getStrCssType()) && !vctCouponInputs.get(i).getStrShopId().equals(globa.userSession.getStrShopid()))
				{
					continue;
				}
		      	sb.append("<tr><td>" + vctCouponInputs.get(i).getStrShopId() +"-"+shop.returnBizShopName("where strid='"+vctCouponInputs.get(i).getStrShopId()+"'")+ "</td>"+"<td>"
					+ obj.getCount("where strShopid='" + vctCouponInputs.get(i).getStrShopId() + "' and dtCreateTime LIKE '" + dtCreateTime + "%'")
					+ "</td><td>"+dtCreateTime.replace("-","年")+"月"+"</td>");
		}
	}
	sb.append("</tr></table>");
	String strFileName = "有价券统计表_" + dtCreateTime + ".xls";		
	response.setContentType("APPLICATION/*");
	response.setHeader("Content-Disposition", "attachment;filename=" + new String(strFileName.getBytes("gbk"), "ISO8859-1"));
	ServletOutputStream output = response.getOutputStream();		
	output.write(sb.toString().getBytes());

}
	
	//关闭数据库连接对象
	globa.closeCon();
%>