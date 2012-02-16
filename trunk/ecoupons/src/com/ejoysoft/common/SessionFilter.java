package com.ejoysoft.common;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SessionFilter implements Filter
{
	private FilterConfig config;
	private String logon_page;
	private String nocheck_page;
	private String uncheck;

	public void init(FilterConfig filterconfig) throws ServletException
	{
		// 从部署描述符中获取登录页面和首页的URI
		config = filterconfig;
		logon_page = filterconfig.getInitParameter("logon_page");
		nocheck_page = filterconfig.getInitParameter("nocheck_page");// 过滤不检查session的路径
		uncheck = filterconfig.getInitParameter("uncheck");
		if (null == logon_page)
		{
			throw new ServletException("没有找到登录页面或主页");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;
		rsp.setContentType("text/html;GBK");
		rsp.setCharacterEncoding("GBK");
		String request_uri = req.getRequestURI(); // 得到用户请求的URI
		String ctxPath = req.getContextPath();
		UserSession userSession = (UserSession) req.getSession().getAttribute(Constants.USER_KEY);
		MemberSession membersession = (MemberSession) req.getSession().getAttribute(Constants.MEMBER_KEY);
		String mHttpUrlName = req.getContextPath();
		String mServerUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + mHttpUrlName;
		if (membersession != null)
		{
			chain.doFilter(request, response);
		}
		try
		{

			if (userSession == null
					&& membersession == null
					&& (request_uri.indexOf(logon_page) == -1 && request_uri.indexOf(uncheck) == -1 && request_uri.indexOf(nocheck_page) == -1 && !(ctxPath + "/")
							.equals(request_uri)))
			{

				PrintWriter out = rsp.getWriter();
				out.println("<script type='text/javascript'>alert('登录超时,重新登录！');top.location='" + mServerUrl + "/" + logon_page + "'</script>");
				return;
			} else
			{
				chain.doFilter(request, response);
			}
		} catch (IOException e)
		{
			e.getMessage();
		} catch (ServletException e1)
		{
			e1.getMessage();
		}

	}

	public void destroy()
	{
		config = null;
	}
}