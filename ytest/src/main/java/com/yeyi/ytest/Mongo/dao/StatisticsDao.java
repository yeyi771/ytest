package com.yeyi.ytest.Mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.yeyi.ytest.Mongo.dto.StatisticsPlatformVO;

@Repository
public class StatisticsDao extends MongoDao {
	
	// 使用已绑定好表名的对象(StatisticsPlatformVO)直接取数据
	public List<StatisticsPlatformVO> getStatisticsPlatformList() {
		Query query = new Query();
		List<Sort.Order> orderList = new ArrayList<Sort.Order>();
		orderList.add(new Sort.Order(Direction.ASC,"date"));
		query.with(new Sort(orderList));
		return mongoTemplate.find(query, StatisticsPlatformVO.class);
//		return mongoTemplate.find(query, StatisticsPlatformVO.class,"statistics_platform"); // 没绑定时用这个
	}
	
	//eee 这里搜索不到不知道为什么
	public StatisticsPlatformVO findById(String id) {
		Query query = new Query(Criteria.where("id").is(new ObjectId(id)));
		return mongoTemplate.findOne(query, StatisticsPlatformVO.class);
	}
}
