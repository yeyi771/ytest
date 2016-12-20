package com.yeyi.ytest.DataBase.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yeyi.ytest.DataBase.dao.DBDao;

@Service
public class DBSer {
	@Inject
	private DBDao testDao;
	
	public int UpdateRoolBackTest(String id, String date) throws Exception {
		int result = 0;
		int iid = Integer.parseInt(id);
		for(int i=0; i<4; i++)
		{
			if( 3==i ) throw new Exception("DBSer.UpdateRoolBackTest exception on id==" + (iid+i));
			result += testDao.updateLastVisitDateById( Integer.toString(iid+i), date);
		}
		
		return result;
	}
}

