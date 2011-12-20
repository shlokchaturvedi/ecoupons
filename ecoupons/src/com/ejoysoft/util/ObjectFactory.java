package com.ejoysoft.util;

//��ҳ���Ʊ���ʵ�ֵĹ����ӿ�

public interface ObjectFactory {

    /**
     * Returns the object associated with <code>id</code> or null if the
     * object could not be loaded.
     *
     * @param id the id of the object to load.
     * @return the object specified by <code>id</code> or null if it could not
     *      be loaded.
     */
    //���ط�ҳbean��Ϣ
    public Object loadObject(long id);
}
