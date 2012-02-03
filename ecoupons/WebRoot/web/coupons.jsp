<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Member,
				com.ejoysoft.ecoupons.business.Coupon,
				java.util.*,
				com.ejoysoft.common.exception.*,
				com.ejoysoft.common.*,
				com.ejoysoft.ecoupons.web.Index,
				com.ejoysoft.ecoupons.system.SysPara,
				com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    //初始化
	Index  obj = new Index(globa);
	HashMap<String,Vector<Coupon>> vctobj = obj.getCouponsByClassfiction();	
	SysPara para=new SysPara(globa);
    ArrayList tradelist = para.list("商家行业");
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>优惠券</title>
  
</head>

<body>
<iframe style="HEIGHT: 130px"  marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<div class="coupons-content">
<!--left部分-->
  <div class="coupons-left">

 <div class=hotList>
 
	<div class=hotList_top>
		<p>优惠券</p>
	</div>
<%
for(int i=0 ;i<tradelist.size();i++)
{
  SysPara syspara1 = (SysPara)tradelist.get(i);
  Vector<Coupon> vctcoup = vctobj.get(syspara1.getStrName());
  int k=0;
if(vctcoup!=null&&vctcoup.size()>0)
{
%>
<div class=hotList_tit1>
		<p><a href="#"><%=syspara1.getStrName()%></a></p>
		<div class=hotList_more><a href="#">更多&gt;&gt;</a>&nbsp;&nbsp;</div>
</div>
<div class=hotList_mid>
<ul>
<%
		for(int j=0;j<vctcoup.size();j++)
		{
		  Coupon obj1 =vctcoup.get(j);
		  k++;
		  if(k==4){break;}
 %>
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
    <li><a href="#"><img src="images/print.jpg" border="0" style="CURSOR: pointer" /> 打印</a></li>
    <li><a href="#"><img src="images/collection.jpg" border="0" style="CURSOR: pointer" /> 收藏</a></li> 
	<li><a href="#"><img src="images/sms.jpg" border="0" style="CURSOR: pointer" /> 短信</a></li>
 </ul>
 </div></div>
  <div class=clearfloat></div>
  </div>
  </li>
<%
      }
	%>

</ul>
<div class=clearfloat></div></div>

<%
	}
}
%>
<div class=hotList_bottom></div></div>
  
  
  
  </div>
 <!--left结束-->
 
  
<!--right部分-->
  <div class="coupons-right">
 
 <div class=sort>
<div class=sort_top>
<h1><strong>推荐优惠券</strong></h1>
<div class=hotList_more><a href="#">更多&gt;&gt;</a>&nbsp;&nbsp;</div></div>
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
         <a href="couponinfo.jsp?strid=<%=coupon2.getStrId() %>" target="_blank"><img src=<%="../coupon/images/"+coupon2.getStrSmallImg()%> width="173" height="110" />
         </a></li> 
		<%
		}
	}
	 %> 
	</ul>
	</div>
<div class=sort_bottom></div>
  </div>  
  
 <div class=sort>
<div class=sort_top>
<h1><strong>类别检索</strong></h1></div>
<div class=sort_con1>
<ul>
  <li><a href="javascript:void(0)">餐饮/酒吧/小吃&nbsp;&nbsp;(97)</a></li>
  <li><a href="javascript:void(0)">休闲/娱乐/游戏&nbsp;&nbsp;(28)</a></li> 
  <li><a href="javascript:void(0)">购物/生活/商超&nbsp;&nbsp;(30)</a></li> 
  <li><a href="javascript:void(0)">美容/美体/健身&nbsp;&nbsp;(50)</a></li>
  <li><a href="javascript:void(0)">旅游/酒店/票务&nbsp;&nbsp;(7)</a></li>
  <li><a href="javascript:void(0)">房产/家居/建材&nbsp;&nbsp;(0)</a></li>
  <li><a href="javascript:void(0)">汽车/4s店/租赁&nbsp;(4)</a></li>
  <li><a href="javascript:void(0)">教育/培训/文化&nbsp;&nbsp;(6)</a> </li></ul></div>
<div class=sort_bottom></div></div> 
 
 
 <div class=sort>
<div class=sort_top>
<h1><strong>热门评论</strong></h1></div>
	<div class=sort_con>
	<ul>
	  <li><a href="#">那店确实还不错，口...</a></li>
	  <li><a href="#">看着还行，昨天去吃了...</a></li> 
	  <li><a href="#">真实物美价廉，还赚积...</a></li>
	  <li><a href="#">去尝尝吧，还不错&nbsp;&nbsp;</a></li>
	  <li><a href="#">太远了</a></li>
	  <li><a href="#">没去过，准备去尝尝</a></li>
	 </ul>
	</div>
<div class=sort_bottom></div>
  </div>
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<%globa.closeCon(); %>
</body>
</html>
