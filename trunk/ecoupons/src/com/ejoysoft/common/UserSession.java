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
	     * ����������֧�ֶ���¼
	     */
	    private HashMap onlineUserInfo = new HashMap();
	    /**
	     * �û��Ƿ���֤
	     */
	    private boolean isAuth = false;
	    //��½��ip��ַ
	    private String loginIp;
	    private String dLatestLoginTime;
	   //Ȩ����Ϣ
	    private HashMap rights = new HashMap();
	    //ÿҳ��¼����
	    private int intPageSize;
	    //�ֱ���
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
	            Globa.logger0("�û���¼", "u/"+strUserId+"/"+strName, loginIp, "��¼�ɹ�", "ϵͳ����",strDepart);
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
	            //��¼�˳�ʱ��
	            Globa globa = new Globa();
	            try {
	                System.out.println("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	                globa.db.executeUpdate("UPDATE t_sy_user SET fOnlineTime = fOnlineTime + DATEDIFF([minute], dLatestLoginTime, getdate())  where strUserId = '" + strUserId + "'");
	                Globa.logger0("�û��˳�",  "u/"+strUserId+"/"+strName, loginIp, "�˳�ϵͳ", "ϵͳ����",strDepart);
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
	    private String     strId ;//�û�id
	    private String strUserId;//		�û���
	    private String strPWD;//			����
	    private String strName;//		����
	    private int intType;//		�û����ͣ���ͨ��Ա-0���쵼-1��
	    private String[] strUnitId;//		����λid
	    private String unitIds;//		������λid���12333,3222
	    private String strUnitCode;//			����λ����
	    private String strUnitName;//			����λ���
	    private String strDepart;//		��֯���
	    private String strCssType;//		�û��ĸ��Ի�ҳ������ʽ
	    private String strCaNO;//			mac��ַ
	    private String strRootId;//			��b��λ��id
	    private String strInfoName;//			��Ϣ�ϱ���λ��ʾ�����
	    private String strDirectName;//			�����ϱ���λ��ʾ�����
	    private String strUpUnitType;//			�û�����
	    private String strSendCenter;//			���а� ���?������
	    private String strAllUnits;//			�û�����������ϼ��û��飬��ʽ��1111��2222��333
	    private String  strMsnQQ;        //Ӱ�����Ȩ��
	    private String strEmail;//			E-mail
	    private int intUnitType;//int		N		��Ϣ�ϱ���λ����1-��Ϣ����2-�����ң�
	    private int intUserType;//�û������ͣ�0��ϵͳ����Ա  1��ҵ�����  2�����¼��Ա  3����ͨ����Ա��
	    private String strBuildId;//������ҵ����������Id

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
	    //�ж��Ƿ����ƶ��û����û�
	    public boolean hasUnitUser(String unitId) {
	        if(strUnitId==null) return false;
	        else
	        for (int i = 0; i < strUnitId.length; i++) {
	            if (strUnitId[i].equals(unitId))
	                return true;
	        }
	        return false;
	    }
	    //��һ����Ϣ
	    private HashMap hmUpInfo = new HashMap();
	    //��һ����Ϣ
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
}
