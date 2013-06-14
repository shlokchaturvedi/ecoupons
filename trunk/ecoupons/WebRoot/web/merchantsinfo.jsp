<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.system.SysUserUnit,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.common.exception.IdObjectException" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="com.ejoysoft.common.Format"%>
<%@page import="com.ejoysoft.ecoupons.business.ShopComment"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    Shop obj=new Shop(globa);
    //查询条件
    String  strId=ParamUtil.getString(request,"strid"," ");	
    if(strId.equals(""))
    	throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
 
	String tWhere=" where strid='" + strId + "'";
	Shop obj1=obj.show(tWhere);
	String bizname = obj1.getStrBizName();
    String shopname = obj1.getStrShopName();
    if(shopname !=null && !shopname.equals(""))  
    {
   		bizname = bizname+"-"+shopname;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/merchantsinfo.css" rel="stylesheet" type="text/css" />
<link href="css/comment.css" rel="stylesheet" type="text/css" />
<title>商家详细</title>  
</head>
<body>
<iframe style="HEIGHT: 164px" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<div id=Main>
<div id=Left>
<div class=left_top>
<div class="hotList_sf">商户详细：<%=bizname%>&nbsp;&nbsp;
<%
				String img1="../images/star-off.png";
				String img2="../images/star-off.png";
				String img3="../images/star-off.png";
				String img4="../images/star-off.png";
				String img5="../images/star-off.png";
			
				int m=obj1.getStar();
				if(m==1){
					img1="../images/star-on.png";
				}else if(m==2){
					img1="../images/star-on.png";
					img2="../images/star-on.png";
				}else if(m==3){
					img1="../images/star-on.png";
					img2="../images/star-on.png";	
					img3="../images/star-on.png";
				}else if(m==4){
					img1="../images/star-on.png";
					img2="../images/star-on.png";	
					img3="../images/star-on.png";
					img4="../images/star-on.png";
				}else if(m==5){
					img1="../images/star-on.png";
					img2="../images/star-on.png";	
					img3="../images/star-on.png";
					img4="../images/star-on.png";
					img5="../images/star-on.png";
				}
			%>
			<img name="img"  alt="1" src="<%=img1 %>"/>	
            <img name="img"  alt="2" src="<%=img2 %>"/>
            <img name="img"  alt="3" src="<%=img3 %>"/>
         	<img name="img"  alt="4" src="<%=img4 %>"/>
            <img name="img"  alt="5" src="<%=img5 %>"/>
</div>
</div>
<div class=left_mid>
<div class=show>
  <div class=show_mid>

<div class=mer_mid>
<div class=mer_img>
<%
                if (obj1.getStrSmallImg().length() > 0) {
                %>
                  <img src="<%="../shop/images/" + obj1.getStrLargeImg() %>" width=400 height=360 />
                <%
                }
                else
                {
                %>
                 <img src="images/temp.jpg" width=400 height=360 />
                 <%}
                  %>
</div>
<div class=mer_info>
<div class=mer_headtitle><a href="#"><%=bizname%></a></div>

<div class=clearfloat></div>
<div class=mer_text_left>
<table border=0 width=100%>
  <tbody>
  <tr>
    <td width=165 height="26">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 行业：<%=obj1.getStrTradeName()%> </td><!--
    <td height="26">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 联系人：<%=obj1.getStrPerson()%></td>
  --></tr>
  <tr>
    <td height="26">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 地址：<%=obj1.getStrAddr()%></td><!--
    <td height="26">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 联系电话：<%=obj1.getStrPhone()%></td>
   --></tr>
  </tbody></table>
<br/></div>
</div>
<div class=clearfloat></div></div>
<div class=show_line></div>
<div class=mer>
  <div class=mer_js>
    <p><b>商家简介：</b><br/></p>
    <p>  <%=obj1.getStrIntro().replace("\n","<br/>")%>
    </p>
    </div>
</div>
  </div>
</div>

<!--店铺展示-->
<div class=zs>
<div class=zs_top>
<p>店铺展示</p></div>
<div class=zs_mid>
<%
Coupon obj2 = new Coupon(globa);
Vector<Coupon> vctCoupon = obj2.listByShopId("where strshopid='" + strId + "' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime ");
if(vctCoupon!=null&&vctCoupon.size()!=0)
{
	int k=vctCoupon.size();
	for(int i=0;i<k;i++)
	{
		Coupon obj3 = vctCoupon.get(i);
		if (obj3.getStrSmallImg()!=null && obj3.getStrSmallImg().length() > 0) {
         %>
         <a href="couponinfo.jsp?strid=<%=obj3.getStrId() %>" target="_blank"><img src="<%="../coupon/images/" + obj3.getStrSmallImg() %>" border="0" width="125" height="95px" style="margin:2px 2px;"  title="<%=obj3.getStrName() %>" /></a>
        <%
        }   
        else{
         %>
        <a href="couponinfo.jsp?strid=<%=obj3.getStrId() %>" target="_blank"><img src="images/temp.jpg"  border="0"  width="125" height="95px" style="margin:2px 2px;" title="<%=obj3.getStrName() %>" /></a>
        <%
        }    	
	}	
} 
%>

</div>
<div class=zs_bottom></div></div><!--更多相关-->
<!--发表评论-->

<%
    ShopComment comobj = new ShopComment(globa);
    //记录总数
	int intAllCount=comobj.getCount(" where strshopid='"+strId+"'");
	//当前页
	int intCurPage=globa.getIntCurPage();
    //每页记录数
	//int intPageSize=globa.getIntPageSize();
	int intPageSize=5;
	//共有页数
 	int intPageCount=(intAllCount-1)/intPageSize+1;
	// 循环显示一页内的记录 开始序号
	int intStartNum=(intCurPage-1)*intPageSize+1;
	//结束序号
	int intEndNum=intCurPage*intPageSize;   
	//获取到当前页面的记录集
	Vector<ShopComment> vctcom = comobj.list(" where strshopid='"+strId+"'",intStartNum,intPageSize);
	//获取当前页的记录条数
	int intVct=(vctcom!=null&&vctcom.size()>0?vctcom.size():0);
%>
<div style="PADDING-LEFT: 23px; width:578px;">
<!--<div style="WIDTH: 100%" class=ny_left>
<div style="FONT-SIZE: 12px" class=line_gray>
<div class=mp_auto>
<div style="HEIGHT: 20px" class="box bold word_gra sp_nav_bg jianju13 ">会员评论：(已有<SPAN 
class=red><%=intAllCount%></SPAN>条评论)</div>
<div style="HEIGHT: 1px; BORDER-TOP: #ebebeb 1px solid"></div>
<div style="PADDING-LEFT: 5px"><BR>
<form name=frm action="couponinfo.jsp" method="post" >
<input type="hidden" name="strid" value="<%=strId %>"/>
<%
	for (int i = 0;i < vctcom.size(); i++) {
       ShopComment comobj1 = vctcom.get(i);
       Member memobj = new Member(globa);
       String twhere=" where 1=1";
       if(comobj1.getStrMemberCardNo()!=null)
       	{	twhere +=" and strcardno='"+comobj1.getStrMemberCardNo()+"'";}
       Member memobj1 = memobj.show(twhere);
       String namememb="***";
       if(memobj1!=null && memobj1.getStrName()!=null && memobj1.getStrName().length()>1)
       {
       		
        	namememb=memobj1.getStrName().substring(0,1)+"* *";
       }
%>
<div style="PADDING-LEFT: 5px"><BR>
<div style="HEIGHT: 50px">
<div style="WIDTH: 10%; FLOAT: left; HEIGHT: 40px"><img alt="" src="images/touxiang.jpg" width=45 height=33> </div>
<div style="LINE-HEIGHT: 18px; WIDTH: 80%; FLOAT: left; ">
<SPAN style="COLOR: #001c55"><%=namememb%>：</SPAN>
<SPAN style="COLOR: gray"><%=comobj1.getStrComment().replace("\n","<br/>") %></SPAN> 
</div>
</div>
<div style="BORDER-BOTTOM: #808080 1px dotted; HEIGHT: 1px; CLEAR: both"></div><BR>
</div>
<%
    }
%>
 翻页开始   
<%@ include file="include/cpage.jsp"%>
 翻页结束 
</form>
</div></div>
</div></div>
--><div class="clr height_05"></div>
<div></div>
<div class="box  line_gray jianju9b">
<div class="box bold word_gra sp_nav_bg jianju13 ">给商家留言：</div>
<div class="box jianju13 jianju2a word_12px">
<%
 if(session.getAttribute(Constants.MEMBER_KEY) != null)
 {  %>
<form action="shopcomment_act.jsp" method=post name=frm1 >
<input type="hidden" name="strshopid" value="<%=strId%>" > 
<input type="hidden" name="<%=Constants.ACTION_TYPE%>" value="<%=Constants.ADD_STR%>" >
<table border=0 width="100%">
  <tbody>
  <tr>
    <td align="center">&nbsp;&nbsp;&nbsp;&nbsp;内&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  容：</td>
    <td colSpan=7><label><TEXTAREA style="WIDTH: 500px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=strcomment></TEXTAREA> 
      </label><SPAN id=span_sub></SPAN></td></tr></tbody></table>

<table id=tr2>
  <tbody>
  <tr>
    <td align=right>验 &nbsp;证 &nbsp;码： </td>
    <td align=left><input style="WIDTH: 60px" name=yanzm class=form type=text value="" maxLength=4 size=10> </td>
    <td align=left> <a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code2').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <img style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code2 border=0 name=checkcode src="../image.jsp"> 
    </a></td>
    <td align=left><input id=btn_login class=cz_button value=" " type=submit name=btn_login> 
    </td></tr></tbody></table>   
    </form> 
    
 <%
 } 
else
{
 %>
<form name="frm2" method=post action="<%=application.getServletContextName()%>/web/Auth" >		
<input type="hidden" name="actiontype" value="weblogon2" />		
<input type="hidden" name="authType" value="password"/>		
<input type="hidden" name="strId" value="<%=strId%>" > 
<input type="hidden" name="gotoPage" value="merchantsinfo" > 
<table border=0 width="100%">
  <tbody>
  <tr>
    <td align="center">内&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  容： </td>
    <td colSpan=7><LABEL><TEXTAREA style="WIDTH: 480px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=strcomment></TEXTAREA> 
      </LABEL><SPAN id=span_sub></SPAN></td></tr></tbody></table>
	
<table id=tr_login>
  <tbody>
  <tr>
    <td align=left>卡号/手机号： </td>
    <td align=left><input style="WIDTH: 90px;" id=txt_username class=form value=" " onclick="document.getElementById('txt_username').value=''" type=text name=username> 
    </td>
    <td align=left>&nbsp; 密码： </td>
    <td align=left><input style="WIDTH: 80px;" id=txt_userpass class=form type=password name=password > </td>
    <td align=left>&nbsp; 验证码： </td>
    <td align=left><input style="WIDTH: 60px" class=form name=yanzm type=text value="" maxLength=4 size=10> </td>
    <td align=left><a style="CURSOR:hand" onclick="javascript:var dt=new Date();document.getElementById('code').src='../image.jsp?dt='+dt;" title="看不清楚，换个图片"> 
    <img style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;" id=code border=0 name=checkcode src="../image.jsp"> </a></td>
    <td align=left><input id=btn_login class=cx_button value=" " type=submit name=btn_login> 
    </td></tr></tbody>
 </table>
 </form>
 <%
 }
 %>
    </div></div>
</div>
<!--评论结束-->
</div>
<div class=left_bottom></div></div>
<div id=Right>
<div class=card>
<div class=card_top>
<div class=heatitle><h6>优惠券推荐<font><a href="recommend_more.jsp">更多&gt;&gt;</a></font></h6> </div>
</div>
</div>
<div class=card_mid>
<%
Coupon coupon = new Coupon(globa);
	Vector<Coupon> vctcoup = coupon.list(" where intrecommend='1' and '"+Format.getDateTime()+"'>dtactivetime and '"+Format.getDateTime()+"'<dtexpiretime ",0,0);
	int k =1;
	for(int i=0;i<vctcoup.size();i++)
	{   
		if(k++ >8){break;}
		Coupon coupon2 = vctcoup.get(i);
		if(coupon2.getStrSmallImg()!=null&&coupon2.getStrSmallImg().length()>0)
		{
	%>
<div class=tuangou_index>
<div class=card_img> <a href="couponinfo.jsp?strid=<%=coupon2.getStrId() %>" target="_blank"><img src=<%="../coupon/images/"+coupon2.getStrSmallImg()%> width="126" height="89" border="0" title="<%=coupon2.getStrName()%>" /></a></div>
<div class=card_js><%=coupon2.getStrName() %><br />
开始日期：<%=coupon2.getDtActiveTime().substring(0,10) %><br />
截止日期：<%=coupon2.getDtExpireTime().substring(0,10) %></div>
</div>
<div class=card_line></div>
<%
}
}
 %>
</div>
<div class=card_bottom></div></div>
</div>
<iframe style="HEIGHT: 260px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<%
globa.closeCon();
 %>
</body>
</html>
