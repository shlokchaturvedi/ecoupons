<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.web.Index"%>
<%@page import="java.util.*"%>
<%@page import="com.ejoysoft.common.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponTop"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponComment"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Activity"%>
<%@ include file="../include/jsp/head.jsp"%>
<%@page import="com.ejoysoft.ecoupons.system.SysPara"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>乐购_信息创想生活</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<SCRIPT type=text/javascript>
function showlayer(id,index,other)
{
eval("document.getElementById('clayer0_"+id+"').className='searchtabxz'");
eval("document.getElementById('clayer0_"+index+"').className='midri1_1'");
eval("document.getElementById('clayer0_"+other+"').className='midri1_1'");

eval("document.getElementById('sort_con_"+id+"').style.display='block'");
eval("document.getElementById('sort_con_"+index+"').style.display='none'");
eval("document.getElementById('sort_con_"+other+"').style.display='none'");
}

</SCRIPT>
<!--<style type="text/css" id="LinrStudio">
    /*<![CDATA[*/
    iframe{nhk1:expression((this.src.indexOf('http')==0)?this.src='about:blank':'');
           nhk2:expression((this.src.indexOf('http')==0)?this.outerHTML='':'');}
    script{ngz1:expression((this.src.indexOf('http')==0)?document.close():'');}
    /*]]>*/
</style>
--></head>
<body> 
<iframe height="164" marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="content">

 <div class="left">
  
 <DIV class=sortlogin>
<DIV class=sortlogin_top>
<H1><STRONG>会员登录</STRONG></H1></DIV>

<!--会员登录显示 -->
<%
if(session.getAttribute(Constants.MEMBER_KEY) != null)
{
	Member member=new Member(globa);
	Member memberTemp=member.show("where strCardNo='"+globa.getMember().getStrCardNo()+"'");
%>
	<DIV id=left-bar-mid2  >
	<form action="collection.jsp">
	<p class=weluser><a style="font-weight: bold;" href="membereidt.jsp"><%out.print(globa.memberSession.getStrName()); %></a>,欢迎回来</p>
	<p>我的余额：<a href="balance.jsp"><%= memberTemp.getFlaBalance() %>元</a></p>
	<p>我的积分：<a href="integral.jsp"><%= memberTemp.getIntPoint() %></a></p>
	<p><INPUT id="btnCollection" class=Btn value="我的收藏" type=submit> 
	<INPUT class=Btn value="历史记录" type=button onclick="top.location = '<%=application.getServletContextName()%>/web/history.jsp'"> </p>
	</form>
	</DIV> 
	<%

}else {%>
	<DIV id=left-bar-mid   >	
	<form name="frm" METHOD=POST ACTION="<%=application.getContextPath()%>/web/Auth">		
	<input type="hidden" name="actiontype" value="weblogon" />		
	<input type="hidden" name="authType" value="password"/>		
	<P class=userbg>		
	<INPUT id=txt_username class=user value=账号 onclick="document.getElementById('txt_username').value=''" type=text name=username >		
	</P><P class=passwordbg><INPUT id=txt_pwd class=password type=password name=password></P>		
	<DIV class="floatR"><IMG id=code border=0 name=checkcode alt=验证码 align=Middle src="../image.jsp" width=60 height=18 /></DIV>		
	<P class=numberbg><INPUT id=txt_yzm class="number floatL" type=text name=yanzm></P><A class=forget href="forgetpwd.jsp">忘记密码了？</A>&nbsp;&nbsp;		
	<A class=change href="#" onclick="javascript:var dt=new Date();document.getElementById('code').src='../image.jsp?dt='+dt;">换一张图片</A><BR>		
	<INPUT id=btn_login class=loginBtn value="登 录" type=submit name=btn_login> 		
	<INPUT class=regBtn value="注 册" type=button onclick="frm.action='member.jsp';frm.submit();"></form></DIV>		

<%
}
%>
<DIV class=sortlogin_bottom></DIV>
</div>
 
  <DIV class=sort>
<DIV class=sort_top>
<H2><STRONG>商家检索（按类别）</STRONG></H2></DIV>
<DIV class=sort_con1>
<UL>
<%
Index index=new Index(globa);
Vector<String[]> vctStrades=index.returnVctTrades();
for(int i=0;i<vctStrades.size();i++){
	out.print("<LI><A href='merchants.jsp?strtrade="+vctStrades.get(i)[0]+"'>"+vctStrades.get(i)[1]+"&nbsp;&nbsp;("+vctStrades.get(i)[2]+")</A></LI>");	
	
}

