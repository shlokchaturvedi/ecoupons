package com.ejoysoft.ecoupons.system;
import java.util.Vector;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-8-2
 * Time: 11:49:49
 * To change this template use Options | File Templates.
 */
/*
 *
 * ����һ��Vector�����࣭�����������Vector��SortVector
 *
 */
public class SortVector extends Vector {
  private Compare compare; // To hold the callback
  public SortVector(Compare comp) {
    compare = comp;
  }
  public void sort() {
    quickSort(0, size() - 1);
  }
  // ��������
  private void quickSort(int left, int right) {
    if(right > left) {
      Object o1 = elementAt(right);//ȡ��Vector�����һ��Ԫ��?
      int i = left - 1;
      int j = right;
      while(true)
      {
       /*
        * while(compare.lessThan(elementAt(++i),o1))
        * �������ô�����ߵ�Ԫ�ؿ�ʼ��o1�Ƚ�,ֱ���ҵ���Ԫ�ر�o1СΪֹ��
        * ����¼��Ԫ����Vector�е�λ��Ϊ i
        */
        while(compare.lessThan(elementAt(++i),o1));//++i���������?1��������
        /*
         * �����whileѭ����Vector�ĵ���ڶ���Ԫ�ؿ�ʼ��o1�Ƚϣ�
         * ֱ���ҵ���Ԫ�ز���o1СΪֹ������¼��Ԫ����Vector�е�λ��Ϊj
         */
        while(j > 0)
        {
   if(compare.lessThanOrEqual(elementAt(--j), o1))
   {
    break; // out of while
   }
        }
        /*
         * ����Vector={2,19,1,5,6,9,15,4}
         * ���i��λ�ò���j��λ��ǰ����i>=j��������iλ�õ�Ԫ�ر�jλ�õ�Ԫ��С�������˳��?
         * ����iλ�õ�Ԫ�ر�jλ�õ�Ԫ�ش���Ҫ��
         */
        if(i >= j) break;
        swap(i, j);
      }
      swap(i , right);
      quickSort(left, i-1);
      quickSort(i+1, right);
    }
  }
  //����λ��
  private void swap(int loc1, int loc2){
    Object tmp = elementAt(loc1);
    setElementAt(elementAt(loc2), loc1);
    setElementAt(tmp, loc2);
  }
}
