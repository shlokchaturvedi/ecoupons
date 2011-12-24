<%@ page import="com.ejoysoft.ecoupons.system.SysPara,
                 com.ejoysoft.conf.SysParameter,com.ejoysoft.common.Constants"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
     SysPara   obj=new SysPara(globa,true);
    String strUrl="syspara_list.jsp";   
    
    if(action.equals(Constants.ADD_STR))  {
        globa.dispatch(obj.add(),strUrl);
    } else if(action.equals(Constants.UPDATE_STR)){
       String  strId=ParamUtil.getString(request,"strId","");
        globa.dispatch(obj.update(strId),strUrl);
    } else if(action.equals(Constants.DELETE_STR)){
      String strArrId[]=ParamUtil.getStrArray(request,"strId");
          //删除所选择的系统参数
        globa.dispatch(obj.delete(" where strId in ("+Constants.ArrayToStr(strArrId)+")"),strUrl);
    }
    SysParameter.init();
    //关闭数据库连接对象
    globa.closeCon();
    //设置成功返回页面
%>