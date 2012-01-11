<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				com.ejoysoft.common.Constants,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.TerminalAnalysis" %>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("13015"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
    TerminalAnalysis  obj0=null;
    TerminalAnalysis obj=new TerminalAnalysis(globa);
   	String  bytime=ParamUtil.getString(request,"byTime");
   	String stime="1000-01-01";
   	String etime="9999-12-30";
    if(bytime!=null&&!(bytime.trim().equals(""))&& bytime.equals("month"))
    {
    	String month= ParamUtil.getString(request,"month","");
    	if(!month.trim().equals(""))
    	{
    	   stime = month+"-01";
    	   String stime0[]=stime.trim().split("-");
    	   int k = Integer.parseInt(stime0[1]);
    	   int y = Integer.parseInt(stime0[0]);
    	   String emonth ="",eyear="";
    	   eyear = String.valueOf(y);
    	   if(k<9)
    	  	 emonth = String.valueOf(0)+String.valueOf(k+1);
    	   else if(k>=9&&k<=11)
    	  	 emonth = String.valueOf(k+1);
    	   else
    	   {
    	   	 eyear = String.valueOf(y+1);
    	   	 emonth = "01";
    	   }
    	  	etime = eyear+"-"+emonth+"-01";   
    	}    	
    }
    else if(bytime!=null&&!(bytime.trim().equals(""))&& bytime.equals("season"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	String season= ParamUtil.getString(request,"season","");
    	if(!year.trim().equals("") && !season.trim().equals(""))
    	{
    	   if(season.trim().equals("1"))
    	   {
    	   		stime=year+"-01-01";
    	   		etime=year+"-04-01";
    	   }
    	   else if(season.trim().equals("2"))
    	   {
    	   		stime=year+"-04-01";
    	   		etime=year+"-07-01";
    	   }
    	   else if(season.trim().equals("3"))
    	   {
    	   		stime=year+"-07-01";
    	   		etime=year+"-09-01";
    	   }
    	   else 
    	   {
    	   		stime=year+"-09-01";
    	   		etime=year+"-12-31";
    	   }
    	}    	
    } else if(bytime!=null&&!(bytime.trim().equals(""))&& bytime.equals("halfyear"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	String season= ParamUtil.getString(request,"halfyear","");
    	if(!year.trim().equals("") && !season.trim().equals(""))
    	{
    	   if(season.trim().equals("1"))
    	   {
    	   		stime=year+"-01-01";
    	   		etime=year+"-07-01";
    	   }
    	   else if(season.trim().equals("2"))
    	   {
    	   		stime=year+"-07-01";
    	   		etime=year+"-01-01";
    	   }
    	}    	
    }
    else if(bytime!=null&&!(bytime.trim().equals(""))&&bytime.equals("year"))
    {
    	String year= ParamUtil.getString(request,"year","");
    	if(!year.trim().equals(""))
    	{
    	   		stime=year+"-01-01";
    	   		etime=String.valueOf(Integer.parseInt(year)+1)+"-01-01";
    	}
    }
    else if(bytime!=null&&!(bytime.trim().equals(""))&&bytime.equals("period"))
    {
    	String smonth2[],emonth2[];
    	String stime1= ParamUtil.getString(request,"stime","");
    	String etime1= ParamUtil.getString(request,"etime","");
    	if(!stime1.trim().equals(""))
    	{
    	   stime = stime1+"-01"; 	   
    	}    	
    	if(!etime1.trim().equals(""))
    	{    	  
    	   String etime0[]=etime1.trim().split("-");
    	   int k = Integer.parseInt(etime0[1]);
    	   int y = Integer.parseInt(etime0[0]);
    	   String emonth ="",eyear="";
    	   eyear = String.valueOf(y);
    	   if(k<9)
    	  	 emonth = String.valueOf(0)+String.valueOf(k+1);
    	   else if(k>=9&&k<=11)
    	  	 emonth = String.valueOf(k+1);
    	   else
    	   {
    	   	 eyear = String.valueOf(y+1);
    	   	 emonth = "01";
    	   }
    	   etime = eyear+"-"+emonth+"-01";   
    	   etime = eyear+"-"+emonth+"-01";
    	}
    	if(!stime1.trim().equals("")&&!etime1.trim().equals(""))
    	{
    	   smonth2 = stime1.split("-");
    	   emonth2 = etime1.split("-");
    	   int sy = Integer.parseInt(smonth2[0]);
    	   int sm = Integer.parseInt(smonth2[1]);
    	   int ey = Integer.parseInt(emonth2[0]);
    	   int em = Integer.parseInt(emonth2[1]);
    	   if((sy == ey && sm >= em)||(sy>ey))
    	   	   out.print("<script type='text/javascript'>alert('输入时间先后有误，请重新输入');history.go(-1);</script>");   	
    	
    	}  	
    	
    }
    obj.setStime(stime);
    obj.setEtime(etime);
    //查询条件
   
	String  strNo=ParamUtil.getString(request,"strNo","");
	String tWhere=" where 1=1";
	if (!strNo.equals("")) {
    		tWhere += " and strno like '%" + strNo + "%'";    	
	}
	tWhere += " order by strid";
	//记录总数
	int intAllCount=obj.getCountSA(tWhere);
	//当前页
	int intCurPage=globa.getIntCurPage();
	//每页记录数
	int intPageSize=globa.getIntPageSize();
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;    
	//获取到当前页面的记录集
	Vector<TerminalAnalysis> vctObj=obj.getTerminalAnalysisList(tWhere);
	//获取当前页的记录条数
	int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
	String setime="";
	if(stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		setime = "所   有   统   计   记   录";
	}
	else if(stime.equals("1000-01-01")&&!etime.equals("9999-12-30"))
	{
		setime =etime+ "之   前   统   计   记   录";
	}
	else if(!stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		setime =stime+ "之   后   统   计   记   录";
	}
	else setime = stime+"  至  "+etime+"   统   计   记   录";
	session.setAttribute("twhere",tWhere);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=application.getAttribute("APP_TITLE")%></title>
<script src="../include/DatePicker/WdatePicker.js"></script>
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
<script src="../include/js/list.js"></script>
<script type="text/javascript">

function showTime(str){
    var array = document.frm.getElementsByTagName("select");
    for(i=0;i<array.length;i++)
	 {
	 	if(array[i].id=="timeid")
	 	{	 		
	 		if(array[i].value=="month")	
            	document.getElementById("showtime").innerHTML="<input name='month' onclick='WdatePicker({dateFmt:&quot;yyyy-MM&quot;});' class='input_box' style='width:100'/>(年-月)";	      
			else if(array[i].value=="season")	
            	document.getElementById("showtime").innerHTML="<select name='season'><option value='1' class='sec2'>第一季度</option><option value='2'>第二季度</option><option value='3'>第三季度</option><option value='4'>第四季度</option></select>(季度)"+
            	"<input name='year' readonly onclick='WdatePicker({dateFmt:&quot;yyyy&quot;});' class='input_box' style='width:100'/>(年)";	    
            else if(array[i].value=="halfyear")	
            	document.getElementById("showtime").innerHTML="<select name='halfyear'><option value='1' class='sec2'>上半年</option><option value='2'>下半年</option></select>(上/下半年)"+
            	"<input name='year' readonly onclick='WdatePicker({dateFmt:&quot;yyyy&quot;});' class='input_box' style='width:100'/>(年)";	    
           else if(array[i].value=="year")	
            	document.getElementById("showtime").innerHTML="<input name='year' readonly onclick='WdatePicker({dateFmt:&quot;yyyy&quot;});' class='input_box' style='width:100'/>(年)";	    
      	   else if(array[i].value=="period")	
            	document.getElementById("showtime").innerHTML="<input name='stime' readonly onclick='WdatePicker({dateFmt:&quot;yyyy-MM&quot;});' class='input_box' style='width:100'/>至<input name='etime' readonly onclick='WdatePicker({dateFmt:&quot;yyyy-MM&quot;});' class='input_box' style='width:100'/>(开始-结束)";	    
      								      
	 	} 
	 }
}

</script>
</head>
<body>
<form name=frm method=post action="terminal_analyse.jsp">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">终端统计</div></td>
      </tr>
    </table></td>
    <td width="22" valign="top" background="../images/mail_rightbg.gif"><img src="../images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td height="71" valign="middle" background="../images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9"><table width="100%" height="547" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="13" valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td height="534" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：业务管理 / 经营分析 / 终端统计分析</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以查看终端统计分析的结果！<br>
                  包括终端发布的优惠券及其被打印次数的统计结果。 </span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>          
          <tr>
            <td >
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
			<td width="40%"><div style="height:26"> 时间：
			<select id="timeid" name="byTime" onchange="showTime(this.value)" class="sec2" >
			<option value="month">   月份</option>
			<option value="season">  季度</option>
			<option value="halfyear">半年</option>
			<option value="year">    年份</option>
			<option value="period">  时间段</option>
			</select>&nbsp;
				<span id="showtime"><input name="month" onclick="WdatePicker({dateFmt:'yyyy-MM'});" class="input_box" style="width:100"/>(年-月)</span>
			</div>
			</td>
			<td align="left" width="35%"><div style="height:26"> 
			终端编号：<input name="strNo" class="input_box" value="" size="10">
			         <input type="submit" class="button_box" value="统计" /> 
			</div>
			</td>  
			<td width="25%" align="right" height="28"><div style="height:26">(统计后查看)
					<input type="button" name="b_submit" value="图形显示" class="button" style="width:100"	onclick="window.open('writeToImage.jsp?tag=terminalanalyse&stime=<%=obj.getStime()%>&etime=<%=obj.getEtime()%>','','width=1000,height=600,top=50,left=100');"
						style="cursor: hand" />	
			</div>			
			</td>		 
			</tr>
			 <tr>
                <td bgcolor="#b5d6e6" width="10%" colspan="4"  height="23"><div align="center"><%=setime%></div></td>
              </tr>
            
	          </table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
               <tr>
                <td width="10%" class="left_bt2"><div align="center">序号</div></td>
                <td width="30%" class="left_bt2"><div align="center">终端编号</div></td>
                <td width="30%" class="left_bt2"><div align="center">发布优惠券名称</div></td>
                <td width="30%" class="left_bt2"><div align="center">优惠券打印数量</div></td>      
              </tr>
            <%
           		if(vctObj.size() < intEndNum)
          		  intEndNum = vctObj.size();
            	for (int i = intStartNum-1;i < intEndNum; i++) {
                        	TerminalAnalysis obj1 = vctObj.get(i);         
		                	
                %> 
              <tr  title="终端：<%=obj1.getTerminalNo()%>" >
                <td bgcolor="#FFFFFF"><div align="center">&nbsp;<%=i+1 %></div></td>
                <td bgcolor="#FFFFFF"> <div align="center"><span class="STYLE1"><%=obj1.getTerminalNo()%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getCouponName() %></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getPerCouponPrintNum()%></span></div></td>
                </tr>
            <%
            }
            %>  
            </table></td>
          </tr>
        </table>
     	<!-- 翻页开始 -->  
     	<%@ include file="../include/jsp/cpage.jsp"%>
       	<!-- 翻页结束 --> 
       </td>
       </tr>
		 <tr><td>&nbsp;</td></tr>
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