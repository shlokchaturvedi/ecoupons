package com.ejoysoft.conf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Vector;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.InputStream;

import com.ejoysoft.common.Format;
import com.ejoysoft.common.Globa;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-13
 * Time: 13:33:44
 * To change this template use Options | File Templates.
 */
public class SysModule {

    private static Logger logger = Logger.getLogger(SysModule.class); //日志
    private static Vector modules = new Vector();   //顶级模块向量
    private final static String PATH_MODULE = "PATH_MODULE";
    private static Document doc = null;
    /**
     * 初始化，加载所有功能模块信息
     * @param application
     */
    public static Document init(javax.servlet.ServletContext application) {
        
    	
    	if (doc != null || (doc = (Document) application.getAttribute(com.ejoysoft.common.Constants.SYSTEM_DOM)) != null)
            return doc;
        DocumentBuilder builder = null;
        InputStream inputstream = null;
        try {
             //init document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            builder = factory.newDocumentBuilder();
            inputstream = new FileInputStream(application.getRealPath("").replace("\\", "/")+"/"+(String) application.getAttribute(PATH_MODULE));
            doc = builder.parse(inputstream);
            doc.normalize(); 
            inputstream.close();
             // 加载顶级模块信息
            Element elModules = doc.getDocumentElement();
            NodeList nlModule = elModules.getElementsByTagName("module");
            for (int i = 0; i < nlModule.getLength(); i++) {
                Element elModule = (Element)nlModule.item(i);
                if (((Element)elModule.getParentNode()).getTagName().equals("modules"))
                    modules.add(new Module(elModule, null));
            }
            System.out.println("[INFO]:SysModule Initialized Successful");
        } catch (java.io.FileNotFoundException fnf) {
        	System.out.println("[ERROR]:An error occured in SysModule.init()");
            fnf.printStackTrace();
        } catch (ParserConfigurationException pce) {
        	System.out.println("[ERROR]:An error occured in SysModule.init()");
            pce.printStackTrace();
        } catch (java.io.IOException ioe) {
        	System.out.println("[ERROR]:An error occured in SysModule.init()");
            ioe.printStackTrace();
        } catch (Exception oxs) {
        	System.out.println("[ERROR]:An error occured in SysModule.init()");
            oxs.printStackTrace();
        } finally {
            application.setAttribute(com.ejoysoft.common.Constants.SYSTEM_DOM, doc);
            logger.debug("SysModule initialized successful!");
            return doc;
        }
    }

    /**
     * 初始化，重新加载所有功能模块信息
     * @param application
     */
    public static Document init1(javax.servlet.ServletContext application,Document document) {
        modules.removeAllElements();
        try {
            doc = document;
             // 加载顶级模块信息
            Element elModules = doc.getDocumentElement();
            NodeList nlModule = elModules.getElementsByTagName("module");
            for (int i = 0; i < nlModule.getLength(); i++) {
                Element elModule = (Element)nlModule.item(i);
                if (((Element)elModule.getParentNode()).getTagName().equals("modules"))
                    modules.add(new Module(elModule, null));
            }
        }   finally {
            application.setAttribute(com.ejoysoft.common.Constants.SYSTEM_DOM, doc);
             logger.debug("SysModule initialized successful!");
            return doc;
        }
    }

    public static java.util.HashMap getRightHasMap(String code) {
        HashMap right = new HashMap();
        NodeList modules = doc.getElementsByTagName("module");
        for (int i = 0; i < modules.getLength(); i++) {
            Element module = (Element) modules.item(i);
            if (module.getAttribute("code").equals(code)) {
                NodeList children = module.getElementsByTagName("right");
                for (int j = 0; j < children.getLength(); j++) {
                    Element child = (Element) children.item(j);
                    right.put(child.getAttribute("key"), child.getAttribute("value"));
                }
                break;
            }   //ifend
        } //forend
        return right;
    }

    /**
     * 返回指定名称的顶级模块
     * @param moduleName
     * @return
     */
    public static Module getModule(String moduleName) {
        for (int i = 0; i < modules.size(); i++)
            if (((Module)modules.get(i)).getName().equals(moduleName))
                return (Module)modules.get(i);
        return null;
    }

    /**
     * 返回所有顶级模块
     * @return
     */
    public static Vector getModules() {
        return modules;
    }

    public static void setModules(Vector modules) {
        SysModule.modules = modules;
    }

    public static Document getDoc() {
        return doc;
    }

    public static void setDoc(Document doc) {
        SysModule.doc = doc;
    }

    public static Vector getModuleTree() {
        Vector tree = new Vector();
        //遍历顶级模块，加载模块信息
        for (int i = 0; i < modules.size(); i++) {
            //加入顶级模块
            Module theModule = (Module)modules.get(i);
            theModule.setIntLevel(1);
            tree.add(theModule);
            //加载下级模块
            theModule.loadAllChild(tree, 2);
        }
        return tree;
    }
}