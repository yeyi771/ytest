package com.yeyi.ytest.DataBase.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeyi.ytest.DataBase.model.Track;
import com.yeyi.ytest.DataBase.service.DBSer;
import com.yeyi.ytest.DataBase.service.DBService;
import com.yeyi.ytest.common.aop.WhiteList;

// 1：标注此类是一个 bean, 然后就可以 @Autowired 其它 bean 了。
// 2: 可以处理 MVC 过滤的 http 请求了。
@Controller
//自测如果这里只写 @RequestMapping("/") 则方法前的  @RequestMapping 可写成  @RequestMapping("/XXX") 也可以写成 @RequestMapping("XXX")
//注意 @RequestMapping 拼出来的 url 是区分大小写的
@RequestMapping("/DBCtrl")
public class DBCtrl {
	
	@Inject
	private DBService testService;
	
	@Inject
	private DBSer testSer;	// 证明不被事务过滤的类名
	
	// 测试读
	@RequestMapping("/GetTrackById")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody Track GetTrackById(HttpServletRequest request) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		String isMicro = request.getAttribute(WhiteList.IS_MICRO_BROWSER).toString();
		System.out.println("IS_MICRO_BROWSER: "+isMicro);
		Track track = testService.GetTrackById(id);
		//track.setJid(null);	如果设置为 null 则 @ResponseBody 转成 json 时会忽略该项
		return track; 
	}
	
	// 测试改
	@RequestMapping("/UpdateLastVisitDate")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String UpdateLastVisitDate(HttpServletRequest request) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		int result = -1;
		try{
			result = testService.UpdateLastVisitDateById(id, date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "result: " + Integer.toString(result);
	}
	
	// 调用 DBService, 可以被 database.xml 中 *Service 过滤到异常时执行事务回滚
	@RequestMapping("/UpdateRoolBackTest")
	public @ResponseBody String TestTransaction(HttpServletRequest request)throws Exception {
		String result;
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		
		// 网上说想要被事务自动回滚则不能用 try catch, 但自测用了 try catch 也没问题。
		try {
			 result = Integer.toString(testService.UpdateRoolBackTest(id, date));
         } catch (Exception e) {
        	 //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动执行事务回滚
             e.printStackTrace();
             return new String("error: " + e.getMessage());
         }
		
		return result;
	}
	
	// 调用 DBSer, 没有被 database.xml 中 *Service 过滤到不执行回滚异常时数据被修改
	@RequestMapping("/UpdateNoRoolBack")
	public @ResponseBody String TestTrans(HttpServletRequest request)throws Exception {
		String result;
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		
		// 网上说想要被事务自动回滚则不能用 try catch, 但自测用了 try catch 也没问题。
		try {
			 result = Integer.toString(testSer.UpdateRoolBackTest(id, date));
         } catch (Exception e) {
        	 //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //代码执行事务回滚
             e.printStackTrace();
             return new String("error: " + e.getMessage());
         }
		
		return result;
	}
	
	// 测试调用两个 service 是否能回滚
	@RequestMapping("/MutRoolBackTest")
	public @ResponseBody String MutRoolBackTest(HttpServletRequest request)throws Exception {
		String result;
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		
		// 网上说想要被事务自动回滚则不能用 try catch, 但自测用了 try catch 也没问题。
		try {
			 result = Integer.toString(testService.MutRoolBackTest(id, date));
         } catch (Exception e) {
        	 //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动执行事务回滚
             e.printStackTrace();
             return new String("error: " + e.getMessage());
         }
		
		return result;
	}
	
	@RequestMapping("/DeleteTrackById")
	public @ResponseBody String DeleteTrackById(HttpServletRequest request)throws Exception {
		String result;
		String id = request.getParameter("id");
		
		// 网上说想要被事务自动回滚则不能用 try catch, 但自测用了 try catch 也没问题。
		try {
			 result = Integer.toString(testService.DeleteTrackById(id));
         } catch (Exception e) {
        	 //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //代码执行事务回滚
             e.printStackTrace();
             return new String("error: " + e.getMessage());
         }
		
		return result;
	}
	
	@RequestMapping(value = "/InsertTrack", method = RequestMethod.POST)
	public @ResponseBody String InsertTrack(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "para", defaultValue = "{}") String para) throws IOException {
		return Integer.toString(testService.InsertTrackJson(para));
	}
}
