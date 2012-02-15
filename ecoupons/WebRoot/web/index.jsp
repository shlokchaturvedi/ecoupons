<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.ejoysoft.ecoupons.web.Index"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ejoysoft.common.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponTop"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponComment"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
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
<!--

function showlayer(id,index,other)
{
eval("document.getElementById('clayer0_"+id+"').className='searchtabxz'");
eval("document.getElementById('clayer0_"+index+"').className='midri1_1'");
eval("document.getElementById('clayer0_"+other+"').className='midri1_1'");

eval("document.getElementById('sort_con_"+id+"').style.display='block'");
eval("document.getElementById('sort_con_"+index+"').style.display='none'");
eval("document.getElementById('sort_con_"+other+"').style.display='none'");
}

// -->
</SCRIPT>

</head>
<body>
<iframe height="167" border=0 marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
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
%>
	<DIV id=left-bar-mid2  >
	<form action="">
	<p class=weluser><b><%out.print(globa.memberSession.getStrName()); %></b>,欢迎回来</p>
	<p>我的余额：<a ><%= globa.memberSession.getFlaBalance() %></a></p>
	<p>我的积分：<a ><%= globa.memberSession.getIntPoint() %></a></p>
	<p><INPUT class=Btn value="我的收藏" type=submit> 
	<INPUT class=Btn value="历史记录" type=button> </p>
	</form>
	</DIV> 
	<%

}else {%>
	<DIV id=left-bar-mid   >	
	<form name="frm" METHOD=POST ACTION="<%=application.getServletContextName()%>/web/Auth">		
	<input type="hidden" name="actiontype" value="weblogon" />		
	<input type="hidden" name="authType" value="password"/>		
	<P class=userbg>		
	<INPUT id=txt_username class=user value=账号/手机 onclick="document.getElementById('txt_username').value=''" type=text name=username>		
	</P><P class=passwordbg><INPUT id=txt_pwd class=password type=password name=password></P>		
	<DIV class="floatR"><IMG id=code border=0 name=checkcode alt=验证码 align=absMiddle src="../image.jsp" width=60 height=18></DIV>		
	<P class=numberbg><INPUT id=txt_yzm class="number floatL" type=text name=yanzm></P><A class=forget href="#">忘记密码了？</A>&nbsp;&nbsp;		
	<A class=change href="#" onclick="javascript:var dt=new Date();document.getElementById('code').src='../image.jsp?dt='+dt;">换一张图片</A><BR>		
	<INPUT id=btn_login class=loginBtn value="登 录" type=submit name=btn_login> 		
	<INPUT class=regBtn value="注 册" type=button></form></DIV>		
<%
}
%>




  
  
  




 


<!--会员登录结束 -->

			
<!--会员登录后切换显示 -->

<!--会员登录后结束 -->


<DIV class=sortlogin_bottom></DIV>

</div>
 
  <DIV class=sort>
<DIV class=sort_top>
<H2><STRONG>类别检索</STRONG></H2></DIV>
<DIV class=sort_con1>
<UL>
<%
Index index=new Index(globa);
HashMap<SysPara, Integer> hmTrades=index.returnTrade();
Vector<SysPara> vctTrades=new Vector<SysPara>();
Iterator iterator=hmTrades.entrySet().iterator();
while(iterator.hasNext()){
	Map.Entry<SysPara, Integer> entry=(Map.Entry<SysPara, Integer>)iterator.next();
	out.print("<LI><A href=javascript:void(0)'>"+entry.getKey().getStrName()+"&nbsp;&nbsp;("+entry.getValue()+")</A></LI>");
	vctTrades.add(entry.getKey());
}
%>
  </UL></DIV>
