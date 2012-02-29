<%@ page import="com.ejoysoft.ecoupons.system.RoleRight,com.ejoysoft.common.exception.IdObjectException,com.ejoysoft.ecoupons.system.Unit,java.util.Vector,com.ejoysoft.ecoupons.system.User,java.util.HashMap,com.ejoysoft.ecoupons.system.SysUserUnit,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    // 接受参数
    String  strId=ParamUtil.getString(request,"strId","");
     if(strId.equals(""))
       throw new IdObjectException("请求处理的信息id为空！或者已经不存在");
    int  intType = 0;
    if (strId.startsWith("g/"))
        intType =1;
    strId = strId.substring(strId.indexOf("/") + 1);
    String[] rights = request.getParameterValues("sel");
    /**
     * 保存角色分配信息
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
                        Vector colTree = unit.getDepColTree();  //部门的树形目录的所有向量
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
                         //重新加载单位信息
                        SysUserUnit.reLoad("");
                        if(iFlase==0)
                              out.print("<script>alert('权限分配成功！');window.close();</script>");
                        else
                            out.print("<script>alert('权限分配失败！');window.close();</script>");
                    } else
                        if(obj.add(rights,strId,intType))
                            out.print("<script>alert('权限分配成功！');window.close();</script>");
                        else
                            out.print("<script>alert('权限分配失败！');window.close();</script>");
                }
            }
      }
      }
      else{
        out.print("<script>alert('权限分配失败！');window.close();</script>");
        
      }
    //关闭数据库连接对象
    globa.closeCon();
%>
