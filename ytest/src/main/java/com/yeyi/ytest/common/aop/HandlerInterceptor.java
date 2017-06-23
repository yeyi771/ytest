package com.yeyi.ytest.common.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.yeyi.YTool.JsonUtil;
import com.yeyi.ytest.common.JsonResult;
import com.yeyi.ytest.common.Status;
import com.yeyi.ytest.util.StringUtil;


public class HandlerInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		init(request, response, handler);
		
		request.setAttribute(WhiteList.IS_MICRO_BROWSER, isMicroMsgBrowser(request));
		
		return filter(request, response, handler);
	}

	/**
	 * after controller before show view 
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		destoryContextThread();
	}

	public void init(HttpServletRequest request, HttpServletResponse response, Object handler) {
		InterceptorUtil.printLog(request,handler);
	}

	public void destoryContextThread() {
	}
	
	/**
	 * 判断是否微信浏览器
	 * 自测微信 PC 端 request.getHeader("user-agent") 返回如下：
	 * ----Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.27.400 QQBrowser/9.0.2524.400
	 */
	protected boolean isMicroMsgBrowser(HttpServletRequest request){
		String agent = request.getHeader("user-agent");
		if( StringUtil.isEmpty(agent) )
			return false;
		
		return -1!=agent.toLowerCase().indexOf("micromessenger");
	}
	
	protected boolean filter(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws IOException{
		
		refreshToken(request);
		if( isNeedCheckToken(request, response, handler) ){
			if( !checkToken(request, response, handler) ){
				setResponse(Status.SYS_TOKEN_INVALID_ERROR, response);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查 token 是否有效
	 * @return 返回是否有效
	 */
	protected boolean checkToken(HttpServletRequest request, HttpServletResponse response, Object handler){
		return true;
//		String token = request.getParameter(InterceptorUtil.TOKEN_NAME);
//		if( StringUtil.isEmpty(token) )
//			return false;
//		return null!=accountService.getCustomerTokenData(token);
	}
	
	/**
	 * （过滤失败时）修改返回状态
	 * @param state
	 * @param response
	 * @throws IOException 
	 */
	protected void setResponse(JsonResult result, HttpServletResponse response) throws IOException{
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			pw = response.getWriter();
			pw.println(JsonUtil.getJSONString(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getJSONString err: {}", e.toString());
		}
		finally {
			IOUtils.closeQuietly(pw);
		}
	}
	
	protected void setResponse(Status state, HttpServletResponse response) throws IOException{
		setResponse(new JsonResult(state), response);
	}
	
	protected boolean isNeedCheckToken(HttpServletRequest request, HttpServletResponse response, Object handler){
		Set<String> ignoreUri = WhiteList.getIgnoreUriSet();
		String url = request.getRequestURI();
		if( ignoreUri.contains(url) )
			return false;
		
		Set<String> ignoreClases = WhiteList.getIgnoreClissSet(); 
		String className = handler.getClass().getSimpleName();
		if( ignoreClases.contains(className) )
			return false;
		
		return true;
	}
	
	protected boolean refreshToken(HttpServletRequest request){
		return true;
//		String token = request.getParameter(InterceptorUtil.TOKEN_NAME);
//		if( StringUtil.isEmpty(token) )
//			return false;
//		
//		return accountService.refreshTokenTimeOutCustomer(token);
	}
}
