package com.ejoysoft.ecoupons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;

public class TerminalStateListener implements ServletContextListener {

	private Timer timer;

	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		System.out.println("==========Terminal State Listener Destroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		System.out.println("==========Terminal State Listener Initialized");
		TerminalStateTask task = new TerminalStateTask(sce.getServletContext());
		timer.schedule(task, 0, 20 * 60 * 1000);
		System.out.println("==========Terminal State Listener Scheduled");
	}
	
	class TerminalStateTask extends TimerTask {

		private ServletContext application;
		
		public TerminalStateTask(ServletContext servletContext) {
			this.application = servletContext;
		}

		@Override
		public void run() {
			Globa globa = new Globa();
			globa.initialize(application);
			DbConnect db = globa.db;
			//查询终端刷新时间间隔参数值
			ResultSet rs = db.executeQuery("select strParamValue from t_bz_terminal_param where strParamName='intRefreshSec'");
			int intRefreshSec = 60;
			try {
				if (rs.next()) {
					intRefreshSec = Integer.parseInt(rs.getString("strParamValue"));
				}
				Date now = new Date();
				Date dtBase = new Date(now.getTime() - intRefreshSec * 2 * 1000);//计算最迟刷新时间，当前时间-刷新间隔的2倍
				//根据最后刷新时间更新终端状态
				db.executeUpdate("update t_bz_terminal set intState = -1 where dtRefreshTime < '" + dtBase.toLocaleString() + "'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				globa.closeCon();
			}
			System.out.println("----------Terminal State Listener Run[" + new Date().toLocaleString() + "]");
		}
		
	}
}
