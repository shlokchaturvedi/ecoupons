<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>

<%@ include file="../include/jsp/head.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=globa.APP_TITLE%></title>
<link rel="stylesheet" rev="stylesheet" href="../css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="../css/css1.css">
<link rel="stylesheet" href="../css/css.css">
<script language="JavaScript" src="../include/date/popc.js"></script>
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
<!--
    function chkFrm(){
        if(trim(frm.strOldPwd.value)=="")
        {
            alert("\请输入原密码！！！")
            frm.strOldPwd.focus();
            return false;
        }
        else if(trim(frm.strNewPwd.value)=="")
        {
            alert("请输入新密码！");
            frm.strNewPwd.focus();
            return false;
        }
       else  if(frm.strNewPwd.value != frm.strNewPwd1.value)
        {
            alert("\两次输入的密码不相同！！！")
            frm.strNewPwd1.focus();
            return false;
        }
        frm.submit();
    }
-->
</script>
</head>

<body class="ContentBody">
<form name="frm" method="post" action="changePwdAct.jsp">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><div align="center" class="style1">
        <div align="left">系统维护</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" class="CPanel">
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="0" align="left"></td></tr>
        <TR>
            <TD width="100%">
                <fieldset>
                <legend>修改密码</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
                <tr class="td_listbg_1">
                <td width="11%" align="right"  height="25">原密码：</td>
                 <td class="time1">&nbsp;
                   <input type="password" name="strOldPwd" size="30" class="forms_color1" value="">
                </td>
              </tr>
              <tr class="td_listbg_2">
                <td width="11%" align="right"  height="25" nowrap>新密码：</td>
                <td class="time1"> &nbsp;
                  <input type="password" name="strNewPwd" size="30" class="forms_color1">
                </td>
              </tr>
              <tr class="td_listbg_1">
                <td width="11%" align="right"  height="25">重输密码：</td>
                 <td class="time1">&nbsp;
                   <input type="password" name="strNewPwd1" size="30" class="forms_color1" value="">
                </td>
              </tr>
             </table>
                      <br/>
              </fieldset>
            </TD>
        </TR>
        </TABLE>
        </td>
  </tr>
        <TR>
            <TD colspan="2" align="center" height="28">
            <input type="button" name="b_submit" value="确 定" class="button" onclick="chkFrm()" style="cursor:hand"/>
            <input type="button" name="b_reset"  value="重 置" class="button" onclick="frm.reset();" style="cursor:hand"/>
            </TD>
        </TR>
    </TABLE>
    
</div>
</form>
</body>
</html>