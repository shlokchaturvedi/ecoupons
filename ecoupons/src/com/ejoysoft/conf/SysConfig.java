package com.ejoysoft.conf;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-19
 * Time: 9:06:28
 * To change this template use Options | File Templates.
 */
public class SysConfig {

    public static ServletContext application; //可以在JSP和Servlet中使用来获得配置信息
    private static Properties prop;

    public static void init(String sysConfigFileName, ServletContext sc) {
        try {
        	
        	application = sc;
            //读取配置文件
            InputStream is = new FileInputStream(application.getRealPath("")+"/" +sysConfigFileName);
            prop = new Properties();
            prop.load(is);
            //加载系统配置信息
            if(prop!=null)  {
                Enumeration eProps=prop.propertyNames();
                while (eProps.hasMoreElements()) {
                    String strTempr=(String)eProps.nextElement();
                    String strPropValue=new String(prop.getProperty(strTempr).getBytes("ISO8859-1"), "UTF-8");
                    prop.setProperty(strTempr, strPropValue);
                    application.setAttribute(strTempr, strPropValue);
                }
            }
            System.out.println("[INFO]:SysConfig Initialized Successful");
        } catch (Exception e) {
        	System.out.println("[ERROR]:An error occured in SysConfig.init()");
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
    	return prop.getProperty("name");
    }
}