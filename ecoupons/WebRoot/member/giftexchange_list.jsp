<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.*,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.common.exception.NoRightException" %>
<%@page import="com.ejoysoft.ecoupons.business.Gift"%>
<%@page import="sun.net.www.content.image.gif"%>
<%@page import="com.ejoysoft.ecoupons.business.GiftExchange"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("11015"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
     //获取单位的strId
    //String  strUnitId=ParamUtil.getString(request,"strUnitId","");
    //初始化
    GiftExchange  user0=null;
    GiftExchange obj=new GiftExchange(globa);
    //查询条件
    String  strName=ParamUtil.getString(request,"strName","");
	String tWhere=" WHERE 1=1";
	if (!strName.equals("")) {
		tWhere += " and strName LIKE '%" + strName + "%' ";
	}
	tWhere += " ORDER BY dtCreateTime";
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
	Vector<GiftExchange> vctObj=obj.list(tWhere,intStartNum,intPageSize);
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
    frm.action="gift_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
</script>
</head>
<body>
<form name=frm method=post action="gift_list.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">礼品兑换记录</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 会员管理 / 礼品兑换记录</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以对礼品兑换记录信息进行管理！<br>
                  包括激活、删除、查询等多种操作。 </span></td>
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
			 
			</td>
			<td align="right" width="600"><div style="height:26"> 
			  名称：<input name="strName" class="editbox4" value="" size="10">
			  &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="submit" class="button_box" value="搜索" /> 
			</div>
			</td>   
			</tr>
			</table>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
              <tr>
                <td width="5%" height="22"  class="left_bt2"><div align="center">&nbsp;</div></td>
                <td width="10%" class="left_bt2"><div align="center">名称</div></td>
                <td width="10%" class="left_bt2"><div align="center">会员卡号</div></td>
                <td width="25%" class="left_bt2"><div align="center">兑换时间</div></td>                
                 <td width="10%" class="left_bt2"><div align="center">寄送地址</div></td>
                 <td width="10%" class="left_bt2"><div align="center">状态</div></td>
                <td width="15%" class="left_bt2"><div align="center">基本操作</div></td>
              </tr>
            <%
            Gift gift=new Gift();
            
            for (int i = 0;i < vctObj.size(); i++) {
            	GiftExchange obj1 = vctObj.get(i);
            %>
              <tr>
                <td height="20" bgcolor="#FFFFFF"><div align="center">
                    <input type="checkbox" name=strId value="<%=obj1.getStrId() %>" />
                </div></td>
                <td bgcolor="#FFFFFF"><div align="center" class="STYLE1"><%=gift.show("where strId='"+obj1.getStrGiftId()+"' ").getStrName()%></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getDtExchangeTime() %></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=Format.forbidNull(obj1.getStrAddr())%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj.returnState(obj1.getIntState())%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">
                  <a href="gift_update.jsp?strId=<%=obj1.getStrId()%>"><img src="../images/edit.gif" width="16" height="16" border="0" />编辑</a> 
			      <a href="#" onclick="if(confirm('确认删除该记录？')){location.href='gift_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>&strId=<%=obj1.getStrId()%>';}"><img src="../images/delete.gif" width="16" height="16" border="0" />删除</a></span> </div>
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