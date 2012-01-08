<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
  <%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Dimension"%> 
<%@ page import="javax.swing.JPanel"%>  
<%@ page import="org.jfree.data.general.DefaultPieDataset"%>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="org.jfree.chart.plot.PiePlot"%>
<%@ page import="org.jfree.chart.ChartRenderingInfo"%>
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@ page import="org.jfree.chart.urls.StandardPieURLGenerator"%>
<%@ page import="org.jfree.chart.entity.StandardEntityCollection"%>
<%@ page import="org.jfree.chart.encoders.SunPNGEncoderAdapter"%>
<%@ page import="org.jfree.chart.renderer.category.BarRenderer"%>
 <%@ page import="org.jfree.chart.renderer.category.StackedBarRenderer"%>
  <%@ page import="org.jfree.data.category.CategoryDataset"%>
  <%@ page import="org.jfree.chart.ChartFactory"%> 
    <%@ page import="java.awt.Color"%> 
     <%@ page import="java.awt.Font"%> 
    
<%@ page import="java.io.File"%> 
<%@ page import="java.io.FileOutputStream"%> 
<%@ page import="java.text.DecimalFormat"%> 
<%@ page import="java.text.NumberFormat"%> 
<%@ page import="org.jfree.chart.ChartUtilities"%> 
<%@ page import="org.jfree.chart.axis.CategoryAxis"%> 
<%@ page import="org.jfree.chart.axis.CategoryLabelPositions"%> 
<%@ page import="org.jfree.chart.axis.NumberAxis"%> 
<%@ page import="org.jfree.chart.axis.ValueAxis"%> 
<%@ page import="org.jfree.chart.labels.StandardCategoryItemLabelGenerator"%>
<%@ page import="org.jfree.chart.labels.StandardPieSectionLabelGenerator"%>

<%@ page import="org.jfree.chart.title.TextTitle"%>

<%@ page import="org.jfree.data.category.CategoryDataset"%>

<%@ page import="org.jfree.data.general.DatasetUtilities"%> 
<%@ page import="org.jfree.chart.plot.CategoryPlot"%> 
<%@ page import="org.jfree.chart.plot.PlotOrientation"%>


<%@ page import="org.jfree.ui.TextAnchor"%> 
<%@ page import="org.jfree.chart.labels.ItemLabelAnchor"%> 
<%@ page import="org.jfree.chart.labels.ItemLabelPosition"%>

<%@ page import="org.jfree.chart.labels.StandardCategoryToolTipGenerator"%>  
 <%@ page import="org.jfree.chart.renderer.category.LineAndShapeRenderer"%>  
<%@ page import="org.jfree.chart.title.CompositeTitle"%>  
<%@ page import="org.jfree.chart.title.LegendTitle"%>  
<%@ page import="org.jfree.data.category.CategoryDataset"%>  
<%@ page import="org.jfree.data.category.DefaultCategoryDataset"%> 

    
<%!

private  static  CategoryDataset  createDataset1(String[] stationName,double[] troubleNumber)  
{  
        DefaultCategoryDataset  defaultcategorydataset  =  new  DefaultCategoryDataset();  
        for(int i=0;i<stationName.length;i++)
        {
          defaultcategorydataset.addValue(troubleNumber[i],stationName[i], stationName[i]);  
        }        
        
        return  defaultcategorydataset;  
}  
%>

<%!
private  static  CategoryDataset  createDataset2(String[] stationName,double troubleAverageNumber)  
{  
        DefaultCategoryDataset  defaultcategorydataset  =  new  DefaultCategoryDataset();  
        for(int i=0;i<stationName.length;i++)
        {
          defaultcategorydataset.addValue(troubleAverageNumber,"平均故障率", stationName[i]);  
        }        
        
        return  defaultcategorydataset;  
} 

