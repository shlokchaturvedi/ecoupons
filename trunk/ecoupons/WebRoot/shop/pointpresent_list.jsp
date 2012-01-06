<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.common.exception.NoRightException" %>
<%@page import="com.ejoysoft.ecoupons.business.Point"%>
<%@page import="sun.net.www.content.image.gif"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.PointPresent"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("10020"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
     //获取单位的strId
    //String  strUnitId=ParamUtil.getString(request,"strUnitId","");
    //初始化
    PointPresent  user0=null;
    PointPresent obj=new PointPresent(globa);
    
    //查询条件
    String  strName=ParamUtil.getString(request,"strName","");
	String tWhere=" WHERE 1=1";
	if (!strName.equals("")) {
		tWhere += " and strName LIKE '%" + strName + "%' ";
	}
	if("商家".equals(globa.userSession.getStrCssType())){
		tWhere +=" where strCreator='"+globa.userSession.getStrCssType()+"' ";
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
	Vector<PointPresent> vctObj=obj.list(tWhere,intStartNum,intPageSize);
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
    frm.action="point_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
</script>
</head>
<body>
<form name=frm method=post action="pointpresent_list.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">积分转赠</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 商家管理 / 积分转赠</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以对电子积分转赠进行管理！<br>
                  包括积分购买、查看等多种操作。 </span></td>
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
			 
			 <a href="pointpresent_add.jsp"><img src="../images/add.gif" width="16" height="16" border="0" />转赠</a>
			</td>
			<td align="right" width="600"><div style="height:26"> </div>
			 
			</td>   
			</tr>
			</table>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
              <tr>
               
                <td width="10%" class="left_bt2"><div align="center">商家</div></td>
                <td width="10%" class="left_bt2"><div align="center">会员卡号</div></td>
                <td width="10%" class="left_bt2"><div align="center">积分</div></td>                
              </tr>
            <%
            Shop shop=new Shop(globa);
            for (int i = 0;i < vctObj.size(); i++) {
            	PointPresent obj1 = vctObj.get(i);
            	
            %>
              <tr>
                
                <td bgcolor="#FFFFFF"><div align="center" class="STYLE1"><%=shop.returnBizShopName("where strId= '"+obj1.getStrShopId()+"' ") %></div>
                </td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrMemberCardNo() %></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getIntPoint()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">
			      </span> </div>
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