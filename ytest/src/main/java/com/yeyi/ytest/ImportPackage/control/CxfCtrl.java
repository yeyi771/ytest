package com.yeyi.ytest.ImportPackage.control;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeyi.ytest.ImportPackage.service.CfxService;

@Controller
@RequestMapping("/CxfCtrl")
public class CxfCtrl {
	@Inject
	CfxService cfxService;
	//CfxService cfxService = new CfxService();
	
	@RequestMapping("/get1")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String Get1(HttpServletRequest request) throws Exception {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		return id;
//		return cfxService.GetServiceById(id);
	}
}
