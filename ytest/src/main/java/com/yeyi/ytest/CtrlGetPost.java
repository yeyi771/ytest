package com.yeyi.ytest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
// 自测如果这里只写 @RequestMapping("/") 则方法前的  @RequestMapping 可写成  @RequestMapping("/XXX") 也可以写成 @RequestMapping("XXX")
// 注意 @RequestMapping 拼出来的 url 是区分大小写的
@RequestMapping("/CtrlGetPost")
public class CtrlGetPost {
	
	// url 例：http://{{ytest}}/ytest/rest/CtrlGetPost/get2?id=12333只&username=abbb
	@RequestMapping("/get1")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String Get1(HttpServletRequest request) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		return "id = "+id;
	}

	@RequestMapping(value="/get2",method = RequestMethod.GET)
	public @ResponseBody String Get2(@RequestParam("username") String username, @RequestParam("id") String id) {
		return "userName = " + username + ", id = " + id;
	}
	
	@RequestMapping("/get3/{id}")
	public @ResponseBody String getProductById(@PathVariable("id") int id) {
		return "get3: id = " + id;
	}
	
	@RequestMapping("/getReDirect/{id}")
	public @ResponseBody String getReDirect(HttpServletResponse response, @PathVariable("id") int id) {
		String result = "getReDirect";
		try {
//			response.sendRedirect("../get3/"+id);	// 相对当前路径
			response.sendRedirect("/ytest/rest/CtrlGetPost/get3/"+id);	// 绝对路径，从服务器地址开始
		} catch (IOException e) {
			e.printStackTrace();
			result += (" err: " + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/get4")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String Get4(int id) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		return "id = "+id;
	}
	
	@RequestMapping("/getbool")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String Getbool(boolean bool) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		return "bool = "+bool;
	}
	
	@RequestMapping("/getBool")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String GetBool(Boolean bool) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		return "bool = "+bool;
	}
	
	@RequestMapping(value = "/post1", method = RequestMethod.POST)
	public @ResponseBody String Post1(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "para", defaultValue = "{}") String para) throws IOException {
		return "get: " + para;
	}
	
	@RequestMapping(value = "/postRedirect", method = RequestMethod.POST)
	public @ResponseBody String PostRedirect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "para", defaultValue = "{}") String para) throws IOException {
		String result = "getReDirect";
		try {
			response.sendRedirect("../post1");	// post 的参数无法传到重定向中,那重定向的接口不能是POST
//			response.sendRedirect("../get1");
		} catch (IOException e) {
			e.printStackTrace();
			result += (" err: " + e.getMessage());
		}
		return result;
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
	
	@RequestMapping(value = "/post3", method = RequestMethod.POST)
	public @ResponseBody String Post3(int id, String str) throws IOException {
		return "id: " + id + " str:"+str;
	}
	
	@RequestMapping(value = "/postbool", method = RequestMethod.POST)
	public @ResponseBody String Postbool(boolean bool, boolean bool2) throws IOException {
		return "bool: "+bool;
	}
	
	// 注意 PostClass 必须要有 public 的构造函数且必须要有 get set
	@RequestMapping(value = "/postClass", method = RequestMethod.POST)
	public @ResponseBody String PostClass(PostClass ps) throws IOException {
		return "bool: "+ps.b;
	}
//	@RequestMapping(value = "/postbool", method = RequestMethod.POST)
//	public @ResponseBody String Postbool(boolean bool, boolean bool2, boolean bool3,
//			boolean bool4, boolean bool5, boolean bool6) throws IOException {
//		return ""+bool+"\n"+bool2+"\n"+bool3+"\n"+bool4+"\n"+bool5+"\n"+bool6;
//	}
	
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
