package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class Game {

	private Globa globa;
    private DbConnect db;

    //构�?�方�?
    public Game() {
    }

    //构�?�方�?
    public Game(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    static String strTableName = "t_bz_game";
    
  //增加
    public boolean add() {
        String strSql = "";
        try {
            strId = UID.getID();
            strSql = "INSERT INTO " + strTableName + " (strId,strName,strIntro,strSmallImage,strLargeImage," +
            		"strFile,intSort,strCreator,dtCreateTime) VALUES (?,?,?,?,?,?,?,?,?) ";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strName);
            db.setString(3, strIntro);
            db.setString(4, strSmallImage);
            db.setString(5, strLargeImage);
            db.setString(6, strFile);
            db.setInt(7, intSort);
            db.setString(8, globa.fullRealName);
            db.setString(9, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("增加游戏", globa.loginName, globa.loginIp, strSql, " 游戏管理", globa.userSession.getStrDepart());
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
            Globa.logger0("删除游戏", globa.loginName, globa.loginIp, sql, "游戏管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //修改
    public boolean update(String tStrId) {
        try {
            String strSql = "UPDATE  " + strTableName + "  SET strName = '" + this.strName + "',strIntro = '" + this.strIntro + "',";
            if (this.strSmallImage.length() > 0) {
            	strSql += "strSmallImage = '" + this.strSmallImage + "',";
            }
            if (this.strLargeImage.length() > 0) {
            	strSql += "strLargeImage = '" + this.strLargeImage + "',";
            }
            if (this.strFile.length() > 0) {
            	strSql += "strFile = '" + this.strFile + "',";
            }
            strSql += "intSort = " + this.intSort + ",strCreator ='" + globa.fullRealName + "',dtCreateTime='" + 
            		com.ejoysoft.common.Format.getDateTime() + "' WHERE strId = '" + this.strId + "'";
            db.executeUpdate(strSql);
            Globa.logger0("修改游戏", globa.loginName, globa.loginIp, strSql, "游戏管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //详细显示单条记录
    public Game show(String where) {
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
    public Game load(ResultSet rs, boolean isView) {
    	Game theBean = new Game();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrName(rs.getString("strName"));
            theBean.setStrIntro(rs.getString("strIntro"));
            theBean.setStrSmallImage(rs.getString("strSmallImage"));
            theBean.setStrLargeImage(rs.getString("strLargeImage"));
            theBean.setStrFile(rs.getString("strFile"));
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
    public Vector<Game> list(String where, int startRow, int rowCount) {
        Vector<Game> beans = new Vector<Game>();
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
                	Game theBean = new Game();
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
    private String strName;//	名称
    private String strIntro;//文字介绍
    private String strSmallImage;//小图片文件名
    private String strLargeImage;//大图片文件名
    private String strFile;//游戏文件
    private int intSort;//		序号
    private String strCreator;//			创建�?
    private String dtCreateTime;//		创建时间

    public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrIntro(String strIntro) {
		this.strIntro = strIntro;
	}

	public String getStrIntro() {
		return strIntro;
	}

	public void setStrSmallImage(String strSmallImage) {
		this.strSmallImage = strSmallImage;
	}

	public String getStrSmallImage() {
		return strSmallImage;
	}

	public void setStrLargeImage(String strLargeImage) {
		this.strLargeImage = strLargeImage;
	}

	public String getStrLargeImage() {
		return strLargeImage;
	}

	public void setStrFile(String strFile) {
		this.strFile = strFile;
	}

	public String getStrFile() {
		return strFile;
	}

	public void setIntSort(int intSort) {
		this.intSort = intSort;
	}

	public int getIntSort() {
		return intSort;
	}

	public void setStrCreator(String strCreator) {
		this.strCreator = strCreator;
	}

	public String getStrCreator() {
		return strCreator;
	}

	public void setDtCreateTime(String dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}

	public String getDtCreateTime() {
		return dtCreateTime;
	}

}