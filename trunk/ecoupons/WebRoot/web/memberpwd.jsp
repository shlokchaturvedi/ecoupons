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

RecordModel reModel=new RecordModel(globa);
//记录总数
String sql1="select count(strId) from ( select strId,intpoint,dtcreatetime from t_bz_point_present where strMemberCardNo='"
+strMemberCardNo+"' union all select strId,intpoint,dtcreatetime from t_bz_pointcard_input where strMemberCardNo='"+strMemberCardNo+"' union all "
+"select a1.strId,a2.intpoint,a1.dtcreatetime from t_bz_gift_exchange a1,t_bz_gift a2 where strmembercardno='"+strMemberCardNo+"' and a2.strid=a1.strgiftid) b order by b.dtcreatetime desc ";
int intAllCount=reModel.getCount(sql1);
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
String sql="select strId,intpoint,dtcreatetime from ( select strId,intpoint,dtcreatetime from t_bz_point_present where strMemberCardNo='"
+strMemberCardNo+"' union all select strId,intpoint,dtcreatetime from t_bz_pointcard_input where strMemberCardNo='"+strMemberCardNo+"' union all "
+"select a1.strId,a2.intpoint,a1.dtcreatetime from t_bz_gift_exchange a1,t_bz_gift a2 where strmembercardno='"+strMemberCardNo+"' and a2.strid=a1.strgiftid) b order by b.dtcreatetime desc ";
Vector<RecordModel> vctRecords=reModel.listIntegral(sql,intStartNum,intPageSize);
//获取当前页的记录条数
int intVct=(vctRecords!=null&&vctRecords.size()>0?vctRecords.size():0);
Member member=new Member(globa);

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
    if(trim(frm.strPwd.value)==""){        
        alert("请输入您的旧密码！！！");
        frm.strPwd.focus();
        return false;
    }else if(trim(frm.newPwd.value)=="") {
        alert("请输入您的新密码！！！");
        frm.newPwd.focus();
        return false;
    }else if(trim(frm.newPwd2.value)=="") {
        alert("请重复您的新密码！！！");
        frm.newPwd2.focus();
        return false;
    }else {           
    	 if(trim(frm.newPwd.value)!= trim(frm.newPwd2.value)){
	        alert("您两次输入的新密码不一致！请重新输入！！！");
	        frm.newPwd.focus();
	        return false;
	     }
	     if(confirm("确定更改密码？"))
    	  {
    		  frm.submit();
    	  }
    }
}


</script>
<title>我的积分</title>
</head>

<body>
<form action="memberpwd_act.jsp" name="frm" method="post">
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
      <td height="32" class="list_wz"><a href="#" onclick="if (confirm('您确定要退出吗？')){top.location = '<%=application.getServletContextName()%>/web/Auth?actiontype=<%=Constants.WEBLOGOFF%>';}	return false;">&nbsp;&gt;&gt; 退出系统</a></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</div>
<div class=collect_bottom></div></div>
</div>
<div id=Left>
<div class=collect_left_top>
<div class="collect_sf">修改个人密码</div></div>
<div class=collect_left_mid>
<div class=collect_show>
  <table width="70%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="20"></td><td></td>
    </tr>
    <tr>
      <td width="55%" class="memberpwd_td">旧 密 码：&nbsp;&nbsp;</td>
      <td width="45%" class="memberpwd_td"><input name="strPwd" id="strPhone" type="password"  class="memberpwd_ipt"/>&nbsp;&nbsp;</td>
     </tr>
     <tr>
      <td width="55%" class="memberpwd_td">新 密 码：&nbsp;&nbsp;</td>
      <td width="45%" class="memberpwd_td"><input name="newPwd" id="strPhone" type="password"  class="memberpwd_ipt"/>&nbsp;&nbsp;</td>
     </tr>
      <tr>
      <td width="55%" class="memberpwd_td">重复密码：&nbsp;&nbsp;</td>
      <td width="45%" class="memberpwd_td"><input name="newPwd2" id="strPhone" type="password"  class="memberpwd_ipt"/>&nbsp;&nbsp;</td>
     </tr>
     <tr>
      <td width="55%" class="memberpwd_td">&nbsp;</td>
      <td width="45%" class="memberpwd_td"><img src="images/sure.jpg" onclick="chkFrm();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <img src="images/quxiao.jpg" onclick="frm.reset();" /></td>
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