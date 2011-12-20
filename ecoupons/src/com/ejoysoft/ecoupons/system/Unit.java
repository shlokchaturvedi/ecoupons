package com.ejoysoft.ecoupons.system;

import com.ejoysoft.common.*;
import com.ejoysoft.common.exception.UserUnitIdException;


import java.util.Date;
import java.util.Vector;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-6
 * Time: 16:59:21
 * To change this template use Options | File Templates.
 */
public class Unit {
    private Globa globa;
    private DbConnect db;
    private Vector users=new Vector();

    //���췽��
    public Unit() {
    }

    //���췽��
    public Unit(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_sy_unit";

    //���?
    public boolean add() {
        String strSql = "";
        try {
            strId = UID.getID();
            //��ɵ�λ���룻�?2λΪһ��
            strUnitCode = creatUnitCode(strParentId);
            strSql = "INSERT INTO " + strTableName + " (strId, strUnitName, strEasyName, strUnitCode, " +
            		"strParentId,strDistrictId,intLevel,intSort, " +
                    "strUnitAddress, strUnitNet, strUnitEmail,strPostalCode, strAreaCode, strUnitPhone, " +
                    "strUnitFax, strRemark, strCreator,dCreatDate,strOrgType,strAFlag) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strUnitName);
            db.setString(3, strEasyName);
            db.setString(4, strUnitCode);
            db.setString(5, strParentId);
            db.setString(6, strDistrictId);
            db.setInt(7, strUnitCode.length() / 2);   //intLevel
            db.setInt(8, creatSort(strParentId));           //intSort
            db.setString(9, strUnitAddress);
            db.setString(10, strUnitNet);
            db.setString(11, strUnitEmail);
            db.setString(12, strPostalCode);
            db.setString(13, strAreaCode);
            db.setString(14, strUnitPhone);
            db.setString(15, strUnitFax);
            db.setString(16, strRemark);
            db.setString(17, globa.loginName);
            db.setString(18, com.ejoysoft.common.Format.getDateTime());
            db.setString(19, strOrgType);
            db.setString(20, strAFlag);
            if (db.executeUpdate() > 0) {
                Globa.logger0("��ӵ�λ���?", globa.loginName, globa.loginIp, strSql, "��λ����", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //��ɵ�λ���룻�?2λΪһ��
    public String creatUnitCode(String strParentId) {
        ResultSet rs = null;
        String tStrUnitCode = "10";
        try {
            if (strParentId.equals("")) {
                //û���ϼ���λ
                rs = db.executeQuery("SELECT  strUnitCode  FROM " + strTableName + " WHERE  strParentId='' ORDER BY strUnitCode DESC LIMIT 0,1");
                if (rs != null && rs.next()) {
                    tStrUnitCode = String.valueOf(Integer.parseInt(rs.getString("strUnitCode")) + 1);
                } else
                    tStrUnitCode = "10";
            } else {
                //���ϼ���λ
                Unit ut0 = show(" where strId='" + strParentId + "'");
                rs = db.executeQuery("SELECT   strUnitCode  FROM " + strTableName + " WHERE  strParentId='" + strParentId + "' and strUnitCode like '" + ut0.getStrUnitCode() + "__' ORDER BY strUnitCode DESC LIMIT 0,1");
                if (rs != null && rs.next()) {
                    //��ǰstrId=strParentId �Ѿ����¼���λ
                    tStrUnitCode = String.valueOf(Integer.parseInt(rs.getString("strUnitCode")) + 1);
                } else {
                    //��ǰstrId=strParentId  û���¼���λ
                    rs = db.executeQuery("SELECT strUnitCode  FROM " + strTableName + " WHERE  strId='" + strParentId + "'");
                    if (rs != null && rs.next())
                        tStrUnitCode = rs.getString("strUnitCode") + "00";
                }
            }
            rs.close();
            rs = null;
            return tStrUnitCode;
        } catch (Exception ee) {
            ee.printStackTrace();
            return "00";
        }
    }

    //���ͬһ��λ�µ��¼���λ���飩��? �����?
    public int creatSort(String strParentId) {
        ResultSet rs = null;
        int tIntSort = 0;
        try {
            rs = db.executeQuery("SELECT Max(intSort)  FROM " + strTableName + " WHERE  strParentId='" + strParentId + "'");
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

    //ɾ��
    public boolean delete(String where) {
        try {
            Unit ut0 = show(where);
            //����
            db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strParentId= '" + ut0.getStrParentId() + "' and intSort>" + ut0.getIntSort() + "");
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            Globa.logger0("ɾ��λ��Ϣ", globa.loginName, globa.loginIp, sql, "��λ����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //�޸�
    public boolean update(String tStrId) {
        try {

            String strSql = "UPDATE  " + strTableName + "  SET strUnitName = ?,strEasyName = ?,strUnitCode = ?," +
            		"strParentId = ?,strDistrictId=?,intLevel = ?, strUnitAddress = ?,strUnitNet = ?, strUnitEmail = ?, strPostalCode = ?, strAreaCode = ?," +
                    "strUnitPhone = ?, strUnitFax = ?,intSort = ?, strRemark = ?, strCreator = ?, dCreatDate = ?," +
                    "strOrgType=?,strAFlag=?  WHERE strId = ? ";
            if (strParentId.equals(strOldParentId)) {

                //��Ÿ���?
                if (intOldSort != intSort) {
                    if (intSort > intOldSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strParentId= '" + strParentId + "' and intSort>" + intOldSort + " and intSort<=" + intSort);
                    } else if (intSort < intOldSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort+1 where strParentId= '" + strParentId + "' and intSort>=" + intSort + " and intSort<" + intOldSort);
                    }
                }
                db.prepareStatement(strSql);
                db.setString(1, strUnitName);
                db.setString(2, strEasyName);
                db.setString(3, strUnitCode);
                db.setString(4, strParentId);
                db.setString(5, strDistrictId);
                db.setInt(6, intLevel);
                db.setString(7, strUnitAddress);
                db.setString(8, strUnitNet);
                db.setString(9, strUnitEmail);
                db.setString(10, strPostalCode);
                db.setString(11, strAreaCode);
                db.setString(12, strUnitPhone);
                db.setString(13, strUnitFax);
                db.setInt(14, intSort);
                db.setString(15, strRemark);
                db.setString(16, globa.loginName);
                db.setString(17, com.ejoysoft.common.Format.getDateTime());
                db.setString(18, strOrgType);
                db.setString(19, strAFlag);
                db.setString(20, tStrId);
                db.executeUpdate();
            } else {
                //����ǰ���������������?
                db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strParentId= '" + strOldParentId + "'  and intSort>" + intOldSort);
                strUnitCode = creatUnitCode(strParentId);
                db.prepareStatement(strSql);
                db.setString(1, strUnitName);
                db.setString(2, strEasyName);
                db.setString(3, strUnitCode);                 //
                db.setString(4, strParentId);
                db.setString(5, strDistrictId);
                db.setInt(6, strUnitCode.length() / 2);        //
                db.setString(7, strUnitAddress);
                db.setString(8, strUnitNet);
                db.setString(9, strUnitEmail);
                db.setString(10, strPostalCode);
                db.setString(11, strAreaCode);
                db.setString(12, strUnitPhone);
                db.setString(13, strUnitFax);
                db.setInt(14, creatSort(strParentId));
                db.setString(15, strRemark);
                db.setString(16, globa.loginName);
                db.setString(17, com.ejoysoft.common.Format.getDateTime());
                db.setString(18, strOrgType);
                db.setString(19, strAFlag);
                db.setString(20, tStrId);
                db.executeUpdate();
                //�ݹ�����ӵ�λ���������Ϣ
                updateChild(tStrId);
            }
            Globa.logger0("�޸ĵ�λ��Ϣ", globa.loginName, globa.loginIp, strSql, "��λ����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("�޸ĵ�λ��Ϣʱ��?" + e);
            return false;
        }
    }

    //�ݹ�����ӵ�λ���������Ϣ
    public void updateChild(String tStrId) throws SQLException {
        Unit unit = this.show(" where strId='" + tStrId + "'");
        Vector vct = this.list(" where strParentId='" + tStrId + "' ORDER BY intSort ASC", 0, 0);
        Unit unit0 = new Unit();
        String tUnitCode = "";
        for (int i = 0; vct != null && i < vct.size(); i++) {
            unit0 = (Unit) vct.get(i);
            tUnitCode = creatUnitCode(tStrId);
            db.executeUpdate("UPDATE  " + strTableName + "  SET  strUnitCode = '" + tUnitCode + "', intLevel = " + tUnitCode.length() / 2 + "  WHERE strId ='" + unit0.getStrId() + "' ");
            if (getCount(" where strParentId='" + unit0.getStrId() + "'") > 0) {
                updateChild(unit0.getStrId());
            }
        }
    }

    //��λ�Լ��޸ı���λ��Ϣ
    public boolean updateSelf(String tStrId) {
        try {
            String strSql = "UPDATE  " + strTableName + "  SET strUnitAddress = ?,strUnitNet = ?, strUnitEmail = ?, " +
                    "strPostalCode = ?, strAreaCode = ?,strUnitPhone = ?, strUnitFax = ?, strRemark = ?, strCreator = ?, dCreatDate = ?  WHERE strId = ? ";
            db.prepareStatement(strSql);
            db.setString(1, strUnitAddress);
            db.setString(2, strUnitNet);
            db.setString(3, strUnitEmail);
            db.setString(4, strPostalCode);
            db.setString(5, strAreaCode);
            db.setString(6, strUnitPhone);
            db.setString(7, strUnitFax);
            db.setString(8, strRemark);
            db.setString(9, globa.loginName);
            db.setString(10, com.ejoysoft.common.Format.getDateTime());
            db.setString(11, tStrId);
            db.executeUpdate();
            Globa.logger0("�޸ı���λ��Ϣ", globa.loginName, globa.loginIp, strSql, "��λ����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("�޸ı���λ��Ϣʱ��?" + e);
            return false;
        }
    }

    //��ϸ��ʾ������?
    public Unit show(String where) {
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
    public Unit load(ResultSet rs, boolean isView) {
        Unit theBean = new Unit();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrUnitName(rs.getString("strUnitName"));
            theBean.setStrEasyName(rs.getString("strEasyName"));
            theBean.setStrUnitCode(rs.getString("strUnitCode"));
            theBean.setStrParentId(rs.getString("strParentId"));
            theBean.setStrDistrictId(rs.getString("strDistrictId"));
            theBean.setIntLevel(rs.getInt("intLevel"));
            theBean.setStrUnitAddress(rs.getString("strUnitAddress"));
            theBean.setStrUnitNet(rs.getString("strUnitNet"));
            theBean.setStrUnitEmail(rs.getString("strUnitEmail"));
            theBean.setStrPostalCode(rs.getString("strPostalCode"));
            theBean.setStrAreaCode(rs.getString("strAreaCode"));
            theBean.setStrUnitPhone(rs.getString("strUnitPhone"));
            theBean.setStrUnitFax(rs.getString("strUnitFax"));
            theBean.setIntSort(rs.getInt("intSort"));
            theBean.setStrRemark(rs.getString("strRemark"));
            theBean.setStrCreator(rs.getString("strCreator"));
            theBean.setdCreatDate(rs.getString("dCreatDate"));
            theBean.setStrOrgType(rs.getString("strOrgType"));
            theBean.setStrAFlag(rs.getString("strAFlag"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //��ѯ�������ļ�¼����
    public int getCount(String where) {
        int count = 0;
        try {
            String sql = "SELECT count(*) FROM " + strTableName + "  ";
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
    public Vector list(String where, int startRow, int rowCount) {
        Vector beans = new Vector();
        try {
            String sql = "SELECT *  FROM  " + strTableName;
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
                    Unit theBean = new Unit();
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

    //�б��¼��?
    public HashMap hmList(String where, int startRow, int rowCount) {
        HashMap hmUnit = new HashMap();
        try {
            String sql = "SELECT *  FROM  " + strTableName;
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
                    Unit theBean = new Unit();
                    theBean = load(rs, false);
                    hmUnit.put(theBean.getStrId(), theBean);
                } while (rs.next());
            }
            rs.close();
            s.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return hmUnit;
    }

    //��ȡָ�����ֶ�ֵ
    public String retFieldValue(String strField, String tUnitId) {
        String strValue = "";
        try {
            ResultSet rs = db.executeQuery("select " + strField + "  from " + strTableName + " where strId='" + tUnitId+"'");
            if (rs.next()) {
                strValue = rs.getString(1);
            } else
                strValue = "";
            rs.close();
            rs = null;
            return strValue;
        } catch (Exception ee) {
            ee.printStackTrace();
            return "";
        }
    }

    //��ȡָ�����ֶ�ֵ
    public String retFieldVal(String strField, String tWhere) {
        String strValue = "";
        try {
            String strSql = "SELECT " + strField + "  FROM " + strTableName + " ".concat(tWhere);
            ResultSet rs = db.executeQuery(strSql);
            if (rs.next()) {
                strValue = rs.getString(1);
            } else
                strValue = "";
            rs.close();
            rs = null;
            return strValue;
        } catch (Exception ee) {
            ee.printStackTrace();
            return "";
        }
    }

//-------------------------------------------------------------------------------------------------------
    public String retFieldCodeValue(String strField, String tUnitCode) {
        try {
            ResultSet rs = db.executeQuery("select " + strField + "  from " + strTableName + " where strUnitCode=" + tUnitCode);
            if (rs.next()) {
                return rs.getString(1);
            } else
                return "";
        } catch (Exception ee) {
            ee.printStackTrace();
            return "";
        }
    }

    //��ȡ��λ���?
    public String retUnitName(String strUnitId) {
        String strArryUnitName = "";
        try {
            if (strUnitId != null && !strUnitId.equals("")) {
                ResultSet rs = db.executeQuery("select strUnitName  from " + strTableName + " where strId  in (" + strUnitId + ")");
                while (rs.next()) {
                    if (strArryUnitName.equals(""))
                        strArryUnitName = rs.getString(1);
                    else
                        strArryUnitName += "��" + rs.getString(1);
                }
            }
            return strArryUnitName;
        } catch (Exception ee) {
            ee.printStackTrace();
            return "";
        }
    }

    //��ʾ��λ���?
    public String getUnitName(String unitcode) {
        String nuitname = "";
        String sql = "select strUnitName  from " + strTableName + " where strUnitCode='" + unitcode + "'";
        try {
            db.prepareStatement(sql);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                nuitname = rs.getString("strUnitName");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("��ȡ��λ���ʱ����?" + e);
        }
        return nuitname;
    }

    private String strId;//	��λ��id��
    private String strUnitName;//	��λ���飩���?
    private String strEasyName;//	��λ���飩���?
    private String strUnitCode;//	��λ���飩����
    private String strParentId;//		 	�ϼ���λID
    private String strDistrictId;	// ����Ͻ��ID
    private int intLevel;//	��λ����
    private int intSort;//	�����?
    private String strUnitAddress;//		jϵ��ַ
    private String strUnitNet;//			��λ��ַ
    private String strUnitEmail;//			E-mail
    private String strPostalCode;//				��������
    private String strAreaCode;//				���?
    private String strUnitPhone;//			jϵ�绰
    private String strUnitFax;//			�������?
    private String strRemark;//		��ע
    private String strCreator;//			������
    private String dCreatDate;//		����ʱ��
    private String strOldParentId;//�޸�ǰ���ϼ���λID
    private int intOldSort;//�޸�ǰ�������?
    
    private String strOrgType;	//��֯��ʽ��ͳһ���ˡ�}�����ˡ�ũ��������С�ũ����ҵ����?
    private String strAFlag;	//A������?


    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrUnitName() {
        return strUnitName;
    }

    public void setStrUnitName(String strUnitName) {
        this.strUnitName = strUnitName;
    }

    public String getStrEasyName() {
        return strEasyName;
    }

    public void setStrEasyName(String strEasyName) {
        this.strEasyName = strEasyName;
    }

    public String getStrUnitCode() {
        return strUnitCode;
    }

    public void setStrUnitCode(String strUnitCode) {
        this.strUnitCode = strUnitCode;
    }

    public String getStrParentId() {
        return Format.removeNull(strParentId);
    }

    public void setStrParentId(String strParentId) {
        this.strParentId = strParentId;
    }

    public void setStrDistrictId(String strDistrictId) {
		this.strDistrictId = strDistrictId;
	}

	public String getStrDistrictId() {
		return strDistrictId;
	}

    public int getIntLevel() {
        return intLevel;
    }

    public void setIntLevel(int intLevel) {
        this.intLevel = intLevel;
    }

    public String getStrUnitAddress() {
        return strUnitAddress;
    }

    public void setStrUnitAddress(String strUnitAddress) {
        this.strUnitAddress = strUnitAddress;
    }

    public String getStrUnitNet() {
        return strUnitNet;
    }

    public void setStrUnitNet(String strUnitNet) {
        this.strUnitNet = strUnitNet;
    }

    public String getStrUnitEmail() {
        return strUnitEmail;
    }

    public void setStrUnitEmail(String strUnitEmail) {
        this.strUnitEmail = strUnitEmail;
    }

    public String getStrPostalCode() {
        return strPostalCode;
    }

    public void setStrPostalCode(String strPostalCode) {
        this.strPostalCode = strPostalCode;
    }

    public String getStrAreaCode() {
        return strAreaCode;
    }

    public void setStrAreaCode(String strAreaCode) {
        this.strAreaCode = strAreaCode;
    }

    public String getStrUnitPhone() {
        return strUnitPhone;
    }

    public void setStrUnitPhone(String strUnitPhone) {
        this.strUnitPhone = strUnitPhone;
    }

    public String getStrUnitFax() {
        return strUnitFax;
    }

    public void setStrUnitFax(String strUnitFax) {
        this.strUnitFax = strUnitFax;
    }

    public int getIntSort() {
        return intSort;
    }

    public void setIntSort(int intSort) {
        this.intSort = intSort;
    }

    public String getStrRemark() {
        return strRemark;
    }

    public void setStrRemark(String strRemark) {
        this.strRemark = strRemark;
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

    public String getStrOldParentId() {
        return strOldParentId;
    }

    public void setStrOldParentId(String strOldParentId) {
        this.strOldParentId = strOldParentId;
    }

    public int getIntOldSort() {
        return intOldSort;
    }

    public void setIntOldSort(int intOldSort) {
        this.intOldSort = intOldSort;
    }

    public void setStrOrgType(String strOrgType) {
		this.strOrgType = strOrgType;
	}

	public String getStrOrgType() {
		return strOrgType;
	}

	public void setStrAFlag(String strAFlag) {
		this.strAFlag = strAFlag;
	}

	public String getStrAFlag() {
		return strAFlag;
	}

	private Vector children = new Vector(); //�¼���λ/����

    public Vector getChildren() {
        return children;
    }

    public void setChildren(Vector children) {
        this.children = children;
    }

    public void addChild(Unit ug) {
        this.children.add(ug);
    }

    /**
     * �ж��Ƿ����¼��û���
     * @return
     */
    public boolean haveChild() {
        return this.children.size() > 0;
    }

    /**
     * �������е��¼��û��飬�����浽�û�����������
     * @param userUnitTree �û���������
     */
    public void loadChildren(Vector userUnitTree) {
        // this.children��������
        SortVector sv = new SortVector(new UnitCompare());
        for (int i = 0; i < this.children.size(); i++) {
            sv.add((Unit) this.children.get(i));
        }
        sv.sort();
        this.children = sv;
        for (int i = 0; i < this.children.size(); i++) {
            Unit unit0 = (Unit) this.children.get(i);
            unit0.setIntLevel(this.getIntLevel() + 1);
            userUnitTree.add(unit0);
//            System.out.println(unit0.getStrUnitName()+"============  " +unit0.getStrId()+"      ========          "+unit0.getStrParentId()+"   ======="+unit0.children.size());
            //�ݹ�
            unit0.loadChildren(userUnitTree);
        }
    }

    //vetcor��������
// �Զ���ȽϹ���?
    static class UnitCompare implements Compare {
        public boolean lessThan(Object l, Object r) {
            return ((Unit) l).getIntSort() < (((Unit) r).getIntSort());
        }

        public boolean lessThanOrEqual(Object l, Object r) {
            return ((Unit) l).getIntSort() <= (((Unit) r).getIntSort());
        }
    }
    public Vector depColTree = new Vector();    //�����νṹ����ָ������
    public Vector depCols = new Vector();    //�����νṹ����ָ������
        public Vector getDepColTree() {
        return depColTree;
    }

    public void setDepColTree(Vector depColTree) {
        this.depColTree = depColTree;
    }

    /**
     * ����ָ��8Ŀ��������8Ŀ
     * @param arryCol
     */
    public boolean getDeptColIds(String[] arryCol) {
        if (arryCol == null || arryCol.length == 0) return false;
        Unit col0 = null;
        for (int m = 0; m < arryCol.length; m++) {
            col0 = SysUserUnit.getUserGroup(arryCol[m]);
            if (col0 != null)
                depCols.add(col0);
        }
        if (depCols != null) {
            loadDeptColTree(depColTree);
            return true;
        } else
            return false;
    }

    /**
     * �������еĲ�����Ϣ�������浽8Ŀ��������
     * @param depColTree 8Ŀ������
     */
    private void loadDeptColTree(Vector depColTree) {
        // ��������
        SortVector sv = new SortVector(new Unit.UnitCompare());
        for (int i = 0; i < depCols.size(); i++) {
            sv.add((Unit) depCols.get(i));
        }
        sv.sort();
        depCols = sv;
        //����8Ŀ������8Ŀ��Ϣ
        for (int i = 0; i < depCols.size(); i++) {
            //���붥��8Ŀ
            Unit col0 = (Unit) depCols.get(i);
            col0.setIntLevel(1);
            depColTree.add(col0);
            //�����¼�8Ŀ
            col0.loadChildren(depColTree);
        }
    }
    /**
     * ����ָ�����ŵ������¼���8Ŀ
     * @param deptCol
     */
    public boolean oneDeptColId(String deptCol) {
        if (deptCol == null || "".equals(deptCol)) return false;
        Unit col0 = SysUserUnit.getUserGroup(deptCol);
        if (col0 != null)
            depCols.add(col0);
        if (depCols != null) {
            loadDeptColTree(depColTree);
            return true;
        } else
            return false;
    }
    
    public void getChildLevel(Vector<Unit> vctChild, int intLevel) {
    	for (int i = 0; i < children.size(); i++) {
    		Unit u = (Unit)children.get(i); 
    		if (u.getIntLevel() == intLevel)
    			vctChild.add(u);
    		else if (u.getIntLevel() < intLevel)
    			u.getChildLevel(vctChild, intLevel);
    	}
    }
}
