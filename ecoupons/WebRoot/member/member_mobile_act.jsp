<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.Member"%>
<%@page import="com.ejoysoft.ecoupons.business.Recharge"%>
<%@page import="java.util.Vector"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
Member  member=null;
Member obj=new Member(globa);
String  strStartId=ParamUtil.getString(request,"strStartId","");
String  strEndId=ParamUtil.getString(request,"strEndId","");
String tWhere=" WHERE 1=1";
if (!strStartId.equals("")&&!strEndId.equals("")) {
	tWhere += " and strCardNo >= '" + strStartId + "' and strCardNo<= '"+ strEndId +"' ";
}
tWhere += " ORDER BY strCardNo";
Vector<Member> vctObj=obj.list(tWhere,0,0);
StringBuffer sb = new StringBuffer();
sb.append("<table border=1>");
sb.append("<tr><td>手机</td><td>姓名</td><td>卡号</td></tr>");
if (vctObj.size() != 0)
{
	for (int i = 0; i < vctObj.size(); i++)
	{
		sb.append("<tr><td>" + vctObj.get(i).getStrMobileNo() + "</td><td>" + vctObj.get(i).getStrName() + "</td><td>"
				+ vctObj.get(i).getStrCardNo() + "</td>");
	}
}
sb.append("</table>");
String strFileName = "手机号码批量导出表_"+strStartId+"_"+strEndId+".xls";
response.setContentType("APPLICATION/*");
response.setHeader( "Content-Disposition", "attachment;filename="  + new String( strFileName.getBytes("gbk"), "ISO8859-1" ));
ServletOutputStream output = response.getOutputStream();
output.write(sb.toString().getBytes());
	//关闭数据库连接对象
	globa.closeCon();
%>