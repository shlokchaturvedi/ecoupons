package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.ejoysoft.ecoupons.system.SysPara;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA. User: feiwj Date: 2005-9-1 Time: 9:12:43 To change
 * this template use Options | File Templates.
 */
public class Shop
{
	private Globa globa;
	private DbConnect db;

	public Shop()
	{
	}

	public Shop(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Shop(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	String strTableName = "t_bz_shop";
	String strTableName2 = "t_bz_coupon";
	String strTableName3 = "t_bz_coupon_input";
	String strTableName4 = "t_bz_point_buy";
	String strTableName5 = "t_bz_point_present";
	String strTableName6 = "t_sy_user";
	String strTableName7 = "t_bz_download_alert";
	String strPrintTable = "t_bz_coupon_print";

	// 添加商家信息
	public boolean add()
	{
		String strSql = "";
		strId = UID.getID(); // 添加商家信息
		strSql = "insert into " + strTableName + "  (strid, strbizname, strshopname, strtrade, straddr, strphone, "
				+ "strperson,inttype, strintro, strsmallimg,strlargeimg,intpoint, strcreator, dtcreatetime,intsort" + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{

			db.getConnection().setAutoCommit(false);// 禁止自动提交事务
			Terminal obj = new Terminal(globa);
			String[] strTerminalId = obj.getAllTerminalNos();
			if (strTerminalId != null)
			{
				for (int i = 0; i < strTerminalId.length; i++)
				{
					String[] strid = strTerminalId[i].split("-");
					String strsql2 = "insert into " + strTableName7 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
							+ "values (" + UID.getID() + ",'" + strid[0] + "','" + strTableName + "','" + strId + "','add',0)";
					db.executeUpdate(strsql2);
				}
			}
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strBizName);
			db.setString(3, strShopName); // strPWD
			db.setString(4, strTrade);
			db.setString(5, strAddr);
			db.setString(6, strPhone);
			db.setString(7, strPerson);
			db.setInt(8, intType);
			db.setString(9, strIntro);
			db.setString(10, strSmallImg);
			db.setString(11, strLargeImg);
			db.setInt(12, intPoint);
			db.setString(13, strCreator);
			db.setString(14, com.ejoysoft.common.Format.getDateTime());
			db.setInt(15, intSort);
			if (db.executeUpdate() > 0)
			{
				db.getConnection().commit(); // 统一提交
				db.setAutoCommit(true);
				Globa.logger0("添加商家信息", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加商家信息异常");
			e.printStackTrace();
			return false;
		}
	}

	// 删除商家信息
	public boolean delete(String where, String where2, String strid)
	{
		String sql1 = "delete from " + strTableName + "  ".concat(where);
		String sql3 = "delete from " + strTableName3 + "  ".concat(where2);
		String sql4 = "delete from " + strTableName4 + "  ".concat(where2);
		String sql5 = "delete from " + strTableName5 + "  ".concat(where2);
		String sql6 = "delete from " + strTableName6 + "  ".concat(where2);
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		Coupon coupon = new Coupon(globa);
		Vector<Coupon> vctCoupons = coupon.listByShopId(" where strshopid='" + strid + "'");DownLoadAlert alert = new DownLoadAlert(globa);
    	Vector<DownLoadAlert> vctAlerts = alert.list(" where strdataid='"+strid+"'",0,0);
  		// 事务处理
		try
		{
			db.getConnection().setAutoCommit(false);// 禁止自动提交事务
			if(vctAlerts.size()==0)
			for(int i=0;i<vctAlerts.size();i++)
        	{
        		DownLoadAlert alert2 = vctAlerts.get(i);
        	    String sql = "delete from "+strTableName7+" where strid='"+alert2.getStrId()+"' and (intstate=0 or intstate=2)";
    			db.executeUpdate(sql);
    		}			         		
 		    Terminal obj = new Terminal(globa);
			String[] strTerminalId = obj.getAllTerminalNos();
			if (strTerminalId != null)
			{
				for (int i = 0; i < strTerminalId.length; i++)
				{
					String[] terminalid = strTerminalId[i].split("-");
					String sql2 ="insert into " + strTableName7 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
		 		     + "values (" + UID.getID() + ",'" + terminalid[0]+ "','" + strTableName + "','" +strid + "','delete',0)";
		 		    db.executeUpdate(sql2);	
				}
			}
			// db.executeUpdate(sql2);//删除商家的优惠券
			for (int i = 0; i < vctCoupons.size(); i++)
			{
				String strTerminals = coupon.show("where strid='" + vctCoupons.get(i).getStrId() + "'").getStrTerminals();
				String sql = "delete from " + strTableName2 + "  where strId =" + vctCoupons.get(i).getStrId();
				db.executeUpdate(sql);
				String strSqlPrint = "delete from " + strPrintTable + " where strcouponid='" + strId + "'";
				db.executeUpdate(strSqlPrint);
				if (strTerminals != null && strTerminals != "")
				{
					String[] TerminalIds = coupon.getTerminalIdsByNames(strTerminals).split(",");
					for (int j = 0; j < TerminalIds.length; j++)
					{
						// 处理在终端刷新期间既出现增加又出现修改的情况
						String strSql2 = downLoadAlert.retStrSql(TerminalIds[j], vctCoupons.get(i).getStrId(), strTableName2);
						if (!strSql2.equals(""))
						{
							db.executeUpdate(strSql2);
						}
					}
				}
			}

			db.executeUpdate(sql1);// 删除商家
			db.executeUpdate(sql3);// 删除商家录入有价券记录
			db.executeUpdate(sql4);// 删除商家购买积分记录
			db.executeUpdate(sql5);// 删除商家转赠积分记录
			db.executeUpdate(sql6);// 删除用户表中商家信息
			deleteShopIdFromIds(strid);// 删除终端表中对应商家id
			db.getConnection().commit(); // 统一提交
			db.setAutoCommit(true);
			Globa.logger0("删除商家信息", globa.loginName, globa.loginIp, sql1, "商家管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	// 从终端的临近商家id中删除指定商家id
	public void deleteShopIdFromIds(String shopid)
	{
		Terminal obj = new Terminal(globa);
		String sql = "select * from t_bz_terminal where straroundshopids like '%" + shopid + "%'";
		ResultSet rs = db.executeQuery(sql);
		try
		{
			if (rs != null && rs.next())
			{
				do
				{
					String dealre1 = rs.getString("strid");
					String dealre2 = rs.getString("straroundshopids");
					String array[] = dealre2.split(",");
					String shopids = " ";
					if (array != null && !shopid.equals(dealre2))
					{
						for (int i = 0; i < array.length; i++)
						{
							if (!array[i].trim().equals(shopid))
							{
								shopids += array[i].trim() + ",";
							}
						}
						shopids = shopids.trim().substring(0, shopids.length() - 2);
					}
					String sql2 = "update " + obj.strTableName + " set straroundshopids='" + shopids + "' where strid='" + dealre1 + "'";
					db.executeUpdate(sql2);
				} while (rs.next());
				System.out.println("Shop.deleteShopIdFromIds()");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 商家更新信息
	public boolean update(String strId2)
	{
		try
		{

			String strSql = "update " + strTableName + "  set strbizname=?, strshopname=?, strtrade=?, straddr=?, strphone=?, "
					+ "strperson=?,inttype=?, strintro=?, ";
			if (this.strSmallImg != null && this.strSmallImg.length() > 0)
			{
				strSql += "strsmallimg = '" + strSmallImg + "',";
			}
			if (this.strLargeImg != null && this.strLargeImg.length() > 0)
			{
				strSql += "strlargeimg = '" + strLargeImg + "',";
			}
			strSql += " intpoint=?,intsort=? where strid=? ";
			db.getConnection().setAutoCommit(false);
			DownLoadAlert alert = new DownLoadAlert(globa);
	        Vector<DownLoadAlert> vctAlerts = alert.list(" where strdataid='"+strId2+"'",0,0);
	        for(int i=0;i<vctAlerts.size();i++)
	        {
	        	DownLoadAlert alert2 = vctAlerts.get(i);
        	    String sql = "delete from "+strTableName7+" where strid='"+alert2.getStrId()+"' and (intstate=0 or intstate=2)";
    			db.executeUpdate(sql);		
	        }
	        Terminal obj = new Terminal(globa);
			String[] strTerminalId = obj.getAllTerminalNos();
			if (strTerminalId != null)
			{
				for (int i = 0; i < strTerminalId.length; i++)
				{
					String[] terminalid = strTerminalId[i].split("-");
					String sql2 ="insert into " + strTableName7 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
		 		     + "values (" + UID.getID() + ",'" + terminalid[0]+ "','" + strTableName + "','" +strId2 + "','update',0)";
		 		    db.executeUpdate(sql2);	
				}
			}
			db.prepareStatement(strSql);
			db.setString(1, strBizName);
			db.setString(2, strShopName);
			db.setString(3, strTrade);
			db.setString(4, strAddr);
			db.setString(5, strPhone);
			db.setInt(7, intType);
			db.setString(6, strPerson);
			db.setString(8, strIntro); // "strUnitCode
			db.setInt(9, intPoint);
			db.setInt(10, intSort);
			db.setString(11, strId2);
			db.executeUpdate();
			db.commit();
			Globa.logger0("更新商家信息", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			db.rollback();
			System.out.println("更行商家信息异常" + e);
			return false;
		}
	}

	// 查询
	public Shop show(String where)
	{
		try
		{
			String strSql = "select * from  " + strTableName + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
				return load(rs, true);
			else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	} // 查询

	// 获取所有商家名称和分部名称
	public Vector<Shop> getShopNames(String where)
	{
		Vector<Shop> vector = new Vector<Shop>();
		try
		{
			String strSql = "select * from  " + strTableName;
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				Shop theBean = new Shop();
				theBean = load3(rs, false);
				vector.addElement(theBean);
				return vector;
			} else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	// 封装商家信息结果集
	public Shop load3(ResultSet rs, boolean isView)
	{
		Shop theBean = new Shop();
		try
		{
			theBean.setStrBizName(rs.getString(2));
			theBean.setStrShopName(rs.getString(3));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	// 封装商家信息结果集
	public Shop load(ResultSet rs, boolean isView)
	{
		Shop theBean = new Shop();
		try
		{
			theBean.setStrId(rs.getString("strid"));
			theBean.setStrBizName(rs.getString("strbizname"));
			theBean.setStrShopName(rs.getString("strshopname"));
			theBean.setStrTrade(rs.getString("strtrade"));
			theBean.setStrTradeName(SysPara.getNameById(rs.getString("strtrade")));
			theBean.setStrAddr(rs.getString("strAddr"));
			theBean.setStrPhone(rs.getString("strphone"));
			theBean.setStrPerson(rs.getString("strperson"));
			theBean.setIntType(rs.getInt("inttype"));
			theBean.setIntSort(rs.getInt("intSort"));
			if (rs.getInt("inttype") == 1)
				theBean.setIntTypeName("是");
			else if (rs.getInt("inttype") == 0)
				theBean.setIntTypeName("否");
			theBean.setStrIntro(rs.getString("strintro"));
			theBean.setStrSmallImg(rs.getString("strsmallimg"));
			theBean.setStrLargeImg(rs.getString("strlargeimg"));
			theBean.setIntPoint(rs.getInt("intpoint"));
			theBean.setStrCreator(rs.getString("strcreator"));
			theBean.setDtCreateTime(rs.getString("dtcreatetime"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	// 查询全部记录
	public Vector<Shop> jionlist(String where, int startRow, int rowCount)
	{
		Vector<Shop> beans = new Vector<Shop>();
		try
		{
			String sql = "select * from " + strTableName;
			if (where.length() > 0)
				sql = String.valueOf(sql) + String.valueOf(where);
			Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (startRow != 0 && rowCount != 0)
				s.setMaxRows((startRow + rowCount) - 1);
			ResultSet rs = s.executeQuery(sql);
			if (rs != null && rs.next())
			{
				if (startRow != 0 && rowCount != 0)
					rs.absolute(startRow);
				do
				{
					Shop theBean = new Shop();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
			rs.close();
			s.close();
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	// 分页整理
	public Vector<Shop> list(String where, int startRow, int rowCount)
	{
		Vector<Shop> beans = new Vector<Shop>();
		try
		{
			String sql = "SELECT *  FROM  " + strTableName + " ";
			if (where.length() > 0)
				sql = String.valueOf(sql) + String.valueOf(where);
			Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (startRow != 0 && rowCount != 0)
				s.setMaxRows((startRow + rowCount) - 1);
			ResultSet rs = s.executeQuery(sql);
			if (rs != null && rs.next())
			{
				if (startRow != 0 && rowCount != 0)
					rs.absolute(startRow);
				do
				{
					Shop theBean = new Shop();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
			rs.close();
			s.close();
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	// �����Լ��޸�
	public boolean selfUpdate(String tStrUserId)
	{
		try
		{

			String strSql = "update  " + strTableName + "  set strbizbame=?, strshopname=?, strtrade=?, straddr=?, strphone=?, "
					+ "strperson=?,inttype=?, strintro=?, strsmallimg=?,strlargeimg=?,intpoint=?, strcreator=?, strcreatetime=?,intsort=?  where strid=? ";
			db.prepareStatement(strSql);
			db.setString(1, strBizName);
			db.setString(2, strShopName);
			db.setString(3, strTrade);
			db.setString(4, strAddr);
			db.setString(5, strPhone);
			db.setString(6, strPerson);
			db.setString(7, strPerson);
			db.setString(8, strIntro);
			db.setString(9, strSmallImg);
			db.setString(10, strLargeImg); // "strUnitCode
			db.setInt(11, intPoint);
			db.setString(12, strCreator);
			db.setString(13, com.ejoysoft.common.Format.getDateTime());
			db.setInt(14, intSort);
			db.setString(15, strId);
			db.executeUpdate();
			Globa.logger0("�޸��û���Ϣ", globa.loginName, globa.loginIp, strSql, "�û�����", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("�޸��û���Ϣʱ��?" + e);
			return false;
		}
	}

	// 查询影响记录数
	public int getCount(String where)
	{
		int count = 0;
		try
		{
			String sql = "SELECT count(strId) FROM " + strTableName + "  ";
			if (where.length() > 0)
			{
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
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return count;
		}
	}

	// 取出所有的shop对象
	@SuppressWarnings("unchecked")
	public Vector allShop(String where)
	{
		Vector<Shop> beans = new Vector<Shop>();
		try
		{

			String strSql = "select * from  " + strTableName + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				do
				{
					Shop theBean = new Shop();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
			rs.close();

		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	// 获取所有商家名称和分部名称
	public String[] getAllShopNames()
	{

		Shop obj = new Shop(globa);
		int i = 0;
		String shopnames[] = new String[obj.getCount("")];
		try
		{

			String strSql = "select * from  " + strTableName + " order by strbizname";
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				do
				{	
					String bizname = rs.getString("strbizname");
				    String shopname = rs.getString("strshopname");
				    if(shopname !=null && !shopname.equals(""))  
				    {
				   		bizname = bizname+"-"+shopname;
				    }
					shopnames[i] = bizname;
					i++;
				} while (rs.next());
			}
			rs.close();

		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return shopnames;
	}
	// 获取所有商家名称和分部名称
	public String[] getAllShopIdsAndNames()
	{

		Shop obj = new Shop(globa);
		int i = 0;
		String shopidandnames[] = new String[obj.getCount("")];
		try
		{

			String strSql = "select * from  " + strTableName + " order by strbizname";
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				do
				{	
					String strid = rs.getString("strid");
					String bizname = rs.getString("strbizname");
				    String shopname = rs.getString("strshopname");				    
				    if(shopname !=null && !shopname.equals(""))  
				    {
				   		bizname = bizname+"-"+shopname;
				    }
					shopidandnames[i] =strid+","+bizname;
					i++;
				} while (rs.next());
			}
			rs.close();

		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return shopidandnames;
	}
		// 获取商家名（无重复）
	@SuppressWarnings("unchecked")
	public String[] getStrBizNames()
	{
		int k = 0;
		String strSql = "select distinct strbizname from " + strTableName + " order by strbizname";
		ResultSet rs = db.executeQuery(strSql);
		try
		{

			if (rs != null && rs.next())
			{
				do
				{
					k++;
				} while (rs.next());
			}
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String allbizname[] = new String[k];
		try
		{
			String strSql1 = "select distinct strbizname from " + strTableName + " order by strbizname";
			ResultSet re = db.executeQuery(strSql1);
			int num = 0;
			if (re != null && re.next())
			{
				do
				{
					allbizname[num] = re.getString("strbizname");
					num++;
				} while (re.next());
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//按拼音首字母排序
		Comparator comparator = Collator.getInstance(Locale.CHINA);
		Arrays.sort(allbizname,comparator);
		return allbizname;
	}

	/**
	 * 根据查询条件返回strbizname-strshopname或strbizname
	 */
	public String returnBizShopName(String where)
	{
		try
		{
			String strSql = "select * from  " + strTableName + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				Shop shopBean = new Shop();
				shopBean = load(rs, true);				
			    String bizname = shopBean.getStrBizName();
			    String shopname = shopBean.getStrShopName();;
			    if(shopname !=null && !shopname.equals(""))  
			    {
			   		bizname = bizname+"-"+shopname;
			    }
			  return bizname;
			} else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	/**
	 * 根据条件，返回商家名称集合
	 */
	public Vector<Shop> returnShopFullName()
	{
		Vector<Shop> beans = new Vector<Shop>();
		try
		{
			String sql = "select * FROM  " + strTableName + " ";
			ResultSet rs = db.executeQuery(sql);
			while (rs.next())
			{
				Shop theBean = new Shop();
				theBean = load(rs, false);
				beans.addElement(theBean);
			}
		} catch (Exception ee)
		{
			return null;
		}
		return beans;
	}

	/**
	 * 根据商家名 返回商家id
	 */
	public String getShopIdByName(String strName)
	{
		try
		{
			String sql = "select * FROM  " + strTableName + " ";
			if (!strName.equals(""))
			{

				String strbizname = "", strshopname = "", where = "";
				String name[] = strName.trim().split("-");
				if (name.length == 1)
				{
					strbizname = name[0];
					where = " where strbizname='" + strbizname + "'";
				} else
				{
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

	private String strId;// 商家信息Id
	private String strBizName;// 商家名称
	private String strShopName;// 分部名称
	private String strTrade;// 所属行业
	private String strTradeName;// 所属行业
	private String strAddr;// 地址
	private String strPhone;// 联系电话
	private String strPerson;// 联系人
	private String strIntro;// 简介
	private String strSmallImg;// 小图
	private String strLargeImg;// 大图
	private int intPoint;// 积分余额
	private int intType;// 商家推荐与否
	private String intTypeName;// 商家推荐与否
	private String strCreator;// 创建人
	private String dtCreateTime;// 创建时间
	private int intSort;//排序序号

	public Globa getGloba()
	{
		return globa;
	}

	public void setGloba(Globa globa)
	{
		this.globa = globa;
	}

	public DbConnect getDb()
	{
		return db;
	}

	public void setDb(DbConnect db)
	{
		this.db = db;
	}

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrBizName()
	{
		return strBizName;
	}

	public void setStrBizName(String strBizName)
	{
		this.strBizName = strBizName;
	}

	public String getStrShopName()
	{
		return strShopName;
	}

	public void setStrShopName(String strShopName)
	{
		this.strShopName = strShopName;
	}

	public String getStrTrade()
	{
		return strTrade;
	}

	public void setStrTrade(String strTrade)
	{
		this.strTrade = strTrade;
	}

	public String getStrTradeName()
	{
		return strTradeName;
	}

	public void setStrTradeName(String strTradeName)
	{
		this.strTradeName = strTradeName;
	}

	public String getStrAddr()
	{
		return strAddr;
	}

	public void setStrAddr(String strAddr)
	{
		this.strAddr = strAddr;
	}

	public String getStrPhone()
	{
		return strPhone;
	}

	public void setStrPhone(String strPhone)
	{
		this.strPhone = strPhone;
	}

	public String getStrPerson()
	{
		return strPerson;
	}

	public void setStrPerson(String strPerson)
	{
		this.strPerson = strPerson;
	}

	public String getStrIntro()
	{
		return strIntro;
	}

	public void setStrIntro(String strIntro)
	{
		this.strIntro = strIntro;
	}

	public String getStrSmallImg()
	{
		return strSmallImg;
	}

	public void setStrSmallImg(String strSmallImg)
	{
		this.strSmallImg = strSmallImg;
	}

	public String getStrLargeImg()
	{
		return strLargeImg;
	}

	public void setStrLargeImg(String strLargeImg)
	{
		this.strLargeImg = strLargeImg;
	}

	public int getIntPoint()
	{
		return intPoint;
	}

	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}

	public String getStrCreator()
	{
		return strCreator;
	}

	public void setStrCreator(String strCreator)
	{
		this.strCreator = strCreator;
	}

	public String getDtCreateTime()
	{
		if (dtCreateTime != null&&dtCreateTime.length()>3)
		{
			return dtCreateTime.substring(0, dtCreateTime.length()-2);

		} else
		{
			return null;
		}
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

	public int getIntType()
	{
		return intType;
	}

	public void setIntType(int intType)
	{
		this.intType = intType;
	}

	public String getIntTypeName()
	{
		return intTypeName;
	}

	public void setIntTypeName(String intTypeName)
	{
		this.intTypeName = intTypeName;
	}

	public int getIntSort()
	{
		return intSort;
	}

	public void setIntSort(int intSort)
	{
		this.intSort = intSort;
	}
}
