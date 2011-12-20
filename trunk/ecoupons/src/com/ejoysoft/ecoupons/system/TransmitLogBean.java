package com.ejoysoft.ecoupons.system;

import com.ejoysoft.common.Globa;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2007-4-5
 * Time: 16:56:20
 * To change this template use Options | File Templates.
 */
public class TransmitLogBean {

    public void TransmitLogBean() {
    }


    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException, ServletException {
        Globa globa = new Globa();

        try {
            //transmit log
            String sql = "INSERT into  t_sy_logHistory " +
                    "SELECT * FROM t_sy_log WHERE sysdate-dOccurDate>=30 ";    //For Oracle
//                    "SELECT * FROM t_log "; //For SQLServer2000
//                    "SELECT * FROM t_log WHERE DATEDIFF(day, t_log.dOccurDate, getdate())<=30"; //For SQLServer2000
            System.out.println("sql in TransmitLogBean is =="+sql);
//            globa.db.setAutoCommit(false);
            globa.db.executeUpdate(sql);
            //delete log transmitted
            sql = "DELETE t_sy_log WHERE sysdate-dOccurDate>=30";//FOR Oracle
//            sql = "DELETE t_log WHERE DATEDIFF(day, t_log.dOccurDate, getdate()) <= 30";//For SQLServer2000
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
