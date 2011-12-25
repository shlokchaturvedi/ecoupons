<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.common.Format,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.SysUserUnit,java.util.Vector,com.ejoysoft.ecoupons.system.Unit,
                 com.ejoysoft.ecoupons.business.Shop,java.util.HashMap" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
	String strId = ParamUtil.getString(request,"strId","");
	 String strUserId=ParamUtil.getString(request,"strUserId","");
	if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    String where="where strId='"+strId+"'";
    User obj=new User(globa,false);
    User obj0=obj.show(where);
    if(obj0==null){
        globa.closeCon();
        throw new IdObjectException("请求处理的信息id='"+strId+"'对象为空！","请检查该信息的相关信息");
    }
    String  strShopid=ParamUtil.getString(globa.request,"strShopid",Format.forbidNull(obj0.getStrShopid()));
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
<script language="javascript">
    function chkFrm() {
        if(trim(frm.strUserId.value)=="") {
            alert("请输入用户名！！！")
            frm.strUserId.focus();
            return false;
        }
        if(trim(frm.strName.value)=="") {
            alert("请输入姓名！！！")
            frm.strName.focus();
            return false;
        } 
        if(trim(frm.arryUnitId.value)=="") {
            alert("请输选择所属机构！！！")
            frm.arryUnitId.focus();
            return false;
        } 
        if (trim(frm.strCssType.value)=="商家"){
            if (trim(frm.strShopid.value)==""){
            alert("请输选择所属商家！！！")
            frm.strShopid.focus();
        	return false;
        	}
        }else{
            if (trim(frm.strShopid.value)!=""){
             alert("非商家用户不得选择所属商家！！！")
             frm.strShopid.value="";
        	 return false;
        }   
        }      
       	frm.submit();
    }
</script>
</head>

<body>
<form name="frm" method="post" action="user_act.jsp" >
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.UPDATE_STR%>">
<input type="hidden" name=strId value="<%=obj0.getStrId()%>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">编辑用户</div></td>
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
            <td class="left_txt">当前位置：系统管理 / 用户管理 / 编辑用户</td>
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
                <td width="94%" valign="top"><span class="left_txt2">在这里，您可以编辑用户信息</span><br>
                      <span class="left_txt2">包括用户名，姓名，职务，部门等属性</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
              <tr>
                <td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;用户</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              
             <tr>
                <td width="20%" height="30" align="right" class="left_txt2">用 户 名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strUserId" type="text" class="input_box" size="30" value="<%=obj0.getStrUserId()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td>
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">姓　　名：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strName" type="text" class="input_box" size="30"  value="<%=obj0.getStrName()%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
             <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">性　　别：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                  <input type="radio" name="strSex" value="男" <%if(obj0.getStrSex().equals("男")) out.print("checked");%> class="input_box">
                  男
                  <input type="radio" name="strSex" value="女" <%if(obj0.getStrSex().equals("女")) out.print("checked");%> class="input_box">
                  女
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              
                
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">所属机构：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
              <select name="arryUnitId" class="forms_color1">
                    <%
                        HashMap unitUsers = obj.unitUser("strUnitId", " WHERE strUserId='"+strUserId+"'");
                        Vector userGroupTree = SysUserUnit.getAllUnitManaged(globa.unitId[0]);
                        String strSelect="";
                         for (int i = 0; i < userGroupTree.size(); i++) {
                            Unit ugNode = (Unit)userGroupTree.get(i);
                            strSelect=(unitUsers.containsKey(ugNode.getStrId()) ? " selected" : "");
                           // System.out.println(ugNode.getStrUnitName()+"=="+strSelect);
                            out.print("<option value=" + ugNode.getStrId() + " "+strSelect+">");
                            for (int j = 1; j < ugNode.getIntLevel(); j++) {
                                out.print("&nbsp;");
                            }
                            out.println("├" + ugNode.getStrUnitName() + "</option>");
                        }
                %>
                  </select> 
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">职　　务：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strDuty" type="text" class="input_box" size="30" value="<%=Format.forbidNull(obj0.getStrDuty())%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">岗　　位：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strStation" type="text" class="input_box" size="30" value="<%=Format.forbidNull(obj0.getStrStation())%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">用户类型：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">
                 <select name="strCssType" class="input_box">
                 <%
                  //从全局变量中读取用户类型
                  String userType = (String)application.getAttribute("USER_TYPE");
                  String  strCssType=ParamUtil.getString(globa.request,"strCssType",obj0.getStrCssType());
                  String[] userTypes = userType.split(",");
                  for (int i = 0; i < userTypes.length; i++) {
              %>
                <option value="<%=userTypes[i]%>" <%if(strCssType.equals(userTypes[i])) out.print("selected");%>><%=userTypes[i]%></option>
              <%
                  }
              %> 
                 </select>                 
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">所属商家：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30">                
               <select name="strShopid" class="input_box">
                   <option value="">请选择</option>
                  
                    <%
                  //从全局变量中读取参数类型
                   Shop objShop=new Shop(globa);
                     Shop  para0=null;
                   Vector vctobj=objShop.allShop("");                  
                  for (int i = 0; i < vctobj.size(); i++) {
                      para0=(Shop)vctobj.get(i);                     
              %>
               
                <option value="<%=para0.getStrId()%>" <%if(strShopid.equals(para0.getStrId())) out.print("selected");%>><%=para0.getStrBizName()+para0.getStrShopName()%></option>
              <%
                  }
              %>
                 </select>  
                </td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
              
              
              <tr >
                 <td width="20%" height="30" align="right" class="left_txt2">手　　机：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strMobile" type="text" class="input_box" size="30" value="<%=Format.forbidNull(obj0.getStrMobile())%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
               <tr bgcolor="#f2f2f2">
                 <td width="20%" height="30" align="right" class="left_txt2">电子邮箱：</td>
                <td width="3%">&nbsp;</td>
                <td width="32%" height="30"><input name="strEmail" type="text" class="input_box" size="30" value="<%=Format.forbidNull(obj0.getStrEmail())%>"/></td>
                <td width="45%" height="30" class="left_txt">&nbsp;</td> 
              </tr>
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