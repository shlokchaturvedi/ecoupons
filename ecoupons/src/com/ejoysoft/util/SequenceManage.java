package com.ejoysoft.util;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.sql.*;
import java.text.SimpleDateFormat;

public final class SequenceManage {
    private static Log log = LogFactory.getLog(SequenceManage.class);

    public static final String PATTERN_YEAR = "yyyy";
    public static final String PATTERN_MONTH = "yyyyMM";
    public static final String PATTERN_DATE = "yyyyMMdd";
    private static final int iSign = 1000000001; //10λ;


    public static String generateID(Connection conn, String table, String field, String pattern, int len) {
        SequenceManage sm = new SequenceManage();
        String sql = "SELECT  TOP 1 *  FROM  " + table + "  WHERE " + field + " like '%" + sm.getStringDate(pattern) + "%' ORDER BY " + field + "  DESC ";
        String sid = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) sid = rs.getString(1);
            rs.close();
            return sm.createTimeID(pattern, sid, len);

        } catch (SQLException e) {
            log.error(e.toString());
            log.error("����ʱ���ʧ��");
            return null;
        }
    }


    public static String generateID(Connection conn, String table, String field) {
        return generateID(conn, table, field, PATTERN_DATE, 4);
    }

    /**
     * ����ʱ��ID��ʹֵ+1
     * @param pattern
     * @param sDefault  ��ʼ��ֵ
     * @param iFreeDigit  ��������λ��<=10
     * @return  �ַ�
     */
    private String createTimeID(String pattern, String sDefault, int iFreeDigit) {
        if (sDefault == null || sDefault.trim().equals("")) {
            return getStringDate(pattern) + amendDigit(iFreeDigit, 0); //������ʼֵ
        }
        String strFree = sDefault.substring(iFreeDigit, sDefault.length());//��ȡ�������ִ�

        return getStringDate(pattern) + amendDigit(iFreeDigit, strFree);
    }


    /**
     * ��ȡ��ǰ����
     * @return
     */
    private String getStringDate(String pattern) {
        if (!pattern.equals(PATTERN_DATE) && !pattern.equals(PATTERN_YEAR) && !pattern.equals(PATTERN_MONTH))
            pattern = PATTERN_DATE;

        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(new java.util.Date());
    }

    /**
     * ����������λ��ʹֵ+1
     * @param iFreeDigit  ��������λ��<=10
     * @param iDefault  ��ʼ��ֵ
     * @return  �ַ�
     */
    private String amendDigit(int iFreeDigit, int iDefault) {
        int iTemp = 0;
        if (iFreeDigit < 10) {
            iTemp = iSign;
        } else {
        }
        iFreeDigit += 1;
        iTemp += iDefault;
        String strTemp = new Integer(iTemp).toString();
        strTemp = strTemp.substring(11 - iFreeDigit, 10);
        return strTemp;
    }

    /**
     * ����������λ��
     * @param iFreeDigit  ��������λ��<=10
     * @param sDefault  ��ʼ�ִ�
     * @return  �ַ�
     */
    private String amendDigit(int iFreeDigit, String sDefault) {
        String strTemp = "";
        int iLength = sDefault.length();
        if (iLength < iFreeDigit) {
        }
        strTemp = sDefault.substring(iLength - iFreeDigit, iLength);
        strTemp = amendDigit(iFreeDigit, Integer.parseInt(strTemp));
        return strTemp;
    }

    public static void main(String[] args) {
        String sqlDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        String sqlURL = "jdbc:microsoft:sqlserver://127.0.0.1:1433";
        String sqlUsr = "";
        String sqlPwd = "";
        try {

            Class.forName(sqlDriver);
            Connection conn = DriverManager.getConnection(sqlURL, sqlUsr, sqlPwd);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            //log.fatal("��l��ʧ��\n" + e.toString());
        }
    }


}
