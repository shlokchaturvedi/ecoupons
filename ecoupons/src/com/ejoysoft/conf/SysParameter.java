package com.ejoysoft.conf;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.ecoupons.system.SysPara;

import java.util.HashMap;
import java.util.Vector;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-26
 * Time: 14:59:27
 * To change this template use Options | File Templates.
 */
public class SysParameter {
    private static HashMap paramGroups = new HashMap(); //�Թ����ķ�ʽ�������еĲ�������Ϣ
    private static HashMap params = new HashMap();      //�Թ����ķ�ʽ�������еĲ�����Ϣ

    //���췽��
    public SysParameter() {
    }

    /**
     * ��ʼ��
     * @throws java.sql.SQLException
     */
    public static void init() throws SQLException {
        //query db
        SysPara param = new SysPara();
        SysPara theParam = new SysPara();
        String sql = "SELECT * FROM  t_SY_sysPara   ORDER BY  strType , intSort ";
        try {
            Connection con = DbConnect.getStaticCon();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String lastParamType = "";  //��һ���������
            Vector paramVec = new Vector();    //ͳһ���͵Ĳ�����
            while (rs.next()) {
                theParam = param.load(rs);
                //�������
                params.put(theParam.getStrId(), theParam);
                //���浽��
                if (lastParamType.equals("")) {
                    lastParamType = theParam.getStrType();
                } else if (!theParam.getStrType().equals(lastParamType)) {
                    //������һ������
                    SysPara[] paramArray = new SysPara[paramVec.size()];
                    paramVec.toArray(paramArray);
                    paramGroups.put(lastParamType, paramArray);
                    paramVec.clear();
                    lastParamType = theParam.getStrType();
                }
                paramVec.add(theParam);
            }
            rs.close();
            stmt.close();
            con.close();
            //�������һ������
            SysPara[] paramArray = new SysPara[paramVec.size()];
            paramVec.toArray(paramArray);
            paramGroups.put(lastParamType, paramArray);
            paramVec.clear();
            System.out.println("Load System Parameters Successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ĳ�����͵����в���
     * @param type
     * @return
     */
    public static SysPara[] getParamsByType(String type) {
        return (SysPara[]) paramGroups.get(type);
    }

    /**
     * ���¼��ز�����Ϣ
     * @throws java.sql.SQLException
     */
    public void reLoad() throws SQLException {
        paramGroups.clear();
        params.clear();
        init();
    }

    /**
     * Get a parameter object specified by the paramId
     * @param paramId
     * @return
     */
    public static SysPara getParameter(String paramId) {
        return (SysPara) params.get(paramId);
    }

    /**
     * get a parameter's name specified by the paramId
     * @param paramId
     * @return
     */
    public static String getParameterName(String paramId) {
        if (params.containsKey(paramId))
            return getParameter(paramId).getStrName();
        else
            return "";
    }
    public static String getParameterValue(String paramId) {
        if (params.containsKey(paramId))
            return getParameter(paramId).getStrValue();
        else
            return "";
    }
    
    public static String getName(String type,String value) {
    	SysPara arry[]=(SysPara[]) paramGroups.get(type);
    	String tName="";
    	if(arry==null) 
    		return "";
    	else {
    		for (int i=0;i<arry.length;i++){
    			if(value.equals(arry[i].getStrValue())){
    				tName=arry[i].getStrName();
    				break;
    			}
    		}
    	}
    	return tName;
    }
}
