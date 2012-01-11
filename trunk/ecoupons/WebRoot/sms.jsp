<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.ejoysoft.common.SendSms" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <%
            	String PostData = "sname=dlqdcs02&spwd=SjkQ35RS&scorpid=&sprdid=1012818&sdst=13955150557&smsg="+java.net.URLEncoder.encode("短信内容","utf-8");
                                    //out.println(PostData);
                                    String ret = SendSms.SMS(PostData, "http://chufa.lmobile.cn/submitdata/service.asmx/g_Submit");
                                    out.println(ret);
                                    //请自己反序列化返回的字符串并实现自己的逻辑
            %>

        </h1>
    </body>
</html>
