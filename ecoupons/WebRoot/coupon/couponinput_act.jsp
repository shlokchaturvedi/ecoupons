<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.ejoysoft.ecoupons.system.User,com.ejoysoft.common.Constants,com.ejoysoft.util.ParamUtil,com.ejoysoft.ecoupons.system.SysUserUnit"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponInput"%>
<%@page import="com.ejoysoft.ecoupons.business.CouponPrint"%>
<%@page import="com.ejoysoft.ecoupons.business.Shop"%>
<%@page import="com.ejoysoft.ecoupons.business.Coupon"%>
<%@ include file="../include/jsp/head.jsp"%>
<%
	CouponInput obj = new CouponInput(globa, true);
	String strUrl = "couponinput_list.jsp";
	if (action.equals(Constants.DELETE_STR))
	{
		String[] aryStrId = ParamUtil.getStrArray(request, "strId");
		for (int i = 0; i < aryStrId.length; i++)
		{
			obj.delete("where strId ='" + aryStrId[i] + "'");
		}
		globa.dispatch(true, strUrl);
	} else
	{
		Shop shop=new Shop();
		String strCouponCode = ParamUtil.getString(request, "strCouponCode", "");
		String strMemberCardNo = ParamUtil.getString(request, "strMemberCardNo", "");
		String strId =ParamUtil.getString(request, "strId", "");
		CouponPrint couponPrint = new CouponPrint(globa, true);
		CouponPrint objCouponPrint = couponPrint.show(" where strCouponCode='" + strCouponCode + "' and strMemberCardNo='"+strMemberCardNo+"' and intstate=0");
	    CouponPrint objCouponPrint2 = couponPrint.show(" where strCouponCode='" + strCouponCode + "' and strMemberCardNo='"+strMemberCardNo+"' and intstate=1");
		if (action.equals(Constants.ADD_STR))
		{
			
			if (objCouponPrint!=null)
			{
				obj.setStrCouponId(objCouponPrint.getStrCouponId());
				Coupon obj2 = new Coupon(globa);
				Coupon objCoupon = obj2.show(" where strid='"+objCouponPrint.getStrCouponId()+"'");				
				obj.setStrShopId(objCoupon.getStrShopId());
				obj.setDtPrintTime(objCouponPrint.getDtPrintTime());
				obj.setStrCouponCode(strCouponCode);
				obj.setStrMemberCardNo(strMemberCardNo);
				globa.dispatch(obj.add(), strUrl);
			} else
			{
				out.print("<script>alert('录入错误：券面代码 和会员卡号的打印记录无效或者已经录入！ ');</script>");
				globa.dispatch(false, strUrl, "录入错误：券面代码 和会员卡号的打印记录无效或者已经录入！");
			}
		} else if (action.equals(Constants.UPDATE_STR))
		{ 
			if(objCouponPrint2!=null)
			{
			    obj.setStrCouponCode(strCouponCode);
				obj.setStrMemberCardNo(strMemberCardNo);
			    globa.dispatch(obj.update(strId), strUrl);					
			}
			else{
				out.print("<script>alert('编辑错误：券面代码 和会员卡号的打印记录无效或者尚未录入！ ');</script>");
				globa.dispatch(false, strUrl,"编辑错误：券面代码 和会员卡号的打印记录无效或者尚未录入！");
			}
					 
		}
	}
	//关闭数据库连接对象
	globa.closeCon();
%>