<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@page import="java.util.Vector"%>
   <%@ include file="../include/jsp/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<LINK rel=stylesheet type=text/css 
href="css/HomeAdv.css">
<META name=GENERATOR content="MSHTML 8.00.6001.19170">
</head>
<body>
<DIV><INPUT id=__VIEWSTATE 
value=/wEPDwUKLTU2NzgzNzU1NmRkQSb+zRUw7aolg2/RtctQMgt0bp4= type=hidden 
name=__VIEWSTATE> </DIV>
<IMG style="DISPLAY: none" src="images/lijin.jpg">
<SCRIPT language=javascript type=text/javascript>
    var imgWidth =535 ;
    var imgHeight =<%=application.getAttribute("COUPON_LARGE_IMG_HEIGHT")%>*535/<%=application.getAttribute("COUPON_LARGE_IMG_WIDTH")%> ;
    var textFromHeight = 0;
    var textStyle = "f12";
    var textLinkStyle = "p1";
    var buttonLineOn = "#f60";
    var buttonLineOff = "#b65063";
    var TimeOut = 5000;
    var imgUrl = new Array();
    var imgLink = new Array();
    var imgtext = new Array();
    var imgAlt = new Array();
    var adNum = 0;
    var theTimer = 0;
    var tt = 1;

    document.write('<style type="text/css">');
    document.write('#focuseFrom{width:' + (imgWidth + 2) + ';margin: 0px; padding:0px;height:' + (imgHeight + textFromHeight) + 'px; overflow:hidden;}');
    document.write('#txtFrom{height:' + textFromHeight + 'px;line-height:' + textFromHeight + 'px;width:' + imgWidth + 'px;overflow:hidden;}');
    document.write('#imgTitle{width:' + imgWidth + ';top:-' + (textFromHeight + 16) + 'px;height:18px}');
    document.write('.axx{padding:1px 7px;border-left:#cccccc 1px solid;}');
    document.write('a.axx:link,a.axx:visited{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#666;}');
    document.write('a.axx:active,a.axx:hover{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#999;}');
    document.write('.bxx{padding:1px 7px;border-left:#cccccc 1px solid;}');
    document.write('a.bxx:link,a.bxx:visited{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#D34600;}');
    document.write('a.bxx:active,a.bxx:hover{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#D34600;}');
    document.write('</style>');
    document.write('<div id="focuseFrom">');

     <%
     Coupon coupon=new Coupon(globa);
     Vector<Coupon>vctCoupons=coupon.list(" where intRecommend=1 and dtactivetime <=now() and dtexpiretime >=now() order by dtcreatetime  desc limit 6",0,0);
     for(int i=0;i<vctCoupons.size();i++)
     {
     %>
     tt = <%=i+1%>;
    imgUrl[tt] = '../coupon/images/<%=vctCoupons.get(i).getStrSmallImg() %>';
    imgtext[tt] = '<A HREF="#"  class="' + textLinkStyle + '"></A>';
    imgLink[tt] = 'couponinfo.jsp?strid=<%=vctCoupons.get(i).getStrId() %>';
    imgAlt[tt] = '<%out.print(vctCoupons.get(i).getStrName());%>';
    <%}%>
    
	
