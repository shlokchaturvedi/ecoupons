 <%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 com.ejoysoft.ecoupons.business.Shop,java.util.HashMap" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Coupon obj=new Coupon(globa,false);
    Coupon obj0=obj.show(where);
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
        }
        else if(trim(frm.strTerminals.value)=="") {
            alert("请选择终端！！！")
            frm.strTerminalIds.focus();
            return false;
        }
        else
        if(trim(frm.flaPrice.value)=="") {
            alert("请输入价格！！！")
            frm.flaPrice.focus();
            return false;
        } 
        else {if(!isMoney(frm.flaPrice.value)){
    		alert("请输入正确的价格格式!")
	        frm.flaPrice.focus();
	        return false;
	    	}else
	    		if(frm.flaPrice.value.length>9){
		    		alert("有效数字不能大于9!")
			        frm.flaPrice.focus();
			        return false;
			    	}else
	        if(confirm("确定修改!"))
		        {
	    	frm.submit();
	    	    }
	          } 
    }
    function addTerminals()
    {
        var terminals = window.showModalDialog("../terminal/terminals_select.jsp?flag=coupon&couponId="+<%=strId%>+"&random="+ Math.random(), "选择投放终端", "width=370,height=250,top=200,left=200,scrollbars=yes,status=yes"); //写成一行 
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
    instruction = encodeURI(instruction).replace(" ", "&nbsp;");
    var couponCode="";
    if(strimg=="")
    {
    	strimg="../coupon/images/"+"<%=obj0.getStrPrintImg()%>";
    }
    if(parseFloat(trim(frm.flaPrice.value))>0)
    {
    	couponCode = "验证码：ABCDEFG";
    }
    window.open("printview.jsp?random="+ Math.random()+"&couponCode="+couponCode+"&couponName="+couponName+"&strIntro="+info+"&strInstruction="+instruction+"&strImg="+strimg, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=280,height=600,left=500,top=60"); //写成一行 
}  
</script>
</head>

<body>
<form name="frm" method="post" action="coupon_act.jsp" enctype="multipart/form-data" >
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">编辑纸质优惠券</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 用户管理 / 编辑优惠券</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以编辑优惠券信息</span><br>
                      <span class="left_txt2">包括名称，启用时间，截至时间，商家，投放终端，价格，是否vip，是否推荐等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;优惠券编辑</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              
             <tr>
                <td width="20%" height="30" align="right" class="left_txt2">名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" value="<%=obj0.getStrName()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">商家：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <select name="strShopId" class="forms_color1" style="width: 213px">
																<%  Shop para=new Shop(globa,true);
																String Where ="";
  						                                         if("商家".equals(globa.userSession.getStrCssType())){
  						                                  		Where =" where strid='"+globa.userSession.getStrShopid()+"' ";
  						                                  	}
																%>
																<option value="<%=obj0.getStrShopId()%>">
																	<%=para.returnBizShopName("where strId="+obj0.getStrShopId())%>
																</option>
																<%
                                                                  //初始化
   						                                             Vector<Shop> vctShop=para.list(Where,0,0);
                                                                     for (int i = 0; i < vctShop.size(); i++) {
                                                                     out.print("<option value=" + vctShop.get(i).getStrId()+ ">");
                                                                     out.println("" +vctShop.get(i).getStrBizName()+vctShop.get(i).getStrShopName() + "</option>");
                	                                          %>
																<%
                                                                 }
                                                               %>
															</select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">启用时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <input name="dtActiveTime" type="text" class="input_box" size="30" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=obj0.getDtActiveTime()%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">截止时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <input name="dtExpireTime" type="text" class="input_box" size="30" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" value="<%=obj0.getDtExpireTime()%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">投放终端：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                    <input type="text" value="<%=obj0.getStrTerminals()%>"  readonly id="strTerminals" name="strTerminals" class="input_box" size="30">
					<input type="button" name="Submit" value="..." onclick="addTerminals()">
				</td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">是否VIP：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                
					 <input type="radio" name="intVip"  value="1" <%if(obj0.getIntVip()==1) out.print("checked");%> class="input_box" />是
					 <input type="radio" name="intVip"  value="0" <%if(obj0.getIntVip()==0) out.print("checked");%> class="input_box"/>不是
				</td>
				</tr>				
              <%
              if(!"商家".equals(globa.userSession.getStrCssType()))
              {
               %>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">是否推荐：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
					 <input type="radio" name="intRecommend"  value="1" <%if(obj0.getIntRecommend()==1) out.print("checked");%> class="input_box" />是
					 <input type="radio" name="intRecommend"  value="0" <%if(obj0.getIntRecommend()==0) out.print("checked");%> class="input_box"/>不是
				</td>
				</tr>
				<%} %>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">价格：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <input name="flaPrice" type="text" class="input_box" size="30"   value="<%=obj0.getFlaPrice()%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;提示：价格不为0，代表此优惠券为有价券！</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">打印次数：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <input name="intPrintLimit" type="text" class="input_box" size="30"   value="<%=obj0.getIntPrintLimit()%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;提示：0为不受限制。</td> 
              </tr>
              <tr >
                 <td width="20%"  height="30" align="right" class="left_txt2">小图片：</td>
                <td width="3%">&nbsp; </td>
                <td width="32%" height="30"><input name="strSmallImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">
                 <%
                if (obj0.getStrSmallImg().length() > 0) {
                %>
                  <img src="<%="images/" + obj0.getStrSmallImg()+"?random="+Math.random() %>" width=<%=application.getAttribute("COUPON_SMALL_IMG_WIDTH") %> height=<%=application.getAttribute("COUPON_SMALL_IMG_HEIGHT") %> alt="小图片"/><br>
                <%
                }
                %>    （大小：<%=application.getAttribute("COUPON_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）
                </td>  
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>               
              <tr >
                <td height="30" align="right" class="left_txt2">大图片：</td>
                <td>&nbsp;</td> 
                <td height="30"><input name="strLargeImg" type="file" class="input_box" size="30"  /></td>
                <td height="30" class="left_txt">
                <%
                if (obj0.getStrLargeImg().length() > 0) {
                %>
                  <img alt="大图片" src="<%="images/" + obj0.getStrLargeImg()+"?random="+Math.random() %>" width=<%=Integer.parseInt(application.getAttribute("COUPON_LARGE_IMG_WIDTH").toString())/2 %> height=<%=Integer.parseInt(application.getAttribute("COUPON_LARGE_IMG_HEIGHT").toString())/2%>/><br>
                <%
                }
                %>   （大小：<%=application.getAttribute("COUPON_LARGE_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_LARGE_IMG_HEIGHT") %>px，用于前台列表显示）
                 </td>
              </tr> 
              <tr >
                <td height="30" align="right" class="left_txt2">打印图：</td>
                <td>&nbsp;</td> 
                <td height="30"><input name="strPrintImg" type="file" class="input_box" size="30"  /></td>
                <td height="30" class="left_txt">
                <%
                if (obj0.getStrPrintImg()!=null && obj0.getStrPrintImg().length() > 0) {
                %>
                  <img alt="打印图" src="<%="images/" + obj0.getStrPrintImg()+"?random="+Math.random() %>" width=<%=application.getAttribute("COUPON_PRINT_IMG_WIDTH") %> height=<%=application.getAttribute("COUPON_PRINT_IMG_HEIGHT") %> /><br>
                <%
                }
                %> 	   （ 大小：<%=application.getAttribute("COUPON_PRINT_IMG_WIDTH") %>*<%=application.getAttribute("COUPON_PRINT_IMG_HEIGHT") %>px，用于前台详细显示）
             
                </td>
              </tr>               
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">简&nbsp;&nbsp;&nbsp;&nbsp;介：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <input name="strIntro" type="text" class="input_box" size="30"   value="<%=Format.forbidNull(obj0.getStrIntro())%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">使用说明：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <textArea class="input_box" name="strInstruction" cols="33" rows="5" ><%=Format.forbidNull(obj0.getStrInstruction())%></textArea>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
          	  
            </table></td>
          </tr>
         </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
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