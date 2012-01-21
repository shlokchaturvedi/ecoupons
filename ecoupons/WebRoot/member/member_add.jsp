<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.Constants,java.util.*" %>
<%@ include file="../include/jsp/head.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=application.getAttribute("APP_TITLE")%></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
	font-size:9pt;
}
body,td,tr{font-size:9pt;}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script src="../include/js/chkFrm.js"></script>
<script language="JavaScript" src="../include/DatePicker/WdatePicker.js"></script>
<script language="javascript">

function chkFrm() {
    if(trim(frm.strCardNo.value)=="") {
        alert("请输入卡号！！！")
        frm.strCardNo.focus();
        return false;
    }
    if(trim(frm.strSalesman.value)=="") {
        alert("请输入销售员！！！")
        frm.strSalesman.focus();
        return false;
    }
	if(trim(frm.strMobileNo.value)!=""){
		if(!isMobilePhone(frm.strMobileNo.value)){  
		    alert("非法的手机号码") 
		    frm.strMobileNo.focus();   
		    return false; 
	  	}else {
	    	if(confirm("确定添加!"))
	        {
				frm.submit();
		    }
	    }
	} 
}
</script>
</head>

<body>
<form name="frm" method="post" action="member_act.jsp">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">增加会员</div></td>
      </tr>
    </table></td>
    <td width="22" valign="top" background="../images/mail_rightbg.gif"><img src="../images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td height="71" valign="middle" background="../images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9"><table width="100%" height="933" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="13" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td height="918" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：日常管理 / 会员管理 / 新增会员</td>
          </tr>
          <tr>
            <td height="20"><table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
              <tr>
                <td></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" height="55" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="6%" height="55" valign="middle"><img src="../images/title.gif" width="54" height="55"></td>
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以增加会员信息</span><br>
                      <span class="left_txt2">包括会员卡号，姓名，会员类型，余额等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;会员</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              
             <tr >
                <td width="20%" height="30" align="right" class="left_txt2">姓       名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">卡号：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strCardNo" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">会员类型：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                  <input type="radio" name="intType"  value="1"  class="input_box">
                  VIP会员
                  <input type="radio" name="intType" checked="checked" value="0"  class="input_box">
                  普通会员
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
               <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">手机：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strMobileNo" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             
               
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">销售员：</td>
                <td width="3%">&nbsp;</td>
               
            	<td width="32%" height="30"><input name="strSalesman" type="text"  class="input_box" size="30"
            	<%if("代理员".equals(globa.userSession.getStrCssType())){
            		out.print("value='"+globa.userSession.getStrName()+"' readonly ");}%>
            	
            	 /></td>
            	
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             
              
              
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
              <td width="6%" height="56" align="right">&nbsp;</td>
              <td width="44%" height="56"><input name="B12" type="reset" class="button_box" value="取消" /></td>
            </tr>
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
          </table></td>
      </tr>
    </table></td>
    <td background="../images/mail_rightbg.gif">&nbsp;</td>
  </tr>
  <tr>
    <td valign="middle" background="../images/mail_leftbg.gif"><img src="../images/buttom_left2.gif" width="17" height="17" /></td>
      <td height="17" valign="top" background="../images/buttom_bgs.gif"><img src="../images/buttom_bgs.gif" width="17" height="17" /></td>
    <td background="../images/mail_rightbg.gif"><img src="../images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>
</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>