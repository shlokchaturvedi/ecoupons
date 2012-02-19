<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
        //返回页面的url
    String strUrl="unit_list.jsp";
    String strId=ParamUtil.getString(request,"strUnitId","");
       if(strId.equals(""))
       throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
       String where="where strId='"+strId+"'";
        Unit unit=new Unit(globa,false);
       Unit  unit0=unit.show(where);
    if(unit0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
    }
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
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
<!--
    function chkFrm(){
        if(frm.strParentId.value=="0")
        {
            alert("\请选择上级机构！！！")
            frm.strParentId.focus();
            return false;
        }
        else if(frm.strUnitName.value=="")
        {
            alert("\请输入单位名称！！！")
            frm.strUnitName.focus();
            return false;
        }
        else if(frm.strUnitEmail.value!=""&&!isEmail(frm.strUnitEmail.value))
        {
            alert("单位EMAIL格式不正确");
            frm.strUnitEmail.focus();
            return false;
        }
       else  frm.submit();
    }
-->
</script>
</head>

<body class="ContentBody">
<form name="frm" method="post" action="unit_act.jsp">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
  <input type="hidden" name=strId  value="<%=strId%>">
  <input type="hidden" name=strUrl  value="<%=strUrl%>">
  <input type="hidden" name="strOldParentId" value="<%=unit0.getStrParentId()%>">
  <input type="hidden" name="strUnitCode" value="<%=unit0.getStrUnitCode()%>">
  <input type="hidden" name="intLevel" value="<%=unit0.getIntLevel()%>">
    <input type="hidden" name="intOldSort" value="<%=unit0.getIntSort()%>">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">修改机构</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 机构管理 / 修改机构</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以修改机构</span><br>
                      <span class="left_txt2">包括机构名称，地址，联系电话等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;机构</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">上级机构：</td>
               
                <td width="32%" height="30"> &nbsp;&nbsp;<select name="strParentId" class="input_box">
                     <option value="0">请选择上级机构</option>
                    <%
                    if (globa.userSession.getIntUserType() == 0) {
                    %>
                    <option value="">=无上级机构=</option>
                    <%
                    }
                    String strStyle="style='background-color:red;'";
                    Vector userGroupTree = SysUserUnit.getUserGroupTree();
                    for (int i = 0; i < userGroupTree.size(); i++) {
                        Unit ugNode = (Unit)userGroupTree.get(i);
                        if (SysUserUnit.isManaged(globa.userSession.getStrUnitId()[0], ugNode.getStrId())) {
	                        strStyle=(ugNode.getStrId().equals(strId)?" style='background-color:red;'":"");
	                        out.print("<option value=" + ugNode.getStrId() + " "+strStyle+">");
	                        for (int j = 1; j < ugNode.getIntLevel(); j++) {
	                            out.print("&nbsp;");
	                        }
                        	out.println("├" + ugNode.getStrUnitName() + "</option>");
                        }
                    }
                %>
                  </select>
                  <script language="javascript">
                  for (var i = 0; i < frm.strParentId.options.length; i++) {
                    if (frm.strParentId.options[i].value == "<%=unit0.getStrParentId()%>") {
                        frm.strParentId.options[i].selected = true;
                        break;
                    }
                  }
              </script>
              </td>
                
              </tr>
              <tr bgcolor="#f2f2f2">
                 
               <td width="11%" align="right"   height="25" nowrap>机构名称：</td>
                <td> &nbsp;
                  <input type="text" name="strUnitName" size="30" class="input_box" value="<%=unit0.getStrUnitName()%>">
                </td>
                <td width="11%" align="right"   height="25">机构简称：</td>
                <td>&nbsp;
                   <input type="text" name="strEasyName" size="30" class="input_box" value="<%=unit0.getStrEasyName()%>">
                   </td>
              
              
              
              </tr>
             <tr >
                <td width="11%" align="right"   height="25">E_mail：</td>
                <td width="35%" > &nbsp;
                  <input type="text" name="strUnitEmail" size="30" class="input_box" value="<%=unit0.getStrUnitEmail()%>">
                  &nbsp; </td>
                <td width="11%" align="right"   height="25">机构网址：</td>
                <td > &nbsp;
                   <input type="text" name="strUnitNet" size="30" class="input_box" value="<%=unit0.getStrUnitNet()%>">
                  &nbsp; </td>
                
              </tr>
              <tr bgcolor="#f2f2f2">
               <td width="11%" align="right"  >区　　号：</td>
                <td width="35%" > &nbsp;
                  <input type="text" name="strAreaCode" size="30" class="input_box" value="<%=unit0.getStrAreaCode()%>">
                </td>
                <td width="11%" align="right"  >联系电话：</td>
                <td > &nbsp;
                  <input type="text" name="strUnitPhone" size="30" class="input_box" value="<%=unit0.getStrUnitPhone()%>">
                </td>
              </tr>
            
              <tr >
                  <td width="11%" align="right"  >邮政编码：</td>
                <td width="35%" > &nbsp;
                  <input type="text" name="strPostalCode" size="30" class="input_box" value="<%=unit0.getStrUnitPhone()%>">
                </td>
                <td width="11%" align="right" >移动电话：</td>
                <td  > &nbsp;
                  <input type="text" name="strUnitFax" size="30" class="input_box" value="<%=unit0.getStrUnitFax()%>">
                </td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="11%" align="right"   height="25">联系地址：</td>
                <td  colspan="3" > &nbsp;
                  <input type="text" name="strUnitAddress" size="42" class="input_box" value="<%=unit0.getStrUnitAddress()%>">
                </td>
              </tr>
               <tr >
                 <td width="11%" align="right"  >备　　注：</td>
                <td colspan="3" > &nbsp;
                  <textarea name="strRemark" cols="54"  rows="4" class="input_box"><%=unit0.getStrRemark()%></textarea>
                  &nbsp; </td>
              </tr>
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
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