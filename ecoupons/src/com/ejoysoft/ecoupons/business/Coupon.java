package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	private String strIntro;
	private String strInstruction;
	private int intSendBySM;
	private String strTerminals;// 投放终端编码
	private float flaWordSize;
	private String strDownLoadAlertTable = "t_bz_download_alert";
	String strPrintTable = "t_bz_coupon_print";
	String strInputTable = "t_bz_coupon_input";
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
	 * 
	 * @param couponId
	 * @return
	 */
	public boolean updateIntPrint(String couponId)
	{
		String strSql = "update " + strTableName + " set intprint=intprint+1 where strid='" + couponId + "'";
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
		//downLoadAlert = new DownLoadAlert(globa);
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

			// System.out.println(strDbTerminalId + "未被选中");
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
					+ ",strInstruction=?,strIntro=?,intsendbysm=?,flawordsize=? WHERE strId=? ";

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
			db.setString(10, strInstruction);
			db.setString(11, strIntro);
			db.setInt(12, intSendBySM);
			db.setFloat(13, flaWordSize);
			db.setString(14, strId);
			if (db.executeUpdate() > 0)
			{
				// 修改couponprint表中的终端字段 当优惠券修改的时候
				String strSqlPrint = "update " + strPrintTable + " set strterminalids='" + getTerminalIdsByNames(strTerminals)
						+ "' where strcouponid='" + strId + "'";
				db.executeUpdate(strSqlPrint);
				String strSqlInput = "update " + strInputTable + " set strshopid='" + strShopId + "' where strcouponid='" + strId + "'";
				db.executeUpdate(strSqlInput);
				// 如果丢弃的终端已处理就增加delete语句，如果没有处理，直接删除
//				for (int i = 0; i < strDbTerminalIds.length; i++)
//				{
//					if (strDbTerminalIds[i] != null && !strDbTerminalIds[i].equals(""))
//					{
//						strSql2 = "delete from " + strDownLoadAlertTable + " where (intstate=0 or intstate=2) and strterminalid='"
//								+ strDbTerminalIds[i] + "' and strdataid='" + strId + "'";
//						db.executeUpdate(strSql2);
//						strSql2 = "insert into " + strDownLoadAlertTable + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
//								+ "values (" + UID.getID() + ",'" + strDbTerminalIds[i] + "','" + strTableName + "','" + strId + "','delete',0) ";
//						db.executeUpdate(strSql2);
//					}
//				}
				// 对没有丢弃即选中的终端如果状态为1的话就增加update语句，如果没有的就不操作

//				for (int i = 0; i < TerminalIds.length; i++)
//				{
//					strSql2 = "delete from " + strDownLoadAlertTable + " where (intstate=0 or intstate=2) and strterminalid='" + TerminalIds[i]
//							+ "' and strdataid='" + strId + "'";
//					db.executeUpdate(strSql2);
//					strSql2 = "insert into " + strDownLoadAlertTable + " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) "
//							+ "values (" + UID.getID() + ",'" + TerminalIds[i] + "','" + strTableName + "','" + strId + "','update',0) ";
//					db.executeUpdate(strSql2);
//				}
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
	 * 修改优惠券过期时间
	 */
	public boolean updateExpireTime(String strId)
	{
		downLoadAlert = new DownLoadAlert(globa);
		try
		{
			db.getConnection().setAutoCommit(false);
			String strSql = "update  " + strTableName + "  SET dtExpireTime = '" + dtExpireTime + "'   WHERE strId='" + strId + "' ";
			if (db.executeUpdate(strSql) > 0)
			{
				DownLoadAlert alert = new DownLoadAlert(globa);
				Vector<DownLoadAlert> vctAlerts = alert.list(" where strdataid='" + strId + "'", 0, 0);
				for (int i = 0; i < vctAlerts.size(); i++)
				{
					DownLoadAlert alert2 = vctAlerts.get(i);
					if (alert2.getIntState().equals("1") && alert2.getStrDataOpeType().equals("add"))
					{
						int alert3 = alert.getCount(" where strterminalid='" + alert2.getStrTerminalId()
								+ "' and strdataopetype='update' and intstate=0");
						if (alert3 == 0)
						{
							String sql2 = "insert into " + strDownLoadAlertTable
									+ " (strid,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values (" + UID.getID() + ",'"
									+ alert2.getStrTerminalId() + "','" + strTableName + "','" + alert2.getStrDataId() + "','update',0)";
							db.executeUpdate(sql2);
						}
					}
				}
				db.commit();
				Globa.logger0("修改优惠券过期时间", globa.loginName, globa.loginIp, strSql, "优惠券打印", "");
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
		String sql = "insert into " + strTableName + " (strid,strname,strsmallimg,dtactivetime,dtexpiretime,strshopid,strterminalids"
				+ ",intvip,intrecommend,flaprice,intprintlimit,strlargeimg,strcreator,dtcreatetime,intprint,strinstruction,strintro,strprintimg,intsendbysm,flawordsize) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

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
			// db.setString(12, strPrintImg);
			db.setString(12, strLargeImg);
			db.setString(13, strUserName);
			db.setString(14, com.ejoysoft.common.Format.getDateTime());
			db.setInt(15, 0);
			db.setString(16, strInstruction);
			db.setString(17, strIntro);
			db.setString(18, strPrintImg);
			db.setInt(19, intSendBySM);
			db.setFloat(20, flaWordSize);
			if (db.executeUpdate() > 0)
			{
//				if (strTerminals != null && strTerminals != "")
//				{
//					String[] strTerminalId = getTerminalIdsByNames(strTerminals).split(",");
//					strDownSql = new String[strTerminalId.length];
//					for (int i = 0; i < strTerminalId.length; i++)
//					{
//						strDownSql[i] = "insert into " + strDownLoadAlertTable
//								+ " (strId,strterminalid,strdatatype,strdataid,strdataopetype,intstate) " + "values (" + UID.getID() + ",'"
//								+ strTerminalId[i] + "','" + strTableName + "','" + strId + "','add',0);";
//						System.out.println(strDownSql[i]);
//						db.executeUpdate(strDownSql[i]);
//						Globa.logger0("增加优惠券信息时，增加下载提醒表中信息", globa.loginName, globa.loginIp, strDownSql[i], "优惠券管理", globa.unitCode);
//					}
//				}
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
			String strSqlPrint = "delete from " + strPrintTable + " where strcouponid='" + strId + "'";
			db.executeUpdate(strSqlPrint);
//			if (strTerminals != null && strTerminals != "")
//			{
//				String[] TerminalIds = getTerminalIdsByNames(strTerminals).split(",");
//				for (int i = 0; i < TerminalIds.length; i++)
//				{
//					// 处理在终端刷新期间既出现增加又出现修改的情况
//					strSql2 = downLoadAlert.retStrSql(TerminalIds[i], where, strTableName);
//					if (!strSql2.equals(""))
//					{
//						db.executeUpdate(strSql2);
//					}
//				}
//			}
			db.commit();
			// db.setAutoCommit(true);
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
			String sql = "select count(strid) from " + strTableName + "  ";
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
			String sql = "select *  from  " + strTableName + " ";
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
			String sql = "select *  from  " + strTableName + " ";
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
			Terminal obj = new Terminal();
			String terminals[] = terminalids.trim().split(",");
			for (int i = 0; i < terminals.length; i++)
			{
				obj = Terminal.hmidTerminal.get(terminals[i]);
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
			String tmp = rs.getString("strTerminalIds");
			theBean.setStrTerminals(getTerminalNamesByIds(tmp));
			theBean.setStrTerminalIds(rs.getString("strTerminalIds"));
			theBean.setStrInstruction(rs.getString("strInstruction"));
			theBean.setStrIntro(rs.getString("strIntro"));
			theBean.setStrName(rs.getString("strName"));
			theBean.setStrSmallImg(rs.getString("strSmallImg"));
			theBean.setIntSendBySM(rs.getInt("intsendbysm"));
			theBean.setFlaWordSize(rs.getInt("flawordsize"));
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
			return "否";
		} else if (num == 1)
		{
			return "是";
		} else
		{
			return "错误！";
		}

	}
	/**
	 * 返回是否支持短信发送功能
	 */
	public String returnStrSendBySM(int num)
	{
		if (num == 0)
		{
			return "不支持";
		} else if (num == 1)
		{
			return "支持";
		} else
		{
			return "错误！";
		}

	}

	private boolean IsUnicode(char word)
    {
        if ((word >= 0x4e00) && (word <= 0x9fbb))
            return true;
        else if ((word >= 0xff00) && (word <= 0xffef))
            return true;
        else
            return false;
    }
	
	public boolean isDoublebyteWord(String str)
	{
		
		
		byte[] b;
		int temp;
		for (int i = 0; i < str.length(); i++)
		{
			b = str.substring(i, i + 1).getBytes();
			temp = b[0];
			if (temp > 0)
			{
				return false;
			}
		}
		return true;
	}
/**
 * 将dealStrByBytes方法处理的结果封装成一个数组返回给调用者
 * @param str
 * @param linePos
 * @return
 */
	public ArrayList< String> returnDealStrByBytes(String[] str, int linePos)
	{

        ArrayList< String>listStrs=new ArrayList<String>();
		for (int i = 0; i < str.length; i++)
		{
			String[] strTemps=dealStrByBytes(str[i], linePos).split("<br>");
			for (int j = 0; j < strTemps.length; j++)
			{
				if (listStrs.size()>14)
				{
					return  listStrs;
				}
				listStrs.add(strTemps[j]);
			}
		}
      return  listStrs;

	}

	/* 给字符串添加换行符，其中linepos是需要换行的位置，按字节算的 */

	public String dealStrByBytes(String str, int linePos)
	{
		String new_str = "";
		int total_len = 0;
		int brNum = 0;

		for (int i = 1; i <= str.length(); i++)
		{
			if (isDoublebyteWord(str.substring(i - 1, i)))
			{

				total_len += 2;
				if (total_len >= linePos )
				{
					new_str += str.substring(i - 1, i) + "\n";
					brNum++;
					total_len=0;
				} else
				{
					new_str += str.substring(i - 1, i);
				}
			} else
			{

				total_len += 1;
				if (total_len >= linePos)
				{
					new_str += str.substring(i - 1, i) + "\n";
					brNum++;
					total_len=0;
				} else
				{
					new_str += str.substring(i - 1, i);
				}
			}
		}
		return new_str.replace(" ", "&nbsp;").replace("\n", "<br>");
	}
	public String dealStrByChars(String str, int linePos)
	{
		String new_str = "";
		int total_len = 0;
		int brNum = 0;
		StringBuffer sBuffer=new StringBuffer();
		char[]strChars=str.toCharArray();
		for (int i = 0; i < strChars.length; i++)
		{
			if (IsUnicode(strChars[i]))
				total_len += 2;
			else total_len += 1;
			sBuffer.append(strChars[i]);
			if (total_len >= linePos )
				{
					//new_str += str.substring(i - 1, i) + "\n";
					brNum++;
					total_len=0;
					sBuffer.append("<br>");
				}
			
		}
		return sBuffer.toString();
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
			return dtActiveTime.substring(0, 10);
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
			return dtExpireTime.substring(0, 10);
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
		if (strPrintImg == null)
		{
			strPrintImg = "";
		}
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
		if (dtCreateTime != null && dtCreateTime.length() > 3)
		{
			return dtCreateTime.substring(0, dtCreateTime.length() - 2);

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

	public String getStrIntro()
	{
		return strIntro;
	}

	public void setStrIntro(String strIntro)
	{
		this.strIntro = strIntro;
	}

	public String getStrInstruction()
	{
		return strInstruction;
	}

	public void setStrInstruction(String strInstruction)
	{
		this.strInstruction = strInstruction;
	}

	public int getIntSendBySM() {
		return intSendBySM;
	}

	public void setIntSendBySM(int intSendBySM) {
		this.intSendBySM = intSendBySM;
	}

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

	public float getFlaWordSize() {
		return flaWordSize;
	}

	public void setFlaWordSize(float flaWordSize) {
		if(flaWordSize==0)
			this.flaWordSize = (float) 10.5;
		else
			this.flaWordSize = flaWordSize;
	}
	
}
