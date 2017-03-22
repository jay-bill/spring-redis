package main;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import util.CacheUtils;
@Component
public class Main {
	public static void main(String [] args){
		ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
		CacheUtils cacheUtils = (CacheUtils)app.getBean("cacheUtils");
		//加入String到redis
		cacheUtils.put("jaybill", "这是我的昵称");
		//获取string
		String t = cacheUtils.get("jaybill");
		System.out.println(t);
		
		//加入Object到redis
		cacheUtils.put("obj类型", new Date());
		//获取Object的值
		Date d = cacheUtils.get("obj类型",Date.class);
		System.out.println(d.toString());
	}
}
