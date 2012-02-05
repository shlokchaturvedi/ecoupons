<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Vector,com.ejoysoft.ecoupons.system.User,
				com.ejoysoft.common.Constants,
				com.ejoysoft.ecoupons.system.SysUserUnit,
				com.ejoysoft.common.exception.NoRightException,
				com.ejoysoft.ecoupons.business.Shop,
				com.ejoysoft.common.exception.IdObjectException" %>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/merchantsinfo.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="css/comment.css" />
<title>商家详细</title>  
</head>
<body>
<iframe style="HEIGHT: 130px" marginwidth=0 marginheight=0 src="top.jsp" frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<div id=Main>
<div id=Left>
<div class=left_top>
<div class="hotList_sf">商户详细：<%=obj1.getStrBizName()+"-"+obj1.getStrShopName() %></div>
</div>
<div class=left_mid>
<div class=show>
  <div class=show_mid>

<div class=mer_mid>
<div class=mer_img>
<%
                if (obj1.getStrSmallImg().length() > 0) {
                %>
                  <img src="<%="../shop/images/" + obj1.getStrLargeImg() %>" width=<%=application.getAttribute("SHOP_LARGE_IMG_WIDTH") %> height=<%=application.getAttribute("SHOP_LARGE_IMG_HEIGHT") %>/>
                <%
                }
                else
                {
                %>
                 <img src="../shop/images/temp.jpg" width=<%=application.getAttribute("SHOP_LARGE_IMG_WIDTH") %> height=<%=application.getAttribute("SHOP_LARGE_IMG_HEIGHT") %>/>
                 <%}
                  %>
</div>
<div class=mer_info>
<div class=mer_headtitle><a href="#"><%=obj1.getStrBizName() %>—<%=obj1.getStrShopName()%></a></div>

<div class=clearfloat></div>
<div class=mer_text_left>
<table border=0 width=100%>
  <tbody>
  <tr>
    <td width=267 height="26">*行业：<%=obj1.getStrTradeName()%> </td>
    <td width=253 height="26">*地址：<%=obj1.getStrAddr()%></td>
  </tr>
  <tr>
    <td height="26">* 联系电话：<%=obj1.getStrPhone()%></td>
    <td height="26">*联系人：<%=obj1.getStrPerson()%></td>
   </tr>
  </tbody></table>
<br/></div>
</div>
<div class=clearfloat></div></div>
<div class=show_line></div>
<div class=mer>
  <div class=mer_js>
    <p><b>商家简介：</b><br/></p>
    <p>  <%=obj1.getStrIntro()%>
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
Vector<Coupon> vctCoupon = obj2.listByShopId("where strshopid='" + strId + "'");
if(vctCoupon!=null&&vctCoupon.size()!=0)
{
	int k=vctCoupon.size();
	for(int i=0;i<k;i++)
	{
		Coupon obj3 = vctCoupon.get(i);
		System.out.println(obj3.getStrSmallImg()+":ddd");
		if (obj3.getStrSmallImg()!=null && obj3.getStrSmallImg().length() > 0) {
         %>
         <div class="zs_img"><a href="couponinfo.jsp?strid=<%=obj3.getStrId() %>" target="_blank"><img src="<%="../coupon/images/" + obj3.getStrSmallImg() %>" /></a></div>
        <%
        }   
        else{
         %>
         <div class="zs_img"><a href="couponinfo.jsp?strid=<%=obj3.getStrId() %>" target="_blank"><img src="images/temp.jpg" /></a></div>
        <%
        }    	
	}	
} 
%>

</div>
<div class=zs_bottom></div></div><!--更多相关-->
</div>
<div class=left_bottom></div></div>
<div id=Right>
<div class=card>
<div class=card_top>
<div class=heatitle><h6>优惠券推荐 </h6>
</div>
</div>
<div class=card_mid>
<%
Coupon coupon = new Coupon(globa);
	Vector<Coupon> vctcoup = coupon.list(" where intrecommend='1'",0,0);
	int k =1;
	for(int i=0;i<vctcoup.size();i++)
	{   
		if(k++ >8){break;}
		Coupon coupon2 = vctcoup.get(i);
		if(coupon2.getStrSmallImg()!=null&&coupon2.getStrSmallImg().length()>0)
		{
	%>
<div class=tuangou_index>
<div class=card_img> <a href="couponinfo.jsp?strid=<%=coupon2.getStrId() %>" target="_blank"><img src=<%="../coupon/images/"+coupon2.getStrSmallImg()%> width="126" height="89"/></a></div>
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
</div>
<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.jsp" frameborder=0 width="100%" scrolling=no></iframe>
<%
globa.closeCon();
 %>
</body>
</html>
