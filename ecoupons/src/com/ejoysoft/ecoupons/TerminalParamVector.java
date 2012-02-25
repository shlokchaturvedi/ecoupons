package com.ejoysoft.ecoupons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.ecoupons.business.TerminalPara;

public class TerminalParamVector {

	public static Vector<TerminalPara> VecTerminalParam;
	
	public static void init() {
		try {
			VecTerminalParam = new Vector<TerminalPara>();
			//query db
	        String sql = "SELECT * FROM t_bz_terminal_param";
	        Connection con = DbConnect.getStaticCon();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        TerminalPara tp = new TerminalPara();
	        while (rs.next()) {
	        	VecTerminalParam.addElement(tp.load(rs, false));
	        }
	        rs.close();
	        stmt.close();
	        con.close();
			System.out.println("=============TerminalParamVector Initialize Successful!");
		} catch (Exception e) {
			System.out.println("=============Error Occured in TerminalParamVector Initializing......");
			e.printStackTrace();
		}
	}
	
	public static String getWelcome() {
		String strWelcome = "";
		for (int i = 0; i < VecTerminalParam.size(); i++) {
			TerminalPara tp = VecTerminalParam.get(i);
			if (tp.getStrParamName().equals("strWelcome")) {
				strWelcome = tp.getStrParamValue();
				break;
			}
		}
		return strWelcome;
	}
}
