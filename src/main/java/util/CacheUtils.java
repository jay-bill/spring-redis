package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
@Component
public class CacheUtils {
	@Autowired
	private StringRedisTemplate redisTemplate;
	public void setRedisTemplate(StringRedisTemplate redisTemplate){
		this.redisTemplate=redisTemplate;
	}
	//存入String类型
	public void put(String key, String value) {  
		
        if (key==null || "".equals(key)) {  
            return;  
        }  
        redisTemplate.opsForHash().put(key, key, value);           
    } 
	//获取String类型
	public String get(String key) {  
		Object obj = redisTemplate.opsForHash().get(key, key);  
        if(obj == null){  
            return null;  
        }else{  
            return String.valueOf(obj);  
        }         
    } 
	
	//存放Object类型值
	public void put(String key,Object value){
		if (key==null || "".equals(key)) {  
            return;  
        }
		redisTemplate.opsForHash().put(key, key, new Gson().toJson(value));
	}
	public <T> T get(String key,Class<T> className){
		Object val = redisTemplate.opsForHash().get(key, key);
		if(val==null)
			return null;
		return new Gson().fromJson(""+val, className);
	}
}
