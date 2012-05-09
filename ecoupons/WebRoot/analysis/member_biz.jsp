<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				com.ejoysoft.common.Constants,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.MemberBiz,
				java.sql.ResultSet,
				com.ejoysoft.ecoupons.business.Coupon" %>
<%@page import="com.ejoysoft.ecoupons.business.Terminal"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
try{
if(!globa.userSession.hasRight("13015"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
    //MemberBiz  obj0=null;
    //MemberBiz obj=new MemberBiz(globa);
   	String  bytime=ParamUtil.getString(request,"byTime");
   	String stime=ParamUtil.getString(request,"stime","1000-01-01");
   	String etime=ParamUtil.getString(request,"etime","9999-12-30");
   	String flag = ParamUtil.getString(request,"flag","bycoupon");   	
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
    //查询条件
   
	String  strName=ParamUtil.getString(request,"strName","");
	String tWhere=" where strmobileno<>'' and strmobileno is not null";
	if (!strName.equals("")) {	
	      tWhere += " and strcardno like '%" + strName + "%' or strcardno like '%" + strName + "%'";
    	
	}
	tWhere += " order by strid";
	
	ResultSet rs = null;
	String label = "",sql="";
	if(flag.equals("byshop"))
		 label = "商    家（名称-分部）";
	else if(flag.equals("byterminal"))
	{
		 label = "终    端（编号）";
		 sql = "select  strMemberCardNo,strTerminalId,count(strTerminalId) " + 
		 "from t_bz_coupon_print where dtPrintTime>='" + stime + "' and dtPrintTime<='" + etime + "' ";
		 if(!strName.equals(""))
			sql += " and strmembercardno like '%" + strName + "%'";
		 sql += " group by strMemberCardNo,strTerminalId " ;
		
	}
	else {
		label = "优惠券（名称）";
		sql = "select  strMemberCardNo,strCouponId,count(strCouponId) " + 
			"from t_bz_coupon_print where dtPrintTime>='" + stime + "' and dtPrintTime<='" + etime + "' ";
			if(!strName.equals(""))
				sql += " and strmembercardno like '%" + strName + "%'";
		    sql += " group by strMemberCardNo,strCouponId " ;
	}
    rs = globa.db.executeRollQuery(sql);		
	String allCountString = "select count(*) from("+sql+") as allcount";
	CouponPrint objCouponPrint = new CouponPrint(globa);
	//记录总数
	int intAllCount = objCouponPrint.getCountA(allCountString);
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	int intPageSize=globa.getIntPageSize();
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;  	
	//共有页数
	 int intPageCount=(intAllCount-1)/intPageSize+1;		 
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
    var array = document.frm1.getElementsByTagName("select");
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" height="29" valign="top" background="../images/mail_leftbg.gif"><img src="../images/left-top-right.gif" width="17" height="29" /></td>
    <td width="1195" height="29" valign="top" background="../images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">会员消费</div></td>
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
            <td class="left_txt">当前位置：业务管理 / 经营分析 / 会员消费统计</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以查看会员消费统计的结果！<br>
                  包括会员基于优惠券、终端和商户的消费统计情况。 </span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>          
          <tr>
            <td >
            <form name=frm1 method=post action="member_biz.jsp">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
			<td align="left" width="400"><div style="height:26"> 时间：
			<select id="timeid" name="byTime" onchange="showTime(this.value)" class="sec2" >
			<option value="month">   月     份</option>
			<option value="season">  季     度</option>
			<option value="halfyear">半     年</option>
			<option value="year">    年     份</option>
			<option value="period">  时间段</option>
			</select>&nbsp;
				<span id="showtime"><input name="month" onclick="WdatePicker({dateFmt:'yyyy-MM'});" class="input_box" style="width:100"/>(年-月)</span>
			</div>
			</td>
			<td align="right"><div style="height:26"> 统计基于：
			<select style="width:80" class="sec2" name="flag" >
			<%String c11="",c12="",c13="";
			if(flag.equals("byterminal")){
				c13="selected=\"selected\"";
			}else if(flag.equals("byshop")){
				c12="selected=\"selected\"";
			}else{
				c11 = "selected=\"selected\"";
			}
			 %>
					<option value="bycoupon" <%=c11 %> >优惠券</option>
					<option value="byshop" <%=c12 %> >  商     家</option>
					<option value="byterminal" <%=c13 %> >终     端</option>
			</select>
		    </div>
			</td>
			
			<td align="right" width="400"><div style="height:26"> 
			会员卡号 ：<input name="strName" class="input_box" value="<%=strName%>" size="10"> 
			     <input type="hidden" name="curpage" value="newsearch">
			         <input type="submit" class="button_box" value="统计" /> 
			</div>
			</td>			
			</tr> 
			<tr>
                <td bgcolor="#b5d6e6" colspan="30"  height="23"><div align="center"><%=setime%></div></td>
              </tr>            
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
               <tr>
                <td width="10%" class="left_bt2"><div align="center">序号</div></td>
                <td width="30%" class="left_bt2"><div align="center">会员（卡号）</div></td>
                <td width="30%" class="left_bt2"><div align="center"><%=label %></div></td>
                <td width="30%" class="left_bt2"><div align="center">消费统计（次）</div></td>      
              </tr>
            <%
            	//记录总数
				Coupon c = new Coupon(globa);
				Coupon c1 = null;
				Terminal t = new Terminal(globa);
				Terminal t1 = null;
				String strTmp="";
				if (rs!=null && rs.next()) {
					int i=intStartNum - 1;					
				    if (intStartNum != 0 && intPageSize != 0)
					   rs.absolute(intStartNum);
					do{
						i++;
						String strCardNo = rs.getString("strMemberCardNo");
						if(flag.equals("byshop"))
							 label = "商    家（名称-分部）";
						else if(flag.equals("byterminal"))
						{
							t1 = t.show(" where strId='" + rs.getString(2) + "'");
							if (t1 == null)
								strTmp = "已删除";
							else
								strTmp = t1.getStrNo();							
						}
						else {
							c1 = c.show(" where strId='" + rs.getString(2) + "'");
							if (c1 == null)
								strTmp = "已删除";
							else
								strTmp = c1.getStrName();
						}
			%>
			  <tr  title="会员：<%=strCardNo%>" >
                <td bgcolor="#FFFFFF"><div align="center">&nbsp;<%=i%></div></td>
                <td bgcolor="#FFFFFF"> <div align="center"><span class="STYLE1"><%=strCardNo%></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=strTmp %></span></div></td>
                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=rs.getInt(3)%></span></div></td>
              </tr>
			<%
					}while (rs.next() && i < intEndNum && i<intAllCount);
				}							
				   //关闭数据库连接对象
		       globa.closeCon();
            %>  
            </table>
            </form></td>
          </tr>
        </table>
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr><td >
        <form name=frm method=post action="member_biz.jsp" style="height=0">
		<input name="strName" type="hidden" value="<%=strName%>">
		<input name="stime" type="hidden" value="<%=stime%>">
		<input name="flag" type="hidden" value="<%=flag%>">
		<input name="etime" type="hidden" value="<%=etime%>">
     	<!-- 翻页开始 -->  
     	<%@ include file="../include/jsp/cpage.jsp"%>
       	<!-- 翻页结束 --> 
       	</form>
        </td></tr>
        </table>      
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
</body>
</html>
<%
}catch(Exception e){e.printStackTrace();}
 %>
<%@ include file="../include/jsp/footer.jsp"%>