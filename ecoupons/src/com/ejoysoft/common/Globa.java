package com.ejoysoft.common;


import org.w3c.dom.Document;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import java.io.IOException;

import com.ejoysoft.ecoupons.business.Member;
import com.ejoysoft.util.ParamUtil;
import com.ejoysoft.util.RequestUtils;


/**
 * �ļ���ƣ�Globa.java
 * @author feiwj    �޶��汾
 * @version $Revision: 1.0 $ $Date: 2005-1-25 11:31 $
 */
public class Globa {

	public ServletContext application;
	public HttpSession session;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public DbConnect db;
	public UserSession userSession;
	public MemberSession memberSession;
	private Member member;
	
	public String loginName;
	public String fullRealName;//��ʽ��u/loginName/realName
	public String unitCode;
	public String[] unitId;
	public String css;
	public String loginIp = "0.0.0.0";
	public int intPageSize = 15;
	
	public static String APP_TITLE;
	public static String APP_FOOTER;
	public static String APP_ORG;
	public static Vector<String> vctCollectStatus;
	
	public Globa() {
	    application = null;
	    session = null;
	    request = null;
	    response = null;
	    application = null;
	    db = new DbConnect();
	}
	
	/**
	 * ��ʼ��
	 * @param application
	 */
	public void initialize(ServletContext application) {
	    this.application = application;
	    this.APP_ORG = getPropValue("APP_ORG");
	    this.APP_TITLE = getPropValue("APP_TITLE");
	    if (APP_TITLE == null)
	        this.APP_TITLE = "UNTITLE";
	    this.APP_FOOTER = getPropValue("APP_FOOTER", "");
	    if (db == null)
	        this.db = new DbConnect();
	}
	/**
	 * ��ʼ��
	 * @param application
	 * @param request
	 */
	public void initialize(ServletContext application, HttpServletRequest request) {
	    initialize(application);
	    this.request = request;
	    this.session = request.getSession();
	    if (session.getAttribute(Constants.USER_KEY) != null) {
	        userSession = (UserSession) session.getAttribute(Constants.USER_KEY);
	        loginName = userSession.getStrUserId();
	        unitCode=userSession.getStrUnitCode();
	        unitId = userSession.getStrUnitId();
	        css= userSession.getStrCssType();
	        fullRealName="u/"+loginName+"/"+userSession.getStrName();
	    }
	    if (session.getAttribute(Constants.MEMBER_KEY) != null) {
			memberSession=(MemberSession)session.getAttribute(Constants.MEMBER_KEY);
			member=new Member();
			member.setFlaBalance(memberSession.getFlaBalance());
			member.setIntPoint(memberSession.getIntPoint());
			member.setIntType(memberSession.getIntType());
			member.setStrCardNo(memberSession.getStrCardNo());
			member.setStrMobileNo(memberSession.getStrMobileNo());
		}
	    this.loginIp = request.getRemoteAddr();
	    //String requestUrl = request.getRequestURL().toString();
	}
	public void initialize(ServletContext application, HttpServletRequest request, HttpServletResponse response) {
	    initialize(application, request);
	    this.response = response;
	}
	
	public Document getDoc() {
	    return (Document) application.getAttribute(Constants.SYSTEM_DOM);
	}
	
	//�ж��Ƿ�Ϊ�����û�
	public boolean isAdministrator() {
	    if (Constants.Administrator.equals(loginName))
	        return true;
	    else
	        return false;
	}
	/**
	 * �ж��û��Ƿ�����
	 * @param userUid
	 * @return
	 */
	public boolean isOnline(String userUid) {
	    ArrayList onlineName = null;
	    onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	    if (onlineName != null)
	        for (int i = 0; i < onlineName.size(); i++) {
	            if (userUid.equalsIgnoreCase((String) onlineName.get(i))) return true;
	        }
	    return false;
	}
	
	public int getOnlineNum() {
	    ArrayList onlineName = null;
	    onlineName = (ArrayList) application.getAttribute(Constants.OnlineName);
	    if (onlineName != null)
	        return onlineName.size();
	    else
	        return 0;
	}
	
	public java.util.HashMap getOnlineUserInfo() {
	    java.util.HashMap onlineUserInfo = null;
	    if (application.getAttribute(Constants.OnlineUserInfo) != null)
	        onlineUserInfo = (java.util.HashMap) application.getAttribute(Constants.OnlineUserInfo);
	    return onlineUserInfo;
	}
	
	/**
	 * ȡ��application�еĲ���ֵ
	 * @param name
	 * @return
	 */
	public String getPropValue(String name) {
	    Object value = application.getAttribute(name);
	    if (value != null)
	        return (String) value;
	    else
	        return null;
	}
	
	/**
	 * ȡ�������ļ��еĲ���
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getPropValue(String name, String defaultValue) {
	    Object value = getPropValue(name);
	    if (value != null)
	        return (String) value;
	    else
	        return defaultValue;
	}
	
	/**д����־ */
	public static void logger0(String logCode, String loginName, String loginIp, String logMsg, String strOther,String unitCode) {
	    Globa globa = new Globa();
	    globa.logger(logCode, loginName, loginIp, logMsg, strOther,unitCode);
	    globa.closeCon();
	}
	
