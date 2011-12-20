<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.SysLog,
                 java.util.Vector,com.ejoysoft.common.Format,com.ejoysoft.common.Constants"%>

<%@ include file="../include/jsp/head.jsp"%>
<%

    //初始化
    SysLog  log0=null;
    SysLog obj=new SysLog(globa);
%>
<%
    //查询条件
    String operator=ParamUtil.getString(request,"operator","");  //操作人
    String startdate=ParamUtil.getString(request,"startdate","");  //起始时间
    String enddate=ParamUtil.getString(request,"enddate","");    //截至时间
    String tWhere=" WHERE 1=1";
    if(!operator.equals(""))
        tWhere+=" and strOperator like '%"+operator+"%'";
%>
<%
       //记录总数
        obj.setStrTableName("t_sy_loghistory");
      int intAllCount=obj.getCount(tWhere,startdate,enddate);
      //当前页
      int  intCurPage=globa.getIntCurPage();
      //每页记录数
       int intPageSize=globa.getIntPageSize();
      //共有页数
       int intPageCount=(intAllCount-1)/intPageSize+1;
        // 循环显示一页内的记录 开始序号
      int intStartNum=(intCurPage-1)*intPageSize+1;
      //结束序号
      int intEndNum=intCurPage*intPageSize;
     //获取到当前页面的记录集
       Vector vctObj=obj.list(tWhere,startdate,enddate,intStartNum,intPageSize);
      //获取当前页的记录条数
      int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
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
<script src="../include/js/list.js"></script>
<script language="javascript">
function logSch(frmObj,actPage)
{
    var frmName=eval(frmObj);
    if (frmName.keyName.value=="")
    {
        alert("请选择查询类别！！！");
        return;
    }
     frmName.action=actPage;
     frmName.submit();
}

function del(){
	if (iCheckedNumber(document.all.strId) == 0) {
		alert("请先选择要删除的记录！");
		return;
	}
    if(!confirm('您是否确认要删除所选中的所有记录？'))
        return;
     frm.action="loginfo_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
</script>
</head>

<body >
<form name=frm method=post action="loginfo_list.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">日志管理</div></td>
      </tr>
    </table></td>
    <td width="22" valign="top" background="../images/mail_rightbg.gif"><img src="../images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td height="71" valign="middle" background="../images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9"><table width="100%" height="547" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="13" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td height="534" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：系统管理 / 运行维护 / 历史日志</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以对历史日志进行管理！<br>
                  包括查询等多种操作。 </span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td >
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
			<td style="font-size:9pt">
			 	</td>
			<td align="right" width="600"><div style="height:26"> 
			  用户名：<input name="strName" class="editbox4" value="" size="10">
			  &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="submit" class="button_box" value="搜索" /> 
			</div>
			</td>   
			</tr>
			</table>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
              <tr>
                <td width="5%" height="22"  class="left_bt2"><div align="center">&nbsp;</td>
                <td width="10%" class="left_bt2"><div align="center">操作人</div></td>
                <td width="10%" class="left_bt2"><div align="center">操作模块</div></td>
                <td width="25%" class="left_bt2"><div align="center">操作休息</div></td>                
                 <td width="10%" class="left_bt2"><div align="center">操作日期</div></td>
                <td width="15%" class="left_bt2"><div align="center">机器IP</div></td>
              </tr>
            <%
             for(int i=0;i<intVct;i++){
		                          log0=(SysLog)vctObj.get(i);
            %>
              <tr>
                <td height="20" bgcolor="#FFFFFF"><div align="center">
                    <input type="checkbox" name=strId value="<%=log0.getStrId() %>" /><%=intStartNum+i%>
                </div></td>
                <td bgcolor="#FFFFFF"><div align="center" class="STYLE1"><%=log0.getStrOperator()%></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=log0.getStrOther()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a href='sysLogShow.jsp?strId=<%=log0.getStrId()%>'><%=log0.getStrCode()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=Format.getFormatDate(log0.getdOccurDate(),"yyyy-mm-dd hh:mm:ss")%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE4"><%=log0.getStrOperatorIp()%></span> </div>
                </td>
              </tr>
            <%
            }
            %>  
            </table></td>
          </tr>
        </table>
     	<!-- 翻页开始 -->  
     	<%@ include file="../include/jsp/cpage.jsp"%>
       	<!-- 翻页结束 --> 
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