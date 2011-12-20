package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class MenuItem {

	private Globa globa;
    private DbConnect db;

    //构�?�方�?
    public MenuItem() {
    }

    //构�?�方�?
    public MenuItem(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    static String strTableName = "t_bz_menuitem";
    
  //增加
    public boolean add() {
        String strSql = "";
        try {
            strId = UID.getID();
            strSql = "INSERT INTO " + strTableName + " (strId,strName,strTypeId,strIntro,strSmallImage,strLargeImage,flaPrice," +
            		"intRecommend,intNew,intSort,strCreator,dtCreateTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strName);
            db.setString(3, strTypeId);
            db.setString(4, strIntro);
            db.setString(5, strSmallImage);
            db.setString(6, strLargeImage);
            db.setFloat(7, flaPrice);
            db.setInt(8, intRecommend);
            db.setInt(9, intNew);
            db.setInt(10, intSort);
            db.setString(11, globa.fullRealName);
            db.setString(12, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("增加菜品", globa.loginName, globa.loginIp, strSql, " 菜谱管理", globa.userSession.getStrDepart());
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
            Globa.logger0("删除菜品", globa.loginName, globa.loginIp, sql, "菜谱管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //修改
    public boolean update(String tStrId) {
        try {
            String strSql = "UPDATE  " + strTableName + "  SET strName = '" + this.strName + "',strTypeId = '" + this.strTypeId + 
            		"',strIntro = '" + this.strIntro + "',";
            if (this.strSmallImage.length() > 0) {
            	strSql += "strSmallImage = '" + this.strSmallImage + "',";
            }
            if (this.strLargeImage.length() > 0) {
            	strSql += "strLargeImage = '" + this.strLargeImage + "',";
            }
            strSql += "flaPrice = " + this.flaPrice + ",intRecommend = " + this.intRecommend + ",intNew = " + this.intNew + 
            		",intSort = " + this.intSort + ",strCreator ='" + globa.fullRealName + "',dtCreateTime='" + 
            		com.ejoysoft.common.Format.getDateTime() + "' WHERE strId = '" + this.strId + "'";
            db.executeUpdate(strSql);
            Globa.logger0("修改菜品", globa.loginName, globa.loginIp, strSql, "菜谱管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //详细显示单条记录
    public MenuItem show(String where) {
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
    public MenuItem load(ResultSet rs, boolean isView) {
    	MenuItem theBean = new MenuItem();
        try {
            theBean.setStrId(rs.getString("strId"));
            theBean.setStrName(rs.getString("strName"));
            theBean.setStrTypeId(rs.getString("strTypeId"));
            theBean.setStrIntro(rs.getString("strIntro"));
            theBean.setStrSmallImage(rs.getString("strSmallImage"));
            theBean.setStrLargeImage(rs.getString("strLargeImage"));
            theBean.setFlaPrice(rs.getFloat("flaPrice"));
            theBean.setIntRecommend(rs.getInt("intRecommend"));
            theBean.setIntNew(rs.getInt("intNew"));
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
    public Vector<MenuItem> list(String where, int startRow, int rowCount) {
        Vector<MenuItem> beans = new Vector<MenuItem>();
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
                	MenuItem theBean = new MenuItem();
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
    private String strTypeId;//�?属类别ID
    private String strIntro;//文字介绍
    private String strSmallImage;//小图片文件名
    private String strLargeImage;//大图片文件名
    private float flaPrice;//价格
    private int intRecommend;//是否是主厨推荐：0-否；1-�?
    private int intNew;//是否是最新菜品：0-否；1-�?
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

	public void setStrTypeId(String strTypeId) {
		this.strTypeId = strTypeId;
	}

	public String getStrTypeId() {
		return strTypeId;
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

	public void setFlaPrice(float flaPrice) {
		this.flaPrice = flaPrice;
	}

	public float getFlaPrice() {
		return flaPrice;
	}

	public void setIntRecommend(int intRecommend) {
		this.intRecommend = intRecommend;
	}

	public int getIntRecommend() {
		return intRecommend;
	}

	public void setIntNew(int intNew) {
		this.intNew = intNew;
	}

	public int getIntNew() {
		return intNew;
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
