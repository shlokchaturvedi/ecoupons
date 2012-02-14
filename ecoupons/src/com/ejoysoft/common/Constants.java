package com.ejoysoft.common;

/**
 * �ļ���ƣ�CacheNames.java
 * @author feiwj    �޶��汾
 * @version $Revision: 1.0 $ $Date: 2004-11-25 11:31 $
 */
public final class Constants {
    public Constants() {
    }

    //ȫ�ֳ���־
    public final static String DB_JNDI_NAME = "DB_JNDI_NAME";
    public final static String SYSTEM_DOM = "sysdomid";
    public final static String _IKEYRANDOM = "ikeyRandom";
    public final static String OnlineUserInfo = "_OnlineUserInfo";
    public final static String OnlineName = "_OnlineName";
    public final static String USER_KEY = "_UserSession";
    public final static String MEMBER_KEY = "_MemberSession";
    public final static String MAIL_DEFINED = "Mail_Defined";
    public static final String CHECK_NUM = "_CheckNum";
    public static final String Error_Lock_Num = "intErrorLockNum";
    //ȫ�ֳ���־
    public static final String PASSPROT = "_sessionPassport";
    public static final String Administrator = "sxoa";
    public static final String initPass = "test";
    public static final String resetPass = "123456";
    public final static String NORIGHT_PAGE = "";
    //��ҳ������
    public static final int PAGE_SIZE_1024 = 15;
    public static final int PAGE_SIZE_800 = 12;
    public static final int PAGE_SIZE_600 = 10;
    public static final String PAGE_SIZE_INDEX = "PageSizeIndex";
    public static final String PAGE_TOTAL_COUNT = "PageTotalCount";
    //active
    public final static String ACTION_TYPE = "actiontype";
    //���
    public final static String ADD_STR = "add";
    //�޸�
    public final static String UPDATE_STR = "update";
     //����
    public final static String SEND_STR = "send";
    //����ɾ��
    public final static String DELETE_STR = "delete";
    //导出
    public final static String EXPORT_STR = "export";
  //审核
    public final static String AUDIT_STR = "audit";
    //充值
    public final static String RECHARGE_STR = "recharge";
    //消费
    public final static String CHARGE_STR = "charge";
    //转赠
    public final static String PRESENT_STR = "charge";
    //���ɾ��
    public final static String REMOVE_STR = "remove";
    //�鿴
    public final static String VIEW_STR = "view";
    //�ϱ�
    public final static String UP_STR = "upload";
     //�ջ�
    public final static String BACK_STR = "recback";
    //����
    public final static String SENDCHK_STR = "sendchk";
    //���
    public final static String CHECK_STR = "check";
    //����
    public final static String PUB_STR = "pub";
     //�鵵
    public final static String ARCH_STR = "arch";
    //��½��־
    public final static String LOGON = "logon";
    //网站登录标识
    public final static String WEBLOGON = "weblogon";
    //网站退出标识
    public final static String WEBLOGOFF = "weblogoff";
    //mac��½��־
    public final static String MACLOGON = "maclogon";
    //�˳��־
    public final static String LOGOFF = "logoff";
    //��ʾ��Ϣ   Appclass
    public final static String MSSAGE = "mssage";
    //�û�״̬-��
    public final static String LOCK_STR = "lock";
    //�û�״̬-����
    public final static String NORMAL_STR = "nornal";
    //��������
    public final static String SETPASS = "setpass";
    //�޸�����
    public final static String MODPASS = "modpass";
    //mac��ַ
    public final static String MACADD = "macadd";
    public final static String EXCHANGERATE = "10";


    //������ת��Ϊ�ַ�
    public static String ArrayToStr(String[] strArrId) {
        String strIds = "";
        for (int i = 0; strArrId != null && i < strArrId.length; i++) {
            if (i == 0)
                strIds = "'" + strArrId[i] + "'";
            else {
                strIds += ",'" + strArrId[i] + "'";
            }
        }
        return strIds;
    }

    //������ת��Ϊ�ַ�2
    public static String ArrayToStr2(String[] strArrId) {
        String strIds = "";
        for (int i = 0; strArrId != null && i < strArrId.length; i++) {
            if (i == 0)
                strIds = "" + strArrId[i] + "";
            else {
                strIds += "," + strArrId[i] + "";
            }
        }
        return strIds;
    }

    //������ģ�����
    public final static String AM_NAME_FWGL = "���Ĺ���";
    public final static String AM_NAME_SWGL = "���Ĺ���";
    public final static String AM_NAME_TZGG = "֪ͨ����";
    public final static String AM_NAME_KWGL = "�ڲ���";
//    public final static String AM_NAME_KWGL = "���ӿ���";
    public final static String AM_NAME_GWJDCPDGL = "�Ӵ����";
    public final static String AM_NAME_CLGL = "��~����";
    public final static String AM_NAME_YFKJK = "Ԥ���������";
    public final static String AM_NAME_QSYJY = "��ʾ�뽨�����";
    public final static String AM_NAME_NBQB = "�ڲ�ǩ��";
    public final static String AM_NAME_HYSGL = "�����ҹ���";
    public final static String AM_NAME_HWGL= "�������";
    public final static String AM_NAME_ZBGL= "ֵ�����";
    public final static String AM_NAME_LDHDAP= "�쵼�����";
    public final static String AM_NAME_JDGL= "�Ӵ����";
    public final static String AM_NAME_DBGL= "�������";
    public final static String AM_NAME_GZFX ="��������";
    public final static String AM_NAME_BGYP ="�칫��Ʒ";
    public final static String AM_NAME_XXSS= "��Ϣ����";
    public final static String AM_NAME_ZXFW= "���߷���";
    public final static String AM_NAME_SQD ="�����������";
    public final static String AM_NAME_DCFW ="�紫����";
    public final static String AM_NAME_DCSW ="�紫����";
    public final static String AM_NAME_CJGL = "��ٹ���";
    public final static String AM_NAME_XCCJ = "�����";
    public final static String WC_STATE_FW = "����";
    public final static String WC_STATE_SW = "����";
    public final static String WC_STATE_MW = "����";