/*  tt = 7;
    imgUrl[tt] = '../images/womaiwang.jpg';
    imgtext[tt] = '<A HREF="#" TARGET="_blank" class="' + textLinkStyle + '"></A>';
    imgLink[tt] = '#';
    imgAlt[tt] = '我买网';
	
   tt = 8;
  imgUrl[tt] = '../images/ahuatian.jpg';
  imgtext[tt] = '<A HREF="#" TARGET="_blank" class="' + textLinkStyle + '"></A>';
  imgLink[tt] = '#';
  imgAlt[tt] = '阿华田';

    tt = 9;
  imgUrl[tt] = '../images/yinhua.jpg';
  imgtext[tt] = '<A HREF="#" TARGET="_blank" class="' + textLinkStyle + '"></A>';
  imgLink[tt] = '#';
  imgAlt[tt] = '樱之花热力贴';
  
 tt = 10;
  imgUrl[tt] = '../images/shouji.jpg';
  imgtext[tt] = '<A HREF="#" TARGET="_blank" class="' + textLinkStyle + '"></A>';
  imgLink[tt] = '#';
  imgAlt[tt] = '手机客户端';  
  
      tt = 11;
  imgUrl[tt] = '../images/jiu.jpg';
  imgtext[tt] = '<A HREF="#" TARGET="_blank" class="' + textLinkStyle + '"></A>';
  imgLink[tt] = '#';
  imgAlt[tt] = 'VELO卡以旧换新';   */


    function changeimg(n) {
        adNum = n;
        window.clearInterval(theTimer);
        adNum = adNum - 1;
        nextAd();
    }
    function goUrl() {
        window.open(imgLink[adNum], '_blank');

    }


    if (navigator.appName == "Netscape") {
        document.write('<style type="text/css">');
        document.write('.buttonDiv{height:4px;width:21px;}');
        document.write('</style>');
        function nextAd() {
            if (adNum <(imgUrl.length - 1)) adNum++;
            else adNum = 1;
            theTimer = setTimeout("nextAd()", TimeOut);
            document.images.imgInit.src = imgUrl[adNum];
            document.images.imgInit.alt = imgAlt[adNum];
            document.getElementById('focustext').innerHTML = imgtext[adNum];
            document.getElementById('imgLink').href = imgLink[adNum];

        }
        document.write('<a id="imgLink" href="' + imgLink[1] + '" target=_blank class="p1"><img src="' + imgUrl[1] + '" id=imgInit name="imgInit" width=' + imgWidth + ' height=' + imgHeight + ' border=1 alt="' + imgAlt[1] + '" class="imgClass"></a><div id="txtFrom"><span id="focustext" class="' + textStyle + '">' + imgtext[1] + '</span></div>')
        document.write('<div id="imgTitle">');
        document.write('<div id="imgTitle_down">');

        for (var i = 1; i < imgUrl.length; i++) { document.write('<a href="javascript:changeimg(' + i + ')" class="button" style="cursor:hand" title="' + imgAlt[i] + '">' + i + '</a>'); }

        document.write('</div>');
        document.write('</div>');
        document.write('</div>');
        nextAd();
    }


    else {
        var count = 0;
        for (i = 1; i < imgUrl.length; i++) {
            if ((imgUrl[i] != "") && (imgLink[i] != "") && (imgtext[i] != "") && (imgAlt[i] != "")) {
                count++;
            } else {
                break;
            }
        }
        function playTran() {
            if (document.all)
                document.getElementById("imgInit").filters.revealTrans.play();
        }
        var key = 0;
        function nextAd() {
            if (adNum < count) adNum++;
            else adNum = 1;

            if (key == 0) {
                key = 1;
            } else if (document.all) {
                document.getElementById("imgInit").filters.revealTrans.Transition = Math.random() * 23;
                document.getElementById("imgInit").filters.revealTrans.apply();
                playTran();
            }
            document.images.imgInit.src = imgUrl[adNum];
            document.images.imgInit.alt = imgAlt[adNum];
            document.getElementById('link' + adNum).style.background = buttonLineOn;
            for (var i = 1; i <= count; i++) {
                if (i != adNum) { document.getElementById('link' + i).style.background = buttonLineOff; }
            }
            focustext.innerHTML = imgtext[adNum];
            theTimer = setTimeout("nextAd()", TimeOut);
        }
        document.write('<a target=_self href="javascript:goUrl()"><img style="FILTER: revealTrans(duration=1,transition=5);" src="javascript:nextAd()" width=' + imgWidth + ' height=' + imgHeight + ' border=0 vspace="0" id=imgInit name=imgInit class="imgClass"></a>');
        document.write('<div id="txtFrom"><span id="focustext" class="' + textStyle + '"></span></div>');
        document.write('<div id="imgTitle">');
        document.write(' <div id="imgTitle_down"> <a class="trans"></a>');

        for (var i = 1; i < imgUrl.length; i++) { document.write('<a id="link' + i + '"  href="javascript:changeimg(' + i + ')" class="button" style="cursor:hand" title="' + imgAlt[i] + '" onFocus="this.blur()">' + i + '</a>'); }

        document.write('</div>');
        document.write('</div>');
        document.write('</div>');
        changeimg(1);
    }

</SCRIPT>
</body>
</html>
<%@ include file="../include/jsp/footer.jsp"%>