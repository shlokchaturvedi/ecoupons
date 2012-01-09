package com.ejoysoft.ecoupons.business;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

@SuppressWarnings("serial")
public class BarChart extends javax.servlet.http.HttpServlet {

	public BarChart() {}
	public String returnBarResult(HttpServletRequest request,String titile,String[] shops,String[] rowKeys,double[][] data,String setime,String name,double maxnum)
	{
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, shops,data); 
		JFreeChart chart = ChartFactory.createBarChart3D(titile+"（"+setime+"）", 
                name,
                "优惠券被打印次数（次）",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 24));
		 CategoryPlot plot = chart.getCategoryPlot();
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		//显示每个柱的数值，并修改该数值的字体属性
		chart.getLegend().setItemFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		CategoryAxis domainAxis = plot.getDomainAxis();
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		//设置X轴坐标上的文字
		domainAxis.setTickLabelFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		//设置x轴的标题文字
		domainAxis.setLabelFont(new Font("宋体",Font.CENTER_BASELINE, 13));
		//设置Y轴坐标的文字
		numberAxis.setTickLabelFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		//设置Y轴的标题文字
		numberAxis.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 13));
		//设置刻度从0开始
		numberAxis.setAutoRangeIncludesZero(true);
		NumberAxis numberaxis1 = new NumberAxis("优惠券发布数量（种）");   
		plot.setRangeAxis(1, numberaxis1);
		numberaxis1.setUpperBound(maxnum);
		numberaxis1.setTickLabelFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		numberaxis1.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 13));
		numberaxis1.setAutoRangeIncludesZero(true);
		BarRenderer renderer = new BarRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(false);
		renderer.setMinimumBarLength(1.0);
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		//renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		//设置每个柱形所包含的平行柱的之间距离
		renderer.setItemMargin(0.02);
//		renderer.setSeriesPaint(0, new Color(255, 13, 0));
//		renderer.setSeriesPaint(1, new Color(35, 61, 237));
		plot.setRenderer(renderer);
		plot.setNoDataMessage("没有数据显示"); 
		//将下方的“坐标”放到上方
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		//将默认放在左边的“坐标”放到右方
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		int width=850;		
		if(shops.length*150>=850)
			width = shops.length*150;
		String filename ="";
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, 500, null, request.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;		
		return graphURL;
	}
	
}