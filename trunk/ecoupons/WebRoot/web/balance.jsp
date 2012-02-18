<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge,com.ejoysoft.common.*"%>
<%@page import="com.ejoysoft.ecoupons.web.RecordModel"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
    <%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
if(session.getAttribute(Constants.MEMBER_KEY) == null)
{
		globa.closeCon();
    response.getWriter().print("<script>alert('您还未登录！请先登录！');top.location = '"+application.getServletContextName()+"/web/index.jsp';</script>");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="css/comment.css">
<title>我的余额</title>
</head> 
<body>
<iframe style="HEIGHT: 167px" border=0 marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=no width="100%" scrolling=no></iframe>

<!--正文部分-->
<DIV id=Main>
<DIV id=collect_Right>
<DIV class=collect_list>
<DIV class=collect_right_top>
<DIV class=collect_heatitle><h6>会员中心</h6></DIV>
</DIV>
<DIV class=collect_mid>
  <p>&nbsp;</p>
  <table width="81%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="32" class="list_wz"><a href="collection.jsp">&nbsp;&gt;&gt; 我的收藏</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="history.jsp">&nbsp;&gt;&gt; 历史记录</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="balance.jsp" >&nbsp;&gt;&gt; 我的余额</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="integral.jsp">&nbsp;&gt;&gt; 我的积分</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="#" onClick="if (confirm('您确定要退出吗？')){top.location = '<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>';}	return false;" >&nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</DIV>
<DIV class=collect_bottom></DIV></DIV>
</DIV>
<DIV id=Left>
<DIV class=collect_left_top>
		<div class="collect_sf">我的积分</div>
</DIV>
<%
Recharge recharge=new Recharge(globa);
Vector<RecordModel> vctRecords=new Vector<RecordModel>();
Vector<Recharge>vctRecharges=recharge.list("where strmembercardno='"+globa.getMember().getStrCardNo()+"' order by dtcreatetime desc",0,0);
for(int i=0;i<vctRecharges.size();i++){
	RecordModel recordModel=new RecordModel();
	recordModel.setDtCreateTime(vctRecharges.get(i).getDtCreateTime());
	recordModel.setIntRecharge(vctRecharges.get(i).getIntMoney());
	recordModel.setFlaPay(-1);
	recordModel.setStrName("充值");
	vctRecords.add(recordModel);
}
Coupon couponCloba=new Coupon(globa);
CouponPrint couponPrint=new CouponPrint(globa);
float flaPriceTemp;
Vector<CouponPrint>vctCouponPrintTemp=couponPrint.list("where strmembercardno='"+globa.getMember().getStrCardNo()+"' order by dtcreatetime desc",0,0);
for(int i=0;i<vctCouponPrintTemp.size();i++){
	flaPriceTemp=couponCloba.show(" where strid='"+vctCouponPrintTemp.get(i).getStrCouponId()+"'").getFlaPrice();
	if(flaPriceTemp>0){
		RecordModel recordModel=new RecordModel();
		recordModel.setDtCreateTime(vctCouponPrintTemp.get(i).getDtPrintTime());
		recordModel.setFlaPay(flaPriceTemp);
		recordModel.setIntRecharge(-1);
		recordModel.setStrName("打印有价券");
		vctRecords.add(recordModel);
	}
}

//记录总数
int intAllCount=vctRecords.size();
//当前页
int intCurPage=globa.getIntCurPage();
//每页记录数
//int intPageSize=globa.getIntPageSize();
int intPageSize=6;
//共有页数
	int intPageCount=(intAllCount-1)/intPageSize+1;
// 循环显示一页内的记录 开始序号
int intStartNum=(intCurPage-1)*intPageSize+1;
//结束序号
int intEndNum=intCurPage*intPageSize;   
//获取到当前页面的记录集
//获取当前页的记录条数
int intVct=(vctRecords!=null&&vctRecords.size()>0?vctRecords.size():0);
Member member=new Member(globa);
%>
<DIV class=collect_left_mid>
<DIV class=collect_show>
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="60" valign="top">可用余额：<span class="fjwz"><%=member.show("where strCardNo='"+globa.getMember().getStrCardNo()+"'").getFlaBalance() %> </span> 元 &nbsp;&nbsp;&nbsp;&nbsp;<a href="alipay/payindex.jsp" /><img src="images/czban.jpg" width="56" height="24" /></a></td>
    </tr>
    <tr>
      <td height="28"><span class="bzb">余额变动情况</span></td>
    </tr>
  </table>
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="DCDCDC">
    <tr> 
      <td height="25" align="center" bgcolor="EEEEEE" class="collect_show_tit">时间</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">项目</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">支出</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">支入</td>
      
      </tr>
      <%
      for(int i=0;i< vctRecords.size();i++){
      %>
    <tr>
       <td height="25" align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=vctRecords.get(i).getDtCreateTime() %> 
         </span></td>
      <td align="center" bgcolor="#FFFFFF"><%=vctRecords.get(i).getStrName() %></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%if(vctRecords.get(i).getFlaPay()>0){out.print(vctRecords.get(i).getFlaPay());} %></span></td>
      <td align="center" bgcolor="#FFFFFF"><%if(vctRecords.get(i).getIntRecharge()>0){out.print(vctRecords.get(i).getIntRecharge());} %></td>
      </tr>
      <%} %>
    
  </table>
   <!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
   	<!-- 翻页结束 -->
</DIV>

</DIV>
<DIV class=collect_show_bottom></DIV></DIV>
</DIV>

<iframe style="HEIGHT: 340px" border=0 marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>