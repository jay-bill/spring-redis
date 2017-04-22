# spring-redis
redis整合spring
步骤：
1、在pom.xml中引入spring的依赖、jedis的依赖（两个，另一个是common-pool）、Gson的依赖
2、在类加载路径下，新建redis.properties文件，里面配置好redis连接池的参数；
3、在spring的配置文件applicationConext.xml中，添加如下内容：
	1）引入propertis文件
		<context:property-placeholder location="classpath:redis.properties"></context:property-placeholder>
	2）配置jedisPoolConfig的参数
		<bean id="jedisConfig" class="redis.clients.jedis.JedisPoolConfig"></bean>
	3）配置连接池的工厂（包括连接池、ip和端口）
		<bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
			<property name="hostName" value="${redis-address}"></property>  
	        <property name="port" value="${redis-port}"></property>  
	        <property name="poolConfig" ref="jedisConfig"></property>  
		</bean>
	4）配置redis的模板
		<bean id="redisTemplate" class="org.springframework.data.redis.core.StringRedisTemplate">
			<property name="connectionFactory" ref="connectionFactory" />  
	    	<!--如果不配置Serializer，那么存储的时候只能使用String，如果用对象类型存储，那么会提示错误 can't cast to String！！！-->  
	        <property name="keySerializer">  
	            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer" />  
	        </property>  
	        <property name="valueSerializer">  
	            <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer" />  
	        </property>
		</bean>
4、在需要用到redis缓存的类中，让StringRedisTemplate redisTemplate成为成员变量，调用其中的方法：
	1）仅仅操作字符串：
		写入：redisTemplate.opsForHash().put("key","key","value");
		读取：redisTemplate.opsForHash().get("key","key");
	
	2）操作Object类型，需要引入Gson类
		写入：redisTemplate.opsForHash().put("key","key",new Gson().toJson("value"));
		读取：new Gson().fromJson(redisTemplate.opsForHash().get("key","key"));
