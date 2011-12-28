package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.ejoysoft.ecoupons.system.SysPara;
import com.ejoysoft.ecoupons.system.Unit;

//import javax.servlet.ServletContext;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: feiwj
 * Date: 2005-9-1
 * Time: 9:12:43
 * To change this template use Options | File Templates.
 */
public class Shop {
    private Globa globa;
    private DbConnect db;

    //���췽��
    public Shop() {
    }

    public Shop(Globa globa) {
        this.globa = globa;
        db = globa.db;
    }

    //���췽��
    public Shop(Globa globa, boolean b) {
        this.globa = globa;
        db = globa.db;
        if (b) globa.setDynamicProperty(this);
    }

    String strTableName = "t_bz_shop";
    String strTableName2 = "t_bz_coupon";
    String strTableName3 = "t_bz_coupon_input";
    String strTableName4 = "t_bz_point_buy";
    String strTableName5 = "t_bz_point_present";

    //添加商家信息
    public boolean add() {
        String strSql = "";
        strId = UID.getID();
        try {
        	System.out.println("insert into " + strTableName + "  (strid, strbizbame, strshopname, strtrade, straddr, strphone, " +
            		"strperson, strintro, strsmallimg,strlargeimg,intpoint, strcreator, dtcreatetime" +
                    " values("+strId+","+strBizName+","+strShopName+","+strTrade+","+strAddr+","+strPhone+","+
                    strPerson+","+strIntro+","+strSmallImg+","+strLargeImg+","+intPoint+","+strCreator+","+ com.ejoysoft.common.Format.getDateTime()+")");
            //添加商家信息
            strSql = "insert into " + strTableName + "  (strid, strbizname, strshopname, strtrade, straddr, strphone, " +
            		"strperson, strintro, strsmallimg,strlargeimg,intpoint, strcreator, dtcreatetime" +
                    ") values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strBizName);
            db.setString(3, strShopName);   //strPWD
            db.setString(4, strTrade);
            db.setString(5, strAddr);
            db.setString(6, strPhone);
            db.setString(7, strPerson);
            db.setString(8, strIntro);
            db.setString(9, strSmallImg);
            db.setString(10, strLargeImg);
            db.setInt(11, intPoint);
            db.setString(12, strCreator);
            db.setString(13, com.ejoysoft.common.Format.getDateTime());
            if (db.executeUpdate() > 0) {
                Globa.logger0("添加商家信息", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.out.println("添加商家信息异常");
            e.printStackTrace();
            return false;
        }
    }
   //删除商家信息
    public boolean delete(String where,String where2,String strid) {
    	String sql1 = "delete from " + strTableName + "  ".concat(where);
    	String sql2 = "delete from " + strTableName2 + "  ".concat(where2);
    	String sql3 = "delete from " + strTableName3 + "  ".concat(where2);
    	String sql4 = "delete from " + strTableName4 + "  ".concat(where2);
    	String sql5 = "delete from " + strTableName5 + "  ".concat(where2);
    	//事务处理
    	try {
        	db.getConnection().setAutoCommit(false);//禁止自动提交事务        
        	db.getConnection().setSavepoint();
            db.executeUpdate(sql1);//删除商家
            db.executeUpdate(sql2);//删除商家的优惠券
            db.executeUpdate(sql3);//删除商家录入有价券记录
            db.executeUpdate(sql4);//删除商家购买积分记录
            db.executeUpdate(sql5);//删除商家转赠积分记录
            deleteShopIdFromIds(strid);//删除终端表中对应商家id
            db.getConnection().commit(); //统一提交
            Globa.logger0("删除商家信息", globa.loginName, globa.loginIp, sql1, "商家管理", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        } 
    }

    //从终端的临近商家id中删除指定商家id
    public void deleteShopIdFromIds(String shopid)
    {
        Terminal obj = new Terminal(globa);
    	String sql ="select * from t_bz_terminal where straroundshopids like '%"+shopid+"%'";
        ResultSet rs = db.executeQuery(sql);
    	try {
			if(rs!=null && rs.next())
			{
				do {
					String dealre1 = rs.getString("strid");
					String dealre2 = rs.getString("straroundshopids");
					String array[]=dealre2.split(",");
					String shopids=" ";
				    if(array!=null && !shopid.equals(dealre2))
					{
						for(int i=0;i<array.length;i++)
						{
							if(!array[i].trim().equals(shopid))
							{
								shopids += array[i].trim()+",";
							}
						}
						shopids = shopids.trim().substring(0,shopids.length()-2);	
					}	
					String sql2 = "update "+obj.strTableName+" set straroundshopids='"+shopids+"' where strid='"+dealre1+"'";
					db.executeUpdate(sql2);
				} while (rs.next());
				System.out.println("Shop.deleteShopIdFromIds()");
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }

    //商家更新信息
    public boolean update(String tStrUserId) {
        try {
            String strSql = "update " + strTableName + "  set strbizname=?, strshopname=?, strtrade=?, straddr=?, strphone=?, " +
            		"strperson=?, strintro=?, " ;
            if (this.strSmallImg!=null&&this.strSmallImg.length() > 0) {
            	strSql += "strsmallimg = '" + strSmallImg + "',";
            }
            if (this.strLargeImg!=null&&this.strLargeImg.length() > 0) {
            	strSql += "strlargeimg = '" + strLargeImg + "',";
            }
            strSql += " intpoint=? where strid=? ";
            db.prepareStatement(strSql);
            db.setString(1, strBizName);
            db.setString(2, strShopName);
            db.setString(3, strTrade);
            db.setString(4, strAddr);
            db.setString(5, strPhone);
            db.setString(6, strPerson);
            db.setString(7, strIntro);      //"strUnitCode
            db.setInt(8, intPoint);
            db.setString(9,strId);
            db.executeUpdate();
            Globa.logger0("更新商家信息", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("更行商家信息异常" + e);
            return false;
        }
    }

    //查询
    public Shop show(String where) {
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
    } //查询
    
    //获取所有商家名称和分部名称
    public Vector<Shop> getShopNames(String where) {
    	Vector<Shop> vector = new Vector<Shop>();
        try {
            String strSql = "select * from  " + strTableName ;
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next())
            {
            	Shop theBean = new Shop();
                theBean = load3(rs, false);
                vector.addElement(theBean);
                return vector;
            }
            else
                return null;
        } catch (Exception ee) {
            return null;
        }
    }
    
    //封装商家信息结果集
    public Shop load3(ResultSet rs, boolean isView) {
    	Shop theBean = new Shop();
        try {
            theBean.setStrBizName(rs.getString(2));
            theBean.setStrShopName(rs.getString(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

  //封装商家信息结果集
    public Shop load(ResultSet rs, boolean isView) {
    	Shop theBean = new Shop();
        try {
        	theBean.setStrId(rs.getString(1));
            theBean.setStrBizName(rs.getString(2));
            theBean.setStrShopName(rs.getString(3));
            theBean.setStrTrade(rs.getString(4));
            theBean.setStrTradeName(SysPara.getNameById(rs.getString(4)));
            theBean.setStrAddr(rs.getString(5));
            theBean.setStrPhone(rs.getString(6));
            theBean.setStrPerson(rs.getString(7));
            theBean.setStrIntro(rs.getString(8));
            theBean.setStrSmallImg(rs.getString(9));
            theBean.setStrLargeImg(rs.getString(10));
            theBean.setIntPoint(rs.getInt(11));
            theBean.setStrCreator(rs.getString(12));
            theBean.setDtCreateTime(rs.getString(13));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

  //查询全部记录
    public Vector<Shop> jionlist(String where, int startRow, int rowCount) {
        Vector<Shop>  beans = new Vector<Shop>();
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
                	Shop theBean = new Shop();
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

    //封装结果集2
    public Shop load2(ResultSet rs, boolean isView) {
    	Shop theBean = new Shop();
        try {
            theBean.setStrId(rs.getString("strid"));
            theBean.setStrBizName(rs.getString("strbizname"));
            theBean.setStrShopName(rs.getString("strshopname"));
            theBean.setStrTrade(rs.getString("strtrade"));
            theBean.setStrTradeName(SysPara.getNameById(rs.getString("strtrade")));
            theBean.setStrAddr(rs.getString("strAddr"));
            theBean.setStrPhone(rs.getString("strphone"));
            theBean.setStrPerson(rs.getString("strperson"));
            theBean.setStrIntro(rs.getString("strintro"));
            theBean.setStrSmallImg(rs.getString("strsmallimg"));
            theBean.setStrLargeImg(rs.getString("strlargeimg"));
            theBean.setIntPoint(rs.getInt("intpoint"));
            theBean.setStrCreator(rs.getString("strcreator"));
            theBean.setDtCreateTime(rs.getString("dtcreatetime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theBean;
    }

    //分页整理
    public Vector<Shop> list(String where, int startRow, int rowCount) {
        Vector<Shop> beans = new Vector<Shop>();
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
                	Shop theBean = new Shop();
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

   
    //�����Լ��޸�
    public boolean selfUpdate(String tStrUserId) {
        try {

			    String strSql = "update  " + strTableName + "  set strbizbame=?, strshopname=?, strtrade=?, straddr=?, strphone=?, " +
			    		"strperson=?, strintro=?, strsmallimg=?,strlargeimg=?,intpoint=?, strcreator=?, strcreatetime=?  where strid=? ";
			    db.prepareStatement(strSql);
			    db.setString(1, strBizName);
			    db.setString(2, strShopName);
			    db.setString(3, strTrade);
			    db.setString(4, strAddr);
			    db.setString(5, strPhone);
			    db.setString(6, strPerson);
			    db.setString(7, strIntro);
			    db.setString(8, strSmallImg);
			    db.setString(9, strLargeImg);       //"strUnitCode
			    db.setInt(10, intPoint);
			    db.setString(11, strCreator);
			    db.setString(12,com.ejoysoft.common.Format.getDateTime());
			    db.executeUpdate();
			    Globa.logger0("�޸��û���Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
			    return true;
			} catch (Exception e) {
			    System.out.println("�޸��û���Ϣʱ��?" + e);
			    return false;
			}
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
    //取出所有的shop对象
    public Vector allShop(String where) {
    	Vector<Shop>  beans = new Vector<Shop>();
        try {
        	
            String strSql = "select * from  " + strTableName + "  ".concat(where);
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next()){               
                do {
                	Shop theBean = new Shop();
                    theBean = load(rs, false);
                    beans.addElement(theBean);
                } while (rs.next());
            }
            rs.close();
           
        }  catch (Exception ee) {
            ee.printStackTrace();
        }
        return beans;
    }
//获取所有商家名称和分部名称
    public String[] getAllShopNames()
    { 
    	
        Shop obj = new Shop(globa);
    	int i=0;
    	String shopnames[]=new String[obj.getCount("")];
        try {
        	
            String strSql = "select * from  " + strTableName+" order by strbizname" ;
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next()){               
                do {
                	shopnames[i]=rs.getString("strbizname")+"-"+rs.getString("strshopname")+" ";
                	i++;
                } while (rs.next());
            }
            rs.close();
           
        }  catch (Exception ee) {
            ee.printStackTrace();
        }
        return shopnames;
    }
    //获取商家名（无重复）
    public String[] getStrBizNames()
	{
    	int k=0;
    	String strSql = "select distinct strbizname from "+ strTableName+" order by strbizname" ;
    	ResultSet rs = db.executeQuery(strSql);
    	try {    	
    		
    		if (rs != null && rs.next()){               
            do {	k++;
			} while (rs.next());
		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	String allbizname[] = new String[k];
		try { 
		String strSql1 = "select distinct strbizname from "+ strTableName+" order by strbizname" ;
    	ResultSet re = db.executeQuery(strSql1);
    	int num =0;
    	if (re != null && re.next()){               
            do {
					allbizname[num] = re.getString("strbizname");
					num++;
				} while (re.next());
			} 
	    }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return allbizname;
    }
    
    private String strId;//商家信息Id
    private String strBizName;//商家名称
    private String strShopName;//分部名称
    private String strTrade;//所属行业
    private String strTradeName;//所属行业
    private String strAddr;//地址
    private String strPhone;//联系电话
    private String strPerson;//联系人
    private String strIntro;//简介
    private String strSmallImg;//小图
    private String strLargeImg;//大图
    private int intPoint;//积分余额
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

	public String getStrBizName() {
		return strBizName;
	}

	public void setStrBizName(String strBizName) {
		this.strBizName = strBizName;
	}

	public String getStrShopName() {
		return strShopName;
	}

	public void setStrShopName(String strShopName) {
		this.strShopName = strShopName;
	}

	public String getStrTrade() {
		return strTrade;
	}

	public void setStrTrade(String strTrade) {
		this.strTrade = strTrade;
	}

	public String getStrTradeName() {
		return strTradeName;
	}

	public void setStrTradeName(String strTradeName) {
		this.strTradeName = strTradeName;
	}

	public String getStrAddr() {
		return strAddr;
	}

	public void setStrAddr(String strAddr) {
		this.strAddr = strAddr;
	}

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getStrPerson() {
		return strPerson;
	}

	public void setStrPerson(String strPerson) {
		this.strPerson = strPerson;
	}

	public String getStrIntro() {
		return strIntro;
	}

	public void setStrIntro(String strIntro) {
		this.strIntro = strIntro;
	}

	public String getStrSmallImg() {
		return strSmallImg;
	}

	public void setStrSmallImg(String strSmallImg) {
		this.strSmallImg = strSmallImg;
	}

	public String getStrLargeImg() {
		return strLargeImg;
	}

	public void setStrLargeImg(String strLargeImg) {
		this.strLargeImg = strLargeImg;
	}

	public int getIntPoint() {
		return intPoint;
	}

	public void setIntPoint(int intPoint) {
		this.intPoint = intPoint;
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
