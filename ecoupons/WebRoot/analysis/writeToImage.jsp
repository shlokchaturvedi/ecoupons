 <%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.common.Globa,
				 com.ejoysoft.util.ParamUtil,				
				 com.ejoysoft.common.exception.NoRightException,
				 com.ejoysoft.ecoupons.business.ShopAnalysis,
				 com.ejoysoft.ecoupons.business.TerminalAnalysis,
				 com.ejoysoft.ecoupons.business.BarChart ,
				 com.ejoysoft.ecoupons.business.PieChart "%>
<%@ include file="../include/jsp/head.jsp"%>
<%

if(!globa.userSession.hasRight("130"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");

	String stime = ParamUtil.getString(request,"stime","1000-01-01");
	String etime = ParamUtil.getString(request,"etime","9999-12-31");
	String twhere = (String)session.getAttribute("twhere");
	
	String tag = ParamUtil.getString(request,"tag","");
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
    else if(tag.trim().equals("shopbiz"))
      {
	        ShopAnalysis obj = new ShopAnalysis(globa);
			obj.setStime(stime);
			obj.setEtime(etime);
			Vector<ShopAnalysis> vector = obj.getShopAnalysisResult(twhere);
			double resultdata[] = new double[vector.size()];
			String[] couponnum = new String[]{"优惠券数量总计", "优惠券打印次数总计"};
			String[] shops = new String[vector.size()];
			double totalnum=0.0;
			for(int j=0;j<vector.size();j++)
			{
				ShopAnalysis obj0 = new ShopAnalysis();
				obj0 = vector.get(j);
				totalnum += obj0.getShopPrintNum()*1.0;
			}
			for(int i=0;i<vector.size();i++)
			{
				ShopAnalysis obj0 = new ShopAnalysis();
				obj0 = vector.get(i);
				resultdata[i]= obj0.getShopPrintNum()*1.0/totalnum;
				shops[i] = obj0.getShopName();
			}
		   PieChart objPieChart = new PieChart();
	       graphURL =objPieChart.returnPieResult(request,"商家消费统计结果",shops,resultdata,setime);
          }
        
%>
<iframe align="middle" marginwidth="50" frameborder=0 id=frmChart name=frmChart src="<%= graphURL %>" scrolling="yes" style=" HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 3"></iframe>
