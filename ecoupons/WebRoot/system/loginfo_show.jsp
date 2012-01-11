<%@ page import="com.ejoysoft.common.exception.IdObjectException,
                 com.ejoysoft.ecoupons.system.SysLog,com.ejoysoft.common.Format"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
        //返回页面的url
    String strId=ParamUtil.getString(request,"strId","");
    String strTableName=ParamUtil.getString(request,"strTable","t_sy_log");
       if(strId.equals(""))
       throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
       String where="where strId='"+strId+"'";
        SysLog log=new SysLog(globa,false);
        log.setStrTableName(strTableName);
       SysLog  log0=log.show(where);
    if(log==null){
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
</head>

<body class="ContentBody">
<form name="frm" method="post" action="">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><div align="center" class="style1">
        <div align="left">日志管理</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" class="CPanel" valign="top">
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="0" align="left"></td></tr>
        <TR>
            <TD width="100%" height="100" valign="top">
                <fieldset>
                <legend>日志详细信息</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
                <tr class="td_listbg_1">
                <td width="11%" align="right"  height="25" nowrap>操 作 人：</td>
                <td   class="time1"> &nbsp;
                 <%=log0.getStrOperator()%>
                </td>
              </tr>
              <tr class="td_listbg_2">
                <td width="11%" align="right"  height="25">操作模块：</td>
                <td class="time1">&nbsp;
                <%=log0.getStrOther()%>
                </td>
              </tr>
              <tr class="td_listbg_1">
                <td width="11%" align="right" height="25">操作信息：</td>
                <td  class="time1"> &nbsp;
                  <%=log0.getStrCode()%>
                </td>
              </tr>
              <tr class="td_listbg_2">
                <td width="11%" align="right"  height="25">操作日期：</td>
                <td class="time1"> &nbsp;
                  <%=Format.getFormatDate(log0.getdOccurDate(),"yyyy-mm-dd hh:mm:ss")%>
                  &nbsp; </td>
              </tr>
              <tr class="td_listbg_1">
                <td width="11%" align="right" height="25">详细内容：</td>
                <td class="time1"> &nbsp; <%=log0.getStrContent()%>
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
            <input type="button" name="b_back"   value="返 回" class="button"  onClick="history.back()"  style="cursor:hand"/>
            </TD>　　
        </TR>
    </TABLE>
    
</div>
</form>
</body>
</html>