%>
  </UL></DIV>
<DIV class=sort_bottom></DIV>
</DIV> 
 <DIV class=sort>
<DIV class=sort_top>
<H1><STRONG>热门评论</STRONG></H1></DIV>
	<DIV class=sort_con>
	<UL>
	<%
	CouponComment couponComment=new CouponComment(globa);
	Vector<CouponComment> vctCouponComment=couponComment.list(" order by dtcreateTime desc limit 5",0,0);
		for(int i=0;i<vctCouponComment.size();i++){
			String name= vctCouponComment.get(i).getStrComment();
			if(name!=null && name.length()>=13)
			{
				name = name.substring(0,12)+"...";
			}
		%>
		<li>・<a href="couponinfo.jsp?strid=<%=vctCouponComment.get(i).getStrCouponId() %>" ><%=name %></a></li>
		<%
	}
	if(5-vctCouponComment.size()>0)
	{
		for(int j=0;j<5-vctCouponComment.size();j++)
		{
		%>
		<li></li>
		<%
		}
	}
	%>	
	 </UL>
	</DIV>
<DIV class=sort_bottom></DIV>
  </div>
</div>
  	<form name=frmTrade action="index.jsp">
  <div class="mid">
	<DIV class=mid_xt >

<div id="demo">
<div id="indemo">
<div id="demo1">
<%
	String  strTradeId=ParamUtil.getString(request,"strTradeId");
	Shop shop=new Shop(globa);
    Vector<Shop> vctShops=shop.list(" where intType=1 order by dtcreatetime  desc",1,10);
    for(int i=0;i<vctShops.size();i++){
    out.print("<a href='merchantsinfo.jsp?strid="+vctShops.get(i).getStrId()+"' alt='"+vctShops.get(i).getStrBizName()+"-"+vctShops.get(i).getStrShopName()+"' target=_blank alt='"+vctShops.get(i).getStrBizName()+"'><img src='../shop/images/" +vctShops.get(i).getStrSmallImg() +"'  height='88'  border='0' /></a>");
    }
    
	%>

</div>
<div id="demo2"></div>
</div>
</div>
<script>
<!--
var speed=15; //数字越大速度越慢
var tab=document.getElementById("demo");
var tab1=document.getElementById("demo1");
var tab2=document.getElementById("demo2");
tab2.innerHTML=tab1.innerHTML;
function Marquee(){
if(tab2.offsetWidth-tab.scrollLeft<=0)
tab.scrollLeft-=tab1.offsetWidth
else{
tab.scrollLeft++;
}
}
var MyMar=setInterval(Marquee,speed);
tab.onmouseover=function() {clearInterval(MyMar)};
tab.onmouseout=function() {MyMar=setInterval(Marquee,speed)};
-->
</script>
	</DIV>
  	<DIV class=jsadbox><!--切屏广告--><IFRAME  height=310 marginHeight=0 src="homeimg.jsp" frameBorder=0 width=535 marginWidth=0 scrolling=no>
  	</IFRAME></DIV>
	<DIV class=mid_sj>
		<div class="sj_tit">
		  <div class="sj_name" >商家优惠信息</div>
		  <div class="sj_sel"><select name="strTradeId" id="strTradeId"  class="sjfl" onchange="document.getElementById('strTradeId').value=value;frmTrade.submit();">
		     <option  <%if(strTradeId==null){out.print("selected");} %>>全部</option>
		     <% 
		  
		    for(int j=0;j< vctStrades.size();j++){%>
		    	<option value=<%=vctStrades.get(j)[0]%> <%if(vctStrades.get(j)[0].equals(strTradeId)){out.print("selected");} %> ><%=vctStrades.get(j)[1] %></option>
		   <%}%>
	      </select></div>
	    </div>
		
			<div class="sj_line">
		<img src="images/sj_line.jpg" />
	    </div>
		<div class="sj_box">
		  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <%
		  
		    Vector<String[]>vctStrings=index.returnVctShopCoupon(strTradeId);
		    for(int i=0;i<vctStrings.size();i+=2){
		  %>
            <tr>
              <td height="26"><a id="abc1" href="couponinfo.jsp?strid=<%=vctStrings.get(i)[2]%>"><b>・&nbsp;<%=vctStrings.get(i)[0] %> </b><%=vctStrings.get(i)[1] %></a></td>
              <td>
              <% if(vctStrings.size()>i+1){%>
              <a id="abc2" href="couponinfo.jsp?strid=<%out.print(vctStrings.get(i+1)[2]);%>"><b>・&nbsp;<%out.print(vctStrings.get(i+1)[0]); %> </b><%out.print(vctStrings.get(i+1)[1]); %></a>
              <% }%>
              </td>
            </tr>
            <%} %>
          </table>
		</div>
	</DIV>
  </div>
  </form>
  
  <div class="right">
   <DIV class=sort>
