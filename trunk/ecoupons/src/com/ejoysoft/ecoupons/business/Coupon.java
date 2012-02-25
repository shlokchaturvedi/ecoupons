package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.sun.org.apache.bcel.internal.generic.Select;

public class Coupon
{
	private String strId;
	private String strName;
	private String dtActiveTime;
	private String dtExpireTime;
	private String strShopId;
	private String strTerminalIds;
	private int intVip;
	private int intRecommend;
	private float flaPrice;
	private int intPrintLimit;
	private int intPrint;
	private String strSmallImg;
	private String strLargeImg;
	private String strPrintImg;
	private String strCreator;
	private String dtCreateTime;
	private String strTerminals;// 投放终端编码
	private String strDownLoadAlertTable = "t_bz_download_alert";
	String strPrintTable="t_bz_coupon_print";
	DownLoadAlert downLoadAlert;

	/**
	 * 根据id找到数据库中终端的id集 增加优惠券是选择1234但是修改时变为23那么此方法返回14
	 * 
	 * @param where
	 * @return
	 */
	public String[] retDbTerminalsId(String where, String strTerminals)
	{
		downLoadAlert = new DownLoadAlert(globa);
		String strDbTerminalIds = show("where strid='" + where + "'").getStrTerminalIds();
		String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");

		for (int i = 0; i < TerminalIds.length; i++)
		{
			strDbTerminalIds = strDbTerminalIds.replace(TerminalIds[i], "");
		}
		strDbTerminalIds = strDbTerminalIds.replace(",", ",");
		System.out.println(strDbTerminalIds);
		if (strDbTerminalIds.length() > 0)
		{
			return strDbTerminalIds.split(",");
		}
		return null;
	}
/**
 * 修改优惠券打印的数量，当打印一次增加一次。
 * @param couponId
 * @return
 */
	public boolean updateIntPrint(String couponId)
	{
		String strSql = "update "+strTableName+" set intprint=intprint+1 where strid='" + couponId + "'";
		try
		{
			if (db.executeUpdate(strSql) > 0)
			{
				return true;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 修改优惠券信息
	 */

	public boolean update(String where)
	{
		downLoadAlert = new DownLoadAlert(globa);
		String strSql2;
		try
		{
			String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");
			String strDbTerminalId = show("where strid='" + where + "'").getStrTerminalIds();
			for (int i = 0; i < TerminalIds.length; i++)
			{
				strDbTerminalId = strDbTerminalId.replace(TerminalIds[i], "");
			}
			String[] strDbTerminalIds = strDbTerminalId.split(",");// 得到在修改时丢弃的终端id，增加时选中1、2、3、4但是修改时选中3、4，此时我们将得到1、2

//			System.out.println(strDbTerminalId + "未被选中");
			String strSql = "update  " + strTableName + "  SET dtActiveTime = ?, ";
			db.setAutoCommit(false);
			if (this.strSmallImg != null && this.strSmallImg.length() > 0)
			{
				strSql += "strsmallimg = '" + strSmallImg + "',";
			}
			if (this.strLargeImg != null && this.strLargeImg.length() > 0)
			{
				strSql += "strlargeimg = '" + strLargeImg + "',";
			}
			if (this.strPrintImg != null && this.strPrintImg.length() > 0)
			{
				strSql += "strPrintImg = '" + strPrintImg + "',";
			}
			strSql += " strName = ?, strShopId = ?,strTerminalIds=?,  " + "dtExpireTime = ?,intVip=?,intRecommend=?,flaPrice=?,intPrintLimit=?"
					+ "  WHERE strId=? ";

			db.prepareStatement(strSql);
			db.setString(1, dtActiveTime);
			db.setString(2, strName);
			db.setString(3, strShopId);
			db.setString(4, getTerminalIdsByNames(strTerminals));
			db.setString(5, dtExpireTime);
			db.setInt(6, intVip);
			db.setInt(7, intRecommend);
			db.setFloat(8, flaPrice);
			db.setInt(9, intPrintLimit);
			db.setString(10, strId);
			if (db.executeUpdate() > 0)
			{
				//修改couponprint表中的终端字段  当优惠券修改的时候
				String strSqlPrint="update "+strPrintTable+" set strTerminalIds='"+getTerminalIdsByNames(strTerminals)+"' where strcouponid='"+strId+"'";
				db.executeUpdate(strSqlPrint);
				// 如果丢弃的终端已处理就增加delete语句，如果没有处理，直接删除
				for (int i = 0; i < strDbTerminalIds.length; i++)
				{

					strSql2 = "delete from " + strDownLoadAlertTable + " where intstate=0 and strterminalid='" + strDbTerminalIds[i]
							+ "' and strdataid='" + where + "'";
					db.executeUpdate(strSql2);
					if (downLoadAlert.getCount("where intstate=1 and strterminalid='" + strDbTerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='add' ") > 0)
					{
						strSql2 = "insert into " + strDownLoadAlertTable + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
								+ "values (" + UID.getID() + ",'" + strDbTerminalIds[i] + "','" + strTableName + "','" + where + "','delete',0) ";
						db.executeUpdate(strSql2);
					}
				}
				// 对没有丢弃即选中的终端如果状态为1的话就增加update语句，如果没有的就不操作
				for (int i = 0; i < TerminalIds.length; i++)
				{
					if (downLoadAlert.getCount("where intstate=1 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='add' ") > 0)
					{
						if (downLoadAlert.getCount("where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + strId
								+ "' and strdataopetype='update' ") <= 0)
						{
						strSql2 = "insert into " + strDownLoadAlertTable + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
								+ "values (" + UID.getID() + ",'" + TerminalIds[i] + "','" + strTableName + "','" + where + "','update',0) ";
						db.executeUpdate(strSql2);
						}
					} else if (downLoadAlert.getCount("where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='add' ") <= 0)
					{
						strSql2 = "insert into " + strDownLoadAlertTable + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
								+ "values (" + UID.getID() + ",'" + TerminalIds[i] + "','" + strTableName + "','" + where + "','add',0) ";
						db.executeUpdate(strSql2);
					}
				}
				for (int i = 0; i < TerminalIds.length; i++)
				{
					if (downLoadAlert.getCount("where intstate=1 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='delete' ") > 0)
					{
						strSql2="update "+strDownLoadAlertTable+" set strdataopetype='add' where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='update' ";
						db.executeUpdate(strSql2);
						
					} 
					if (downLoadAlert.getCount("where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='delete' ") > 0)
					{
						strSql="delete from "+strDownLoadAlertTable+" where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + where
							+ "' and strdataopetype='delete' " ;
						db.executeUpdate(strSql);
						
					} 
					
				}
				db.commit();
				Globa.logger0("修改优惠券信息", globa.loginName, globa.loginIp, strSql, "优惠券管理", globa.userSession.getStrDepart());
				return true;
			} else
			{
				db.rollback();
				return false;

			}
		} catch (Exception e)
		{
			System.out.println("修改优惠券信息：" + e);
			db.rollback();
			return false;
		}
	}

	/**
	 * 增加优惠券信息
	 */
	public boolean add()
	{
		db.setAutoCommit(false);
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String[] strDownSql = null;
		String sql = "insert into " + strTableName + " (strId,strName,strSmallImg,dtActiveTime,dtExpireTime,strShopId,strTerminalIds"
				+ ",intVip,intRecommend,flaPrice,intPrintLimit,strPrintImg,strLargeImg,strCreator,dtCreateTime,intprint) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		try
		{
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strName);
			db.setString(3, strSmallImg);
			db.setString(4, dtActiveTime);
			db.setString(5, dtExpireTime);
			db.setString(6, strShopId);
			db.setString(7, getTerminalIdsByNames(strTerminals));
			db.setInt(8, intVip);
			db.setInt(9, intRecommend);
			db.setFloat(10, flaPrice);
			db.setInt(11, intPrintLimit);
			db.setString(12, strPrintImg);
			db.setString(13, strLargeImg);
			db.setString(14, strUserName);
			db.setString(15, com.ejoysoft.common.Format.getDateTime());
			db.setInt(16, 0);
			if (db.executeUpdate() > 0)
			{
				
				if (strTerminals != null && strTerminals != "")
				{
					String[] strTerminalId = getTerminalIdsByNames(strTerminals).split(",");
					strDownSql = new String[strTerminalId.length];
					for (int i = 0; i < strTerminalId.length; i++)
					{
						strDownSql[i] = "insert into " + strDownLoadAlertTable
								+ " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values (" + UID.getID() + ",'"
								+ strTerminalId[i] + "','" + strTableName + "','" + strId + "','add',0);";
						System.out.println(strDownSql[i]);
						db.executeUpdate(strDownSql[i]);
						Globa.logger0("增加优惠券信息时，增加下载提醒表中信息", globa.loginName, globa.loginIp, strDownSql[i], "优惠券管理", globa.unitCode);
					}
				}
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("增加优惠券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
				return true;
			} else
			{
				db.rollback();
				return false;

			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.rollback();
			return false;
		}

	}

	/**
	 * 删除优惠券信息
	 */
	public boolean delete(String where)
	{
		downLoadAlert = new DownLoadAlert(globa);
		String strSql2 = "";
		strTerminals = this.show("where strid='" + where + "'").getStrTerminals();
		try
		{
			db.setAutoCommit(false);
			String sql = "delete from " + strTableName + "  where strId =" + where;
			db.executeUpdate(sql);
			String strSqlPrint="delete from "+strPrintTable+" where strcouponid='"+strId+"'";
			db.executeUpdate(strSqlPrint);
			if (strTerminals != null && strTerminals != "")
			{
				String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");
				for (int i = 0; i < TerminalIds.length; i++)
				{
					// 处理在终端刷新期间既出现增加又出现修改的情况
					strSql2 = downLoadAlert.retStrSql(TerminalIds[i], where, strTableName);
					if (!strSql2.equals(""))
					{
						db.executeUpdate(strSql2);
					}
				}
			}
			db.commit();
			//db.setAutoCommit(true);
			Globa.logger0("删除优惠券信息", globa.loginName, globa.loginIp, sql, "优惠券管理", globa.unitCode);
			Globa.logger0("删除优惠券信息时，删除下载提醒表中的优惠券信息", globa.loginName, globa.loginIp, strSql2, "优惠券管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			db.rollback();
			return false;
		}
	}

	/**
	 * 详细显示单条记录
	 */
	public Coupon show(String where)
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
	}

	/**
	 * 返回某一行业的所有优惠券集合
	 * 
	 * @return
	 */
	public Vector<Coupon> getPerTradeCoupons(String strTrade)
	{
		Vector<Coupon> beans = new Vector<Coupon>();
		try
		{
			String sql = "select a.* from  " + strTableName + " a  left join " + strTableName2 + " b on a.strshopid=b.strid ";
			if (strTrade.length() > 0)
				sql = String.valueOf(sql) + String.valueOf(" where b.strtrade='" + strTrade + "' ");
			// System.out.println(sql+":111111getPerTradeCoupons");
			ResultSet rs = db.executeQuery(sql);
			if (rs != null && rs.next())
			{
				do
				{
					Coupon theBean = new Coupon();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	/**
	 * 查询符合条件的记录总数
	 */
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

	/**
	 * 根据条件，返回优惠券信息的集合
	 */
	public Vector<Coupon> list(String where, int startRow, int rowCount)
	{
		Vector<Coupon> beans = new Vector<Coupon>();
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
					Coupon theBean = new Coupon();
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

	/**
	 * 根据商家行业条件，返回优惠券信息的集合
	 */
	public Vector<Coupon> listByTrade(String where, int startRow, int rowCount)
	{
		Vector<Coupon> beans = new Vector<Coupon>();
		try
		{
			String sql = "select a.*  from  " + strTableName + " a left join " + strTableName2 + " b on a.strshopid=b.strid";
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
					Coupon theBean = new Coupon();
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

	/**
	 * 根据商家id条件，返回优惠券信息的集合
	 */
	public Vector<Coupon> listByShopId(String where)
	{
		Vector<Coupon> beans = new Vector<Coupon>();
		try
		{
			String sql = "SELECT *  FROM  " + strTableName + " ";
			if (where.length() > 0)
				sql = String.valueOf(sql) + String.valueOf(where);
			ResultSet rs = db.executeQuery(sql);
			if (rs != null && rs.next())
			{
				do
				{
					Coupon theBean = new Coupon();
					theBean = load(rs, false);
					beans.addElement(theBean);
				} while (rs.next());
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}
		return beans;
	}

	// 获取投放终端IDs
	public String getTerminalIdsByNames(String terminals)
	{
		String terminalids = "";
		if (terminals != null && terminals.trim() != "")
		{
			Terminal obj0 = new Terminal(globa, false);
			String terminalnames[] = terminals.split("，");
			for (int i = 0; i < terminalnames.length; i++)
			{
				Terminal obj = new Terminal();
				obj = obj0.show("where strno='" + terminalnames[i].trim() + "'");
				if (obj != null)
				{
					terminalids += obj.getStrId();
					if (i < terminalnames.length - 1)
						terminalids += ",";
				}
			}
		}
		return terminalids;
	}

	// 获取投放终端编号s
	public String getTerminalNamesByIds(String terminalids)
	{
		String terminalnos = " ";
		if (terminalids != null && terminalids.trim() != "")
		{
			Terminal obj0 = new Terminal(globa, false);
			Terminal obj = new Terminal();
			String terminals[] = terminalids.trim().split(",");
			for (int i = 0; i < terminals.length; i++)
			{
				obj = obj0.show("where strid='" + terminals[i].trim() + "'");
				if (obj != null)
				{
					terminalnos += obj.getStrNo();
					if (i < terminals.length - 1)
						terminalnos += "，";
				}
			}
		}
		return terminalnos;
	}

	public Coupon load(ResultSet rs, boolean isView)
	{
		Coupon theBean = new Coupon();
		try
		{
			theBean.setDtActiveTime(rs.getString("dtActiveTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setDtExpireTime(rs.getString("dtExpireTime"));
			theBean.setFlaPrice(rs.getFloat("flaPrice"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setIntPrintLimit(rs.getInt("intPrintLimit"));
			theBean.setIntRecommend(rs.getInt("intRecommend"));
			theBean.setIntVip(rs.getInt("intVip"));
			theBean.setStrPrintImg(rs.getString("strPrintImg"));
			theBean.setStrLargeImg(rs.getString("strLargeImg"));
			theBean.setStrShopId(rs.getString("strShopId"));
			theBean.setStrTerminals(getTerminalNamesByIds(rs.getString("strTerminalIds")));
			theBean.setStrTerminalIds(rs.getString("strTerminalIds"));

			theBean.setStrName(rs.getString("strName"));
			theBean.setStrSmallImg(rs.getString("strSmallImg"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	/**
	 * 返回是否推荐和vip
	 */
	public String returnStrRecommendOrVip(int num)
	{
		if (num == 0)
		{
			return "不是";
		} else if (num == 1)
		{
			return "是";
		} else
		{
			return "错误！";
		}

	}

	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_coupon";
	String strTableName2 = "t_bz_shop";

	public Coupon()
	{
	}

	public Coupon(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Coupon(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrName()
	{
		return strName;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

	public String getDtActiveTime()
	{
		if (!dtActiveTime.equals(null) && !dtActiveTime.equals(""))
		{
			return dtActiveTime.substring(0,10);
		}
		return dtActiveTime;
	}

	public void setDtActiveTime(String dtActiveTime)
	{
		this.dtActiveTime = dtActiveTime;
	}

	public String getDtExpireTime()
	{
		if (!dtExpireTime.equals(null) && !dtExpireTime.equals(""))
		{
			return dtExpireTime.substring(0,10);
		}
		return dtExpireTime;
	}

	public void setDtExpireTime(String dtExpireTime)
	{
		this.dtExpireTime = dtExpireTime;
	}

	public String getStrShopId()
	{
		return strShopId;
	}

	public void setStrShopId(String strShopId)
	{
		this.strShopId = strShopId;
	}

	public String getStrTerminalIds()
	{
		return strTerminalIds;
	}

	public void setStrTerminalIds(String strTerminalIds)
	{
		this.strTerminalIds = strTerminalIds;
	}

	public int getIntVip()
	{
		return intVip;
	}

	public void setIntVip(int intVip)
	{
		this.intVip = intVip;
	}

	public int getIntRecommend()
	{
		return intRecommend;
	}

	public void setIntRecommend(int intRecommend)
	{
		this.intRecommend = intRecommend;
	}

	public float getFlaPrice()
	{
		return flaPrice;
	}

	public void setFlaPrice(float flaPrice)
	{
		this.flaPrice = flaPrice;
	}

	public int getIntPrintLimit()
	{
		return intPrintLimit;
	}

	public void setIntPrintLimit(int intPrintLimit)
	{
		this.intPrintLimit = intPrintLimit;
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

	public String getStrPrintImg()
	{
		return strPrintImg;
	}

	public void setStrPrintImg(String strPrintImg)
	{
		this.strPrintImg = strPrintImg;
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

	public String getStrTerminals()
	{
		return strTerminals;
	}

	public void setStrTerminals(String strTerminals)
	{
		this.strTerminals = strTerminals;
	}

	public int getIntPrint()
	{
		return intPrint;
	}

	public void setIntPrint(int intPrint)
	{
		this.intPrint = intPrint;
	}

}
