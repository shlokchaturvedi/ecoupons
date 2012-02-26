<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.web.Index,
				com.ejoysoft.ecoupons.business.Gift" %>
<%@ include file="../include/jsp/head.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    //初始化
    Gift  shop0=null;
    Gift obj=new Gift(globa);
    //查询条件
	String tWhere=" where  dtactivetime <=now() and dtexpiretime >=now() order by strid";
	//记录总数
	int intAllCount=obj.getCount(tWhere);
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	int intPageSize=globa.getIntPageSize()+1;
	//int intPageSize=6;
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<Gift> vctObj=obj.list(tWhere,intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>礼品</title>
<link href="css/gift.css" rel="stylesheet" type="text/css" /></head>

<body>
<form method=post action="gift.jsp" name=frm />
<iframe style="HEIGHT: 164px" border=0 marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="gift-content">
<!--left部分-->
  <div class="gift-left">

 <div class=giftList>
 
	<div class=giftList_top>
		<div class="giftList_sf">礼品</div>

	</div>
	
	
<div class=giftList_mid>
<ul>
<%
for(int i = 0;i < vctObj.size(); i++) {
       Gift obj1 = vctObj.get(i);
 %>
  <li>
    <div class=giftList_list_mid>
  <div class=giftList_list_img><a href="giftinfo.jsp?strid=<%=obj1.getStrId() %>" target="_blank" >  
       <%
       if (obj1.getStrSmallImg().length() > 0) {
       %>
         <img src="<%="../member/images/" + obj1.getStrSmallImg() %>" width=142  height=134 border="0" />
       <%
       }
       else
       {
       %>
        <img src="images/temp.jpg" width=142  height=134 border="0"/>
        <%} %>
  </a></div>
   <div class=giftname><a href="giftinfo.jsp?strid=<%=obj1.getStrId() %>" target="_blank" ><%=obj1.getStrName() %></a></div>
	  <p>
	  <font color=#005ECA><b>积分：<%=obj1.getIntPoint() %>分</b></font><br/>
	  <font color=#FF4B00><b>市场价：<%=obj1.getFlaPrice() %>元</b></font><br/>
	  </p>
    </div>
 </li>
<%} %>   
</ul>
<div class=clearfloat></div></div>


<div class=gift_bottom></div></div>
 <!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
 <!-- 翻页结束 --> 


</div>
 <!--left结束-->
  
<!--right部分-->
  <div class="gift-right">
    <div class=gift_sort>
<div class=gift_sort_top>
<h1><strong>常见问题</strong></h1>
</div>
	<div class=gift_sort_con>
	<span>积分用什么用？</span>
	<p>积用可以通用乐购网、服务设备兑换礼品或抵用券等。</p>
	<span>积分怎么获得？</span>
	<p>积用可以通过商家转赠或者纸质积分卡录入。</p>
	<span>积分有几种类型？</span>
	<p>积用可以分为两种类型：电子积分和纸质积分。</p>
	
	</div>
    <div class=gift_sort_bottom></div>
  </div>
  
  
  
      <div class=gift_sort>
<div class=gift_sort_top>
<h1><strong>兑换说明</strong></h1>
</div>
	<div class=gift_sort_con>
	<p>・用户首先注册成为乐购网会员，并且通过消费获得积分。</p>
	<p>・会员首先登录乐购网，进入礼品频道，选取相应的礼品，输入短信接收号码。</p>
	<p>・会员根据接收到的担心提示，领取兑换的礼品。</p>
	</div>
    <div class=gift_sort_bottom></div>
  </div>
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 260px" border=0 marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</form>
<%globa.closeCon(); %>
</body>
</html>

