<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ejoysoft.common.exception.IdObjectException"%>
<%@page import="com.ejoysoft.ecoupons.business.TerminalTemplate"%>
<%@page import="com.ejoysoft.common.Constants"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    TerminalTemplate obj=new TerminalTemplate(globa,false);
    TerminalTemplate obj0=obj.show(where);
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
<script src="../include/js/chkFrm.js"></script>
<script src="../include/DatePicker/WdatePicker.js"></script>
<script language="javascript">
    function chkFrm() {
       if(trim(frm.strName.value)=="") {
            alert("请输入元素名称！！！")
            frm.strName.focus();
            return false;
        }else if(trim(frm.strLocationX.value)=="") {
            alert("请输入正确元素位置！！！")
            frm.strLocationX.focus();
            return false;
        }else if(trim(frm.strLocationY.value)=="") {
            alert("请输入正确元素位置！！！")
            frm.strLocationY.focus();
            return false;
        }else if(trim(frm.strSizeW.value)=="") {
            alert("请输入正确元素大小！！！")
            frm.strSizeW.focus();
            return false;
        }else if(trim(frm.strSizeH.value)=="") {
            alert("请输入正确元素大小！！！")
            frm.strSizeH.focus();
            return false;
        }
        else {  
       		if(trim(frm.strLocationX.value)!="") {
	        	var strLocationX = trim(frm.strLocationX.value);
	            var strLocationXParn =/^\d+$/; 
	            var reParn = new RegExp(strLocationXParn);
	        	if(!reParn.test(trim(strLocationX)))
        		{
                  alert("请输入正确的元素位置！！！如20");
                  frm.strLocationX.focus();         
        		  return false;
        		}            
        	} 
        	if(trim(frm.strLocationY.value)!="") {
	        	var strLocationY = trim(frm.strLocationY.value);
	            var strLocationYParn =/^\d+$/; 
	            var reParn = new RegExp(strLocationYParn);
	        	if(!reParn.test(trim(strLocationY)))
        		{
                  alert("请输入正确的元素位置！！！如20");
                  frm.strLocationY.focus();         
        		  return false;
        		}            
        	}            	
        	if(trim(frm.strSizeW.value)!="") {
	        	var strSizeW = trim(frm.strSizeW.value);
	            var strSizeWParn =/^\d+$/; 
	            var reParn = new RegExp(strSizeWParn);
	        	if(!reParn.test(trim(strSizeW)))
        		{
                  alert("请输入正确的元素大小！！！如200");
                  frm.strSizeW.focus();         
        		  return false;
        		}            
        	}        
        	if(trim(frm.strSizeH.value)!="") {
	        	var strSizeH = trim(frm.strSizeH.value);
	            var strSizeHParn =/^\d+$/; 
	            var reParn = new RegExp(strSizeHParn);
	        	if(!reParn.test(trim(strSizeH)))
        		{
                  alert("请输入正确的元素大小！！！如200");
                  frm.strSizeH.focus();         
        		  return false;
        		}            
        	}         	
	        if(trim(frm.intFontSize.value)!="") {
	        	var fontsize = trim(frm.intFontSize.value);
	            var FontSizeParn =/^\d+$/; 
	            var reParn = new RegExp(FontSizeParn);
	        	if(!reParn.test(trim(fontsize)))
        		{
                  alert("请输入正确的字体大小！！！如20");
                  frm.intFontSize.focus();         
        		  return false;
        		}            
        	}   	
         	frm.submit();
        }
    } 
</script>
</head>

