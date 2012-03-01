<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
    <%@page import="com.ejoysoft.common.*"%>
<%@page import="com.ejoysoft.auth.AppClass"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponFavourite"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
  <%@ include file="../include/jsp/head.jsp"%>
  <%
if(session.getAttribute(Constants.MEMBER_KEY) == null)
{
		globa.closeCon();
    response.getWriter().print("<script>alert('您还未登录！请先登录！');top.location = '"+application.getServletContextName()+"/web/index.jsp';</script>");
}
%>
<%
CouponFavourite couponFavourite=new CouponFavourite(globa);
Vector<CouponFavourite> vctCouponFavourites=couponFavourite.list("where strMemberCardNo='"+globa.getMember().getStrCardNo()+"'",0,0);
for(int i=0;i<vctCouponFavourites.size();i++)
{
for(int j=0;j<i;j++)
{
if(vctCouponFavourites.get(i).getStrCouponId().equals(vctCouponFavourites.get(j).getStrCouponId()))
{
vctCouponFavourites.remove(i);	
}	
}
}
Shop shop=new Shop(globa);
Coupon couponGloba=new Coupon(globa);
Coupon coupon=new Coupon();
//记录总数
int intAllCount=vctCouponFavourites.size();
//当前页
int intCurPage=globa.getIntCurPage();
//每页记录数
//int intPageSize=globa.getIntPageSize();
int intPageSize=8;
//共有页数
	int intPageCount=(intAllCount-1)/intPageSize+1;
// 循环显示一页内的记录 开始序号
int intStartNum=(intCurPage-1)*intPageSize+1;
//结束序号
int intEndNum=intCurPage*intPageSize;   
//获取到当前页面的记录集
//获取当前页的记录条数
int intVct=(vctCouponFavourites!=null&&vctCouponFavourites.size()>0?vctCouponFavourites.size():0);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<link  href="css/comment.css" rel="stylesheet" type="text/css" />
<title>我的收藏</title>
<script language=JavaScript>
function logout(){
	if (confirm("您确定要退出吗？"))
		top.location = "<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>";
	return false;
}
</script>
<script src="../include/js/list.js"></script>
<script type="text/javascript">
//批量删除信息
function del(){
	if (iCheckedNumber(document.all.strId) == 0) {
		alert("请先选择要删除的记录！");
		return;
	}
    if(!confirm('您是否确认要删除所选中的所有记录？'))
        return;
     frm.action="collection_act.jsp";
     frm.submit();
}
</script>
</head>

<body>
<form name="frm" method=post action="collection.jsp" >	
<iframe style="HEIGHT: 164px"  marginwidth=0 marginheight=0 src="top.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<div id=Main>
<div id=collect_Right>
<div class=collect_list>
<div class=collect_right_top>
<div class=collect_heatitle><h6>会员中心</h6></div>
</div>
<div class=collect_mid>
  <p>&nbsp;</p>
  <table width="81%" border="0" cellpadding="0" cellspacing="0">
       <tr>
      <td height="38" class="list_wz"><a href="collection.jsp">&nbsp;&gt;&gt; 我的收藏</a></td>
    </tr>
    <tr>
      <td height="38" class="list_wz"><a href="history.jsp">&nbsp;&gt;&gt; 历史记录</a></td>
    </tr>
    <tr>
      <td height="38" class="list_wz"><a href="balance.jsp" >&nbsp;&gt;&gt; 我的余额</a></td>
    </tr>
    <tr>
      <td height="38" class="list_wz"><a href="integral.jsp">&nbsp;&gt;&gt; 我的积分</a></td>
    </tr>
    <tr>
      <td height="38" class="list_wz"><a href="memberpwd.jsp">&nbsp;&gt;&gt; 修改密码</a></td>
    </tr>
     <tr>
      <td height="38" class="list_wz"><a href="membereidt.jsp">&nbsp;&gt;&gt; 信息设置</a></td>
    </tr>
    <tr>
      <td height="38" class="list_wz"><a href="#" 
      onclick="if (confirm('您确定要退出吗？')){top.location = '<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>';}	return false;">
      &nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
     <tr>
      <td height="51" class="list_wz">&nbsp; </td>
    </tr>
  </table>
  <p>&nbsp;</p>
</div>
<div class=collect_bottom></div></div>
</div>


<div id=Left>
<div class=collect_left_top>
		<div class="collect_sf">我的收藏</div>
</div>
<div class=collect_left_mid>
<div class=collect_show>
<table border="0" style="HEIGHT:40px;" cellpadding="0" cellspacing="0" width="96%">
			<tr>
			<td style="font-size:9pt">
			 &nbsp;&nbsp;&nbsp;
			</td>
			<td align="right" width="600">
			 <input type="checkbox" name="checkbox62" value="checkbox" onclick="selAll(document.all.strId)"/>
			 全选
			 <a href="#" onclick="del();"><img src="../images/delete.gif" width="16" height="16" border="0" />批量删除</a>
			</td>   
			</tr>
			</table>
  <table width="96%" border="0" style="HEIGHT: 240px"  align="center" cellpadding="0" cellspacing="1" bgcolor="DCDCDC">
    <tr> 
     <td width="5%" height="22"  class="left_bt2"><div align="center">&nbsp;</div></td>
      <td height="25" align="center" bgcolor="EEEEEE" class="collect_show_tit">商家名称</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">优惠券名称</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">金额</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">失效时间</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">收藏时间</td>
      <td align="center" bgcolor="EEEEEE" class="collect_show_tit">操作</td>
    </tr>
    <%if(vctCouponFavourites.size()>0){ %>
    <%
    for(int i=0;i<vctCouponFavourites.size();i++){
    	
    coupon=couponGloba.show("where strid='"+vctCouponFavourites.get(i).getStrCouponId()+"' order by dtcreatetime desc");
    %>
    <tr>
    <td height="20" bgcolor="#FFFFFF">
    <div align="center"><input type="checkbox" name=strId value="<%=vctCouponFavourites.get(i).getStrId() %>" /></div></td>
      <td height="25" align="center" bgcolor="#FFFFFF"><span class="STYLE1"><a href="merchantsinfo.jsp?strid=<%=coupon.getStrShopId()%>"><%=shop.returnBizShopName("where strid='"+coupon.getStrShopId()+"'") %></a></span></td>
      <td align="center" bgcolor="#FFFFFF"><a  href="couponinfo.jsp?strid=<%=coupon.getStrId() %>"><%=coupon.getStrName() %></a></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=coupon.getFlaPrice() %></span></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=coupon.getDtExpireTime() %></span></td>
      <td align="center" bgcolor="#FFFFFF"><span class="STYLE1"><%=vctCouponFavourites.get(i).getDtFavouriteTime() %></span></td>
      <td align="center" bgcolor="#FFFFFF">
      <span class="STYLE1">
      <a href="#" onclick="if(confirm('确认删除该记录？')){location.href='collection_act.jsp?strId=<%=vctCouponFavourites.get(i).getStrId()%>';}"><img src="../images/delete.gif" width="16" height="16" border="0" />删除</a></span> 
                </td>
    </tr>
    <%} }else{%>
    <tr>
      <td height="25" align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>
    <%} %>
  </table>
  <!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
   	<!-- 翻页结束 -->
</div>

</div>

<div class=collect_show_bottom></div>
</div>
</div>

<iframe style="HEIGHT: 260px" border=0 marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=no width="100%" scrolling=no></iframe>
</form>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>