<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ejoysoft.ecoupons.business.Terminal,
				 com.ejoysoft.common.Constants,
				 com.ejoysoft.util.ParamUtil,
				 com.ejoysoft.ecoupons.business.CouponComment" %>
<%@ include file="../include/jsp/cpage.jsp" %>
<%
    CouponComment obj=new CouponComment(globa);
    String strUrl="couponinfo.jsp";
    String  strId=ParamUtil.getString(request,"strId","");
	         System.out.println(action);
    if(action.equals(Constants.DELETE_STR)){
    	String[] aryStrId = ParamUtil.getStrArray(request, "strId");
    	for (int i = 0; i < aryStrId.length; i++) {
	    	//obj.delete("where strId ='"+aryStrId[i]+"'",aryStrId[i]);
    	}
    	globa.dispatch(true, strUrl);
	} 
	else {
	         String strComment=ParamUtil.getString(request,"strcomment"," ");	
	         String strCouponId=ParamUtil.getString(request,"setcouponid"," ");	        
	         obj.setStrComment(strComment);
	         obj.setStrCreator(globa.loginName);
	         obj.setStrCouponId(strCouponId);
	         obj.setStrMemberCardNo("23423423423423423");
		
	    if(action.equals(Constants.ADD_STR)) {
             if(strComment==null||strComment.trim().equals("")){
                globa.closeCon();
                out.print("<script>alert('您的优惠券评价内容为空！评价失败！');window.location.href='javascript:history.go(-1)'</script>");
             }else{
               globa.dispatch(obj.addComment(),strUrl);
             }	    
		}
	}
    //关闭数据库连接对象
    globa.closeCon();
%>