/*
 * 
 * author: yeyi
 * date: 2017年11月23日
 */
package com.yeyi.ytest.common;

import java.io.IOException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
import org.codehaus.jackson.JsonGenerator;  
import org.codehaus.jackson.JsonProcessingException;  
import org.codehaus.jackson.map.JsonSerializer;  
import org.codehaus.jackson.map.ObjectMapper;  
import org.codehaus.jackson.map.SerializerProvider;  
import org.codehaus.jackson.map.ser.CustomSerializerFactory;  
  
/** 
 * @description 解决Date类型返回json格式为自定义格式 
 * @author aokunsang 
 * @date 2013-5-28 
 */  
public class DateObjectMapper extends ObjectMapper {
    
    /**
     * 要转换的日期格式
     * 可以从 xml 中配置，也可以从自定义的配置文件中读取
     */
    private String datePatten = "yyyy-MM-dd HH:mm:ss";
  
    public DateObjectMapper(){  
        CustomSerializerFactory factory = new CustomSerializerFactory();  
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){  
            @Override  
            public void serialize(Date value,   
                    JsonGenerator jsonGenerator,   
                    SerializerProvider provider)  
                    throws IOException, JsonProcessingException {  
                SimpleDateFormat sdf = new SimpleDateFormat(datePatten);  
                jsonGenerator.writeString(sdf.format(value));  
            }  
        });  
        this.setSerializerFactory(factory);  
    }

    public void setDatePatten(String datePatten) {
        this.datePatten = datePatten;
    }
}  