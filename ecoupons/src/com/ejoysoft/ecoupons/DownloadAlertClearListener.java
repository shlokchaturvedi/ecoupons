package com.ejoysoft.ecoupons;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class DownloadAlertClearListener implements ServletContextListener {

	private Timer timer;

	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		System.out.println("=========DownloadAlert Clear Listener Destroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		System.out.println("==========DownloadAlert Clear Listener Initialized");
		AlertClearTask task = new AlertClearTask(sce.getServletContext());
//		timer.schedule(task, 0, 24*60*60*1000);
		SimpleDateFormat dFormat = new SimpleDateFormat("hh:mm:ss");
		try {
			timer.schedule(task, dFormat.parse("22:00:00"), 24*60*60*1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("==========DownloadAlert Clear Listener Scheduled");
	}
	
	class AlertClearTask extends TimerTask {

		private ServletContext application;
		
		public AlertClearTask(ServletContext servletContext) {
			this.application = servletContext;
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
