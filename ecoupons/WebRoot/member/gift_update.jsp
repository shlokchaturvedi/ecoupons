<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 com.ejoysoft.ecoupons.business.Shop,java.util.HashMap" %>
<%@page import="com.ejoysoft.ecoupons.business.Gift"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Gift obj=new Gift(globa,false);
    Gift obj0=obj.show(where);
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
        if(trim(frm.intPoint.value)=="") {
            alert("请输入兑换积分！！！")
            frm.strName.focus();
            return false;
        } 
        if(trim(frm.flaPrice.value)=="") {
            alert("请输入市场价格！！！")
            frm.strName.focus();
            return false;
        } 
        else 
        	if(!isMoney(frm.intPoint.value)){
	    		alert("请输入数字，积分为不大于9的整数!")
		        frm.intPoint.focus();
		        return false;
		    	}else
		    		if(frm.intPoint.value.length>9){
			    		alert("积分为不大于9的整数!")
				        frm.intPoint.focus();
				        return false;
				    	}else
        	if(!isMoney(frm.flaPrice.value)){
	    		alert("非法市场价格格式!")
		        frm.flaPrice.focus();
		        return false;
		    	}else
		    		if(frm.flaPrice.value.length>9){
			    		alert("市场价格为不大于9的整数!")
				        frm.flaPrice.focus();
				        return false;
				    	}else
             {
	        if(confirm("确定修改!"))
		        {
	    	frm.submit();
	    	    }
	          } 
    }
</script>
</head>

<body>
<form name="frm" method="post" action="gift_act.jsp" enctype="multipart/form-data" >
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">编辑礼品</div></td>
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
            <td class="left_txt">当前位置：日常管理 / 用户管理 / 编辑礼品</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以编辑礼品信息</span><br>
                      <span class="left_txt2">包括礼品名称，礼品说明，礼品图片，生效时间，截止时间，兑换积分等属性。</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;礼品</td>
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
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">兑换积分：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input   name="intPoint" type="text" class="input_box" size="30" value="<%=obj0.getIntPoint()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">市场价格：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="flaPrice" type="text" class="input_box" size="30"  value="<%=obj0.getFlaPrice()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">生效时间：</td>
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
                 <td width="20%"  height="30" align="right" class="left_txt2">小图片：</td>
                <td width="3%">&nbsp; </td>
                <td width="32%" height="30"><input name="strSmallImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">
                 <%
                if (obj0.getStrSmallImg().length() > 0) {
                %>
                  <img src="<%="images/" + obj0.getStrSmallImg() %>" width=<%=application.getAttribute("GIFT_SMALL_IMG_WIDTH") %> height=<%=application.getAttribute("GIFT_SMALL_IMG_HEIGHT") %>/><br>
                <%
                }
                %>  </td>  
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">&nbsp; </td>
                 <td height="30" class="left_txt">&nbsp;</td> 
                <td width="20%" height="30" class="left_txt">&nbsp;</td> 
                 <td height="30" class="left_txt">
            		     （大小：<%=application.getAttribute("GIFT_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("GIFT_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）
                 </td>	
              </tr>
              <tr >
                <td height="30" align="right" class="left_txt2">大图片：</td>
                <td>&nbsp;</td> 
                <td height="30"><input name="strLargeImg" type="file" class="input_box" size="30"  /></td>
                <td height="30" class="left_txt">
                <%
                if (obj0.getStrLargeImg().length() > 0) {
                %>
                  <img src="<%="images/" + obj0.getStrLargeImg() %>" width=<%=application.getAttribute("GIFT_LARGE_IMG_WIDTH") %> height=<%=application.getAttribute("GIFT_LARGE_IMG_HEIGHT") %>/><br>
                <%
                }
                %> 
                </td>
              </tr> 
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">&nbsp; </td>
                 <td height="30" class="left_txt">&nbsp;</td> 
                <td width="20%" height="30" class="left_txt">&nbsp;</td> 
                 <td height="30" class="left_txt">
            		   （ 大小：<%=application.getAttribute("GIFT_LARGE_IMG_WIDTH") %>*<%=application.getAttribute("GIFT_LARGE_IMG_HEIGHT") %>px，用于前台详细显示）
                 </td>	
              </tr>
          	  <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">礼品简介：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><textArea name="strIntro" cols="33" rows="5" ><%=Format.forbidNull(obj0.getStrIntro())%></textArea></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr> 
          	  <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">注意事项：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><textArea name="strAttention" cols="33" rows="5" ><%=Format.forbidNull(obj0.getStrAttention())%></textArea></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
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
               <td width="58%" height="56"><input name="B12" type="button" onclick="window.history.back();" class="button_box" value="返回" /></td>
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