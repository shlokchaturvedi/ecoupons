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
          defaultcategorydataset.addValue(troubleAverageNumber,"ƽ��������", stationName[i]);  
        }        
        
        return  defaultcategorydataset;  
} 

%>
  <%!
  private  static  JFreeChart  createChart(String chartTitle,String Xname, String Yname, CategoryDataset dataset1, CategoryDataset dataset2)  
  {  
    
   JFreeChart  chart  =  ChartFactory.createBarChart(chartTitle, Xname, Yname,  dataset1,  PlotOrientation.VERTICAL,  true,  false,  false);  
   chart.setBackgroundPaint(Color.white);  
   
   CategoryPlot categoryplot = chart.getCategoryPlot();//���ͼ�����
   
   // ͼ���������� 
    chart.getLegend().setItemFont(new Font("����", Font.BOLD, 14));
    chart.setTextAntiAlias(false); 
   // 2 ��2 ��������� ����������� TextTitle ���� 
    chart.setTitle(new TextTitle(chartTitle, new Font("����", Font.BOLD,25))); 
    // 2 ��2.1:�������� 
    // x,y���������� 
    Font labelFont = new Font("����", Font.TRUETYPE_FONT, 12); 
   
   //Y����������
    NumberAxis vn = (NumberAxis) categoryplot.getRangeAxis(); 
    // �������ֵ��1 
   // vn.setUpperBound(1); 
    // ���������������0��ʼ 
   // vn.setAutoRangeIncludesZero(true); 
    // ������ʾ��ʽ�ǰٷֱ� 
    DecimalFormat df = new DecimalFormat("0.0%"); 
    vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ 
   
        //X���������ʾ����   domainAxis��X��
       CategoryAxis domainAxis = categoryplot.getDomainAxis(); 
     domainAxis.setLabelFont(labelFont);// ����� 
     domainAxis.setTickLabelFont(labelFont);// ����ֵ    
     
     // y������ 
  ValueAxis rangeAxis = categoryplot.getRangeAxis(); 
  rangeAxis.setLabelFont(labelFont); //�����
     rangeAxis.setTickLabelFont(labelFont);//����ֵ 
      
          categoryplot.setBackgroundPaint(new  Color(238,  238,  255));  
         // categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);  
         //��������ͼ 
         CategoryDataset  categorydataset  =  dataset2;  
          //0��ʾ����״ͼ��1��ʾ����ͼ
         categoryplot.setDataset(1,categorydataset);  
          //��ʾ����ͼ��0��0�����ᶼ�����
          categoryplot.mapDatasetToRangeAxis(0, 0);  
          
          //���ú����labelΪ45��
          CategoryAxis  categoryaxis  =  categoryplot.getDomainAxis();  
          categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);  
          
          //���ұ���ʾ����ͼ�����������
         // NumberAxis  numberaxis  =  new  NumberAxis("ƽ��ֵ");  
         /// numberaxis.setLabelFont(labelFont);
         // categoryplot.setRangeAxis(1,  numberaxis);  
          
         
          //������ͼ
          LineAndShapeRenderer  lineandshaperenderer  =  new  LineAndShapeRenderer();  
          lineandshaperenderer.setToolTipGenerator(new  StandardCategoryToolTipGenerator());  
          categoryplot.setRenderer(1,  lineandshaperenderer); 
        
          // ͼ���������� 
  
          //����״ͼ
          StackedBarRenderer renderer = new StackedBarRenderer(); 
        
          renderer.setItemLabelsVisible(true);
 
          
          // �������ӿ�� 
     renderer.setMaximumBarWidth(0.03); 
     // �������Ӹ߶� 
     renderer.setMinimumBarLength(0.1); 
     // �������ı߿���ɫ 
     renderer.setBaseOutlinePaint(Color.BLACK); 
     // �������ı߿�ɼ� 
     renderer.setDrawBarOutline(true); 
     // // ����������ɫ(���趨Ҳ��Ĭ��) 
     renderer.setSeriesPaint(0, new Color(204, 255, 204)); 
     renderer.setSeriesPaint(1, new Color(255, 204, 153)); 
     // ����ÿ��������������ƽ������֮����� 
     renderer.setItemMargin(0.4); 
     //��ʾ����
     renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
     //��ʾ���ݵĸ�ʽ�ǰٷֱ�
     renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",NumberFormat.getPercentInstance()));
    renderer.setItemLabelFont(new Font("����",Font.BOLD,12));
    renderer.setItemLabelsVisible(true); 
    //������ʾ��ʽ�ǰٷֱ�
    ItemLabelPosition itemLabelPositionFallback=new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_LEFT,TextAnchor.HALF_ASCENT_LEFT,-1.57D);
    //��ʾС����ʾ��Χ������
     renderer.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
     renderer.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);
     //��ʾ��״ͼ
          categoryplot.setRenderer(0,renderer);       
          
          return  chart;  
  }  
  
  %>