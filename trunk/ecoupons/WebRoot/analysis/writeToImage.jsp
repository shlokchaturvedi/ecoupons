 <%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.common.Globa,
				 com.ejoysoft.util.ParamUtil,				
				 com.ejoysoft.common.exception.NoRightException,
				 com.ejoysoft.ecoupons.business.ShopAnalysis,
				 com.ejoysoft.ecoupons.business.TerminalAnalysis,
				 com.ejoysoft.ecoupons.business.BarChart "%>
<%@ include file="../include/jsp/head.jsp"%>
<%

if(!globa.userSession.hasRight("130"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");

	System.out.println("22222222222111111111111111111111111");
	String stime = ParamUtil.getString(request,"stime","1000-01-01");
	String etime = ParamUtil.getString(request,"etime","9999-12-31");
	String twhere = ParamUtil.getString(request,"twhere","");
	String tag = ParamUtil.getString(request,"tag","");
	System.out.println(tag+"111111111111111111111111");
	String setime = "";
	String graphURL="";
	if(stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		setime = "所有";
	}
	else if(stime.equals("1000-01-01")&&!etime.equals("9999-12-30"))
	{
		setime =etime+ "之前";
	}
	else if(!stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		setime =stime+ "之后";
	}
	else setime = stime+"至"+etime;
	
	if(tag.trim().equals("shopanalyse"))
	{
	    ShopAnalysis obj = new ShopAnalysis(globa);
		obj.setStime(stime);
		obj.setEtime(etime);
		Vector<ShopAnalysis> vector = obj.getShopAnalysisResult(twhere);
		double resultdata[][] = new double[2][vector.size()];
		String[] couponnum = new String[]{"优惠券数量总计", "优惠券打印次数总计"};
		String[] shops = new String[vector.size()];
		for(int i=0;i<vector.size();i++)
		{
			ShopAnalysis obj0 = new ShopAnalysis();
			obj0 = vector.get(i);
			resultdata[0][i] = obj0.getShopCouponNum()*1.0;
			resultdata[1][i]= obj0.getShopPrintNum()*1.0;
			shops[i] = obj0.getShopName();
		}
	 BarChart objBarChart = new BarChart();
     graphURL =objBarChart.returnBarResult(request,"商家统计分析结果",shops, couponnum,resultdata,setime,"商家（名称-分部）");
		
    }
    else if(tag.trim().equals("terminalanalyse"))
    {
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
		obj.setStime(stime);
		obj.setEtime(etime);
		Vector<TerminalAnalysis> vector = obj.getTermianlAnalysisResult(twhere);
		double resultdata[][] = new double[2][vector.size()];
		String[] couponnum = new String[]{"优惠券数量总计", "优惠券打印次数总计"};
		String[] shops = new String[vector.size()];
		for(int i=0;i<vector.size();i++)
		{
			TerminalAnalysis obj0 = new TerminalAnalysis();
			obj0 = vector.get(i);
			resultdata[0][i] = obj0.getTerminalCouponNum()*1.0;
			resultdata[1][i]= obj0.getTerminalPrintNum()*1.0;
			shops[i] = obj0.getTerminalNo();
		}
	   BarChart objBarChart = new BarChart();
       graphURL =objBarChart.returnBarResult(request,"终端统计分析结果",shops, couponnum,resultdata,setime,"终端（编号）");
		
    }
	  
%>
<iframe frameborder=0 id=frmChart name=frmChart src="<%= graphURL %>" scrolling="yes" style=" HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 3"></iframe>
