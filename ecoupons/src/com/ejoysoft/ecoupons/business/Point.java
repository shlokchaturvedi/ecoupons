package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.Constants;
import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class Point
{

	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_point_buy";
	
	private String strId;// 自动生成
	private String strShopId;// 商家id
	private int intMoney;// 购买金额
	private int intPoint;// 积分

	private String dtBuyTime;// 购买日期
	private int intType;// 积分类型1电子积分2纸质积分
	private int intState;// 状态0未审核1已审核
	private String strCreator;// 创建人
	private String dtCreateTime;// 创建时间
	private String strAuditor;// 审核人
	private String dtAuditTime;// 审核时间
	private String exchangeRate;
	

	/*
	 * 修改积分购买信息
	 */
	public boolean update(String tStrId)
	{
		Shop shop = new Shop(globa);
		
		int intDbPoint=show("where strId='" + tStrId+"'").getIntPoint();
		if (intMoney < intDbPoint)
		{
			if (intDbPoint-intMoney>shop.show("where strId='" + show("where strId='" + tStrId+"'").getStrShopId()+"'").getIntPoint())
			{
				return false;
			}
		}
		try
		{
			String strSql="update "+strTableName+" set strShopId=?,intMoney=?,intPoint=?,intType=?  where strId=? ";
			System.out.println(strSql);
			System.out.println(strShopId);
			System.out.println(intMoney);
			System.out.println(intPoint);
			System.out.println(intType);
			System.out.println(strId);
			String strSql2 = "update t_bz_shop set intpoint=intPoint-" + intPoint + " where strid=" + strShopId;
			db.getConnection().setAutoCommit(false);
			db.prepareStatement(strSql);
			db.setString(1, strShopId);
			db.setInt(2, intMoney);
			db.setInt(3, intPoint);
			db.setInt(4, intType);
			db.setString(5, tStrId);
			if (db.executeUpdate() > 0 && db.executeUpdate(strSql2) > 0)
			{
				Globa.logger0("修改积分购买记录", globa.loginName, globa.loginIp, strSql, "商家管理", globa.userSession.getStrDepart());

				return true;
			} else
			{
				db.rollback();
				db.setAutoCommit(true);
				return false;
			}
		} catch (Exception e)
		{
			db.rollback();
			System.out.println("修改积分购买记录：" + e);
			return false;
		}
	}

	public Point()
	{

	}

	public Point(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public Point(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	/*
	 * 审核积分购买状态
	 */
	public boolean setAudit()
	{
		
		String strUserName = globa.userSession.getStrId();
		String strSql = "update " + strTableName + " set intState='1',  strAuditor='"+strUserName+"' , dtAuditTime='"+
		com.ejoysoft.common.Format.getDateTime()+"' where strId='" + strId + "' ";
		try
		{

			if (db.executeUpdate(strSql) > 0)
			{
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * 返回积分类型
	 */
	public String returnType(int num)
	{
		if (num == 1)
		{
			return "电子积分";
		} else if (num == 2)
		{
			return "纸质积分";
		} else
		{
			return "出现错误！";
		}
	}

	/*
	 * 状态转换
	 */
	public String returnState(int num)
	{
		if (num == 1)
		{
			return "已审核";
		} else if (num == 0)
		{
			return "未审核";
		} else
		{
			return "出现错误！";
		}
	}

	/*
	 * 增加积分购买记录
	 */
	public boolean add()
	{
		String strUserName = globa.userSession.getStrId();
		String strId = UID.getID();
		String sql = "insert into " + strTableName + " (strId,strShopId,intMoney,dtBuyTime,intPoint,intType" + ",strCreator,dtCreateTime) "
				+ "values (?,?,?,?,?,?,?,?) ";
		String strSql = "update t_bz_shop set intpoint=intPoint+" + intPoint + " where strid='" + strShopId+"'";
		try
		{
			db.getConnection().setAutoCommit(false);
			db.getConnection().setSavepoint();
			db.prepareStatement(sql);
			db.setString(1, strId);
			db.setString(2, strShopId);
			db.setInt(3, intMoney);
			db.setString(4, com.ejoysoft.common.Format.getDateTime());
			db.setInt(5, intPoint);
			db.setInt(6, intType);
			db.setString(7, strUserName);
			db.setString(8, com.ejoysoft.common.Format.getDateTime());
			if (db.executeUpdate() > 0 && db.executeUpdate(strSql) > 0)
			{
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				Globa.logger0("增加积分购买记录 ", globa.loginName, globa.loginIp, sql, "商家管理", globa.unitCode);
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
	 * 删除积分信息
	 */
	public boolean delete(String where, String intPoint)
	{
		Shop shop = new Shop(globa);
		strShopId = show("where strId='" + where+"'").getStrShopId();
		if (show("where strId='" + where+"'").getIntPoint() > shop.show("where strId='" + strShopId+"'").getIntPoint())
		{
			return false;
		}
		try
		{
			db.getConnection().setAutoCommit(false);
			db.getConnection().setSavepoint();
			String strSql = "update t_bz_shop set intpoint=intPoint-" + intPoint + " where strid=" + strShopId;
			String sql = "DELETE FROM " + strTableName + " where strId='" + where+"'";
			if (db.executeUpdate(sql) > 0 && db.executeUpdate(strSql) > 0)
			{

				db.commit();
				db.setAutoCommit(true);
				Globa.logger0("删除礼物信息", globa.loginName, globa.loginIp, sql, "会员管理", globa.unitCode);
				return true;
			} else
			{
				db.rollback();
				return false;
			}
		} catch (Exception ee)
		{
			db.rollback();
			ee.printStackTrace();
			return false;
		}
	}

	/*
	 * 详细显示单条记录
	 */
	public Point show(String where)
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

	public Vector<Point> list(String where, int startRow, int rowCount)
	{
		Vector<Point> beans = new Vector<Point>();
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
					Point theBean = new Point();
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

	public Point load(ResultSet rs, boolean isView)
	{
		Point theBean = new Point();
		try
		{
			theBean.setDtAuditTime(rs.getString("dtAuditTime"));
			theBean.setDtBuyTime(rs.getString("dtBuyTime"));
			theBean.setDtCreateTime(rs.getString("dtCreateTime"));
			theBean.setIntMoney(rs.getInt("intMoney"));
			theBean.setIntPoint(rs.getInt("intPoint"));
			theBean.setIntState(rs.getInt("intState"));
			theBean.setIntType(rs.getInt("intType"));
			theBean.setStrAuditor(rs.getString("strAuditor"));
			theBean.setStrCreator(rs.getString("strCreator"));
			theBean.setStrId(rs.getString("strId"));
			theBean.setStrShopId(rs.getString("strShopId"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return theBean;
	}

	/*
	 * --------------------------------------------------------------------------
	 * -----------
	 */
	
	
	

	public String getStrId()
	{
		return strId;
	}

	public void setStrId(String strId)
	{
		this.strId = strId;
	}

	public String getStrShopId()
	{
		return strShopId;
	}

	public void setStrShopId(String strShopId)
	{
		this.strShopId = strShopId;
	}

	public int getIntMoney()
	{
		return intMoney;
	}

	public void setIntMoney(int intMoney)
	{
		this.intMoney = intMoney;
	}

	public int getIntPoint()
	{
		return intPoint;
	}

	public void setIntPoint(int intPoint)
	{
		this.intPoint = intPoint;
	}

	public String getDtBuyTime()
	{
		return dtBuyTime;
	}

	public void setDtBuyTime(String dtBuyTime)
	{
		this.dtBuyTime = dtBuyTime;
	}

	public int getIntType()
	{
		return intType;
	}

	public void setIntType(int intType)
	{
		this.intType = intType;
	}

	public int getIntState()
	{
		return intState;
	}

	public void setIntState(int intState)
	{
		this.intState = intState;
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

	public String getStrAuditor()
	{
		return strAuditor;
	}

	public void setStrAuditor(String strAuditor)
	{
		this.strAuditor = strAuditor;
	}

	public String getDtAuditTime()
	{
		return dtAuditTime;
	}

	public void setDtAuditTime(String dtAuditTime)
	{
		this.dtAuditTime = dtAuditTime;
	}

	public String getExchangeRate()
	{
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}

	

}
