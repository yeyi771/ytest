package com.yeyi.ytest.Mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDao {
	@Autowired
	@Qualifier("commonMongoTemplate")
	protected MongoTemplate mongoTemplate;
}
