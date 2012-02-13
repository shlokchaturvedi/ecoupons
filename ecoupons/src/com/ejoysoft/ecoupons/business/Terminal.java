package com.ejoysoft.ecoupons.business;

import com.ejoysoft.common.*;
import com.ejoysoft.ecoupons.system.SysPara;

import java.util.HashMap;
import java.util.Vector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.ejoysoft.ecoupons.business.Shop;

/**
 * Created by IntelliJ IDEA. User: feiwj Date: 2005-9-1 Time: 9:12:43 To change
 * this template use Options | File Templates.
 */
public class Terminal
{
	private Globa globa;
	private DbConnect db;
	private int intPaperState;// 终端纸状态

	// ���췽��
	public Terminal()
	{
	}

	public Terminal(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	// ���췽��
	public Terminal(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b) globa.setDynamicProperty(this);
	}

	static String strTableName = "t_bz_terminal";
	String strTableName2 = "t_bz_advertisement";
	String strTableName3 = "t_bz_download_alert";
	public static HashMap<String, Terminal> hmTerminal;

	/**
	 * 将打印纸大刀阀值修改打印纸的状态
	 * 
	 */
	public boolean updatePrintPaperState(String strId, int num)
	{

		String strSql = "update " + strTableName + "  set intpaperstate=" + num + " where strid='" + strId + "'";
		String strSql1 = "update " + strTableName + "  set intstate=0, dtRefreshTime='" + com.ejoysoft.common.Format.getDateTime()
				+ "' where strid='" + strId + "'";
		try
		{
			db.setAutoCommit(false);
			db.executeUpdate(strSql1);
			db.executeUpdate(strSql);
			db.commit();
			db.setAutoCommit(true);
			return true;
		} catch (Exception e)
		{
			System.out.println("更新状态失败" + e);
			return false;
		}
	}

	/**
	 * 状态更新修改
	 * 
	 * @param strId
	 * @return
	 */
	public boolean updateState(String strId)
	{
		String strSql = "update " + strTableName + "  set intstate=0, dtRefreshTime='" + com.ejoysoft.common.Format.getDateTime() + "' where strid='"
				+ strId + "'";
		try
		{
			db.executeUpdate(strSql);
			return true;
		} catch (Exception e)
		{
			System.out.println("更新状态失败" + e);
			return false;
		}
	}

