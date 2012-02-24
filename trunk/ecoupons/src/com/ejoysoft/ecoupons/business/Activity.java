package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
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
public class Activity {
    private Globa globa;
    private DbConnect db;

    public Activity() {
    }

    public Activity(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    public Activity(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_bz_activity";
    String strTableName2 = "t_bz_download_alert";

    //添加最新活动信息
    public boolean add() {
        String strSql = "";
        strId = UID.getID();  
        strSql = "insert into " + strTableName + "  (strid, strname, strcontent, dtactivetime, strcreator, dtcreatetime" +
        ") values(?,?,?,?,?,?)";
        try {

        	db.getConnection().setAutoCommit(false);//禁止自动提交事务        
            Terminal obj = new Terminal(globa);
            String[] strTerminalId = obj.getAllTerminalNos();
            if(strTerminalId!=null)
            {
            	  for(int i=0;i<strTerminalId.length;i++)
            	  {
            		  String[] strid = strTerminalId[i].split("-");
            		  String strsql2 ="insert into " + strTableName2 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
            		  + "values (" + UID.getID() + ",'" + strid[0]+ "','" + strTableName + "','" + strId + "','add',0)";
            		  db.executeUpdate(strsql2);
            	  }                
            }
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strName.replace("<br/>", ""));
            db.setString(3, strContent.replace("<br/>", ""));   
            db.setString(4, com.ejoysoft.common.Format.getStrDate2(dtActiveTime));
            db.setString(5, strCreator);
            db.setString(6, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {                
            	db.getConnection().commit(); //统一提交
			    db.setAutoCommit(true);
                Globa.logger0("添加活动信息", globa.loginName, globa.loginIp, strSql, "活动管理", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("添加活动信息异常");
            e.printStackTrace();
            return false;
        }
    }
   //删除最新信息
    public boolean delete(String where,String where2,String strid) {
    	String sql1 = "delete from " + strTableName + "  ".concat(where);
    	String sql2 = "update  " + strTableName2 + "  set strdataopetype='delete',intstate=0 where strdataid=" + strid;

    	//事务处理
    	try {
        	db.getConnection().setAutoCommit(false);//禁止自动提交事务
        	
            db.executeUpdate(sql1);//删除最新活动
            db.executeUpdate(sql2);//删除活动信息表时更新下载提醒表
            db.getConnection().commit(); //统一提交
			db.setAutoCommit(true);
            Globa.logger0("删除最新活动信息", globa.loginName, globa.loginIp, sql1, "最新活动管理", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        } 
    }
    //活动更新信息
    public boolean update(String strId) {
        try {
        	String strSql2 = "update  " + strTableName2 + " set strdataopetype='update',intstate=0 where strdataid=" + strId;
        	
		    String strSql = "update " + strTableName + "  set strname=?, strcontent=?, dtactivetime=?, strcreator=? where strid=? ";
            db.getConnection().setAutoCommit(false);
    		db.executeUpdate(strSql2);
            db.prepareStatement(strSql);
            db.setString(1, strName.replace("<br/>", ""));
            db.setString(2, strContent.replace("<br/>", ""));
            db.setString(3, dtActiveTime);
            db.setString(4, strCreator);
            db.setString(5, strId);
            db.executeUpdate();
			db.commit();
			db.setAutoCommit(true);
            Globa.logger0("更新活动信息", globa.loginName, globa.loginIp, strSql, "活动管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("更行活动信息异常" + e);
            return false;
        }
    }

    //查询
    public Activity show(String where) {
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
    
  //封装活动信息结果集
    public Activity load(ResultSet rs, boolean isView) {
    	Activity theBean = new Activity();
        try {
            theBean.setStrId(rs.getString("strid"));
            theBean.setStrName(rs.getString("strname"));
            theBean.setStrContent(rs.getString("strcontent"));
            theBean.setDtActiveTime(rs.getString("dtactivetime"));
            theBean.setStrCreator(rs.getString("strcreator"));
            theBean.setDtCreateTime(rs.getString("dtcreatetime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //分页整理
    public Vector<Activity> list(String where, int startRow, int rowCount) {
        Vector<Activity> beans = new Vector<Activity>();
        try {
            String sql = "select *  from  " + strTableName + " ";
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
                	Activity theBean = new Activity();
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
  
	/**
	 * 根据活动名 返回活动id
	 */
	public String getShopIdByName(String strName)
	{
		try
		{
			String sql = "select * FROM  " + strTableName + " ";
			if (!strName.equals("")) {
				
				String strbizname="",strshopname="",where="";
					String name[]= strName.trim().split("-");
			    	if(name.length==1)
			    	{
			    		strbizname = name[0]; 
			    		where = " where strbizname='" + strbizname + "'";						
			    	}
			    	else{
			    		strbizname = name[0];
			    		strshopname = name[1];
			    		where = " where strbizname='" + strbizname + "' and strshopname='" + strshopname + "'";
					}
					 sql += where;
			}			
			ResultSet rs = db.executeQuery(sql);
			if (rs != null && rs.next())
			{
				return rs.getString("strid");
			} else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}
    
    private String strId;//活动Id
    private String strName;//活动名称
    private String strContent;//活动内容
    private String dtActiveTime;//活动时间
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

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public String getDtActiveTime() {
		if (dtActiveTime!=null)
		{
			return dtActiveTime.substring(0, dtActiveTime.length()-2);
		}
		return dtActiveTime;
	}

	public void setDtActiveTime(String strActiveTime) {
		this.dtActiveTime = strActiveTime;
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