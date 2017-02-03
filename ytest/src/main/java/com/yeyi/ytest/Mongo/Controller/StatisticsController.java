package com.yeyi.ytest.Mongo.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeyi.ytest.Mongo.Service.StatisticsService;
import com.yeyi.ytest.Mongo.dto.StatisticsPlatformVO;

@Controller
@RequestMapping("/statisticsCtrl")
public class StatisticsController {
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/getList")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody List<StatisticsPlatformVO> getStatisticsPlatformList(){
		return statisticsService.getStatisticsPlatformList();
	}
	
	@RequestMapping("/findById")	// 自测如果类上的 @RequestMapping("/") 则这里可以不用 /也可以用
	public @ResponseBody StatisticsPlatformVO findById(HttpServletRequest request){
		String id = request.getParameter("id");
		return statisticsService.findById(id);
	}
}