	/**
	 * 初始化
	 */
	public static void init()
	{
		hmTerminal = new HashMap<String, Terminal>();
		String sql = "SELECT * FROM " + strTableName + " ORDER BY dtcreatetime";
		try
		{
			Connection con = DbConnect.getStaticCon();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				Terminal theBean = new Terminal();
				theBean.setStrId(rs.getString("strId"));
				theBean.setDtCreateTime(rs.getString("dtCreateTime"));
				theBean.setIntState(rs.getInt("intState"));
				theBean.setStrNo(rs.getString("strNo"));
				hmTerminal.put(theBean.getStrNo(), theBean);
			}
			System.out.println("[INFO]:terminal Initialized Successful");
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("[ERROR]:An error occured in terminal.init()");
		}
	}

	// 添加券打机信息
	public boolean add()
	{
		String strSql = "";
		strId = UID.getID();
		Shop shop = new Shop(globa);
		Vector<Shop> vctShops = shop.list("", 0, 0);
		try
		{
			db.setAutoCommit(false);
			// 添加券打机信息
			strSql = "insert into " + strTableName + "  (strid, strno, dtactivetime, strlocation,straroundshopids,strproducer, strtype, "
					+ "strresolution, strresolution2, strresolution3,intstate,dtrefreshtime, strcreator, dtcreatetime,intpaperstate,strimage)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strNo);
			db.setString(3, com.ejoysoft.common.Format.getStrDate2(dtActiveTime));
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
			db.setInt(15, 0);
			db.setString(16, strImage);
			if (db.executeUpdate() > 0)
			{
				for (int i = 0; i < vctShops.size(); i++)
				{
					strSql = "insert into " + strTableName3 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values ("
							+ UID.getID() + ",'" + strId + "','t_bz_shop','" + vctShops.get(i).getStrId() + "','add',0)";
					db.executeUpdate(strSql);
				}
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("添加终端信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
				return true;
			} else
				db.rollback();
				return false;
		} catch (Exception e)
		{
			System.out.println("添加终端信息异常");
			e.printStackTrace();
			db.rollback();
			return false;
		}
	}

	// 删除终端信息
	public boolean delete(String where, String terminalid)
	{
		String sql = "delete from " + strTableName + "  ".concat(where);
		// 事务处理
		try
		{
			db.getConnection().setAutoCommit(false);// 禁止自动提交事务
			db.getConnection().setSavepoint();
			db.executeUpdate(sql);// 删除终端
			deleteTerminalIdsFromIds(terminalid);
			db.getConnection().commit(); // 统一提交
			Globa.logger0("删除终端信息", globa.loginName, globa.loginIp, sql, "终端管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	/**
	 * 状态更新修改
	 * 
	 * @param strId
	 * @return
	 */
	public boolean updateState(String strId, String strDateType)
	{
		String strSql = "update " + strTableName + "  set intstate=0, dtRefreshTime='" + com.ejoysoft.common.Format.getDateTime() + "' where strid='"
				+ strId + "'";
		String strSql2 = "update t_bz_download_alert set intstate=1 where strDataType='" + strDateType + "' and" + " strTerminalId='" + strId + "'";
		db.setAutoCommit(false);
		try
		{
			if (db.executeUpdate(strSql) > 0)
			{
				db.executeUpdate(strSql2);
				db.commit();
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

	// 终端更新信息
	public boolean update(String strId)
	{
		try
		{
			// System.out.println("where strbizname='"+strAroundShops+"' and strshopname='"+getAroundShopIds(strAroundShops)+"'");
			String strSql = "update " + strTableName + "  set strno=?, dtactivetime=?, strlocation=?,strAroundShopIds=?, strproducer=?, strtype=?, "
					+ "strresolution=?, strresolution2=?, strresolution3=?," ;
		    if (this.strImage!=null&&this.strImage.length() > 0) {
            	strSql += "strimage = '" + strImage + "' ";
            }
		    strSql += " where strid=? ";
			db.prepareStatement(strSql);
			db.setString(1, strNo);
			db.setString(2, com.ejoysoft.common.Format.getStrDate2(dtActiveTime));
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
		} catch (Exception e)
		{
			System.out.println("更行终端信息异常" + e);
			return false;
		}
	}

	// 获取临近商家ids
	public String getAroundShopIds(String strAroundShops)
	{
		String shopids = "";
		if (strAroundShops != null && strAroundShops.trim() != "")
		{
			Shop obj0 = new Shop(globa, false);
			String shopnames[] = strAroundShops.split("，");
			for (int i = 0; i < shopnames.length; i++)
			{
				Shop obj = new Shop();
				if (shopnames[i].split("-").length == 2)
				{
					obj = obj0.show("where strbizname='" + ((shopnames[i].split("-"))[0]).trim() + "' and strshopname='"
							+ ((shopnames[i].split("-"))[1]).trim() + "'");
					if (obj != null)
					{
						shopids += obj.getStrId();
						if (i < shopnames.length - 1)
							shopids += ",";
					}
				} else
					shopids = " ";
			}
		}
		return shopids;
	}

	// 封装结果集2
	public Terminal load2(ResultSet rs, boolean isView)
	{
		Terminal theBean = new Terminal();
		try
		{
			theBean.setStrId(rs.getString("strid"));
			theBean.setStrNo(rs.getString("strno"));
			theBean.setDtActiveTime(rs.getString("dtactivetime"));
			theBean.setStrLocation(rs.getString("strlocation"));
			theBean.setStrProducer(rs.getString("strproducer"));
			theBean.setStrProducerName(SysPara.getNameById(rs.getString("strproducer")));
			theBean.setStrType(rs.getString("strtype"));
			theBean.setStrTypeName(SysPara.getNameById(rs.getString("strtype")));
			theBean.setStrResolution(rs.getString("strresolution"));
			theBean.setStrResolution2(rs.getString("strresolution2"));
			theBean.setStrResolution3(rs.getString("strresolution3"));
			theBean.setStrImage(rs.getString("strimage"));
			theBean.setIntState(rs.getInt("intstate"));
			theBean.setIntPaperState(rs.getInt("intPaperState"));
			if (rs.getInt("intstate") == -1)
				theBean.setStateString("异常");
			else
				theBean.setStateString("正常");
			theBean.setIntState(rs.getInt("intstate"));
			theBean.setDtRefreshTime(rs.getString("dtrefreshtime"));
			theBean.setStrCreator(rs.getString("strcreator"));
			theBean.setDtCreateTime(rs.getString("dtcreatetime"));
			// 获取临近商家名称
			String shopids = rs.getString("straroundshopids");
			theBean.setStrAroundShopIds(shopids);
			if (shopids != null && shopids.trim() != "")
			{
				Shop obj0 = new Shop(globa);
				String shopnames = " ";
				String shops[] = shopids.split(",");
				for (int i = 0; i < shops.length; i++)
				{
					Shop obj = new Shop();
					obj = obj0.show("where strid='" + shops[i] + "'");
					if (obj != null)
					{
						shopnames += obj.getStrBizName() + "-" + obj.getStrShopName();
						if (i < shops.length - 1)
							shopnames += "，";
					}
				}
				theBean.setStrAroundShops(shopnames);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	// 查询记录
	public Vector<Terminal> jionlist(String where, int startRow, int rowCount)
	{
		Vector<Terminal> beans = new Vector<Terminal>();
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
					Terminal theBean = new Terminal();
					theBean = load2(rs, false);
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

	// 分页整理终端
	public Vector<Terminal> list(String where, int startRow, int rowCount)
	{
		Vector<Terminal> beans = new Vector<Terminal>();
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
					Terminal theBean = new Terminal();
					theBean = load2(rs, false);
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

	// 查询终端
	public Terminal show(String where)
	{
		try
		{
			String strSql = "select * from  " + strTableName + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
				return load2(rs, true);
			else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	// 获取所有终端编号
	public String[] getAllTerminalNos()
	{
		Terminal obj = new Terminal(globa);
		String allnos[] = new String[obj.getCount(" ")];
		int num = 0;
		String sql = "select * from " + strTableName;
		ResultSet re = db.executeQuery(sql);
		try
		{
			if (re != null && re.next())
			{
				do
				{
					allnos[num] = re.getString("strId") + "-" + re.getString("strno") + "-" + re.getString("strlocation");
					num++;
				} while (re.next());
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allnos;
	}

	// 自我更新
	public boolean selfUpdate(String tStrUserId)
	{
		try
		{

			String strSql = "update  " + strTableName + "  set strbizbame=?, strTerminalname=?, strtrade=?, straddr=?, strphone=?, "
					+ "strperson=?, strintro=?, strsmallimg=?,strlargeimg=?,intpoint=?, strcreator=?, strcreatetime=?  where strid=? ";
			db.prepareStatement(strSql);
			db.setString(1, strNo);
			db.setString(2, dtActiveTime); // strPWD
			db.setString(3, strLocation);
			db.setString(4, getTerminalIdsByNames(strTerminals));
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
			Globa.logger0("更新", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("更新失败" + e);
			return false;
		}
	}

	// 查询记录数
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

	// 添加广告记录
	public boolean addAd()
	{
		strId = UID.getID();
		String strSql = "";
		try
		{

			strSql = "insert into " + strTableName2 + "  (strid, strname,inttype, strcontent,strterminalids,dtstarttime,dtendtime, "
					+ "strcreator, dtcreatetime) values(?,?,?,?,?,?,?,?,?)";
			db.getConnection().setAutoCommit(false);// 禁止自动提交事务
			String strTerminalId = this.getTerminalIdsByNames(strTerminals);
			if (strTerminalId != null)
			{
				String[] strid = strTerminalId.split(",");
				for (int i = 0; i < strid.length; i++)
				{
					String strsql2 = "insert into " + strTableName3 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
							+ "values (" + UID.getID() + ",'" + strid[i] + "','" + strTableName2 + "','" + strId + "','add',0)";
					db.executeUpdate(strsql2);
				}
			}
			db.prepareStatement(strSql);
			db.setString(1, strId);
			db.setString(2, strName);
			db.setString(3, intType);
			db.setString(4, strContent);
			db.setString(5, getTerminalIdsByNames(strTerminals));
			db.setString(6, dtStartTime);
			db.setString(7, dtEndTime);
			db.setString(8, strCreator);
			db.setString(9, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				db.getConnection().commit(); // 统一提交
				db.setAutoCommit(true);
				Globa.logger0("添加广告信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
				return true;
			} else
				return false;
		} catch (Exception e)
		{
			System.out.println("添加广告信息异常");
			e.printStackTrace();
			return false;
		}
	}

	// 广告更新
	public boolean updateAd(String strId)
	{
		// -------------------------------------------------------------------------------------------------------------
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		String strSql2;
		String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");
		String strDbTerminalId = showAd("where strid='" + strId + "'").getStrTerminalIds();
		for (int i = 0; i < TerminalIds.length; i++)
		{
			strDbTerminalId = strDbTerminalId.replace(TerminalIds[i], "");
		}
		String[] strDbTerminalIds = strDbTerminalId.split(",");// 得到在修改时丢弃的终端id，增加时选中1、2、3、4但是修改时选中3、4，此时我们将得到1、2
		// -------------------------------------------------------------------------------------------------------
		try
		{
			String strSql = "update " + strTableName2 + "  set strname=?,inttype=?,";
			if (this.strContent != null && this.strContent.trim().length() > 0)
				strSql += " strcontent='" + this.strContent + "', ";
			strSql += "strterminalids=?,dtstarttime=?,dtendtime=? where strid=? ";
			// String sql3 = "update  " + strTableName3 +
			// "  set strdataopetype='update',intstate=0 where strdataid=" +
			// strId;
			// System.err.println(sql3);
			db.setAutoCommit(false);
			// db.executeUpdate(sql3);
			db.prepareStatement(strSql);
			db.setString(1, strName);
			db.setString(2, intType);
			db.setString(3, getTerminalIdsByNames(strTerminals));
			db.setString(4, dtStartTime);
			db.setString(5, dtEndTime);
			db.setString(6, strId);
			db.executeUpdate();
			// -------------------------------------------------------------------------------------------------------------

			// 如果丢弃的终端已处理就增加delete语句，如果没有处理，直接删除
			for (int i = 0; i < strDbTerminalIds.length; i++)
			{

				strSql2 = "delete from " + strTableName3 + " where intstate=0 and strterminalid='" + strDbTerminalIds[i] + "' and strdataid='"
						+ strId + "'";
				db.executeUpdate(strSql2);
				if (downLoadAlert.getCount("where intstate=1 and strterminalid='" + strDbTerminalIds[i] + "' and strdataid='" + strId
						+ "' and strdataopetype='add' ") > 0)
				{
					strSql2 = "insert into " + strTableName3 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values ("
							+ UID.getID() + ",'" + strDbTerminalIds[i] + "','" + strTableName2 + "','" + strId + "','delete',0) ";
					db.executeUpdate(strSql2);
				}
			}
			// 对没有丢弃即选中的终端如果状态为1的话就增加update语句，如果没有的就不操作
			for (int i = 0; i < TerminalIds.length; i++)
			{
				if (downLoadAlert.getCount("where intstate=1 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + strId
						+ "' and strdataopetype='add' ") > 0)
				{
					strSql2 = "insert into " + strTableName3 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values ("
							+ UID.getID() + ",'" + TerminalIds[i] + "','" + strTableName2 + "','" + strId + "','update',0) ";
					db.executeUpdate(strSql2);
				} else if (downLoadAlert.getCount("where intstate=0 and strterminalid='" + TerminalIds[i] + "' and strdataid='" + strId
						+ "' and strdataopetype='add' ") <= 0)
				{
					strSql2 = "insert into " + strTableName3 + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values ("
							+ UID.getID() + ",'" + TerminalIds[i] + "','" + strTableName2 + "','" + strId + "','add',0) ";
					db.executeUpdate(strSql2);
				}
			}
			// -------------------------------------------------------------------------------------------------------------
			db.getConnection().commit(); // 统一提交
			db.setAutoCommit(true);
			Globa.logger0("更新广告信息", globa.loginName, globa.loginIp, strSql, "终端管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("更新广告信息异常" + e);
			return false;
		}
	}

	// 封装广告信息结果集
	public Terminal loadAd(ResultSet rs, boolean isView)
	{
		Terminal theBean = new Terminal();
		try
		{
			theBean.setStrId(rs.getString("strid"));
			theBean.setStrName(rs.getString("strname"));
			theBean.setIntType(rs.getString("inttype"));
			theBean.setStrContent(rs.getString("strcontent"));
			theBean.setStrTerminalIds(rs.getString("strTerminalids"));
			theBean.setStrTerminals(getTerminalNamesByIds(rs.getString("strTerminalids")));
			theBean.setDtStartTime((rs.getString("dtstarttime")).substring(0, 5));
			theBean.setDtEndTime((rs.getString("dtendtime")).substring(0, 5));
			theBean.setStrCreator(rs.getString("strcreator"));
			theBean.setDtCreateTime(rs.getString("dtcreatetime"));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	// 删除广告信息
	public boolean deleteAd(String where, String strId)
	{
		DownLoadAlert downLoadAlert = new DownLoadAlert(globa);
		String sql = "delete from " + strTableName2 + "  ".concat(where);
		String sql3;
		strTerminals = this.showAd("where strid='" + strId + "'").getStrTerminals();
		// 事务处理
		try
		{
			db.getConnection().setAutoCommit(false);// 禁止自动提交事务
			// db.executeUpdate(sql3);
			db.executeUpdate(sql);// 删除终端
			String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");
			for (int i = 0; i < TerminalIds.length; i++)
			{
				// 处理在终端刷新期间既出现增加又出现修改的情况
				sql3 = downLoadAlert.retStrSql(TerminalIds[i], strId, strTableName2);
				if (!sql3.equals(""))
				{
					System.out.println(sql3);
					db.executeUpdate(sql3);
				}
			}
			db.getConnection().commit(); // 统一提交
			Globa.logger0("删除广告信息", globa.loginName, globa.loginIp, sql, "终端管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	// 从广告的投放终端id中删除指定终端id
	public void deleteTerminalIdsFromIds(String terminalid)
	{
		String sql = "select * from " + strTableName2 + " where strterminalids like '%" + terminalid + "%'";
		// System.out.println("Terminal.deleteTerminalsFromIds()"+sql);
		ResultSet rs = db.executeQuery(sql);
		try
		{
			if (rs != null && rs.next())
			{
				do
				{
					String dealre1 = rs.getString("strid");
					String dealre2 = rs.getString("strterminalids");
					// System.out.println("Shop.deleteShopIdFromIds()"+terminalid+rs.getString("strterminalids"));
					String array[] = dealre2.split(",");
					String terminalids = " ";
					if (array != null && !terminalid.equals(dealre2))
					{
						for (int i = 0; i < array.length; i++)
						{
							if (!array[i].trim().equals(terminalid))
							{
								terminalids += array[i].trim() + ",";
							}
						}
						terminalids = terminalids.trim().substring(0, terminalids.length() - 2);
					}
					String sql2 = "update " + strTableName2 + " set strterminalids='" + terminalids + "' where strid='" + dealre1 + "'";
					db.executeUpdate(sql2);
				} while (rs.next());
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 分页整理广告结果
	public Vector<Terminal> listAd(String where, int startRow, int rowCount)
	{
		Vector<Terminal> beans = new Vector<Terminal>();
		try
		{
			String sql = "SELECT *  FROM  " + strTableName2 + " ";
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
					Terminal theBean = new Terminal();
					theBean = loadAd(rs, false);
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

	// 查询记录数
	public int getCountAd(String where)
	{
		int count = 0;
		try
		{
			String sql = "SELECT count(strId) FROM " + strTableName2 + "  ";
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

	// 查询广告
	public Terminal showAd(String where)
	{
		try
		{
			String strSql = "select * from  " + strTableName2 + "  ".concat(where);
			ResultSet rs = db.executeQuery(strSql);
			if (rs != null && rs.next())
				return loadAd(rs, true);
			else
				return null;
		} catch (Exception ee)
		{
			return null;
		}
	}

	// 终端信息
	private String strId;// 终端信息Id
	private String strNo;// 终端编号
	private String dtActiveTime;// 启用时间
	private String strLocation;// 地点
	private String strAroundShopIds;// 临近终端
	private String strAroundShops;// 临近终端
	private String strProducer;// 生产厂家
	private String strProducerName;// 生产厂家
	private String strType;// 规格型号
	private String strTypeName;// 规格型号
	private String strImage;// 规格型号
	private String strResolution;// 主屏分辨率
	private String strResolution2;// 主屏分辨率2
	private String strResolution3;// 主屏分辨率3
	private int intState;// 状态
	private String stateString;// 默认状态显示正常
	private String printpaperStateString;// 默认状态显示正常

	private String dtRefreshTime;// 状态更新时间
	// 广告信息
	private String strName;// 广告名称
	private String intType;// 广告类型
	private String typeName;// 类型内容
	private String strContent;// 广告内容
	private String strTerminalIds;// 投放终端
	private String strTerminals;// 投放终端编码
	private String dtStartTime;// 每天开始时间
	private String dtEndTime;// 每天结束时间

	private String strCreator;// 创建人
	private String dtCreateTime;// 创建时间

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

	public String getStrImage() {
		return strImage;
	}

	public void setStrImage(String strImage) {
		this.strImage = strImage;
	}

	public String getStrProducerName()
	{
		return strProducerName;
	}

	public void setStrProducerName(String strProducerName)
	{
		this.strProducerName = strProducerName;
	}

	public String getStrTypeName()
	{
		return strTypeName;
	}

	public void setStrTypeName(String strTypeName)
	{
		this.strTypeName = strTypeName;
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

	public String getStrNo()
	{
		return strNo;
	}

	public void setStrNo(String strNo)
	{
		this.strNo = strNo;
	}

	public String getDtActiveTime()
	{
		return dtActiveTime;
	}

	public void setDtActiveTime(String dtActiveTime)
	{
		this.dtActiveTime = dtActiveTime;
	}

	public String getStrLocation()
	{
		return strLocation;
	}

	public void setStrLocation(String strLocation)
	{
		this.strLocation = strLocation;
	}

	public String getStrAroundShopIds()
	{
		return strAroundShopIds;
	}

	public void setStrAroundShopIds(String strAroundShopIds)
	{
		this.strAroundShopIds = strAroundShopIds;
	}

	public String getStrAroundShops()
	{
		return strAroundShops;
	}

	public void setStrAroundShops(String strAroundShops)
	{
		this.strAroundShops = strAroundShops;
	}

	public String getStrProducer()
	{
		return strProducer;
	}

	public void setStrProducer(String strProducer)
	{
		this.strProducer = strProducer;
	}

	public String getStrType()
	{
		return strType;
	}

	public void setStrType(String strType)
	{
		this.strType = strType;
	}

	public String getStrResolution()
	{
		return strResolution;
	}

	public void setStrResolution(String strResolution)
	{
		this.strResolution = strResolution;
	}

	public String getStrResolution2()
	{
		return strResolution2;
	}

	public void setStrResolution2(String strResolution2)
	{
		this.strResolution2 = strResolution2;
	}

	public String getStrResolution3()
	{
		return strResolution3;
	}

	public void setStrResolution3(String strResolution3)
	{
		this.strResolution3 = strResolution3;
	}

	public int getIntState()
	{
		return intState;
	}

	public void setIntState(int intState)
	{
		this.intState = intState;
	}

	public String getStateString()
	{
		return stateString;
	}

	public void setStateString(String stateString)
	{
		this.stateString = stateString;
	}

	public String getDtRefreshTime()
	{
		return dtRefreshTime;
	}

	public void setDtRefreshTime(String dtRefreshTime)
	{
		this.dtRefreshTime = dtRefreshTime;
	}

	public String getStrCreator()
	{
		return strCreator;
	}

	public void setStrCreator(String strCreator)
	{
		this.strCreator = strCreator;
	}

	public String getStrName()
	{
		return strName;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

	public String getIntType()
	{
		return intType;
	}

	public void setIntType(String intType)
	{
		this.intType = intType;
	}

	public String getStrContent()
	{
		return strContent;
	}

	public void setStrContent(String strContent)
	{
		this.strContent = strContent;
	}

	public String getStrTerminalIds()
	{
		return strTerminalIds;
	}

	public void setStrTerminalIds(String strTerminalIds)
	{
		this.strTerminalIds = strTerminalIds;
	}

	public String getDtStartTime()
	{
		return dtStartTime;
	}

	public void setDtStartTime(String dtStartTime)
	{
		this.dtStartTime = dtStartTime;
	}

	public String getDtEndTime()
	{
		return dtEndTime;
	}

	public void setDtEndTime(String dtEndTime)
	{
		this.dtEndTime = dtEndTime;
	}

	public String getDtCreateTime()
	{
		return dtCreateTime;
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

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public int getIntPaperState()
	{
		return intPaperState;
	}

	public void setIntPaperState(int intPaperState)
	{
		this.intPaperState = intPaperState;
	}

	public String getPrintpaperStateString()
	{
		return printpaperStateString;
	}

	public void setPrintpaperStateString(String printpaperStateString)
	{
		this.printpaperStateString = printpaperStateString;
	}

}
