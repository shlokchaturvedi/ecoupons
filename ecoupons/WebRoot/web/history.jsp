<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ejoysoft.common.Constants,java.util.*"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
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
<%
String tWhere=" where strMemberCardNo='"+globa.getMember().getStrCardNo()+"'";
tWhere += " order by dtCreateTime desc";
CouponPrint obj=new CouponPrint(globa);
//记录总数
int intAllCount=obj.getCount(tWhere);
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
Vector<CouponPrint> vctObj=obj.list(tWhere,intStartNum,intPageSize);
//获取当前页的记录条数
int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
Coupon coupon=new Coupon(globa);
Shop shop=new Shop(globa);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="css/comment.css">

<title>历史记录</title>
</head>

<body>
<form name="frm" method=post action=" " >	
<iframe style="HEIGHT: 164px" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<DIV id=Main>
<DIV id=collect_Right>
<DIV class=collect_list>
<DIV class=collect_right_top>
<DIV class=collect_heatitle><h6>会员中心</h6></DIV>
</DIV>
<DIV class=collect_mid>
  <p>&nbsp;</p>
  <table width="96%" border="0"  cellpadding="0" cellspacing="0">
  <tbody>
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
      <td height="32" class="list_wz"><a href="memberpwd.jsp">&nbsp;&gt;&gt; 修改密码</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="#" 
      onClick="if (confirm('您确定要退出吗？')){top.location = '<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>';}	return false;">
      &nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
    </tbody>
  </table>
  <p>&nbsp;</p>
</DIV>
<DIV class=collect_bottom></DIV></DIV>
</DIV>

<DIV id=Left>
<DIV class=collect_left_top>
		<div class="collect_sf">历史记录</div>
</DIV>

<DIV class=collect_left_mid>
<DIV class=collect_show>  
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="DCDCDC">
    <tr> 
      <td height="25" align="center" bgcolor="EEEEEE" class="collect_show_tit">商家名称</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">优惠券名称</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">金额</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">时间</td>
      </tr>
      <%
      for(int i=0;i<vctObj.size();i++)
      {
       Coupon couponTemp=coupon.show("where strid='"+vctObj.get(i).getStrCouponId()+"'");
      %>
    <tr>
      <td height="25" align="center" bgcolor="#FFFFFF"><span class="STYLE1"><a href="merchantsinfo.jsp?strid=<%=couponTemp.getStrShopId() %>"><%=shop.returnBizShopName("where strid='"+couponTemp.getStrShopId()+"'")%></a></span></td>
      <td align="center" bgcolor="#FFFFFF"><a href="couponinfo.jsp?strid=<%=couponTemp.getStrId() %>"><%=couponTemp.getStrName() %></a></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=couponTemp.getFlaPrice() %></span></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=vctObj.get(i).getDtPrintTime() %></span></td>
      </tr>
      <%} %>
  </table>
</DIV>

  <!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
   	<!-- 翻页结束 -->
</DIV>

<DIV class=collect_show_bottom></DIV></DIV>
</DIV>

<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
</form>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>