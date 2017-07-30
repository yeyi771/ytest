package com.yeyi.ytest.excel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yeyi.ytest.excel.util.ExcelReadUtil;
import com.yeyi.ytest.excel.util.SheetConfig;

@Controller
@RequestMapping("/excel")
public class ExcelTest {

	private final static Map<String, String> colMap = new HashMap<String, String>();
	static {
		colMap.put("province", "省");
		colMap.put("city", "市");
		colMap.put("county", "区/县");
		colMap.put("zipCode", "邮编");
	}

	@RequestMapping("/read")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	@ResponseBody
	public String read(HttpServletRequest request) {// 不加 @ResponseBody 将默认返回XXX.jsp 页面的名称，不存在则出错
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile excelFile = req.getFile("excel");

		// 文件名
		String fileName = excelFile.getOriginalFilename();
		// 后缀名
		String exName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		try {
			Workbook workbook = null;
			if ("xls".equals(exName)) {
//				logger.info("Excel版本 version = 2003");
				workbook = new HSSFWorkbook(excelFile.getInputStream());
			} else {
				workbook = new XSSFWorkbook(excelFile.getInputStream());
			}
			List<Map<String, Object>> list = ExcelReadUtil.parseExcel(workbook, colMap, new SheetConfig());
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
			return list.toString();
		} catch (IOException e) {
//			logger.error("导入包裹错误，文件为:" + e.toString());
			return "/homepage/import_pkg";
		}
	}
}
