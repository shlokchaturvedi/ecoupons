<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Shop,
				 java.util.Vector,com.ejoysoft.common.Format,
				 com.ejoysoft.common.Constants" %>
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
<script language="JavaScript" src="../include/DatePicker/WdatePicker.js"></script>
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
    function chkFrm() {
        	frm.submit();
    }
</script>
</head>

<body>
<form name="frm" method="post" action="gift_act.jsp" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">增加礼品</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 会员管理 / 新增礼品</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以增加礼品信息</span><br>
                      <span class="left_txt2">包括礼品名称，礼品说明，礼品图片，生效时间，截止时间，兑换积分等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;礼品增加</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                <td width="20%" height="30" align="right" class="left_txt2">名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">兑换积分：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <input name="intPoint" type="text" class="input_box" size="30" value="0"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">生效时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="dtActiveTime" readonly="readonly" value="<%=Format.getDateTime() %>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             
               
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">截止时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="dtExpireTime" readonly="readonly" value="<%=Format.getDateTime() %>" type="text" class="input_box" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr>
                <td height="30" align="right" class="left_txt2">小图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strSmallImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("GIFT_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("GIFT_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                <td height="30" align="right" class="left_txt2">大图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strLargeImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("GIFT_LARGE_IMG_WIDTH") %>*<%=application.getAttribute("GIFT_LARGE_IMG_HEIGHT") %>px，用于前台详细显示）</td>
              </tr>
             
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">礼品简介：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><textArea name="strIntro" cols="33" rows="5" ></textArea></td>
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
              <td width="6%" height="56" align="right">&nbsp;</td>
              <td width="64%" height="56"><input name="B12" type="reset" class="button_box" value="取消" /></td>
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