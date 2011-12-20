package com.ejoysoft.util;

import java.io.PrintStream;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 * �ļ���ƣ�Functions.java
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-11 11:31 $
 */
public class Functions {

    public Functions() {
    }

    public static Date toDate(String datetime) {
        String dt[] = SplitString(datetime, "-");
        if (dt == null || dt.length != 3)
            return null;
        int idt[] = new int[3];
        for (int i = 0; i < idt.length; i++)
            idt[i] = toInt(dt[i]);

        Calendar c = Calendar.getInstance();
        c.set(idt[0], idt[1] - 1, idt[2]);
        return new Date(c.getTime().getTime());
    }

    public static Date DateFormat(String datetime) {
        String dt[] = SplitString(datetime, "-");
        if (dt == null || dt.length != 3)
            return null;
        int idt[] = new int[3];
        for (int i = 0; i < idt.length; i++)
            idt[i] = toInt(dt[i]);

        Calendar c = Calendar.getInstance();
        c.set(idt[0], idt[1] - 1, idt[2]);
        return new Date(c.getTime().getTime());
    }

    public static Date DateFormat(String format, String datetime) {
        SimpleDateFormat sdf = null;
        Date d = null;
        try {
            sdf = new SimpleDateFormat(format, Locale.CHINA);
            d = (Date) sdf.parse(datetime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    public static Date getTodayDate() {
        long date = System.currentTimeMillis();
        Date result = new Date(date);
        return result;
    }

    public static String getDateString(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            String result = "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(date.getTime()));
            int _year = calendar.get(1);
            int _month = calendar.get(2) + 1;
            int _date = calendar.get(5);
            int _week = calendar.get(7) - 1;
            result = String.valueOf(result) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(_year)))).append("\u5E74").append(_month).append("\u6708").append(_date).append("\u65E5").append(" ").append(WeekCn[_week]))));
            return result;
        }
    }

    public static String[] SplitString(String szSource, String token) {
        if (szSource == null || token == null)
            return null;
        StringTokenizer st1 = new StringTokenizer(szSource, token);
        String d1[] = new String[st1.countTokens()];
        for (int x = 0; x < d1.length; x++)
            if (st1.hasMoreTokens())
                d1[x] = st1.nextToken();

        return d1;
    }

    public static String getCountSql(String sql) {
        return getCountSql(sql, "record_count");
    }

    public static String getCountSql(String sql, String countfield) {
        if (sql == null)
            return null;
        String selectStr = "select ";
        sql = sql.trim();
        int selectIndex = sql.toLowerCase().indexOf(selectStr);
        if (selectIndex < 0)
            return null;
        selectStr = sql.substring(0, selectIndex + selectStr.length());
        String temp_sql = sql.substring(selectIndex + selectStr.length(), sql.length()).trim();
        String fromStr = " from ";
        int fromIndex = temp_sql.toLowerCase().indexOf(fromStr);
        if (fromIndex < 0)
            return null;
        int seperateIndex = temp_sql.indexOf(",");
        if (seperateIndex < 0 || seperateIndex > fromIndex)
            seperateIndex = temp_sql.indexOf(" ");
        String countField = temp_sql.substring(0, seperateIndex);
        int orderbyindex = temp_sql.toLowerCase().lastIndexOf(" order by ");
        if (orderbyindex > 0)
            fromStr = temp_sql.substring(fromIndex, orderbyindex);
        else
            fromStr = temp_sql.substring(fromIndex, temp_sql.length());
        if (countField != null && countField.indexOf("*") >= 0)
            countField = "*";
        if (countField != null && countField.toLowerCase().indexOf(" as") > 0)
            countField = countField.substring(0, countField.toLowerCase().indexOf(" as"));
        String count_sql = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(selectStr)))).append("count(").append(countField).append(") as ").append(countfield).append(fromStr)));
        return count_sql;
    }

    public static String to_iso_8859_1(String source) {
        if (source == null)
            return null;
        String s1;
        try {
            String s = new String(source.getBytes(), "iso-8859-1");
            String s2 = s;
            return s2;
        } catch (Exception uee) {
            s1 = null;
        }
        String s3 = s1;
        return s3;
    }

    public static String from_iso_8859_1(String source) {
        if (source == null)
            return null;
        String s1;
        try {
            String s = new String(source.getBytes("iso-8859-1"));
            String s2 = s;
            return s2;
        } catch (Exception uee) {
            s1 = null;
        }
        String s3 = s1;
        return s3;
    }

    public static String getDateString(Date dd) {
        if (dd == null)
            return "";
        String tds = dd.toString();
        String temp = "";
        for (int x = 0; x < tds.length(); x++)
            if (tds.charAt(x) != '-')
                temp = String.valueOf(temp) + String.valueOf(tds.charAt(x));

        return temp;
    }

    public static String getDateTimeString(Date dd) {
        if (dd == null) {
            return "";
        } else {
            String tds = dd.toString();
            Time tt = new Time(dd.getTime());
            tds = String.valueOf(tds) + String.valueOf(" ".concat(String.valueOf(String.valueOf(tt.toString()))));
            return tds;
        }
    }

    public static Date getDateAddDay(Date dd, int days) {
        if (dd == null) {
            return null;
        } else {
            Date dret = new Date(dd.getTime() + (long) (days * 60 * 60 * 1000 * 24));
            return dret;
        }
    }

    public static Date getDateAddMonth(Date date, int months) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(date.getTime()));
            int month = calendar.get(2);
            calendar.set(2, month + months);
            return new Date(calendar.getTime().getTime());
        }
    }

    public static void setDateParameter(Date date, int param, int value) {
        if (date == null) {
            return;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(date.getTime()));
            calendar.set(param, value);
            date.setTime(calendar.getTime().getTime());
            return;
        }
    }

    public static int getDateParameter(Date date, int param) {
        if (date == null) {
            return -1;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(date.getTime()));
            return calendar.get(param);
        }
    }

    public static String getNumberString(int i, int length) {
        String sret = String.valueOf(i);
        int lack = length - sret.length();
        if (lack == 0)
            return sret;
        if (lack > 0) {
            for (int x = 0; x < lack; x++)
                sret = "0".concat(String.valueOf(String.valueOf(sret)));

        } else {
            sret = sret.substring(0 - lack, sret.length());
        }
        return sret;
    }

    public static String getNumberString(long i, int length) {
        String sret = String.valueOf(i);
        int lack = length - sret.length();
        if (lack == 0)
            return sret;
        if (lack > 0) {
            for (int x = 0; x < lack; x++)
                sret = "0".concat(String.valueOf(String.valueOf(sret)));

        } else {
            sret = sret.substring(0 - lack, sret.length());
        }
        return sret;
    }

    public static String toFormInput(String oldValue) {
        if (oldValue == null)
            return null;
        String szTemp = "";
        int len = oldValue.length();
        for (int i = 0; i < len; i++)
            switch (oldValue.charAt(i)) {
                case 34: // '"'
                    szTemp = String.valueOf(String.valueOf(szTemp)).concat("&quot;");
                    break;

                default:
                    szTemp = String.valueOf(szTemp) + String.valueOf(oldValue.charAt(i));
                    break;
            }

        return szTemp;
    }

    public static String toPrice(double d) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(d);
    }

    public static String toPrice(float d) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(d);
    }

    public static String toPrice(float d, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }

    public static String toPrice(double d, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }

    public static String Replace(String source, String oldtext, String newtext) {
        if (source == null || oldtext == null || newtext == null)
            return null;
        String temp1 = source;
        String temp = "";
        for (int index = temp1.indexOf(oldtext); index >= 0; index = temp1.indexOf(oldtext)) {
            temp = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(temp)))).append(temp1.substring(0, index)).append(newtext)));
            temp1 = temp1.substring(index + oldtext.length(), temp1.length());
        }

        temp = String.valueOf(temp) + String.valueOf(temp1);
        return temp;
    }

    public static float toFloat(String value) {
        if (value == null)
            return 0.0F;
        String szTemp = "";
        for (int i = 0; i < value.length(); i++)
            if (value.charAt(i) != ',')
                szTemp = String.valueOf(szTemp) + String.valueOf(value.charAt(i));

        float f1;
        try {
            float f = Float.parseFloat(szTemp);
            float f2 = f;
            return f2;
        } catch (NumberFormatException e) {
            f1 = 0.0F;
        }
        float f3 = f1;
        return f3;
    }

    public static double toDouble(String value) {
        if (value == null)
            return 0.0D;
        String szTemp = "";
        for (int i = 0; i < value.length(); i++)
            if (value.charAt(i) != ',')
                szTemp = String.valueOf(szTemp) + String.valueOf(value.charAt(i));

        try {
            double d = Double.parseDouble(szTemp);
            double d2 = d;
            return d2;
        } catch (NumberFormatException e) {
            double d1 = 0.0D;
            double d3 = d1;
            return d3;
        }
    }

    public static long toLong(String value) {
        if (value == null)
            return 0L;
        String szTemp = "";
        for (int i = 0; i < value.length(); i++)
            if (value.charAt(i) != ',')
                szTemp = String.valueOf(szTemp) + String.valueOf(value.charAt(i));

        try {
            double dd = Double.parseDouble(szTemp);
            long l1 = (long) dd;
            long l3 = l1;
            return l3;
        } catch (NumberFormatException e) {
            long l = 0L;
            long l2 = l;
            return l2;
        }
    }

    public static int toInt(String value) {
        return (int) toLong(value);
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    public static String getShortString(String source) {
        int len = 10;
        String endStr = "...";
        String result = source;
        if (source != null && source.length() > len)
            result = String.valueOf(source.substring(0, len)) + String.valueOf(endStr);
        return result;
    }

    public static String getShortString(String source, int length) {
        int len = length <= 0 ? 10 : length;
        String endStr = "...";
        String result = source;
        if (source != null && source.length() > len)
            result = String.valueOf(source.substring(0, len)) + String.valueOf(endStr);
        return result;
    }

    public static String getShortString(String source, int length, String lastStr) {
        int len = length <= 0 ? 10 : length;
        String endStr = lastStr == null ? "..." : lastStr;
        String result = source;
        if (source != null && source.length() > len)
            result = String.valueOf(source.substring(0, len)) + String.valueOf(endStr);
        return result;
    }

    public static double P(int a, int b) {
        int result = 1;
        for (int i = 0; i < b; i++)
            result *= a;

        return (double) result;
    }

    public static double P(float a, int b) {
        float result = 1.0F;
        for (int i = 0; i < b; i++)
            result *= a;

        return (double) result;
    }

    public static String strChange(String str) {
        return str != null ? str : "";
    }

    public static String toStrChange(String str) {
        return str != null ? str : "&nbsp";
    }

    public static final String WeekCn[] = {
        "\u661F\u671F\u65E5", "\u661F\u671F\u4E00", "\u661F\u671F\u4E8C", "\u661F\u671F\u4E09", "\u661F\u671F\u56DB", "\u661F\u671F\u4E94", "\u661F\u671F\u516D"
    };
    public static final String WeekEn[] = {
        "Sunday", "Monday", "Tuesday", "Wednsday", "Thursday", "Friday", "Saturday"
    };
    public static final String MonthCn[] = {
        "\u4E00", "\u4E8C", "\u4E09", "\u56DB", "\u4E94", "\u516D", "\u4E03", "\u516B", "\u4E5D", "\u5341",
        "\u5341\u4E00", "\u5341\u4E8C"
    };
    public static final String NumberCnS[] = {
        "\u3007", "\u4E00", "\u4E8C", "\u4E09", "\u56DB", "\u4E94", "\u516D", "\u4E03", "\u516B", "\u4E5D",
        "\u5341"
    };
    public static final String NumberCnF[] = {
        "\u96F6", "\u58F9", "\u8D30", "\u53C1", "\u8086", "\u4F0D", "\u9646", "\u67D2", "\u634C", "\u7396",
        "\u62FE"
    };
    public static final String MonthEn[] = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
        "November", "December"
    };

}
