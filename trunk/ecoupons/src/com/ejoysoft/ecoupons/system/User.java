package com.ejoysoft.ecoupons.system;

import com.ejoysoft.auth.MD5;
import com.ejoysoft.common.*;
import com.ejoysoft.common.exception.UserUnitIdException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.HashMap;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-1
 * Time: 9:12:43
 * To change this template use Options | File Templates.
 */
public class User {
    private Globa globa;
    private DbConnect db;

    //���췽��
    public User() {
    }

    public User(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //���췽��
    public User(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_sy_user";
    String strTableName2 = "t_sy_unituser";

    //�ж��û���Ϣ�Ƿ���?
    public void bCheckAccount(String tStrAccount) throws UserUnitIdException, SQLException {
        String strSql = "select strUserId  from " + strTableName + "   where strUserId='" + tStrAccount + "'";
        try {
            ResultSet rs = db.executeQuery(strSql);
            if (rs.next()) {
                globa.closeCon();
                throw new UserUnitIdException("�Ѿ�����'" + tStrAccount + "' �û�", "�����������û���");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //����û�?
    public boolean add(String strUserId) {
        String strSql = "";
        strId = UID.getID();
        try {
            //���û���Ϣд����ݿ�?
            strSql = "INSERT INTO " + strTableName + "  (strId, strUserId, strPWD, strName, intError, intState, dBirthday, strSex, strIntro,intType, strUnitId, strUnitCode," +
                    " strNation, strMobile, strEmail, strMsnQQ,strOPhone, strHPhone, strDuty, strStation, intLoginNum, dLatestLoginTime,fOnlineTime, strCaNO, " +
                    "strDepart, strCssType,  strLinkAdd, strCreator, dCreatDate,intUserType,strBuildId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strUserId);
            db.setString(3, MD5.getMD5ofString(Constants.resetPass));   //strPWD
            db.setString(4, strName);
            db.setInt(5, 0);
            db.setInt(6, 0);
            db.setString(7, dBirthday);
            db.setString(8, strSex);
            db.setString(9, strIntro);
            db.setInt(10, intType);
            db.setString(11, strUnitId);
            db.setString(12, "");                 //strUnitCode                  new Unit(globa, false).retFieldValue("strUnitCode", strUnitId)
            db.setString(13, strNation);
            db.setString(14, strMobile);
            db.setString(15, strEmail);
            db.setString(16, strMsnQQ);
            db.setString(17, strOPhone);
            db.setString(18, strHPhone);
            db.setString(19, strDuty);
            db.setString(20, strStation);
            db.setInt(21, intLoginNum);
            db.setString(22, com.ejoysoft.common.Format.getDateTime());
            db.setDouble(23, 0);
            db.setString(24, strCaNO);
            db.setString(25, strDepart);
            db.setString(26, "1");
            db.setString(27, strLinkAdd);
            db.setString(28, globa.loginName);
            db.setString(29, com.ejoysoft.common.Format.getDateTime());
            db.setInt(30, intUserType);
            db.setString(31, strBuildId);
            if (db.executeUpdate() > 0) {
                Globa.logger0("����û����?", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("����û���Ϣʱ��??��");
            e.printStackTrace();
            return false;
        }
    }

    //����û�?
    public boolean addUnitUser(String strUserId, String[] arryUnitId) {
        String strSql = "";
        try {
            //����Ϣд����ݿ�?
            strSql = "INSERT INTO " + strTableName2 + "  (strUserId, strUnitId, intSort, strCreator, dCreatDate) VALUES (?,?,?,?,?)";
            if (arryUnitId != null && arryUnitId.length > 0)
                for (int i = 0; i < arryUnitId.length; i++) {
                    db.prepareStatement(strSql);
                    db.setString(1, strUserId);
                    db.setString(2, arryUnitId[i]);
                    db.setInt(3, creatSort(arryUnitId[i]));            // intSort
                    db.setString(4, globa.loginName);
                    db.setString(5, com.ejoysoft.common.Format.getDateTime());
                    db.executeUpdate();
                }
            return true;
        } catch (Exception e) {
            System.out.println("��ӵ�λ�û�ӳ��ʱ��??��");
            e.printStackTrace();
            return false;
        }
    }

    //ɾ��
    public boolean delUnitUser(String where) {
        try {
            String sql = "DELETE FROM " + strTableName2 + "  ".concat(where);
            db.executeUpdate(sql);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //����ƶ��û�����ĵ�λ
    public String[] arryUnitUser(String tWhere) {
        String[] result = null;
        try {
            String strSql = "SELECT count(*) FROM  " + strTableName2 + " ".concat(tWhere);
            ResultSet rs = db.executeQuery(strSql);
            int iLen = 0;
            if (rs.next())
                iLen = rs.getInt(1);
            result = new String[iLen];
            strSql = "SELECT strUnitId FROM  " + strTableName2 + " ".concat(tWhere);
            rs = db.executeQuery(strSql);
            int i = 0;
            while (rs.next()) {
                result[i] = rs.getString(1);
                i++;
            }
            rs.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }

    //����ƶ��û�����ĵ�λ
    public HashMap unitUser(String tField, String tWhere) {
        HashMap result = new HashMap();
        String strSql = "SELECT " + tField + " FROM  " + strTableName2 + " ".concat(tWhere);
        try {
            ResultSet rs = db.executeQuery(strSql);
            while (rs.next()) {
                result.put(rs.getString(1), "true");
            }
            rs.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }
    //����ƶ��û�����ĵ�λ
    public HashMap unitUser(String tWhere) {
        HashMap result = new HashMap();
//        String strSql = "SELECT strUserId,strId FROM  " + strTableName2 + " ".concat(tWhere);
        String strSql = "SELECT b.strUserId,b.strId FROM t_sy_unitUser a LEFT JOIN t_sy_user b ON a.strUserId=b.strUserId   ".concat(tWhere);
        try {
            ResultSet rs = db.executeQuery(strSql);
            while (rs.next()) {
                result.put(rs.getString(1),rs.getString(2));
            }
            rs.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return result;
    }
    //�б��¼��?
    public String retVal(String strField, String where) {
        String val = "''";
        try {
            String sql = "SELECT " + strField + "  FROM  " + strTableName2 + "  ".concat(where);
            ResultSet rs = db.executeQuery(sql);
            while (rs != null && rs.next()) {
                val = val + ",'" + rs.getString(1) + "'";
            }
            rs.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return val;
    }

    //���ͬһ��λ�µ��¼���λ���飩��? �����?
    public int creatSort(String tStrUnitId) {
        ResultSet rs = null;
        int tIntSort = 0;
        try {
            rs = db.executeQuery("SELECT  Max(intSort)  FROM " + strTableName2 + " WHERE  strUnitId='" + tStrUnitId + "' ");
            if (rs != null && rs.next()) {
                tIntSort = rs.getInt(1);
            }
            rs.close();
            rs = null;
            return tIntSort + 1;
        } catch (Exception ee) {
            ee.printStackTrace();
            return 0;
        }
    }

    //���ͬһ��λ�ϱ���λ���û���?
    public String creatUserId(String tStrUnitId) {
        ResultSet rs = null;
        String tStrUserId = "";
        try {
            Unit unit0 = new Unit(globa, false).show(" WHERE strId='" + tStrUnitId + "'");
//            if(unit0.getIntUnitType()!=2)
//                    return "";
            System.out.println("SELECT Max(strUserId)  FROM " + strTableName + " WHERE  strUnitId='" + tStrUnitId + "'");
            rs = db.executeQuery("SELECT Max(strUserId)  FROM " + strTableName + " WHERE  strUnitId='" + tStrUnitId + "'");
            if (rs != null && rs.next()) {
                tStrUserId = rs.getString(1);
            }
            rs.close();
            rs = null;
            if (tStrUserId == null || tStrUserId.equals("")) {
                tStrUserId = unit0.getStrUnitCode() + "00";
            } else {
                tStrUserId = String.valueOf(Long.parseLong(tStrUserId) + 1);
            }
            return tStrUserId;
        } catch (Exception ee) {
            ee.printStackTrace();
            return "";
        }
    }

    //ɾ��
    public boolean delete(String where) {
        try {
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            //ɾ���û�ӳ����?
            delUnitUser(where);
            Globa.logger0("ɾ���û���Ϣ", globa.loginName, globa.loginIp, sql, "�û�����", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //�޸�
    public boolean update(String tStrUserId) {
        try {
            String strSql = "UPDATE  " + strTableName + "  SET  strName = ?, intError = ?, intState = ?, dBirthday = ?, strSex = ?, strIntro = ?, intType = ?, " +
                    "strUnitId = ?, strUnitCode = ?,strNation = ?, strMobile = ?, strEmail = ?, strMsnQQ = ?, strOPhone = ?, strHPhone = ?, strDuty = ?, strStation = ?, " +
                    " strCaNO = ?, strDepart = ?,  strLinkAdd = ?, strCreator = ?, dCreatDate = ? ,intUserType=?,strBuildId=?  WHERE strUserId=? ";
            db.prepareStatement(strSql);
            db.setString(1, strName);
            db.setInt(2, 0);
            db.setInt(3, intState);
            db.setString(4, dBirthday);
            db.setString(5, strSex);
            db.setString(6, strIntro);
            db.setInt(7, intType);
            db.setString(8, strUnitId);
            db.setString(9, "");       //"strUnitCode
            db.setString(10, strNation);
            db.setString(11, strMobile);
            db.setString(12, strEmail);
            db.setString(13, strMsnQQ);
            db.setString(14, strOPhone);
            db.setString(15, strHPhone);
            db.setString(16, strDuty);
            db.setString(17, strStation);
            db.setString(18, strCaNO);
            db.setString(19, strDepart);
            db.setString(20, strLinkAdd);
            db.setString(21, globa.loginName);
            db.setString(22, com.ejoysoft.common.Format.getDateTime());
            db.setInt(23, intUserType);
            db.setString(24, strBuildId);
            db.setString(25, tStrUserId);
            db.executeUpdate();
            Globa.logger0("�޸��û���Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("�޸��û���Ϣʱ��?" + e);
            return false;
        }
    }

    //��ϸ��ʾ������?
    public User show(String where) {
        try {
            String strSql = "select * from  " + strTableName + "  ".concat(where);
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next())
                return load(rs, true);
            else
                return null;
        } catch (Exception ee) {
            return null;
        }
    }

    //��¼��ת��Ϊ����
    public User load(ResultSet rs, boolean isView) {
        User theBean = new User();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrUserId(rs.getString("strUserId"));
            theBean.setStrPWD(rs.getString("strPWD"));
            theBean.setStrName(rs.getString("strName"));
            theBean.setIntError(rs.getInt("intError"));
            theBean.setIntState(rs.getInt("intState"));
            theBean.setdBirthday(rs.getString("dBirthday"));
            theBean.setStrSex(rs.getString("strSex"));
            theBean.setStrIntro(rs.getString("strIntro"));
            theBean.setIntType(rs.getInt("intType"));
            theBean.setStrUnitId(rs.getString("strUnitId"));
            theBean.setStrUnitCode(rs.getString("strUnitCode"));
            theBean.setStrNation(rs.getString("strNation"));
            theBean.setStrMobile(rs.getString("strMobile"));
            theBean.setStrEmail(rs.getString("strEmail"));
            theBean.setStrMsnQQ(rs.getString("strMsnQQ"));
            theBean.setStrOPhone(rs.getString("strOPhone"));
            theBean.setStrHPhone(rs.getString("strHPhone"));
            theBean.setStrDuty(rs.getString("strDuty"));
            theBean.setStrStation(rs.getString("strStation"));
            theBean.setIntLoginNum(rs.getInt("intLoginNum"));
            theBean.setdLatestLoginTime(rs.getString("dLatestLoginTime"));
            theBean.setfOnlineTime(rs.getFloat("fOnlineTime"));
            theBean.setStrCaNO(rs.getString("strCaNO"));
            theBean.setStrDepart(rs.getString("strDepart"));
            theBean.setStrCssType(rs.getString("strCssType"));
            theBean.setStrLinkAdd(rs.getString("strLinkAdd"));
            theBean.setStrCreator(rs.getString("strCreator"));
            theBean.setdCreatDate(rs.getString("dCreatDate"));
            theBean.setIntUserType(rs.getInt("intUserType"));
            theBean.setStrBuildId(rs.getString("strBuildId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //��ѯ�������ļ�¼����
    public int getCount(String where) {
        int count = 0;
        try {
            String sql = "SELECT count(strId) FROM " + strTableName + "  ";
            if (where.length() > 0) {
                where = where.toLowerCase();
                if (where.indexOf("order") > 0)
                    where = where.substring(0, where.lastIndexOf("order"));
                sql = String.valueOf(sql) + String.valueOf(where);
            }
            ResultSet rs = db.executeQuery(sql);
            if (rs.next())
                count = rs.getInt(1);
            rs.close();
            return count;
        } catch (Exception ee) {
            ee.printStackTrace();
            return count;
        }
    }

    //�б��¼��?
    public Vector jionlist(String where, int startRow, int rowCount) {
        Vector beans = new Vector();
        try {
            String sql = "SELECT a.strId ,a.strUserId ,a.strName,a.strCaNO,b.strUnitId  FROM  " + strTableName + " a LEFT JOIN " + strTableName2 + " b ON a.strUserId=b.strUserId ";
            if (where.length() > 0)
                sql = String.valueOf(sql) + String.valueOf(where);
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (startRow != 0 && rowCount != 0)
                s.setMaxRows((startRow + rowCount) - 1);
            ResultSet rs = s.executeQuery(sql);
            if (rs != null && rs.next()) {
                if (startRow != 0 && rowCount != 0)
                    rs.absolute(startRow);
                do {
                    User theBean = new User();
                    theBean = load2(rs, false);
                    beans.addElement(theBean);
                } while (rs.next());
            }
            rs.close();
            s.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return beans;
    }

    //��¼��ת��Ϊ����
    public User load2(ResultSet rs, boolean isView) {
        User theBean = new User();
        try {
            theBean.setStrId(rs.getString(1));
            theBean.setStrUserId(rs.getString(2));
            theBean.setStrName(rs.getString(3));
            theBean.setStrCaNO(rs.getString(4));
            theBean.setStrUnitId(rs.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //�б��¼��?
    public Vector list(String where, int startRow, int rowCount) {
        Vector beans = new Vector();
        try {
            String sql = "SELECT *  FROM  " + strTableName + " ";
            if (where.length() > 0)
                sql = String.valueOf(sql) + String.valueOf(where);
            Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (startRow != 0 && rowCount != 0)
                s.setMaxRows((startRow + rowCount) - 1);
//            System.out.println(""+sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs != null && rs.next()) {
                if (startRow != 0 && rowCount != 0)
                    rs.absolute(startRow);
                do {
                    User theBean = new User();
                    theBean = load(rs, false);
                    beans.addElement(theBean);
                } while (rs.next());
            }
            rs.close();
            s.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return beans;
    }

    //��������û�������?
    public HashMap getUnitAllUser(String tWhere) throws SQLException {
        HashMap result = new HashMap();
        //��ѯ
        Vector vct = this.jionlist(" ORDER BY a.strUnitId  ASC,a.strUserId ASC", 0, 0);
        User user0 = null;
        for (int i = 0; vct != null && i < vct.size(); i++) {
            //set value
            user0 = (User) vct.get(i);
            //add into the hashmap
            if (result.containsKey(user0.getStrUnitId())) {
                ((Vector) result.get(user0.getStrUnitId())).add(user0);
            } else {
                Vector v = new Vector();
                v.add(user0);
                result.put(user0.getStrUnitId(), v);
            }
        }
        return result;
    }

    //�趨�û�״̬
    public boolean doSetState(int tState, String tWhere) {
        try {
            String sql = "UPDATE " + strTableName + " SET intState=" + tState + " ,intError=0   ".concat(tWhere);
            db.executeUpdate(sql);
            Globa.logger0("�趨�û�״̬", globa.loginName, globa.loginIp, sql, "�û�����", globa.unitCode);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //�����û�����
    public boolean resetPwd(String tWhere) {
        try {
            String sql = "UPDATE " + strTableName + " SET strPWD='" + MD5.getMD5ofString(Constants.resetPass) + "',intState=" + Constants.U_STATE_ON + " ,intError=0   ".concat(tWhere);
            db.executeUpdate(sql);
            Globa.logger0("�����û�����", globa.loginName, globa.loginIp, sql, "�û�����", globa.unitCode);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //�û� ��֤
    public boolean authUser(String oldPwd) {
        String pwd = (new MD5().getMD5ofStr(oldPwd));
        String strSql = "SELECT  *  FROM  " + strTableName + "  WHERE strUserId='" + globa.loginName + "' and strPWD='" + pwd + "'";
        try {
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //����û�ID��ʾ�û���
    public String ReturnName(String struserid) {
        String sql = "SELECT strName FROM " + strTableName + " WHERE strUserId=?";
        try {
            db.prepareStatement(sql);
            db.setString(1, struserid);
            ResultSet rs = db.executeQuery();
            if (rs.next())
                return rs.getString("strName");
            rs.close();
        } catch (Exception e) {
            System.out.println("��ʾ�û������?:" + e);
            return "";
        }
        return "";
    }

    //�޸�����
    public boolean doSetPwd(String tStrUserId, ServletContext application, HttpSession session) {
        try {
            String sql = "UPDATE " + strTableName + " SET strPWD=?,intState=? ,intError=0 WHERE  strUserId=? ";
            db.prepareStatement(sql);
            db.setString(1, MD5.getMD5ofString(strPWD));
            db.setInt(2, Constants.U_STATE_ON);
            db.setString(3, tStrUserId);
            db.executeUpdate();
            //�޸��û���������
            Globa.logger0("�޸��û�����", globa.loginName, globa.loginIp, sql, "�û�����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //�����Լ��޸�
    public boolean selfUpdate(String tStrUserId) {
        try {

            String strSql = "UPDATE  " + strTableName + "  SET  strName = ?, intError = ?,dBirthday = ?, strSex = ?, strIntro = ?," +
                    "strNation = ?, strMobile = ?, strEmail = ?, strMsnQQ = ?, strOPhone = ?, strHPhone = ?, strDuty = ?, strStation = ?, " +
                    " strLinkAdd = ? WHERE strUserId=? ";
            db.prepareStatement(strSql);
            db.setString(1, strName);
            db.setInt(2, 0);
            db.setString(3, dBirthday);
            db.setString(4, strSex);
            db.setString(5, strIntro);
            db.setString(6, strNation);
            db.setString(7, strMobile);
            db.setString(8, strEmail);
            db.setString(9, strMsnQQ);
            db.setString(10, strOPhone);
            db.setString(11, strHPhone);
            db.setString(12, strDuty);
            db.setString(13, strStation);
            db.setString(14, strLinkAdd);
            db.setString(15, tStrUserId);
            db.executeUpdate();
            Globa.logger0("�޸��û�������Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("�޸��û���Ϣʱ��?" + e);
            return false;
        }
    }

    //�޸�CaNO
    public boolean setCaNO(String tCaNO) {
        try {
            String strSql = "update " + strTableName + "  set strCaNO='" + tCaNO + "' where strUserId='" + globa.loginName + "'";
            db.executeUpdate(strSql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //�޸�strCssType
    public boolean setCss(String tCss) {
        try {
            String strSql = "update " + strTableName + "  set strCssType='" + tCss + "' where strUserId='" + globa.loginName + "'";
            db.executeUpdate(strSql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //�޸��쵼�����û�����
    public boolean setPassword(String pwd) {
        try {
            String strSql = "update " + strTableName + "  set strDepart='" + Format.enPass(pwd) + "' where strUserId='" + globa.loginName + "'";
            db.executeUpdate(strSql);
            UserSession userSession = globa.userSession;
            userSession.setStrPWD(pwd);
            globa.session.setAttribute(com.ejoysoft.common.Constants.USER_KEY, userSession);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //��ʾ�ƶ��ֶ���
    public String retFieldValue(String strFieldName, String twhere) {
        String strFieldValue = "";
        String sql = "select  " + strFieldName + "  from " + strTableName + "  ".concat(twhere);
        try {
            db.prepareStatement(sql);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                strFieldValue = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("��ȡ" + strFieldName + "ֵʱ����" + e);
        }
        return strFieldValue;
    }

    /**
     * ����������е��û�?
     */
    public User[] getUsers(String twhere) {
        String strSql;
        try {
            strSql = "select strUserId,strMobile,strName from " + strTableName + "  ".concat(twhere);
            ResultSet rs = db.executeRollQuery(strSql);
            if (rs.last()) {
                int total = rs.getRow();
                rs.beforeFirst();
                User[] users = new User[total];
                int i = 0;
                while (rs.next()) {
                    users[i] = new User(rs.getString("strUserId"), rs.getString("strMobile"), rs.getString("strName"));
                    i++;
                }
                rs.close();
                return users;
            } else {
                rs.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * ����
     */
    public User(String userId,String mobile, String name) {
        this.strUserId = userId;
        this.strMobile = mobile;
        this.strName = name;
    }

    private String strId;//	id��
    private String strUserId;//		�û���
    private String strPWD;//			����
    private String strName;//		����
    private int intError;//	0	��½�������?
    private int intState;//		0	״̬
    private String dBirthday;//		��������
    private String strSex;//		�Ա�
    private String strIntro;//		���˼��?
    private int intType;//		�û����ͣ���ͨ��Ա-0���쵼-1��
    private String strUnitId;//		����λid
    private String strUnitCode;//			����λ����
    private String strNation;//		����
    private String strMobile;//			�ֻ�
    private String strEmail;//			E-mail
    private String strMsnQQ;//		ICQ��MSN
    private String strOPhone;//		�칫�绰
    private String strHPhone;//				סլ�绰
    private String strDuty;//				ְ��
    private String strStation;//		��λ
    private int intLoginNum;//		��½����
    private String dLatestLoginTime;//			���һ�ε�½ʱ��?
    private float fOnlineTime;//	������ʱ��
    private String strCaNO;//	�û������mac��ַ
    private String strDepart;//	usbKey��Կ
    private String strCssType;//		�û��ĸ��Ի�ҳ������ʽ
    private int intSort;//		0	�����?
    private String strLinkAdd;//			jϵ��ַ
    private String strCreator;//			������
    private String dCreatDate;//		����ʱ��
    private String strOldUnitId;//	//�޸�ǰ�ĵ�λID
    private int intOldSort;//�޸�ǰ�������?
    private int intUserType;//�û������ͣ�0��������Ĺ������?  1��������ҵ��
    private String strBuildId;//������ҵ����������Id

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

    public int getIntError() {
        return intError;
    }

    public void setIntError(int intError) {
        this.intError = intError;
    }

    public int getIntState() {
        return intState;
    }

    public void setIntState(int intState) {
        this.intState = intState;
    }


    public String getdBirthday() {
        return dBirthday;
    }

    public void setdBirthday(String dBirthday) {
        this.dBirthday = dBirthday;
    }

    public String getStrSex() {
        return strSex;
    }

    public void setStrSex(String strSex) {
        this.strSex = strSex;
    }

    public String getStrIntro() {
        return strIntro;
    }

    public void setStrIntro(String strIntro) {
        this.strIntro = strIntro;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public String getStrUnitId() {
        return strUnitId;
    }

    public void setStrUnitId(String strUnitId) {
        this.strUnitId = strUnitId;
    }

    public String getStrUnitCode() {
        return strUnitCode;
    }

    public void setStrUnitCode(String strUnitCode) {
        this.strUnitCode = strUnitCode;
    }

    public String getStrNation() {
        return strNation;
    }

    public void setStrNation(String strNation) {
        this.strNation = strNation;
    }

    public String getStrMobile() {
        return strMobile;
    }

    public void setStrMobile(String strMobile) {
        this.strMobile = strMobile;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrMsnQQ() {
        return strMsnQQ;
    }

    public void setStrMsnQQ(String strMsnQQ) {
        this.strMsnQQ = strMsnQQ;
    }

    public String getStrOPhone() {
        return strOPhone;
    }

    public void setStrOPhone(String strOPhone) {
        this.strOPhone = strOPhone;
    }

    public String getStrHPhone() {
        return strHPhone;
    }

    public void setStrHPhone(String strHPhone) {
        this.strHPhone = strHPhone;
    }

    public String getStrDuty() {
        return strDuty;
    }

    public void setStrDuty(String strDuty) {
        this.strDuty = strDuty;
    }

    public String getStrStation() {
        return strStation;
    }

    public void setStrStation(String strStation) {
        this.strStation = strStation;
    }

    public int getIntLoginNum() {
        return intLoginNum;
    }

    public void setIntLoginNum(int intLoginNum) {
        this.intLoginNum = intLoginNum;
    }

    public String getdLatestLoginTime() {
        return dLatestLoginTime;
    }

    public void setdLatestLoginTime(String dLatestLoginTime) {
        this.dLatestLoginTime = dLatestLoginTime;
    }

    public float getfOnlineTime() {
        return fOnlineTime;
    }

    public void setfOnlineTime(float fOnlineTime) {
        this.fOnlineTime = fOnlineTime;
    }

    public String getStrCaNO() {
        return strCaNO;
    }

    public void setStrCaNO(String strCaNO) {
        this.strCaNO = strCaNO;
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

    public int getIntSort() {
        return intSort;
    }

    public void setIntSort(int intSort) {
        this.intSort = intSort;
    }

    public String getStrLinkAdd() {
        return strLinkAdd;
    }

    public void setStrLinkAdd(String strLinkAdd) {
        this.strLinkAdd = strLinkAdd;
    }

    public String getStrCreator() {
        return strCreator;
    }

    public void setStrCreator(String strCreator) {
        this.strCreator = strCreator;
    }

    public String getdCreatDate() {
        return dCreatDate;
    }

    public void setdCreatDate(String dCreatDate) {
        this.dCreatDate = dCreatDate;
    }

    public String getStrOldUnitId() {
        return strOldUnitId;
    }

    public void setStrOldUnitId(String strOldUnitId) {
        this.strOldUnitId = strOldUnitId;
    }

    public int getIntOldSort() {
        return intOldSort;
    }

    public void setIntOldSort(int intOldSort) {
        this.intOldSort = intOldSort;
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
    
}
