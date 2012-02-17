package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.ejoysoft.ecoupons.system.User;

public class PointCardInput
{

	private Globa globa;
	private DbConnect db;

	public PointCardInput()
	{

	}

	public PointCardInput(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public PointCardInput(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	private String strId;
	private String strPointCardNo;
	private String strPointCardPwd;
	private String strMemberCardNo;
	private int intPoint;
	private String strCreator;
	private String dtCreateTime;
	String strTableName= "t_bz_pointcard_input";
	String strTableMember="t_bz_member";
	String strTablePonitCard="t_bz_pointcard";
	/**
	 *修改纸质积分卡信息
	 * @param where
	 * @return
	 */
	 public boolean update(String tStrUserId) {
	        try {
	            String strSql = "UPDATE  " + strTableName + "  SET  strPointCardNo = ?, strPointCardPwd = ?, intPoint = ?,strMemberCardNo=? WHERE strId=? ";
	            db.prepareStatement(strSql);
	            db.setString(1, strPointCardNo);
	            db.setString(2, strPointCardPwd);
	            db.setInt(3, intPoint);
	            db.setString(4, strMemberCardNo);
	            db.setString(5, tStrUserId);
	            db.executeUpdate();
	            Globa.logger0("修改纸质积分信息", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());
	            return true;
	        } catch (Exception e) {
	            System.out.println("修改纸质积分信息：" + e);
	            return false;
	        }
	    }
	
	/**
	 * 删除纸质积分卡信息
	 */
	 public boolean delete(String where) {
	        try {
	            String sql = "DELETE FROM " + strTableName + "  ".concat(where);
	            db.executeUpdate(sql);
	            Globa.logger0("删除纸质积分卡信息", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
	            return true;
	        } catch (Exception ee) {
	            ee.printStackTrace();
	            return false;
	        }
	    }
	 /**
	  * 详细显示单条记录
	  */
	
	    public PointCardInput show(String where) {
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
	    }
	
	/**
	 * 录入纸质积分卡
	 */
	public boolean add()
	{
		
		String strId = UID.getID();
		String sql = "insert into " + strTableName + " (strId,strPointCardNo,strPointCardPwd,intPoint" 
		+ ",strCreator,dtCreateTime,strMemberCardNo) "
				+ "values (?,?,?,?,?,?,?) ";
		String strSql = "update " + strTableMember + " set flabalance=flabalance+"+intPoint+" where strcardno='" + strMemberCardNo + "' ";
	
		String strSqlPointCard = "update " + strTablePonitCard + " set inttype="+1+" where strPointCardNo='" + strPointCardNo + "' ";
		try
		{
			db.setAutoCommit(false);
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strPointCardNo);
			db.setString(3, strPointCardPwd);
			db.setInt(4,intPoint);
			db.setString(5, "system");
			db.setString(6, com.ejoysoft.common.Format.getDateTime());
			db.setString(7, strMemberCardNo);
			if (db.executeUpdate() > 0 )
			{
				db.executeUpdate(strSql);
				db.executeUpdate(strSqlPointCard);
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("录入纸质积分卡 ", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
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
	 * 根据条件返回积分的集合
	 */

	public Vector<PointCardInput> list(String where, int startRow, int rowCount)
	{
		Vector<PointCardInput> beans = new Vector<PointCardInput>();
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
					PointCardInput theBean = new PointCardInput();
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

	public PointCardInput load(ResultSet rs, boolean isView)
	{
		PointCardInput theBean = new PointCardInput();
		try
		{
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setIntPoint(rs.getInt("intPoint"));
			theBean.setStrPointCardNo(rs.getString("strPointCardNo"));
			theBean.setStrPointCardPwd(rs.getString("strPointCardPwd"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrPointCardNo()
	{
		return strPointCardNo;
	}

	public void setStrPointCardNo(String strPointCardNo)
	{
		this.strPointCardNo = strPointCardNo;
	}

	public String getStrPointCardPwd()
	{
		return strPointCardPwd;
	}

	public void setStrPointCardPwd(String strPointCardPwd)
	{
		this.strPointCardPwd = strPointCardPwd;
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
		return dtCreateTime;
	}

	public void setDtCreateTime(String dtCreateTime)
	{
		this.dtCreateTime = dtCreateTime;
	}

	public String getStrMemberCardNo()
	{
		return strMemberCardNo;
	}

	public void setStrMemberCardNo(String strMemberCardNo)
	{
		this.strMemberCardNo = strMemberCardNo;
	}
}
