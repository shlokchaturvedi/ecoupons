<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 java.util.HashMap"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	 
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Member obj=new Member(globa,false);
    Member obj0=obj.show(where);
    if(obj0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
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
	font-size: 9pt;
}

body,td,tr {
	font-size: 9pt;
}
-->
</style>
		<link href="../images/skin.css" rel="stylesheet" type="text/css" />
		<script src="../include/js/chkFrm.js"></script>
		<script language="JavaScript" src="../include/DatePicker/WdatePicker.js"></script>
		<script language="javascript">
		function chkFrm() {
		    if(trim(frm.strCardNo.value)=="") {
		        alert("请输入卡号！！！")
		        frm.strCardNo.focus();
		        return false;
		    }else if(trim(frm.dtExpireTime.value)==""){
		    	alert("请输入有效期！！！")
		        frm.dtExpireTime.focus();
		        return false;
		        } 
		    else {
		    	if(confirm("确定激活!"))
		        {
			      frm.submit();
			    }
		        }
		   
		}
</script>
	</head>

	<body>
		<form name="frm" method="post" action="member_act.jsp">
			<input type="hidden" name="<%=Constants.ACTION_TYPE%>"
				value="<%=Constants.AUDIT_STR%>">
			<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
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
										激活会员
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
												当前位置：日常管理 / 会员管理 / 激活会员
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
															<span class="left_txt2">在这里，您可以激活会员</span>
															<br>
															<span class="left_txt2">提示：激活会员前，必须填写会员有效期。。</span>
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
															&nbsp;&nbsp;&nbsp;&nbsp;会员
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">

													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															姓 名：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="strName" type="text" class="input_box" readonly="readonly"
																size="30"
																value="<%=Format.forbidNull(obj0.getStrName())%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>
													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															卡号：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="strCardNo" type="text" class="input_box" readonly="readonly"
																size="30"
																value="<%=Format.forbidNull(obj0.getStrCardNo())%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>
													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															会员类型：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input type="text" name="intAudit" 
																value="<%=obj0.getType(obj0.getIntType())%>" readonly="readonly"
																class="input_box">
															
															
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>


													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															手机：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="strMobileNo" type="text" class="input_box"
																size="30"
																value="<%=Format.forbidNull(obj0.getStrMobileNo())%>" readonly="readonly"/>
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>
													</tr>
													<tr  >
														<td width="20%" height="30" align="right"
															class="left_txt2">
															启用时间：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="dtActiveTime" readonly="readonly"
																type="text" class="input_box" size="30"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																value="<%=com.ejoysoft.common.Format.getDateTime()%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;
														</td>
													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															余额：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="flaBalance" readonly="readonly" type="text"
																class="input_box" size="30"
																value="<%=Format.forbidNull(String.valueOf(obj0.getFlaBalance()))%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>
													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															积分：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="intPoint" readonly="readonly" type="text"
																class="input_box" size="30"
																value="<%=Format.forbidNull(String.valueOf(obj0.getIntPoint()))%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
														</td>
													</tr>
													<tr  >
														<td width="20%" height="30" align="right"
															class="left_txt2">
															有效期：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="dtExpireTime" type="text" readonly="readonly" value="2099-12-31 00:00:00" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input_box"
																size="30"
																 />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;
														</td>
													</tr>
													<tr  bgcolor="#f2f2f2">
														<td width="20%" height="30" align="right"
															class="left_txt2">
															销售员：
														</td>
														<td width="3%">
															&nbsp;
														</td>
														<td width="32%" height="30">
															<input name="strSalesman" readonly="readonly" type="text"
																class="input_box" size="30"
																value="<%=Format.forbidNull(obj0.getStrSalesman())%>" />
														</td>
														<td width="45%" height="30" class="left_txt">
															&nbsp;提示：不能修改！
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