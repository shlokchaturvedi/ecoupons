<%
/* *
 功能：支付宝服务器异步通知页面
 版本：3.2
 日期：2011-03-17
 说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 //***********页面功能说明***********
 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
 该页面调试工具请使用写文本函数logResult，该函数在com.ejoysoft.alipay文件夹的AlipayNotify.java类文件中
 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
 TRADE_FINISHED(表示交易已经成功结束，为普通即时到帐的交易状态成功标识);
 TRADE_SUCCESS(表示交易已经成功结束，为高级即时到帐的交易状态成功标识);
 //********************************
 * */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ejoysoft.alipay.*"%>
<%
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
		params.put(name, valueStr);
	}


	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

	String trade_no = request.getParameter("trade_no");				//支付宝交易号
	String order_no = request.getParameter("out_trade_no");	        //获取订单号
	String total_fee = request.getParameter("total_fee");	        //获取总金额
	String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"utf-8");//商品名称、订单名称
	String body = "";
	if(request.getParameter("body") != null){
		body = new String(request.getParameter("body").getBytes("ISO-8859-1"), "utf-8");//商品描述、订单备注、描述
	}
	String buyer_email = request.getParameter("buyer_email");		//买家支付宝账号
	String trade_status = request.getParameter("trade_status");		//交易状态
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

	if(AlipayNotify.verify(params)){//验证成功
		//////////////////////////////////////////////////////////////////////////////////////////
		//请在这里加上商户的业务逻辑程序代码

		//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
		
		if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
			//判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
			
			out.println("success");	//请不要修改或删除
		} else {
			out.println("success");	//请不要修改或删除
		}

		//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

		//////////////////////////////////////////////////////////////////////////////////////////
	}else{//验证失败
		out.println("fail");
	}
%>
