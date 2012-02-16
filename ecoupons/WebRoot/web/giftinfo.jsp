<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.web.Index,
				com.ejoysoft.ecoupons.business.Gift,
				com.ejoysoft.common.exception.IdObjectException" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    Gift obj=new Gift(globa);
    //查询条件
    String  strId=ParamUtil.getString(request,"strid"," ");	
    if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
 
	String tWhere=" where strid='" + strId + "'";
	Gift obj1=obj.show(tWhere);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=obj1.getStrName() %>乐购礼品详情</title>
<link href="css/giftinfo.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function openwin()
{
    con = confirm("确定兑换该礼品？");
	if(con)
	{
		url ="giftexchange_act.jsp?strid="+<%=obj1.getStrId()%>+"&random="+<%= Math.random()%>+"&strPhone="+document.getElementById("strPhone").value;
		window.showModalDialog(url, "", "dialogWidth=200px;dialogHeight:150px;dialogTop:400px;dialogLeft:550px;scrollbars=yes;status=yes;center=yes;");
	}
}

</script>
</head>

<body>
<iframe height="167"  marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="gift-content">
<!--left部分-->
  <div class="gift-left">

 <div class=giftList>
 
	<div class=giftList_top>
		<div class="giftList_sf"><%=obj1.getStrName() %>（礼品详情）</div>

	</div>
    <div class=giftList_mid>
	
<div class=gift_pro>
<div class=gift_mid_img><img src="images/giftimg.jpg" /></div>
<div class=gift_mid_img_txt>
	<span class=gift_cpmc><%=obj1.getStrName() %></span>
    <span class=gift_jf>积分：<%=obj1.getIntPoint() %>分</span>
    <span class=gift_rcj> 市场价：<%=obj1.getFlaPrice()%>元</span>
    <span class=gift_lppp>兑换截止时间：<%=obj1.getDtExpireTime() %></span>
  <%
  String strPhone="";System.out.println("eeeeeeeeeeeeeeeee");
  if(globa.memberSession!=null){if(globa.memberSession.getStrMobileNo()!=null){strPhone=globa.memberSession.getStrMobileNo();}}
   %>
   <span class=gift_lppp>短信接收号码：<input type="text" id="strPhone" value="<%=strPhone %>" /></span>
     <p><a href="#" onclick="openwin()">
  <img src="images/zjdh.jpg" width="100" height="27" border="0"/>
  </a></p>
</div>
<div style="clear:both"></div>
</div>
<div class=gift_line></div>

<div class=gift_smwz>
<p class=gift_smwz_tit>产品说明：</p>
<p><%=obj1.getStrIntro() %></p>
<p class=gift_smwz_tit>使用注意事项：</p>
<p><%=obj1.getStrAttention() %></p>
</div>

</div>


<div class=gift_bottom></div></div>
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
	<p>积用可以通用乐购网服务设备充值手机话费、兑换福利彩票，可以通过乐购网购买抵扣现金、兑换礼品或抵用券等。</p>
	<span>积分计算方式？</span>
	<p>积用可以通用乐购网服务设备充值手机话费</p>
	<span>积分用什么用？</span>
	<p>积用可以通用乐购网服务设备充值手机话费</p>
	
	</div>
    <div class=gift_sort_bottom></div>
  </div>
  
  
  
      <div class=gift_sort>
<div class=gift_sort_top>
<h1><strong>兑换说明</strong></h1>
</div>
	<div class=gift_sort_con>
	<p>积用可以通用乐购网服务设备充值手机话费、兑换福利彩票，可以通过乐购网购买抵扣现金、兑换礼品或抵用券等。</p>
	</div>
    <div class=gift_sort_bottom></div>
  </div>
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
<%globa.closeCon(); %>
</body>
</html>
