<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
        		 java.util.Vector,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.ecoupons.system.SysPara,
				 java.util.ArrayList,
				 com.ejoysoft.common.exception.IdObjectException" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
try{
   	SysPara para=new SysPara(globa);
	String strId = ParamUtil.getString(request,"strId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    Terminal obj=new Terminal(globa,false);
    Terminal obj0=obj.show(where);
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
        if(trim(frm.strNo.value)=="") {
            alert("请输入终端编号！！！")
            frm.strNo.focus();
            return false;
        } else if(trim(frm.strType.value)=="") {
            alert("请选择规格型号！！！")
            frm.strType.focus();
            return false;
        } else if(trim(frm.strProducer.value)=="") {
            alert("请选择生产厂家！！！")
            frm.strProducer.focus();
            return false;
        }else {
	         if(trim(frm.strResolution.value)!=""){        
	            var strResolution = trim(frm.strResolution.value);
	            var resParn =/(^[0-9]{1,9}\*[0-9]{1,9}$)/; 
	            var reParn = new RegExp(resParn);
	       	 	if(!reParn.test(strResolution))
	       		{
	                 alert(strResolution+"请输入正确的分辨率！！！如1024*1024");
	                 frm.strResolution.focus();         
	       		     return false;
	       		}       	
	          }
	          if(trim(frm.strResolution2.value)!=""){        
	            var strResolution = trim(frm.strResolution2.value);
	            var resParn =/(^[0-9]{1,9}\*[0-9]{1,9}$)/; 
	            var reParn = new RegExp(resParn);
	       	 	if(!reParn.test(strResolution))
	       		{
	                 alert(strResolution+"请输入正确的分辨率！！！如1024*1024");
	                 frm.strResolution2.focus();         
	       		     return false;
	       		}       	
	         }
			 if(trim(frm.strResolution3.value)!=""){        
	            var strResolution = trim(frm.strResolution3.value);
	            var resParn =/(^[0-9]{1,9}\*[0-9]{1,9}$)/; 
	            var reParn = new RegExp(resParn);
	       	 	if(!reParn.test(strResolution))
	       		{
	                 alert(strResolution+"请输入正确的分辨率！！！如1024*1024");
	                 frm.strResolution3.focus();         
	       		     return false;
	       		}
	       	}       	
        	frm.submit();
        }
    }
   function openwin() {  
			 var shops=window.showModalDialog("shops_select.jsp?strid="+<%=strId%>+"&random="+ Math.random(), "选择临近商家", "dialogWidth=520px;dialogHeight:600px;dialogTop:300px;dialogLeft:450px;scrollbars=yes;status=yes;"); //写成一行 
		  	document.getElementById("strAroundShops").value=shops.substring(0,shops.length-1);
		  	}
</script>
</head>

<body>
<form name="frm" method="post" action="terminal_act.jsp" enctype="multipart/form-data" > 
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name="strId" value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">更新终端</div></td>
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
            <td class="left_txt">当前位置：业务管理 / 终端管理 / 更新终端</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以更新终端</span><br>
                      <span class="left_txt2">包括终端编号、地点、厂家、规格型号等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;终端</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">终端编号：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strNo" value="<%=obj0.getStrNo()%>" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">启用时间：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="dtActiveTime" value="<%=(obj0.getDtActiveTime()).substring(0,16)%>"  type="text" readonly class="input_box" size="30" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                <td width="20%" height="30" align="right" class="left_txt2">生产厂家：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
				 <select name="strProducer" class="forms_color1" style= "width:213px">
                   <%
                         ArrayList para1 = para.list("券打机厂家");
                        for (int i = 0; i < para1.size(); i++) {
                            SysPara d = (SysPara)para1.get(i);
                            if(d.getStrState()!=null &&d.getStrState().equals("正常"))
                            {
	                            if(d.getStrId().equals(obj0.getStrProducer()))
	                            {
	                            	out.print("<option value=" + d.getStrId() + " selected=selected >");                         
	                            }
	                            else{
	                              out.print("<option value=" + d.getStrId() + ">");
	                            }
	                            out.println("" + d.getStrName() + "</option>");
	                         }
                       
                	%>
				 
                 <%
                 }
                 
                  %>
                   </select>
				</td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                <td width="20%" height="30" align="right" class="left_txt2">规格型号：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30">
                <select name="strType" class="forms_color1" style= "width:213px">
                 <%
                        //初始化
    					//SysPara  para=null;
                        ArrayList para3 = para.list("券打机规格型号");
                        for (int i = 0; i < para3.size(); i++) {
                            SysPara d = (SysPara)para3.get(i);
                            if(d.getStrId().equals(obj0.getStrType()))
                            {
                            	out.print("<option value=" + d.getStrId() + " selected=selected >");                         
                            }
                            else{
                              out.print("<option value=" + d.getStrId() + ">");
                            }                        
                            out.println("" + d.getStrName() + "</option>");
                       }                 
                 %>
                   </select></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">地            点：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" ><input  name="strLocation" value="<%=obj0.getStrLocation()%>" type="text" class="input_box" value="" size="30" /></td>
                <td width="45%" height="30" align="left" >&nbsp;</td>    
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">临近商家：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strAroundShops" value="<%=obj0.getStrAroundShops()%>" type="text" readonly class="input_box" size="30"/><input type="button" value="..." onclick="openwin()" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr> <tr>
                <td width="20%"  height="30" align="right" class="left_txt2">图            片：</td>
                <td width="3%">&nbsp; </td>
                <td width="32%" height="30"><input name="strImage" type="file" class="input_box" size="30" /></td>
                <td height="30" class="left_txt">
                <%
                if (obj0.getStrImage()!=null && obj0.getStrImage().length() > 0) {
                %>
                  <img src="<%="images/" + obj0.getStrImage() %>" width=119 height=138 /><br>
                <%
                }
                %>  
                   	（大小：<%=application.getAttribute("SHOP_SMALL_IMG_WIDTH") %>*<%=application.getAttribute("SHOP_SMALL_IMG_HEIGHT") %>px，用于前台列表显示）
                                 
                </td>
               </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">主屏分辨率：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strResolution" value="<%=obj0.getStrResolution()%>" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">（分辨率格式如：1024*1024）</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">主屏分辨率2：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strResolution2" value="<%=obj0.getStrResolution2()%>" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">主屏分辨率3：</td>
                <td width="3%" height="30">&nbsp;</td>
                <td width="32%" height="30"><input name="strResolution3" value="<%=obj0.getStrResolution3()%>" type="text" class="input_box" size="30" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>          
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">终端状态：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getStateString()%></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr> 
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">最近更新时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getDtRefreshTime()%></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr>         
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">创  建  人：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getStrCreator()%></td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
              </tr> 
          	  <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">创建时间：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30" class="left_txt"><%=obj0.getDtCreateTime()%>
                </td>
                <td width="45%" height="30" class="left_txt" > &nbsp;</td> 
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
<%
}catch(Exception e){e.printStackTrace();}
%>
<%@ include file="../include/jsp/footer.jsp"%>