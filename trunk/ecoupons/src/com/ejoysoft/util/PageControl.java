package com.ejoysoft.util;

import com.ejoysoft.common.Globa;

import java.io.Serializable;

/**
 * ����: ��ҳ����ʵ����
 * @author feiwj
 * @version $Revision: 1.0 $ $Date: 2004-8-12 11:31 $
 */
public class PageControl
        implements Serializable {


    public PageControl() {
        rstCount = 0;
        pageSize = 1;
        currentPage = 0;
    }

    //���ò���ģʽ���
    public PageControl(Globa globa, int intRowCount) {
        rstCount = intRowCount < 1 ? 1 : intRowCount;
        pageSize = globa.intPageSize;
        currentPage = ParamUtil.getInt((javax.servlet.http.HttpServletRequest) globa.request, "pageIndex", 1);
    }

    public PageControl(int intCurPage, int intPageSize, int intRowCount) {
        rstCount = intRowCount < 1 ? 1 : intRowCount;
        pageSize = intPageSize < 1 ? 1 : intPageSize;
        currentPage = intCurPage < 1 ? 1 : intCurPage;
    }

    //��ȡ��ʼ��¼λ��
    public int getBeginRstNo() {
        if (currentPage > getPageCount())
            currentPage = getPageCount();
        return pageSize * currentPage + 1;
    }

    //��ȡ��һҳ
    public int getHomeNo() {
        return 1;
    }

    //��ȡ��һҳ
    public int getNextNo() {
        if (currentPage + 1 > getPageCount())
            return currentPage;
        else
            return currentPage + 1;
    }

    //��ȡ��һҳ
    public int getPreNo() {
        if (currentPage - 1 < 1)
            return currentPage;
        else
            return currentPage - 1;
    }

    //��ȡ���һҳ
    public int getEndNo() {
        return getPageCount();
    }

    //��ȡ�����¼λ��
    public int getEndRstNo() {
        if (currentPage > getPageCount())
            currentPage = getPageCount();
        int lngEndNo = pageSize * (currentPage + 1);
        if (lngEndNo > rstCount)
            lngEndNo = rstCount;
        return lngEndNo;
    }

    //��ȡ��ǰҳ��
    public int getCurrentPage() {
        return currentPage;
    }

    //��ȡ��ҳ��
    public int getPageCount() {
        int retInt = 0;
        if (rstCount == 0)
            retInt = 1;
        else if (rstCount % pageSize == 0)
            retInt = rstCount / pageSize;
        else
            retInt = rstCount / pageSize + 1;

        // retInt=  (rstCount-1)/ pageSize +1;
        return retInt;
    }

    public int getPageSize() {
        return pageSize;
    }

    //��ȡ������
    public int getRstCount() {
        return rstCount;
    }

    //Ĭ�Ϸ�ҳ���
    public String toString() {
        String ret = super.toString();
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("rstCount = ")).append(rstCount).append("\n"))));
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("pageCount = ")).append(getPageCount()).append("\n"))));
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("pageSize = ")).append(pageSize).append("\n"))));
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("currentPage = ")).append(currentPage).append("\n"))));
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("getBeginRstNo = ")).append(getBeginRstNo()).append("\n"))));
        ret = String.valueOf(ret) + String.valueOf(String.valueOf(String.valueOf((new StringBuffer("getEndRstNo = ")).append(getEndRstNo()).append("\n"))));
        return ret;
    }

    //���ط�ҳ���
    public String toPageTable() {
        return null;
    }

    private int rstCount;
    private int pageSize;
    private int currentPage;
}