    //�������ĵ����񼶱�  Priority
    public final static String WC_PRIORITY_PT = "��ͨ";                     //NORMAL
    public final static String WC_PRIORITY_JJ = "��";                 //URGENCY
    public final static String WC_PRIORITY_TJ = "�ؼ�";                   // ESPURGENCY
    public final static String WC_STATE_DGZ = "�����";
    public final static String WC_STATE_YGZ = "�Ѹ���";
    public final static String WC_STATE_WZR = "δת��";
    public final static String WC_STATE_YZR = "��ת��";
    public final static String WC_STATE_DFF = "��ַ�";
    public final static String WC_STATE_YFF = "�ѷַ�";

    public final static String WC_STATE_DFB = "��";
    public final static String WC_STATE_YFB = "�ѷ���";
    public final static String WC_STATE_DFS = "����";
    public final static String WC_STATE_DBL = "�����";
    public final static String WC_STATE_YLL = "�����";
    public final static String WC_STATE_DHF = "��ظ�";
    public final static String WC_STATE_YHF = "�ѻظ�";
    public final static String WC_STATE_BLZ = "������";
    public final static String WC_STATE_YBL = "�Ѱ���";
    public final static String WC_STATE_DBJ= "����";
    public final static String WC_STATE_YBJ = "�Ѱ��";
    public final static String WC_STATE_YZF = "�����";
    public final static String WC_STATE_QXSH = "ǿ���ջ�";
    public final static String WC_STATE_YGQ = "�ѹ���";
    public final static String WC_STATE_CXQD = "������";
    public final static String WC_STATE_ZBZ = "ת����";
    public final static String WC_STATE_YQX = "��ȡ��";

    public final static String WC_STATE_DGD = "��鵵";
    public final static String WC_STATE_YGD = "�ѹ鵵";
    //���ķ�ʽ
    public final static String DC_SENDTYPE_ZC = "����";
    public final static String DC_SENDTYPE_CF = "����";
    //�ļ��ַ�״̬
    public final static String DC_STATE_DQS = "��ǩ��";
    public final static String DC_STATE_YQS = "��ǩ��";
    public final static String DC_STATE_JS = "�Ѿ���";
    public final static String DC_STATE_YQT = "������";
    public final static String DC_STATE_WQT = "δ����";
    /*
  * DEFINE USER STATE STATIC FINAL VAR
  * NORMAL->��  AND��LOCK->��   AND��DELETE->����ɾ��   AND��REMOVE->����ɾ��
  */
    public final static int U_STATE_DEL = 2;//2;       //DELETE
    public final static int U_STATE_LOCK = 1;//1;    //LOCK
    public final static int U_STATE_ON = 0;//0;         //NORMAL
    public final static int U_STATE_REMOVE = 3;//3;         //REMOVE
   //�ĵ����Ͽ��ļ�״̬
    public final static String DB_STATE_ZC= "0";
    public final static String DB_STATE_BJ = "1";
    //  �ĵ����Ͽ��״̬
    public final static String DB_PROPERTY_KKZ= "�ɿ���";
    public final static String DB_PROPERTY_KXG= "���޸�";
    public final static String DB_PROPERTY_ZD= "ֻ��";

    //����������״̬
    public final static String HYS_SQZ="������";
    public final static String HYS_YQR="��ȷ��";
    public final static String HYS_YCX="�ѳ���";

    //��~״̬
    public final static String AUTO_FREE="����";
    public final static String AUTO_OUT="��";
    public final static String YCY="�Ѳ���";
    public final static String DCY="�����";


    //�칫��Ʒ
    public final static String GOODS_WBS="δ����";
    public final static String GOODS_WQR="δȷ��";
    public final static String GOODS_YQR="��ȷ��";
    public final static String GOODS_BTG="��ͨ��";



    //  ������Ϣ8Ŀ�������
    public final static String WEB_JGSZ = "10";  //"������";
    public final static String WEB_ZWYW = "20";  //"����Ҫ��";
    public final static String WEB_LDWK = "30";  //"�쵼�Ŀ�";
    public final static String WEB_ZWDC = "40";  //"���񶽲�";
    public final static String WEB_ZLZX = "50";  //"��������";
    public final static String WEB_FLFG = "60";  //"���ɷ���";

    public final static String WEB_YQLJ = "70";   //����t��
    public final static String WEB_ZFWJ = "80";   //�����ļ�

    public final static String[][] ARRAY_WEB =    //
            new String[][] {
                { WEB_JGSZ,"������" },
                { WEB_ZWYW,"����Ҫ��" },
                { WEB_LDWK,"�쵼�Ŀ�" },
                { WEB_ZWDC,"���񶽲�" },
                { WEB_ZLZX,"��������" },
                { WEB_FLFG,"���ɷ���" },
            };


}
