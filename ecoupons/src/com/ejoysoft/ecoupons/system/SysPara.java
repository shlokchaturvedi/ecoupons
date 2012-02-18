package com.ejoysoft.ecoupons.system;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;



import java.util.HashMap;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Date;


public class SysPara {

    private Globa globa;
    private DbConnect db;

    //构�?�方�?
    public SysPara() {
    }

    public SysPara(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //构�?�方�?
    public SysPara(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }
      /**
     * 根据�?个已有的Parameter对象构�??
     * @param param 已有的Parameter对象
     */
    public SysPara(SysPara param) {
        this.strId = param.strId;
        this.strName = param.strName;
        this.strValue = param.strValue;
        this.strType = param.strType;
        this.strState = param.strState;
        this.intSort = param.intSort;
    }
    static String  strTableName = "t_sy_syspara";
    public static Vector<SysPara> vctSysPara;	//�?有参�?
    public static HashMap<String, SysPara> hmSysPara;	//�?有参数哈希表
    
    public static void init() {
    	 hmSysPara = new HashMap<String, SysPara>();
    	 vctSysPara = new Vector<SysPara>();
    	String sql = "SELECT * FROM " + strTableName + " ORDER BY intsort";
    	try {
    		Connection con = DbConnect.getStaticCon();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	SysPara theBean = new SysPara();
            	theBean.setStrId(rs.getString("strId"));
                theBean.setStrName(rs.getString("strName"));
                theBean.setStrValue(rs.getString("strValue"));
                theBean.setStrType(rs.getString("strType"));
                theBean.setStrState(rs.getString("strState"));
                theBean.setdCreatDate(rs.getString("dCreatDate"));
                theBean.setStrCreator(rs.getString("strCreator"));
               
                hmSysPara.put(theBean.getStrId(), theBean);
                vctSysPara.add(theBean);
            }
            System.out.println("[INFO]:SysPara Initialized Successful");
            rs.close();
            stmt.close();
            con.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("[ERROR]:An error occured in SysPara.init()");
    	}
    }
    
    
    //增加�?个栏�?
    public boolean add() throws SQLException {
        try {
            strId = UID.getID();
            int tintSort = netOrder(strType);
            //排序
            if (tintSort != intSort) {
                if (intSort > tintSort) {
                    db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strType= '" + strType + "' and intSort>" + tintSort + " and intSort<=" + intSort);
                } else if (intSort < tintSort) {
                    db.executeUpdate("update " + strTableName + " set intSort=intSort+1 where strType='" + strType + "' and intSort>=" + intSort + " and intSort<" + tintSort);
                }
            }
            String strSql = "INSERT INTO " + strTableName + "(strId, strType, strName, strValue, intSort, strState,strCreator, dCreatDate) VALUES (?,?,?,?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strType);
            db.setString(3, strName);
            db.setString(4, strValue);
            db.setInt(5, intSort);
            db.setString(6, strState);
            db.setString(7, globa.loginName);
            db.setString(8, com.ejoysoft.common.Format.getDateTime());
            db.executeUpdate();
            Globa.logger0("增加系统参数strId=" + strId, globa.loginName, globa.loginIp, strSql, "系统参数", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取�?大序列号
     *
     */
    public int netOrder(String tStrType) throws SQLException{
        String sql = "SELECT MAX(intSort) FROM  " + strTableName + " WHERE strType= '" + tStrType + "'";
        int lastOrder = 0;
        ResultSet rs = db.executeQuery(sql);
        try {
            if (rs != null && rs.next()) {
                lastOrder = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastOrder + 1;
    }

    public boolean update(String tStrtId) throws SQLException {
        try {
            if (strType.equals(strOldType)) {
                //序号更新
                if (intOldSort != intSort) {
                    if (intSort > intOldSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strType= '" + strType + "' and intSort>" + intOldSort + " and intSort<=" + intSort);
                    } else if (intSort < intOldSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort+1 where strType= '" + strType + "' and intSort>=" + intSort + " and intSort<" + intOldSort);
                    }
                }
            } else {
                int tintSort = netOrder(strType);
                //排序
                if (tintSort != intSort) {
                    if (intSort > tintSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strType= '" + strType + "' and intSort>" + tintSort + " and intSort<=" + intSort);
                    } else if (intSort < tintSort) {
                        db.executeUpdate("update " + strTableName + " set intSort=intSort+1 where strType='" + strType + "' and intSort>=" + intSort + " and intSort<" + tintSort);
                    }
                }
                db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strType= '" + strOldType + "'  and intSort>" + intSort + " ");
            }
            //更新内容
            String strSql = "UPDATE " + strTableName + " SET strType=?,strName=?,strValue=?,intSort=?,strState=?, strCreator=?, dCreatDate=?  WHERE strId='" + tStrtId + "'";
            db.prepareStatement(strSql);
            db.setString(1, strType);
            db.setString(2, strName);
            db.setString(3, strValue);
            db.setInt(4, intSort);
            db.setString(5, strState);
            db.setString(6, globa.loginName);
            db.setDate(7, new Date());
            db.executeUpdate();
            Globa.logger0("修改系统参数strId=" + strId, globa.loginName, globa.loginIp, strSql, "系统参数", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除�?条记�?
    public boolean delete(String where)throws SQLException {
        try {
            //排序
            SysPara var0 = show(where);
            db.executeUpdate("update " + strTableName + " set intSort=intSort-1 where strType= '" + var0.getStrType() + "'  and intSort>" + var0.intSort + " ");
            //删除
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            Globa.logger0("删除系统参数where=" + where, globa.loginName, globa.loginIp, sql, "系统参数", globa.userSession.getStrDepart());
            db.executeUpdate(sql);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    public static String getIdByName(String strName) {
		for (int i = 0; i < vctSysPara.size(); i++) {
			if (vctSysPara.get(i).getStrName().equals(strName))
				return vctSysPara.get(i).getStrValue();
		}
		return null;
	}
    public static String getIdByName2(String strName) {
		for (int i = 0; i < vctSysPara.size(); i++) {
			if (vctSysPara.get(i).getStrName().equals(strName))
				return vctSysPara.get(i).getStrId();
		}
		return null;
	}
    public static String getNameById(String strId) {
		for (int i = 0; i < vctSysPara.size(); i++) {
			if (vctSysPara.get(i).getStrId().equals(strId))
				return vctSysPara.get(i).getStrName();
		}
		return null;
	}

    public static String getIdByName(String strName, String strType) {
		for (int i = 0; i < vctSysPara.size(); i++) {
			SysPara bean = vctSysPara.get(i);
			if (bean.getStrName().equals(strName) && bean.getStrType().equals(strType))
				return bean.getStrId();
		}
		return null;
	}
    
    
	public static SysPara getParaByName(String strName) {
		for (int i = 0; i < vctSysPara.size(); i++) {
			if (vctSysPara.get(i).getStrName().equals(strName))
				return vctSysPara.get(i);
		}
		return null;
	}
	
   
    //详细显示单条记录
    public SysPara show(String where) throws SQLException{
        try {
            String strSql = "select * from  " + strTableName + "  ".concat(String.valueOf(String.valueOf(where)));
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next())
                return load(rs);
            else
                return null;
        } catch (Exception ee) {
            return null;
        }
    }

    //记录集转化为对象
    public SysPara load(ResultSet rs) throws SQLException{
        SysPara theBean = new SysPara();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrType(rs.getString("strType"));
            theBean.setStrName(rs.getString("strName"));
            theBean.setStrValue(rs.getString("strValue"));
            theBean.setIntSort(rs.getInt("intSort"));
            theBean.setStrState(rs.getString("strState"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //查询符合条件的记录�?�数
    public int getCount(String where) throws SQLException{
        int count = 0;
        try {
            String sql = "SELECT count(*) FROM " + strTableName + "  ";
            if (where.length() > 0) {
                where = where.toLowerCase();
                if (where.indexOf("order") > 0)
                    where = where.substring(0, where.indexOf("order"));
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
   

    //列表记录�?
    public Vector list(String where, int startRow, int rowCount) throws SQLException{
        Vector beans = new Vector();
        try {
            String sql = "SELECT *  FROM  " + strTableName + " ";
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
                    SysPara theBean = new SysPara();
                    theBean = load(rs);
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

    //列表显示
    public ArrayList list(String type) {

        String strSql = "SELECT strId,strType,strName,strValue,intSort FROM " + strTableName + "  order by strType , intSort ";
        if (type != null && !type.equals("")) {
            strSql = "SELECT strId,strType,strName,strValue,intSort  FROM " + strTableName + " where strType ='" + type + "' order by intSort ";
        }
        ArrayList vector = new ArrayList();
        try {
            ResultSet rs1 = db.executeQuery(strSql);
            while (rs1.next()) {
                SysPara bVar = new SysPara();
                bVar.setStrId(rs1.getString(1));
                bVar.setStrType(rs1.getString(2));
                bVar.setStrName(rs1.getString(3));
                bVar.setStrValue(rs1.getString(4));
                bVar.setIntSort(rs1.getInt(5));
                vector.add(bVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return vector;
        }
    }

    //列表显示
    public ArrayList list(String type,String userid) {

        String strSql = "SELECT strId,strType,strName,strValue,intSort FROM " + strTableName + "  order by strType , intSort ";
        if (type != null && !type.equals("")) {
            strSql = "SELECT strId,strType,strName,strValue,intSort  FROM " + strTableName + " where strCreator='"+userid+"' and strType ='" + type + "' order by intSort ";
        }
        ArrayList vector = new ArrayList();
        try {
            ResultSet rs1 = db.executeQuery(strSql);
            while (rs1.next()) {
                SysPara bVar = new SysPara();
                bVar.setStrId(rs1.getString(1));
                bVar.setStrType(rs1.getString(2));
                bVar.setStrName(rs1.getString(3));
                bVar.setStrValue(rs1.getString(4));
                bVar.setIntSort(rs1.getInt(5));
                vector.add(bVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return vector;
        }
    }

    
    
    
    
    private String strId;  //	参数id�?
    private String strType;  //		�?属类�?
    private String strName;  //	选项名称
    private String strValue;  //选项内容
    private int intSort;  //0	排序�?
    private String strState  ;//状�??
    private String strCreator;  //		创建�?
    private String dCreatDate;  //		创建时间
    private String strOldType;  //原所属类�?
    private int intOldSort;    //原序�?
    //private String strUserid;  //创建
    
    
    
    
    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public int getIntSort() {
        return intSort;
    }

    public void setIntSort(int intSort) {
        this.intSort = intSort;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
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

    public String getStrOldType() {
        return strOldType;
    }

    public void setStrOldType(String strOldType) {
        this.strOldType = strOldType;
    }

    public int getIntOldSort() {
        return intOldSort;
    }

    public void setIntOldSort(int intOldSort) {
        this.intOldSort = intOldSort;
    }

    public final static String _REMIND_TYPE = "备忘类型";

    public final static String[] ARRAY_TYPE =
            new String[]{
                _REMIND_TYPE,
            };
}