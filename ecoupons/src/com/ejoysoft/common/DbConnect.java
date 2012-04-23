package com.ejoysoft.common;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.sql.DataSource;
import javax.naming.*;
import java.sql.*;
/**
 * DbConnect.java
 * @version $Revision: 1.0 $ $Date: 2004-11-25 11:31 $
 */
public class DbConnect {
    private static DataSource pool = null;
    private static DataSource mpool = null;
    private static int curConnNum = 0;
    /**
     */
    private volatile Connection con;
    private Statement stmt;
    private PreparedStatement prepstmt;
    private static Log log = LogFactory.getLog(DbConnect.class);


    public static void initPool(String jndiName) {
        Connection conTest = null;
        if (mpool != null) return;
        try {
            Context env = new InitialContext();
            mpool = (DataSource) env.lookup(jndiName);
            conTest = mpool.getConnection();
            if (conTest != null) {
                System.out.println("[" + Format.getDateTime() + "] Connect to database:" + mpool);
                conTest.close();
            }
            System.out.println("[INFO]:DB Connection Pool Initialized Successful");
        } catch (Exception e) {
        	System.out.println("[ERROR]:An error occured in DbConnect.initPool()");
            e.printStackTrace();
        }
    }
    
 public static Connection getStaticCon() {
        Connection conNew = null;
        try {
            conNew = mpool.getConnection();
            conNew.setAutoCommit(true);
            log.debug("getNewConn :" + conNew);
            System.out.println("getNewConn :" + conNew);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conNew;
    }
    /**
     */
    public Connection getNewConnect() {
        Connection conNew = null;
        try {
            conNew = mpool.getConnection();
            conNew.setAutoCommit(true);
            log.debug("getNewConn :" + conNew);
            System.out.println("getNewConn :" + conNew);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conNew;
    }

    /**
     *
     * @return
     */
    public Connection getCurConnect() {
        return con;
    }

    /**
     */
    public Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = mpool.getConnection();
                con.setAutoCommit(true);
                curConnNum++;
                log.debug("dbGet con=[" + curConnNum + "]" + con);
                System.out.println("dbGet con=[" + curConnNum + "]" + con);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            con = null;
            log.error("Can not connect to database:" + e.getMessage());
        }
        return con;
    }
    /**
     * @param sql
     * @return
     */
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("error during executeQuery: " + sql + "\n");
            ex.printStackTrace();
        } finally {
            return rs;
        }
    }
    /**
     *
     * @param sql
     * @return
     */
    public ResultSet executeRollQuery(String sql) {
        ResultSet rs = null;
        try {
            getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("error during executeRollQuery: " + sql + "\n");
            ex.printStackTrace();
        } finally {
            return rs;
        }
    }

    /**
     * ??��??????
     *
     * @param sql
     */
    public int executeUpdate(String sql) throws SQLException {

        getConnection();
        stmt = con.createStatement();
        int result = stmt.executeUpdate(sql);
        stmt.close();
        return result;
    }

    /**
     */
    public void closeStatement() throws SQLException {
        if (prepstmt != null)
            prepstmt.close();
        if (stmt != null)
            stmt.close();
    }

    /**
     */
    public void closeCon() {

        try {
            if (prepstmt != null)
                prepstmt.close();
            if (stmt != null)
                stmt.close();

            if (con != null && !con.isClosed()) {
                log.debug("dbclose con=[" + curConnNum + "]" + con);
                System.out.println("dbclose con=[" + curConnNum + "]" + con);
                curConnNum--;
                con.close();
                con = null;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	con=null;
        }
    }


    /**
     *
     * @param s sql
     */
    public void prepareStatement(String s) throws SQLException//????s ??????SQL???????
    {
        getConnection();
        prepstmt = con.prepareStatement(s);

    }


    public int executeUpdate() throws SQLException {
        int result = 0;
        if (prepstmt != null)
            result = prepstmt.executeUpdate();
        return result;
    }


    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery() throws SQLException {

        if (prepstmt != null) {
            return prepstmt.executeQuery();
        } else
            return null;
    }

    /**
     *
     * @param s sql
     * @throws java.sql.SQLException
     */
    public void prepareStatement(String s, int i, int j) throws SQLException {
        getConnection();
        prepstmt = con.prepareStatement(s, i, j);
    }

    /**
     *
     * @param i  
     * @param s  
     * @throws java.sql.SQLException
     */

    public void setString(int i, String s) throws SQLException {
        prepstmt.setString(i, s);
    }


    public void setBlob(int i, java.sql.Blob s) throws SQLException {
        prepstmt.setBlob(i, s);
    }


    public void addBatch() throws SQLException {
        prepstmt.addBatch();
    }

    public int[] executeBatch() throws SQLException {
        return prepstmt.executeBatch();
    }


    public void setTimestamp(int i, java.sql.Timestamp f) throws SQLException {
        prepstmt.setTimestamp(i, f);
    }


    public void setTime(int i, java.sql.Time f) throws SQLException {
        prepstmt.setTime(i, f);
    }

    public void setObject(int i, Object f) throws SQLException {
        prepstmt.setObject(i, f);
    }

    public void setDouble(int i, double f) throws SQLException {
        prepstmt.setDouble(i, f);
    }

    public void setClob(int i, Clob f) throws SQLException {
        prepstmt.setClob(i, f);
    }

    public void setByte(int i, byte f) throws SQLException {
        prepstmt.setByte(i, f);
    }

    public java.sql.ResultSetMetaData getMetaData() throws SQLException {
        return prepstmt.getMetaData();
    }

    /**
     * ?????sql????��?int????
     *
     * @param i ��??
     * @param j ?????
     * @throws java.sql.SQLException
     */
    public void setInt(int i, int j) throws SQLException {
        prepstmt.setInt(i, j);
    }

    /**
     * ?????sql????��?boolean????
     *
     * @param i    ��??
     * @param flag ?????
     * @throws java.sql.SQLException
     */
    public void setBoolean(int i, boolean flag) throws SQLException {
        prepstmt.setBoolean(i, flag);
    }

    /**
     * ?????sql????��?Date????
     *
     * @param i    ��??
     * @param date :java.sql.Date ?????
     * @throws java.sql.SQLException
     */
    public void setDate(int i, java.util.Date date) throws SQLException {
        prepstmt.setDate(i, Format.getSqlDate(date));
    }

    /**
     * ?????sql????��?long????
     *
     * @param i ��??
     * @param l long?????
     * @throws java.sql.SQLException
     */
    public void setLong(int i, long l) throws SQLException {
        prepstmt.setLong(i, l);
    }

    /**
     * ?????sql????��?float????
     *
     * @param i ��??
     * @param f float?????
     * @throws java.sql.SQLException
     */
    public void setFloat(int i, float f) throws SQLException {
        prepstmt.setFloat(i, f);
    }

    /**
     * ?????sql????��?byte[]????
     *
     * @param i      ��??
     * @param abyte0 ?????
     * @throws java.sql.SQLException
     */
    public void setBytes(int i, byte abyte0[])
            throws SQLException {
        prepstmt.setBytes(i, abyte0);
    }

    /**
     * ?????????Statament
     */
    public void clearParameters() throws SQLException {

        if (prepstmt != null) {
            prepstmt.clearParameters();
            prepstmt.close();
        }

    }


    /**
     * ????  PreparedStatement????
     *
     * @return PreparedStatement
     */

    public PreparedStatement getPreparedStatement() {
        return prepstmt;
    }


    protected void finalize() {

        closeCon();
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setAutoCommit(boolean t) {
        try {
            getConnection();
            con.setAutoCommit(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void commit() {
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void rollback() {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}