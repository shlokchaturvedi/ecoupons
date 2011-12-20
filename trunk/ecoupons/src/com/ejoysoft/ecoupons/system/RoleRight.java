package com.ejoysoft.ecoupons.system;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2005-9-7
 * Time: 22:51:07
 * To change this template use Options | File Templates.
 */
public class RoleRight {
    private Globa globa;
    private DbConnect db;
    String strTableName = "t_sy_userright";

    //���췽�� 
    public RoleRight() {
    }

    public RoleRight(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //���췽��
    public RoleRight(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    /**
     * ���ؾ߱�������Ȩ�޵��û�strid�͵�λ/���strId
     * @return
     * @throws java.sql.SQLException
     */
    public HashMap getAllUserUnit(String tRoleId) throws SQLException {
        HashMap allRights = new HashMap();
        //query db
        String strSql = "SELECT strUserId FROM  " + strTableName + "  WHERE strRoleId='" + tRoleId + "'";
        ResultSet rs = db.executeQuery(strSql);
        //add right into hashmap
        while (rs.next()) {
            allRights.put(rs.getString("strUserId"), "");
        }
        rs.close();
        return allRights;
    }

    /**
     * �����û���߱�������Ȩ��?
     * @return
     * @throws SQLException
     */
    public HashMap getAllRights(int tType, String tUnitId) throws SQLException {
        HashMap allRights = new HashMap();
        //query db
        try {
            String sql = "SELECT strRoleId FROM " + strTableName + " WHERE intType=" + tType + " AND strUserId='" + tUnitId + "'";
            ResultSet rs = db.executeQuery(sql);
            //add right into hashmap
            while (rs.next()) {
                allRights.put(rs.getString("strRoleId"), "");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allRights;
    }

    //��ѯ�������ļ�¼����
    public int getCount(String where) {
        int count = 0;
        try {
            String sql = "SELECT count(strId) FROM " + strTableName + "  ".concat(where);
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

    //���?
    public boolean add(String[] tArry, String tStrObjId, int tIntType) {
        String strSql = "";
        if (tArry == null || tArry.length == 0) {
            //��ǰ�û��б�Ϊ��     ֱ�ӷ���
            return true;
        } else {
            // ���û��б���ѭ����ȡ��Ȼ����뵽��ݿ���
            strSql = "insert into " + strTableName + "(strId, strUserId, strRoleId, intType, strUnitId, strUnitCode, strCreator, dCreatDate) VALUES (?,?,?,?,?,?,?,?)";
            try {
                for (int i = 0; i < tArry.length; i++) {
                    if (getCount(" WHERE strUserId='" + tStrObjId + "'  AND strRoleId='" + tArry[i] + "'") <= 0) {
                        db.prepareStatement(strSql);
                        db.setString(1, UID.getID());
                        db.setString(2, tStrObjId);
                        db.setString(3, tArry[i]);
                        db.setInt(4, tIntType);
                        db.setString(5, "");
                        db.setString(6, globa.unitCode);
                        db.setString(7, globa.userSession.getStrUserId());
                        db.setString(8, com.ejoysoft.common.Format.getDateTime());
                        db.executeUpdate();
                    }
                }
                Globa.logger0("����Ȩ����Ϣ", globa.loginName, globa.loginIp, strSql, "Ȩ�޷���", globa.unitCode);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    //ɾ��
    public boolean delete(String where) {
        try {
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    private String strId;//ID
    private String strUserId;//�û�(��λ)ID
    private String strRoleId;//Y		��ɫID
    private int intType;//	��������0-�û���1-��λ�����ţ�
    private String strUnitId;//			��λ��id
    private String strUnitCode;//			����λ����
    private String strCreator;//			������
    private String dCreatDate;//		����ʱ��

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

    public String getStrRoleId() {
        return strRoleId;
    }

    public void setStrRoleId(String strRoleId) {
        this.strRoleId = strRoleId;
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
}
