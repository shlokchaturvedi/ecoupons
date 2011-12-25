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

public class UserSession implements HttpSessionBindingListener {

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


	    public UserSession(ServletContext application) {
	        this.application = application;
	        this.strUserId = "anonymous";
	    }

	    public void clear() {
	    }

	    public void valueBound(HttpSessionBindingEvent event) {
	        HttpSession session = event.getSession();
	        if (application != null) {
	            if (application.getAttribute(Constants.OnlineName) != null)
	                onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	            onlineName.add(strUserId);
	            application.setAttribute(Constants.OnlineName, onlineName);
	            //-------------------------------------------------------------------------------------------------------------------
	            if (application.getAttribute(Constants.OnlineUserInfo) != null)
	                onlineUserInfo = (HashMap) application.getAttribute(Constants.OnlineUserInfo);
	            onlineUserInfo.put(session.getId(), this);
	            application.setAttribute(Constants.OnlineUserInfo, onlineUserInfo);
	            //-------------------------------------------------------------------------------------------------------------------
	            Globa.logger0("用户登录", "u/"+strUserId+"/"+strName, loginIp, "登录成功", "系统管理",strDepart);
	            System.out.println("[" + Format.getDateTime() + "]  user [" + strUserId + "] login from " + loginIp + " !");
	        }
	    }

