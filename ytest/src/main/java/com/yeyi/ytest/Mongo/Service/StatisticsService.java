package com.yeyi.ytest.Mongo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeyi.ytest.Mongo.dao.StatisticsDao;
import com.yeyi.ytest.Mongo.dto.StatisticsPlatformVO;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;

	public List<StatisticsPlatformVO> getStatisticsPlatformList(){
		return statisticsDao.getStatisticsPlatformList();
	}
	
	public StatisticsPlatformVO findById(String id){
		return statisticsDao.findById(id);
	}
}
