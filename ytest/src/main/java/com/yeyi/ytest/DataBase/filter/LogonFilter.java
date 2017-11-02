package com.yeyi.ytest.DataBase.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LogonFilter implements Filter {
	private FilterConfig filterConfig;
	private String[] urls = new String[] {};
	private String[] webViewUrl = new String[] {};

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			// 设置编码格式
			req.setCharacterEncoding("UTF-8");
			HttpServletRequest request = (HttpServletRequest) req;
			
			System.out.println("--------------LogonFilter doFilter: "+request.getRequestURL());

			chain.doFilter(req, res);
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		// 读取配置文件中的初始化参数
		String filterurl = filterConfig.getInitParameter("filterurl");
		if (filterurl != null) {
			String[] tmps = filterurl.split(",");
			this.urls = new String[tmps.length];
			for (int i = 0; i < tmps.length; i++) {
				this.urls[i] = tmps[i].trim();// 去掉空格
			}
		}
		
		// 读取配置文件中的初始化参数
		String webViewUrl = filterConfig.getInitParameter("webViewUrl");
		if (filterurl != null) {
			String[] tmps = webViewUrl.split(",");
			this.webViewUrl = new String[tmps.length];
			for (int i = 0; i < tmps.length; i++) {
				this.webViewUrl[i] = tmps[i].trim();// 去掉空格
			}
		}
	}
}

