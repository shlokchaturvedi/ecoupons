package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class MenuType {

	private Globa globa;
    private DbConnect db;

    //构�?�方�?
    public MenuType() {
    }

    //构�?�方�?
    public MenuType(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    static String strTableName = "t_bz_menutype";
    
  //增加
    public boolean add() {
        String strSql = "";
        try {
            strId = UID.getID();
            strSql = "INSERT INTO " + strTableName + " (strId, strCode, strName, intSort, strCreator, dtCreateTime) VALUES (?,?,?,?,?,?) ";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strCode);
            db.setString(3, strName);
            db.setInt(4, intSort);
            db.setString(5, globa.fullRealName);
            db.setString(6, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("增加菜品类别", globa.loginName, globa.loginIp, strSql, " 菜谱管理", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除
    public boolean delete(String where) {
        try {
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            Globa.logger0("删除菜品类别", globa.loginName, globa.loginIp, sql, "菜谱管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //修改
    public boolean update(String tStrId) {
        try {

            String strSql = "UPDATE  " + strTableName + "  SET strCode = ?, strName = ?, intSort = ?, " +
            		"strCreator =?,dtCreateTime=? WHERE strId = ? ";
            db.prepareStatement(strSql);
            db.setString(1, strCode);
            db.setString(2, strName);
            db.setInt(3, intSort);
            db.setString(4, globa.fullRealName);
            db.setString(5, com.ejoysoft.common.Format.getDateTime());
            db.setString(6, tStrId);
            db.executeUpdate();
            Globa.logger0("修改菜品类别", globa.loginName, globa.loginIp, strSql, "菜谱管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //详细显示单条记录
    public MenuType show(String where) {
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

    //记录集转化为对象
    public MenuType load(ResultSet rs, boolean isView) {
    	MenuType theBean = new MenuType();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrCode(rs.getString("strCode"));
            theBean.setStrName(rs.getString("strName"));
            theBean.setIntSort(rs.getInt("intSort"));
            theBean.setStrCreator(rs.getString("strCreator"));
            theBean.setDtCreateTime(rs.getString("dtCreateTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //查询符合条件的记录�?�数
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

    //列表记录�?
    public Vector<MenuType> list(String where, int startRow, int rowCount) {
        Vector<MenuType> beans = new Vector<MenuType>();
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
                	MenuType theBean = new MenuType();
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

    private String strId;//	唯一关键�?
    private String strCode;//	编码
    private String strName;//	名称
    private int intSort;//		序号
    private String strCreator;//			创建�?
    private String dtCreateTime;//		创建时间

	public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrName() {
		return strName;
	}

	public int getIntSort() {
        return this.intSort;
    }

    public void setIntSort(int intSort) {
        this.intSort = intSort;
    }

    public String getStrCreator() {
        return strCreator;
    }

    public void setStrCreator(String strCreator) {
        this.strCreator = strCreator;
    }

    public String getDtCreateTime() {
        return this.dtCreateTime;
    }

    public void setDtCreateTime(String dtCreateTime) {
        this.dtCreateTime = dtCreateTime;
    }
}