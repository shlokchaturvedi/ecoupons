package com.ejoysoft.ecoupons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class DownloadAlertClearListener implements ServletContextListener {

	private Timer timer;
	private ServletContext application;
	private Properties prop;
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		System.out.println("=========DownloadAlert Clear Listener Destroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		this.application = sce.getServletContext();
	    InputStream is;
		try {
	        String dealTime="22:00:00";
			is = new FileInputStream(application.getRealPath("")+"/WEB-INF/sysConfig.properties");
	        prop = new Properties();
	        prop.load(is);
	        if(prop!=null)
	        	dealTime = new String(prop.getProperty("DOWNLOAD_ALERT_CLEAR_TIME").getBytes("ISO8859-1"), "UTF-8");
			System.out.println("==========DownloadAlert Clear Listener Initialized");
			AlertClearTask task = new AlertClearTask();
			SimpleDateFormat dFormat = new SimpleDateFormat("hh:mm:ss");
			timer.schedule(task, dFormat.parse(dealTime),24*60*60*1000);
			System.out.println("==========DownloadAlert Clear Listener Scheduled");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	class AlertClearTask extends TimerTask {
		public AlertClearTask() {
		}

		@Override
		public void run() {
			Globa globa = new Globa();
			globa.initialize(application);
			DbConnect db = globa.db;
			try {
				db.executeUpdate("delete from  t_bz_download_alert where intState=1 ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				globa.closeCon();
			}	
			System.out.println("----------DownloadAlert Clear Listener Run[" + new Date().toLocaleString().substring(0,10) + "]");
			
		}
		
	}
}
