package com.ejoysoft.util;

import java.sql.Date;
import java.util.Calendar;

/**
 * ���ڴ��?����<br>
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-11 11:31 $
 */

public class DateUtil {

    public DateUtil() {
    }

    public static Date getCurDate() {
        Date mydate = new Date(new java.util.Date().getTime());
        return mydate;
    }

    /**
     * Converts a string in JDBC date escape format to
     * a <code>Date</code> value.
     *
     * @param s date in format "yyyy-mm-dd hh:mm:ss"
     * @return a <code>java.sql.Date</code> object representing the
     *         given date
     */
    public static java.util.Date getDate(String s) {

        int year;
        int month;
        int day;
        int firstDash;
        int secondDash;
        int hour = 0;
        int min = 0;
        int sec = 0;
        int space;

        if (s == null || s.equals("")) throw new java.lang.IllegalArgumentException();

        firstDash = s.indexOf('-');
        secondDash = s.indexOf('-', firstDash + 1);
        if ((firstDash > 0) & (secondDash > 0) & (secondDash < s.length() - 1)) {
            year = Integer.parseInt(s.substring(0, firstDash).trim()) - 1900;
            month = Integer.parseInt(s.substring(firstDash + 1, secondDash).trim()) - 1;
            day = Integer.parseInt(s.substring(secondDash + 1, secondDash + 3).trim());
        } else {
            throw new java.lang.IllegalArgumentException();
        }
        if (s.length() > 10) {
            space = s.indexOf(" ");
            firstDash = s.indexOf(":");
            secondDash = s.indexOf(":", firstDash + 1);
            if ((firstDash < 0))
                hour = Integer.parseInt(s.substring(space).trim());
            if ((firstDash > 0) & (secondDash - space > 0))
                hour = Integer.parseInt(s.substring(space, firstDash).trim());
            if ((firstDash > 0) & (secondDash > 0))
                min = Integer.parseInt(s.substring(firstDash + 1, secondDash).trim());

            if ((firstDash > 0) & (secondDash > 0) & (secondDash < firstDash - 1))
                sec = Integer.parseInt(s.substring(secondDash + 1).trim());
        }

        return new java.util.Date(year, month, day, hour, min, sec);

    }

    public static Date getDate(String yearstr, String monthstr, String datestr) {
        int date = Integer.parseInt(datestr);
        int month = Integer.parseInt(monthstr);
        int year = Integer.parseInt(yearstr);
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(year, month - 1, date);
        Date mydate = new Date(rightNow.getTime().getTime());
        return mydate;
    }

    public static Date getDate(int year, int month, int date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(year, month - 1, date);
        Date mydate = new Date(rightNow.getTime().getTime());
        return mydate;
    }

    public static int getYear(java.util.Date newDate) {
        if (newDate == null || newDate.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(newDate);
            return mycal.get(1);
        }
    }

    public static int getMonth(java.util.Date newDate) {
        if (newDate == null || newDate.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(newDate);
            return mycal.get(2) + 1;
        }
    }

    public static int getDate(java.util.Date newDate) {
        if (newDate == null || newDate.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(newDate);
            return mycal.get(5);
        }
    }

    //�����׼��ʽ
    public static String format0 = "yyyy-MM-dd";
    public static String format1 = "yyyy-MM-dd hh:mm:ss";
    public static String format2 = "yyyy-MM-dd hh:mm";
    public static String format3 = "yyyy��MM��dd��";
    public static String format4 = "yyyy��MM��dd��hhʱmm����ss��";
    public static String format5 = "yyyy��MM��dd��hhʱmm��";
    public static String format6 = "yyyy��MM��";
    public static String format7 = "yyyy-MM";
    public static String format8 = "yyyy/MM/dd";


    /**
     * ���ܶ��壺��ʽ����
     * @param date     ����
     * @param format  ��ʽ
     * @return
     */
    public static String formatDate(java.util.Date date, String format) {
        if (date == null) return "";
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String formatDate(java.sql.Date date, String format) {
        if (date == null) return "";
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String formatDate(String date, String format) {
        if (date == null) return "";

        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat(format);
        return sf.format(getDate(date));
    }


    //�Զ����ʽ������
    public static String getFormatDate(String format) {
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat(format);
        return sf.format(new java.util.Date());
    }

    //Ĭ�ϸ�ʽ������
    public static String getFormatDate() {

        return getFormatDate(format1);
    }

    public static void main(String[] args) {

    }

}
