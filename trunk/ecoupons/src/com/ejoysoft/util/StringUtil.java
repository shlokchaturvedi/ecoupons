package com.ejoysoft.util;

import java.io.*;

/**
 * �ַ��?����<br>
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-11 12:48:29 $
 */

public class StringUtil {
    /**
     * iso-8859-1�ַ����
     */
    public static final String ISO_8859_1 = "iso-8859-1";
    /**
     * GBK�ַ����
     */
    public static final String GBK = "GBK";
    /**
     * GB2312�ַ����
     */
    public static final String GB2312 = "gb2312";

    /**
     * �ַ�ת��
     * @param s Ҫת�����ַ�
     * @param charSetName �ַ�
     * @param defaultCharSetName Ĭ���ַ�
     * @return String ת���ַ�
     */
    public static String CharSetConvert(String s, String charSetName, String defaultCharSetName) {
        String newString = null;
        try {
            newString = new String(s.getBytes(charSetName), defaultCharSetName);
        } catch (UnsupportedEncodingException e) {

            System.out.println("StringUtil.CharSetConvert() UnsupportedEncodingException" + e);

        } catch (NullPointerException nulle) {
            System.out.println("StringUtil.CharSetConvert() NullPointerException" + nulle);

        }
        return newString;
    }


    public static String CharSetConvertDefault(String s) {

        return CharSetConvert(s, ISO_8859_1, GBK);

    }


    private static int by2int(int b) {
        return b & 0xff;
    }

    public static String Utf2String(byte buf[]) {
        int len = buf.length;
        StringBuffer sb = new StringBuffer(len / 2);
        for (int i = 0; i < len; i++) {

            if (by2int(buf[i]) <= 0x7F)
                sb.append((char) buf[i]);
            else if (by2int(buf[i]) <= 0xDF && by2int(buf[i]) >= 0xC0) {
                int bh = by2int(buf[i] & 0x1F);
                int bl = by2int(buf[++i] & 0x3F);

                bl = by2int(bh << 6 | bl);
                bh = by2int(bh >> 2);
                int c = bh << 8 | bl;
                sb.append((char) c);
            } else if (by2int(buf[i]) <= 0xEF && by2int(buf[i]) >= 0xE0) {
                int bh = by2int(buf[i] & 0x0F);
                int bl = by2int(buf[++i] & 0x3F);
                int bll = by2int(buf[++i] & 0x3F);

                bh = by2int(bh << 4 | bl >> 2);
                bl = by2int(bl << 6 | bll);


                int c = bh << 8 | bl;
                //�ո�ת��Ϊ���
                if (c == 58865) {
                    c = 32;
                }
                sb.append((char) c);

            }
        }
        return sb.toString();

    }



/////////////////////////////////////////////////////
    public void testCharSetConvert() {
        StringUtil test = new StringUtil();
        test.CharSetConvert("dsasafda", ISO_8859_1, GBK);
    }


    public static String ConvertSizeToStr(long l) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (l < 1024L)
            return l + "bytes";
        else if (l / 1024F < 1024F)
            return df.format(l / 1024F) + "K";

        else if (l / 1024F / 1024 < 1024F)
            return df.format(l / 1024F / 1024) + "M";
        else
        // if (l / 1024F / 1024 / 1024 < 1024F)
            return df.format(l / 1024F / 1024 / 1024) + "G";

    }


    public static String getDecimalFormat(float f) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(f);
    }

    public static String getISOTOGBK(String s) {
        return CharSetConvert(s, ISO_8859_1, GBK);
    }

    public static String getGBKTOISO(String s) {
        return CharSetConvert(s, GBK, ISO_8859_1);
    }

    /**
     * ���ַ�ת����Unicode��
     * @param strText ��ת�����ַ�
     * @param code ת��ǰ�ַ�ı��룬��"GBK"
     * @return ת�����Unicode���ַ�
     */
    public static String toUnicode(String strText, String code) throws UnsupportedEncodingException {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        strText = new String(strText.getBytes("8859_1"), code);
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            if (intAsc > 128) {
                strHex = Integer.toHexString(intAsc);
                strRet = strRet + "&#x" + strHex + ";";
            } else {
                strRet = strRet + c;
            }
        }
        return strRet;
    }
     public static String replaceAll(String source, String info, String tag)
    {
        if(source == null || source.length() == 0)
            return "";
        if(info == null || tag == null || tag.length() == 0)
            return source;
        int index = source.indexOf(tag);
        boolean valid = index >= 0;
        if(!valid)
            return source;
        StringBuffer ret = new StringBuffer();
        int start = 0;
        int length = tag.length();
        for(; valid; valid = index >= 0)
        {
            ret.append(source.substring(start, index)).append(info);
            start = index + length;
            index = source.indexOf(tag, start);
        }

        ret.append(source.substring(start));
        return ret.toString();
    }
     
     public static int indexAfterOf(String str, String indexStr) {
    	 if (str.indexOf(indexStr) < 0) {
    		 return -1;
    	 } else {
    		 return str.indexOf(indexStr) + indexStr.length();
    	 }
     }
     
     public static int indexAfterOf(String str, String indexStr, int from) {
    	 if (str.indexOf(indexStr, from) < 0) {
    		 return -1;
    	 } else {
    		 return str.indexOf(indexStr, from) + indexStr.length();
    	 }
     }
     
     public static String innerOf(String str, String preStr, String nextStr) {
    	 int i = indexAfterOf(str, preStr);
    	 int j = str.indexOf(nextStr, i);
    	 if (i < 0 || j < 0)
    		 return "";
    	 else
    		 return str.substring(i, j);
     }
     
     public static String innerOf(String str, String preStr, String nextStr, int from) {
    	 int i = indexAfterOf(str, preStr, from);
    	 int j = str.indexOf(nextStr, i);
    	 if (i < 0 || j < 0 || from < 0)
    		 return "";
    	 else
    		 return str.substring(i, j);
     }

    public static void main(String[] args) throws Exception {


    }


}
