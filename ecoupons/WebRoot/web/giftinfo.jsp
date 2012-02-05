<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>礼品</title>
<link href="css/giftinfo.css" rel="stylesheet" type="text/css" /></head>

<body>
<iframe height="130" border=0 marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="gift-content">
<!--left部分-->
  <div class="gift-left">

 <DIV class=giftList>
 
	<DIV class=giftList_top>
		<div class="giftList_sf">礼品</div>

	</DIV>
    <DIV class=giftList_mid>
	
<DIV class=gift_pro>
<div class=gift_mid_img><img src="images/giftimg.jpg" /></div>
<div class=gift_mid_img_txt>
	<span class=gift_cpmc>数码相框</span>
    <span class=gift_lppp>礼品号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品牌：&nbsp;</span>
    <span class=gift_jf>积分：6950</span>
    <span class=gift_rcj> 市场价：65元</span>
  <p><img src="images/zjdh.jpg" width="100" height="27" /></p>
</div>
<div style="clear:both"></div>
</DIV>
<div class=gift_line></div>

<div class=gift_smwz>
<p class=gift_smwz_tit>产品说明：</p>
<p>数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，</p>
<p class=gift_smwz_tit>使用注意事项：</p>
<p>数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，数码相框产品说明，</p>
</div>

</DIV>


<DIV class=gift_bottom></DIV></DIV>
 <DIV class=gift_pageSide>
总计852条记录，共6页
<input type="submit" name="Submit" class="gift_pageipt" value="第一页" />
 <input type="submit" name="Submit2" class="gift_pageipt" value="上一页" />
 <input type="submit" name="Submit3" class="gift_pageipt" value="下一页" />
 <input type="submit" name="Submit4" class="gift_pageipt" value="最末页" />
 <select name="select">
   <option>1</option>
   <option>2</option>
 </select>
 </DIV>


</div>
 <!--left结束-->
  
<!--right部分-->
  <div class="gift-right">
    <DIV class=gift_sort>
<DIV class=gift_sort_top>
<H1><STRONG>常见问题</STRONG></H1>
</DIV>
	<DIV class=gift_sort_con>
	<span>积分用什么用？</span>
	<p>积用可以通用乐购网服务设备充值手机话费、兑换福利彩票，可以通过乐购网购买抵扣现金、兑换礼品或抵用券等。</p>
	<span>积分计算方式？</span>
	<p>积用可以通用乐购网服务设备充值手机话费</p>
	<span>积分用什么用？</span>
	<p>积用可以通用乐购网服务设备充值手机话费</p>
	
	</DIV>
    <DIV class=gift_sort_bottom></DIV>
  </div>
  
  
  
      <DIV class=gift_sort>
<DIV class=gift_sort_top>
<H1><STRONG>兑换说明</STRONG></H1>
</DIV>
	<DIV class=gift_sort_con>
	<p>积用可以通用乐购网服务设备充值手机话费、兑换福利彩票，可以通过乐购网购买抵扣现金、兑换礼品或抵用券等。</p>
	</DIV>
    <DIV class=gift_sort_bottom></DIV>
  </div>
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 340px" border=0 marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</body>
</html>
