<%@ page import="com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.Unit,java.util.Vector,com.ejoysoft.ecoupons.system.User,java.util.HashMap,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    // ���ܲ���
    String  strId=ParamUtil.getString(request,"strId","");
     if(strId.equals(""))
       throw new IdObjectException("���������ϢidΪ�գ������Ѿ�������");
    int  intType = 0;
    if (strId.startsWith("g/"))
        intType =1;
    strId = strId.substring(strId.indexOf("/") + 1);
    String[] rights = request.getParameterValues("sel");
    /**
     * �����ɫ������Ϣ
     */
    
    if (rights!=null){
    if(action.equals(Constants.ADD_STR)){
           RoleRight obj=new RoleRight(globa);
            if(obj.delete(" where strUserId='"+strId+"' "))
            {
                if(rights!=null&&rights.length>0){
                    if(intType==1){
                        obj.add(rights,strId,intType);
                        int iFlase=0;
                         Unit unit=new Unit(globa,false);
                        Unit unit0=new Unit();
                        unit.oneDeptColId(strId);
                        Vector colTree = unit.getDepColTree();  //���ŵ�����Ŀ¼����������
                       String strUnits="''";
                        for (int i = 0; colTree!=null&&i < colTree.size(); i++) {
                             unit0 = (Unit)colTree.get(i);
                             strUnits+=",'"+unit0.getStrId()+"'";
                        }
                       HashMap maps=new User(globa).unitUser(" WHERE a.strUnitId IN ("+strUnits+") ");
                        if (maps != null) {
                            java.util.Iterator itAddr = maps.values().iterator();
                            int i=0;
                            while (itAddr.hasNext()) {
                                if(!obj.add(rights,(String) itAddr.next(),0))
                                     iFlase++;
                            }
                        }
                         //���¼��ص�λ��Ϣ
                        SysUserUnit.reLoad("");
                        if(iFlase==0)
                              out.print("<script>alert('Ȩ�޷���ɹ���');window.close();</script>");
                        else
                            out.print("<script>alert('Ȩ�޷���ʧ�ܣ�');window.close();</script>");
                    } else
                        if(obj.add(rights,strId,intType))
                            out.print("<script>alert('Ȩ�޷���ɹ���');window.close();</script>");
                        else
                            out.print("<script>alert('Ȩ�޷���ʧ�ܣ�');window.close();</script>");
                }
            }
      }
      }
      else{
        out.print("<script>alert('Ȩ�޷���ʧ�ܣ�');window.close();</script>");
        
      }
    //�ر����ݿ����Ӷ���
    globa.closeCon();
%>