	    public void valueUnbound(HttpSessionBindingEvent event) {
	        HttpSession session = event.getSession();
	        if (application != null) {
	            if (application.getAttribute(Constants.OnlineName) != null)
	                onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	            onlineName.remove(strUserId);
	            application.setAttribute(Constants.OnlineName, onlineName);
	//-------------------------------------------------------------------------------------------------------------------
	            if (application.getAttribute(Constants.OnlineUserInfo) != null)
	                onlineUserInfo = (HashMap) application.getAttribute(Constants.OnlineUserInfo);
	            onlineUserInfo.remove(session.getId());
	            application.setAttribute(Constants.OnlineUserInfo, onlineUserInfo);
	//-------------------------------------------------------------------------------------------------------------------
	            //记录退出时间
	            Globa globa = new Globa();
	            try {
	                System.out.println("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	                globa.db.executeUpdate("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	                Globa.logger0("用户退出",  "u/"+strUserId+"/"+strName, loginIp, "退出系统", "系统管理",strDepart);
	            } catch (SQLException e) {
	            } finally {
	                globa.closeCon();
	            }
	            System.err.println("[" + Format.getDateTime() + "]  user [" + strUserId + "] logout !");
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
	    private String     strId ;//用户id
	    private String strUserId;//		用户名
	    private String strPWD;//			密码
	    private String strName;//		姓名
	    private int intType;//		用户类型（普通人员-0、领导-1）
	    private String[] strUnitId;//		所属单位id
	    private String unitIds;//		所属多个单位id组合12333,3222
	    private String strUnitCode;//			所属单位编码
	    private String strUnitName;//			所属单位名称
	    private String strDepart;//		组织名称
	    private String strCssType;//		用户的个性化页面风格样式
	    private String strCaNO;//			mac地址
	    private String strRootId;//			独立单位的id
	    private String strInfoName;//			信息上报单位标示的名称
	    private String strDirectName;//			督查上报单位标示的名称
	    private String strUpUnitType;//			用户类型
	    private String strSendCenter;//			报中办 允许，不允许
	    private String strAllUnits;//			用户所属的所有上级用户组，格式：1111，2222，333
	    private String  strMsnQQ;        //影视浏览权限
	    private String strEmail;//			E-mail
	    private int intUnitType;//int		N		信息上报单位类型1-信息处，2-督查室，
	    private int intUserType;//用户基本类型（0：系统管理员  1：业务主管  2：数据录入员  3：普通操作员）
	    private String strBuildId;//建筑物业主所属建筑物Id
	    private String strShopid;//商家id

	    public int getIntUnitType() {
	        return intUnitType;
	    }

	    public void setIntUnitType(int intUnitType) {
	        this.intUnitType = intUnitType;
	    }

	    public int getIntUserType() {
			return intUserType;
		}

		public void setIntUserType(int intUserType) {
			this.intUserType = intUserType;
		}

		public String getStrBuildId() {
			return Format.removeNull(strBuildId);
		}

		public void setStrBuildId(String strBuildId) {
			this.strBuildId = strBuildId;
		}

		public String getStrEmail() {
	        return  Format.removeNull(strEmail);
	    }

	    public void setStrEmail(String strEmail) {
	        this.strEmail = strEmail;
	    }

	    public String getStrMsnQQ() {
	        return Format.removeNull(strMsnQQ) ;
	    }

	    public void setStrMsnQQ(String strMsnQQ) {
	        this.strMsnQQ = strMsnQQ;
	    }

	    public String getStrAllUnits() {
	        return strAllUnits;
	    }

	    public void setStrAllUnits(String strAllUnits) {
	        this.strAllUnits = strAllUnits;
	    }

	    public String getStrSendCenter() {
	        return Format.removeNull(strSendCenter);
	    }

	    public void setStrSendCenter(String strSendCenter) {
	        this.strSendCenter = strSendCenter;
	    }

	    public String getStrUpUnitType() {
	        return  Format.removeNull(strUpUnitType);
	    }

	    public void setStrUpUnitType(String strUpUnitType) {
	        this.strUpUnitType = strUpUnitType;
	    }

	    public String getStrInfoName() {
	        return Format.removeNull(strInfoName);
	    }

	    public void setStrInfoName(String strInfoName) {
	        this.strInfoName = strInfoName;
	    }

	    public String getStrDirectName() {
	        return Format.removeNull(strDirectName);
	    }

	    public void setStrDirectName(String strDirectName) {
	        this.strDirectName = strDirectName;
	    }

	 public String getUnitIds() {
	        return unitIds;
	    }

	    public void setUnitIds(String unitIds) {
	        this.unitIds = unitIds;
	    }

	    public String getStrUnitName() {
	        return strUnitName;
	    }

	    public void setStrUnitName(String strUnitName) {
	        this.strUnitName = strUnitName;
	    }

	    public String getStrRootId() {
	        return Format.removeNull(strRootId);
	    }

	    public void setStrRootId(String strRootId) {
	        this.strRootId = strRootId;
	    }

	    public String getStrCaNO() {
	        return strCaNO;
	    }

	    public void setStrCaNO(String strCaNO) {
	        this.strCaNO = strCaNO;
	    }

	    public String getStrId() {
	        return strId;
	    }

	    public void setStrId(String strId) {
	        this.strId = strId;
	    }

	    public String getStrUserId() {
	        return strUserId;
	    }

	    public void setStrUserId(String strUserId) {
	        this.strUserId = strUserId;
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

	    public String[] getStrUnitId() {
	        return strUnitId;
	    }

	    public void setStrUnitId(String[] strUnitId) {
	        this.strUnitId = strUnitId;
	    }

	    public String getStrUnitCode() {
	        return strUnitCode;
	    }

	    public void setStrUnitCode(String strUnitCode) {
	        this.strUnitCode = strUnitCode;
	    }

	    public String getStrDepart() {
	        return strDepart;
	    }

	    public void setStrDepart(String strDepart) {
	        this.strDepart = strDepart;
	    }

	    public String getStrCssType() {
	        return strCssType;
	    }

	    public void setStrCssType(String strCssType) {
	        this.strCssType = strCssType;
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
	    	if (this.strUserId.equals("admin")) {
	    		return true;
	    	}
	        Object[] modules = this.rights.keySet().toArray();
	        for (int i = 0; i < modules.length; i++) {
	            if (modules[i].toString().startsWith(code))
	                return true;
	        }
	        return false;
	    }
	    //判断是否是制定用户组用户
	    public boolean hasUnitUser(String unitId) {
	        if(strUnitId==null) return false;
	        else
	        for (int i = 0; i < strUnitId.length; i++) {
	            if (strUnitId[i].equals(unitId))
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
		 * @return the strShopid
		 */
		public String getStrShopid() {
			return strShopid;
		}

		/**
		 * @param strShopid the strShopid to set
		 */
		public void setStrShopid(String strShopid) {
			this.strShopid = strShopid;
		}
	    
}
