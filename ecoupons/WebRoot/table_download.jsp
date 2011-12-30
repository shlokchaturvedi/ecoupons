<%@ page contentType="text/html;charset=UTF-8"%>
<%
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	for (int i = 0; i < 2; i++) {
		sb.append("<tr>");
		for (int j = 0; j < 3; j++) {
			sb.append("<td>" + i + j + "</td>");
		}
		sb.append("</tr>");
	}
	sb.append("</table>");
	String strFileName = "2011年商家统计表.xls";
    response.setContentType("APPLICATION/*");
    response.setHeader( "Content-Disposition", "attachment;filename="  + new String( strFileName.getBytes("gbk"), "ISO8859-1" ));
    ServletOutputStream output = response.getOutputStream();
    output.write(sb.toString().getBytes());
%>