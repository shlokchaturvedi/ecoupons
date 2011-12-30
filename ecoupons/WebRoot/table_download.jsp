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
	String strFileName = "1.xls";
    response.setContentType("APPLICATION/*");
    response.setHeader( "Content-Disposition", "attachment;filename="  + strFileName);
    ServletOutputStream output = response.getOutputStream();
    output.write(sb.toString().getBytes());
%>