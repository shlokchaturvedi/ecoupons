package com.ejoysoft.ecoupons.business;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
@SuppressWarnings("serial")
public class PieChart extends javax.servlet.http.HttpServlet {
	
	public PieChart() {}
	public String returnPieResult(HttpServletRequest request,String titile,String[] shops,double[] data,String setime)
	{
		//设置数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(int i=0;i<shops.length;i++)
		{
			dataset.setValue(shops[i], data[i]);
		}
		//通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart3D(titile+"（"+setime+"）", dataset, true, false, false);
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 20));
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));

		pieplot.setBackgroundPaint(Color.white);

		//没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.05D);
		//获得3D的水晶饼图对象
		PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
		//设置开始角度
		pieplot3d.setStartAngle(150D);
		//设置方向为”顺时针方向“
		pieplot3d.setDirection(Rotation.CLOCKWISE);
		//设置透明度，0.5F为半透明，1为不透明，0为全透明
		pieplot3d.setForegroundAlpha(0.7F);


		String filename="";
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 870, 540, null, request.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;	
		return graphURL;
	}

}
