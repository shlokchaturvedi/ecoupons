package com.ejoysoft.ecoupons.business;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleInsets;

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

		TextTitle texttitle = chart.getTitle();
		texttitle.setFont(new Font("黑体", Font.BOLD, 24));
		texttitle.setBorder(0.0D,0.0D,1.0D,0.0D);
		texttitle.setBackgroundPaint(new GradientPaint(0.0F, 0.0F,new Color(5,39,86), 350F, 0.0F, Color.white, true));
		texttitle.setExpandToFitSpace(true);
		texttitle.setPaint(new Color(245,45,27));
		chart.setBackgroundPaint(new GradientPaint(0.0F, 0.0F, new Color(148,205,233), 350F, 0.0F, Color.white, true));
		CategoryPlot plot = chart.getCategoryPlot();
		//设置网格背景颜色
		plot.setBackgroundPaint(null);
		plot.setInsets(new RectangleInsets(10D, 5D, 5D, 5D));
		plot.setOutlinePaint(Color.black);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.gray);
		plot.setRangeGridlineStroke(new BasicStroke(1.0F));
		plot.setForegroundAlpha(1.0f);
		chart.getLegend().setItemFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		CategoryAxis domainAxis = plot.getDomainAxis();
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		domainAxis.setTickLabelFont(new Font("楷体", Font.CENTER_BASELINE, 11));
		domainAxis.setLabelFont(new Font("宋体",Font.CENTER_BASELINE, 13));
//		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		numberAxis.setTickLabelFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		numberAxis.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 13));
		numberAxis.setAutoRangeIncludesZero(true);
		numberAxis.setLowerBound(0);
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		NumberAxis numberaxis1 = new NumberAxis("优惠券发布数量（种）");   
		plot.setRangeAxis(1, numberaxis1);
		numberaxis1.setLowerBound(0);
		numberaxis1.setUpperBound(maxnum);
		numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis1.setTickLabelFont(new Font("宋体", Font.CENTER_BASELINE, 10));
		numberaxis1.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 13));
		numberaxis1.setAutoRangeIncludesZero(true);
		BarRenderer renderer = new BarRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(false);
		renderer.setMinimumBarLength(1.0);
		renderer.setSeriesPaint(0, new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.red));
		renderer.setSeriesPaint(1, new GradientPaint(0.0F, 0.0F, Color.white, 0.0F, 0.0F, Color.blue));
		//renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		//设置每个柱形所包含的平行柱的之间距离
		renderer.setItemMargin(0.02);
		plot.setRenderer(renderer);
		plot.setNoDataMessage("没有数据显示"); 
		//将下方的“坐标”放到上方
//		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		//将默认放在左边的“坐标”放到右方
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
//		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		int width=850;		
		if(shops.length*150>=850)
			width = shops.length*150;
		String filename ="";
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, 580, null, request.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;		
		return graphURL;
	}
	
}