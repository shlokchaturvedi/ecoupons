<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
        //返回页面的url
    String strUrl="unit_list.jsp";
    String strId=ParamUtil.getString(request,"strId","");
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


</head>

<body class="ContentBody">
<form name="frm" method="post" action="">


<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">查看机构</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 机构管理 / 查看机构</td>
          </tr>
          <tr>
            <td height="20"><table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
              <tr>
                <td></td>
              </tr>
            </table></td>
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
               
                <td width="32%" height="30"> &nbsp;&nbsp;
                <%=SysUserUnit.getTotalName(unit0.getStrParentId()) %>
              </td>
                
              </tr>
              <tr bgcolor="#f2f2f2">
                 
               <td width="11%" align="right"   height="30" nowrap>机构名称：</td>
                <td> &nbsp;
                  <%=unit0.getStrUnitName()%>
                </td>
                <td width="11%" align="right"   height="30">机构简称：</td>
                <td>&nbsp;
                 <%=unit0.getStrEasyName()%>
                   </td>
              
              
              
              </tr>
             <tr >
                <td width="11%" align="right"  height="30">E_mail：</td>
                <td width="35%" > &nbsp;
                  <%=unit0.getStrUnitEmail()%>
                  &nbsp; </td>
                <td width="11%" align="right"   height="30">机构网址：</td>
                <td > &nbsp;
                  <%=unit0.getStrUnitNet()%>
                  &nbsp; </td>
                
              </tr>
              <tr bgcolor="#f2f2f2">
               <td width="11%" align="right"  height="30">区　　号：</td>
                <td width="35%" > &nbsp;
                  <%=unit0.getStrAreaCode()%>
                </td>
                <td width="11%" align="right" height="30" >联系电话：</td>
                <td > &nbsp;
                  <%=unit0.getStrUnitPhone()%>
                </td>
              </tr>
            
              <tr >
                  <td width="11%" align="right"  height="30">邮政编码：</td>
                <td width="35%" > &nbsp;
                  <%=unit0.getStrUnitPhone()%>
                </td>
                <td width="11%" align="right" height="30">移动电话：</td>
                <td  > &nbsp;
                  <%=unit0.getStrUnitFax()%>
                </td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="11%" align="right"   height="30">联系地址：</td>
                <td  colspan="3" > &nbsp;
                  <%=unit0.getStrUnitAddress()%>
                </td>
              </tr>
               <tr >
                 <td width="11%" align="right"  height="30">备　　注：</td>
                <td colspan="3" > &nbsp;
                 <%=unit0.getStrRemark()%>
                  &nbsp; </td>
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