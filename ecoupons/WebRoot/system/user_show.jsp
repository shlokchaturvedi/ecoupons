<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 java.util.HashMap" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	 String strUserId=ParamUtil.getString(request,"strUserId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    User obj=new User(globa,false);
    User obj0=obj.show(where);
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
<script language="javascript">

</script>
</head>

<body>
<form name="frm" method="post" action="" >
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">用户信息</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 用户管理 / 查看用户</td>
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
               </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;用户</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              
             <tr>
                <td width="20%" height="30" align="right" class="left_txt2">用 户 名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=obj0.getStrUserId()%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">姓　　名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=obj0.getStrName()%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">性　　别：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                  <input type="radio" name="strSex" value="男" <%if(obj0.getStrSex().equals("男")) out.print("checked");%> class="input_box">
                  男
                  <input type="radio" name="strSex" value="女" <%if(obj0.getStrSex().equals("女")) out.print("checked");%> class="input_box">
                  女
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">所属机构：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
            <%=SysUserUnit.getTotalName(obj0.getStrUnitId())%>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">职　　务：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=Format.forbidNull(obj0.getStrDuty())%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">岗　　位：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=Format.forbidNull(obj0.getStrStation())%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">手　　机：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=Format.forbidNull(obj0.getStrMobile())%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">电子邮箱：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><%=Format.forbidNull(obj0.getStrEmail())%></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
            </table></td>
          </tr>
          <TR>
            <TD colspan="2" align="center" height="28">
            <input type="button" name="b_back"   value="返 回" class="button_box" onClick="history.back()"  style="cursor:hand"/>
            </TD>
        </TR>
        </table>
         </td>
      </tr>
    </table></td>
    <td background="../images/mail_rightbg.gif">&nbsp;</td>
  </tr>
  
   
</table>
</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>