<%@ page language="java"
	import="java.util.*,com.ejoysoft.common.exception.*,com.ejoysoft.common.*"
	pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%
	if (!globa.userSession.hasRight("12015"))
		throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>
<%
	//初始化
	//获取单位的strId
	//String  strUnitId=ParamUtil.getString(request,"strUnitId","");
	//初始化
	CouponInput member = null;
	CouponInput obj = new CouponInput(globa);
	boolean flag = false;
	//查询条件
	String dtCreateTime = ParamUtil.getString(request, "dtCreateTime", "");
	String tWhere = " WHERE 1=1";
	if (!dtCreateTime.equals(""))
	{
		tWhere += " and dtCreateTime LIKE '" + dtCreateTime + "%' ";
		flag = true;
	}
	tWhere += "  ORDER BY strId";
	//记录总数
	int intAllCount = obj.getCount(tWhere);
	//当前页
	int intCurPage = globa.getIntCurPage();
	//每页记录数
	int intPageSize = globa.getIntPageSize();
	//共有页数
	int intPageCount = (intAllCount - 1) / intPageSize + 1;
	// 循环显示一页内的记录 开始序号
	int intStartNum = (intCurPage - 1) * intPageSize + 1;
	//结束序号
	int intEndNum = intCurPage * intPageSize;
	//获取到当前页面的记录集
	Vector<CouponInput> vctObj = obj.list(tWhere, intStartNum, intPageSize);
	//获取当前页的记录条数
	int intVct = (vctObj != null && vctObj.size() > 0 ? vctObj.size() : 0);
	if (flag)
	{
		Vector<CouponInput>vctCouponInputs=obj.returnDistinctStrShopId();
		Shop shop=new Shop(globa);
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1>");
		sb.append("<tr><td>商家ID+名称</td><td>有价券数量</td><td>统计时间</td></tr>");
		if (vctCouponInputs.size() != 0)
		{
			for (int i = 0; i < vctCouponInputs.size(); i++)
			{
				obj.State(obj.show(" where strshopid= '"+vctCouponInputs.get(i).getStrShopId()).getStrMemberCardNo()+"'"," and dtCreateTime LIKE '" + dtCreateTime + "%'");
				sb.append("<tr><td>" + vctCouponInputs.get(i).getStrShopId() +shop.returnBizShopName("where strid='"+vctCouponInputs.get(i).getStrShopId())+"'"+ "</td>"+"<td>"
						+ obj.getCount("where strShopid='" + vctCouponInputs.get(i).getStrShopId() + "' and dtCreateTime LIKE '" + dtCreateTime + "%'")
						+ "</td><td>"+dtCreateTime.replace("-","年")+"月"+"</td>");
			}
		}
		sb.append("</tr></table>");
		String strFileName = "有价券统计表_" + dtCreateTime + ".xls";
		response.setContentType("APPLICATION/*");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(strFileName.getBytes("gbk"), "ISO8859-1"));
		ServletOutputStream output = response.getOutputStream();
		output.write(sb.toString().getBytes());
	}
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
		<script language="JavaScript"
			src="../include/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">