%>
  <%!
  private  static  JFreeChart  createChart(String chartTitle,String Xname, String Yname, CategoryDataset dataset1, CategoryDataset dataset2)  
  {  
    
   JFreeChart  chart  =  ChartFactory.createBarChart(chartTitle, Xname, Yname,  dataset1,  PlotOrientation.VERTICAL,  true,  false,  false);  
   chart.setBackgroundPaint(Color.white);  
   
   CategoryPlot categoryplot = chart.getCategoryPlot();//获得图表对象
   
   // 图例字体清晰 
    chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 14));
    chart.setTextAntiAlias(false); 
   // 2 ．2 主标题对象 主标题对象是 TextTitle 类型 
    chart.setTitle(new TextTitle(chartTitle, new Font("隶书", Font.BOLD,25))); 
    // 2 ．2.1:设置中文 
    // x,y轴坐标字体 
    Font labelFont = new Font("宋体", Font.TRUETYPE_FONT, 12); 
   
   //Y轴数据设置
    NumberAxis vn = (NumberAxis) categoryplot.getRangeAxis(); 
    // 设置最大值是1 
   // vn.setUpperBound(1); 
    // 设置数据轴坐标从0开始 
   // vn.setAutoRangeIncludesZero(true); 
    // 数据显示格式是百分比 
    DecimalFormat df = new DecimalFormat("0.0%"); 
    vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式 
   
        //X轴坐标的显示中文   domainAxis是X轴
       CategoryAxis domainAxis = categoryplot.getDomainAxis(); 
     domainAxis.setLabelFont(labelFont);// 轴标题 
     domainAxis.setTickLabelFont(labelFont);// 轴数值    
     
     // y轴设置 
  ValueAxis rangeAxis = categoryplot.getRangeAxis(); 
  rangeAxis.setLabelFont(labelFont); //轴标题
     rangeAxis.setTickLabelFont(labelFont);//轴数值 
      
          categoryplot.setBackgroundPaint(new  Color(238,  238,  255));  
         // categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);  
         //创建折线图 
         CategoryDataset  categorydataset  =  dataset2;  
          //0显示是柱状图，1显示折线图
         categoryplot.setDataset(1,categorydataset);  
          //显示折线图，0，0坐标轴都在左侧
          categoryplot.mapDatasetToRangeAxis(0, 0);  
          
          //设置横轴的label为45度
          CategoryAxis  categoryaxis  =  categoryplot.getDomainAxis();  
          categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);  
          
          //在右边显示折线图的坐标和主题
         // NumberAxis  numberaxis  =  new  NumberAxis("平均值");  
         /// numberaxis.setLabelFont(labelFont);
         // categoryplot.setRangeAxis(1,  numberaxis);  
          
         
          //画折线图
          LineAndShapeRenderer  lineandshaperenderer  =  new  LineAndShapeRenderer();  
          lineandshaperenderer.setToolTipGenerator(new  StandardCategoryToolTipGenerator());  
          categoryplot.setRenderer(1,  lineandshaperenderer); 
        
          // 图例字体清晰 
  
          //画柱状图
          StackedBarRenderer renderer = new StackedBarRenderer(); 
        
          renderer.setItemLabelsVisible(true);
 
          
          // 设置柱子宽度 
     renderer.setMaximumBarWidth(0.03); 
     // 设置柱子高度 
     renderer.setMinimumBarLength(0.1); 
     // 设置柱的边框颜色 
     renderer.setBaseOutlinePaint(Color.BLACK); 
     // 设置柱的边框可见 
     renderer.setDrawBarOutline(true); 
     // // 设置柱的颜色(可设定也可默认) 
     renderer.setSeriesPaint(0, new Color(204, 255, 204)); 
     renderer.setSeriesPaint(1, new Color(255, 204, 153)); 
     // 设置每个地区所包含的平行柱的之间距离 
     renderer.setItemMargin(0.4); 
     //显示数据
     renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
     //显示数据的格式是百分比
     renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",NumberFormat.getPercentInstance()));
    renderer.setItemLabelFont(new Font("黑体",Font.BOLD,12));
    renderer.setItemLabelsVisible(true); 
    //数据显示格式是百分比
    ItemLabelPosition itemLabelPositionFallback=new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_LEFT,TextAnchor.HALF_ASCENT_LEFT,-1.57D);
    //显示小于显示范围的数据
     renderer.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
     renderer.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);
     //显示柱状图
          categoryplot.setRenderer(0,renderer);       
          
          return  chart;  
  }  
  
  %>