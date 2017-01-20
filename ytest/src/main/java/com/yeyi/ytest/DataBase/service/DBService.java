package com.yeyi.ytest.DataBase.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yeyi.YTool.JsonUtil;
import com.yeyi.ytest.DataBase.dao.DBDao;
import com.yeyi.ytest.DataBase.model.Track;

@Service // 将此类标记为一个服务层的 bean
//@Transactional // 这种对 RuntimeException 回滚，但对 Exception 不回滚
public class DBService {
	@Inject
	private DBDao testDao;	// 这里应该还要加一层 service 测试就省了
	
	public Track GetTrackById(String id){
		return testDao.getTrackById(id);
	}
	
	public int UpdateLastVisitDateById(String id, String date) throws Exception {
		return testDao.updateLastVisitDateById(id, date);
	}
	
	public int UpdateRoolBackTest(String id, String date) throws Exception {
		int result = 0;
		int iid = Integer.parseInt(id);
		for(int i=0; i<4; i++)
		{
			if( 3==i ) //throw new Exception("TestTransaction exception on id==" + (iid+i));
			throw new RuntimeException("运行期例外");
			result += testDao.updateLastVisitDateById( Integer.toString(iid+i), date);
		}
		
		return result;
	}
	
	public int MutRoolBackTest(String id, String date) throws Exception{
		Integer i = Integer.parseInt(id)-1;
		int result = UpdateLastVisitDateById(i.toString(), date);	// 即使是调用service内部类也会正常回滚
		result = UpdateRoolBackTest(id, date);
//		testDao.updateLastVisitDateById(id, date);
//		int iid = Integer.parseInt(id);
//		int result = 0;
//		for(int i=0; i<4; i++)
//		{
//			if( 3==i ) //throw new Exception("TestTransaction exception on id==" + (iid+i));
//				throw new RuntimeException("运行期例外");
//			result += testDao.updateLastVisitDateById( Integer.toString(iid+i), date);
//		}
		return result;
	}
	
	public int DeleteTrackById(String id) {
		return testDao.deleteTrackById(id);
	}
	
	public int InsertTrackJson(String para){
		int result = -1;

		Track track = JsonUtil.fromJson(para, Track.class);
		if( null!=track )
		result = testDao.insertTrack(track);
		
		return result;
	}
}
