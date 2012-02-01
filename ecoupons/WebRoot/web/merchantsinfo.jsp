<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<iframe style="HEIGHT: 130px" marginwidth=0 marginheight=0 src="top.htm" frameborder=0 width="100%" scrolling=no></iframe>

<!--正文部分-->
<div id=Main>
<div id=Left>
<div class=left_top>
		<div class="hotList_sf">商户列表</div>
</div>
<div class=left_mid>
<div class=show>
  <div class=show_mid>

<div class=mer_mid>
<div class=mer_img><a href="#" target=_blank><img src="images/s_201103280922392386.jpg" width=112 height=110 border="0" /></a></div>
<div class=mer_info>
<div class=mer_headtitle><a href="#" target=_blank>名店街购物</a></div>

<div class=clearfloat></div>
<div class=mer_text_left>
<table border=0 width=100%>
  <tbody>
  <tr>
    <td width=267 height="26">*行业：<a href="#">生活购物</a> </td>
    <td width=253 height="26">*地址：<a href="#">生活购物</a></td>
  </tr>
  <tr>
    <td height="26">* 联系电话：13783582891<a href="#"></a> </td>
    <td height="26">*联系人：<a href="#">张先生</a></td>
   </tr>
  </tbody></table>
<br/></div>
</div>
<div class=clearfloat></div></div>



<div class=show_line></div>
<div class=mer>
  <div class=mer_js>
    <p><b>商家简介：</b><br/></p>
    <p>  大学路华城国际2号楼11号
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
<div class="zs_img"><img src="images/crad2.jpg"/></div>
<div class="zs_img"><img src="images/crad2.jpg"/></div>
<div class="zs_img"><img src="images/crad2.jpg"/></div>
<div class="zs_img"><img src="images/crad2.jpg"/></div>

</div>
<div class=zs_bottom></div></div><!--更多相关-->
<!--发表评论-->
<div style="PADDING-LEFT: 23px; width:578px;">
<div style="WIDTH: 100%" class=ny_left>
<div style="FONT-SIZE: 12px" class=line_gray>
<div class=mp_auto>
<div style="HEIGHT: 20px" class="box bold word_gra sp_nav_bg jianju13 ">网友评论：(已有<span class=red>2</span>条评论)</div>
<div style="HEIGHT: 1px; BORDER-TOP: #ebebeb 1px solid"></div>
<div style="PADDING-LEFT: 5px"><br/>
<div style="HEIGHT: 50px">
<div style="WIDTH: 10%; FLOAT: left; HEIGHT: 45px"><img alt="" src="images/201.jpg" width=45 height=45 /> </div>
<div style="LINE-HEIGHT: 18px; WIDTH: 80%; FLOAT: left; HEIGHT: 33px"><span style="COLOR: #001c55">rang：</span>
<span style="COLOR: gray">真实物美价廉，还赚积分</span> </div>
</div>
<div style="BORDER-BOTTOM: #808080 1px dotted; HEIGHT: 1px; CLEAR: both"></div><br/>
<div style="HEIGHT: 50px">
<div style="WIDTH: 10%; FLOAT: left; HEIGHT: 45px">
<img alt="" src="images/201.jpg" width=45 height=45 /> </div>
<div style="LINE-HEIGHT: 18px; WIDTH: 80%; FLOAT: left; HEIGHT: 33px">
<span style="COLOR: #001c55">&nbsp;fwer：</span><span style="COLOR: gray">物美价廉</span> 
</div>
</div>
</div></div></div></div>
<div class="clr height_05"></div>
<div></div>
<div class="box  line_gray jianju9b">
<div class="box bold word_gra sp_nav_bg jianju13 ">发表评论：</div>
<div class="box jianju13 jianju2a word_12px">
<table border=0 width="100%">
  <tbody>
  <tr>
    <td align=center >内容： </td>
    <td colspan=7>
    <label><textarea style="WIDTH: 500px; HEIGHT: 100px" id=txt_con class=form rows=2 cols=20 name=txt_con></textarea></label>
    <span id=span_sub></span></td></tr></tbody></table>
<table id=tr_login>
  <tbody>
  <tr>
    <td align=left>用户名： </td>
    <td align=left><input style="WIDTH: 90px" id=txt_username class=form value=用户名 type=text name=txt_username /> 
    </td>
    <td align=left>&nbsp; 密码： </td>
    <td align=left><input style="WIDTH: 80px" id=txt_userpass class=form type=password name=txt_userpass /> </td>
    <td align=left>&nbsp; 验证码： </td>
    <td align=left><input style="WIDTH: 60px" id=txt_yzm class=form type=text name=txt_yzm /> </td>
    <td align=left><img style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;"id=checkcode border=0 name=checkcode  alt=看不清楚，换个图片 src="images/Code.jpg" /> </td>
    <td align=left><input id=btn_login class=cx_button value=" " type=submit name=btn_login /> 
    </td></tr></tbody></table>
<table style="DISPLAY: none; MARGIN-LEFT: 100px" id=tr2>
  <tbody>
  <tr>
    <td align=right>验证码： </td>
    <td align=left><input style="WIDTH: 150px" id=txt_com_yzm class=form type=text name=txt_com_yzm /> </td>
    <td align=left><img style="BORDER: #ffffff 1px solid; WIDTH: 65px; HEIGHT: 20px; CURSOR: pointer;"  id=Img2 border=0 name=checkcode  alt=看不清楚，换个图片 src="images/Code_Comment.jpg" /> </td>
    <td align=left><input id=btn_Sub class=cx_button value=发表评论 type=submit name=btn_Sub /> 
    </td></tr></tbody></table></div></div>

</div>
<!--评论结束-->


</div>
<div class=left_bottom></div></div>
<div id=Right>
<div class=card>
<div class=card_top>
<div class=heatitle><h6>前沿团购</h6>
</div>
</div>
<div class=card_mid>

<div class=tuangou_index>
<div class=card_img><img src="images/crad2.jpg" width="126" height="89"/></div>
<div class=card_js>乐界KTV<br />
开始日期：2012-01-01<br />
截止日期：2012-02-20</div>
</div>
<div class=card_line></div>

<div class=tuangou_index>
<div class=card_img><img src="images/crad1.jpg"/></div>
<div class=card_js>乐界KTV<br />
开始日期：2012-01-01<br />
截止日期：2012-02-20</div>
</div>
<div class=card_line></div>

<div class=tuangou_index>
<div class=card_img><img src="images/crad2.jpg" width="126" height="89"/></div>
<div class=card_js>乐界KTV<br />
开始日期：2012-01-01<br />
截止日期：2012-02-20</div>
</div>
<div class=card_line></div>

<div class=tuangou_index>
<div class=card_img><img src="images/crad1.jpg"/></div>
<div class=card_js>乐界KTV<br />
开始日期：2012-01-01<br />
截止日期：2012-02-20</div>
</div>
<div class=card_line></div>


</div>
<div class=card_bottom></div></div>
</div>
</div>











<iframe style="HEIGHT: 340px" marginwidth=0 marginheight=0 src="bottom.htm" frameborder=0 width="100%" scrolling=no></iframe>
</body>
</html>
