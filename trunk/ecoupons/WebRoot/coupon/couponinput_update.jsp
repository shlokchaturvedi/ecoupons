<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 com.ejoysoft.ecoupons.business.Shop,java.util.HashMap" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    CouponInput obj=new CouponInput(globa,false);
    CouponInput obj0=obj.show(where);
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
        if(trim(frm.strCouponCode.value)=="") {
            alert("请输入券面代码！！！")
            frm.strCouponCode.focus();
            return false;
        }
        if(trim(frm.strMemberCardNo.value)=="") {
            alert("请输入会员卡号！！！")
            frm.strMemberCardNo.focus();
            return false;
        } 
        else {
	        if(confirm("确定修改!"))
		        {
	    	frm.submit();
	    	    }
	          } 
    }
   
</script>
</head>

<body>
<form name="frm" method="post" action="couponinput_act.jsp"  >
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">编辑有价券</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 用户管理 / 编辑有价券</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以编辑有价券信息</span><br>
                      <span class="left_txt2">包括优惠券名称，打印时间，券面代码，商家，会员卡号等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;有价券</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              
             <tr>
                <td width="20%" height="30" align="right" class="left_txt2">优惠券：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <select name="strCouponId" class="forms_color1"
																style="width: 213px">
																<%  Coupon coupon=new Coupon(globa,true); %>
																<option value="<%=obj0.getStrCouponId() %>">
																	<%=coupon.show("where strId='"+obj0.getStrCouponId()+"'").getStrName()%>
																</option>
																<%
                                                                  //初始化
                                                                   String Where ="";
  						                                         if("商家".equals(globa.userSession.getStrCssType())){
  						                                  		Where =" where strshopid='"+globa.userSession.getStrShopid()+"' ";
  						                                  	}
   						                                             Vector<Coupon> vctCoupon=coupon.list(Where,0,0);
                                                                     for (int i = 0; i < vctCoupon.size(); i++) {
                                                                     out.print("<option value=" + vctCoupon.get(i).getStrId()+ ">");
                                                                     out.println("" +vctCoupon.get(i).getStrName() + "</option>");
                	                                          %>
																<%
                                                                 }
                                                               %>
															</select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
               
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">券面代码：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <input name="strCouponCode" type="text" class="input_box" size="30"  value="<%=obj0.getStrCouponCode()%>"/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">会员卡号：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                   <input name="strMemberCardNo" type="text" class="input_box" size="30"  value="<%=obj0.getStrMemberCardNo()%>"/>
				</td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">商家：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
               <select name="strShopId" class="forms_color1" style="width: 213px">
																<%  Shop para=new Shop(globa,true);%>
																<option value="<%=obj0.getStrShopId()%>">
																	<%=para.returnBizShopName("where strId="+obj0.getStrShopId())%>
																</option>
																<%
                                                                  //初始化
                                                                  
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
															</select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">打印时间时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <input name="dtPrintTime" type="text" class="input_box" size="30"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" value="<%=obj0.getDtPrintTime().substring(0,obj0.getDtPrintTime().length()-2)%> "/>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
             
              
             
          	  
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="56" align="right"><input name="B1" type="button" class="button_box" value="确定" onclick="chkFrm()" /></td>
              <td width="6%" height="56" align="right">&nbsp;</td>
              <td width="44%" height="56"><input name="B12" type="reset" class="button_box" value="取消" /></td>
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