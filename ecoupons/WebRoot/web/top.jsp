<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
 <script type=text/javascript>
<!--

function showlayer0(id,index)
{
eval("document.getElementById('clayer0_"+id+"').className='searchtabxz'");
eval("document.getElementById('clayer0_"+index+"').className='midri1_1'");

eval("document.getElementById('clay0_"+id+"').style.display='block'");
eval("document.getElementById('clay0_"+index+"').style.display='none'");
}
// -->
</script></head>

<body>

<form action="merchants.jsp" method="post" name=frm target="_parent">
<!--top部分-->
<div class="top">
<!--logo部分-->
  <div class="logo">
     <div class="top_lx"><img src="images/xlwb.gif" /><a href="#"> 新浪微博&nbsp;</a>  <img src="images/txwb.gif"/><a href="#"> 腾讯收听&nbsp;</a>  <img src="images/kf.gif" /><a href="#"> 客服在线</a> &nbsp;&nbsp;电话：400-868-968&nbsp;&nbsp;<a href="#">收藏本站</a> | <a href="#">我要留言</a></div>
     <div class="logo_img"><img src="images/logo.jpg"/></div>
    <div class="tab"> 
     
			<div class="logo_tab">
		
				<div class="searchtab">
					<ul>
					  <li class="searchtabxz" id=clayer0_1  onmouseover=showlayer0(1,2) >商家名称</li>
					  <li class="searchtabz" id=clayer0_2 onmouseover=showlayer0(2,1)>优惠券</li>
					</ul>
				</div>
		
		  	 <div id=clay0_1 style="display:block"><input class="newsearch-txt" id=headbcsearchtxt onkeyup=searchSuggest(event); name="strName" />
			      <input type="button" name="Button" value=" " onclick="frm.action='merchants.jsp';frm.submit();" class="newsearch-btn"  />
			 </div>
	         <div id=clay0_2 style="display:none"><input class="newsearch-txt" id=headbcsearchtxt onkeyup=searchSuggest(event); name="strName2" />
				<input type="button" name="Button" onclick="frm.action='coupons_more.jsp';frm.submit();" value=" " class="newsearch-btn"  />
			 </div>
			</div>
			<div class="search_txt">热门搜索：<a href="#">麦当劳</a> <a href="#">肯德鸡</a> <a href="#">三福</a> <a href="#">米乐迪</a></div>
</div>
 </div>
<!--logo结束-->
<div style="clear:both"></div>
<!--nav导航部分-->
 <div class="nav">
 	<ul>
		<li class="nav_le"><img src="images/nav_le.jpg" /></li>
		<li><a href="index.jsp" target="_parent"><img src="images/nav_1.jpg" border="0" /></a></li>
		<li><a href="coupons.jsp" target="_parent"><img src="images/nav_2.jpg" border="0" /></a></li>
		<li><a href="merchants.jsp" target="_parent"><img src="images/nav_3.jpg" border="0" /></a></li>
		<li><a href="gift.jsp" target="_parent"><img src="images/nav_4.jpg" border="0" /></a></li>
		<li><a href="terminals.jsp" target="_parent"><img src="images/nav_6.jpg" border="0" /></a></li>
		<li><a href="vips.jsp" target="_parent"><img src="images/nav_7.jpg" border="0" /></a></li>
		<li></li>
		<li class="nav_ri"><img src="images/nav_ri.jpg" /></li>

	</ul>
 </div>
<!--nav导航结束-->
</div>
<!--top结束-->
</form>
</body>
</html>

