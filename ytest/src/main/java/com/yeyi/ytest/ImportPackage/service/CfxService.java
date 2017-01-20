package com.yeyi.ytest.ImportPackage.service;

import org.springframework.stereotype.Service;
import javax.inject.Inject;
import com.service.TestService;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.apache.cxf.endpoint.Client;

@Service	// 不加这个 @Inject 会有运行异常
public class CfxService {
	@Inject
	public TestService ts;
	
	public String GetServiceById(String id) throws Exception{
		return ts.GetServiceById(id);
		//return "GetServiceById: " + id;
//		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//		Client client = factory.createClient("http://localhost:8087/CxfServiceTest/services/TestService?wsdl");
//		Object[] objs = client.invoke("GetServiceById", id);
//		return objs[0].toString();
	}
}
