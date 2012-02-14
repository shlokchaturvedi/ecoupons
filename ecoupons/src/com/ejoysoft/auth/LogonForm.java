package com.ejoysoft.auth;

import com.ejoysoft.common.*;
import com.ejoysoft.common.exception.NoAuthException;
import com.ejoysoft.ecoupons.system.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class LogonForm {


    private Globa globa;
    private String error;
    UserSession userSession;
    MemberSession memberSession;//会员登录信息
    private String strAllUnits;//			用户所属的所有上级用户组，格式：1111，2222，333
     String[] strArryUnitId=null;

    public String getError() {
        return error;
    }

    public LogonForm(ServletContext application, HttpServletRequest request, HttpServletResponse response) {

        globa = new Globa();
        globa.initialize(application, request, response);
        globa.setDynamicProperty(this);
        error = null;
    }


    private int getErrorLockNum() {
        int intErrorLockNum = 3;
        try {
            intErrorLockNum = Integer.parseInt(globa.getPropValue(Constants.Error_Lock_Num));
        } catch (NumberFormatException ne) {
            intErrorLockNum = 3;
        } finally {
            return intErrorLockNum;
        }
    }
    public int authenticate() {
        int value = 0;
        HttpSession session = globa.session;
        userSession = new UserSession(globa.application);
        userSession.setExculpate(this.getScreensize());
        userSession.setLoginIp(globa.loginIp);
        userSession.setdLatestLoginTime(Format.getDateTime());
        String tUserId = Format.removeNull(this.getUsername()).replaceAll(" ", "");
        String strCaNO = Format.removeNull(this.getCaNO());
        userSession.setStrCaNO(this.getCaNO());
        try {
            if (tUserId.equals(Constants.Administrator) && this.getPassword().equals(MD5.getMD5ofString(Constants.initPass))) {
                userSession.setStrId("007");
                userSession.setStrUserId(tUserId);
                userSession.setStrName("配置管理员");
                userSession.setStrCssType("1");
                userSession.setStrUnitCode("00");
                value = 1;
                //个人事务权限信息
                loadRight();
            } else {
                if (strCaNO.equals("") || !tUserId.equals(""))
                    value = userPwdAuth(tUserId);
                else {
                    value = authMac(strCaNO);
                }
            }
            session.setAttribute(com.ejoysoft.common.Constants.USER_KEY, userSession);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new NoAuthException(error);
        } finally {
            globa.db.closeCon();
            return value;
        }
    }

    //普通人员认证
    public int userPwdAuth(String tUserId) {
        int value = 0;
        int intErrorLockNum = getErrorLockNum();
        try {
            java.sql.ResultSet rs = null;
            String strSql = "SELECT strId,strUserId,strPWD,strName,intError,intState,intType,strEmail,strMsnQQ,strCssType,intUserType,strShopid " +
            		"FROM t_sy_user WHERE strUserId='" + tUserId + "' ";
            rs = globa.db.executeQuery(strSql);
            if (!rs.next()) {
                error = new String("用户不存在，或者你输入的用户名有误！");
                value = -1;
            } else {
                //用户相关信息
                userSession.setStrId(rs.getString("strId"));
                userSession.setStrUserId(rs.getString("strUserId"));
                String pwd = rs.getString("strPWD");
                if (pwd == null || !pwd.equals(this.getPassword())) 
                {
           			error = new String("你输入的密码有误！");
           			return -1;   			
          		}
                userSession.setStrPWD(this.getPassword1());
                userSession.setStrName(rs.getString("strName"));
                int intError = rs.getInt("intError");
                int intState = rs.getInt("intState");
                value = rs.getInt("intType");
                userSession.setIntType(value);
                userSession.setStrEmail(rs.getString("strEmail"));
                userSession.setStrMsnQQ(rs.getString("strMsnQQ"));
                userSession.setStrCssType(rs.getString("strCssType"));
                userSession.setStrShopid(rs.getString("strShopid"));
                if (intState > Constants.U_STATE_ON) {
                    error = new String("该用户已经锁定或是禁用，请与管理员联系!");
//                    value = -1;
                    return  -1;
                }
                //更新登录成功次数和时间
                globa.db.executeUpdate("UPDATE t_sy_user SET intLoginNum=intLoginNum+1,intError=0,dLatestLoginTime='" + Format.getDateTime() + "'  where strUserId = '" + tUserId + "'");
                //用户所属的单位id
                User user = new User(globa);
                userSession.setStrUnitId(user.arryUnitUser(" WHERE strUserId='" + tUserId + "'"));
                //获取第一个单位的独立机构id
                if (userSession.getStrUnitId() != null && userSession.getStrUnitId().length > 0) {
                    String tUnitId = userSession.getStrUnitId()[0];
                    userSession.setStrUnitCode(tUnitId);
                    userSession.setStrUnitName(SysUserUnit.getUnitName(tUnitId));
                    //单位组
                    userSession.setUnitIds(Format.arryToStr2(userSession.getStrUnitId()));
                    userSession.setStrRootId(SysUserUnit.getRootId(tUnitId));
                } else
                    userSession.setStrUnitCode("");
                //个人事务权限信息
                loadRight();
                userSession.setStrAllUnits(strAllUnits);
                userSession.setIntUserType(rs.getInt("intUserType"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
  //会员认证
    public int memberPwdAuth(String strCardNo) {
    	HttpSession session = globa.session;
        int value = 0;
        memberSession = new MemberSession(globa.application);
        memberSession.setExculpate(this.getScreensize());
        memberSession.setLoginIp(globa.loginIp);
        memberSession.setdLatestLoginTime(Format.getDateTime());
        String tUserId = Format.removeNull(this.getUsername()).replaceAll(" ", "");
        String strCaNO = Format.removeNull(this.getCaNO());
//      int intErrorLockNum = getErrorLockNum();
        try {
            java.sql.ResultSet rs = null;
            String strSql = "SELECT distinct * " +
            		"FROM t_bz_member WHERE strCardNo='" + strCardNo + "' or strMobileNo='"+strCardNo+"'";
            System.out.println(strSql);
            rs = globa.db.executeQuery(strSql);
            if (!rs.next()) {
                error = new String("会员不存在，或者你输入的会员帐号和手机号码有误！");
                value = -1;
            } else {
                //用户相关信息
            	memberSession.setStrId(rs.getString("strId"));
            	memberSession.setStrCardNo(rs.getString("strCardNo"));
                String pwd = rs.getString("strPWD");
                System.out.println();
                if (pwd == null || !pwd.equals(this.getPassword())) {
//                	if (pwd == null || !pwd.equals(this.getPassword1())) {
           			error = new String("你输入的密码有误！");
           			return -1;   			
          		}
                memberSession.setStrPWD(pwd);
                memberSession.setStrName(rs.getString("strName"));
//                int intError = rs.getInt("intError");
//                int intState = rs.getInt("intState");
                value = rs.getInt("intType");
                memberSession.setIntType(value);
                //memberSession.setIntType(rs.getInt("intType"));
                memberSession.setStrMobileNo(rs.getString("strMobileNo"));
                memberSession.setFlaBalance(rs.getFloat("flaBalance"));
                memberSession.setIntPoint(rs.getInt("intPoint"));
                //memberSession.sets(rs.getString("strCssType"));
                session.setAttribute(com.ejoysoft.common.Constants.MEMBER_KEY, memberSession);
               
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
    //领导的Mac地址认证
    private int authMac(String CaNO) throws SQLException {
        int value = -1;
        boolean isNo = false;
        ResultSet rs = globa.db.executeQuery("SELECT * from t_sy_user where strCaNO='" + CaNO + "'");
        boolean isRes = rs.next();
        if (!isRes) {
            isNo = true;
            String tUserId = Format.removeNull(this.getPassword2());
            rs = globa.db.executeQuery("SELECT * from t_sy_user WHERE strUserId='" + tUserId + "' ");
            isRes = rs.next();
        }
        if (isRes) { // 正确
            //用户相关信息
            userSession.setStrId(rs.getString("strId"));
            String userId = rs.getString("strUserId");
            userSession.setStrUserId(userId);
            userSession.setStrName(rs.getString("strName"));
            value = rs.getInt("intType");
            userSession.setIntType(value);
            userSession.setStrEmail(rs.getString("strEmail"));
            userSession.setStrMsnQQ(rs.getString("strMsnQQ"));
            userSession.setStrCaNO(rs.getString("strCaNO"));
            userSession.setStrCssType(rs.getString("strCssType"));
            //更新证书序列号
            if (isNo) globa.db.executeUpdate("UPDATE t_sy_user  SET strCaNO='" + CaNO + "'  where strUserId = '" + userId + "'");
            //更新登录成功次数和时间
            globa.db.executeUpdate("UPDATE t_sy_user SET intLoginNum=intLoginNum+1,intError=0,dLatestLoginTime='" + Format.getDateTime() + "'  where strUserId = '" + userId + "'");
            //用户所属的单位id
            User user = new User(globa);
            userSession.setStrUnitId(user.arryUnitUser(" WHERE strUserId='" + userId + "'"));
            //获取第一个单位的独立机构id
            if (userSession.getStrUnitId() != null && userSession.getStrUnitId().length > 0) {
                String tUnitId = userSession.getStrUnitId()[0];
                userSession.setStrUnitCode(tUnitId);
                userSession.setStrUnitName(SysUserUnit.getUnitName(tUnitId));
                //单位组
                userSession.setUnitIds(Format.arryToStr2(userSession.getStrUnitId()));
                userSession.setStrRootId(SysUserUnit.getRootId(tUnitId));
                
            } else
                userSession.setStrUnitCode("");
            //个人事务权限信息
            loadRight();
            userSession.setStrAllUnits(strAllUnits);
            userSession.setIntUserType(rs.getInt("intUserType"));
            userSession.setStrBuildId(rs.getString("strBuildId"));
        } else
            error = new String("你的电子钥匙有误，用户身份验证失败，请重试");
        rs.close();
        return value;
    }   

    public boolean doModiPass() {
        try {
            error = null;
            String sql = "UPDATE t_sy_user SET strPWD=? WHERE  strUserId=? AND strPWD=?";
            globa.db.prepareStatement(sql);
            globa.db.setString(1, this.getPassword1());
            globa.db.setString(2, this.getUsername());
            globa.db.setString(3, this.getPassword());

            if (!this.getPassword1().equals(this.getPassword2())) {
                error = new String("\u4e24\u6b21\u5bc6\u7801\u8f93\u5165\u4e0d\u4e00\u81f4");
            }
            int j = globa.db.executeUpdate();
            if (j <= 0)
                error = new String("\u4fee\u6539\u5bc6\u7801\u5931\u8d25");
            return error == null;
        } catch (SQLException e) {
            error = new String("\u6570\u636e\u5e93\u5904\u7406\u5931\u8d25");
            return false;
        } finally {
            globa.db.closeCon();
        }
    }


    public boolean doSetPwd(String strid) {
        try {
            String sql = "UPDATE t_sy_user SET strPWD=?,intState=? ,intError=0 WHERE  strUserId=? ";
            globa.db.prepareStatement(sql);
            globa.db.setString(1, MD5.getMD5ofString(Constants.initPass));
            globa.db.setInt(2, Constants.U_STATE_ON);
            globa.db.setString(3, strid);
            globa.db.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            globa.db.closeCon();
        }
    }

    public boolean doExit() {
        globa.session.removeAttribute(Constants.USER_KEY);
        globa.session.removeAttribute(Constants.MEMBER_KEY);
        globa.session.invalidate();
        return true;
    }

    /**
     * see UserActor.update()
     */
    public synchronized static void restartAffairs() {
        APP_AFFAIRS = new java.util.Hashtable();
    }

    /**
     * see UserActor.allocate()
     * @param strid
     */
    public static void restartAffairs(String strid) {
        APP_AFFAIRS.remove(strid);
    }

    private static java.util.Hashtable APP_AFFAIRS = new java.util.Hashtable();

    private void loadRight() {
        java.sql.ResultSet rs;
        HashMap rights = userSession.getRights();
        try {
            if (userSession.getStrUserId().equals(Constants.Administrator)) {
                userSession.setStrName("配置管理员");
                rights.put("90005", "true");
                rights.put("90010", "true");
                rights.put("90015", "true");
                rights.put("91005", "true");
                rights.put("91010", "true");
                rights.put("91015", "true");
                rights.put("91020", "true");
                rights.put("91025", "true");
                rights.put("91030", "true");
            } else {
                String strSql = "SELECT strRoleId FROM t_sy_userRight WHERE strUserId='" + userSession.getStrId() + "' AND intType=0";
                rs = globa.db.executeQuery(strSql);
                while (rs.next()) {
                    rights.put(rs.getString("strRoleId"), "true");
                }
                //query right of the user's group and parent group
                String[] arryUnit = userSession.getStrUnitId();
                strAllUnits = "u/" + userSession.getStrUserId();
                if (arryUnit != null && arryUnit.length > 0)
                    for (int i = 0; i < arryUnit.length; i++) {
                        Unit ug = SysUserUnit.getUserGroup(arryUnit[i]);
                        strAllUnits = strAllUnits + "," + SysUserUnit.getAllRootUnitIds(arryUnit[i]);
                    }
            }
            System.out.println("Begin Load [" + userSession.getStrUserId() + "] 's  All  Affairs");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rights != null)
                APP_AFFAIRS.put(userSession.getStrUserId(), rights);
        }
    }

    private String username;
    private String password;
    private String caNO;
    private String password1;
    private String password2;
    private int screensize;
    private String ikeyDigest;
    public String getCaNO() {
        return Format.removeNull(caNO);
    }

    public void setCaNO(String caNO) {
        this.caNO = caNO;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password1 = password;
        this.password = MD5.getMD5ofString(password);
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = MD5.getMD5ofString(password1);
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = MD5.getMD5ofString(password2);
    }

    public int getScreensize() {
        return screensize;
    }

    public void setScreensize(int screensize) {
        this.screensize = screensize;
    }

    public String getIkeyDigest() {
        return ikeyDigest;
    }

    public void setIkeyDigest(String ikeyDigest) {
        this.ikeyDigest = ikeyDigest;
    }

    public void reset() {
        username = null;
        password = null;
        password1 = null;
        password2 = null;
        ikeyDigest = null;
        screensize = 800;
    }
}