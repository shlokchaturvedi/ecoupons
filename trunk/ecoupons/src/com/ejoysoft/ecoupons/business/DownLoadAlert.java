package com.ejoysoft.ecoupons.business;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.Globa;
import com.ejoysoft.common.UID;

public class DownLoadAlert
{
	private Globa globa;
	private DbConnect db;
	String strTableName = "t_bz_download_alert";

	public DownLoadAlert()
	{
		// TODO Auto-generated constructor stub
	}

	public DownLoadAlert(Globa globa)
	{
		this.globa = globa;
		db = globa.db;
	}

	public DownLoadAlert(Globa globa, boolean b)
	{
		this.globa = globa;
		db = globa.db;
		if (b)
			globa.setDynamicProperty(this);
	}

	private String strId;
	private String strTerminalId;
	private String strDataType;
	private String strDataId;
	private String strDataOpeType;
	private String intState;

	/**
	 * 判断在终端刷新之前同时出现增加、修改、删除的情况的时候，该选择哪个sql语句
	 */
	public String retStrSql(String strTerminalIds, String where, String strDataType)
	{
		if (getCount("where intstate=0 and strdataid='" + where + "' and strterminalid=" + strTerminalIds + " and strdataopetype='update'") > 0)
		{
			return "update  " + strTableName + "  SET strdataopetype='delete',intstate=0 where intstate=0 and strdataid=" + where;
		} else if (getCount("where intstate=0 and strdataid='" + where + "' and strterminalid=" + strTerminalIds + " and strdataopetype='add'") > 0)
		{
			return "delete from " + strTableName + " where intstate=0 and strdataid='" + where + "' and strterminalid='" + strTerminalIds + "'";
		}
		return "";
	}

	/**
	 * 详细显示单条记录
	 */
	public DownLoadAlert show(String where)
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
	 * 根据条件返回下载提醒表的集合
	 */

	public Vector<DownLoadAlert> list(String where, int startRow, int rowCount)
	{
		Vector<DownLoadAlert> beans = new Vector<DownLoadAlert>();
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
					DownLoadAlert theBean = new DownLoadAlert();
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
	 * 删除下载提醒表记录
	 */
	public boolean delete(String where)
	{
		try
		{
			String sql = "delete from " + strTableName + "  ".concat(where);
			db.executeUpdate(sql);
			Globa.logger0("删除下载提醒表记录", globa.loginName, globa.loginIp, sql, "接口管理", globa.unitCode);
			return true;
		} catch (Exception ee)
		{
			ee.printStackTrace();
			return false;
		}
	}

	public DownLoadAlert load(ResultSet rs, boolean isView)
	{
		DownLoadAlert theBean = new DownLoadAlert();
		try
		{
			theBean.setStrId(rs.getString("strId"));
			theBean.setIntState(rs.getString("intState"));
			theBean.setStrDataId(rs.getString("strDataId"));
			theBean.setStrDataOpeType(rs.getString("strDataOpeType"));
			theBean.setStrDataType(rs.getString("strDataType"));
			theBean.setStrTerminalId(rs.getString("strTerminalId"));
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

	public String getStrTerminalId()
	{
		return strTerminalId;
	}

	public void setStrTerminalId(String strTerminalId)
	{
		this.strTerminalId = strTerminalId;
	}

	public String getStrDataType()
	{
		return strDataType;
	}

	public void setStrDataType(String strDataType)
	{
		this.strDataType = strDataType;
	}

	public String getStrDataId()
	{
		return strDataId;
	}

	public void setStrDataId(String strDataId)
	{
		this.strDataId = strDataId;
	}

	public String getStrDataOpeType()
	{
		return strDataOpeType;
	}

	public void setStrDataOpeType(String strDataOpeType)
	{
		this.strDataOpeType = strDataOpeType;
	}

	public String getIntState()
	{
		return intState;
	}

	public void setIntState(String intState)
	{
		this.intState = intState;
	}
}
