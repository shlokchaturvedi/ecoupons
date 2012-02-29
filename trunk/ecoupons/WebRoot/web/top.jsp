<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ejoysoft.ecoupons.system.SysPara"%>
<%@page import="com.ejoysoft.common.*"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
  <%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
SysPara sysPara=new SysPara(globa);
Vector<SysPara> vctSyspParas=sysPara.list("where strtype='热门搜索' and  strstate='正常' order by dcreatdate  desc limit 4",0,0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>乐购</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
 <script type=text/javascript> 
<!--

function showlayer0(id,index)
{
eval("document.getElementById('clayer0_"+id+"').className='searchtabxz'");
eval("document.getElementById('clayer0_"+index+"').className='midri1_1'");

eval("document.getElementById('clay0_"+id+"').style.display='block'");
eval("document.getElementById('clay0_"+index+"').style.display='none'");
eval("document.getElementById('hot_"+id+"').style.display='block'");
eval("document.getElementById('hot_"+index+"').style.display='none'");
}

-->
</script></head>

<body>

<form action="merchants.jsp" method="post" name=frm target="_parent">
<!--top部分-->
<div class="top">
<!--logo部分-->
  <div class="logo">
     <div class="top_lx">
     
     <img src="images/xlwb.gif" />
     <a href="http://weibo.com/xjtcm" target="_target"> 新浪微博&nbsp;</a> 
    
      <img src="images/txwb.gif"/><a href="http://t.qq.com/fyjt2010" target="_target"> 腾讯收听&nbsp;</a> 
      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=307075831&site=qq&menu=yes"><img border="0" src="images/kf.gif" alt="点击这里给我发消息" title="点击这里给我发消息" />客服在线</a>
      	电话:4001-868-968&nbsp;
      <a href="javascript:window.external.AddFavorite(location.href, document.title);" >加入收藏</a>
      </div>
     <div class="logo_img"><img src="images/logo.jpg"/></div>
    <div class="tab"> 
     
			<div class="logo_tab">
		
				<div class="searchtab">
					<ul>
					  <li class="searchtabxz" id=clayer0_1  onmouseover=showlayer0(1,2) >商家名称</li>
					  <li class="searchtabz" id=clayer0_2 onmouseover=showlayer0(2,1)>优惠券</li>
					</ul>
				</div>
		
		  	 <div id=clay0_1 style="display:block">
		  	
		  	 	  <input class="newsearch-txt" id=headbcsearchtxt  name="strName" />
			      <input type="button" name="Button" value=" " onclick="frm.action='merchants.jsp';frm.submit();" class="newsearch-btn"  />
		  	
			 </div>
			       <div id=hot_1   style="display:block" align="left">&nbsp;&nbsp;&nbsp;&nbsp;热门搜索：<%for(int i=0;i<vctSyspParas.size();i++){ %>
					<a id="hotId" href="#" onclick="frm.action='merchants.jsp';frm.strName.value='<%=vctSyspParas.get(i).getStrName() %>';frm.submit();"><%=vctSyspParas.get(i).getStrName() %></a> 
					<%} %>	
					</div>
		     <div id=clay0_2 style="display:none">
	          <input class="newsearch-txt" id=headbcsearchtxt  name="strName2" />
			  <input type="button" name="Button" onclick="frm.action='coupons_more.jsp';frm.submit();" value=" " class="newsearch-btn"  />
			 </div>
			    <div id=hot_2 style="display:none" align="left">&nbsp;&nbsp;&nbsp;&nbsp;热门搜索：<%for(int i=0;i<vctSyspParas.size();i++){ %>
				<a id="hotId" href="#" onclick="frm.action='coupons_more.jsp';frm.strName2.value='<%=vctSyspParas.get(i).getStrName() %>';frm.submit();"><%=vctSyspParas.get(i).getStrName() %></a> 
				<%} %>	
				</div> 			
			</div>
			
</div>
 </div>
<!--logo结束-->
<div style="clear:both"></div>
<!--nav导航部分-->
 <div class="nav">
 	<ul>
		<li class="nav_le"><img src="images/nav_le.jpg" /></li>
		<li><a href="index.jsp" target="_parent"><img src="images/nav_1.jpg" border="0" /></a></li>
		<li><a href="coupons_more.jsp" target="_parent"><img src="images/nav_2.jpg" border="0" /></a></li>
		<li><a href="merchants.jsp" target="_parent"><img src="images/nav_3.jpg" border="0" /></a></li>
		<!--<li><a href="gift.jsp" target="_parent"><img src="images/nav_4.jpg" border="0" /></a></li>-->
		<li><a href="terminals.jsp" target="_parent"><img src="images/nav_6.jpg" border="0" /></a></li>
		<li><a href="vips.jsp" target="_parent"><img src="images/nav_7.jpg" border="0" /></a></li>
		<li></li>
		<li class="nav_ri"><img src="images/nav_ri.jpg" /></li>
		<%
if(session.getAttribute(Constants.MEMBER_KEY) != null)
{
	Member member=new Member(globa);
	Member memberTemp=member.show("where strCardNo='"+globa.getMember().getStrCardNo()+"'");
	int vip = globa.memberSession.getIntType();
	String vipString="";
	if(vip==1)
	{
		vipString="（VIP）";
	}
	
%>

<li style="width:300px;margin-top:6px;color:white;font-size: 16px;font-family: 黑体; text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;
<a style="color: white;" href="history.jsp" target="_parent"><%=globa.memberSession.getStrName()+vipString%></a>,欢迎回来

</li>

<%
}%>

	</ul>
 </div>
<!--nav导航结束-->
</div>
<!--top结束-->
</form>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>
