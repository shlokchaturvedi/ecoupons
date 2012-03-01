<%@ page import="java.util.HashMap,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.ecoupons.system.User,com.ejoysoft.conf.SysModule,java.util.Vector,com.ejoysoft.conf.Module,com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.ejoysoft.common.Globa,
				com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Terminal"%>
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
    if(retunshop==null)
    {
    	retunshop=" ";
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
<table width=480 border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th ><div class="titlebt">
        <div align="left">选择商家</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" >
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr><td height="5" align="left"></td></tr>
        <TR>
            <TD width="100%" height="222">
                <fieldset>
                <legend>商家列表（按拼音首字母排序）</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
			  <tr>
                 <td colspan="5">  
                        &nbsp;		
                </td>
               </tr>
				<% 
				  String strid = ParamUtil.getString(request,"strid","");
				  String strshopnames ="", strshopids ="";
				  if(!strid.equals(""))
				  {
					  Terminal objTerminal = new Terminal(globa);  
					  Terminal terminal = objTerminal.show(" where strid='"+strid+"'");
					  strshopids = terminal.getStrAroundShopIds();
					  strshopnames = objTerminal.getArroundShopNames(strshopids);
				  }				 
				  Shop obj = new Shop(globa,true);
				  String allshopname[]=obj.getAllShopIdsAndNames();
				  String allbizname[]=obj.getStrBizNames();		
			      for(int i=0;i<allbizname.length;i++)
                  { 
	               if(allbizname[i]!=null ||allbizname[i].trim()!="")
	               {
	                    
               %>
               <tr>
                 <td>  
                        *<font style="font-family:黑体  ; font-size: 16px;color: #003333;">&nbsp;&nbsp;<%=allbizname[i]%>	</font>	
                </td>
               </tr>
               <tr>
		           <td colspan="5">
		           <table width="100%">
		               <tr> 
               <%	    int k=0;     
               		    for(int j=0;j<allshopname.length;j++)
	                    {
	                       String[] shopStrings =(allshopname[j].split(",")[1]).split("-");
	                       String checked ="";
	                       if(strshopids!=null && !strshopids.equals("") && strshopids.indexOf(allshopname[j].split(",")[0])>=0)
	                       {
	                       		checked ="checked";
	                       }
	                       if(shopStrings.length>=2 && shopStrings[0].trim().equals(allbizname[i]))
	                       { 
	                         if(k%3==0)
	              			 {
	              			 %>
		                 </tr><tr>
		              
	               <% 		 }	   
	              			 k++;                       
	           %>
		                 <td width="33%">		                       
						 <input name="shopname" type="checkbox" <%=checked%> value="<%=allshopname[j].split(",")[1]%>" /><font style="font-family:仿宋GB2312 ; font-size: 12px;color: #444444;"><%=shopStrings[1]%></font>		
						 </td>
		              
	               <% 		 
              			  }else if(shopStrings.length==1 && shopStrings[0].trim().equals(allbizname[i]))
	                       { 
	                         if(k%3==0)
	              			 {
	              			 %>
		                 </tr><tr>
		              
	               <% 		 }	   
	              			 k++;                       
	           %>
		                 <td width="33%">		                       
						 <input name="shopname" type="checkbox" <%=checked%> value="<%=allshopname[j].split(",")[1]%>" /><font style="font-family:仿宋GB2312 ; font-size: 12px;color: #444444;"><%=allshopname[j].split(",")[1]%></font>		
						 </td>
		              
	               <% 		 
              			  }
              			  
               			}
               		%>
					  </tr>	
					  </table> 
               		 </td>
		        </tr>
               <tr>
                 <td colspan="5">  
                      <font style="color: #888888;">...........................................................................</font>		
                </td>
               </tr>
                <tr>
                <td colspan="5" height="10"></td>
                </tr>
               <%   
               		}      
               }
                %>
                <tr>
                <td colspan="4">&nbsp;</td>
                 <td width="50%">  
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
