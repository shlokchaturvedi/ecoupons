<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.Constants,
com.ejoysoft.common.exception.NoRightException"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("90005"))
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
.ipt{ background:url(../images/folder.gif) no-repeat;}
a{ font-size:12px; color:#333333; text-decoration:none;}
a:hover{font-size:12px; color:#993300; text-decoration:none;}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script src="../include/js/list.js"></script>
<script language="javascript">
//增加信息
function add() {
   frm.action="unit_add.jsp";
   frm.submit();
}
//修改信息
function update() {
if (iCheckedNumber(document.all.strUnitId) == 0) {
		alert("请先选择要修改的部门！");
		return;
	}
   frm.action="unit_update.jsp";
   frm.submit();
}
//删除信息
function del(){
    if(!confirm('您是否确认要删除所选中的组织机构？'))
        return
     frm.action="unit_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
function expand(obj, gid) {
    var oGid = eval("document.all." + gid);
    if (obj.value == "+") {
        obj.value = "-";
        if (oGid.length == undefined)
            oGid.style.display = "";
        else
            for (var i = 0; i < oGid.length; i++)
                oGid[i].style.display = "";
    } else {
        obj.value = "+";
        if (oGid.length == undefined)
            oGid.style.display = "none";
        else
            for (var i = 0; i < oGid.length; i++)
                oGid[i].style.display = "none";
    }
}
//单位用户信息管理
function viewUser() {
   frm.action="user_list.jsp";
   frm.submit();
}
</script>
</head>


<body >
<form name="frm" method="post" action="">
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
               <td width="40%" align="right" >
	              <input type="button" class="button"   value="增加"  style="cursor:hand" onClick="add()" >
		          <input type="button" class="button"   value="修改"  style="cursor:hand" onClick="update()" >
		          <input type="button" class="button"   value="删除"  style="cursor:hand" onClick="del()" >
		          <input type="button" class="button"   value="用户管理" style="cursor:hand" onClick="viewUser()">
               </td>
               </tr>
               <tr><td height="2" colspan="2"></td></tr>
              <tr>
                <td height="210" colspan="2" class="font42"  valign="top">
                <table width="100%" border="0" cellpadding="2" cellspacing="1" class="CContent" >
                  <tr class="CTitle" height="20">
                    <td  align="center"><div align="left"><strong> 组织机构管理</strong></div></td>
                  </tr>
                  <tr  height="20">
                  <td   height="300" valign="top" style="border:1px solid #B9BEC1">
               <!-- start -->
               <table width="100%" border="1" style="border-collapse:collapse " bordercolor="#FFFFFF" align="center" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="109" valign="top" >
                  <%
                  
                  	String unitId = globa.userSession.getStrUnitId()[0];
                  	
                    Vector userGroupTree = SysUserUnit.getUserGroupTree();  //树形目录的所有用户组向量
                    int curLevel = 1;
                    for (int i = 0; i < userGroupTree.size(); i++) {
                        Unit ug = (Unit)userGroupTree.get(i);
                        if (ug.getIntLevel() < curLevel) {   //上级用户�?
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
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="30" align="right">
        <%
                if (ug.haveChild()) {   //有下级用户组
        %>
        <input type="button" name="b_<%=ug.getStrId()%>" value=" "  onClick="expand(this,'g<%=ug.getStrId()%>');" class="ipt">
        <%
                } else {
        %>
                    &nbsp;
        <%
                }
        %>                  </td>
                </tr>
              </table>
            </td>
            <td width="96%" height="22" align="left">
              <input name='strUnitId' type='radio' value='<%=ug.getStrId()%>'<%=SysUserUnit.isManaged(unitId, ug.getStrId()) && !unitId.equals(ug.getStrId()) || globa.userSession.getIntUserType() == 0 ? "" : " disabled"%>>
              <a href='unit_show.jsp?strId=<%=ug.getStrId()%>'><%=ug.getStrUnitName()%>/<%=ug.getStrUnitCode()%></a>
        <%
                if (!ug.haveChild()) {  //没有下级用户组，直接收口
        %>
            </td>
          </tr>
        </table>
        <%
                }
                curLevel = ug.getIntLevel();
            }
            //最后收�?
            for (int j = 1; j < curLevel; j++) {
        %>
            </td>
          </tr>
        </table>
        <%
            }
        %>

                  
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
