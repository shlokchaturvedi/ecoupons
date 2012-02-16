<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Member,
				com.ejoysoft.ecoupons.business.Coupon,
				java.util.*,
				com.ejoysoft.common.exception.*,
				com.ejoysoft.common.*,
				com.ejoysoft.ecoupons.web.Index,
				com.ejoysoft.ecoupons.system.SysPara,
				com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponComment"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    //初始化
    Coupon coupobj=new Coupon(globa);
    SysPara syspara = new SysPara(globa);
    //查询条件
	String tWhere=" where intrecommend='1' order by dtcreatetime";
	//记录总数
	int intAllCount=coupobj.getCount(tWhere);
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	int intPageSize=globa.getIntPageSize();
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<Coupon> vctObj=coupobj.list(tWhere,intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);

	Index  obj = new Index(globa);
	HashMap<String,Vector<Coupon>> vctobj = obj.getCouponsByClassfiction();	
	SysPara para=new SysPara(globa);
    ArrayList tradelist = para.list("商家行业");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>推荐优惠券</title>
<link href="css/merchants.css" rel="stylesheet" type="text/css" />
</head>

<body>
<form name=frm method=post action="recommend_jsp.jsp">		
&nbsp; 
<iframe height="167" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="coupons-content">
<!--left部分-->
  <div class="coupons-left">

 <div class=hotList>
 
	<div class=hotList_top>
		<div class="hotList_sf">推荐优惠券列表</div>
<div class=more>
<table>
  <tbody>
  <tr>
    <td></td>
    <td><a onclick="seturl('order','hotnum')" href="javascript:void(0)"></a> </td>
    <td></td>
    <td><a onclick="seturl('order','grade')" href="javascript:void(0)"></a> </td></tr>
    </tbody>
 </table>
</div>
</div>
    <div class=hotList_mid>
<ul>
<% for(int i = 0;i < vctObj.size(); i++) {
       Coupon obj1 = vctObj.get(i);%>
 <li>
  <div class=list>
    <div class=list_mid>
  <div class=list_img>
  <a href="couponinfo.jsp?strid=<%=obj1.getStrId() %>" target="_blank">
  <%
  if(obj1.getStrSmallImg()!=null && obj1.getStrSmallImg().length()>0) {
  %>
 <img src=<%="../coupon/images/"+ obj1.getStrSmallImg()%> width=136  height=89 border="0"/>
  <%
  }
  else{ %>
  <img src="images/temp.jpg" width=136  height=89 border="0"/>
  <%} %>
  </a></div>
  <div class=list_hot> <div class=money><%=obj1.getFlaPrice()%>元</div> </div>
  <div class=clearfloat></div>
  <p>名称：<span> <a href="couponinfo.jsp?strid=<%=obj1.getStrId() %>" target="_blank"><%=obj1.getStrName() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span><br/>
  开始时间
    ：<a href="#">2012-01-01</a><br/>
    <font color=#ff0000>截止时间：<%=obj1.getDtExpireTime().substring(0,10) %></font><br/>
    商家
    ：<span>
    <%
     Shop objshop = new Shop(globa);
     String shopname = objshop.returnBizShopName(" where strid='"+obj1.getStrShopId()+"'");
    %>
   <a href="merchantsinfo.jsp?strid=<%=obj1.getStrShopId() %>" target="_blank" ><%=shopname %>&nbsp;</a> </span> </p>
  <div class=line><img src="images/line.gif" /></div>
  <div class=list_bar>
  <ul>
    <li><a href="#" onclick="window.open('coupon_print.jsp?random=<%= Math.random()%>&strid=<%=obj1.getStrId()%>&strimg=<%=obj1.getStrPrintImg()%>','','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=420,height=540,left=450,top=160');"><img src="images/print.jpg" border="0" style="CURSOR: pointer" /> 打印</a></li>
    <li><a href="#"><img src="images/collection.jpg" border="0" style="CURSOR: pointer" /> 收藏</a></li> 
	<li><a href="#"><img src="images/sms.jpg" border="0" style="CURSOR: pointer" /> 短信</a></li>
 </ul>
 </div></div>
  <div class=clearfloat></div>
  </div>
  </li>
<%} %>
 </ul>
<div class=clearfloat></div>
</div>
<div class=hotList_bottom>
</div></div>

 	<!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
   	<!-- 翻页结束 --> 


</div>
 <!--left结束-->
  
<!--right部分-->
  <div class="coupons-right">
  
 <div class=sort>
<div class=sort_top>
<h1><strong>优惠券检索（按类别）</strong></h1></div>
<div class=sort_con1>
<ul> 
<%
Index index=new Index(globa);
HashMap<String, Integer> hmTrades=index.returnTradeForCoup();
for(int i=0;i<tradelist.size();i++)
{
    SysPara syspara1 = (SysPara)tradelist.get(i);
    int coupnum = hmTrades.get(syspara1.getStrName());
    System.out.println(syspara1.getStrName()+coupnum);
	String strtradeid = syspara1.getStrId();
	%>
	<li><a href="coupons_more.jsp?strtrade=<%=strtradeid%>" ><%=syspara1.getStrName()%>&nbsp;&nbsp;(<%=coupnum%>)</a></li>
<%
}

%>
</ul>
</div>
<div class=sort_bottom></div></div> 
 
 
 <div class=sort>
<div class=sort_top>
<h1><strong>热门评论</strong></h1></div>
	<div class=sort_con>
	<ul>
	  <%
	CouponComment couponComment=new CouponComment(globa);
	Vector<CouponComment> vctCouponComment=couponComment.list("",0,0);
	if(vctCouponComment.size()>6){
		for(int i=0;i<6;i++){
			out.print("<LI>・<A href='#'>"+vctCouponComment.get(i).getStrComment()+"</A></LI>");
		}
	}else{
		for(int i=0;i<vctCouponComment.size();i++){
			out.print("<LI>・<A href='#'>"+vctCouponComment.get(i).getStrComment()+"</A></LI>");
		}
	}
	%> </ul>
	</div>
<div class=sort_bottom></div>
  </div>
  <div class=sort>
<div class=sort_top>
<h1><strong>推荐优惠券</strong></h1>
<div class=hotList_more><a href="recommend_more.jsp">更多&gt;&gt;</a>&nbsp;&nbsp;</div></div>
	<div class=sort_con2>
	<ul>
	<%
	Coupon coupon = new Coupon(globa);
	Vector<Coupon> vctcoup = coupon.list(" where intrecommend='1'",0,0);
	int k =1;
	for(int i=0;i<vctcoup.size();i++)
	{   
		if(k++ >6){break;}
		Coupon coupon2 = vctcoup.get(i);
		if(coupon2.getStrSmallImg()!=null&&coupon2.getStrSmallImg().length()>0)
		{
		%>
		  <li>
         <a href="couponinfo.jsp?strid=<%=coupon2.getStrId() %>" target="_blank"><img src=<%="../coupon/images/"+coupon2.getStrSmallImg()%> width="173" height="110" border="0" title="<%=coupon2.getStrName()%>"/>
         </a></li> 
		<%
		}
	}
	 %> 
	</ul>
	</div>
<div class=sort_bottom></div>
  </div> 
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</form>
<%globa.closeCon(); %>
</body>
</html>
