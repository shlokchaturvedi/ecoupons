package com.ejoysoft.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {

	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String strFileType = request.getParameter("strFileType");
		 String strFileName = request.getParameter("strFileName");
		 String strPath = request.getSession().getServletContext().getRealPath("");
		 if (strFileType.equals("shop")) {
			 strPath += "\\shop\\images\\";
		 } else if (strFileType.equals("coupon")) {
			 strPath += "\\coupon\\images\\";
		 } else if (strFileType.equals("ad")) {
			 strPath += "\\terminal\\advertisement\\";
		 } else {			 
			 throw new ServletException("strFileType参数有误：" + strFileType);
		 }
		 FileInputStream fis = new FileInputStream(strPath + strFileName);
		 byte[] bytes = new byte[2048];
		 ServletOutputStream sos = response.getOutputStream();
		 int i;
		 while ((i = fis.read(bytes)) > 0) {
			 sos.write(bytes, 0, i);
		 }
		 fis.close();
		 sos.close();
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	 }
}
