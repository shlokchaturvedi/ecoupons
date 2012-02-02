package com.ejoysoft.ecoupons.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ejoysoft.auth.MD5;
import com.ejoysoft.common.Constants;
import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class Gift
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_gift";
	// String strRechargeTableName = "t_bz_member_recharge";
	private String strId;// 自动生成
	private String strIntro;// 礼品说明
	private String strSmallImg;// 小图地址
	private String strName;// 名称
	private String dtActiveTime;// 生效时间
	private int intPoint;// 兑换积分
	private String dtExpireTime;// 截至时间
	private String strLargeImg;// 大图
	private String strCreator;// insert操作的用户名
	private String dtCreateTime;// insert操作的系统时间
	private String strAttention;
	private float flaPrice;
	private String strGiftExchangeTable = "t_bz_gift_exchange";

	/**
	 * 增加礼品信息
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();

		String sql = "insert into " + strTableName + " (strId,strIntro,strSmallImg,strName,dtActiveTime,dtExpireTime,intPoint"
				+ ",strLargeImg,strCreator,dtCreateTime,flaPrice,strAttention) " + "values (?,?,?,?,?,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		System.out.println(com.ejoysoft.common.Format.getDate());
		try
		{
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strIntro);
			db.setString(3, strSmallImg);
			db.setString(4, strName);
			db.setString(5, dtActiveTime);
			db.setString(6, dtExpireTime);
			db.setInt(7, intPoint);
			db.setString(8, strLargeImg);
			db.setString(9, strUserName);
			db.setString(10, com.ejoysoft.common.Format.getDateTime());
			db.setFloat(11, flaPrice);
			db.setString(12, strAttention);
			if (db.executeUpdate() > 0)
			{
				Globa.logger0("增加礼品信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
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

	/**
	 * 修改礼品信息
	 * 
	 */
	public boolean update(String tStrId)
	{
		try
		{
			String strSql = "UPDATE  " + strTableName + "  SET strIntro = ?, ";
			if (this.strSmallImg != null && this.strSmallImg.length() > 0)
			{
				strSql += "strsmallimg = '" + strSmallImg + "',";
			}
			if (this.strLargeImg != null && this.strLargeImg.length() > 0)
			{
				strSql += "strlargeimg = '" + strLargeImg + "',";
			}
			strSql += " strName = ?, intPoint = ?,  " + "dtExpireTime = ? ,strAttention=?, flaPrice=? WHERE strId=? ";
			db.prepareStatement(strSql);
			db.setString(1, strIntro);
			db.setString(2, strName);
			db.setInt(3, intPoint);
			db.setString(4, dtExpireTime);
			db.setString(5, strAttention);
			db.setFloat(6, flaPrice);
			db.setString(7, tStrId);
			db.executeUpdate();
			Globa.logger0("修改礼品信息", globa.loginName, globa.loginIp, strSql, "会员管理", globa.userSession.getStrDepart());
			return true;
		} catch (Exception e)
		{
			System.out.println("修改礼品信息时出错：" + e);
			return false;
		}
	}

	/**
	 * 删除礼品信息
	 */
	public boolean delete(String where)
	{
		try
		{
			String sql = "DELETE FROM " + strTableName + " where strId='" + where+"'";
			String strSql = "delete from " + strGiftExchangeTable + " where strGiftId='" + where+"'";
			db.setAutoCommit(false);
			db.executeUpdate(sql);
			db.executeUpdate(strSql);
			db.commit();
			db.setAutoCommit(true);
			Globa.logger0("删除礼物信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			db.rollback();
			ee.printStackTrace();
			return false;
		}

	}

	/**
	 * 详细显示单条记录
	 */
	public Gift show(String where)
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
	 * 根据条件，返回礼品信息的集合
	 */
	public Vector<Gift> list(String where, int startRow, int rowCount)
	{
		Vector<Gift> beans = new Vector<Gift>();
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
					Gift theBean = new Gift();
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
	 * 根据卡号返回余额
	 */
	public float getFlaBalance(String strMemberCardNo)
	{
		float a = 0f;
		String sql = "select flabalance from " + strTableName + " where strCardNo='" + strMemberCardNo + "' ";
		ResultSet rs = db.executeQuery(sql);
		try
		{
			while (rs.next())
			{
				a = rs.getFloat("flabalance");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0f;
		}
		return a;
	}

	public Gift load(ResultSet rs, boolean isView)
	{
		Gift theBean = new Gift();
		try
		{
			theBean.setDtActiveTime(rs.getString("dtActiveTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setDtExpireTime(rs.getString("dtExpireTime"));
			theBean.setIntPoint(rs.getInt("intPoint"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setStrIntro(rs.getString("strIntro"));
			theBean.setStrLargeImg(rs.getString("strLargeImg"));
			theBean.setStrName(rs.getString("strName"));
			theBean.setStrSmallImg(rs.getString("strSmallImg"));
			theBean.setStrAttention(rs.getString("strAttention"));
			theBean.setFlaPrice(rs.getFloat("flaPrice"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public Gift()
	{
		// TODO Auto-generated constructor stub
	}

	public Gift(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Gift(Globa globa, boolean b)
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
		return dtActiveTime;
	}

	public void setDtActiveTime(String dtActiveTime)
	{
		this.dtActiveTime = dtActiveTime;
	}

	public int getIntPoint()
	{
		return intPoint;
	}

	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}

	public String getDtExpireTime()
	{
		return dtExpireTime;
	}

	public void setDtExpireTime(String dtExpireTime)
	{
		this.dtExpireTime = dtExpireTime;
	}

	public String getStrLargeImg()
	{
		return strLargeImg;
	}

	public void setStrLargeImg(String strLargeImg)
	{
		this.strLargeImg = strLargeImg;
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

	public String getStrAttention()
	{
		return strAttention;
	}

	public void setStrAttention(String strAttention)
	{
		this.strAttention = strAttention;
	}

	public float getFlaPrice()
	{
		return flaPrice;
	}

	public void setFlaPrice(float flaPrice)
	{
		this.flaPrice = flaPrice;
	}

}
