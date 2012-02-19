<%@ page import="com.ejoysoft.ecoupons.system.SysPara,
                 com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
     String strId=ParamUtil.getString(request,"strId","");
       if(strId.equals(""))
       throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    SysPara para=new SysPara(globa,false);
       String where="where strId='"+strId+"'";
       SysPara  para0=para.show(where);
    if(para0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
    }
         int intMaxSort=1;
     String  strType=ParamUtil.getString(globa.request,"strType",para0.getStrType());
     
         intMaxSort=para.netOrder(strType);
    
     //返回页面的url
String strUrl="sysParaList.jsp";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=globa.APP_TITLE%></title>
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
<script language="javascript">
<!--
function chkFrm(){
         if(frm.strName.value=="")
        {
            alert("\请输入参数名称！！！")
            frm.strName.focus();
            return false;
        }else if (frm.strValue.value==""){
            alert("\请输入参数值！！！")
            frm.strValue.focus();
            return false;
        }
        
         else  frm.submit();
    }
   
-->
</script>
</head>

<body class="ContentBody">
<form name="frm" method="post" action="syspara_act.jsp">
<input type="hidden" name=strUrl  value="<%=strUrl%>">
 <input type="hidden" name=<%=Constants.ACTION_TYPE%>  value=<%=Constants.UPDATE_STR%>>
 <input type="hidden" name=strId  value=<%=para0.getStrId()%>>
 <input type="hidden" name=strOldType  value="<%=para0.getStrType()%>">
 <input type="hidden" name=intOldSort  value="<%=para0.getIntSort()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">修改参数</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 数据字典管理 / 修改参数</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以修改参数</span><br>
                      <span class="left_txt2">包括参数名，参数值等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;参数</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">参数类型：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <select name="strType" onChange="chgType(this.value,frm.strId.value)" class="input_box">
                    <%
                  //从全局变量中读取参数类型
                  String paramType = (String)application.getAttribute("PARAM_TYPE");
                  String[] paramTypes = paramType.split(",");
                  for (int i = 0; i < paramTypes.length; i++) {
              %>
                <option value="<%=paramTypes[i]%>" <%if(strType.equals(paramTypes[i])) out.print("selected");%>><%=paramTypes[i]%></option>
              <%
                  }
              %>
                 </select>  </td>
                  <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              
            
              <tr>
                <td width="20%" height="30" align="right" class="left_txt2">参数名称：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30"  value="<%=para0.getStrName()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">参数值：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strValue" type="text" class="input_box" size="30" value="<%=para0.getStrValue()%>" /></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
            <tr>
                <td width="20%" height="30" align="right" class="left_txt2">序　　号：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <select name="intSort"  class="forms_color1">
                 <%
                       for (int i=intMaxSort;i>=1;i--){
                   %>
                    <option value="<%=i%>" <%if(i==para0.getIntSort()) out.print("selected");%>><%=i%></option>
                    <%}%>
                      </select>
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
            
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">状　　态：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                <input type="radio" name="strState" value="正常" <%=(para0.getStrState().equals("正常")?"checked":"")%> class="input_box">
                  正常
                  <input type="radio" name="strState" value="禁用"  <%=(para0.getStrState().equals("禁用")?"checked":"")%> class="input_box">
                  禁用
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
