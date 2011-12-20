package com.ejoysoft.ecoupons.system;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-26
 * Time: 17:28:09
 * To change this template use Options | File Templates.
 */
public class TransmitLog extends javax.servlet.GenericServlet {

    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException, ServletException {
        com.ejoysoft.common.Globa globa = new com.ejoysoft.common.Globa();
        globa.initialize(getServletContext());
        try {
            //transmit log
//            String sql = "INSERT into  t_logHistory " +
//                    "SELECT * FROM t_log WHERE sysdate-dOccurDate>=0 ";
            String sql = "INSERT t_sy_logHistory " +
                    "SELECT * FROM t_sy_log WHERE DATEDIFF(day, t_sy_log.dOccurDate, getdate())<=30";
            globa.db.setAutoCommit(false);
            globa.db.executeUpdate(sql);
            //delete log transmitted
            sql = "DELETE t_log WHERE DATEDIFF(day, t_sy_log.dOccurDate, getdate()) <= 30";
//            sql = "DELETE t_log WHERE sysdate-dOccurDate>=0";
            globa.db.executeUpdate(sql);
            globa.db.commit();
            globa.closeCon();
            System.out.println("ת����־�ɹ�����");
        } catch (SQLException e) {
            if (globa.db != null) {
                try {
                    globa.db.rollback();
                    globa.db.closeCon();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("ת����־ʱ�������?");
            e.printStackTrace();
        }
    }
}