package com.ejoysoft.common;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-6
 * Time: 9:33:24
 * To change this template use Options | File Templates.
 */
public class UID {
      private static int gene = 0;    //��ֹ1�����ڲ�����������ӵ�0-9֮���һ����

    public static synchronized String getID() {
    	int t = gene++ % 1000;
    	String result = "" + new Date().getTime();
    	if (t < 10)
    		result += "00" + t;
    	else if (t < 100)
    		result += "0" + t;
    	else
    		result += t;
        return  result;
    }
}