<DIV class=sort_bottom></DIV></DIV> 
</div>
  	<form name=frm action="index.jsp">
  <div class="mid">
	<DIV class=mid_xt><marquee behavior="scroll" scrollamount="5" onmouseover="this.stop();" onmouseout="this.start();">
	<%
	Shop shop=new Shop(globa);
    Vector<Shop> vctShops=shop.list("order by dtcreatetime  desc",1,10);
    for(int i=0;i<vctShops.size();i++){
    out.print("<a href='merchantsinfo.jsp?strid="+vctShops.get(i).getStrId()+"' target=_blank alt='"+vctShops.get(i).getStrBizName()+"'><img src='../shop/images/" +vctShops.get(i).getStrSmallImg() +"' width='99' height='44'  border='0' /></a>");
    }
	%>
	
	</marquee></DIV>
  	<DIV class=jsadbox><!--切屏广告--><IFRAME height=200 marginHeight=0 src="homeimg.jsp" frameBorder=0 width=535 marginWidth=0 scrolling=no></IFRAME></DIV>
	<DIV class=mid_sj>
		<div class="sj_tit">
		  <div class="sj_name" >商家优惠信息</div>
		  <div class="sj_sel"><select name="strTradeId" class="sjfl" onchange="document.getElementById('strTradeId').value=this.value;frm.submit();">
		    
		     <% 
		    for(int j=0;j< vctTrades.size();j++){
		    	out.print("<option value="+vctTrades.get(j).getStrId()+">"+vctTrades.get(j).getStrName()+"</option>");
		    }
		    
		    %>
	      </select></div>
	    </div>
		
			<div class="sj_line">
		<img src="images/sj_line.jpg" />
	    </div>
		<div class="sj_box">
		  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <%
		  String  strTradeId=ParamUtil.getString(request,"strTradeId");
		    
		    	HashMap<String, String>hmShopCoupon=index.returnShopCoupon(strTradeId);
		    	Iterator iter=hmShopCoupon.entrySet().iterator();
		    	while(iter.hasNext()){
		    		Map.Entry<String, String> entry=(Map.Entry<String, String>)iter.next();
		    		out.print("<tr><td width='50%' height='26'><a href='#'><b>・&nbsp;"+entry.getKey()+"&nbsp;</b>"+entry.getValue()+"</a></td></tr>");
		    		
		    	}	
		   
		  %>
            
            
           
            <tr>
              <td height="26"><a id="abc1" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc2" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            <tr>
              <td height="26"><a id="abc3" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc4" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            <tr>
              <td height="26"><a id="abc5" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc6" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            <tr>
              <td height="26"><a id="abc7" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc8" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            <tr>
              <td height="26"><a id="abc9" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc10" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            <tr>
              <td height="26"><a id="abc11" href="#"><b>・&nbsp;商家名称</b></a></td>
              <td><a id="abc12" href="#"><b>・&nbsp;商家名称 </b>商家优惠活动信息</a></td>
            </tr>
            
          </table>
		</div>
	</DIV>
  </div>
  </form>
  
  
  <div class="right">
  
  <DIV class=sort>
<DIV class=sort_top><H3><STRONG>下载优惠券排行</STRONG></H1> 
  <span><a href="#" id=clayer0_1  onmouseover=showlayer(1,2,3)><font color="#CC6600">日</font></a>&nbsp;<a href="#" id=clayer0_2  onmouseover=showlayer(2,1,3)><font color="#CC6600">周</font></a>&nbsp;<a href="#" id=clayer0_3  onmouseover=showlayer(3,1,2)><font color="#CC6600">月</font></a></span></DIV>
<DIV id=sort_con_1>
<UL>
<%
Vector<String>vecCouponps=index.returnTopCoupons("日");
if(vecCouponps.size()>8){
for(int i=0;i<8;i++){
	out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
}}else{
	for(int i=0;i<vecCouponps.size();i++){
		out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
	}	
	
}

%>

  </UL></DIV>
  
 <DIV id=sort_con_2 style="display:none">
<UL>
 <%
vecCouponps=index.returnTopCoupons("周");
if(vecCouponps.size()>8){
for(int i=0;i<8;i++){
	out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
}}else{
	for(int i=0;i<vecCouponps.size();i++){
		out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
	}	
	
}

%>
</UL></DIV> 
  
  <DIV id=sort_con_3 style="display:none">
<UL>
 <%
vecCouponps=index.returnTopCoupons("月");
if(vecCouponps.size()>8){
for(int i=0;i<8;i++){
	out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
}}else{
	for(int i=0;i<vecCouponps.size();i++){
		out.print("<LI>・<A href='#'>"+vecCouponps.get(i)+"</A></LI>");
	}	
	
}

%>
</UL></DIV>
  
  
  
<DIV class=sort_bottom></DIV></DIV>
  
  
  
   <DIV class=sort>
<DIV class=sort_top>
<H1><STRONG>热门评论</STRONG></H1></DIV>
	<DIV class=sort_con>
	<UL>
	<%
	CouponComment couponComment=new CouponComment(globa);
	Vector<CouponComment> vctCouponComment=couponComment.list("",1,6);
		for(int i=0;i<vctCouponComment.size();i++){
			out.print("<LI>・<A href='#'>"+vctCouponComment.get(i).getStrComment()+"</A></LI>");
	}
	%>
	  
	 
	 </UL>
	</DIV>
<DIV class=sort_bottom></DIV>
  </div>
  </div>

</div>

<iframe style="HEIGHT: 340px" border=0 marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>