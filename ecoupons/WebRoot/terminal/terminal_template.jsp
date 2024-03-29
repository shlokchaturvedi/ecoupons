<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				com.ejoysoft.common.Constants,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.Terminal" %>
<%@page import="com.ejoysoft.ecoupons.business.TerminalTemplate"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("13010"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
    TerminalTemplate obj=new TerminalTemplate(globa);
    //查询条件
    String  strName=ParamUtil.getString(request,"strName","");
     String  strModuleOfTempl = ParamUtil.getString(request,"strModuleOfTempl","");
	String tWhere=" where 1=1";
	if (!strName.equals("")) {
		tWhere += " and strname like '%" + strName + "%'";
	}
	if (!strModuleOfTempl.equals("") && !strModuleOfTempl.equals("all") )
	{
		tWhere += " and strModuleOfTempl='" + strModuleOfTempl + "'";
	}
	tWhere += " order by intsort";
	//记录总数
	int intAllCount=obj.getCount(tWhere);
	//当前页
	int intCurPage=globa.getIntCurPage();
	//每页记录数
	int intPageSize=globa.getIntPageSize();
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<TerminalTemplate> vctObj=obj.list(tWhere,intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
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
}
body,td,th {
	font-size: 9pt;
	color: #111111;
}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script src="../include/js/list.js"></script>
<script type="text/javascript">
//批量删除信息
function del(){
	if (iCheckedNumber(document.all.strId) == 0) {
		alert("请先选择要删除的记录！");
		return;
	}
    if(!confirm('您是否确认要删除所选中的所有记录？'))
        return;
     frm.action="template_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
</script>
</head>
<body>
<form name=frm method=post action="terminal_template.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">模版信息管理</div></td>
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
            <td class="left_txt">当前位置：业务管理 / 终端管理 / 模版信息</td>
          </tr>
          <tr>
            <td height="20"><table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
              <tr>
                <td align="left"><br></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" height="55" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="6%" height="55" valign="middle"><img src="../images/title.gif" width="54" height="55"></td>
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以对模版信息进行管理！<br>
                  包括新增、编辑、删除、查询等操作。 </span></td>
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
			 <input type="checkbox" name="checkbox62" value="checkbox" onclick="selAll(document.all.strId)"/>
			 全选
			<!-- <a href="template_add.jsp"><img src="../images/add.gif" width="16" height="16" border="0" />新增</a>		
			 <a href="#" onclick="del();"><img src="../images/delete.gif" width="16" height="16" border="0" />批量删除</a>
	
			 --></td>
			<td align="right" width="600"><div style="height:26"> 
			模块类型：<select name="strModuleOfTempl" class="input_text" style="width:185px">
					<%if( strModuleOfTempl!=null && !strModuleOfTempl.trim().equals("")&& !strModuleOfTempl.trim().equals("all"))
                  {%>
                  	<option value="<%=strModuleOfTempl%>" ><%=obj.returnModuleName(strModuleOfTempl)%></option>    
                  <%}%>                                	
				    <option value="all"  > -- 所有终模板元素 -- </option> 
                  	<option value="top">界面顶部</option>
					<option value="home">界面中间首页</option>
					<option value="shopInfo">商家详细</option>
					<option value="shop">商家二级</option>
					<option value="coupon">优惠劵二级和VIP专区</option>
					<option value="myInfo">我的专区</option>
					<option value="nearshop">周边商家</option>
					<option value="ad">广告</option>
					<option value="waitdownload">下载时显示的等待界面</option>
					<option value="waitlogin">验证用户登录时显示的等待界面</option>
					<option value="bottom">界面底部</option>
				</select>
			元素名称：<input type="hidden" value="newsearch" name="curpage"/>
			  <input name="strName" class="editbox4" value="<%=strName%>" size="10">
			  &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="submit" class="button_box" value="搜索" /> 
			</div>
			</td>   
			</tr>
			</table>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
              <tr>
                <td  width=3%" height="22"  class="left_bt2"><div align="center">&nbsp;</div></td>
                <td width="10%" class="left_bt2"><div align="center">元素名称</div></td>                
                <td class="left_bt2"><div align="center">所属模块</div></td>
                <td width="8%" class="left_bt2"><div align="center">元素位置</div></td>
                <td width="8%" class="left_bt2"><div align="center">元素大小</div></td>      
                <td width="10%" class="left_bt2"><div align="center">背景图</div></td>        
                <td width="6%" class="left_bt2"><div align="center">字体</div></td>
                <td width="6%" class="left_bt2"><div align="center">字体大小</div></td>
                <td width="6%" class="left_bt2"><div align="center">字体颜色</div></td>
                <td width="8%" class="left_bt2"><div align="center">元素内容</div></td>
                <td width="16%" class="left_bt2"><div align="center">说明</div></td>
                <td width="10%" class="left_bt2"><div align="center">操作</div></td>
               </tr>
            <%
            	for (int i = 0;i < vctObj.size(); i++) {
                        	TerminalTemplate obj1 = vctObj.get(i);                        	
		                                %> 
              <tr  title="创建时间：<%=obj1.getDtCreateTime()%>" >
                <td height="20" bgcolor="#FFFFFF"><div align="center">
                    <input type="checkbox" name=strId value="<%=obj1.getStrId() %>" />
                </div></td>
                <td bgcolor="#FFFFFF"> <div align="center"><span class="STYLE1"><%=obj1.getStrName()%></span></div></td>
                <td bgcolor="#FFFFFF"> <div align="center"><span class="STYLE1"><%=obj1.getStrModuleOfTemplName()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrLocation()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrSize()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrBgImage()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrFontFamily()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%if(obj1.getIntFontSize()!=0){%><%=obj1.getIntFontSize()%><%}%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrFontColor()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrContent()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrIntro()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">
                  <a href="template_update.jsp?strId=<%=obj1.getStrId()%>"><img src="../images/edit.gif" width="16" height="16" border="0" />编辑</a>
                  <!-- 
			      <a href="#" onclick="if(confirm('确认删除该记录？')){location.href='template_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>&strId=<%=obj1.getStrId()%>';}"><img src="../images/delete.gif" width="16" height="16" border="0" />删除</a> 
                --></span></div></td>
              </tr>
            <%
            }
            %>  
            </table>
          </td>
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