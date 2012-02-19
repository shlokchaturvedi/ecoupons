<%@ page language="java" import="java.util.*,com.ejoysoft.common.exception.*,com.ejoysoft.common.*" pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(!globa.userSession.hasRight("12010"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>
<%
	//初始化
     //获取单位的strId
    //String  strUnitId=ParamUtil.getString(request,"strUnitId","");
    //初始化
    CouponInput  member=null;
    CouponInput obj=new CouponInput(globa);
    //查询条件
    String  strCouponCode=ParamUtil.getString(request,"strCouponCode","");
	String tWhere=" WHERE 1=1";
	if (!strCouponCode.equals("")) {
		tWhere += " and strCouponCode like '%" + strCouponCode + "%' ";
	}else if("商家".equals(globa.userSession.getStrCssType())){
		tWhere +=" and strShopId='"+globa.userSession.getStrShopid()+"' ";
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
	Vector<CouponInput> vctObj=obj.list(tWhere,intStartNum,intPageSize);
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
     frm.action="couponinput_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>";
     frm.submit();
}
</script>
</head>
<body>
<form name=frm method=post action="couponinput_list.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">有价券录入</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 优惠券管理 / 有价券录入</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以对有价券录入进行管理！<br>
                  包括新增、编辑、删除、查询、审核等多种操作。 </span></td>
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
			 <a href="couponinput_add.jsp"><img src="../images/add.gif" width="16" height="16" border="0" />录入</a>
			 <a href="#" onclick="del();"><img src="../images/delete.gif" width="16" height="16" border="0" />批量删除</a>
			</td>
			<td align="right" width="600"><div style="height:26"> 
			  有价券代码：<input name="strCouponCode" class="editbox4" value="<%=strCouponCode%>" size="10">
			  &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="submit" class="button_box" value="搜索" /> 
			</div>
			</td>   
			</tr>
			</table>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
              <tr>
                <td width="5%" height="22"  class="left_bt2"><div align="center">&nbsp;</div></td>
                <td width="10%" class="left_bt2"><div align="center">优惠券</div></td>
                <td width="15%" class="left_bt2"><div align="center">券面代码</div></td>
                <td width="15%" class="left_bt2"><div align="center">会员卡号</div></td>                
                 <td width="15%" class="left_bt2"><div align="center">打印时间</div></td>
                <td width="15%" class="left_bt2"><div align="center">商家</div></td>
                <td width="5%" class="left_bt2"><div align="center">状态</div></td>
                <td width="20%" class="left_bt2"><div align="center">基本操作</div></td>
              </tr>
            <%
            Coupon coupon=new Coupon(globa);
            Shop shop=new Shop(globa);
            
            	for (int i = 0;i < vctObj.size(); i++) {
                        	CouponInput obj1 = vctObj.get(i);
                        	
           
            %>
              <tr>
                <td height="20" bgcolor="#FFFFFF"><div align="center">
                    <input type="checkbox" name=strId value="<%=obj1.getStrId() %>" />
                </div></td>
                <td bgcolor="#FFFFFF"><div align="center" class="STYLE1"><%= coupon.show("where strId='"+obj1.getStrCouponId()+"'").getStrName() %></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrCouponCode()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getStrMemberCardNo()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getDtPrintTime().substring(0,obj1.getDtPrintTime().length()-2)%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=shop.returnBizShopName("where strId='"+obj1.getStrShopId()+"'")%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.returnStrState(obj1.getIntState())%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE4">
                  <a href="couponinput_update.jsp?strId=<%=obj1.getStrId()%>"><%if(obj1.getIntState()==0){out.print("<img src='../images/edit.gif' width='16'  height='16' border='0' />编辑");} %></a> 
			      <a href="#" onclick="if(confirm('确认删除该记录？')){location.href='couponinput_act.jsp?<%=Constants.ACTION_TYPE%>=<%=Constants.DELETE_STR%>&strId=<%=obj1.getStrId()%>';}"><%if(obj1.getIntState()==0){out.print("<img src='../images/delete.gif' width='16'  height='16' border='0' />删除");} %></a>
			      
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
