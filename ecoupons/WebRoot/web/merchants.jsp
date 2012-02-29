<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.system.SysUserUnit,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.ecoupons.web.Index,
				com.ejoysoft.ecoupons.system.SysPara,
				com.ejoysoft.ecoupons.business.CouponComment" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    //初始化
    Shop  shop0=null;
    Shop obj=new Shop(globa);
    SysPara syspara = new SysPara(globa);
    //查询条件
    String  strName=ParamUtil.getString(request,"strName","");
    String  strTrade=ParamUtil.getString(request,"strtrade","");
	String tWhere=" where 1=1";
	if (!strName.equals("")) {
		tWhere += " and strbizname like '%" + strName + "%'";
	}
	if(!strTrade.equals(""))
	{
    //System.out.println(strTrade);
		tWhere += " and strtrade='" + strTrade + "'";
	}
	tWhere += " order by strid";
	//记录总数
	int intAllCount=obj.getCount(tWhere);
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	//int intPageSize=globa.getIntPageSize();
	int intPageSize=6;
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<Shop> vctObj=obj.list(tWhere,intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctObj!=null&&vctObj.size()>0?vctObj.size():0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户</title>
<link href="css/merchants.css" rel="stylesheet" type="text/css" />
</head>

<body>
<form name=frm method=post action="marchants.jsp" >		
<iframe height="164" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<!--正文部分-->
<div class="coupons-content">
<!--left部分-->
  <div class="coupons-left">

 <div class=hotList>
 
	<div class=hotList_top>
		<div class="hotList_sf">
	   <%
	   if(!strName.equals(""))
	   {
	   %>商户列表<%="(关于'"+strName+"'的查询结果)"%>
	   <%
	   }else if(!strTrade.equals(""))
	   {
	   %>商户列表<%="("+syspara.getNameById(strTrade)+")"%>
	   <%
	   }else
	   {
	   %>商户列表<%="(所有)"%>
	   <%
	   }
	   %>
	   </div>
	   
<div class=more>
<table>
  <tbody>
  <tr>
    <td></td>
    <td><a onclick="seturl('order','hotnum')" href="javascript:void(0)"></a> </td>
    <td></td>
    <td><a onclick="seturl('order','grade')" href="javascript:void(0)"></a> </td></tr>
    </tbody>
 </table>
</div>
</div>
    <div class=hotList_mid>
<% for(int i = 0;i < vctObj.size(); i++) {
       Shop obj1 = vctObj.get(i);
	   String bizname = obj1.getStrBizName();
	   String shopname = obj1.getStrShopName();
	   if(shopname !=null && !shopname.equals(""))  
	   {
	   		bizname = bizname+"-"+shopname;
	   }
%>
<div class=pro>	
	
	<div class=pro_mid>
<div class=pro_img><a href="merchantsinfo.jsp?strid=<%=obj1.getStrId() %>" target=_blank>
                <%
                if (obj1.getStrSmallImg().length() > 0) {
                %>
                  <img src="<%="../shop/images/" + obj1.getStrSmallImg() %>" width=112 height=110 border="0" />
                <%
                }
                else
                {
                %>
                 <img src="images/temp.jpg" width=112 height=110 border="0" />
                 <%} %>
</a></div>
<div class=pro_info>
<div class=headtitle><a href="merchantsinfo.jsp?strid=<%=obj1.getStrId() %>" target=_blank><%=bizname%></a></div>

<div class=clearfloat></div>
<div class=line><img src="images/fg.gif" width=525 height=4 /></div>
<div class=text_left>
<table border=0 width=100%>
  <tbody>
  <tr>
    <td width=267 height="26">* 行        业：<%=obj1.getStrTradeName()%></td>
    <td height="26"><!--* 联系电话：<%=obj1.getStrPhone()%>--></td>
  </tr>
  <tr>
    <td width=253 height="26">* 地        址：<%=obj1.getStrAddr() %></td>
    <td height="26"><!--* 联   系  人：<%=obj1.getStrPerson() %>--></td>
    </tr>
  <tr>
    <td height="26" colspan="2">
    * 简          介：<%=obj1.getStrIntro().replace("\n","<br/>")%>    
    </td>
  </tr>
</tbody></table>
<br/></div>
</div>
<div class=clearfloat></div></div>
</div>
<%} %>
</div>
<div class=hotList_bottom>
<input type=hidden name=strName value="<%=strName %>" />
<input type=hidden name=strtrade value="<%=strTrade %>" />
</div></div>

 	<!-- 翻页开始 -->  
 	<%@ include file="include/cpage.jsp"%>
   	<!-- 翻页结束 --> 


</div>
 <!--left结束-->
  
<!--right部分-->
  <div class="coupons-right">
  
 <div class=sort>
<div class=sort_top>
<h1><strong>商家检索（按类别）</strong></h1></div>
<div class=sort_con1>
<ul> 
<%
Index index=new Index(globa);
Vector<String[]> vctStrades=index.returnVctTrades();
for(int i=0;i<vctStrades.size();i++){
	out.print("<LI><A href='merchants.jsp?strtrade="+vctStrades.get(i)[0]+"'>"+vctStrades.get(i)[1]+"&nbsp;&nbsp;("+vctStrades.get(i)[2]+")</A></LI>");	
	
}

%>
</ul>
</div>
<div class=sort_bottom></div></div> 
 
 
  <div class=sort>
<div class=sort_top>
<h1><strong>推荐优惠券</strong></h1>
<div class=hotList_more><a href="recommend_more.jsp">更多&gt;&gt;</a>&nbsp;&nbsp;</div></div>
	<div class=sort_con2>
	<ul>
	<%
	Coupon coupon = new Coupon(globa);
	Vector<Coupon> vctcoup = coupon.list(" where intrecommend='1' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime ",0,0);
	int k =1;
	for(int i=0;i<vctcoup.size();i++)
	{   
		if(k++ >6){break;}
		Coupon coupon2 = vctcoup.get(i);
		if(coupon2.getStrSmallImg()!=null&&coupon2.getStrSmallImg().length()>0)
		{
		%>
		  <li>
         <a href="couponinfo.jsp?strid=<%=coupon2.getStrId() %>" target="_blank"><img src=<%="../coupon/images/"+coupon2.getStrSmallImg()%> width="173" height="110"  border="0" title="<%=coupon2.getStrName()%>"/>
         </a></li> 
		<%
		}
	}
	 %> 
	</ul>
	</div>
<div class=sort_bottom></div>
  </div> 
 <!--right结束-->
 
  
</div>
<!--正文部分结束-->
</div>

<iframe style="HEIGHT: 260px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
</form>
<%globa.closeCon(); %>
</body>
</html>
