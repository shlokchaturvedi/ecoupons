package com.ejoysoft.conf;

import javax.servlet.ServletContext;
import java.util.Properties;
import java.util.Enumeration;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2007-3-6
 * Time: 10:00:45
 * To change this template use Options | File Templates.
 */
public class Prop {
    private Properties props;
    String confFile = "sysConfig.properties";
    boolean action = false;
    ServletContext application;
     //���췽��
    public Prop() {
    }
    //���췽��
    public Prop(ServletContext application) {
       this.application = application;
    }
    public void init(ServletContext sc) {
        try {
            application = sc;
            //��ȡ�����ļ�
            InputStream is = new FileInputStream(application.getRealPath("") + "/WEB-INF/" + confFile);
            Properties prop = new Properties();
            prop.load(is);
            //����ϵͳ������Ϣ
            if (prop != null) {
                Enumeration eProps = prop.propertyNames();
                while (eProps.hasMoreElements()) {
                    String strTempr = (String) eProps.nextElement();
                    String strPropValue = prop.getProperty(strTempr);
                    application.setAttribute(strTempr, com.ejoysoft.util.StringUtil.CharSetConvertDefault(strPropValue));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** load the config file to properties*/
    private void confFileLoad() {
        try {
            props = new Properties();
            InputStream inputstream = new FileInputStream(application.getRealPath("") + "/WEB-INF/" + confFile);
            props.load(inputstream);
            inputstream.close();
        } catch (FileNotFoundException e) {
            System.out.println("init the parameters false: file not fond......");
        } catch (IOException e) {
        }
    }

    public Properties getProp() {
        confFileLoad();
        return props;
    }

    /**  save a propties value to config file*/

    public void setPropValue(String prop, String value) {
        try {
            value = new String(value.getBytes("gb2312"), "iso8859-1");
            if (props == null)
                props = getProp();
            props.setProperty(prop, value);
            String path = application.getRealPath("") + "/WEB-INF/" + confFile;
            FileOutputStream fo = new FileOutputStream(path);
            props.store(fo, "sxca 1.0 ca  properties ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the value of conf File
     * @param prop
     * @param value
     */
    public void setValue(String prop, String value) {
        try {
            value = new String(value.getBytes("gb2312"), "iso8859-1");
            if (props == null)
                props = getProp();
            props.setProperty(prop, value);
            action = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setValueSave() {
        try {
            String path = application.getRealPath("") + "/WEB-INF/" + confFile;
            FileOutputStream fo = new FileOutputStream(path);
            if (action) {
                props.store(fo, "sxca 1.0 ca properties ");
                Enumeration e_props = props.propertyNames();
                while (e_props.hasMoreElements()) {
                    String tempAttr = (String) e_props.nextElement();
                    String propValue = props.getProperty(tempAttr);
                    application.setAttribute(tempAttr, getCn(propValue));
                }
            }
            fo.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getValue(String name) {
        try {
            if (props == null)
                props = getProp();
            String value = props.getProperty(name, "");
            return new String(value.getBytes("iso8859-1"), "gb2312");
        } catch (Exception e) {
            return "";
        }
    }

    private String getCn(String value) {
        try {
            return new String(value.getBytes("iso8859-1"), "gb2312");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}