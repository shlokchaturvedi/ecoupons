<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="java.util.Vector,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.common.Constants,java.util.*"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String strId=ParamUtil.getString(request,"strId",""); 
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
	font-size: 9pt;
}

body,td,tr {
	font-size: 9pt;
}
-->
</style>
		<link href="../images/skin.css" rel="stylesheet" type="text/css" />
		<script src="../include/js/chkFrm.js"></script>
		<script language="JavaScript"
			src="../include/DatePicker/WdatePicker.js"></script>
		<script language="javascript">
		
		function chkPoit() {
			if(trim(frm.intMoney.value)!="") {
				frm.intPoint.focus();
				frm.intPoint.value=frm.intMoney.value*10;
			}}
		
		function chkFrm() {
			if(trim(frm.strShopId.value)=="") {
		        alert("请选择商家！！")
		        frm.strShopId.focus();
		        return false;
		    }else
		    if(trim(frm.intMoney.value)=="") {
		        alert("请输入金额！！！")
		        frm.intMoney.focus();
		        return false;
		    }
		    else
			    if(frm.intPoint.value==""){
			    	 alert("请输入积分数额！！！")
				        frm.intPoint.focus();
				        return false;
				    }
			    
		    else {
		    	if(!isNumber(frm.intMoney.value)){
		    		alert("请输入数字，"+frm.intMoney.id+"为不大于9的整数!")
			        frm.intMoney.focus();
			        return false;
			    	}else
			    		if(frm.intMoney.value.length>8){
				    		alert("金额为不大于9的整数!")
					        frm.intMoney.focus();
					        return false;
					    	}else
		    	if(!isNumber(frm.intPoint.value)){
		    		alert("请输入数字，积分为不大于9的整数!")
			        frm.intPoint.focus();
			        return false;
			    	}else
			    		if(frm.intPoint.value.length>9){
				    		alert("积分为不大于9的整数!")
					        frm.intPoint.focus();
					        return false;
					    	}else
					    		
		        if(confirm("确定购买!"))
			        {
		    	frm.submit();
		    	    }
		          }
		}
		
</script>
	</head>
	<body>
		<form name="frm" method="post" action="point_act.jsp">
			<input type="hidden" name="<%=Constants.ACTION_TYPE%>"
				value="<%=Constants.ADD_STR%>">
			<input type="hidden" name=strMemberCardNo value="<%=strId%>">
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
										购买管理
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
						<table width="100%" height="933" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="13" valign="top">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="918" valign="top">
									<table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td class="left_txt">
												当前位置：日常管理 / 商家管理 / 购买积分
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
															<span class="left_txt2">在这里，您可以用现金购买积分！</span>
															<br>

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
												<table width="100%" height="31" border="0" cellpadding="0"
													cellspacing="0" class="nowtable">
													<tr>
														<td class="left_bt2">
															&nbsp;&nbsp;&nbsp;&nbsp;积分购买
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="20%" height="30" align="right"
															class="left_txt2">
															商家名称：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30"><select name="strShopId" class="forms_color1"
																style="width: 213px">
																<option value="">
																	请选择商家名称
																</option>
																<%
                                                                  //初始化
    				                                            	//SysPara  para=null;
   						                                            Shop para=new Shop(globa,true);
   						                                         String Where ="";
   						                                         if("商家".equals(globa.userSession.getStrCssType())){
   						                                  		Where =" where strid='"+globa.userSession.getStrShopid()+"' ";
   						                                  	}
   						                                             Vector<Shop> vctShop=para.list(Where,0,0);
                                                                     for (int i = 0; i < vctShop.size(); i++) {
                                                                     out.print("<option value=" + vctShop.get(i).getStrId()+ ">");
                                                                     out.println("" +vctShop.get(i).getStrBizName()+vctShop.get(i).getStrShopName() + "</option>");
                	                                          %>
																<%
                                                                 }
                                                               %>
															</select></td>
														<td width="45%" height="30" class="left_txt">

														</td>
													</tr>

													<tr>
														<td width="20%" height="30" align="right"
															class="left_txt2">
															积分类型：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input type="radio" name="intType" value="1" checked
																class="input_box">
															电子积分
															<input type="radio" name="intType" value="2"
																class="input_box">
															纸质积分
														</td>
														<td width="45%" height="30" class="left_txt">

														</td>
													</tr>

													<tr>
														<td width="20%" height="30" align="right"
															class="left_txt2">
															购买金额：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input id="金额" name="intMoney" type="text" class="input_box"
																size="30" onchange="chkPoit()"/>
														</td>
														<td width="45%" height="30" class="left_txt">

														</td>
													</tr>
													<tr>
														<td width="20%" height="30" align="right"
															class="left_txt2">
															购买积分：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input id="积分" name="intPoint" type="text" class="input_box"
																size="30" />
														</td>
														<td width="45%" height="30" class="left_txt">

														</td>
													</tr>





												</table>
											</td>
										</tr>
									</table>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="50%" height="56" align="right">
												<input name="B1" type="button" class="button_box" value="确定"
													onclick="chkFrm()" />
											</td>
											<td width="6%" height="56" align="right">
												&nbsp;
											</td>
											<td width="44%" height="56">
												<input name="B12" type="reset" class="button_box" value="取消" />
											</td>
										</tr>
										<tr>
											<td height="30" colspan="3">
												&nbsp;
											</td>
										</tr>
									</table>
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