<%@ page import="java.util.HashMap,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.ecoupons.system.User,com.ejoysoft.conf.SysModule,java.util.Vector,com.ejoysoft.conf.Module,com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>

<%
//if(!globa.userSession.hasRight("90015")&&!globa.userSession.hasRight("21010"))
      // throw new NoRightException("�û����߱������ù���ģ���Ȩ�ޣ�����ϵͳ����Ա��ϵ��");
%>
<%
    String id = request.getParameter("sel");
    //�����û����û��������Ȩ��
    HashMap rights = new HashMap();
    int intType=0;
    String strObject=id.substring(id.indexOf("/") + 1);
    if (id.startsWith("g/")) {
        intType=1;
    } 
    RoleRight right=new RoleRight(globa);
      rights = right.getAllRights(intType,strObject);
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
<script language="JavaScript" src="../include/date/popc.js"></script>
<script src="../include/js/chkFrm.js"></script>
</head>

<body >
<form name="frm" method="post" action="right_setact.jsp">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
  <input type="hidden" name="strId" value="<%=id%>">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th ><div class="titlebt">
        <div align="left">Ȩ�޷���</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" >
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="0" align="left"></td></tr>
        <TR>
            <TD width="100%" height="222">
                <fieldset>
                <legend>Ȩ���б�</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
                <TR>
                <TD >
                <%
            //�������Ŀ¼������ģ��
            Vector moduleTree = SysModule.getModuleTree();
            int curDegree = 1;
            for (int i = 0; i < moduleTree.size(); i++) {
                Module md = (Module)moduleTree.get(i);
                String mdCode = md.getCode();   //ģ��ı���
                if (md.getIntLevel() < curDegree) {   //�ϼ�ģ��
                     for (int j = md.getIntLevel(); j < curDegree; j++) {  //����տڱ�ǩ
        %>
            </td>
          </tr>
        </table>
        <%
                     }
                }
        %>
        <table width="100%" border="0" align="center" cellspacing="1" cellpadding="4">
          <tr>
            <td width="1%" height="22" valign="top" bgcolor="#F6F6F6">
              <table width="12" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="30">
        <%
                //ֻ��affair��operate�߱������Ȩ��
                if (md.getType().equals("module")&&md.getUrl().equals("")) {
        %>
                    <font color="#0000FF"><strong>*</strong></font>
        <%
                } else {
        %>
                    <input name='sel' type='checkbox' value='<%=mdCode%>'<%=rights.containsKey(mdCode) ? " checked" : ""%>>
        <%
                }
        %>
                  </td>
                </tr>
              </table>
            </td>
            <td width="99%" height="22">
              <%=md.getName()%>
        <%
                if (!md.haveChild()) {  //û���¼�ģ�飬ֱ���տ�
        %>
            </td>
          </tr>
        </table>
        <%
                }
                curDegree = md.getIntLevel();
            }
            //����տ�
            for (int j = 1; j < curDegree; j++) {
        %>
            </td>
          </tr>
        </table>
        <%
            }
        %>
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
            <input type="button" name="b_submit" value="����" class="button_box" onclick="frm.submit()"/>
            <input type="button" name="b_reset"    value="�� ��" class="button_box" onClick="frm.reset();" style="cursor:hand">
            <input type="button" name="b_back"     value="�� ��" class="button_box"  onClick="window.close();"style="cursor:hand">
          </TD>
        </TR>
    </TABLE>
    
</div>
</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>