package com.yeyi.test;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class FastjsonTest {
    public static void main(String[] args) {  
        //方法１  
        String json = "[{\"companyId\":\"111111111\",\"companyName\":\"Huuuu\",\"_uid\":10,\"_index\":0,\"_state\":\"modified\"},{\"companyId\":\"000000000000000000\",\"companyName\":\"cx01\",\"_uid\":11,\"_index\":1,\"_state\":\"modified\"},{\"companyId\":\"9999999999999\",\"companyName\":\"ttt\",\"_uid\":12,\"_index\":2,\"_state\":\"modified\"}]";  
        List<HashMap> list =JSON.parseArray(json, HashMap.class);  
        for(int i=0;i<list.size();i++){  
          System.out.println(list.get(i).get("companyId"));;  
        }  
        //方法２  
        /* 
        JSONArray jarr = JSONArray.parseArray(json); 
        for (Iterator iterator = jarr.iterator(); iterator.hasNext();) { 
          JSONObject job = (JSONObject) iterator.next(); 
          System.out.println(job.get("companyId").toString()); 
        }*/  
    }
}
