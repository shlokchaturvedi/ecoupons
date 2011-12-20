package com.ejoysoft.common;

/**
 * Created by IntelliJ IDEA.
 * User:panzw
 * Date: 2005-11-5
 * Time: 15:28:10
 * To change this template use Options | File Templates.
 */
public class ChgState {
   public static String UserState(int iState) {
        if (iState == 0) {
            return "<font color=#0000FF>��</font>";
        } else if (iState == 1) {
              return "<font color=#FF00FF>��</font>";
        } else if (iState == 2) {
            return"<font color=#FF0000>ɾ��</font>";
        } else if (iState == 3) {
            return"<font color=#FF0000>����ɾ��</font>";
        } else
            return "";
    }

    public static String UserStateNoCss(int iState) {
        if (iState == 0) {
            return "��";
        } else if (iState == 1) {
              return "��";
        } else if (iState == 2) {
            return"ɾ��";
        } else if (iState == 3) {
            return"����ɾ��";
        } else
            return "";
    }


    public static String getInfoState(int state) {
        if (state == 0) {
            return "�����";
        } else if (state == 1) {
            return "���ͨ��";
        } else if (state == -1) {
            return "������";
        } else
            return "���δͨ��";
    }

    //�ٱ��������
    public static String getDiscloseState(int state) {
        if (state == 0)
            return "<font color=#A344BB>���</font>";
        else if (state == 1)
            return "<font color=#990000>������</font>";
        else if (state == 2)
            return "<font color=##FF0000>������</font>";
        else if (state == 5)
            return "<font color=#0000FF>����</font>";
        else
            return "<font color=#000000>���</font>";
    }

    //�ٱ���ʽ
    public static String getDiscloseType(String state) {
        if (state.equals("01"))
            return "�绰";
        else if (state.equals("02"))
            return "�ż�����";
        else if (state.equals("03"))
            return "�����ʼ�";
        else
            return "4��";
    }

    public static String getCaseState(int state) {
        if (state == 0)
            return "�Ǽ���";
        else if (state == 5)
            return "������";
        else if (state ==1)
            return "�ϼ�����";
        else if (state == 2)
            return "�¼�����";
         else if (state ==3)
            return "��������λ";
        else if (state == 4)
            return "����˾������";
        else if (state == 9)
            return "���";
        return "";
    }

    //�����-���е�ֵ��ʾ״̬    String ---> String
    public static String getSelectString(String var0, String var1) {
        String state = "";
        if (var0.equals(var1))
            state = "selected";
        return state;
    }

    //�����-���е�ֵ��ʾ״̬    int  ---> int
    public static String getSelectInt(int var0, int var1) {
        String state = "";
        if (var0 == var1)
            state = "selected";
        return state;
    }

    //�����-���е�ֵ��ʾ״̬    String  ---> int
    public static String getSelecStringInt(String var0, int var1) {
        String state = "";
        if (Integer.parseInt(var0) == var1)
            state = "selected";
        return state;
    }

    //�����-���е�ֵ��ʾ״̬    int   ---> String
    public static String getSelecIntString(int var0, String var1) {
        String state = "";
        if (Integer.parseInt(var1) == var0)
            state = "selected";
        return state;
    }

    //��ѡ��ѡ����
    public static String Radio(String result) {
        String state = "";
        if (result == null || result.equals(""))
            return "";
        if (result.equals("��"))
            state = "checked";
        return state;
    }

    public static String Radio2(String result) {
        String state = "";
        if (result == null || result.equals(""))
            return "";
        if (result.equals("��"))
            state = "checked";
        return state;
    }
}
