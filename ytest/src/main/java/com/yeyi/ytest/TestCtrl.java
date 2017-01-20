package com.yeyi.ytest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TestCtrl {
	@RequestMapping("get1")
	public @ResponseBody String Get1(HttpServletRequest request) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		return "id = "+id;
	}

	@RequestMapping(value="/get2",method = RequestMethod.GET)
	public @ResponseBody String Get2(@RequestParam("username") String username, @RequestParam("id") String id) {
		return "userName = " + username + ", id = " + id;
	}
	
	@RequestMapping(value = "/post1", method = RequestMethod.POST)
	public @ResponseBody String Post1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "para", defaultValue = "{}") String para) throws IOException {
		return "get: " + para;
	}
	
	// 通过传入 json 来共用同一个接口
	@RequestMapping(value = "/post2", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> Post2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "para", defaultValue = "{}") String para) throws IOException {
		Map<String, Object> result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, String> params = mapper.readValue(para, Map.class);

			if (null!=params && !params.isEmpty()) {
				result = this.PostSwitch(params, request, response);
			}
		} catch (Exception e) {
			System.out.println("TestController 查询数据异常：" + e.getMessage());
			result = new HashMap<String, Object>();
			result.put("state", 1);
			result.put("message", "TestController 查询数据异常" + e.getMessage());
		}
		return result;
	}
	
	private Map<String, Object> PostSwitch(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

//		if (!MapUtils.isNotEmpty(params))
//			return resultMap;

		// 如果这里每个方法要处理的事比较庞大可以使用类的多态来解决
		String method = params.get("method");
		if (null == method || method.length() <= 0) {
			resultMap.put("state", 1);
			resultMap.put("message", "TestController method null!");
			return resultMap;
		} else if (method.equals("updateById")) {
			String id = params.get("id");
			String name = params.get("name");
			resultMap.put("data", "get updatebyid id: " + id + ", name: " + name);
			resultMap.put("state", "0");
			return resultMap;
		} else if(method.equals("saveProduct")){
			resultMap.put("data", "get saveProduct");
			resultMap.put("state", "0");
			return resultMap;
		}else {
			resultMap.put("state", 1);
			resultMap.put("message", method + " TestController not support!");
			return resultMap;
		}
	}
}
