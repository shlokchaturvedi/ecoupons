<%@ page import="com.ejoysoft.ecoupons.system.Unit,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    Unit obj=new Unit(globa,true);
    if (request.getParameter("strAFlag") == null)
    	obj.setStrAFlag("");
    String strUrl=ParamUtil.getString(request,"strUrl","unit_list.jsp");
        if(action.equals(Constants.ADD_STR))  {
           strUrl="unit_add.jsp?strUnitId="+obj.getStrParentId() ;
           globa.dispatch(obj.add(),strUrl);
       }else  if(action.equals(Constants.DELETE_STR)){
            //��֤�Ƿ����ɾ������
            //������û���¼��û�����û��������ɾ��
           String strUserId=ParamUtil.getString(request,"strUnitId","");
           SysUserUnit.validateDelId(strUserId);
           globa.dispatch(obj.delete("where strId ='"+strUserId+"'"),strUrl);
      }else if(action.equals(Constants.UPDATE_STR)){
            String  strUnitId=ParamUtil.getString(request,"strId","");
            //��֤�Ƿ������ѭ��
            SysUserUnit.validateId(strUnitId, obj.getStrParentId());
            globa.dispatch(obj.update(strUnitId),strUrl);
      }
    //���¼��ص�λ��Ϣ
   SysUserUnit.reLoad("");
    //�ر����ݿ����Ӷ���
    globa.closeCon();

%>