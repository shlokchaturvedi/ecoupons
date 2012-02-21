<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Activity,
				 java.util.Vector,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.ecoupons.system.SysPara,
				 java.util.ArrayList,
				  com.ejoysoft.common.exception.IdObjectException,
				  com.ejoysoft.common.exception.NoRightException" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("15005"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Activity obj=new Activity(globa,false);
    Activity obj0=obj.show(where);
    if(obj0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
    }
%>
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
<script src="../include/DatePicker/WdatePicker.js"></script>
<script language="javascript">
   function chkFrm() {
        if(trim(frm.strName.value)=="") {
            alert("请输入活动名！！！")
            frm.strName.focus();
            return false;
        } else if(trim(frm.dtActiveTime.value)=="") {
            alert("请选择活动时间！！！")
            frm.dtActiveTime.focus();
            return false;
        } else if(trim(frm.strContent.value)=="") {
            alert("请输入活动内容！！！")
            frm.strContent.focus();
            return false;
        }else {
        	frm.submit();
        }
    }
</script>
</head>

<body>
<form name="frm" method="post" action="activity_act.jsp?" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name="strId" value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">更新活动</div></td>
      </tr>
    </table>
    </td>
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
            <td class="left_txt">当前位置：业务管理 / 活动管理 / 更新活动</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以更新活动</span><br>
                      <span class="left_txt2">包括活动名，活动内容，活动时间属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;活动</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">活  动  名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" value="<%=obj0.getStrName()%>" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">活动名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input readonly name="dtActiveTime" type="text" class="input_box" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="<%=obj0.getDtActiveTime() %>" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">活动内容：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><textArea name="strContent" cols="33" rows="5" ><%=obj0.getStrContent()%></textArea></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr>              
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">创  建  人：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getStrCreator()%></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr> 
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">创建时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getDtCreateTime()%></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr>
            </table></td>
          </tr>
        </table>
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>  
               <td width="6%" height="26" align="right">&nbsp;</td>
            </tr>
          </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>  
              <td width="30%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
                 <td width="1%" height="56" align="right">&nbsp;</td>
              <td width="10%" height="56"><input name="B12" type="reset" class="button_box" value="取消" /></td>
              <td width="1%" height="56" align="right">&nbsp;</td>
               <td width="58%" height="56"><input name="B12" type="button" onclick="window.history.back();" class="button_box" value="返回" /></td>
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