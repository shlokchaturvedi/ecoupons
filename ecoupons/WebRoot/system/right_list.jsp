<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,
                 java.util.HashMap,com.ejoysoft.common.Constants,com.ejoysoft.common.exception.NoRightException"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("90015"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=globa.APP_TITLE%></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
}
body,td,th {
	font-size: 9pt;
	color: #111111;
}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script src="../include/js/list.js"></script>
<script language="javascript">
function expand(obj, gid) {
    var oGid = eval("document.all." + gid);
    if (obj.value == "+") {
        obj.value = "-";
        if(oGid!=undefined)
        if (oGid.length == undefined)
            oGid.style.display = "";
        else
            for (var i = 0; i < oGid.length; i++)
                oGid[i].style.display = "";
    } else {
        obj.value = "+";
        if(oGid!=undefined)
        if (oGid.length == undefined)
            oGid.style.display = "none";
        else
            for (var i = 0; i < oGid.length; i++)
                oGid[i].style.display = "none";
    }
}
function rightSet() {
    for (var i = 0; i < frm.sel.length; i++)
        if (frm.sel[i].checked)
            window.open("right_set.jsp?sel=" + frm.sel[i].value, "权限分配", "width=370,height=650,top=20,left=50,scrollbars=yes,status=yes");
}
</script>
</head>


<body >
<form name=frm method=post action="right_list.jsp">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td>
    <table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="283"  valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
             <tr>
               <td height="20"></td>
               <td width="30%" align="right" >
                  <input type="button" class="button_box"    value="分配权限" onClick="rightSet()" style="cursor:hand" >
               </td>
               </tr>
               <tr><td height="2" colspan="2"></td></tr>
              <tr>
                <td height="210" colspan="2"  bgcolor="#F7F8F9" valign="top">
                <table width="100%" border="0" cellpadding="2" cellspacing="1" class="CContent" >
                  <tr class="CTitle" height="20">
                    <td  align="center"><div align="left"><strong> 权限分配</strong></div></td>
                  </tr>
                  <tr  height="20">
                  <td   height="300" valign="top">
               <!-- start -->
               <table width="100%" border="0" cellspacing="0" cellpadding="0" class="td_color_1">
              <tr>
                <td width="20%" height="109" valign="top">
                  <%
                      User user = new User(globa);
                    //获得所有用户并分组
                    HashMap allUsers = user.getUnitAllUser(" ORDER BY strUnitId  ASC");
                    Vector userGroupTree = SysUserUnit.getUserGroupTree();  //树形目录的所有用户组向量
                    int curLevel = 1;
                    for (int i = 0; i < userGroupTree.size(); i++) {
                        Unit ug = (Unit)userGroupTree.get(i);
                        if (ug.getIntLevel() < curLevel) {   //上级用户组
                             for (int j = ug.getIntLevel(); j < curLevel; j++) {  //输出收口标签
                %>
                    </td>
                  </tr>
                </table>
        <%
                     }
                }
        %>
        <table width="100%" border="0" align="center" cellspacing="1" cellpadding="4" id="g<%=ug.getStrParentId()%>" style="display:<%=ug.getStrParentId().equals("") ? "":"none"%>" class="td_color_1">
          <tr >
            <td width="4%" height="22" valign="top" bgcolor="#F6F6F6">
              <table width="12" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="30">
<%--        <%if (ug.haveChild()) {   //有下级用户组%>--%>
                    <input type="button" name="b_<%=ug.getStrId()%>" value="+"  onclick="expand(this,'g<%=ug.getStrId()%>');">
<%--        <%} else {%>&nbsp;<% }%>--%>
                  </td>
                </tr>
              </table>
            </td>
            <td width="96%" height="22" align="left">
              <input  name='sel'  type='radio' value='g/<%=ug.getStrId()%>' <%=i == 0 ? " checked" : ""%>>
              <%=ug.getStrUnitName()%>
              <%
                if (allUsers.containsKey(ug.getStrId())) { //有用户，显示用户
                    for (int m = 0; m < ((Vector)allUsers.get(ug.getStrId())).size(); m++) {
                        User theUser = (User)((Vector)allUsers.get(ug.getStrId())).get(m);
        %>
        <table width="100%" border="0" align="center" cellspacing="1" cellpadding="4" id="g<%=ug.getStrId()%>" style="display:none">
          <tr>
            <td  height="22" valign="top" bgcolor="#F6F6F6">
              <table  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="40" align="right"><font color="#FF0000">*
                  <input  name='sel'  type='radio' value='u/<%=theUser.getStrId()%>'>
                  </td>
                </tr>
              </table>
            </td>
            <td width="92%" height="22">
              <%=theUser.getStrUserId()%><font color="#FF0000">／</font><%=theUser.getStrName()%>
            </td>
          </tr>
        </table>
        <%    }
                }
                if (!ug.haveChild()) {  //没有下级用户组，直接收口
        %>
            </td>
          </tr>
        </table>
        <% }
                curLevel = ug.getIntLevel();
            }
            //最后收口
            for (int j = 1; j < curLevel; j++) {  %>
            </td>
          </tr>
        </table>
        <% }%>
               <!-- end -->    
                  </td>
                  </tr>
                </table>                
            </td>
        </tr>
      </table>
      </td>
  </tr>
</table>

</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>
