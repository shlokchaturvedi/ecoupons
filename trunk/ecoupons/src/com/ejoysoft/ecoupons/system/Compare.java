package com.ejoysoft.ecoupons.system;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-8-2
 * Time: 11:51:22
 * To change this template use Options | File Templates.
 */

/*
 * ����һ��Compare�ӿ�
 */
public interface Compare {
  boolean lessThan(Object lhs, Object rhs);
  boolean lessThanOrEqual(Object lhs, Object rhs);
}


