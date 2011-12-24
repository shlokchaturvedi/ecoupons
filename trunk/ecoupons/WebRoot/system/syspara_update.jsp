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
<link rel="stylesheet" rev="stylesheet" href="../css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="../css/css1.css">
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
.style1 {font-size: 12px}
-->
</style>
<script src="../include/js/chkFrm.js"></script>
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
<form name="frm" method="post" action="sysParaAct.jsp">
<input type="hidden" name=strUrl  value="<%=strUrl%>">
 <input type="hidden" name=<%=Constants.ACTION_TYPE%>  value=<%=Constants.UPDATE_STR%>>
 <input type="hidden" name=strId  value=<%=para0.getStrId()%>>
 <input type="hidden" name=strOldType  value="<%=para0.getStrType()%>">
 <input type="hidden" name=intOldSort  value="<%=para0.getIntSort()%>">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><div align="center" class="style1">
        <div align="left">参数管理</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" class="CPanel" valign="top">
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="0" align="left"></td></tr>
        <TR>
            <TD width="100%" height="122" valign="top">
                <fieldset>
                <legend>修改参数</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
               
              <tr class="td_listbg_1">
                <td width="10%" align="right" class="time1">参数名称：</td>
                <td width="90%" class="time1" height="25"> &nbsp;
                  <%=para0.getStrName()%>
                </td>
              </tr>
              <tr class="td_listbg_2">
                <td width="10%" align="right" class="time1" height="25">参数值：</td>
                <td width="90%" class="time1"> &nbsp;
                  <input type="text" name="strValue" size="30" class="forms_color1" value="<%=para0.getStrValue()%>">
                </td>
              </tr>
              
              <tr class="td_listbg_1">
                  <td width="10%" align="right" class="time1" height="25">参数注释：</td>
                
                <td width="90%">
						<textarea name="strState" cols="80" rows="4" id="textarea" class="text_ipt2"><%=para0.getStrState()%></textarea>
					</td>
              </tr>
             </table>
                      <br/>
              </fieldset>
            </TD>
        </TR>
        </TABLE>
        </td>
  </tr>
        <TR>
            <TD colspan="2" align="center" height="28">
            <input type="button" name="b_submit" value="确 定" class="button" onclick="chkFrm()" style="cursor:hand"/>
            <input type="button" name="b_reset"  value="重 置" class="button" onclick="frm.reset();" style="cursor:hand"/>
            <input type="button" name="b_back"   value="返 回" class="button" onClick="location.href='<%=strUrl%>'" style="cursor:hand"/>
            </TD>　　
        </TR>
    </TABLE>
    
</div>
</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>
