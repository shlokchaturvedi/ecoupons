<%@ page import="java.util.HashMap,com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.ecoupons.system.User,com.ejoysoft.conf.SysModule,java.util.Vector,com.ejoysoft.conf.Module,com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.ejoysoft.common.Globa,
				com.ejoysoft.ecoupons.business.Terminal"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@ include file="../include/jsp/head.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
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
function ReturnTerminals(){
    var terminalarray = document.frm.getElementsByTagName("input");
    var  retunterminal=" ";
    for(i=0;i<terminalarray.length;i++)
	 {
	 	if(terminalarray[i].type=="checkbox" && terminalarray[i].checked)
	 	{	 		
	        retunterminal += terminalarray[i].value+"，";
	 	}
	 }
    
	window.returnValue = retunterminal;
	window.close();	
}
</script>
</head>
<body >
<form name="frm" method="post" action="">
<div class="MainDiv">
<table width="520" border="0" cellpadding="0" cellspacing="0">
  <tr>
      <th ><div class="titlebt">
        <div align="left">选择终端</div>
      </div></th>
  </tr>
  <tr>
    <td height="0" >
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%">
        <tr><td height="10" align="left"></td></tr>
        <TR>
            <TD width="100%" height="222">
                <fieldset>
                <legend>终端（编号-地点）（按终端编号排序）</legend>
               <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
			  <tr>
                 <td colspan="4">  
                        &nbsp;		
                </td>
               </tr>
               <tr>
				<% 
				  String strId = ParamUtil.getString(request,"strId","");
				  String couponId = ParamUtil.getString(request,"couponId","");
				  System.out.println(couponId);
				  String flag = ParamUtil.getString(request,"flag","");
				  String strTerminalids ="";				  
				  try{
				  }catch(Exception e)
				  {
				  e.printStackTrace();
				  }
				  if(flag.equals("ad"))
				  {
					  if(!strId.equals(""))
					  {
						  Terminal objTerminal = new Terminal(globa);  
						  Terminal terminal = objTerminal.showAd(" where strid='"+strId+"'");
						  strTerminalids = terminal.getStrTerminalIds();
					  }
				  }
				  else if(flag.equals("coupon"))
				  {
				  	  if(!couponId.equals(""))	
					  {
					  	  Coupon objCoupon = new Coupon(globa);  
						  Coupon objCoupon1 = objCoupon.show(" where strid='"+couponId+"'");
						  if(objCoupon1!=null)
						  {
						  	strTerminalids = objCoupon1.getStrTerminalIds();				  	
						  }
					  }	
				  }	 
				  //System.out.println(couponId+"dddddddddddddddd"+strTerminalids);							
				  Terminal obj = new Terminal(globa,true);
				  String allterminal[]=obj.getAllTerminalNos();
				  int k=0;
			      for(int i=0;i<allterminal.length;i++)
                 { 
	               if(allterminal[i]!=null && allterminal[i].trim()!="")
	               {
	                    String strid = allterminal[i].split("-")[0];
	                    String strno = allterminal[i].split("-")[1];
	                    String strlocation = allterminal[i].split("-")[2];
                        String checked ="";
                        if(strTerminalids!=null && !strTerminalids.equals("") && strTerminalids.indexOf(strid)>=0)
                        {
                       		checked ="checked";
                        }
	                    if(k%3==0)
	                    {
               %>
               		</tr><tr><td colspan="3" height="20"></td></tr><tr>   
               <%} k++;%>
               		
                 <td width="33%">  
                       <input name="terminalname" <%=checked %> type="checkbox" value="<%=strno%>" /><%=strno%>-<%=strlocation%>			
		              	
                </td>
               <%   
               		}      
               }
                %>
               </tr>               
               <tr>
                 <td colspan="3">  
                       &nbsp;		
                </td>
               </tr>
                <tr>
                <td colspan="2" width="66%"></td>
                 <td >  
						<input name="bn" type="button" value="确定" style="width:90px" onclick="ReturnTerminals()" />					
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
