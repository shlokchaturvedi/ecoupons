<%@ page import="java.util.HashMap,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.ecoupons.system.User,com.ejoysoft.conf.SysModule,java.util.Vector,com.ejoysoft.conf.Module,com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.ejoysoft.common.Globa,
				com.ejoysoft.ecoupons.business.Shop"%>
<%@ include file="../include/jsp/head.jsp"%>

<html>
<head>
<meta http-equiv="Vache-Control" content="no-cache" />
<!--
<meta http-equiv="pragram" content="no-cache; charset=gb2312" />
<meta http-equiv="expires" content="0; charset=gb2312" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
-->
<title><%=Globa.APP_TITLE%></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
}
body,td,th {
	font-size: 9pt;
	color: #111111;
}
-->
</style>
<link href="../images/skin.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../include/date/popc.js"></script>
<script src="../include/js/chkFrm.js"></script>
<script type="text/javascript">
function ReturnShops(){
    var shoparray = document.frm.getElementsByTagName("input");
    var  retunshop=" ";
    for(i=0;i<shoparray.length;i++)
	 {
	 	if(shoparray[i].type=="checkbox" && shoparray[i].checked)
	 	{	 		
	        retunshop += shoparray[i].value+"，";
	 	}
	 }
    
	window.returnValue = retunshop;
	window.close();	
}
</script>
</head>
<body onkeydown="if(event.KeyCode==116){reload.click()}" >
<form name="frm" method="post" action="">
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>">
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th ><div class="titlebt">
        <div align="left">选择商家</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" >
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="5" align="left"></td></tr>
        <TR>
            <TD width="100%" height="222">
                <fieldset>
                <legend>商家列表</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
			  <tr>
                 <td width="20%">  
                        &nbsp;		
                </td>
                 <td width="20%">  
                        &nbsp;		
                </td>
                 <td width="20%">  
                        &nbsp;		
                </td>
                 <td width="20%">  
                        &nbsp;		
                </td>
                 <td>  
                        &nbsp;		
                </td>
               </tr>
				<% 
				  Shop obj = new Shop(globa,true);
				  String allshopname[]=obj.getAllShopNames();
				  String allbizname[]=obj.getStrBizNames();
					System.out.println(allbizname.length );
			      for(int i=0;i<allbizname.length;i++)
               { 
	               if(allbizname[i]!=null ||allbizname[i].trim()!="")
	               {
	                    
               %>
               <tr>
                 <td>  
                        |—<%=allbizname[i]%>		
                </td>
               </tr>
               <tr>
               <%      for(int j=0;j<allshopname.length;j++)
	                    {
	                       if((allshopname[j].split("-")[0]).trim().equals(allbizname[i]))
	                       {
	           %>
		               
		                 <td >         
							&nbsp;<input name="shopname" type="checkbox" value="<%=allshopname[j] %>" /><%=allshopname[j].split("-")[1]%>			
		                </td>
               <% 
              			  }
               			}
               		%>
		        </tr>
               <tr>
                 <td>  
                       &nbsp;		
                </td>
               </tr>
               <%   
               		}      
               }
                %>
                <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                 <td >  
						<input name="bn" type="button" value="确定" style="width:90px" onclick="ReturnShops()" />					
                </td>
               </tr>
           </table>         
        </fieldset>
            </td>
          </tr>
        </table>
          </td>
          </tr>
             </table>    
</div>
</form>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>
