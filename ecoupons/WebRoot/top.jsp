<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.common.Constants" %>
<%@page import="com.ejoysoft.ecoupons.business.Terminal,java.util.*"%>
<%@include file="include/jsp/head.jsp"%>

<html>
<head>
<title><%=application.getAttribute("APP_TITLE")%></title>
   
<script language=JavaScript>
function logout(){
	//<%=application.getServletContextName()%>
	if (confirm("您确定要退出吗？"))
		top.location = "<%=application.getServletContextName()%>/Auth?actiontype=<%=Constants.LOGOFF%>";
	return false;
}
</script>
<style>
a:link,a:hover,a:active,a:visited{
	color: #F8F9FAs;
	text-decoration: none;
}
.admin_txt {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #FFFFFF;
	text-decoration: none;
	height: 38px;
	width: 100%;
	position: 固定;
	line-height: 38px;
}
.admin_topbg {
	background-image: url(top-right.gif);
	background-repeat: repeat-x;
}
.body {
	background-color:  #F8F9FAs;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
}
</style>

<meta http-equiv="Refresh" content="60">
<meta http-equiv=Content-Type content=text/html;charset=gb2312>
<script language=JavaScript1.2>
function showsubmenu(sid) {
	var whichEl = eval("submenu" + sid);
	var menuTitle = eval("menuTitle" + sid);
	if (whichEl.style.display == "none"){
		eval("submenu" + sid + ".style.display=\"\";");
	}else{
		eval("submenu" + sid + ".style.display=\"none\";");
	}
}
function openwin(strId,strNum,intPaper) {  
	if(confirm("终端"+strNum+"已打印"+intPaper+"张优惠券\n确定终端"+strNum+"已添加打印纸!"))
    {
		window.location.href="printpaper_act.jsp?strId="+strId;
	      
    }
 	
 	}


function realSysTime(clock){
        var now=new Date();            //创建Date对象
        var year=now.getFullYear();    //获取年份
        var month=now.getMonth();    //获取月份
        var date=now.getDate();        //获取日期
        var day=now.getDay();        //获取星期
        var hour=now.getHours();    //获取小时
        var minu=now.getMinutes();    //获取分钟
        var sec=now.getSeconds();    //获取秒钟
        month=month+1;
        var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var week=arr_week[day];        //获取中文的星期
        var time=year+"年"+month+"月"+date+"日 "+week+" "+hour+":"+minu+":"+sec;    //组合系统时间
        clock.innerHTML=time;    //显示系统时间
    }
   window.onload=function(){
        window.setInterval("realSysTime(clock)",1000);    //实时获取并显示系统时间
    }


</script>
</head>
<body leftmargin="0" topmargin="0">
<form action="">
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB" class="admin_topbg">
  <tr>
    <td width="61%" height="64" background="images/top-right.gif" bgcolor="#EEF2FB"><img src="images/logo.gif" width="262" height="64"></td>
    <td width="39%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/top-right.gif">
      <tr>
      
        <td width="74%" height="38" class="admin_txt" align="right">
        <span id=""></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <a href="#" onclick="top.leftFrame.location='left.jsp?root=日常管理';top.main.location='right.jsp';">日常管理</a> | 
          <a href="#" onclick="top.leftFrame.location='left.jsp?root=系统管理';top.main.location='right.jsp';">系统管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td width="22%"><a href="#" target="_self" onClick="logout();"><img src="images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
     
      
        <td height="19" colspan="3">

 <marquee  behavior="scroll" scrollamount="2" onmouseover="this.stop();" onmouseout="this.start();" ><b><font color="#FF0000">
<%
//System.out.println("application.getServletContextName()::"+application.getServletContextName());
Vector<Terminal> vctTerminals=new Vector<Terminal>();
StringBuffer sbTip=new StringBuffer("提醒:");
	if("管理员".equals(globa.userSession.getStrCssType()))
	{
		Terminal terminal=new Terminal(globa);
		vctTerminals=terminal.list("where intpaperstate>0 ",0,0);
		if(vctTerminals.size()>0)
		{
			for(int i=0;i<vctTerminals.size();i++)
			{
			  if(i!=vctTerminals.size()-1)
			  {
				  
		      sbTip.append("<a href='#' onclick='openwin("+vctTerminals.get(i).getStrId()+","+vctTerminals.get(i).getStrNo()+","+vctTerminals.get(i).getIntPaperState()+")' >"+vctTerminals.get(i).getStrNo()+"</a>,");	
			  }	else{ sbTip.append("<a href='#' onclick='openwin("+vctTerminals.get(i).getStrId()+","+vctTerminals.get(i).getStrNo()+","+vctTerminals.get(i).getIntPaperState()+")' >"+vctTerminals.get(i).getStrNo()+"</a>");}
			}
		sbTip.append("终端机，打印纸已经打印完，请重新添加！！");
		out.print(sbTip);
		}
	}	
%>
</font>
    </b>  
      </marquee>

</td>
        </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
<%@ include file="include/jsp/footer.jsp"%>