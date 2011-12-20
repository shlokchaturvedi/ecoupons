package com.ejoysoft.util;

/**
 * �ļ���ƣ�ConvertUtils.java
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-3-11 11:31 $
 */

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.lang.reflect.Array;
import java.io.UnsupportedEncodingException;

public class ConvertUtils {
    private static Log log = LogFactory.getLog(ConvertUtils.class);

    public ConvertUtils() {

    }

    public static String UTC(String s) {
        try {
            if (s == null || s.equals("")) return "";
            String newstring = null;
            newstring = new String(s.getBytes("ISO8859_1"), "gbk");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String CTU(String s) {
        try {
            if (s == null || s.equals("")) return "";
            String newstring = null;
            newstring = new String(s.getBytes("gbk"), "ISO8859_1");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public boolean getDefaultBoolean() {
        return defaultBoolean.booleanValue();
    }

    public void setDefaultBoolean(boolean defaultBoolean) {
        this.defaultBoolean = new Boolean(defaultBoolean);
    }

    public byte getDefaultByte() {
        return defaultByte.byteValue();
    }

    public void setDefaultByte(byte defaultByte) {
        this.defaultByte = new Byte(defaultByte);
    }

    public char getDefaultCharacter() {
        return defaultCharacter.charValue();
    }

    public void setDefaultCharacter(char defaultCharacter) {
        this.defaultCharacter = new Character(defaultCharacter);
    }

    public double getDefaultDouble() {
        return defaultDouble.doubleValue();
    }

    public void setDefaultDouble(double defaultDouble) {
        this.defaultDouble = new Double(defaultDouble);
    }

    public float getDefaultFloat() {
        return defaultFloat.floatValue();
    }

    public void setDefaultFloat(float defaultFloat) {
        this.defaultFloat = new Float(defaultFloat);
    }

    public int getDefaultInteger() {
        return defaultInteger.intValue();
    }

    public void setDefaultInteger(int defaultInteger) {
        this.defaultInteger = new Integer(defaultInteger);
    }

    public long getDefaultLong() {
        return defaultLong.longValue();
    }

    public void setDefaultLong(long defaultLong) {
        this.defaultLong = new Long(defaultLong);
    }

    public short getDefaultShort() {
        return defaultShort.shortValue();
    }

    public void setDefaultShort(short defaultShort) {
        this.defaultShort = new Short(defaultShort);
    }

    public static String convert(Object value) {
        if (value == null)
            return (String) null;
        if (value.getClass().isArray()) {
            value = Array.get(value, 0);
            if (value == null)
                return (String) null;
            else
                return value.toString();
        } else {
            return value.toString();
        }
    }

    public static Object convert(String value, Class clazz) {


        if (clazz == stringClass)
            if (value == null)
                return (String) null;
            else
                return value;

        if (clazz == Integer.TYPE)
            return convertInteger(value, defaultInteger);
        if (clazz == Boolean.TYPE)
            return convertBoolean(value, defaultBoolean);
        if (clazz == Long.TYPE)
            return convertLong(value, defaultLong);
        if (clazz == Double.TYPE)
            return convertDouble(value, defaultDouble);
        if (clazz == Character.TYPE)
            return convertCharacter(value, defaultCharacter);
        if (clazz == Byte.TYPE)
            return convertByte(value, defaultByte);
        if (clazz == Float.TYPE)
            return convertFloat(value, defaultFloat);
        if (clazz == Short.TYPE)
            return convertShort(value, defaultShort);
        if (clazz == (class$java$lang$Integer != null ? class$java$lang$Integer : (class$java$lang$Integer = class1("java.lang.Integer"))))
            return convertInteger(value, null);
        if (clazz == (class$java$lang$Boolean != null ? class$java$lang$Boolean : (class$java$lang$Boolean = class1("java.lang.Boolean"))))
            return convertBoolean(value, null);
        if (clazz == (class$java$lang$Long != null ? class$java$lang$Long : (class$java$lang$Long = class1("java.lang.Long"))))
            return convertLong(value, null);
        if (clazz == (class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class1("java.lang.Double"))))
            return convertDouble(value, null);
        if (clazz == (class$java$lang$Character != null ? class$java$lang$Character : (class$java$lang$Character = class1("java.lang.Character"))))
            return convertCharacter(value, null);
        if (clazz == (class$java$lang$Byte != null ? class$java$lang$Byte : (class$java$lang$Byte = class1("java.lang.Byte"))))
            return convertByte(value, null);
        if (clazz == (class$java$lang$Float != null ? class$java$lang$Float : (class$java$lang$Float = class1("java.lang.Float"))))
            return convertFloat(value, null);
        if (clazz == (class$java$lang$Short != null ? class$java$lang$Short : (class$java$lang$Short = class1("java.lang.Short"))))
            return convertShort(value, null);
        if (value == null)
            return (String) null;
        else
            return value.toString();
    }

    public static Object convert(String values[], Class clazz) {
        Class type = clazz.getComponentType();
        if (type == stringClass)
            if (values == null)
                return (String[]) null;
            else
                return values;
        int len = values.length;

        if (type == Integer.TYPE) {
            int[] array = new int[len];
            for (int i = 0; i < len; i++)
                array[i] = convertInteger(values[i], defaultInteger).intValue();

            return array;
        }
        if (type == Boolean.TYPE) {
            boolean[] array = new boolean[len];
            for (int i = 0; i < len; i++)
                array[i] = convertBoolean(values[i], defaultBoolean).booleanValue();

            return array;
        }
        if (type == Long.TYPE) {
            long[] array = new long[len];
            for (int i = 0; i < len; i++)
                array[i] = convertLong(values[i], defaultLong).longValue();

            return array;
        }
        if (type == Double.TYPE) {
            double[] array = new double[len];
            for (int i = 0; i < len; i++)
                array[i] = convertDouble(values[i], defaultDouble).doubleValue();

            return array;
        }
        if (type == Character.TYPE) {
            char[] array = new char[len];
            for (int i = 0; i < len; i++)
                array[i] = convertCharacter(values[i], defaultCharacter).charValue();

            return array;
        }
        if (type == Byte.TYPE) {
            byte[] array = new byte[len];
            for (int i = 0; i < len; i++)
                array[i] = convertByte(values[i], defaultByte).byteValue();

            return array;
        }
        if (type == Float.TYPE) {
            float[] array = new float[len];
            for (int i = 0; i < len; i++)
                array[i] = convertFloat(values[i], defaultFloat).floatValue();

            return array;
        }
        if (type == Short.TYPE) {
            short[] array = new short[len];
            for (int i = 0; i < len; i++)
                array[i] = convertShort(values[i], defaultShort).shortValue();

            return array;
        }
        if (type == (class$java$lang$Integer != null ? class$java$lang$Integer : (class$java$lang$Integer = class1("java.lang.Integer")))) {
            Integer[] array = new Integer[len];
            for (int i = 0; i < len; i++)
                array[i] = convertInteger(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Boolean != null ? class$java$lang$Boolean : (class$java$lang$Boolean = class1("java.lang.Boolean")))) {
            Boolean[] array = new Boolean[len];
            for (int i = 0; i < len; i++)
                array[i] = convertBoolean(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Long != null ? class$java$lang$Long : (class$java$lang$Long = class1("java.lang.Long")))) {
            Long[] array = new Long[len];
            for (int i = 0; i < len; i++)
                array[i] = convertLong(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Double != null ? class$java$lang$Double : (class$java$lang$Double = class1("java.lang.Double")))) {
            Double[] array = new Double[len];
            for (int i = 0; i < len; i++)
                array[i] = convertDouble(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Character != null ? class$java$lang$Character : (class$java$lang$Character = class1("java.lang.Character")))) {
            Character[] array = new Character[len];
            for (int i = 0; i < len; i++)
                array[i] = convertCharacter(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Byte != null ? class$java$lang$Byte : (class$java$lang$Byte = class1("java.lang.Byte")))) {
            Byte[] array = new Byte[len];
            for (int i = 0; i < len; i++)
                array[i] = convertByte(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Float != null ? class$java$lang$Float : (class$java$lang$Float = class1("java.lang.Float")))) {
            Float[] array = new Float[len];
            for (int i = 0; i < len; i++)
                array[i] = convertFloat(values[i], null);

            return array;
        }
        if (type == (class$java$lang$Short != null ? class$java$lang$Short : (class$java$lang$Short = class1("java.lang.Short")))) {
            Short[] array = new Short[len];
            for (int i = 0; i < len; i++)
                array[i] = convertShort(values[i], null);

            return array;
        }
        if (values == null)
            return (String[]) null;
        String[] array = new String[len];
        for (int i = 0; i < len; i++)
            array[i] = values[i].toString();

        return array;
    }

    private static Boolean convertBoolean(String value, Boolean defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on"))
            return Boolean.TRUE;
        if (value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("off"))
            return Boolean.FALSE;
        else
            return defaultValue;
    }

    private static Byte convertByte(String value, Byte defaultValue) {
        try {
            return new Byte(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    private static Character convertCharacter(String value, Character defaultValue) {
        if (value == null || value.length() == 0)
            return defaultValue;
        else
            return new Character(value.charAt(0));
    }

    private static Double convertDouble(String value, Double defaultValue) {
        try {
            return new Double(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    private static Float convertFloat(String value, Float defaultValue) {
        try {
            return new Float(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    private static Integer convertInteger(String value, Integer defaultValue) {
        try {
            return new Integer(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    private static Long convertLong(String value, Long defaultValue) {
        try {
            return new Long(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    private static Short convertShort(String value, Short defaultValue) {
        try {
            return new Short(value);
        } catch (NumberFormatException numberformatexception) {
            return defaultValue;
        }
    }

    static Class class1(String x0) {
        log.debug("Load " + x0);
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static Boolean defaultBoolean;
    private static Byte defaultByte = new Byte((byte) 0);
    private static Character defaultCharacter = new Character(' ');
    private static Double defaultDouble = new Double(0.0D);
    private static Float defaultFloat = new Float(0.0F);
    private static Integer defaultInteger = new Integer(0);
    private static Long defaultLong = new Long(0L);
    private static Short defaultShort = new Short((short) 0);
    private static Class stringClass;
    static Class class$java$lang$String; /* synthetic field */
    static Class class$java$lang$Integer; /* synthetic field */
    static Class class$java$lang$Boolean; /* synthetic field */
    static Class class$java$lang$Long; /* synthetic field */
    static Class class$java$lang$Double; /* synthetic field */
    static Class class$java$lang$Character; /* synthetic field */
    static Class class$java$lang$Byte; /* synthetic field */
    static Class class$java$lang$Float; /* synthetic field */
    static Class class$java$lang$Short; /* synthetic field */

    static {
        defaultBoolean = Boolean.FALSE;
        stringClass = class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = class1("java.lang.String"));
    }

}