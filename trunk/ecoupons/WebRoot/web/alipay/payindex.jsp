<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge,com.ejoysoft.common.*"%>
<%@page import="com.ejoysoft.ecoupons.web.RecordModel"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.util.ParamUtil"%>
<jsp:useBean id="globa" scope="page" class="com.ejoysoft.common.Globa" />
<%
    globa.initialize(application,request,response); 
    String action=ParamUtil.getAction(request);
%>
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
<link href="../css/collection.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="css/comment.css" />
<title>在线充值</title>
<script language=JavaScript>
function CheckForm()
{
	function getStrLength(value){
        return value.replace(/[^\x00-\xFF]/g,'**').length;
    }
    function trim(str)
	{
	    rstr="";
	    for(i=0;i<str.length;i++)
	    {
	        if(str.charAt(i)!="　"&&str.charAt(i)!=" ")
	        {
	            rstr=str.substring(i);
	            break;
	        }
	    }
	    for(i=rstr.length-1;i>0;i--)
	    {
	        if(rstr.charAt(i)!="　"&&rstr.charAt(i)!=" ")
	        {
	            rstr=rstr.substring(0,i+1);
	            break;
	        }
	    }
	    return rstr;
	}
	var reg	= new RegExp(/^\d*\.?\d{0,2}$/);
	if (getStrLength(frm.subject.value)<= 0) {
		alert("请输入商品名称！");
		frm.subject.focus();
		return false;
	}
	else if (trim(frm.total_fee.value) == "") {
		alert("请输入付款金额！");
		frm.total_fee.focus();
		return false;
	}
	else if (! reg.test(trim(frm.total_fee.value)))
	{
        alert("请正确输入付款金额！");
		frm.total_fee.focus();
		return false;
	}
	else if (Number(frm.total_fee.value) < 0.01) {
		alert("付款金额金额最小是0.01！");
		frm.total_fee.focus();
		return false;
	}
    else if(getStrLength(frm.subject.value) > 256){
        alert("标题过长！请在128个汉字以内！");
        frm.subject.focus();
        return false;
    }
    else if(getStrLength(frm.alibody.value) > 200){
        alert("备注过长！请在100个汉字以内!");
        frm.alibody.focus();
        return false;
    }
    else
    {
    	frm.alibody.value = frm.alibody.value.replace(/\n/g,'');
    	frm.submit();
    }
}  

</script>
</head> 
<body>
<form name=frm  action=alipayto.jsp method=post target="_blank">
<iframe style="HEIGHT: 164px"  marginwidth=0 marginheight=0 src="../top.jsp" 
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
      <td height="32" class="list_wz"><a href="../collection.jsp">&nbsp;&gt;&gt; 我的收藏</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="../history.jsp">&nbsp;&gt;&gt; 历史记录</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="../balance.jsp" >&nbsp;&gt;&gt; 我的余额</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="../integral.jsp">&nbsp;&gt;&gt; 我的积分</a></td>
    </tr>
    <tr>
      <td height="32" class="list_wz"><a href="#" onclick="if (confirm('您确定要退出吗？')){top.location = '<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>';}	return false;" >&nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</div>
<div class=collect_bottom></div></div>
</div>
<div id=Left>
<div class=collect_left_top>
<div class="collect_sf">在线充值</div>
</div>
<div class=collect_left_mid>
<div class=collect_show>
<div id="main1">
<div id="head1">
<div id="logo1"></div>
<div class="alipay_link">
	<a target="_blank" href="http://www.alipay.com/" ><span>支付宝首页</span></a>
	|
	<a target="_blank" href="https://b.alipay.com/home.htm" ><span>商家服务</span></a>
	|
	<a target="_blank" href="http://help.alipay.com/support/index_sh.htm" ><span>帮助中心</span></a>
</div>
<span class="title">支付宝即时到帐付款快速通道</span> <!--<div id="title" class="title">支付宝即时到帐付款快速通道</div>-->
</div>
<div class="cashier-nav">
<ol>
	<li class="current">1、确认付款信息 →</li>
	<li>2、付款 →</li>
	<li class="last">3、付款完成</li>
</ol>
</div>
<div id="body" style="clear: left">
<dl class="content">
	<dt>标题：</dt>
	<dd><span class="red-star">*</span> <input value="乐购在线充值" size=30 type="text" name=subject readonly />
	<span> </span></dd>
	<dt>付款金额：</dt>
	<dd><span class="red-star">*</span> <input maxlength=10 size=30	name=total_fee onfocus="if(Number(this.value)==0){this.value='';}" value="00.00" /> <span>如：112.21</span></dd>
	<dt>备注：</dt>
	<dd><span class="null-star">*</span> 
	<textarea style="margin-left: 3px;" name=alibody rows=2 cols=40 ></textarea><br />
	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（如联系方法，商品要求、数量等。100汉字内）</span></dd>
	<dt></dt>
	<dd><span class="new-btn-login-sp">
	<button class="new-btn-login" type="button" style="text-align: center;" onclick="CheckForm();">确认付款</button>
	</span></dd>
</dl>
</div>
<div id="foot">
<ul class="foot-ul">
	<li><font class=note-help>如果您点击“确认付款”按钮，即表示您同意向卖家购买此物品。 <br />
	您有责任查阅完整的物品登录资料，包括卖家的说明和接受的付款方式。卖家必须承担物品信息正确登录的责任！ </font></li>
	<li>支付宝版权所有 2011-2015 ALIPAY.COM</li>
</ul>
</div>
</div>
</div>

</div>
<div class=collect_show_bottom></div></div>
</div>

<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="../bottom.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
</form>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>