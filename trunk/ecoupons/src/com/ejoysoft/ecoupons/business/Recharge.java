package com.ejoysoft.ecoupons.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Recharge
{

	private Globa globa;
	private DbConnect db;

	String strTableName = "t_bz_member_recharge";

	public Recharge()
	{

	}

	public Recharge(Globa globa)
	{
		this.globa = globa;
		db = globa.db;

	}

	public Recharge(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	public Vector<Recharge> list(String where, int startRow, int rowCount)
	{
		Vector<Recharge> beans = new Vector<Recharge>();
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
					Recharge theBean = new Recharge();
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

	public Recharge load(ResultSet rs, boolean isView)
	{
		Recharge theBean = new Recharge();
		try
		{
			theBean.setStrId(rs.getString("strId"));
			theBean.setIntMoney(rs.getInt("intMoney"));
			theBean.setStrMemberCardNo(rs.getString("strMemberCardNo"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	/*
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

	/*
	 * 根据充值编号返回会员缴费数额；
	 */
	public int returnIntMoney(String strId)
	{
		Recharge rechargeReturn = show("where strid=" + strId + " ");
		return rechargeReturn.getIntMoney();
	}
	
public boolean delete(String str)
{
	try
	{
		String sql = "delete from " + strTableName + "  where strmembercardno='"+str+"' ";
		db.executeUpdate(sql);
		Globa.logger0("删除会员卡充值记录", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
		return true;
	} catch (Exception ee)
	{
		ee.printStackTrace();
		return false;
	}
}	

	/*
	 * 修改会员的缴费记录
	 */
	public boolean update( int intMoney2)
	{
		Member member = new Member(globa, false);
		Connection connection = db.getConnection();
		float realBalance = member.getFlaBalance(strMemberCardNo)-(float) returnIntMoney(strId) + (float) intMoney2;
//		System.out.println("实际余额+"+realBalance+"--"+member.getFlaBalance(strMemberCardNo)+"--"+(float) returnIntMoney(strId)+"--"+intMoney2+"---"+intMoney+"---");
//		System.out.println(strMemberCardNo);
//		System.out.println(strId);
		PreparedStatement pStatement=null;
		PreparedStatement pStatement2=null;
		if (realBalance >= 0)
		{
			String strUserName = globa.userSession.getStrId();
			try
			{
				connection.setAutoCommit(false);
				connection.setSavepoint();
				String strSql = "UPDATE  " + strTableName + "  SET  intMoney= ? ,strCreator=? ,dtCreateTime=? WHERE strId=? ";
				String sql2 = "UPDATE t_bz_member SET flaBalance = ? WHERE strCardNo=? ";
				pStatement = connection.prepareStatement(strSql);
			    pStatement2 = connection.prepareStatement(sql2);
				pStatement.setInt(1, intMoney2);
				pStatement.setString(2, strUserName);
				pStatement.setString(3, com.ejoysoft.common.Format.getDate());
				pStatement.setString(4, strId);
				pStatement2.setFloat(1, realBalance);
				pStatement2.setString(2, strMemberCardNo);
				connection.commit();
				if (pStatement.executeUpdate() > 0 && pStatement2.executeUpdate() > 0)
				{
//					System.out.println("chongzhi3345688888888888888888");
					connection.setAutoCommit(true);
					Globa.logger0("修改会员缴费记录", globa.loginName, globa.loginIp, strSql + sql2, "会员管理", globa.unitCode);
					
					return true;
				} else
				{
					return false;
				}

			} catch (Exception e)
			{
				try
				{
					connection.rollback();
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				System.out.println("修改会员记录时出错：" + e);
				return false;
			}finally{
				try
				{
					pStatement.close();
					pStatement2.close();
					connection.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else
		{

			return false;
		}
	}

	/*
	 * 详细显示单条记录
	 */
	public Recharge show(String where)
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
	 * 会员缴费操作
	 */
	public boolean add(String money)
	{
		intMoney = new Integer(money);
		Member member = new Member(globa, false);
		float a = 0f;
		if (member.getFlaBalance(strMemberCardNo) > 0f)
		{
			a = member.getFlaBalance(strMemberCardNo) + (float) intMoney;
		} else
		{
			a = (float) intMoney;

		}
		String strId = UID.getID();
		String strUserName = globa.userSession.getStrId();
		String sql = "insert into " + strTableName + " (strId,strMemberCardNo,intMoney,strCreator,dtCreateTime) " + "values (?,?,?,?,?) ";
		String sql2 = "UPDATE t_bz_member SET flaBalance ="+a+" WHERE strCardNo='"+strMemberCardNo+"' ";
		
		try
		{
			db.setAutoCommit(false);
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strMemberCardNo);
			db.setInt(3, intMoney);
			db.setString(4, strUserName);
			db.setString(5, com.ejoysoft.common.Format.getDateTime());
			
			if (db.executeUpdate() > 0 && db.executeUpdate(sql2) > 0)
			{
				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("会员缴费成功", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
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

	private String strId;
	private String strMemberCardNo;
	private int intMoney;
	private String strCreator;
	private String dtCreateTime;

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public int getIntMoney()
	{
		return intMoney;
	}

	public void setIntMoney(int intMoney)
	{
		this.intMoney = intMoney;
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
