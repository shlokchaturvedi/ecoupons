package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class PointPresent
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_point_present";
	private String strId;// 自动生成
	private int intPoint;// 积分
	private String strMemberCardNo;//积分转赠卡号
	private String strCreator;// 创建人
	private String dtCreateTime;// 创建时间
	private String strShopId;// 商家id
	

	
	/*
	 * 增加积分转赠记录
	 */
	public boolean add()
	{
		Shop shop=new Shop(globa);
		int intShopPoint=shop.show("where strId="+strShopId).getIntPoint();
		if(intShopPoint<intPoint){
			return false;
		}
		
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String sql = "insert into " + strTableName + " (strId,strShopId,strMemberCardNo,intPoint" + ",strCreator,dtCreateTime) "
				+ "values (?,?,?,?,?,?) ";
		String strSql = "update t_bz_shop set intpoint=intPoint-" + intPoint + " where strid=" + strShopId;
		String strSql2="update t_bz_member set intpoint=intPoint+" + intPoint + " where strCardNo=" + strMemberCardNo;
//		String strSqlInsertRecharge="";
		try
		{
			
			db.getConnection().setAutoCommit(false);
			db.getConnection().setSavepoint();
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strShopId);
			db.setString(3, strMemberCardNo);
			db.setInt(4,intPoint);
			db.setString(5, strUserName);
			db.setString(6, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0 && db.executeUpdate(strSql) > 0&&db.executeUpdate(strSql2)>0)
			{
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				Globa.logger0("增加积分转赠记录 ", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			db.rollback();
			e.printStackTrace();
			return false;
		}

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

	/*
	 * 根据条件返回积分的集合
	 */

	public Vector<PointPresent> list(String where, int startRow, int rowCount)
	{
		Vector<PointPresent> beans = new Vector<PointPresent>();
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
					PointPresent theBean = new PointPresent();
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

	public PointPresent load(ResultSet rs, boolean isView)
	{
		PointPresent theBean = new PointPresent();
		try
		{
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setIntPoint(rs.getInt("intPoint"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setStrShopId(rs.getString("strShopId"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}
	

	public PointPresent()
	{

	}

	public PointPresent(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public PointPresent(Globa globa, boolean b)
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
	public int getIntPoint()
	{
		return intPoint;
	}
	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}
	public String getStrMemberCardNo()
	{
		return strMemberCardNo;
	}
	public void setStrMemberCardNo(String strMemberCardNo)
	{
		this.strMemberCardNo = strMemberCardNo;
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
	public String getStrShopId()
	{
		return strShopId;
	}
	public void setStrShopId(String strShopId)
	{
		this.strShopId = strShopId;
	}
	
	
	
	
}
