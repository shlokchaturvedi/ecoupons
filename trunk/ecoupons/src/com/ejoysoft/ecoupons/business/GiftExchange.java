package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class GiftExchange
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_gift_exchange";
	private String strId;// 自动生成id
	private String strGiftId;// 礼品ID
	private String strMemberCardNo;// 会员卡号
	private String dtExchangeTime;// 对换时间
	private int intState;// 状态0：未处理1：已处理
	private String strAddr;// 寄送地址
	private String strCreator;//
	private String dtCreateTime;//

	/*
	 * 返回礼品记录处理的状态
	 */
	public String returnState(int num)
	{
		if (num == 0)
		{
			return "未处理";

		} else
		{
			return "已处理";
		}
	}

	/*
	 * 增加礼品兑换记录
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();

		String sql = "insert into " + strTableName + " (strId,strGiftId,strMemberCardNo,dtExchangeTime,strAddr" + ",strCreator,dtCreateTime) "
				+ "values (?,?,?,?,?,?,?) ";
		System.out.println(sql);
		System.out.println(com.ejoysoft.common.Format.getDate());
		try
		{
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strGiftId);
			db.setString(3, strMemberCardNo);
			db.setString(4, dtExchangeTime);
			db.setString(5, strAddr);
			db.setString(6, strUserName);
			db.setString(7, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("增加礼品兑换记录", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * 删除礼品记录
	 */
	public boolean delete(String where)
	{
		try
		{
			String sql = "DELETE FROM " + strTableName + "  ".concat(where);
			db.executeUpdate(sql);
			Globa.logger0("删除礼物记录信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	/*
	 * 详细显示单条记录
	 */
	public GiftExchange show(String where)
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

	/*
	 * 将resultset数据导出
	 */
	public GiftExchange load(ResultSet rs, boolean isView)
	{
		GiftExchange theBean = new GiftExchange();
		try
		{
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setDtExchangeTime(rs.getString("dtExchangeTime"));
			theBean.setIntState(rs.getInt("intState"));
			theBean.setStrAddr(rs.getString("strAddr"));
			theBean.setStrGiftId(rs.getString("strGiftId"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	/*
	 * 根据查询条件返回一个礼品兑换记录的集合
	 */
	public Vector<GiftExchange> list(String where, int startRow, int rowCount)
	{
		Vector<GiftExchange> beans = new Vector<GiftExchange>();
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
					GiftExchange theBean = new GiftExchange();
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

	/*
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

	public GiftExchange()
	{
		// TODO Auto-generated constructor stub
	}

	public GiftExchange(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public GiftExchange(Globa globa, boolean b)
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

	public String getStrGiftId()
	{
		return strGiftId;
	}

	public void setStrGiftId(String strGiftId)
	{
		this.strGiftId = strGiftId;
	}

	public String getStrMemberCardNo()
	{
		return strMemberCardNo;
	}

	public void setStrMemberCardNo(String strMemberCardNo)
	{
		this.strMemberCardNo = strMemberCardNo;
	}

	public String getDtExchangeTime()
	{
		return dtExchangeTime;
	}

	public void setDtExchangeTime(String dtExchangeTime)
	{
		this.dtExchangeTime = dtExchangeTime;
	}

	public int getIntState()
	{
		return intState;
	}

	public void setIntState(int intState)
	{
		this.intState = intState;
	}

	public String getStrAddr()
	{
		return strAddr;
	}

	public void setStrAddr(String strAddr)
	{
		this.strAddr = strAddr;
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
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

}
