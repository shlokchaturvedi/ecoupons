package com.ejoysoft.ecoupons.business;
import java.awt.Color;   
import java.awt.Dimension;   
import javax.swing.JPanel;   
import org.jfree.chart.*;   
import org.jfree.chart.axis.*;   
import org.jfree.chart.block.*;   
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;   
import org.jfree.chart.plot.*;   
import org.jfree.chart.renderer.category.LineAndShapeRenderer;   
import org.jfree.chart.title.CompositeTitle;   
import org.jfree.chart.title.LegendTitle;   
import org.jfree.data.category.CategoryDataset;   
import org.jfree.data.category.DefaultCategoryDataset;   
import org.jfree.ui.*;   
   
public class DualAxisDemo extends ApplicationFrame   
{   
   
  public DualAxisDemo(String s)   
  {   
  super(s);   
  JFreeChart jfreechart = createChart();   
  ChartPanel chartpanel = new ChartPanel(jfreechart);   
  chartpanel.setPreferredSize(new Dimension(500, 270));   
  setContentPane(chartpanel);   
  }   
   
  private static CategoryDataset createDataset1()   
  {   
  String s1 = "S1";   
  String s2 = "S2";   
  String s3 = "S3";   
  String s4 = "S4";   
  String s5 = "S5";   
  DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();   
  defaultcategorydataset.addValue(150D, s1, s2);   
  defaultcategorydataset.addValue(200D, s1, s3);   
  defaultcategorydataset.addValue(80D, s1, s4);   
  defaultcategorydataset.addValue(220D, s1, s5);   
  return defaultcategorydataset;   
  }   
   
  private static CategoryDataset createDataset2()   
  {   
  String s1 = "S6";   
  String s2 = "S2";   
  DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();   
  defaultcategorydataset.addValue(200D, "", "");  
  return defaultcategorydataset;   
  }   
   
  private static JFreeChart createChart()   
  {   
  JFreeChart jfreechart = ChartFactory.createBarChart("DualAxisChart", "Category", "Value", createDataset1(), PlotOrientation.VERTICAL, false, true, false);   
  jfreechart.setBackgroundPaint(Color.white);   
  CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();   
  categoryplot.setBackgroundPaint(new Color(238, 238, 255));   
  categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);   
  CategoryDataset categorydataset = createDataset2();   
  categoryplot.setDataset(1, categorydataset);   
  categoryplot.mapDatasetToRangeAxis(1, 1);   
  CategoryAxis categoryaxis = categoryplot.getDomainAxis();   
  categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);   
   NumberAxis numberaxis = new NumberAxis("Secondary");   
   categoryplot.setRangeAxis(1, numberaxis);   
  LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();   
  lineandshaperenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());   
  categoryplot.setRenderer(1, lineandshaperenderer);   
  categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);   
  LegendTitle legendtitle = new LegendTitle(categoryplot.getRenderer(0));   
  legendtitle.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));   
  legendtitle.setBorder(new BlockBorder());   
  LegendTitle legendtitle1 = new LegendTitle(categoryplot.getRenderer(1));   
  legendtitle1.setMargin(new RectangleInsets(2D, 2D, 2D, 2D));   
  legendtitle1.setBorder(new BlockBorder());   
  BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());   
  blockcontainer.add(legendtitle, RectangleEdge.LEFT);   
  blockcontainer.add(legendtitle1, RectangleEdge.RIGHT);   
  blockcontainer.add(new EmptyBlock(2000D, 0.0D));   
  CompositeTitle compositetitle = new CompositeTitle(blockcontainer);   
  compositetitle.setPosition(RectangleEdge.BOTTOM);   
  jfreechart.addSubtitle(compositetitle);   
  return jfreechart;   
  }   
   
  public static JPanel createDemoPanel()   
  {   
  JFreeChart jfreechart = createChart();   
  return new ChartPanel(jfreechart);   
  }   
   
  public static void main(String args[])   
  {   
  DualAxisDemo dualaxisdemo = new DualAxisDemo("DualAxisDemo");   
  dualaxisdemo.pack();   
  RefineryUtilities.centerFrameOnScreen(dualaxisdemo);   
  dualaxisdemo.setVisible(true);   
  }   
}