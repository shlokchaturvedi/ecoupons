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
		 try {
			 String strFileType = request.getParameter("strFileType");
			 String strFileName = request.getParameter("strFileName");
//			 strFileName = "框架.jpg";
//			 strFileType = "template";
			 String strPath = request.getSession().getServletContext().getRealPath("");
			 if (strFileType.equals("shop")) {
				 strPath += "\\shop\\images\\";
			 } else if (strFileType.equals("coupon")) {
				 strPath += "\\coupon\\images\\";
			 } else if (strFileType.equals("ad")) {
				 strPath += "\\terminal\\advertisement\\";
			 }else if (strFileType.equals("template")) {
				 String strModuleOfTempl = request.getParameter("strModuleOfTempl");
//				 strModuleOfTempl = "home";
				 strPath += "\\terminal\\template\\";
				 if(strModuleOfTempl!=null && (strModuleOfTempl.equals("top") || strModuleOfTempl.equals("bottom")))
				 {
					 strPath += strModuleOfTempl+"\\";
				 }else if (strModuleOfTempl!=null && (strModuleOfTempl.equals("home") || strModuleOfTempl.equals("shopInfo")|| strModuleOfTempl.equals("shop")|| strModuleOfTempl.equals("coupon")|| strModuleOfTempl.equals("myInfo")|| strModuleOfTempl.equals("nearshop")|| strModuleOfTempl.equals("ad")|| strModuleOfTempl.equals("waitdownload")|| strModuleOfTempl.equals("waitlogin"))) {
					 strPath += "\\middle\\"+strModuleOfTempl+"\\";
				}
				 else {
					 throw new ServletException("strModuleOfTempl参数有误：" + strFileType);
				}
			 }
			 else {			 
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
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	 }
}
