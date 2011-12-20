package com.ejoysoft.common;

import com.ejoysoft.common.exception.IdObjectException;
import com.ejoysoft.util.ParamUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-5-25
 * Time: 17:38:32
 * To change this template use Options | File Templates.
 */
public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setContentType("text/html;charset=GBK");
//        response.setContentType("text/html;charset="+gbk);
        response.setContentType("APPLICATION/*");
        ServletContext application = getServletContext();
        Globa globa = new Globa();
        globa.initialize(application);
        String pathType = request.getParameter("pathType");
        String path;
        if ("abs".equals(pathType))
        	path = request.getParameter("strPath");
        else
        	path = globa.getPropValue("PATH_ROOT_PHYSIC") + ParamUtil.getString(request, "strPath", "");
        String name = request.getParameter("strName");
        //String strId = request.getParameter("strId");
        String strId=ParamUtil.getString(request,"strId","");
        //String strType = request.getParameter("strType");
        String strType=ParamUtil.getString(request,"strType","");
        if (strId.equals("")) {
             File file = new File(path);
            if (!file.exists()) {
                throw new NullPointerException("��ǰ���ص��ļ������ڣ�");
            }

//            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(name, "utf-8") + "\"");
            response.setHeader( "Content-Disposition", "attachment;filename="  + new String( name.getBytes("gbk"), "ISO8859-1" ) );

            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(path));
            ServletOutputStream output = response.getOutputStream();
            // д������Ϣ
            byte[] bytBuffer = new byte[1024 * 8];
            int i;
            while ((i = fileInputStream.read(bytBuffer)) != -1) {
                output.write(bytBuffer, 0, i);
            }
            fileInputStream.close();
            output.flush();
            output.close();
        } 
        else if(strType.equals("cert")){
                if(strId.equals(""))
                   throw new NullPointerException("���������ϢidΪ�գ������Ѿ�������");
               String where="where strId='"+strId+"'";
//                UserCert cert0=new UserCert(globa,false).show(where);
//                if(cert0==null){
//                    globa.closeCon();
//                    throw new NullPointerException("���������Ϣid='"+strId+"'����Ϊ�գ��������Ϣ�������Ϣ");
//                }
//            String strTitle =cert0.getStrCertName();
//            path=cert0.getStrCertPath()+strTitle;
//            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(strTitle, "utf-8") + "\"");
          /*
            ServletOutputStream output = response.getOutputStream();
            // д������Ϣ
            byte[] bytBuffer = content.getBytes();
            output.write(bytBuffer, 0, bytBuffer.length);
            output.flush();
            output.close();
            */
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(path));
            ServletOutputStream output = response.getOutputStream();
            // д������Ϣ
            byte[] bytBuffer = new byte[1024 * 8];
            int i;
            while ((i = fileInputStream.read(bytBuffer)) != -1) {
                output.write(bytBuffer, 0, i);
            }
            fileInputStream.close();
            output.flush();
            output.close();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
