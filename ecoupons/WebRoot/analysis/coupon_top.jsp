<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector,
				com.ejoysoft.common.Constants,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.CouponTop" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
if(!globa.userSession.hasRight("13020"))
      throw new NoRightException("用户不具备操作该功能模块的权限，请与系统管理员联系！");
%>

<%
    //初始化
    CouponTop obj=new CouponTop(globa);
   	String  bytime=ParamUtil.getString(request,"byTime");
   	String  topnumString=ParamUtil.getString(request,"strTopnum","0");
    int topnum = Integer.parseInt(topnumString);  
   	String stime=ParamUtil.getString(request,"stime","1000-01-01");
   	String etime=ParamUtil.getString(request,"etime","9999-12-30");
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
	CouponPrint objCoupon = new CouponPrint(globa);	
	String strId ="";
	if (!strName.equals("")) {	
	      	String tWhere=" select strid from t_bz_coupon where strName like '%" + strName + "%'";   	
	      	strId = objCoupon.getCouponIds(tWhere);
	}
	//记录总数
	String strSql ="select count(distinct strcouponid) from t_bz_coupon_print ";
	if(!strId.equals(""))
	{	String strIds[] = strId.split(",");
		strSql += " where 1=2";
		for(int k=0;k<strIds.length;k++)
		{
			strSql += " or strcouponid like '%"+strIds[k]+"%'";
		}
	}
	int  allCount = objCoupon.getCountA(strSql);
	if(topnum == 0 || allCount <topnum)
		topnum = allCount;
	int intAllCount = topnum;
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
    int allcoupon= intAllCount;	
	if(stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		if(allcoupon == topnum)
			setime = "所   有  优   惠   券   排  行   统   计   记   录";
		else		
			setime = "前 "+topnum+" 名   优   惠   券   排  行   统   计   记   录";
	}
	else if(stime.equals("1000-01-01")&&!etime.equals("9999-12-30"))
	{
		if(allcoupon == topnum)
			setime =etime+ "之   前  所  有   优   惠   券    统   计   记   录";
		else
			setime =etime+ "之   前  前  "+topnum+" 名    优   惠   券   统   计   记   录";
	}
	else if(!stime.equals("1000-01-01")&&etime.equals("9999-12-30"))
	{
		if(allcoupon == topnum)
			setime =stime+ "之   后   所  有   优   惠   券   统   计   记   录";
		else
			setime =stime+ "之   后  前  "+topnum+" 名   优   惠   券    统   计   记   录";
	}
	else 
	{
		if(allcoupon == topnum)
			setime = stime+"  至  "+etime+"  所  有   优   惠   券    统   计   记   录";
		else
			setime = stime+"  至  "+etime+"  前  "+topnum+" 名   优   惠   券    统   计   记   录";		
	}
	ResultSet rs = null;
	String  sql = "select  strcouponid,count(strcouponid),strshop from t_bz_coupon_print where dtPrintTime>='" + stime + "' and dtPrintTime<='" + etime + "' ";
	sql += " group by strcouponid order by count(strcouponid) desc " ;
	rs = globa.db.executeQuery(sql);	
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
 function chkFrm()
 {
    var topnum =document.getElementById("strTopnum").value;    
 	if(topnum!="")
 	{
 	   var topnumParn =/^[0-9]*[1-9][0-9]*$/; 
       var reParn = new RegExp(topnumParn);
       if(!reParn.test(topnum))
       {
          alert(topnum+"请输入正确的排行要求数目！！！如20");
          frm1.strTopnum.focus();         
		  return false;
       } 
 	   frm1.submit();
 	} 
 	else
 	{ 		
 	   frm1.submit();
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
        <td height="31"><div class="titlebt">优惠券消费排行</div></td>
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
        <td height="534" valign="top">
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：业务管理 / 经营分析 / 优惠券消费排行榜</td>
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
                <td width="94%" valign="top"><span class="left_txt3">在这里，您可以查看优惠券消费排行榜！<br>
                  包括优惠券消费排名、优惠券基本信息等统计情况。 </span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>          
          <tr>
            <td >
			<form name=frm1 method=post action="coupon_top.jsp">
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
			<td align="right"><div style="height:26"> 统计前 <input name="strTopnum" type="text"  class="input_box" maxlength="9" value="<%=topnum==0?"":topnum %>" size="6"/>名
		    </div>
			</td>
			
			<td align="right" width="400"><div style="height:26"> 
			优惠券名称：<input name="strName" type="text" class="input_box" value="<%=strName%>" size=" ">
					<input type="hidden" value="newsearch" name="curpage"/>
			         <input type="button" class="button_box" onclick="chkFrm()" value="统计" /> 
			</div>
			</td>			
			</tr> 
			<tr>
                <td bgcolor="#b5d6e6" colspan="30"  height="23"><div align="center"><%=setime%></div></td>
              </tr>
            
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
               <tr>
                <td width="10%" class="left_bt2"><div align="center">排行榜</div></td>
                <td width="15%" class="left_bt2"><div align="center">名          称</div></td>
                <td width="10%" class="left_bt2"><div align="center">消费统计（次）</div></td>
                <td width="20%" class="left_bt2"><div align="center">所属商家(名称-分部)</div></td> 
                <td width="15%" class="left_bt2"><div align="center">价          格</div></td>   
                <td width="30%" class="left_bt2"><div align="center">活动时间</div></td>       
              </tr>
            <%
            	//记录总数
				Coupon c = new Coupon(globa);
				Coupon obj1 = null;				
				String strTmp="";
				if (rs!=null && rs.next()) {
					int i=intStartNum - 1;					
				    if (intStartNum != 0 && intPageSize != 0)
					   rs.absolute(intStartNum);
					do{
						String shopFullName = rs.getString("strshop");
						int printnum = rs.getInt(2);
						String couponid = rs.getString("strcouponid");
						String shopname = "";
						if(shopFullName!=null)
						{
							if( shopFullName.contains("/"))
							{
								String shops[] = shopFullName.split("/");
								if(shops.length==2)
								{
									shopname = shops[1];
								}
								else
								{
									shopname = "";
								}
							}
							else
								shopname = "已删除";
						}
						String couponname="已删除",couponid2="";
						if(couponid!=null)
						{
							if( couponid.contains("/"))
							{
								String coupons[] = couponid.split("/");
								if(coupons.length==2)
								{
									couponname = coupons[1];
									couponid2 = coupons[0];
								}
								else
								{
									couponname = "";
									couponid2 = couponid;
								}
							}
							else
							{
								 couponid2 = "";	
								 couponname = "已删除";
							}
						}
						obj1 = c.show(" where strid='"+couponid2+"'");
						if(obj1 !=null)
						{
						if(strId.equals("") || (obj1.getStrId()!=null && (strId).contains(couponid)))
						{
				   			   i++;
		%>
						  <tr  title="优惠券：<%=couponname%>" >
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">第&nbsp;&nbsp;<%=i%>&nbsp;&nbsp;名</span></div></td>
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=couponname%> </span></div></td>
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=rs.getInt(2)%></span></div></td>
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=shopname%></span></div></td>
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getFlaPrice()%></span></div></td>
			                <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=obj1.getDtActiveTime()%></span></div></td>
			              </tr>
		<%			   } 
						}
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
         <tr>
         <td>
	        <form name=frm method=post action="coupon_top.jsp">
			<input name="strName" type="hidden" value="<%=strName%>"/>
			<input name="strTopnum" type="hidden" value="<%=topnum%>"/>
			<input name="stime" type="hidden" value="<%=stime%>"/>
			<input name="etime" type="hidden" value="<%=etime%>"/>
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
<%@ include file="../include/jsp/footer.jsp"%>