package com.ejoysoft.common;


import com.ejoysoft.conf.Module;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.text.SimpleDateFormat;

public class MemberSession implements HttpSessionBindingListener {

	   private ServletContext application;
	    private ArrayList onlineName = new ArrayList();
	    /**
	     * 在线总人数，支持多点登录
	     */
	    private HashMap onlineUserInfo = new HashMap();
	    /**
	     * 用户是否认证
	     */
	    private boolean isAuth = false;
	    //登陆的ip地址
	    private String loginIp;
	    private String dLatestLoginTime;
	   //权限信息
	    private HashMap rights = new HashMap();
	    //每页记录条数
	    private int intPageSize;
	    //分辨率
	    private int exculpate;


	    public MemberSession(ServletContext application) {
	        this.application = application;
	       // this.strUserId = "anonymous";
	    }

	    public void clear() {
	    }

	    public void valueBound(HttpSessionBindingEvent event) {
	        HttpSession session = event.getSession();
	        if (application != null) {
	            if (application.getAttribute(Constants.OnlineName) != null)
	                onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	            onlineName.add(strCardNo);
	            application.setAttribute(Constants.OnlineName, onlineName);
	            //-------------------------------------------------------------------------------------------------------------------
	            if (application.getAttribute(Constants.OnlineUserInfo) != null)
	                onlineUserInfo = (HashMap) application.getAttribute(Constants.OnlineUserInfo);
	            onlineUserInfo.put(session.getId(), this);
	            application.setAttribute(Constants.OnlineUserInfo, onlineUserInfo);
	            //-------------------------------------------------------------------------------------------------------------------
	            Globa.logger0("会员登录", strCardNo, loginIp, "登录成功", "系统管理","");
	            System.out.println("[" + Format.getDateTime() + "]  member [" + strCardNo + "] login from " + loginIp + " !");
	        }
	    }

	    public void valueUnbound(HttpSessionBindingEvent event) {
	        HttpSession session = event.getSession();
	        if (application != null) {
	            if (application.getAttribute(Constants.OnlineName) != null)
	                onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	            onlineName.remove(strCardNo);
	            application.setAttribute(Constants.OnlineName, onlineName);
	//-------------------------------------------------------------------------------------------------------------------
	            if (application.getAttribute(Constants.OnlineUserInfo) != null)
	                onlineUserInfo = (HashMap) application.getAttribute(Constants.OnlineUserInfo);
	            onlineUserInfo.remove(session.getId());
	            application.setAttribute(Constants.OnlineUserInfo, onlineUserInfo);
	//-------------------------------------------------------------------------------------------------------------------
	            //记录退出时间
	            Globa globa = new Globa();
	           
	               // System.out.println("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	               // globa.db.executeUpdate("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	                Globa.logger0("会员退出",  strCardNo, loginIp, "退出系统", "系统管理","");
	           
	                globa.closeCon();
	           
	            System.err.println("[" + Format.getDateTime() + "]  member [" + strCardNo + "] logout !");
	        }//end if(application!=null)
	    }


	    public void setLoginIp(String loginIp) {
	        this.loginIp = loginIp;
	    }

	    public String getLoginIp() {
	        return loginIp;
	    }

	    public String getdLatestLoginTime() {
	        return dLatestLoginTime;
	    }

	    public void setdLatestLoginTime(String dLatestLoginTime) {
	        this.dLatestLoginTime = dLatestLoginTime;
	    }

	    public void setAuth(boolean auth) {
	        isAuth = auth;
	    }

	    public boolean isAuth() {
	        return isAuth;
	    }

	    public int getExculpate() {
	        return exculpate;
	    }

	    public void setExculpate(int exculpate) {
	        this.exculpate = exculpate;
	        if (exculpate <= 800)
	            intPageSize = Constants.PAGE_SIZE_1024;
	        else if (exculpate > 800 & exculpate < 1024)
	            intPageSize = Constants.PAGE_SIZE_1024;
	        else
	            intPageSize = Constants.PAGE_SIZE_1024;
	    }
	    public int getIntPageSize() {
	        return intPageSize;
	    }

	    public ArrayList getOnlineName() {
	        return onlineName;
	    }

	    public HashMap getOnlineUserInfo() {
	        return onlineUserInfo;
	    }
	    private String strId ;//用户id
	   // private String strUserId;//		用户名
	    private String strPWD;//			密码    
	  
