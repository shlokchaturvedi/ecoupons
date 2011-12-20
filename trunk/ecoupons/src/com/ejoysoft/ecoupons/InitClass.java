package com.ejoysoft.ecoupons;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ejoysoft.conf.SysConfig;
import com.ejoysoft.conf.SysModule;
import com.ejoysoft.ecoupons.system.SysPara;
import com.ejoysoft.ecoupons.system.SysUserUnit;
public class InitClass  extends GenericServlet {
    ServletContext application;
    private static int intFlag=0;
   /**
    * 初始化�?�加载数据源，读取配置信�?

    */
   public void init() {
       this.application = getServletContext();
       ServletConfig scf = getServletConfig();
       
       //获得数据源地JNDI名称
       String sysJndiName = scf.getInitParameter("DB_JNDI_NAME");
        //加载数据�? 
       com.ejoysoft.common.DbConnect.initPool(sysJndiName);
       //获得系统参数的配置文件名
       String sysConfigFileName = scf.getInitParameter("SYS_CONFIG_FILE");
       SysConfig.init(sysConfigFileName, application); //初始化系统配置信�?
       //加载�?有的功能菜单信息
       SysModule.init(application);
       try {
           //加载系统中所有的用户组（组织机构）信�?
    	   SysUserUnit.init("");
    	   SysPara.init();
       } catch (Exception e) {
           System.out.println("--------------系统启动时出错：");
           e.printStackTrace();
       }
       intFlag=1;
   }
   public void service(ServletRequest req, ServletResponse res) {
	   if(intFlag==0)
       init();
   }
}