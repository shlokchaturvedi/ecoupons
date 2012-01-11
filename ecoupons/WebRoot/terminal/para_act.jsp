<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.TerminalPara,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
    TerminalPara obj=new TerminalPara(globa,false);
    String strUrl="para_list.jsp";
    String  strId=ParamUtil.getString(request,"strId","");
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	obj.delete("where strId ='"+aryStrId[i]+"'");
    	}
    	globa.dispatch(true, strUrl);
	} else {
	         String strParamName=ParamUtil.getString(request,"strParamName"," ");
	         String strParamValue=ParamUtil.getString(request,"strParamValue"," ");
	         obj.setStrParamName(strParamName);
	         obj.setStrParamValue(strParamValue);
	         obj.setStrCreator(globa.loginName);
		
	    if(action.equals(Constants.ADD_STR)) {
             if(obj.getCount(" where strparamname='" + strParamName + "'")>0){
                globa.closeCon();
                out.print("<script>alert('已经存在"+strParamName+"的名字的终端参数, 请添加其他名称的终端参数');window.location.href='javascript:history.go(-1)'</script>");
             }else{
               globa.dispatch(obj.add(),strUrl);
             }	    
		}
		else if(action.equals(Constants.UPDATE_STR)){
                 globa.dispatch(obj.update(strId),strUrl);
      }
	}
    //关闭数据库连接对象
    globa.closeCon();
%>