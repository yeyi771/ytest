package com.yeyi.ytest.XFire_;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;  

@Controller
@RequestMapping("/XFireTest")
public class XFireTest { 
	
	@RequestMapping("/get1")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody String Get1(HttpServletRequest request){// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		String id = request.getParameter("id");
		String result = "err";

//        try {  
//            //String url = "http://localhost:8087/CxfServiceTest/services/TestService" ;
//        	String url = "http://localhost:8087/CxfServiceTest/services/TestService?wsdl";
//            Service service = new ObjectServiceFactory().create(IAnyName.class) ;  
//            XFireProxyFactory factory = new XFireProxyFactory() ;  
//            IAnyName hello = (IAnyName) factory.create(service,url) ;  
//            result = hello.GetServiceById(id) ;
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();  
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8087/CxfServiceTest/services/TestService?wsdl");
        //GetServiceById 为接口中定义的方法名称   张三为传递的参数   返回一个Object数组  
        Object[] objects;
		try {
			objects = client.invoke("GetServiceById", id);
			result = objects[0].toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
        return result;
	}
}
