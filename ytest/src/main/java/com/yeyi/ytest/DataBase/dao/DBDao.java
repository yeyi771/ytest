package com.yeyi.ytest.DataBase.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yeyi.ytest.DataBase.model.Track;


@Repository
public class DBDao {
	@Autowired
	//@Qualifier("namedParameterJdbcTemplate") 自测不用写这个也是可以的
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public Track getTrackById(String id){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from  t_customer_im_track where id= :id");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);

		List<Track> track = jdbcTemplate.query(sb.toString(), paramMap, new BeanPropertyRowMapper<Track>(
				Track.class));
		if (null!=track && track.size()>0) {
			return track.get(0);
		}
		return null;
	}
	
	public int updateLastVisitDateById(String id, String lastVisit) {
		int result = -1;
		try {
				StringBuilder sb = new StringBuilder();
				sb.append("update t_customer_im_track set last_visit= :last_visit where id= :id");

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("last_visit", lastVisit);
				paramMap.put("id", id);
				
				result = jdbcTemplate.update(sb.toString(), paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("updateLastVisitDateByI Err" + ex.getMessage());
		}
		return result;
	}
	
	public int deleteTrackById(String id) {
		int result = -1;
		try {
				StringBuilder sb = new StringBuilder();
				sb.append("delete from t_customer_im_track where id= :id");

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("id", id);
				
				result = jdbcTemplate.update(sb.toString(), paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("updateLastVisitDateByI Err" + ex.getMessage());
		}
		return result;
	}
	
	public int insertTrack(Track ta){
		int result = -1;
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String sql = "insert into t_customer_im_track set jid = :jid, last_visit = now(), from_type = :fromType, from_id = :fromId, uploader_jid = :uploaderJid ";
			paramMap.put("jid", ta.getJid());
			paramMap.put("fromType", ta.getFromType());
			paramMap.put("fromId", ta.getFromId());
			paramMap.put("uploaderJid", ta.getUploaderJid());
			result = jdbcTemplate.update(sql, paramMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int updateCharTest(int id, Character c){
		int result = -1;
		try {
				StringBuilder sb = new StringBuilder();
				sb.append("update t_freight_template_rule_item set is_default = :is_default where id= :id");

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("is_default", c);
				paramMap.put("id", id);
				
				result = jdbcTemplate.update(sb.toString(), paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("updateLastVisitDateByI Err" + ex.getMessage());
		}
		return result;
	}
}
