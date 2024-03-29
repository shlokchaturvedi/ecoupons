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
<%@page import="java.math.BigDecimal"%>
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
	String titile="";
	if(tag.trim().equals("shopanalyse"))
	{	
		titile="商家统计分析结果";
	    ShopAnalysis obj = new ShopAnalysis(globa);
		obj.setStime(stime);
		obj.setEtime(etime);
		Vector<ShopAnalysis> vector = obj.getShopAnalysisResult(twhere);
		double resultdata[][] = new double[2][vector.size()];
		String[] couponnum = new String[]{"优惠券数量总计", "优惠券打印次数总计"};
		String[] shops = new String[vector.size()];
		double maxnum = 0;
		double maxnum1 = 0;
		double maxnum2 = 0;
		for(int i=0;i<vector.size();i++)
		{
			ShopAnalysis obj0 = new ShopAnalysis();
			obj0 = vector.get(i);
			resultdata[0][i] = obj0.getShopCouponNum()*1.0;
			if(maxnum<resultdata[0][i])
			 	maxnum = resultdata[0][i];
		    resultdata[1][i]= obj0.getShopPrintNum()*1.0;
			if(maxnum1 < resultdata[1][i])
			 	maxnum1 = resultdata[1][i];
			shops[i] = obj0.getShopName();
		}
		if(maxnum > maxnum1)
			maxnum2 = maxnum*8.95/8.5;
		else{
			double k=1,k1=1;
			for(int j=0;j<10;j++)
			{
				if(maxnum < k)
					break;
				k*=10;
			}
			for(int j=0;j<10;j++)
			{
				if(maxnum1 < k1)
					break;
				k1*=10;
			}
			double max = maxnum/k;
			double max1 = maxnum1/k1;
			if(k == k1)
				maxnum2 = maxnum1*8.95/8.5;
			else{
				double k2 = k1/k;
				if(max < max1)
				{
					maxnum2 = maxnum1*8.95/8.5/k2;
					for(int i=0;i<vector.size();i++)
					{			
					   resultdata[0][i] *= k2;
					}
				}
				else{
				    double maxx = max/max1;
				    maxx = Math.floor(maxx)+1;
				  	maxnum2 = maxnum1*8.95/8.5/k2*maxx;
					for(int i=0;i<vector.size();i++)
					{			
					   resultdata[0][i] *= k2/maxx;
					}
				}
			}
		}		
	   	if(maxnum==0&&maxnum1==0)
			maxnum2=1;
	    BarChart objBarChart = new BarChart();
        graphURL =objBarChart.returnBarResult(request,"商家统计分析结果",shops, couponnum,resultdata,setime,"商家（名称-分部）",maxnum2);
		
    }
    else if(tag.trim().equals("terminalanalyse"))
    {
		titile="终端统计分析结果";
    	TerminalAnalysis obj = new TerminalAnalysis(globa);
		obj.setStime(stime);
		obj.setEtime(etime);
		Vector<TerminalAnalysis> vector = obj.getTermianlAnalysisResult(twhere);
		double resultdata[][] = new double[2][vector.size()];
		String[] couponnum = new String[]{"优惠券数量总计", "优惠券打印次数总计"};
		String[] terminals = new String[vector.size()];
		double maxnum = 0;
		double maxnum1 = 0;
		double maxnum2 = 0;
		for(int i=0;i<vector.size();i++)
		{
			TerminalAnalysis obj0 = new TerminalAnalysis();
			obj0 = vector.get(i);
			resultdata[0][i] = obj0.getTerminalCouponNum()*1.0;
			if(maxnum < resultdata[0][i])
			 	maxnum = resultdata[0][i];
			resultdata[1][i]= obj0.getTerminalPrintNum()*1.0;
			if(maxnum1 <resultdata[1][i])
			 	maxnum1 = resultdata[1][i];		
			terminals[i] = obj0.getTerminalNo();
		}
		if(maxnum > maxnum1)
			maxnum2 = maxnum*8.95/8.5;
		else{
			double k=1,k1=1;
			for(int j=0;j<10;j++)
			{
				if(maxnum < k)
					break;
				k*=10;
			}
			for(int j=0;j<10;j++)
			{
				if(maxnum1 < k1) 
					break;
				k1*=10;
			}
			double max = maxnum/k;
			double max1 = maxnum1/k1;
			if(k == k1)
				maxnum2 = maxnum1*8.95/8.5;
			else{
				double k2 = k1/k;
				if(max < max1)
				{
					maxnum2 = maxnum1*8.95/8.5/k2;
					for(int i=0;i<vector.size();i++)
					{			
					   resultdata[0][i] *= k2;
					}
				}
				else{
				    double maxx = max/max1;
				    maxx = Math.floor(maxx)+1;
				  	maxnum2 = maxnum1*8.95/8.5/k2*maxx;
					for(int i=0;i<vector.size();i++)
					{			
					   resultdata[0][i] *= k2/maxx;
					}
				}
			}
		}		
	   	if(maxnum==0&&maxnum1==0)
			maxnum2=1;
        BarChart objBarChart = new BarChart();
        graphURL =objBarChart.returnBarResult(request,"终端统计分析结果",terminals, couponnum,resultdata,setime,"终端（编号）",maxnum2);
	  }
    else if(tag.trim().equals("shopbiz"))
      {
			titile="商家消费统计结果";
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
	    //关闭数据库连接对象
	    globa.closeCon();
        
%>
<html>
<head>
<title><%=titile %></title>
</head>
<body>
<iframe align="middle" marginwidth="50" frameborder=0 id=frmChart name=frmChart src="<%= graphURL %>" scrolling="yes" style=" HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 3"></iframe>
</body>
</html>