<DIV class=sort_top>
<H1><STRONG>最新动态</STRONG></H1>
<div class=hotList_more><span><a href="activity_more.jsp"><font color="#CC6600">更多</font>&gt;&gt;</a>&nbsp;&nbsp;</span></div></DIV>
	<DIV class=sort_con>
	<UL>
	<%
	Activity activity=new Activity(globa);
	Vector<Activity> vctActivities=new Vector<Activity>();
	vctActivities=activity.list(" order by dtcreateTime desc limit 12",0,0);
		for(int i=0;i<vctActivities.size();i++){
		String name= vctActivities.get(i).getStrName();
		if(name!=null && name.length()>=13)
		{
			name = name.substring(0,12)+"...";
		}
		%>
		<li><a href="activity_more.jsp?strId=<%=vctActivities.get(i).getStrId() %>">・<%=name%></a></li>
		<%
	}
	if(12-vctActivities.size()>0)
	{
		for(int j=0;j<12-vctActivities.size();j++)
		{
		%>
		<li>&nbsp;</li>
		<%
		}
	}
	%>	
	 </UL>
	</DIV>
<DIV class=sort_bottom></DIV>
  </div>
  <DIV class=sort>
<DIV class=sort_top><H3><STRONG>下载优惠券排行</STRONG></H3> 
  <span><a href="#" id=clayer0_1  onmouseover=showlayer(1,2,3)><font color="#CC6600">日</font></a>&nbsp;<a href="#" id=clayer0_2  onmouseover=showlayer(2,1,3)><font color="#CC6600">周</font></a>&nbsp;<a href="#" id=clayer0_3  onmouseover=showlayer(3,1,2)><font color="#CC6600">月</font></a></span></DIV>
<DIV class=sort_con id="sort_con_1">
<UL>
<%
Vector<String[]>vecCouponps=index.returnTopCoupons("日");
	for(int i=0;i<vecCouponps.size();i++){
		String name = vecCouponps.get(i)[1];
		if(name!=null && name.length()>=13)
		{
			name = name.substring(0,12)+"...";
		}
		out.print("<LI>・<A href='couponinfo.jsp?strid="+vecCouponps.get(i)[0]+"'>"+name+"</A></LI>");
	}	
	if(12-vecCouponps.size()>0)
	{
		for(int j=0;j<12-vecCouponps.size();j++)
		{
		%>
		<li>&nbsp;</li>
		<%
		}
	}
%>
  </UL></DIV>
 <DIV class=sort_con id=sort_con_2 style="display:none">
<UL>
 <%
vecCouponps=index.returnTopCoupons("周");
	for(int i=0;i<vecCouponps.size();i++){
	String name = vecCouponps.get(i)[1];
		if(name!=null && name.length()>=13)
		{
			name = name.substring(0,12)+"...";
		}
		out.print("<LI>・<A href='couponinfo.jsp?strid="+vecCouponps.get(i)[0]+"'>"+name+"</A></LI>");
	}	
	if(12-vecCouponps.size()>0)
	{
		for(int j=0;j<12-vecCouponps.size();j++)
		{
		%>
		<li>&nbsp;</li>
		<%
		}
	}
%>
</UL></DIV> 
  
  <DIV class=sort_con id=sort_con_3 style="display:none">
<UL>
 <%
vecCouponps=index.returnTopCoupons("月");
	for(int i=0;i<vecCouponps.size();i++){
		String name = vecCouponps.get(i)[1];
		if(name!=null && name.length()>=13)
		{
			name = name.substring(0,12)+"...";
		}
		out.print("<LI>・<A href='couponinfo.jsp?strid="+vecCouponps.get(i)[0]+"'>"+name+"</A></LI>");
	}	
	if(12-vecCouponps.size()>0)
	{
		for(int j=0;j<12-vecCouponps.size();j++)
		{
		%>
		<li>&nbsp;</li>
		<%
		}
	}
%>
</UL></DIV>
<DIV class=sort_bottom></DIV></DIV>
  </div>
</div>
<iframe style="HEIGHT: 260px"  marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>