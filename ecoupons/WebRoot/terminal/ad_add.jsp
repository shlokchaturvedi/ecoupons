<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Shop,
				 java.util.Vector,
				 com.ejoysoft.ecoupons.system.SysPara,
				 java.util.ArrayList,
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
<script src="../include/js/chkFrm.js"></script>
<script src="../include/DatePicker/WdatePicker.js"></script>
<script language="javascript">
    function chkFrm() {
       if(trim(frm.strName.value)=="") {
            alert("请输入广告名称！！！")
            frm.strName.focus();
            return false;
        }else if(trim(frm.strContent.value)=="") {
            alert("请选择广告文件或填写内容！！！")
            frm.strContent.focus();
            return false;
        }else if(trim(frm.strTerminals.value)=="") {
            alert("请选择投放终端！！！")
            frm.strTerminals.focus();
            return false;
        }else if(trim(frm.dtStartTime.value)=="") {
            alert("请输入广告开始时间！！！")
            frm.dtStartTime.focus();
            return false;
        }else if(trim(frm.dtEndTime.value)=="") {
            alert("请输入广告结束时间！！！")
            frm.dtEndTime.focus();
            return false;
        }
        else {
	         if(trim(frm.dtStartTime.value)!=""){        
	            var dtStartTime = trim(frm.dtStartTime.value);
	            var resParn =/(^[0-1]{1}[0-9]{1}\:[0-5]{1}[0-9]{1}$)|(^2[0-3]{1}\:[0-5]{1}[0-9]{1}$)/;
	            var reParn = new RegExp(resParn);
	       	 	if(!reParn.test(dtStartTime))
	       		{
	                 alert(dtStartTime+"请输入正确开始时间！！！如08:25");
	                 frm.dtStartTime.focus();         
	       		     return false;
	       		}    
         	 }
         	 if(trim(frm.dtEndTime.value)!=""){        
	            var dtEndTime = trim(frm.dtEndTime.value);
	            var resParn =/(^[0-1]{1}[0-9]{1}\:[0-5]{1}[0-9]{1}$)|(^2[0-3]{1}\:[0-5]{1}[0-9]{1}$)/;
	            var reParn = new RegExp(resParn);
	       	 	if(!reParn.test(dtEndTime))
	       		{
	                 alert(dtEndTime+"请输入正确结束时间！！！如08:25");
	                 frm.dtEndTime.focus();         
	       		     return false;
	       		}    
         	 }
         	var startime =  dtStartTime.split(":");
         	var endtime = dtEndTime.split(":");
         	if(trim(startime[0]) > trim(endtime[0]))
         	{
                 alert("起始时间不能大于结束时间！！！");
                 frm.dtEndTime.focus();         
       		     return false;
       		}    
         	if((trim(startime[0])==trim(endtime[0])) && (trim(startime[1]) >= trim(endtime[1])))
         	{
                 alert("起始时间不能大于结束时间！！！");
                 frm.dtEndTime.focus();         
       		     return false;
       		}    
         	frm.submit();
        }
    }
    
function addPicRow(){ 	 
	//var f = document.forms["frm"];
	var input = document.createElement("input");
	input.setAttribute("type","file");
	input.setAttribute("style","width:213");
	input.setAttribute("size","30");
	input.setAttribute("name","strContent");
	//f.appendChild(input);
	var br = document.createElement('br');
	//f.appendChild(br);		
	document.getElementById("strContent").parentNode.appendChild(br);
	document.getElementById("strContent").parentNode.appendChild(input);
   }
	 
<!-- 显示终端列表-->
function addTerminals()
{
    var terminals = window.showModalDialog("terminals_select.jsp?random="+ Math.random(), "选择投放终端", "dialogWidth=550px;dialogHeight:600px;dialogTop:300px;dialogLeft:450px;scrollbars=yes;status=yes;"); //写成一行 
	document.getElementById("strTerminals").value=terminals.substring(0,terminals.length-1);
		  
}  
function showTextContent(){
    var array = document.frm.getElementsByTagName("input");
    for(i=0;i<array.length;i++)
	 {
	 	if(array[i].type=="radio" && array[i].id=="type3" )
	 	{	 		
            document.getElementById("strContentid").innerHTML="<input type='text' name='strContent' id='strContent'  class='input_box' size='30'>(走马灯内容)";	      
	 	} 
	 }
}
function showFileContent(){
    var array = document.frm.getElementsByTagName("input");
    for(i=0;i<array.length;i++)
	 {
	 	if(array[i].type=="radio" && array[i].id=="type1")
	 	{	 		
            document.getElementById("strContentid").innerHTML="<input type='file' style='width:213' name='strContent' id='strContent' size='30'>(视频文件)";	      
	 	} 
	 }
    }
function showPicContent(){
    var array = document.frm.getElementsByTagName("input");
    for(i=0;i<array.length;i++)
	 {
	 	if(array[i].type=="radio" && array[i].id=="type2" )
	 	{	 		
            document.getElementById("strContentid").innerHTML="<input type='file' style='width:213' name='strContent' id='strContent' size='30'>"+
           													"<input type='button' value='+'  onclick='addPicRow();'/>(图片文件)";	      
	 	} 
	 }
}
</script>
</head>

<body>
<form name="frm" method="post" action="ad_act.jsp" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif">
    <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">新增广告</div></td>
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
            <td class="left_txt">当前位置：业务管理 / 终端管理 / 新增广告</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以新增广告</span><br>
                      <span class="left_txt2">包括广告名称、投放终端、内容、开始结束时间等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;广告
                </td>
              </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">广告名称：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">广告类型：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
					 <input type="radio" name="intType" id="type1" value="1" checked onclick="showFileContent()" class="input_box" />视频
					 <input type="radio" name="intType" id="type2" value="2" onclick="showPicContent()" class="input_box">图片
                     <input type="radio" name="intType" id="type3" value="3" onclick="showTextContent()" class="input_box">走马灯
				</td>
				</tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">广告内容：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
	              <span id="strContentid"><input type='file' style='width=213' name='strContent' id='strContent' >(视频文件)</span>
				</td>
                <td width="45%" height="30" class="left_txt"></td> 
              </tr>
              
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">投放终端：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
	                <input type="text" readonly name="strTerminals" class="input_box" size="30">
					<input type="button" name="Submit" class=" " value="..." onclick="addTerminals()">
				</td>
                <td width="45%" height="30" class="left_txt"><br></td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">开始播放时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" ><input readonly name="dtStartTime" type="text" class="input_box" onclick="WdatePicker({dateFmt:'HH:mm'});" value="" size="30" /></td>
                <td width="45%" height="30" align="left" >&nbsp;</td>    
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">结束播放时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="dtEndTime" type="text" readonly onclick="WdatePicker({dateFmt:'HH:mm'});" class="input_box" size="30"/></td>
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