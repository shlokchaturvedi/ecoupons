package com.ejoysoft.ecoupons.business;

import com.ejoysoft.auth.MD5;
import com.ejoysoft.common.*;
import com.ejoysoft.common.exception.UserUnitIdException;

//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
import java.util.Vector;
//import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

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
    String strTableName2 = "t_bn_lineofbn";

    //�ж��û���Ϣ�Ƿ���?
    public void bCheckAccount(String tStrAccount) throws UserUnitIdException, SQLException {
        String strSql = "select strUserId  from " + strTableName + "   where strUserId='" + tStrAccount + "'";
        try {
            ResultSet rs = db.executeQuery(strSql);
            if (rs.next()) {
                globa.closeCon();
                throw new UserUnitIdException("�Ѿ�����'" + tStrAccount + "' �û�", "�����������û���");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加商家信息
    public boolean add() {
    	System.out.println("Shop.add()");
        String strSql = "";
        strId = UID.getID();
        try {
            //添加商家信息
            strSql = "insert into " + strTableName + "  (strid, strbizbame, strshopname, srtrade, straddr, strphone, " +
            		"strperson, strintro, strsmallimg,strlargeimg,intpoint, strcreator, strcreatetime" +
                    " alues(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db.prepareStatement(strSql);
            db.setString(1, strId);
            db.setString(2, strBizName);
            db.setString(3, strShopName);   //strPWD
            db.setString(4, strTrade);
            db.setString(5, strAddr);
            db.setString(6, strPhone);
            db.setString(7, strPerson);
            db.setString(8, strSmallImg);
            db.setString(9, strLargeImg);
            db.setInt(10, intPoint);
            db.setString(11, strCreator);
            db.setString(12, com.ejoysoft.common.Format.getDateTime());
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

    public boolean delete(String where) {
        try {
            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
            db.executeUpdate(sql);
            //ɾ���û�ӳ����?
//            delUnitUser(where);
            Globa.logger0("删除商家信息", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
            return true;
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    //�޸�
    public boolean update(String tStrUserId) {
        try {
            String strSql = "update  " + strTableName + "  set strbizbame=?, strshopname=?, strtrade=?, straddr=?, strphone=?, " +
            		"strperson=?, strintro=?, " ;
            if (this.strSmallImg.length() > 0) {
            	strSql += "strSmallImg = '" + strSmallImg + "',";
            }
            if (this.strLargeImg.length() > 0) {
            	strSql += "strLargeImage = '" + strLargeImg + "',";
            }
            strSql += ",intpoint=?, strcreator=?, strcreatetime=?  where strid=? ";
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
            db.setString(13,strId);
            db.executeUpdate();
            Globa.logger0("�޸��û���Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            System.out.println("�޸��û���Ϣʱ��?" + e);
            return false;
        }
    }

    //��ϸ��ʾ������?
    public Shop show(String where) {
        try {
            String strSql = "select * from  " + strTableName2 + "  ".concat(where);
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
    public Shop load(ResultSet rs, boolean isView) {
    	Shop theBean = new Shop();
        try {
        	theBean.setStrId(rs.getString(1));
            theBean.setStrBizName(rs.getString(2));
            theBean.setStrShopName(rs.getString(3));
            theBean.setStrTrade(rs.getString(4));
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

    //��¼��ת��Ϊ����
    public Shop load2(ResultSet rs, boolean isView) {
    	Shop theBean = new Shop();
        try {
            theBean.setStrId(rs.getString("strid"));
            theBean.setStrBizName(rs.getString("strbizname"));
            theBean.setStrShopName(rs.getString("strshopname"));
            theBean.setStrTrade(rs.getString("strtrade"));
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

    //�б��¼��?
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

  
    //�û� ��֤
    public boolean authUser(String oldPwd) {
        String pwd = (new MD5().getMD5ofStr(oldPwd));
        String strSql = "SELECT  *  FROM  " + strTableName + "  WHERE strUserId='" + globa.loginName + "' and strPWD='" + pwd + "'";
        try {
            ResultSet rs = db.executeQuery(strSql);
            if (rs != null && rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //����û�ID��ʾ�û���
    public String ReturnName(String struserid) {
        String sql = "SELECT comName FROM " + strTableName + " WHERE strUserId=?";
        try {
            db.prepareStatement(sql);
            db.setString(1, struserid);
            ResultSet rs = db.executeQuery();
            if (rs.next())
                return rs.getString("comName");
            rs.close();
        } catch (Exception e) {
            System.out.println("��ʾ�û������?:" + e);
            return "";
        }
        return "";
    }

    //�޸�����
 /*   public boolean doSetPwd(String tStrUserId, ServletContext application, HttpSession session) {
        try {
            String sql = "UPDATE " + strTableName + " SET strPWD=?,intState=? ,intError=0 WHERE  strUserId=? ";
            db.prepareStatement(sql);
            db.setString(1, MD5.getMD5ofString(strPWD));
            db.setInt(2, Constants.U_STATE_ON);
            db.setString(3, tStrUserId);
            db.executeUpdate();
            //�޸��û���������
            Globa.logger0("�޸��û�����", globa.loginName, globa.loginIp, sql, "�û�����", globa.userSession.getStrDepart());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

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

    //��ѯ�������ļ�¼����
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
     * ����������е��û�?
     */
    public Shop[] getShops(String twhere) {
        String strSql;
        try {
            strSql = "select strUserId,strMobile,comName from " + strTableName + "  ".concat(twhere);
            ResultSet rs = db.executeRollQuery(strSql);
            if (rs.last()) {
                int total = rs.getRow();
                rs.beforeFirst();
                Shop[] users = new Shop[total];
                int i = 0;
                while (rs.next()) {
                    users[i] = new Shop(rs.getString("strUserId"), rs.getString("strMobile"), rs.getString("comName"));
                    i++;
                }
                rs.close();
                return users;
            } else {
                rs.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * ����
     */
    public Shop(String userId,String mobile, String name) {
        this.strId = userId;
        this.strPhone = mobile;
        this.strBizName = name;
    }

    private String strId;//商家信息Id
    private String strBizName;//商家名称
    private String strShopName;//分部名称
    private String strTrade;//所属行业
    private String strAddr;//地址
    private String strPhone;//联系电话
    private String strPerson;//联系人
    private String strIntro;//简介
    private String strSmallImg;//小图
    private String strLargeImg;//大图
    private int intPoint;//积分余额
    private String strCreator;//创建人
    private String dtCreateTime;//创建时间
    private int intLoginNum;//
    private String dLatestLoginTime;//
    private float fOnlineTime;//
    private int intSort;//
    private String strLinkAdd;
    private String dCreatDate;//
    private String strOldUnitId;//
    private int intOldSort;//
    private int intUserType;//
    private int intState;

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

	public int getIntLoginNum() {
		return intLoginNum;
	}

	public void setIntLoginNum(int intLoginNum) {
		this.intLoginNum = intLoginNum;
	}

	public String getdLatestLoginTime() {
		return dLatestLoginTime;
	}

	public void setdLatestLoginTime(String dLatestLoginTime) {
		this.dLatestLoginTime = dLatestLoginTime;
	}

	public float getfOnlineTime() {
		return fOnlineTime;
	}

	public void setfOnlineTime(float fOnlineTime) {
		this.fOnlineTime = fOnlineTime;
	}

	public int getIntSort() {
		return intSort;
	}

	public void setIntSort(int intSort) {
		this.intSort = intSort;
	}

	public String getStrLinkAdd() {
		return strLinkAdd;
	}

	public void setStrLinkAdd(String strLinkAdd) {
		this.strLinkAdd = strLinkAdd;
	}

	public String getdCreatDate() {
		return dCreatDate;
	}

	public void setdCreatDate(String dCreatDate) {
		this.dCreatDate = dCreatDate;
	}

	public String getStrOldUnitId() {
		return strOldUnitId;
	}

	public void setStrOldUnitId(String strOldUnitId) {
		this.strOldUnitId = strOldUnitId;
	}

	public int getIntOldSort() {
		return intOldSort;
	}

	public void setIntOldSort(int intOldSort) {
		this.intOldSort = intOldSort;
	}

	public int getIntUserType() {
		return intUserType;
	}

	public void setIntUserType(int intUserType) {
		this.intUserType = intUserType;
	}

	public int getIntState() {
		return intState;
	}

	public void setIntState(int intState) {
		this.intState = intState;
	}
    

    
}
