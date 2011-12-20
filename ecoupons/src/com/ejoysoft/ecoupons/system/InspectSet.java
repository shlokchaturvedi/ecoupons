
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
public class InspectSet {
    private Globa globa;
    private DbConnect db;
    String strTableName = "t_sy_inspect";

    //���췽�� 
    public InspectSet() {
    }

    public InspectSet(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //���췽��
    public InspectSet(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    

    /**
     * ���ؼ�����߱����������?
     * @return
     * @throws SQLException
     */
    public HashMap getAllRights(String tUnitId) throws SQLException {
        HashMap allRights = new HashMap();
        //query db
        try {
            String sql = "SELECT strOrgId FROM " + strTableName + " WHERE  strInspectId='" + tUnitId + "'";
            ResultSet rs = db.executeQuery(sql);
            //add right into hashmap
            while (rs.next()) {
                allRights.put(rs.getString("strOrgId"), "");
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
    //�жϵ�ǰ������������ҵid��Ӧ��strorgid
    public int getOrgCount(String where) {
        int count = 0;
        try {
            String sql = "SELECT count(t_sy_inspect.strorgid) FROM  t_sy_inspect,T_ebaseInfo where t_sy_inspect.strorgid=t_ebaseinfo.strorgid and t_ebaseinfo.strid='"+where+"'";
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
    public boolean add(String[] tArry, String tStrObjId) {
        String strSql = "";
        if (tArry == null || tArry.length == 0) {
            //��ǰ�û��б�Ϊ��     ֱ�ӷ���
            return true;
        } else {
            // ���û��б���ѭ����ȡ��Ȼ����뵽��ݿ���
            strSql = "insert into " + strTableName + "(strId, strInspectId, strOrgId, strCreator, dCreatDate) VALUES (?,?,?,?,?)";
            try {
                for (int i = 0; i < tArry.length; i++) {
                    if (getCount(" WHERE strInspectId='" + tStrObjId + "'  AND strOrgId='" + tArry[i] + "'") <= 0) {
                        db.prepareStatement(strSql);
                        db.setString(1, UID.getID());
                        db.setString(2, tStrObjId);
                        db.setString(3, tArry[i]);
                     
                        db.setString(4, globa.userSession.getStrUserId());
                        db.setString(5, com.ejoysoft.common.Format.getDateTime());
                        db.executeUpdate();
                    }
                }
                Globa.logger0("�����ҵ�������?", globa.loginName, globa.loginIp, strSql, "�����ҵά��?", globa.unitCode);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    public boolean update(String tStrtId) throws SQLException {
        try {
           
             
               
            //��������
            String strSql = "UPDATE " + strTableName + " SET strorgid=?  WHERE strorgid='" + tStrtId + "'";
            db.prepareStatement(strSql);
            db.setString(1, strOrgId);
          // db.setString(2, strOrgId);
           
          
           db.executeUpdate() ;
            	
            	 Globa.logger0("�޸ļ����ҵ����?" + strId, globa.loginName, globa.loginIp, strSql, "�����ҵ����?", globa.userSession.getStrDepart());
                    return true;
          
           // return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    private String strInspectId;//������id strInspectId
    private String strOrgId;//Y		��ɫID
  
    private String strCreator;//			������
    private String dCreatDate;//		����ʱ��

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrInspectId() {
        return strInspectId;
    }

    public void setStrInspectId(String strInspectId) {
        this.strInspectId = strInspectId;
    }

    public String getStrOrgId() {
        return strOrgId;
    }

    public void setStrOrgId(String strOrgId) {
        this.strOrgId = strOrgId;
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