	    private String strCardNo;// 卡号
		private String strMobileNo;// 手机号
		private String strName;//会员名称
		private int intType;// 会员类型0普通会员1VIP会员
		private String dtActiveTime;// 激活操作的系统日期
		private float flaBalance;
		private int intPoint;
		private String dtExpireTime;
		private String strSalesman;// 销售员姓名
		private String strPwd;
	    
	        
	    
	 
	    public String getStrId() {
	        return strId;
	    }

	    public void setStrId(String strId) {
	        this.strId = strId;
	    }

	  
	    public String getStrPWD() {
	        return strPWD;
	    }

	    public void setStrPWD(String strPWD) {
	        this.strPWD = strPWD;
	    }

	    public String getStrName() {
	        return strName;
	    }

	    public void setStrName(String strName) {
	        this.strName = strName;
	    }

	    public int getIntType() {
	        return intType;
	    }

	    public void setIntType(int intType) {
	        this.intType = intType;
	    }

	  
	    public HashMap getRights() {
	        return rights;
	    }

	    public void setRights(HashMap rights) {
	        this.rights = rights;
	    }
	    public boolean hasRight(Module module) {
	        String code = module.getCode();
	        return this.hasRight(code);
	    }

	    public boolean hasRight(String code) {
	    	
	        Object[] modules = this.rights.keySet().toArray();
	        for (int i = 0; i < modules.length; i++) {
	            if (modules[i].toString().startsWith(code))
	                return true;
	        }
	        return false;
	    }
	  
	    //上一条信息
	    private HashMap hmUpInfo = new HashMap();
	    //下一条信息
	    private HashMap hmDownInfo = new HashMap();

	    public HashMap getHmUpInfo() {
	        return hmUpInfo;
	    }

	    public void setHmUpInfo(HashMap hmUpInfo) {
	        this.hmUpInfo = hmUpInfo;
	    }

	    public HashMap getHmDownInfo() {
	        return hmDownInfo;
	    }

	    public void setHmDownInfo(HashMap hmDownInfo) {
	        this.hmDownInfo = hmDownInfo;
	    }

		/**
		 * @return the dLatestLoginTime
		 */
		public String getDLatestLoginTime() {
			return dLatestLoginTime;
		}

		/**
		 * @param latestLoginTime the dLatestLoginTime to set
		 */
		public void setDLatestLoginTime(String latestLoginTime) {
			dLatestLoginTime = latestLoginTime;
		}

		/**
		 * @return the strCardNo
		 */
		public String getStrCardNo() {
			return strCardNo;
		}

		/**
		 * @param strCardNo the strCardNo to set
		 */
		public void setStrCardNo(String strCardNo) {
			this.strCardNo = strCardNo;
		}

		/**
		 * @return the dtActiveTime
		 */
		public String getDtActiveTime() {
			return dtActiveTime;
		}

		/**
		 * @param dtActiveTime the dtActiveTime to set
		 */
		public void setDtActiveTime(String dtActiveTime) {
			this.dtActiveTime = dtActiveTime;
		}

		/**
		 * @return the flaBalance
		 */
		public float getFlaBalance() {
			return flaBalance;
		}

		/**
		 * @param flaBalance the flaBalance to set
		 */
		public void setFlaBalance(float flaBalance) {
			this.flaBalance = flaBalance;
		}

		/**
		 * @return the dtExpireTime
		 */
		public String getDtExpireTime() {
			return dtExpireTime;
		}

		/**
		 * @param dtExpireTime the dtExpireTime to set
		 */
		public void setDtExpireTime(String dtExpireTime) {
			this.dtExpireTime = dtExpireTime;
		}

		/**
		 * @return the strSalesman
		 */
		public String getStrSalesman() {
			return strSalesman;
		}

		/**
		 * @param strSalesman the strSalesman to set
		 */
		public void setStrSalesman(String strSalesman) {
			this.strSalesman = strSalesman;
		}

		/**
		 * @return the strPwd
		 */
		public String getStrPwd() {
			return strPwd;
		}

		/**
		 * @param strPwd the strPwd to set
		 */
		public void setStrPwd(String strPwd) {
			this.strPwd = strPwd;
		}

		/**
		 * @return the strMobileNo
		 */
		public String getStrMobileNo() {
			return strMobileNo;
		}

		/**
		 * @param strMobileNo the strMobileNo to set
		 */
		public void setStrMobileNo(String strMobileNo) {
			this.strMobileNo = strMobileNo;
		}

		/**
		 * @return the intPoint
		 */
		public int getIntPoint() {
			return intPoint;
		}

		/**
		 * @param intPoint the intPoint to set
		 */
		public void setIntPoint(int intPoint) {
			this.intPoint = intPoint;
		}

	
	    
}
