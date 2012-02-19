package com.ejoysoft.ecoupons.web;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.ecoupons.business.CouponPrint;
import com.ejoysoft.ecoupons.business.GiftExchange;
import com.ejoysoft.ecoupons.business.PointCard;
import com.ejoysoft.ecoupons.business.PointCardInput;
import com.ejoysoft.ecoupons.business.PointPresent;
import com.ejoysoft.ecoupons.business.Recharge;

public class RecordModel
{
	private String dtCreateTime;
	private String strName;
	private float flaPay;
	private int intRecharge;
	private float flaBalance;
	private int intPoint;
	private String strId;

	private Globa globa;
	private DbConnect db;

	public RecordModel()
	{

	}

	public RecordModel(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public RecordModel(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	/**
	 * 查询符合条件的记录总数
	 */
	public int getCount(String strSql)
	{
		int count = 0;
		try
		{
			ResultSet rs = db.executeQuery(strSql);
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
	 * 根据条件返回积分的集合
	 */

	public Vector<RecordModel> listBalance(String strSql, int startRow, int rowCount)
	{

		Vector<RecordModel> beans = new Vector<RecordModel>();
		Recharge recharge = new Recharge(globa);
		CouponPrint couponPrint = new CouponPrint(globa);
		try
		{
			Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (startRow != 0 && rowCount != 0)
				s.setMaxRows((startRow + rowCount) - 1);
			ResultSet rs = s.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				if (startRow != 0 && rowCount != 0)
					rs.absolute(startRow);
				do
				{
					RecordModel theBean = new RecordModel();
					theBean.setDtCreateTime(rs.getString("dtcreatetime"));
					if (recharge.getCount("where strid='" + rs.getString("strId") + "'") > 0)
					{
						theBean.setStrName("充值");
						theBean.setIntRecharge(rs.getInt("intMoney"));
					}
					if (couponPrint.getCount("where strid='" + rs.getString("strId") + "'") > 0)
					{
						theBean.setStrName("打印有价券");
						theBean.setFlaPay(rs.getFloat("intMoney"));
					}
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
	 * 根据条件返回积分的集合
	 */

	public Vector<RecordModel> listIntegral(String strSql, int startRow, int rowCount)
	{

		Vector<RecordModel> beans = new Vector<RecordModel>();
		PointPresent pointPresent = new PointPresent(globa);
		PointCardInput pointInput = new PointCardInput(globa);
		GiftExchange giftExchange = new GiftExchange(globa);
		try
		{
			Statement s = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (startRow != 0 && rowCount != 0)
				s.setMaxRows((startRow + rowCount) - 1);
			ResultSet rs = s.executeQuery(strSql);
			if (rs != null && rs.next())
			{
				if (startRow != 0 && rowCount != 0)
					rs.absolute(startRow);
				do
				{
					RecordModel theBean = new RecordModel();
					theBean.setDtCreateTime(rs.getString("dtcreatetime"));
					theBean.setIntPoint(rs.getInt("intpoint"));
					if (pointPresent.getCount("where strid='" + rs.getString("strId") + "'") > 0)
						theBean.setStrName("商家转赠");
					if (pointInput.getCount("where strid='" + rs.getString("strId") + "'") > 0)
						theBean.setStrName("积分卡录入");
					if (giftExchange.getCount("where strid='" + rs.getString("strId") + "'") > 0)
						theBean.setStrName("购买礼品");

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

	public RecordModel load(ResultSet rs, boolean isView)
	{
		RecordModel theBean = new RecordModel();
		try
		{
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setIntPoint(rs.getInt("intPoint"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public String getDtCreateTime()
	{
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

	public String getStrName()
	{
		return strName;
	}

	public void setStrName(String strName)
	{
		this.strName = strName;
	}

	public float getFlaPay()
	{
		return flaPay;
	}

	public void setFlaPay(float flaPay)
	{
		this.flaPay = flaPay;
	}

	public int getIntRecharge()
	{
		return intRecharge;
	}

	public void setIntRecharge(int intRecharge)
	{
		this.intRecharge = intRecharge;
	}

	public float getFlaBalance()
	{
		return flaBalance;
	}

	public void setFlaBalance(float flaBalance)
	{
		this.flaBalance = flaBalance;
	}

	public int getIntPoint()
	{
		return intPoint;
	}

	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

}