	public int logger(String logCode, String logMsg, String strOther,String unitCode) {
	    return logger(logCode, loginName, loginIp, logMsg, strOther,unitCode);
	}
	
	/**
	 * д����־
	 */
	private int logger(String logCode, String loginName, String loginIp, String logMsg, String strOther,String unitCode) {
	
	    try {
	        db.prepareStatement("INSERT  INTO t_sy_log (strId, dOccurDate, strOperator, strContent, strCode, strOperatorIp, strOther,strUnitCode)VALUES (?,?,?,?,?,?,?,?)");
	        db.setString (1, UID.getID());
	        db.setTimestamp(2, Format.getTimestamp());
	        db.setString(3, loginName);
	        db.setString(4, logMsg);
	        db.setString(5, logCode);
	        db.setString(6, loginIp);
	        db.setString(7, strOther);
	        db.setString(8, unitCode);
	        return db.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	public ResultSet fingerTo(ResultSet rs, int intCurPage) throws SQLException {
	    int min = (intCurPage - 1) * intPageSize;
	    if (intCurPage != 1 && min > 0) rs.absolute(min);
	    return rs;
	}
	
	public ResultSet fingerTo(ResultSet rs) throws SQLException {
	    int intCurPage = getIntCurPage();
	    int min = (intCurPage - 1) * intPageSize;
	    if (intCurPage != 1 && min > 0) rs.absolute(min);
	    return rs;
	}
	
	public boolean checkRs(ResultSet rs, int rsCount) throws SQLException {
	    if (rs == null) {
	        return false;
	    }
	    if (rs.isAfterLast() || rsCount >= intPageSize) {
	        return false;
	    }
	    boolean tail = rs.next();
	    if (!tail) {
	        return false;
	    }
	    return true;
	}
	
	public void closeCon() {
	    if (db != null)
	        db.closeCon();
	}
	
	public int getIntCurPage() {
	    try {
	        int pindex = ParamUtil.getInt(request, Constants.PAGE_SIZE_INDEX, 1);
	        return pindex;
	    } catch (Exception e) {
	        return 1;
	    }
	}
	
	public int getIntPageSize() {
	    if (userSession != null) return userSession.getIntPageSize();
	    return intPageSize;
	}
	
	//����formbean
	public void setDynamicProperty(Object o) {
	    try {
	        if (request == null) throw new javax.servlet.ServletException("no such request null");
	        setDynamicProperty(o, request);
	    } catch (javax.servlet.ServletException e) {
	        e.printStackTrace();
	    }
	}
	
	//����formbean
	public void setDynamicProperty(Object o, HttpServletRequest req) throws ServletException {
	    RequestUtils.populate(o, req);
	}
	
	//��ӡ������
	public void dispatch(boolean result, String page, String s) {
	    dispatch(result, page, null, true, s);
	}
	
	public void dispatch(boolean result, String s) {
	    dispatch(result, s, null, true, null);
	}
	
	public void dispatch(boolean result, String s, boolean b) {
	    dispatch(result, s, null, b, null);
	}
	
	public void dispatch(javax.servlet.jsp.JspWriter out, boolean result, String success, String error) {
	    if (result) {
	        try {
	            out.println(new StringBuffer("<script>alert('").append(success).append("')</script>").toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        try {
	            out.println(new StringBuffer("<script>alert('").append(error).append("');history.back();</script>").toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	// \u589e\u52a0 =>���
	// \u4fee\u6539 =>�޸�
	// \u4e0a\u62a5 =>�ϱ�
	// \u5220\u9664 =>ɾ��
	
	public void dispatch(boolean result, String s, String s2, boolean isPrompt, String msg) {
	    if (msg == null || msg.equals(""))
	        msg = "\u64cd\u4f5c";
	    try {
	        if (response == null) throw new java.io.IOException("no such response null");
	        if (result) {
	            response.getWriter().println(new StringBuffer("<script>if(").append(isPrompt).append(")alert('").append(msg).append("\u6210\u529f');location.href='").append(s).append("';</script>"));
	        } else {
	            if (s2 == null)
	                response.getWriter().println(new StringBuffer("<script>alert('").append(msg).append("\u5931\u8d25');history.back();</script>"));
	            else
	                response.getWriter().println(new StringBuffer("<script>alert('").append(msg).append("\u5931\u8d25');location.href='").append(s2).append("';</script>"));
	        }
	    } catch (java.io.IOException ioe) {
	        ioe.printStackTrace();
	    }
	}
	
	public void dispatch(String s) {
	    try {
	        if (response == null) throw new java.io.IOException("no such response null");
	        response.getWriter().println(new StringBuffer("<script>location.href='").append(s).append("';</script>"));
	    } catch (java.io.IOException ioe) {
	        ioe.printStackTrace();
	    }
	}
	
	public void addCollectThread(Thread collectThread) {
		Vector<Thread> vctThread = (Vector<Thread>)application.getAttribute("CollectThread");
		if (vctThread == null)
			vctThread = new Vector<Thread>();
		vctThread.add(collectThread);
		application.setAttribute("CollectThread", vctThread);
	}
	
	public static void addCollectStatus(String status) {
		vctCollectStatus.add(0, status);
	}

	public Member getMember()
	{
		return member;
	}

	public void setMember(Member member)
	{
		this.member = member;
	}
}

