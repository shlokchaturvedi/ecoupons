package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.ejoysoft.common.exception.UserUnitIdException;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import com.ejoysoft.ecoupons.business.Shop;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-1
 * Time: 9:12:43
 * To change this template use Options | File Templates.
 */
public class Terminal {
    private Globa globa;
    private DbConnect db;

    //���췽��
    public Terminal() {
    }

    public Terminal(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //���췽��
    public Terminal(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_bz_terminal";
    String strTableName2 = "t_bz_coupon";
    String strTableName3 = "t_bz_coupon_input";
    String strTableName4 = "t_bz_point_buy";
    String strTableName5 = "t_bz_point_present";

    //添加券打机信息
    public boolean add() {
        String strSql = "";
        strId = UID.getID();
        try {
        	
        	   //添加券打机信息
            strSql = "insert into " + strTableName + "  (strid, strno, dtactivetime, strlocation,straroundshopids,strproducer, strtype, " +
            		"strresolution, strresolution2, strresolution3,intstate,dtrefreshtime, strcreator, dtcreatetime)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strNo);
            db.setString(3, com.ejoysoft.common.Format.getStrDate(dtActiveTime));   //strPWD
            db.setString(4, strLocation);
            db.setString(5, this.getAroundShopIds(strAroundShops));
            db.setString(6, strProducer);
            db.setString(7, strType);
            db.setString(8, strResolution);
            db.setString(9, strResolution2);
            db.setString(10, strResolution3);
            db.setInt(11, intState);
            db.setString(12, com.ejoysoft.common.Format.getDateTime());
            db.setString(13, strCreator);
            db.setString(14, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("添加终端信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("添加终端信息异常");
            e.printStackTrace();
            return false;
        }
    }
   //删除终端信息
    public boolean delete(String where) {
    	String sql = "delete from " + strTableName + "  ".concat(where);
       //事务处理
    	try {
        	db.getConnection().setAutoCommit(false);//禁止自动提交事务 
        	
            db.executeUpdate(sql);//删除终端
            
            db.getConnection().commit(); //统一提交
            Globa.logger0("删除终端信息", globa.loginName, globa.loginIp, sql, "终端管理", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        } 
    }

    //终端更新信息
    public boolean update(String strId) {
        try {
       	 System.out.println("where strbizname='"+strAroundShops+"' and strshopname='"+getAroundShopIds(strAroundShops)+"'");
         
        	String strSql = "update " + strTableName + "  set strno=?, dtactivetime=?, strlocation=?,strAroundShopIds=?, strproducer=?, strtype=?, " +
            		"strresolution=?, strresolution2=?, strresolution3=? where strid=? ";
            db.prepareStatement(strSql);
            db.setString(1, strNo);
            db.setString(2, com.ejoysoft.common.Format.getStrDate(dtActiveTime));   
            db.setString(3, strLocation);
            db.setString(4, this.getAroundShopIds(strAroundShops));
            db.setString(5, strProducer);
            db.setString(6, strType);
            db.setString(7, strResolution);
            db.setString(8, strResolution2);
            db.setString(9, strResolution3);
            db.setString(10, strId);
            db.executeUpdate();
            Globa.logger0("更新终端信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("更行终端信息异常" + e);
            return false;
        }
    }

    //查询
    public Terminal show(String where) {
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
    //获取临近商家ids
    public String getAroundShopIds(String strAroundShops)
    {
        String shopids ="";
    	if(strAroundShops!=null&& strAroundShops.trim()!="")
        {
       	    Shop obj0 = new Shop(globa,false);
            String shopnames[]= strAroundShops.split("，");             
            for(int i=0;i<shopnames.length;i++)
            {
           	 Shop obj = new Shop();
           	 System.out.println("where strbizname='"+((shopnames[i].split("-"))[0]).trim()+"' and strshopname='"+((shopnames[i].split("-"))[1]).trim()+"'");
             obj=obj0.show("where strbizname='"+((shopnames[i].split("-"))[0]).trim()+"' and strshopname='"+((shopnames[i].split("-"))[1]).trim()+"'");
           	 if(obj!=null)
           	 {
           		 shopids +=obj.getStrId();
           		 if(i<shopnames.length-1)
           			 shopids +=",";
           	 }
            }
           
         }
    	return shopids;
    }
    //封装终端信息结果集
    public Terminal load(ResultSet rs, boolean isView) {
    	Terminal theBean = new Terminal();
        try {
        	 theBean.setStrId(rs.getString("strid"));
             theBean.setStrNo(rs.getString("strno"));
             theBean.setDtActiveTime(rs.getString("dtactivetime"));
             theBean.setStrLocation(rs.getString("strlocation"));
             theBean.setStrProducer(rs.getString("strproducer"));
             theBean.setStrType(rs.getString("strtype"));
             theBean.setStrResolution(rs.getString("strresolution"));
             theBean.setStrResolution2(rs.getString("strresolution2"));
             theBean.setStrResolution3(rs.getString("strresolution3"));
             theBean.setIntState(rs.getInt("intstate"));
             if(rs.getInt("intstate")==-1)
            	 theBean.setStateString("异常");
             else 
            	 theBean.setStateString("正常");
			 theBean.setIntState(rs.getInt("intstate"));
             theBean.setDtRefreshTime(rs.getString("dtrefreshtime"));
             theBean.setStrCreator(rs.getString("strcreator"));
             theBean.setDtCreateTime(rs.getString("dtcreatetime"));
             //获取临近商家名称
             String shopids = rs.getString("straroundshopids");
             theBean.setStrAroundShopIds(shopids);
             if(shopids!=null&& shopids.trim()!="")
             {
            	 Shop obj0 = new Shop(globa);
                 String shopnames =" ";
                 String shops[]= shopids.split(",");             
                 for(int i=0;i<shops.length;i++)
                 {
                	 Shop obj = new Shop();
                	 obj=obj0.show("where strid='"+shops[i]+"'");
                	 if(obj!=null)
                	 {
                		 shopnames +=obj.getStrBizName()+"-"+obj.getStrShopName();
                		 if(i<shops.length-1)
                			 shopnames +="，";
                	 }
                 }
                 theBean.setStrAroundShops(shopnames);
             }
        	 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }


    //封装结果集2
    public Terminal load2(ResultSet rs, boolean isView) {
    	Terminal theBean = new Terminal();
        try {
        	 theBean.setStrId(rs.getString("strid"));
             theBean.setStrNo(rs.getString("strno"));
             theBean.setDtActiveTime(rs.getString("dtactivetime"));
             theBean.setStrLocation(rs.getString("strlocation"));
             theBean.setStrProducer(rs.getString("strproducer"));
             theBean.setStrType(rs.getString("strtype"));
             theBean.setStrResolution(rs.getString("strresolution"));
             theBean.setStrResolution2(rs.getString("strresolution2"));
             theBean.setStrResolution3(rs.getString("strresolution3"));
             theBean.setIntState(rs.getInt("intstate"));
             if(rs.getInt("intstate")==-1)
            	 theBean.setStateString("异常");
             else 
            	 theBean.setStateString("正常");
			 theBean.setIntState(rs.getInt("intstate"));
             theBean.setDtRefreshTime(rs.getString("dtrefreshtime"));
             theBean.setStrCreator(rs.getString("strcreator"));
             theBean.setDtCreateTime(rs.getString("dtcreatetime"));
             //获取临近商家名称
             String shopids = rs.getString("straroundshopids");
             theBean.setStrAroundShopIds(shopids);
             if(shopids!=null&& shopids.trim()!="")
             {
            	 Shop obj0 = new Shop(globa);
                 String shopnames =" ";
                 String shops[]= shopids.split(",");             
                 for(int i=0;i<shops.length;i++)
                 {
                	 Shop obj = new Shop();
                	 obj=obj0.show("where strid='"+shops[i]+"'");
                	 if(obj!=null)
                	 {
                		 shopnames +=obj.getStrBizName()+"-"+obj.getStrShopName();
                		 if(i<shops.length-1)
                			 shopnames +="，";
                	 }
                 }
                 theBean.setStrAroundShops(shopnames);
             }
        	 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //查询全部记录
      public Vector<Terminal> jionlist(String where, int startRow, int rowCount) {
          Vector<Terminal>  beans = new Vector<Terminal>();
          try {
              String sql = "select * from "+strTableName;
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
                  	Terminal theBean = new Terminal();
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
    //分页整理
    public Vector<Terminal> list(String where, int startRow, int rowCount) {
        Vector<Terminal> beans = new Vector<Terminal>();
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
                	Terminal theBean = new Terminal();
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

   
    //�����Լ��޸�
    public boolean selfUpdate(String tStrUserId) {
        try {

			    String strSql = "update  " + strTableName + "  set strbizbame=?, strTerminalname=?, strtrade=?, straddr=?, strphone=?, " +
			    		"strperson=?, strintro=?, strsmallimg=?,strlargeimg=?,intpoint=?, strcreator=?, strcreatetime=?  where strid=? ";
			    db.prepareStatement(strSql);
			    db.setString(1, strNo);
	            db.setString(2, dtActiveTime);   //strPWD
	            db.setString(3, strLocation);
	            db.setString(4, strAroundShopIds);
	            db.setString(5, strType);
	            db.setString(6, strResolution);
	            db.setString(7, strResolution2);
	            db.setString(8, strResolution3);
	            db.setInt(9, intState);
	            db.setString(10, dtRefreshTime);
	            db.setString(11, strCreator);
	            db.setString(12, com.ejoysoft.common.Format.getDateTime());
	            db.setString(13, strId);
	            db.executeUpdate();
			    Globa.logger0("�޸��û���Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
			    return true;
			} catch (Exception e) {
			    System.out.println("�޸��û���Ϣʱ��?" + e);
			    return false;
			}
    }

    //查询记录数
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

    private String strId;//终端信息Id
    private String strNo;//终端编号
    private String dtActiveTime;//启用时间
    private String strLocation;//地点
    private String strAroundShopIds;//临近终端
    private String strAroundShops;//临近终端
    private String strProducer;//生产厂家
    private String strType;//规格型号
    private String strResolution;//主屏分辨率
    private String strResolution2;//主屏分辨率2
    private String strResolution3;//主屏分辨率3
    private int intState;//状态
    private String stateString ;//默认状态显示正常
    private String dtRefreshTime;//状态更新时间
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

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getDtActiveTime() {
		return dtActiveTime;
	}

	public void setDtActiveTime(String dtActiveTime) {
		this.dtActiveTime = dtActiveTime;
	}

	public String getStrLocation() {
		return strLocation;
	}

	public void setStrLocation(String strLocation) {
		this.strLocation = strLocation;
	}

	public String getStrAroundShopIds() {
		return strAroundShopIds;
	}

	public void setStrAroundShopIds(String strAroundShopIds) {
		this.strAroundShopIds = strAroundShopIds;
	}

	public String getStrAroundShops() {
		return strAroundShops;
	}

	public void setStrAroundShops(String strAroundShops) {
		this.strAroundShops = strAroundShops;
	}

	public String getStrProducer() {
		return strProducer;
	}

	public void setStrProducer(String strProducer) {
		this.strProducer = strProducer;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrResolution() {
		return strResolution;
	}

	public void setStrResolution(String strResolution) {
		this.strResolution = strResolution;
	}

	public String getStrResolution2() {
		return strResolution2;
	}

	public void setStrResolution2(String strResolution2) {
		this.strResolution2 = strResolution2;
	}

	public String getStrResolution3() {
		return strResolution3;
	}

	public void setStrResolution3(String strResolution3) {
		this.strResolution3 = strResolution3;
	}

	public int getIntState() {
		return intState;
	}

	public void setIntState(int intState) {
		this.intState = intState;
	}

	public String getStateString() {
		return stateString;
	}

	public void setStateString(String stateString) {
		this.stateString = stateString;
	}

	public String getDtRefreshTime() {
		return dtRefreshTime;
	}

	public void setDtRefreshTime(String dtRefreshTime) {
		this.dtRefreshTime = dtRefreshTime;
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
