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
<iframe style="HEIGHT: 130px" frameborder=0 marginwidth=0 marginheight=0 src="top.jsp" width="100%" scrolling=no></iframe>

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
	<IMG id=cps_image src="../coupon/images/"+<%=obj0.getStrLargeImg()%> width=340 height=218>
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
  <DIV class=info_mid>使用说明：<BR>
    1、凭此券到店消费可享受优惠！<BR>
    2、此优惠不得与店内其他优惠一起使用；<BR>
    3、此券不兑换现金、不找零、不开发票；<BR>
    4、最终解释权归本店所有。<BR><BR>
  联系电话：<%=obj3.getStrPhone()%>
<BR>
详细地址：<%=obj3.getStrAddr()%>
<div class=info_bottom>
<div class=coupon_jzrq><FONT color=#ff0000>截止时间：<%=obj0.getDtExpireTime().substring(0,10)%></FONT></div>
<DIV class=coupon_bar>
  <UL>
  <LI><a href="#" onclick="window.open('coupon_print.jsp?random=<%= Math.random()%>&strid=<%=obj0.getStrId()%>&strimg=<%=obj0.getStrPrintImg()%>','','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=420,height=540,left=450,top=160');">打印</a></LI>
    <li><a href="#" onclick="window.showModalDialog('favourite_act.jsp?strid=<%=obj0.getStrId()%>&random=<%= Math.random()%>', '', 'dialogWidth=200px;dialogHeight:150px;dialogTop:400px;dialogLeft:550px;scrollbars=yes;status=yes;center=yes;')";><img src="images/collection.jpg" border="0" style="CURSOR: pointer" /> 收藏</a></li> 
	<LI><a href="#"><IMG src="images/sms.jpg" border="0" style="CURSOR: pointer"> 短信</a></LI> </UL>
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
<DIV class=link_mid>(1) 前沿生活优惠券支持打印、短信或彩信三种模式，可选择优惠券支持的下载模式，或您方便的使用方式。<BR>(2) 
短信、彩信优惠券下载服务时间：上午9:00-下午21:00，为保证发送的安全性，其他时间段领取将在下一服务时间段发送；打印券下载无时间限制。<BR>(3) 
高峰时间段短信或彩信优惠券的领取会延迟3-15分钟，无需重复领取。<BR>(4) 
前沿生活网站上商家发布的所有电子优惠券均由正规商家发布，亦经过本站的严格审核，信息公正准确有效，请放心下载使用。<BR>(5) 
请在消费时向商家出示优惠券，如在使用过程中遇到任何问题，请致电前沿生活客服热线。<BR>(6) 
下载彩信优惠券时，请确认您的手机能正常接收彩信,详情可查看手机设置帮助； 打印优惠券时，请确认您的电脑已和打印机连接。<BR>(7) 
不得擅自修改短信、彩信优惠券或打印优惠券的内容，如有特殊情况，会在信息中说明。优惠券内容最终解释权归该加盟商家所有。如有问题，可联系网站客服。</DIV>
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
<DIV style="WIDTH: 10%; FLOAT: left; HEIGHT: 45px"><IMG alt="" src="images/201.jpg" width=45 height=45> </DIV>
<DIV style="LINE-HEIGHT: 18px; WIDTH: 80%; FLOAT: left; HEIGHT: 33px"><SPAN style="COLOR: #001c55"><%=namememb%>：</SPAN><SPAN 
style="COLOR: gray"><%=comobj1.getStrComment() %></SPAN> </DIV>
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
<form action="comment_act.jsp" method=post name=frm1>
<input type="hidden" name="strcouponid" value="<%=strId%>" > 
<TABLE border=0 width="100%">
  <TBODY>
  <TR>
    <TD align="center">内容： </TD>
    <TD colSpan=7><LABEL><TEXTAREA style="WIDTH: 500px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=strcomment></TEXTAREA> 
      </LABEL><SPAN id=span_sub></SPAN></TD></TR></TBODY></TABLE>
<%
if(strId.equals(""))
{
 %>
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="login" >
<TABLE id=tr_login>
  <TBODY>
  <TR>
    <TD align=left>用户名： </TD>
    <TD align=left><INPUT style="WIDTH: 90px" id=txt_username class=form value=用户名 type=text name=txt_username> 
    </TD>
    <TD align=left>&nbsp; 密码： </TD>
    <TD align=left><INPUT style="WIDTH: 80px" id=txt_userpass class=form type=password name=txt_userpass> </TD>
    <TD align=left>&nbsp; 验证码： </TD>
    <TD align=left><INPUT style="WIDTH: 60px" class=form name=yanzm type=text value="" maxLength=4 size=10> </TD>
    <TD align=left><a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <IMG style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code border=0 name=checkcode src="../image.jsp"> </a></TD>
    <TD align=left><INPUT id=btn_login class=cx_button value=" " type=submit name=btn_login> 
    </TD></TR></TBODY>
 </TABLE>
 <%
 }
 else{  %>
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>" >
<TABLE id=tr2>
  <TBODY>
  <TR>
    <TD align=right>验证码： </TD>
    <TD align=left><INPUT style="WIDTH: 60px" name=yanzm class=form type=text value="" maxLength=4 size=10> </TD>
    <TD align=left> <a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code2').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <IMG style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code2 border=0 name=checkcode src="../image.jsp"> 
    </a></TD>
    <TD align=left><INPUT id=btn_login class=cy_button value=" " type=submit name=btn_login> 
    </TD></TR></TBODY></TABLE>    
    
 <%
 } 
 %>
 
 </form>
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
Vector<Coupon> vctCoupon = obj.listByShopId(" where strshopid='" + obj3.getStrId() + "'");
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
         %><img src="<%="../coupon/images/" + obj4.getStrSmallImg() %>" width="126" height="89"/>
         <%
         }
         else{
          %>
          <img src="<%="images/temp.jpg" %>" width="126" height="89" />
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
  <p>联系电话：<%=obj3.getStrPhone() %></p>
  <p>联系人：<%=obj3.getStrPerson() %></p>
  <p>地 &nbsp;址：<%=obj3.getStrAddr() %></p>
  <p>简 &nbsp;介：<%=obj3.getStrIntro() %></p>
</DIV>
<DIV class=card_bottom></DIV></DIV></DIV></DIV>
<iframe style="HEIGHT: 340px" frameborder=0 marginwidth=0 marginheight=0 src="bottom.jsp"  width="100%" scrolling=no></iframe>
<%globa.closeCon();%>
</body>
</html>