<body>
<form name="frm" method="post" action="template_act.jsp" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name="strId" value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif">
    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">更新模版信息</div></td>
      </tr>
    </table></td>
    <td width="22" valign="top" background="../images/mail_rightbg.gif"><img src="../images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
 <tr>
    <td height="71" valign="middle" background="../images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    <table width="100%" height="933" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="13" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td height="918" valign="top">
       <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：业务管理 / 终端管理 / 更新模版信息</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以更新模版信息</span><br>
                      <span class="left_txt2">包括模版信息中的元素名称、位置、背景图等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;模版信息
                </td>
              </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">所属模块：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
				<select name="strModuleOfTempl" class="input_text" style="width:213px">
				    <option value="<%=obj0.getStrModuleOfTempl()%>" ><%=obj0.getStrModuleOfTemplName()%></option>    
                 	<option value="top">界面顶部</option>
					<option value="home">界面中间首页</option>
					<option value="shopInfo">商家详细</option>
					<option value="shop">商家二级</option>
					<option value="coupon">优惠劵二级和VIP专区</option>
					<option value="myInfo">我的专区</option>
					<option value="nearshop">周边商家</option>
					<option value="ad">广告</option>
					<option value="waitdownload">下载时显示的等待界面</option>
					<option value="waitlogin">验证用户登录时显示的等待界面</option>
					<option value="bottom">界面底部</option>
				</select>
				</td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>   
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">元素名称：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" value="<%=obj0.getStrName()%>" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>  
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">元素位置：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                <%
                String X ="",Y="";
                if(obj0.getStrLocation()!=null && !obj0.getStrLocation().trim().equals("")) 
                {
                	X = obj0.getStrLocation().split(",")[0];
                	Y = obj0.getStrLocation().split(",")[1];
                }
                %>
                <table>
                	<tr>
                		<td> X：<input name="strLocationX" type="text" value="<%=X%>"  class="input_box" style="width:60px"/></td>
                		<td style="width: 40px">&nbsp;</td>
                		<td> Y：<input name="strLocationY" type="text" value="<%=Y%>"  class="input_box" style="width:60px"/></td>
                	</tr>
                </table>                  
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;(X、Y分别表示上边距与左边距)</td>
              </tr>  
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">元素大小：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                <%
                String W ="",H="";
                if(obj0.getStrSize()!=null && !obj0.getStrSize().trim().equals("")) 
                {
                	W = obj0.getStrSize().split(",")[0];
                	H = obj0.getStrSize().split(",")[1];
                }
                %>
                <table>
                	<tr>
                		<td> W：<input name="strSizeW" type="text" class="input_box" value="<%=W%>" style="width:60px"/></td>
                		<td style="width: 38px">&nbsp;</td>
                		<td> H：<input name="strSizeH" type="text" class="input_box" value="<%=H%>" style="width:60px"/></td>
                	</tr>
                </table>   
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;(W、H分别表示元素的长与宽)</td>
              </tr>    
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">文本内容：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                 <input name="strContent" type="text" value="<%=obj0.getStrContent()%>" class="input_box" size="30" />
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>          
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">元素字体：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                  <select name="strFontFamily" class="input_text" style="width:213px;">
                  <%if( obj0.getStrFontFamily()!=null && !obj0.getStrFontFamily().trim().equals(""))
                  {%>
                  	<option value="<%=obj0.getStrFontFamily()%>" ><%=obj0.getStrFontFamily()%></option>    
                  <%}%>              	
                  	<option value="" >请选择--</option>
              		<option value="宋体-PUA" >宋体-PUA</option>
					<option value="宋体" >宋 体</option>
					<option value="楷体_GB2312" >楷体_GB2312</option>
					<option value="黑体" >黑 体</option>
					<option value="仿宋_GB2312" >仿宋_GB2312</option>
					<option value="新宋体" >新宋体</option>
				  </select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr> 
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">字体颜色：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                  <select name="strFontColor" class="input_text" style="width:213px; ">
                  <%if( obj0.getStrFontColor()!=null && !obj0.getStrFontColor().trim().equals(""))
                  {%>
                  	<option value="<%=obj0.getStrFontColor()%>" ><%=obj0.getStrFontColorName()%></option>    
                  <%}%>              	
                  	<option value=" " >请选择--</option>
					<option value="white" >白 色</option>
					<option value="blue" >蓝 色</option>
					<option value="red" >红 色</option>
					<option value="green" >绿 色</option>
					<option value="gray" >灰 色</option>
					<option value="purple" >紫 色</option>
					<option value="black" >黑 色</option>
					<option value="yellow" >黄 色</option>
				  </select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>            
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">字体大小：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                <select name="intFontSize" class="input_text"  style="width:213px; ">
                 <%if( obj0.getIntFontSize()!=0)
                   {%>
                  	<option value="<%=obj0.getIntFontSize()%>" ><%=obj0.getIntFontSize()%></option>    
                  <%} %>              	
                  	<option value=" " >请选择--</option>                  	
                  	<%for(int i=0;i<4;i++)
	                { %>
	                <option value="<%=8+i%>"><%=8+i%></option>
	                <%} %>   
	                <%for(int i=0;i<9;i++)
	                { %>
	                <option value="<%=12+i*2%>"><%=12+i*2%></option>
	                <%} %>
	                 <option value="30">30</option>
	                 <option value="36">36</option>
	                 <option value="48">48</option>
	                 <option value="72">72</option>
                </select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>        
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">背 景 图：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strBgImage" type="file" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;
                <%
                if (obj0.getStrBgImage().trim().length() > 0) {
                %>
                  <img src="<%="template/" + obj0.getStrBgImage() %>" width=119 height=138 /><br>
                <%
                }
                %>  
                </td>
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