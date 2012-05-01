<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Shop,com.ejoysoft.common.Format,
				 java.util.Vector,
				 com.ejoysoft.common.Constants" %>
<%@ include file="../include/jsp/head.jsp"%>
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
	font-size:9pt;
}
body,td,tr{font-size:9pt;}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../include/DatePicker/WdatePicker.js"></script>
<script src="../include/js/chkFrm.js"></script>
<script language="javascript">
function chkFrm() {
    if(trim(frm.strName.value)=="") {
        alert("请输入名称！！！")
        frm.strName.focus();
        return false;
    }else if(trim(frm.strShopId.value)=="") {
        alert("请选择商家！！！")
        frm.strShopId.focus();
        return false;
    }
    else if(trim(frm.strTerminals.value)=="") {
        alert("请选择终端！！！")
        frm.strTerminalIds.focus();
        return false;
    }
    else if(trim(frm.flaPrice.value)=="") {
        alert("请输入价格！！！")
        frm.flaPrice.focus();
        return false;
    }else if(!isMoney(frm.flaPrice.value)) {
        alert("请输入正确的价格格式！！！")
        frm.flaPrice.focus();
        return false;
    }
    else if(trim(frm.intPrintLimit.value)=="") {
        alert("请输入打印限制！！！")
        frm.intPrintLimit.focus();
        return false;
    } else
    if(!isNumber(frm.intPrintLimit.value)){
		alert("请输入数字，打印次数为不大于9的整数!")
        frm.intPrintLimit.focus();
        return false;
    	}else
    if(frm.intPrintLimit.value.length>9){
	    alert("打印次数为不大于9的整数!")
		frm.intPrintLimit.focus();
		return false;
		}
	else {
    	if(confirm("确定录入!"))
        {
	      frm.submit();
	    }
        }
}

<!-- 显示终端列表-->
function addTerminals()
{
    var terminals = window.showModalDialog("../terminal/terminals_select.jsp?random="+ Math.random(), "选择投放终端", "width=370,height=250,top=200,left=200,scrollbars=yes,status=yes"); //写成一行 
	document.getElementById("strTerminals").value=terminals.substring(0,terminals.length-1);
		  
}  
<!-- 显示打印预览-->
function viewPrint()
{
	if(!isMoney(frm.flaPrice.value)) {
        alert("请输入正确的价格格式！！！")
        frm.flaPrice.focus();
        return false;
    }
    var couponName =trim(frm.strName.value);    
    couponName = encodeURI(couponName);
	var strimg = trim(frm.strPrintImg.value);
    strimg = encodeURI(strimg);
	var info = trim(frm.strIntro.value) ;
    info = encodeURI(info);
	var instruction = frm.strInstruction.value;
	var couponCode ="";
    instruction = encodeURI(instruction).replace(" ", "&nbsp;");
    if(parseFloat(trim(frm.flaPrice.value))>0)
    {
    	couponCode = "验证码：ABCDEFG";
    }
    window.open("printview.jsp?random="+ Math.random()+"&couponCode="+couponCode+"&couponName="+couponName+"&strIntro="+info+"&strInstruction="+instruction+"&strImg="+strimg, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=280,height=600,left=500,top=60"); //写成一行 
}  
</script>
</head>

<body>
<form name="frm" method="post" action="coupon_act.jsp" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">增加优惠券</div></td>
      </tr>
    </table></td>
    <td width="22" valign="top" background="../images/mail_rightbg.gif"><img src="../images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td height="71" valign="middle" background="../images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9"><table width="100%" height="933" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="13" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td height="918" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：日常管理 / 优惠券管理 / 增加优惠券</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以增加优惠券信息</span><br>
                      <span class="left_txt2">包括优惠券名称，投放终端，优惠券图片，生效时间，截止时间，价格等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;优惠券</td>
              </tr>
            </table></td>
          </tr>
          <tr  >
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                <td width="20%" height="30" align="right" class="left_txt2">名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" maxlength="20"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">启用时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <input name="dtActiveTime" type="text" class="input_box" size="30"  value="<%=Format.getDate()%>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
               <tr  >
                 <td width="20%" height="30" align="right" class="left_txt2">截止时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="dtExpireTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<%=Format.getDate()%>" readonly="readonly" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             
               
              <tr  >
                 <td width="20%" height="30" align="right" class="left_txt2">商家：</td>
                <td width="3%">&nbsp;</td>
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
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">投放终端：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
	                <input type="text" readonly name="strTerminals" class="input_box" size="30">
					<input type="button" name="Submit" value="..." onclick="addTerminals()">
				</td>
                <td width="45%" height="30" class="left_txt"><br></td> 
              </tr>
              <tr>
                 <td width="20%" height="30" align="right" class="left_txt2">是否VIP：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <input type="radio" name="intVip" checked="checked" value="1"  class="input_box">
                  是
                  <input type="radio" name="intVip" value="0"  class="input_box">
                  否
                                 </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <%
              if(!"商家".equals(globa.userSession.getStrCssType()))
              {
               %>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">是否推荐：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <input type="radio" name="intRecommend" value="1" checked="checked" class="input_box">
                  是
                  <input type="radio" name="intRecommend" value="0"  class="input_box">
                  否
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>    
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">短信发送：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <input type="radio" name="intSendBySM" value="1" checked="checked" class="input_box">
                  支持
                  <input type="radio" name="intSendBySM" value="0"  class="input_box">
                 不支持
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>              
              <%
              }
               %>
              <tr  >
                 <td width="20%" height="30" align="right" class="left_txt2">价格：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="flaPrice"  type="text" value="0" class="input_box"  size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;提示：价格不为0，代表此优惠券为有价券！</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">打印次数：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="intPrintLimit"  type="text" class="input_box" value="0" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;提示：0为不受限制。</td> 
              </tr>
             
             <tr  >
                <td height="30" align="right" class="left_txt2">小图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strSmallImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("COUPON_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）</td>
              </tr>
              <tr >
                <td height="30" align="right" class="left_txt2">大图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strLargeImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("COUPON_LARGE_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_LARGE_IMG_HEIGHT") %>px，用于前台详细显示）</td>
              </tr>
              <tr >
                <td height="30" align="right" class="left_txt2">打印图：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strPrintImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("COUPON_PRINT_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_PRINT_IMG_HEIGHT") %>px，用于前台详细显示）</td>
              </tr>
              <tr  >
                 <td width="20%" height="30" align="right" class="left_txt2">简&nbsp;&nbsp;&nbsp;&nbsp;介：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strIntro"  type="text"  class="input_box"  size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr  >
                 <td width="20%" height="30" align="right" class="left_txt2">使用说明：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <textArea class="input_box" name="strInstruction" cols="33" rows="5" ></textArea>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
            </table></td>
          </tr>
        </table>
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>  
               <td width="6%" height="26" align="right">&nbsp;</td>
            </tr>
          </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>  
              <td width="30%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
           	  <td width="1%" height="56" align="right">&nbsp;</td>
              <td width="10%" height="56"><input name="B12" type="reset" class="button_box" value="取消" /></td>
              <td width="1%" height="56" align="right">&nbsp;</td>
              <td width="10%" height="56"><input name="B12" type="button" onclick="window.history.back();" class="button_box" value="返回" /></td>
              <td width="1%" height="56" align="right">&nbsp;</td>
              <td width="47%" height="56"><input name="B12" type="button" onclick="viewPrint();" class="button_box" value="打印预览" /></td>
            </tr>            
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
          </table></td>
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