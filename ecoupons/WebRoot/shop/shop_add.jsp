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
<%--
<script type="text/javascript" src="../include/js/js/jquery.js"></script>
--%>
<script type="text/javascript" src="../include/js/jquery.js"></script>
<script type="text/javascript" src="../include/js/jquery.raty.min.js"></script>

<script language="javascript">
    function chkFrm() {
    	//判断几颗星
    	var m=0;
    	var a = document.getElementsByName('img');
    	 for(var k=0;k<5;k++){
    		if(a[k].src.indexOf("images/star-on.png")!=-1){ 
    			m++;
    		 }else{
    			m=m;
    		 }
    	}
    	
    	document.getElementById('star').value=m;
    	
    	
    	
        if(trim(frm.strBizName.value)=="") {
            alert("请输入商家名！！！")
            frm.strBizName.focus();
            return false;
        } else if(trim(frm.strTrade.value)=="") {
            alert("请输选择所属行业！！！")
            frm.strTrade.focus();
            return false;
        } 
        else {
        	if(trim(frm.intSort.value)!="")
            {
                 if(!isNumber(frm.intSort.value))
                   {
    	             alert("请输入整数！")
                     frm.intSort.focus();
    	             return false;
    	           }
             }
       		if(trim(frm.strPhone.value)!=""){      
	            var telphone = trim(frm.strPhone.value);
	            var tel=new Array(); 
	            tel = telphone.split("、"); 
	            var TelPhoneParn =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{7,8}$)|(^0{0,1}1(3|5|8)[0-9]{9}$)/; 
	            var reParn = new RegExp(TelPhoneParn);
	        	for(i=0;i<tel.length;i++)
	        	{
	        	 	if(!reParn.test(trim(tel[i])))
	        		{
	                  alert("请输入正确的联系电话！！！如0551-2342345或13200000001");
	                  frm.strPhone.focus();         
	        		  return false;
	        		}      		
	        	}
        	}
        	frm.submit();
        }
    }
    
    function dd(s){	
    	
    		  var m=0;
    	  var a = document.getElementsByName('img');
    	  
    	 if(a[s-1].src.indexOf("/images/star-off.png")!= -1){
    	    for(var i=0;i<=s-1;i++){
    	  	 a[i].src="../images/star-on.png";
		   }
    	 } else{
    		 for(var j=4;j>=s-1;j--){
    			 a[j].src="../images/star-off.png";
    		 }	 
    	 }
    }
</script>
</head>

<body>
<form name="frm" method="post" action="shop_act.jsp" enctype="multipart/form-data">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<input type="hidden" name="star" id="star" value=""/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">新增商家</div></td>
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
            <td class="left_txt">当前位置：业务管理 / 商家管理 / 新增商家</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以新增商家</span><br>
                      <span class="left_txt2">包括商家名，分部名称，所属行业，商家简介等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;商家</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">商  家  名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strBizName" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr>
              <td width="20%" height="30" align="right" class="left_txt2">评       价：</td>
               <td width="3%">&nbsp;</td>   
           <td ><img name="img" onclick="dd('1')" alt="1" src="../images/star-off.png"/>
            <img name="img" onclick="dd('2')" alt="2" src="../images/star-off.png"/>
            <img name="img" onclick="dd('3')" alt="3" src="../images/star-off.png"/>
         	<img name="img" onclick="dd('4')" alt="4" src="../images/star-off.png"/>
            <img name="img" onclick="dd('5')" alt="5" src="../images/star-off.png"/></td>  
            </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">分部名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strShopName" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">所属行业：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" > 
              
                 <select name="strTrade" class="forms_color1" style= "width:213px">
                 <%
                        //初始化
    					//SysPara  para=null;
   						SysPara para=new SysPara(globa);
                        ArrayList para1 = para.list("商家行业");
                        System.out.println(para1.size());
                        for (int i = 0; i < para1.size(); i++) {
                            SysPara d = (SysPara)para1.get(i);
                            if(d.getStrState()!=null && d.getStrState().equals("正常"))
                            {
                              out.print("<option value=" + d.getStrId() + ">");
                           
                            out.println("" + d.getStrName() + "</option>");
                            }
                       
                	%>
				 
                 <%
                 }
                 
                  %>
                   </select>
                 </td>
                <td width="45%" height="30" align="left" >
              &nbsp;</td>    
              </tr>
               <tr>
                 <td width="20%" height="30" align="right" class="left_txt2">是否推荐：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <input type="radio" name="intType" checked="checked" value="0"  class="input_box">否
                  <input type="radio" name="intType" value="1"  class="input_box"> 是
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">推荐排序：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="intSort" type="text" value="0" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">请输入整数，默认为0，代表没有排序！</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">地		址：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strAddr" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">联系电话：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strPhone" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">（手机号或固定电话，多个联系电话号码用顿号“、”隔开）</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">联  系  人：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strPerson" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">积分余额：</td>
                <td width="3%">&nbsp; </td>
                <td width="32%" height="30"><input name="intPoint" type="text" class="input_box" size="30" value="0" readonly="true" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr>
                <td height="30" align="right" class="left_txt2">小图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strSmallImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("SHOP_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("SHOP_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                <td height="30" align="right" class="left_txt2">大图片：</td>
                <td>&nbsp;</td>
                <td height="30"><input name="strLargeImg" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">（大小：<%=application.getAttribute("SHOP_LARGE_IMG_WIDTH") %>*<%=application.getAttribute("SHOP_LARGE_IMG_HEIGHT") %>px，用于前台详细显示）</td>
              </tr>
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">商家简介：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><textArea name="strIntro" cols="33" rows="5" ></textArea></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
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