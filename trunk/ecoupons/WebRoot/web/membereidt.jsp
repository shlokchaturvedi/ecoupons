<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.web.RecordModel"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.ejoysoft.ecoupons.business.PointPresent"%>
<%@page import="com.ejoysoft.ecoupons.business.Point"%>
<%@page import="com.ejoysoft.ecoupons.business.PointCardInput"%>
<%@page import="com.ejoysoft.ecoupons.business.GiftExchange"%>
<%@page import="com.ejoysoft.common.Globa"%>
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
String strMemberCardNo=globa.getMember().getStrCardNo();
Member member=new Member(globa);
Member objMember = member.show(" where strcardno='"+strMemberCardNo+"'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/collection.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="css/comment.css" />
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
function chkFrm()
{
    if(trim(frm.strName.value)==""){        
        alert("请输入您的真实姓名！！！");
        frm.strName.focus();
        return false;
    }else if(trim(frm.strPhone.value)=="") {
        alert("请输入您的手机号码！！！");
        frm.strPhone.focus();
        return false;
    }else {  
       if(!isMobilePhone(frm.strPhone.value)){  
		    alert("请输入正确的手机号！！！") 
		    frm.strPhone.focus();   
		    return false; 
	  	  }
         var name = "<%=objMember.getStrName()%>" ;
         var phone = "<%=objMember.getStrMobileNo()%>" ;
    	 if(trim(frm.strName.value)== name && trim(frm.strPhone.value)== phone)
    	 {
		       alert("您的信息无变化，请重新输入！！！");
		       frm.strName.focus();
		       return false;
    	 }        
    	 if(confirm("确定提交设置吗？"))
    	 {
    		  frm.submit();
    	 }
    }
}


</script>
<title>个人信息设置</title>
</head>

<body>
<form action="memberedit_act.jsp" name="frm" method="post">
<iframe style="HEIGHT: 164px" marginwidth=0 marginheight=0 src="top.jsp" 
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
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
<div class="collect_sf">个人信息设置</div></div>
<div class=collect_left_mid>
<div class=collect_show>
<%

 %>
  <table width="70%" style="HEIGHT:280px;" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="55">&nbsp;</td><td></td>
    </tr>
    <tr>
      <td width="55%" class="memberpwd_td">真实姓名：&nbsp;&nbsp;</td>
      <td width="45%" class="memberpwd_td"><input name="strName" id="strName" value="<%=objMember.getStrName() %>" type="text"  class="memberpwd_ipt"/>&nbsp;&nbsp;</td>
     </tr>
     <tr>
      <td width="55%" class="memberpwd_td">手 机 号：&nbsp;&nbsp;</td>
      <td width="45%" class="memberpwd_td"><input name="strPhone" id="strPhone" value="<%=objMember.getStrMobileNo() %>" type="text"  class="memberpwd_ipt"/>&nbsp;&nbsp;</td>
     </tr><tr>
      <td height="45">&nbsp;</td><td></td>
    </tr>
     <tr>
      <td width="45%" align="center" colspan="2" class="memberpwd_td"><img src="images/sure.jpg"  onclick="chkFrm();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <img src="images/quxiao.jpg" onclick="frm.reset();" /></td>
     </tr>
     <tr>
      <td height="90">&nbsp;</td>
     </tr>
  </table>  
</div>

</div>
<div class=collect_show_bottom></div></div>
</div>

<iframe style="HEIGHT: 260px"  marginwidth=0 marginheight=0 src="bottom.jsp" 
frameborder=0 width="100%" scrolling=no></iframe>
</form>
</body>
</html>
<%@ include file="/include/jsp/footer.jsp"%>