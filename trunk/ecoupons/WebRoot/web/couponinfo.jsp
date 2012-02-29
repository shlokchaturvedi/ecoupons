<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.util.*,
		com.ejoysoft.common.exception.*,
		com.ejoysoft.common.*,
		com.ejoysoft.ecoupons.business.Member,
		com.ejoysoft.ecoupons.business.Coupon,
		com.ejoysoft.common.exception.IdObjectException,
		com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponComment"%>
<%@ include file="../include/jsp/head.jsp"%>
<%

	String strId = ParamUtil.getString(request,"strid","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Coupon obj=new Coupon(globa,false);
    Coupon obj0=obj.show(where);
    if(obj0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
    }
    Shop obj2= new Shop(globa);
    Shop obj3 = obj2.show(" where strid='"+obj0.getStrShopId()+"'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/couponinfo.css" rel="stylesheet" type="text/css" />
<link href="css/comment.css" rel="stylesheet" type="text/css" />
<title>优惠券详细</title>  
</head>
<body>&nbsp; 
<iframe style="HEIGHT: 164px" frameborder=0 marginwidth=0 marginheight=0 src="top.jsp" width="100%" scrolling=no></iframe>

<!--正文部分-->
<DIV id=Main>
<DIV id=Left>
<DIV class=left_top>
<P>优惠券详细：<%=obj0.getStrName() %></P></DIV>
<DIV class=left_mid><!--优惠券详情-->
<DIV class=show>
  <DIV class=show_mid>
<DIV class=show_img>
<%
if(obj0.getStrLargeImg()!=null && obj0.getStrLargeImg().length()>0)
{
	%>
	<IMG id=cps_image src=<%="../coupon/images/"+obj0.getStrLargeImg()%> width=340 height=218>
	<%
}
else{
 %>
<IMG id=cps_image src="images/temp.jpg" width=340 height=218>
<%
 }
%></DIV>
<DIV class=show_desgin>
<DIV style="BACKGROUND-IMAGE: url(images/zk.jpg); TEXT-ALIGN: center; WIDTH: 132px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 134px">
<DIV class=coupon_money><%=obj0.getFlaPrice()%>元</DIV></DIV></DIV>
<DIV class=clearfloat></DIV>
<DIV class=show_line></DIV>
<DIV class=info>
  <DIV class=info_mid>使用说明：
<%=Format.forbidNull(obj0.getStrInstruction().replace("\n","<br/>")) %>
    <!--
  联系电话：<%=obj3.getStrPhone()%>
--><BR>
详细地址：<%=obj3.getStrAddr()%>
<div class=info_bottom>
<div class=coupon_jzrq><FONT color=#ff0000>截止时间：<%=obj0.getDtExpireTime().substring(0,10)%></FONT></div>
<DIV class=coupon_bar>
  <UL>
  <LI><a href="#" onclick="window.open('coupon_print.jsp?random=<%= Math.random()%>&strid=<%=obj0.getStrId()%>&strimg=<%=obj0.getStrPrintImg()%>','','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=260,height=600,left=500,top=100');"><img src="images/print.jpg" border="0" style="CURSOR: pointer" /> 打印</a></LI>
    <li><a href="#" onclick="if(window.showModalDialog('favourite_act.jsp?strid=<%=obj0.getStrId()%>&random=<%= Math.random()%>', '', 'dialogWidth=200px;dialogHeight:150px;dialogTop:400px;dialogLeft:550px;scrollbars=yes;status=yes;center=yes;')=='success'){window.location.reload();};" ><img src="images/collection.jpg" border="0" style="CURSOR: pointer" /> 收藏</a></li> 
	<!--<LI><a href="#"><IMG src="images/sms.jpg" border="0" style="CURSOR: pointer"> 短信</a></LI> --> </UL>
</DIV>
</div> 
 </DIV>
</DIV>
  </DIV>
</DIV>
<!--友情提示-->
<DIV class=link>
<DIV class=link_top>
<P>友情提示</P></DIV>
<DIV class=link_mid>(1) 乐购优惠券支持网站打印和终端打印模式，您可以选择这两种下载模式来下载您心仪的优惠券。<BR>				
					(2) 乐购上优惠券可以直接抵用现金使用的一类优惠券，在乐购您可以以超低的折扣购买到非常实惠的优惠券。<BR>
					(3) 乐购上打印的每张乐购有价券均会产生唯一的优惠券验证码，优惠券打印后，您可以凭纸质优惠券去相应的商家消费。如在使用过程中遇到任何问题，请致电乐购客服热线或联系乐购网站客服。	<BR>
					(4) 网站打印优惠券时，请确认您的电脑已和打印机连接。<BR>
					(5) 乐购上所有优惠券内容最终解释权归该加盟商家所有。如出现争议，请致电乐购客服热线或联系乐购网站客服。	<BR>
					(6) 乐购上所有商家发布的所有电子优惠券均由正规商家发布，亦经过本站的严格审核，信息公正准确有效，请放心下载使用。<BR>
					
</DIV>
<DIV class=link_bottom></DIV></DIV><!--更多相关-->
<!--发表评论-->

<%
    CouponComment comobj = new CouponComment(globa);
    //记录总数
	int intAllCount=comobj.getCount(" where strcouponid='"+strId+"'");
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	//int intPageSize=globa.getIntPageSize();
	int intPageSize=5;
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<CouponComment> vctcom = comobj.list(" where strcouponid='"+strId+"'",intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctcom!=null&&vctcom.size()>0?vctcom.size():0);
%>
<DIV style="PADDING-LEFT: 23px; width:578px;">
<DIV style="WIDTH: 100%" class=ny_left>
<DIV style="FONT-SIZE: 12px" class=line_gray>
<DIV class=mp_auto>
<DIV style="HEIGHT: 20px" class="box bold word_gra sp_nav_bg jianju13 ">网友评论：(已有<SPAN 
class=red><%=intAllCount%></SPAN>条评论)</DIV>
<DIV style="HEIGHT: 1px; BORDER-TOP: #ebebeb 1px solid"></DIV>
<DIV style="PADDING-LEFT: 5px"><BR>
<form name=frm action="couponinfo.jsp" method="post" >
<input type="hidden" name="strid" value="<%=strId %>"/>
<%
	for (int i = 0;i < vctcom.size(); i++) {
       CouponComment comobj1 = vctcom.get(i);
       Member memobj = new Member(globa);
       String twhere=" where strcardno='111'";
       if(comobj1.getStrMemberCardNo()!=null)
       	{	twhere =" where strcardno='"+comobj1.getStrMemberCardNo()+"'";}
       Member memobj1 = memobj.show(twhere);
       String namememb="Xman";
       if(memobj1!=null)
       {
        	namememb=memobj1.getStrName();
       }
%>
<DIV style="PADDING-LEFT: 5px"><BR>
<DIV style="HEIGHT: 50px">
<DIV style="WIDTH: 10%; FLOAT: left; HEIGHT: 40px"><IMG alt="" src="images/touxiang.jpg" width=45 height=33> </DIV>
<DIV style="LINE-HEIGHT: 18px; WIDTH: 80%; FLOAT: left; HEIGHT: 33px"><SPAN style="COLOR: #001c55"><%=namememb%>：</SPAN>
<SPAN style="COLOR: gray"><%=comobj1.getStrComment().replace("\n","<br/>") %></SPAN> 
</DIV>
</DIV>
<DIV style="BORDER-BOTTOM: #808080 1px dotted; HEIGHT: 1px; CLEAR: both"></DIV><BR>
</DIV>
<%
    }
%>
<!-- 翻页开始 -->  
<%@ include file="include/cpage.jsp"%>
<!-- 翻页结束 -->
</form>
</DIV></DIV>
</DIV></DIV>
<DIV class="clr height_05"></DIV>
<DIV></DIV>
<DIV class="box  line_gray jianju9b">
<DIv class="box bold word_gra sp_nav_bg jianju13 ">发表评论：</DIV>
<DIV class="box jianju13 jianju2a word_12px">
<%
 if(session.getAttribute(Constants.MEMBER_KEY) != null)
 {  %>
<form action="comment_act.jsp" method=post name=frm1 >
<input type="hidden" name="strcouponid" value="<%=strId%>" > 
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>" >
<TABLE border=0 width="100%">
  <TBODY>
  <TR>
    <TD align="center">内&nbsp; &nbsp; 容： </TD>
    <TD colSpan=7><LABEL><TEXTAREA style="WIDTH: 500px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=strcomment></TEXTAREA> 
      </LABEL><SPAN id=span_sub></SPAN></TD></TR></TBODY></TABLE>

<TABLE id=tr2>
  <TBODY>
  <TR>
    <TD align=right>验 证 码： </TD>
    <TD align=left><INPUT style="WIDTH: 60px" name=yanzm class=form type=text value="" maxLength=4 size=10> </TD>
    <TD align=left> <a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code2').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <IMG style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code2 border=0 name=checkcode src="../image.jsp"> 
    </a></TD>
    <TD align=left><INPUT id=btn_login class=cy_button value=" " type=submit name=btn_login> 
    </TD></TR></TBODY></TABLE>   
    </form> 
    
 <%
 } 
else
{
 %>
<form name="frm2" method=post action="<%=application.getServletContextName()%>/web/Auth" >		
<input type="hidden" name="actiontype" value="weblogon2" />		
<input type="hidden" name="authType" value="password"/>		
<input type="hidden" name="strCouponId" value="<%=strId%>"/>
<TABLE border=0 width="100%">
  <TBODY>
  <TR>
    <TD align="center">内&nbsp; &nbsp; 容： </TD>
    <TD colSpan=7><LABEL><TEXTAREA style="WIDTH: 500px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=strcomment></TEXTAREA> 
      </LABEL><SPAN id=span_sub></SPAN></TD></TR></TBODY></TABLE>
	
<TABLE id=tr_login>
  <TBODY>
  <TR>
    <TD align=left>卡号/手机号： </TD>
    <TD align=left><INPUT style="WIDTH: 90px;" id=txt_username class=form value=" " onclick="document.getElementById('txt_username').value=''" type=text name=username> 
    </TD>
    <TD align=left>&nbsp; 密码： </TD>
    <TD align=left><INPUT style="WIDTH: 80px;" id=txt_userpass class=form type=password name=password > </TD>
    <TD align=left>&nbsp; 验证码： </TD>
    <TD align=left><INPUT style="WIDTH: 60px" class=form name=yanzm type=text value="" maxLength=4 size=10> </TD>
    <TD align=left><a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <IMG style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code border=0 name=checkcode src="../image.jsp"> </a></TD>
    <TD align=left><INPUT id=btn_login class=cx_button value=" " type=submit name=btn_login> 
    </TD></TR></TBODY>
 </TABLE>
 </form>
 <%
 }
 %>
    </DIV></DIV>
</DIV>
<!--评论结束-->

</DIV>
<DIV class=left_bottom></DIV></DIV>
<DIV id=Right>
<DIV class=card>
<DIV class=card_top>
<DIV class=heatitle><h6>本店其他优惠</h6>
</DIV>
</DIV>
<DIV class=card_mid>

<%
Vector<Coupon> vctCoupon = obj.listByShopId(" where strshopid='" + obj3.getStrId() + "' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime limit 6");
if(vctCoupon!=null&&vctCoupon.size()!=0)
{
	int k=vctCoupon.size(),n=0;
	for(int i=0;i<k;i++)
	{
		Coupon obj4 = vctCoupon.get(i);
		if (obj4!=null && !obj4.getStrId().equals(strId)) {
		n++;
		if(n==7)
		{
		   break;
		}
         %>
		<DIV class=tuangou_index>
        <div class=card_img><a href="couponinfo.jsp?strid=<%=obj4.getStrId() %>" target="_blank">
        <%
        if(obj4.getStrSmallImg()!=null && obj4.getStrSmallImg().length() > 0){
         %><img src="<%="../coupon/images/" + obj4.getStrSmallImg() %>" width="126" height="89" border="0" title="<%=obj4.getStrName()%>" />
         <%
         }
         else{
          %>
          <img src="<%="images/temp.jpg" %>" width="126" height="89" border="0" title="<%=obj4.getStrName()%>" />
          <%} %>
         </a></div>
        <div class=card_js><%=obj4.getStrName() %><br/>
		开始日期：<%=obj4.getDtActiveTime().substring(0,10) %><br/>
		截止日期：<%=obj4.getDtExpireTime().substring(0,10) %></div>
		</DIV>
        <div class=card_line></div>
        <%
        }       	
	}	
} 
%>
</DIV>
<DIV class=card_bottom></DIV></DIV>
<DIV class=card>
<DIV class=card_top>
<DIV class=heatitle><h6>相关商家</h6></DIV></DIV>
<DIV class=card_mid>
  <p>店铺名称：<%=obj3.getStrBizName() %>-<%=obj3.getStrShopName() %><br />
    行业分类：<%=obj3.getStrTradeName() %></p>
    <!--
  <p>联系电话：<%=obj3.getStrPhone() %></p>
  <p>联系人：<%=obj3.getStrPerson() %></p>
  -->
  <p>地 &nbsp;址：<%=obj3.getStrAddr() %></p>
  <p>简 &nbsp;介：<%=obj3.getStrIntro() %></p>
</DIV>
<DIV class=card_bottom></DIV></DIV></DIV></DIV>
<iframe style="HEIGHT: 260px" frameborder=0 marginwidth=0 marginheight=0 src="bottom.jsp"  width="100%" scrolling=no></iframe>
<%globa.closeCon();%>
</body>
</html>