</script>
	</head>
	<body>
		<form name=frm method=post action="coupon_stat.jsp">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="17" height="29" valign="top"
						background="../images/mail_leftbg.gif">
						<img src="../images/left-top-right.gif" width="17" height="29" />
					</td>
					<td width="1195" height="29" valign="top"
						background="../images/content-bg.gif">
						<table width="100%" height="31" border="0" cellpadding="0"
							cellspacing="0" class="left_topbg" id="table2">
							<tr>
								<td height="31">
									<div class="titlebt">
										有价券统计
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td width="22" valign="top" background="../images/mail_rightbg.gif">
						<img src="../images/nav-right-bg.gif" width="16" height="29" />
					</td>
				</tr>
				<tr>
					<td height="71" valign="middle"
						background="../images/mail_leftbg.gif">
						&nbsp;
					</td>
					<td valign="top" bgcolor="#F7F8F9">
						<table width="100%" height="547" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="13" valign="top">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="534" valign="top">
									<table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td class="left_txt">
												当前位置：日常管理 / 优惠券管理 / 有价券统计
											</td>
										</tr>
										<tr>
											<td height="20">
												<table width="100%" height="1" border="0" cellpadding="0"
													cellspacing="0" bgcolor="#CCCCCC">
													<tr>
														<td></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table width="100%" height="55" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="6%" height="55" valign="middle">
															<img src="../images/title.gif" width="54" height="55">
														</td>
														<td width="94%" valign="top">
															<span class="left_txt3">在这里，您可以按月对商家有价券录入进行统计！<br>
															</span>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<tr>
											<td>
												<table border="0" cellpadding="0" cellspacing="0"
													width="100%">
													<tr>
														<td style="font-size: 9pt">
															&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
														<td align="right" width="600">
															<div style="height: 26">
																统计年月：
																<input name="dtCreateTime" class="editbox4"
																	onClick="WdatePicker({dateFmt:'yyyy-MM'})"
																	readonly="readonly" size="10">
																&nbsp;&nbsp;&nbsp;&nbsp;
																<input type="submit"  class="button_box" value="统计" />
															</div>
														</td>
													</tr>
												</table>

												<table width="100%" border="0" cellpadding="0"
													cellspacing="1" bgcolor="b5d6e6"
													onmouseover="chang
	eto();
							onmouseout="changeback()">
													<tr>
														<td width="5%" height="22" class="left_bt2">
															<div align="center">
																&nbsp;
															</div>
														</td>
														<td width="10%" class="left_bt2">
															<div align="center">
																优惠券
															</div>
														</td>
														<td width="15%" class="left_bt2">
															<div align="center">
																券面代码
															</div>
														</td>
														<td width="15%" class="left_bt2">
															<div align="center">
																会员卡号
															</div>
														</td>
														<td width="15%" class="left_bt2">
															<div align="center">
																打印时间
															</div>
														</td>
														<td width="15%" class="left_bt2">
															<div align="center">
																商家
															</div>
														</td>
														<td width="5%" class="left_bt2">
															<div align="center">
																状态
															</div>
														</td>
														<td width="15%" class="left_bt2">
															<div align="center">
																录入时间
															</div>
														</td>

													</tr>
													<%
														Coupon coupon = new Coupon(globa);
														Shop shop = new Shop(globa);
														for (int i = 0; i < vctObj.size(); i++)
														{
															CouponInput obj1 = vctObj.get(i);
													%>
													<tr>
														<td height="20" bgcolor="#FFFFFF">
															<div align="center">
																
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center" class="STYLE1"><%=coupon.show("where strId=" + obj1.getStrCouponId()).getStrName()%></div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=obj1.getStrCouponCode()%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=obj1.getStrMemberCardNo()%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=obj1.getDtPrintTime().substring(0, obj1.getDtPrintTime().length() - 2)%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=shop.returnBizShopName("where strId=" + obj1.getStrShopId())%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=obj1.returnStrState(obj1.getIntState())%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE1"><%=obj1.getStrCreateTime().substring(0, obj1.getStrCreateTime().length() - 2)%></span>
															</div>
														</td>
														<td bgcolor="#FFFFFF">
															<div align="center">
																<span class="STYLE4"> </span>
															</div>
														</td>
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
						</table>
					</td>
					<td background="../images/mail_rightbg.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td valign="middle" background="../images/mail_leftbg.gif">
						<img src="../images/buttom_left2.gif" width="17" height="17" />
					</td>
					<td height="17" valign="top" background="../images/buttom_bgs.gif">
						<img src="../images/buttom_bgs.gif" width="17" height="17" />
					</td>
					<td background="../images/mail_rightbg.gif">
						<img src="../images/buttom_right2.gif" width="16" height="17" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>
