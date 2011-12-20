package com.ejoysoft.common;

import java.text.*;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.sql.SQLException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ejoysoft.auth.Base64;

/**
 * ���;java.util.Date��java.sql.Date�������͵�ת��
 * Date: Dec 24, 2002
 * Time: 10:38:13 AM
 * @author feiwj
 * @version ahyjj1.0
 */

public class Format {

    public Format() {
    }

    /**
     * ȡ�õ�ǰ��ʱ�䣬ת��Ϊjava.sql.Date�����ַ�
     * @return  String
     */
    public static java.sql.Timestamp getTimestamp() {
        java.util.Date date = new java.util.Date();
        return new java.sql.Timestamp(date.getTime());
    }


    public static java.util.Date getDateTime(long time) {

        return new java.util.Date(time);
    }

    public static java.util.Date getDateTime(java.sql.Timestamp timestamp) {
        if (timestamp == null) return null;
        return new java.util.Date(timestamp.getTime());
    }

    /**
     * ȡ�õ�ǰ��ʱ�䣬ת��Ϊjava.sql.Date�����ַ�
     * @return  String
     */
    public static String getDateTime() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return simpledateformat.format(date);
    }

    public static String getDate() {
        return getDateTime().substring(0, 10);
    }

    public static String getStrTime() {
        return getDateTime().substring(11);
    }

    public static String getDateTime2() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy��MM��dd��");
        java.util.Date date = new java.util.Date();
        return simpledateformat.format(date);
    }

    //��Ϊ���Ͽ��һ���֣�
    public static String getDateDB() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        String tmpDate = simpledateformat.format(date);
        tmpDate = tmpDate.replaceAll("-", "");
        tmpDate = tmpDate.replaceAll(":", "");
        tmpDate = tmpDate.replaceAll(" ", "");
        return tmpDate;
    }

    /**
     * ���õ�ǰʱ�䣬ת��Ϊjava.sql.Date�����ַ�
     * @return  String
     */
    public static String getStrDate(int year, int mount, int day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        date.setYear(year - 1900);
        date.setMonth(mount - 1);
        date.setDate(day);
        String s = simpledateformat.format(date);
        return s;
    }

    /**ȡ��ʱ���ַ��е�����*/
    public static String getStrDate(String s) {
        return s.substring(0, 10);
    }

    /**�Ƚ�i�������ַ� */
    public static boolean compareTo(String s, String s1) {
        try {
            DateFormat dateformat = DateFormat.getDateInstance();
            java.util.Date date = dateformat.parse(s);
            java.util.Date date1 = dateformat.parse(s1);
            if (date.after(date1))
                return false;
            if (date.before(date1))
                return true;
        } catch (ParseException parseexception) {
            parseexception.printStackTrace();
        }
        return false;
    }

    public static String Replace(String s, String s1, String s2) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        int j = s1.length();
        int k;
        int l;
        for (k = 0; (l = s.indexOf(s1, k)) >= 0; k = l + j) {
            stringbuffer.append(s.substring(k, l));
            stringbuffer.append(s2);
        }

        if (k < i)
            stringbuffer.append(s.substring(k));
        return stringbuffer.toString();
    }

    public static String toHtmlInput(String s) {
        if (s == null) {
            return null;
        } else {
            String s1 = new String(s);
            s1 = Replace(s1, "&", "&amp;");
            s1 = Replace(s1, "<", "&lt;");
            s1 = Replace(s1, ">", "&gt;");
            return s1;
        }
    }

    /**
     * ת���ַ��еĿո񡢻س�Ϊhtml��ʽ
     * @param s
     * @return  String
     */
    public static String toHtml(String s) {
        if (s == null) {
            return null;
        } else {
            String s1 = new String(s);
            //s1 = toHtmlInput(s1);
            //s1 = Replace(s1, "\r\n", "\n");
            s1 = s1.replaceAll("\r\n", "\n");
            s1 = s1.replaceAll("\n", "<br>\n");
            s1 = s1.replaceAll("\t", "   ");
            s1 = s1.replaceAll(" ", "&nbsp;");
            //s1 = Replace(s1, "\n", "<br>\n");
            //s1 = Replace(s1, "\t", "    ");
            //s1 = Replace(s1, "  ", " &nbsp;");
            return s1;
        }
    }

    public static String forHtml(String s) {
        if (s == null) {
            return null;
        } else {
            String s1 = new String(s);
            s1 = s1.replaceAll("\n", "<br>");
            s1 = s1.replaceAll("\t", "   ");
            s1 = s1.replaceAll(" ", "&nbsp;");
            return s1;
        }
    }

    public static String toWorld(String s) {
        if (s == null) {
            return null;
        } else {
            String s1 = new String(s);
            s1 = Replace(s1, "\r\n", "");
            s1 = Replace(s1, "\n", "");
            s1 = Replace(s1, "\"", "\\\"");
            return s1;
        }
    }

    /**
     * �����ַ��е� '�ַ�
     * @param s
     * @return String
     */
    public static String toSql(String s) {
        String s1 = new String(s);
        return Replace(s1, "'", "''");
    }

    /** ����ַ��Ƿ������ֽ�β*/
    public static boolean isEndWithInt(String str) {
        if (str == null || str.length() == 0)
            str = " ";
        try {
            String end = str.substring(str.length() - 1, str.length());
            Integer.parseInt(end);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** ����ַ��Ƿ������ָ�ʽ*/
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
  public static float strtofla(String str){
	  try{
		 
		  
		 // java.text.DecimalFormat df = new   java.text.DecimalFormat("#0.00");//
		  return   Float.parseFloat(str);
		  
	  }
	  catch (Exception e) {
          return 0;
      }
  }
    /**
     * �����ŵķ���
     * @param strSrc
     * @return
     */
    public static String changeStr(String strSrc) {
        String intStr = "0123456789";
        String str = strSrc;
        String strStart = str;
        String strStart0 = "";
        //---
        int intStart0 = str.indexOf(']');
        if (intStart0 != -1) {
            strStart0 = str.substring(0, intStart0);
            str = str.substring(intStart0, str.length());
        }
        //--
        try {
            int intStart = 0;
            for (int j = 0; j < str.length(); j++) {
                int ishave = intStr.indexOf(str.charAt(j));
                if (ishave != -1) {
                    intStart = j;
                    break;
                }
            }
            strStart = str.substring(0, intStart);
            String strEnd = str.substring(intStart);
            int intEnd = Integer.parseInt(strEnd) + 1;
            return strStart0 + strStart + intEnd;
        } catch (Exception e) {
            return str;
        }

    }

    /***/
    public static String getStart2(String s) {
        if (s != null && s.length() >= 2) {
            s = s.substring(0, 2);
        } else
            s = "";
        return s;
    }

    public static String getStart3(String s) {
        if (s != null && s.length() >= 3) {
            s = s.substring(2, 3);
        } else
            s = "";
        return s;
    }

    public static String getStart4(String s) {
        if (s != null && s.length() >= 4) {
            s = s.substring(3, 4);
        } else
            s = "��";
        return s;
    }

    public static java.sql.Date getSqlDate(java.util.Date dtDate, String strType) {
        if (dtDate == null) return null;
        java.sql.Date sqlDate = new java.sql.Date(dtDate.getTime());
        return sqlDate;
    }


    /**
     * ��ȡ��ʽ�����ַ��ʾ������
     * @param dtDate
     * @param strType
     * @return
     */
    public static String getFormatDate(java.util.Date dtDate, String strType) {

        if (dtDate == null) return "";

        String strValue = "";
        //year
        String strYear = String.valueOf((dtDate.getYear() + 1900));
        String strMonth = "";
        String strDay = "";
        //month
        if (("" + (dtDate.getMonth() + 1)).length() == 1) {
            strMonth = "0" + (dtDate.getMonth() + 1);
        } else {
            strMonth = String.valueOf(dtDate.getMonth() + 1);
        }
        //date
        strValue += "-";
        if (("" + dtDate.getDate()).length() == 1) {
            strDay = "0" + dtDate.getDate();
        } else {
            strDay = String.valueOf(dtDate.getDate());
        }
        if (strType.toLowerCase().equals("yyyy/mm/dd")) {
            strValue = strYear + "/" + strMonth + "/" + strDay;
        }
        if (strType.toLowerCase().equals("yyyy-mm-dd")) {
            strValue = strYear + "-" + strMonth + "-" + strDay;
        }
        if (strType.toLowerCase().equals("yyyy-MM-dd".toLowerCase())) {
            strValue = strYear + "-" + strMonth + "-" + strDay;
        }

        if (strType.toLowerCase().equals("yyyy��mm��dd��")) {
            strValue = dtDate.getYear() + 1900 + "��" + (dtDate.getMonth() + 1) + "��" + dtDate.getDate() + "��";
        }

        if (strType.toLowerCase().equals("yyyy/mm/dd hh:mm:ss")) {
            strValue = dtDate.getYear() + 1900 + "/" + (dtDate.getMonth() + 1) + "/" + dtDate.getDate() + " " + dtDate.getHours() + ":" + dtDate.getMinutes() + ":" + dtDate.getSeconds();
        }
        if (strType.toLowerCase().equals("yyyy-mm-dd hh:mm:ss")) {
            strValue = dtDate.getYear() + 1900 + "-" + (dtDate.getMonth() + 1) + "-" + dtDate.getDate() + " " + dtDate.getHours() + ":" + dtDate.getMinutes() + ":" + dtDate.getSeconds();
        }
        if (strType.toLowerCase().equals("yyyy��mm��dd��hhʱmm��ss��")) {
            strValue = dtDate.getYear() + 1900 + "��" + (dtDate.getMonth() + 1) + "��" + dtDate.getDate() + "��" + dtDate.getHours() + "ʱ" + dtDate.getMinutes() + "��" + dtDate.getSeconds() + "��";
        }

        if (strType.toLowerCase().equals("yyyy��mm��dd��hhʱmm��")) {
            strValue = dtDate.getYear() + 1900 + "��" + (dtDate.getMonth() + 1) + "��" + dtDate.getDate() + "��" + dtDate.getHours() + "ʱ" + dtDate.getMinutes() + "��";
        }
        if (strType.toLowerCase().equals("yyyy��mm��dd��hh:mm")) {
            strValue = dtDate.getYear() + 1900 + "��" + (dtDate.getMonth() + 1) + "��" + dtDate.getDate() + "��   " + dtDate.getHours() + ":" + dtDate.getMinutes();
        }
        if (strType.toLowerCase().equals("hhʱmm��")) {
            strValue = dtDate.getHours() + "ʱ" + dtDate.getMinutes() + "��";
        }

        if (strType.toLowerCase().equals("mm��dd��")) {
            strValue = dtDate.getMonth() + 1 + "��" + dtDate.getDate() + "��";
        }

        if (strType.toLowerCase().equals("mm��")) {
            strValue = dtDate.getMonth() + 1 + " �� ";
        }

        if (strType.toLowerCase().equals("yyyy-mm-dd-hh:mm")) {
            //year
            strValue = dtDate.getYear() + 1900 + "-";
            //month
            if (("" + (dtDate.getMonth() + 1)).length() == 1) {
                strValue += "0" + (dtDate.getMonth() + 1);
            } else {
                strValue += dtDate.getMonth() + 1;
            }
            //date
            strValue += "-";
            if (("" + dtDate.getDate()).length() == 1) {
                strValue += "0" + dtDate.getDate();
            } else {
                strValue += dtDate.getDate();
            }
            //hours
            strValue += "-";
            if (("" + dtDate.getHours()).length() == 1) {
                strValue += "0" + dtDate.getHours();
            } else {
                strValue += dtDate.getHours();
            }
            //min
            strValue += ":";
            if (("" + dtDate.getMinutes()).length() == 1) {
                strValue += "0" + dtDate.getMinutes();
            } else {
                strValue += dtDate.getMinutes();
            }
        }
        return strValue;
    }

    /**
     * ��ȡHTML������BODY����
     * @param str
     * @return
     */
    public static String getBodyFromHtml(String str) {
        if (str == null) return "";
        int iBegin = str.indexOf("<body>");
        int iEnd = str.lastIndexOf("</body>");
        if (iBegin == -1 && iEnd == -1) {
            return str;
        } else if (iBegin == -1) {
            return str.substring(0, iEnd);
        } else if (iEnd == -1) {
            return str.substring(iBegin + 6, str.length());
        } else {
            return str.substring(iBegin + 6, iEnd);
        }
    }


    /**
     * ��������ַ��ȳ�����ָ���������ȡ
     * @param str �����ַ�
     * @param iLen ָ������
     * @return ��ʽ������ַ�
     */
    public static String substr(String str, int iLen) {
        if (str == null) return "";
        if (iLen > 3) {
            if (str.length() > iLen - 3) {
                str = str.substring(0, iLen - 3) + "������";
            }
        } else {
            str = str.substring(0, iLen);
        }
        return str;
    }

    public static String substrDb(String str, int iLen) {
        if (str == null) return "";
        if (iLen >= str.length()) {
            return str;
        } else {
            str = str.substring(0, iLen);
        }
        return str;
    }

    /**
     * ��ֹHTML���룬�� < ��ǻ��� <  ���
     * @param str �����ַ�
     * @return ����ַ�
     */
    public static String forbidHTML(String str) {
        if (str == null) return "";
        str = str.replaceAll("<", "��");
        str = str.replaceAll(">", "��");
        str = str.replaceAll("\"", "��");
        return str;
    }

    /**
     * ��ֹ��ʾ��NULLֵ
     * @param str �����ַ�
     * @return ����ַ�
     */
    public static String forbidNull(String str) {
        if (str == null) return "";
        return str;
    }

    /**
     * ���Եķ���
     * @param args �������
     */
    public static void main(String args[]) {

    }

    /**
     * ת��Ϊ����ʽ����
     * @param i
     * @return
     */
    public static String getNumberCn(int i) {
        String strValue = "";
        switch (i) {
            case 1:
                strValue = "һ";
                break;
            case 2:
                strValue = "��";
                break;
            case 3:
                strValue = "��";
                break;
            case 4:
                strValue = "��";
                break;
            case 5:
                strValue = "��";
                break;
            case 6:
                strValue = "��";
                break;
            case 7:
                strValue = "��";
                break;
            case 8:
                strValue = "��";
                break;
            case 9:
                strValue = "��";
                break;
            case 10:
                strValue = "ʮ";
                break;
            case 11:
                strValue = "ʮһ";
                break;
            case 12:
                strValue = "ʮ��";
                break;
            case 13:
                strValue = "ʮ��";
                break;
            case 14:
                strValue = "ʮ��";
                break;
            case 15:
                strValue = "ʮ��";
                break;
            case 16:
                strValue = "ʮ��";
                break;
            case 17:
                strValue = "ʮ��";
                break;
            case 18:
                strValue = "ʮ��";
                break;
            case 19:
                strValue = "ʮ��";
                break;
            case 20:
                strValue = "��ʮ";
                break;
            case 21:
                strValue = "��ʮһ";
                break;
            case 22:
                strValue = "��ʮ��";
                break;
            default :
                strValue = "" + i;
        }
        return strValue;
    }


    public static SimpleDateFormat yyyyYmmMdd = new SimpleDateFormat("yyyy\u5e74MM\u6708dd\u65e5");
    public static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat yyyyMMddhhmmss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static SimpleDateFormat yyyy_MM_dd_hhmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String dateToStr(java.util.Date date) {
        if (date == null) return "";
        return yyyyYmmMdd.format(date);
    }

    /**
     * @param date
     * @param style
     * @return
     */
    public static String dateToStr(java.util.Date date, int style) {
        if (date == null) return "";
        if (style == 1)
            return yyyyMMdd.format(date);
        else if (style == 2)
            return yyyy_MM_dd.format(date);
        else if (style == 3)
            return yyyyMMdd.format(date);
        else if (style == 4)
            return yyyyMMddhhmmss.format(date);
        else if (style == 5)
            return yyyy_MM_dd_hhmmss.format(date);
        else
            return yyyyMMdd.format(date);
    }

    public static java.sql.Date strToDate(String strDate, SimpleDateFormat sf) {
        try {
            return new java.sql.Date((sf.parse(strDate).getTime()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String get(java.util.Date dtDate, String strType) {

        if (null == dtDate) return "";

        int iYear = dtDate.getYear() + 1900;
        String sMonth = (dtDate.getMonth() >= 9) ? ("" + (dtDate.getMonth() + 1)) : ("0" + (dtDate.getMonth() + 1));
        String sDate = (dtDate.getDate() > 9) ? ("" + dtDate.getDate()) : ("0" + dtDate.getDate());


        //System.out.println("--sDate--"+sDate+"--sMonth--"+sMonth);
        String strValue = "";
        try {
            if (strType.equals("yyyy/mm/dd")) {
                strValue = iYear + "/" + sMonth + "/" + sDate;
            }
            if (strType.equals("yyyy-mm-dd")) {
                strValue = iYear + "-" + sMonth + "-" + sDate;
            }

            if (strType.equals("yyyy��mm��dd��")) {
                strValue = iYear + "��" + sMonth + "��" + sDate + "��";
            }

            if (strType.equals("yyyy/mm/dd hh:mm:ss")) {
                String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
                String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
                String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
                strValue = iYear + "/" + sMonth + "/" + sDate + " " + strHours + ":" + strMinutes + ":" + strSeconds;
            }

            if (strType.equals("yyyy��mm��dd��hhʱmm��ss��")) {
                String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
                String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
                String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
                strValue = iYear + "��" + sMonth + "��" + sDate + "��" + strHours + "ʱ" + strMinutes + "��" + strSeconds + "��";
            }

            if (strType.equals("yyyy��mm��dd��hhʱmm��")) {
                String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
                String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
                String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
                strValue = iYear + "��" + sMonth + "��" + sDate + "��" + strHours + "ʱ" + strMinutes + "��";
            }
            if (strType.equals("yyyy��mm��dd��  hh:mm")) {
                String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
                String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
                String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
                strValue = iYear + "��" + sMonth + "��" + sDate + "��  " + strHours + ":" + strMinutes;
            }
        } catch (Exception e) {
            strValue = "";
        }
        return strValue;
    }

    public static String getFormatDay(String str, String strType) {
        java.util.Date dtDate;
        String strValue = "";
        if (str == null || str.equals("null") || str.equals("") || str.length() < 8)
            return "";
        try {
            if (str.indexOf(".") != -1)
                dtDate = new java.util.Date(str.substring(0, str.lastIndexOf(".")).replace('-', '/'));
            else
                dtDate = new java.util.Date(str.replace('-', '/'));
            int iMonth = dtDate.getMonth();
            int iDay = dtDate.getDate();
            String strMonth = (iMonth >= 9) ? ("" + (iMonth + 1)) : ("0" + (iMonth + 1));
            String strDate = (iDay > 9) ? ("" + iDay) : ("0" + iDay);
            if (strType.equals("mm-dd")) {
                strValue = strMonth + "-" + strDate;
            }
            if (strType.equals("mm��dd��")) {
                strValue = (iMonth + 1) + "��" + iDay + "��";
            }
            if (strType.equals("mm.dd")) {
                strValue = (iMonth + 1) + "." + iDay + "";
            }
        } catch (Exception e) {
            strValue = "";
            e.printStackTrace();
        }
        return strValue;
    }

    public static String getFormatDate(String str, String strType) {
        java.util.Date dtDate;
        if (str == null || str.equals("null") || str.equals(""))
            return "";
        if (str.indexOf(".") != -1)
            dtDate = new java.util.Date(str.substring(0, str.lastIndexOf(".")).replace('-', '/'));
        else
            dtDate = new java.util.Date(str.replace('-', '/'));
        String strValue = "";

        int iYear = dtDate.getYear() + 1900;
        int iMonth = dtDate.getMonth();
        String strMonth = (iMonth >= 9) ? ("" + (iMonth + 1)) : ("0" + (iMonth + 1));
        String strDate = (dtDate.getDate() > 9) ? ("" + dtDate.getDate()) : ("0" + dtDate.getDate());

        if (strType.equals("yyyy/mm/dd")) {
            strValue = iYear + "/" + strMonth + "/" + strDate;
        }
        if (strType.equals("yyyy.mm.dd")) {
            strValue = iYear + "." + strMonth + "." + strDate;
        }
        if (strType.equals("yyyymmdd")) {
            strValue = iYear + "" + strMonth + "" + strDate;
        }
        if (strType.equals("yyyy-mm2-dd")) {
            strValue = iYear + "-" + (iMonth + 1) + "-" + strDate;
        }
        if (strType.equals("yyyy-mm-dd")) {
            strValue = iYear + "-" + strMonth + "-" + strDate;
        }
        if (strType.equals("yyyy��mm��dd��")) {
            strValue = iYear + "��" + strMonth + "��" + strDate + "��";
        }
         if (strType.equals("yyyy��mm2��dd��")) {
            strValue = iYear + "��" + (iMonth+1) + "��" + strDate + "��";
        }
        if (strType.equals("mm��dd��")) {
            strValue = (iMonth+1)  + "��" + strDate + "��";
        }
        if (strType.equals("yyyy/mm/dd hh:mm:ss")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
            strValue = iYear + "/" + strMonth + "/" + strDate + " " + strHours + ":" + strMinutes + ":" + strSeconds;
        }
        if (strType.equals("yyyy-mm-dd hh:mm:ss")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
            strValue = iYear + "-" + strMonth + "-" + strDate + " " + strHours + ":" + strMinutes + ":" + strSeconds;
        }

        if (strType.equals("yyyy-mm-dd hh:mm")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
            strValue = iYear + "-" + strMonth + "-" + strDate + " " + strHours + ":" + strMinutes;
        }

        if (strType.equals("yyyy��mm��dd��hhʱmm��ss��")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            String strSeconds = (dtDate.getSeconds() > 9) ? ("" + dtDate.getSeconds()) : ("0" + dtDate.getSeconds());
            strValue = iYear + "��" + strMonth + "��" + strDate + "��" + strHours + "ʱ" + strMinutes + "��" + strSeconds + "��";
        }
        if (strType.equals("yyyy��mm��dd��hhʱmm��")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            strValue = iYear + "��" + strMonth + "��" + strDate + "��" + strHours + "ʱ" + strMinutes + "��";
        }
        if (strType.equals("yyyy��mm��dd��hh:mm")) {
            String strHours = (dtDate.getHours() > 9) ? ("" + dtDate.getHours()) : ("0" + dtDate.getHours());
            String strMinutes = (dtDate.getMinutes() > 9) ? ("" + dtDate.getMinutes()) : ("0" + dtDate.getMinutes());
            strValue = iYear + "��" + strMonth + "��" + strDate + "��" + strHours + "��" + strMinutes + "";
        }

        return strValue;
    }

    public static java.sql.Date getSqlDate(java.util.Date dtDate) {
        if (dtDate == null) return null;
        java.sql.Date sqlDate = new java.sql.Date(dtDate.getTime());
        return sqlDate;
    }


    public static String tString(String s) {
        if (s == null) return "";
        return s;
    }


    //bill 2005/03/22 ���¶������״̬,>0Ϊ�����,see com.suncn.common.Constants
    public static String getWorkflowState(int state) {

        if (state > 0) return "\u5ba1\u6838\u4e2d";

        switch (state) {
            //case com.suncn.common.Constants.WK_DSB_STATE: return "���ϱ�";
//            case com.suncn.common.Constants.WK_DSB_STATE:
//                return "\u5f85\u4e0a\u62a5";
//                //case com.suncn.common.Constants.WK_DXG_STATE: return "���޸�";
//            case com.suncn.common.Constants.WK_DXG_STATE:
//                return "\u5f85\u4fee\u6539";
//                //case com.suncn.common.Constants.WK_DSP_STATE: return "������";
//            case com.suncn.common.Constants.WK_DSP_STATE:
//                return "\u5f85\u5ba1\u6279";
//                //case com.suncn.common.Constants.WK_DTG_STATE: return "���ͨ��";
//            case com.suncn.common.Constants.WK_DTG_STATE:
//                return "\u5ba1\u6838\u901a\u8fc7";
//                //case com.suncn.common.Constants.WK_WTG_STATE: return "δͨ��";
//            case com.suncn.common.Constants.WK_WTG_STATE:
//                return "\u672a\u901a\u8fc7";

            //default: return("δ����");
            default:
                return ("\u672a\u5b9a\u4e49");
        }

    }


    public static String dateFormat(java.util.Date date) {
        if (date == null) return "";
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    public static String dateFormat2(java.util.Date date) {
        if (date == null) return "";
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy\u5e74MM\u6708dd\u65e5");
        return sf.format(date);
    }


    /**
     *���ܣ� �����
     * @param d
     * @return  String
     */
    public static String decimalFormat(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * �õ�ָ�������ǵ���ȵĵڼ���
     * @param dtDate
     * @return ����
     */
    public static int getWeekNumber(Date dtDate) {
        //�õ������ڵ����ǵڼ���
        long iDay = dtDate.getTime() / (1000 * 60 * 60 * 24) - (new Date(dtDate.getYear() + 1900 + "/1/1").getTime() / (1000 * 60 * 60 * 24)) + 1;
        if (new Date(dtDate.getYear() + 1900 + "/1/1").getDay() == 1) {
            if (iDay <= 7) {
                return 1;
            }
            if (iDay % 7 == 0)
                return (int) iDay / 7;
            else
                return (int) iDay / 7 + 1;
        } else {
            Date dtBase = new Date(dtDate.getYear() + 1900 + "/1/1");
            int d = dtBase.getDay();
            Date dtTemp = new Date(dtBase.getTime() + 1000 * 60 * 60 * 24 * (7 - d));
            if (dtDate.getTime() <= dtTemp.getTime()) {
                return getWeekNumber(new Date(dtDate.getYear() + 1899 + "/12/31"));
            }
            iDay = iDay - (7 - d + 1);
            if (iDay % 7 == 0)
                return (int) iDay / 7;
            else
                return (int) iDay / 7 + 1;
        }
    }


    /**
     * ��ȡָ�������������ڵ�����һ������
     * @param dtBase
     * @return
     */
    public static Date getMondayDate(Date dtBase) {
        try {
            int iWeek = dtBase.getDay();
            if (iWeek == 0) iWeek = 7;
            Date dtMonday = new Date(dtBase.getTime() - ((iWeek - 1) * 1000 * 24 * 60 * 60));
            return dtMonday;
        } catch (Exception e) {
            return null;
        }
    }

    //ָ�����µĽ�����
    public static int getMondayDay(int tYear, int tMonth) {
        int intDay = 30;
        try {
            //ÿ���µĽ�����
            if (tMonth == 1 || tMonth == 3 || tMonth == 5 || tMonth == 7 || tMonth == 8 || tMonth == 10 || tMonth == 12)
                intDay = 31;
            else if (tMonth == 2) {
                if (tYear % 400 == 0 || (tYear % 4 == 0 && tYear % 100 != 0)) {
                    intDay = 29;
                } else
                    intDay = 28;
            } else
                intDay = 30;
        } catch (Exception e) {
            return intDay;
        }
        return intDay;
    }

    //ǰ�油��
    public static String addZero(int iLen, int iNum) {
        String count = "";
        try {
            count = String.valueOf(iNum);
            for (int i = count.length(); i <= iLen; i++) {
                count = "0" + count;
            }
            return count;
        } catch (Exception e) {
            return count;
        }
    }

    //�滻
    public static String replace(String handleStr, String pointStr, String repStr) {
        String str = new String();
        int pos1,pos2;
        try {
            if (handleStr.length() > 0) {
                pos1 = handleStr.indexOf(pointStr);
                pos2 = 0;
                while (pos1 != -1) {
                    str += handleStr.substring(pos2, pos1);
                    str += repStr;
                    pos2 = pos1 + pointStr.length();
                    pos1 = handleStr.indexOf(pointStr, pos2);
                }
                str += handleStr.substring(pos2);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return str;
    }


    public static String removeNull(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else
            return tmpStr;
    }

    public static String rmZero(float score) {
        String strScore = String.valueOf(score);
        strScore = strScore.indexOf(".0") > 0 ? strScore.substring(0, strScore.length() - 2) : strScore;
        return strScore;
    }

    public static String subBottom(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("") || tmpStr.indexOf("-") < 0)
            return "";
        else
            return tmpStr.substring(tmpStr.indexOf("-") + 1);
    }

    public static String subTop(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.indexOf("-") > 0)
            return tmpStr.substring(0, tmpStr.indexOf("-"));
        else
            return tmpStr;
    }

    public static String subCenter(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.indexOf("��") > 0 && tmpStr.indexOf("��") > 0)
            return tmpStr.substring(tmpStr.length() - 2, tmpStr.length() - 1);
        else
            return "";
    }

    //����ת��Ϊ�ַ�  '112211','33333221'
    public static String arryToStr(String[] tmpArry) {
        String str = "";
        if (tmpArry != null || tmpArry.length > 0) {
            str = "'" + tmpArry[0] + "'";
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + ",'" + tmpArry[i] + "'";
            }
            return str;
        } else
            return "";
    }

    //����ת��Ϊ�ַ�  1221,2232
    public static String arryToStr2(String[] tmpArry) {
        String str = "";
        if (tmpArry != null || tmpArry.length > 0) {
            str = tmpArry[0];
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + "," + tmpArry[i] + "";
            }
            return str;
        } else
            return "";
    }

    //����ת��Ϊsql�ַ�
    public static String arryToSql(String tFeild, String[] tmpArry) {
        if (tFeild.equals("")) return " 1=1 ";
        String str = "(";
        if (tmpArry != null && tmpArry.length > 0) {
            str = str + tFeild + " LIKE '%" + tmpArry[0] + "%'";
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + " OR  " + tFeild + " LIKE '%" + tmpArry[i] + "%'";
            }
            return str + ")";
        } else
            return " 1=1 ";
    }

    public static String subName(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.lastIndexOf("/") > 0)
            return tmpStr.substring(tmpStr.lastIndexOf("/") + 1);
        else
            return tmpStr;
    }
   //�λ���Ա��Ƭ�õ�
    public static String subLast(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.lastIndexOf("/") > 0)
            return tmpStr.substring(0, tmpStr.lastIndexOf("/")+1);
        else
            return tmpStr;
    }

    public static String subUnitID(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.lastIndexOf("/") > 0)
            return tmpStr.substring(2, tmpStr.lastIndexOf("/"));
        else
            return tmpStr;
    }

    public static String subID(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.indexOf("/") > 0)
            return tmpStr.substring(0, tmpStr.indexOf("/"));
        else
            return tmpStr;
    }

    public static String FileExp(String tmpName) {
        if (tmpName == null || tmpName.equals("null"))
            return "";
        else if (tmpName.lastIndexOf(".") > 0)
            return tmpName.substring(tmpName.lastIndexOf("."));
        else
            return tmpName;
    }

    //����ת��Ϊsql�ַ�
    public static String strToSql(String tFeild, String tmp) {
        String[] tmpArry = tmp.split(",");
        if (tFeild.equals("")) return " 1=1 ";
        String str = "(";
        if (tmpArry != null && tmpArry.length > 0) {
            str = str + tFeild + " LIKE '%" + tmpArry[0] + "%'";
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + " OR  " + tFeild + " LIKE '%" + tmpArry[i] + "%'";
            }
            return str + ")";
        } else
            return " 1=1 ";
    }

    //����ת��Ϊsql�ַ�
    public static String strToSql2(String tFeild, String tmp) {
        String[] tmpArry = tmp.split(",");
        if (tFeild.equals("")) return " 1=1 ";
        String str = "(";
        if (tmpArry != null && tmpArry.length > 0) {
            str = str + tFeild + " = '' OR " + tFeild + " IS NULL ";
//            str = str + tFeild + " LIKE '%" + tmpArry[0] + "%'";
            for (int i = 0; i < tmpArry.length; i++) {
                str = str + " OR  " + tFeild + " LIKE '%" + tmpArry[i] + "%'";
            }
            return str + ")";
        } else
            return " 1=1 ";
    }
     //�ַ�ת��Ϊsql�ַ�'111' ,'222','333'
    public static String strToSql3(String tmp) {
        if (tmp.equals("")) return " 1=1 ";
        String[] tmpArry = tmp.split(",");
        String str = "";
        if (tmpArry != null && tmpArry.length > 0) {
            str =  "'" +subUnitID(tmpArry[0]) + "'";
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + ",'" +subUnitID(tmpArry[i]) + "'";
            }
            return str ;
        } else
            return " 1=1 ";
    }
      //����ת��Ϊsql�ַ�
    public static String arryToSql2(String tFeild, String[] tmpArry) {
        if (tFeild.equals("")) return " 1=1 ";
        String str = "(";
        if (tmpArry != null && tmpArry.length > 0) {
//            str = str + tFeild + " LIKE '%" + tmpArry[0] + "%'";
            str = str + tFeild + " IS NULL ";
            for (int i = 0; i < tmpArry.length; i++) {
                str = str + " OR  " + tFeild + " LIKE '%" + tmpArry[i] + "%'";
            }
            return str + ")";
        } else
            return " 1=1 ";
    }
    //����ת��Ϊsql�ַ�
    public static String strToSql3(String tFeild, String tmp) {
        String[] tmpArry = tmp.split(",");
        if (tFeild.equals("")) return " 1=1 ";
        String str = "(";
        if (tmpArry != null && tmpArry.length > 0) {
            str = str + tFeild + " IS NULL ";
//            str = str + tFeild + " LIKE '%" + tmpArry[0] + "%'";
            for (int i = 0; i < tmpArry.length; i++) {
                str = str + " OR  " + tFeild + " LIKE '%" + tmpArry[i] + "%'";
            }
            return str + ")";
        } else
            return " 1=1 ";
    }
    //ָ��ĳ������n����������
    public static String newDate(String tDate, int nDay) {
        if (tDate == null || tDate.equals("")) tDate = Format.getDateTime();
        tDate = tDate.replaceAll("-", "/");
        if (nDay == 0) nDay = 1;
        Date dLimitDate = new Date(new Date(tDate).getTime() + (long) nDay * 24 * 60 * 60 * 1000);
        return Format.getFormatDate(dLimitDate, "yyyy-mm-dd hh:mm:ss");
    }

    //�û�����������Ȩ���ж�
    public static boolean viewRight(String tuser, String tRang) {
        if (Format.removeNull(tRang).equals(""))
            return true;
        String[] tmpArry = tuser.split(",");
        boolean isFlag = false;
        String str = "";
        if (tmpArry != null || tmpArry.length > 0) {
            for (int i = 0; i < tmpArry.length; i++) {
                isFlag = tRang.indexOf(tmpArry[i]) >= 0;
                if (isFlag) return true;
            }
            return isFlag;
        } else
            return false;
    }

    public static String[] subIDtoArry(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return null;
        String[] tmpArry = tmpStr.split(",");
        if (tmpArry != null || tmpArry.length > 0) {
            String[] sqlArry = new String[tmpArry.length];
            for (int i = 0; i < tmpArry.length; i++) {
                sqlArry[i] = subID(tmpArry[i]);
            }
           return sqlArry;
        }
        return null;
    }
        public static String subPubIssue(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.lastIndexOf("��") > 0)
            return tmpStr.substring(tmpStr.lastIndexOf("��") );
        else
            return "";
    }
     public static String subPubType(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.lastIndexOf("��") > 0)
            return tmpStr.substring(0,tmpStr.indexOf("��")+1 );
        else
            return "";
    }
      public static String subPubYearIssue(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "";
        else if (tmpStr.indexOf("2") > 0)
            return tmpStr.substring(tmpStr.indexOf("2") );
        else
            return "";
    }
        //����ת��Ϊ�ַ� 112211,33333221������ '112211','33333221'
    public static String strToStr(String tmpStr) {
        if (tmpStr == null || tmpStr.equals("null"))
            return "''";
        String[] tmpArry = tmpStr.split(",");
         String str = "";
        if (tmpArry != null || tmpArry.length > 0) {
            str = "'" + tmpArry[0] + "'";
            for (int i = 1; i < tmpArry.length; i++) {
                str = str + ",'" + tmpArry[i] + "'";
            }
            return str;
        } else
            return "''";
    }
    //������  �б�����
    public static String markSort(String strObjFeild,String inFeildName,String inSortType) {
        if(!strObjFeild.equals(inFeildName))
            return "";
        else {
           if("DESC".equals(inSortType.toUpperCase()))
                return "<font color=#00FF00>��</font>";
            else
                return "<font color=#FF0000>��</font>";
        }
    }
    public static String enPass(String pwd) {
        char[] enPwd = Base64.encode(pwd.getBytes());
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < enPwd.length; i++) {
            temp.append(enPwd[i]);
        }
        return temp.toString();
    }

    public static String dePass(String bPass) {
        char[] enPwd = new char[bPass.length()];
        for (int i = 0; i < bPass.length(); i++) {
            enPwd[i] = bPass.charAt(i);
        }
        return new String(Base64.decode(enPwd));
    }
    /* ����html����
     * @param element �����ַ�,ͨ����doc��html����
     * @return ����ַ�
     */
    public static String getTxtWithoutHTMLElement(String element){
        if(element==null||"".equals(element.trim())){
            return element;
        }
        Pattern pattern = Pattern.compile("<[^<|^>]*>");
        Matcher matcher = pattern.matcher(element);
        StringBuffer txt = new StringBuffer();
        while(matcher.find()) {
            String group = matcher.group();
            if(group.matches("<[\\s]*>"))
            {
                matcher.appendReplacement(txt,group);
            }
            else
            {
                matcher.appendReplacement(txt,"");
            }
        }
        matcher.appendTail(txt);
        return txt.toString().replaceAll("&nbsp;","");
    }

}
