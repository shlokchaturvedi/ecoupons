package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.ejoysoft.ecoupons.system.SysPara;

//import javax.servlet.ServletContext;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-1
 * Time: 9:12:43
 * To change this template use Options | File Templates.
 */
public class TerminalPara {
    private Globa globa;
    private DbConnect db;

    public TerminalPara() {
    }

    public TerminalPara(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public TerminalPara(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_bz_terminal_param";

    //添加终端参数信息
    public boolean add() {
        String strSql = "";
        strId = UID.getID();
        try {
            strSql = "insert into " + strTableName + "  (strid, strparamname, strparamvalue, strcreator," +
            		" dtcreatetime) values(?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strParamName);
            db.setString(3, strParamValue);   //strPWD
            db.setString(4, strCreator);
            db.setString(5, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("添加终端参数信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("添加终端参数信息异常");
            e.printStackTrace();
            return false;
        }
    }
   //删除终端参数信息
    public boolean delete(String where) {
    	String sql1 = "delete from " + strTableName + "  ".concat(where);
    	   	//事务处理
    	try {
        	db.getConnection().setAutoCommit(false);//禁止自动提交事务        
        	db.getConnection().setSavepoint();
            db.executeUpdate(sql1);//删除商家
                db.getConnection().commit(); //统一提交
            Globa.logger0("删除终端参数信息", globa.loginName, globa.loginIp, sql1, "终端管理", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        } 
    }
  
    //更新终端参数
    public boolean update(String tStrUserId) {
        try {
        	String strSql = "update " + strTableName + "  set strparamname=?, strparamvalue=? where strid=? ";
        	db.prepareStatement(strSql);
            db.setString(1, strParamName);
            db.setString(2, strParamValue);
            db.setString(3,tStrUserId);
            db.executeUpdate();
            Globa.logger0("更新终端参数信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("更行终端参数信息异常" + e);
            return false;
        }
    }

    //查询
    public TerminalPara show(String where) {
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
  
  //封装终端参数
    public TerminalPara load(ResultSet rs, boolean isView) {
    	TerminalPara theBean = new TerminalPara();
        try {
        	theBean.setStrId(rs.getString(1));
            theBean.setStrParamName(rs.getString(2));
            theBean.setStrParamValue(rs.getString(3));
            theBean.setStrCreator(rs.getString(4));
            theBean.setDtCreateTime(rs.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //分页整理
    public Vector<TerminalPara> list(String where, int startRow, int rowCount) {
        Vector<TerminalPara> beans = new Vector<TerminalPara>();
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
                	TerminalPara theBean = new TerminalPara();
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

   //查询影响记录数
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
	
    
    private String strId;//参数id
    private String strParamName;//参数名称
    private String strParamValue;//参数值
    private String strCreator;//创建人
    private String dtCreateTime;//创建时间

	public Globa getGloba() {
		return globa;
	}

	public void setGloba(Globa globa) {
		this.globa = globa;
	}

	public DbConnect getDb() {
		return db;
	}

	public void setDb(DbConnect db) {
		this.db = db;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}
	public String getStrParamName() {
		return strParamName;
	}

	public void setStrParamName(String strParamName) {
		this.strParamName = strParamName;
	}

	public String getStrParamValue() {
		return strParamValue;
	}

	public void setStrParamValue(String strParamValue) {
		this.strParamValue = strParamValue;
	}

	public String getStrCreator() {
		return strCreator;
	}

	public void setStrCreator(String strCreator) {
		this.strCreator = strCreator;
	}

	public String getDtCreateTime() {
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}

}
