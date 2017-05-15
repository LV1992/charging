package com.redis.test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class connect {
	   public static void main(String[] args) {
		      //连接本地的 Redis 服务
//		      Jedis jedis = new Jedis("localhost");
		      Jedis jedis = new Jedis("120.76.65.195");
		      jedis.auth("chinajune");
//		      jedis.append("54", "value");
//		      jedis.append("54", "value");
//		      jedis.del("54");
//		      System.out.println(jedis.get("oYW4uwPiQI-MvnD5N7TYi7PwXVmo"));
//		      System.out.println(jedis.get("user1"));
//		      System.out.println(jedis.del("user1"));
//		      System.out.println(jedis.del("accessToken"));
//		      System.out.println(jedis.del("user1"));
//		      System.out.println(jedis.keys("*"));
//		      System.out.println("系统中所有键如下：");
		      Set<String> keys = jedis.keys("*"); 
		        Iterator<String> it=keys.iterator() ;   
		        while(it.hasNext()){   
		            String key = it.next(); 
//		            jedis.del(key);
		            System.out.println("key: "+key+" value: "+jedis.get(key)); 
		        }
		        // 删除某个key,若key不存在，则忽略该命令。
		        //System.out.println("系统中删除key002: "+jedis.del("key002"));
		        //System.out.println("判断key002是否存在："+jedis.exists("key002"));		      
		      //查看服务是否运行
//		      System.out.println("Server is running: "+jedis.ping());
		    //设置 redis 字符串数据
//		      jedis.set("test", "redis测试");
		      //删除redis数据
//		      jedis.del("test");
//		      jedis.del("test-list");
		     // 获取存储的数据并输出
//		     System.out.println("redis字符串测试:: "+ jedis.get("test"));
//		     System.out.println("redis已存字符串age测试:: "+ jedis.get("age"));
		     
		     /*Redis Java List(列表) 实例*/
		     //存储数据到列表中
//		      jedis.lpush("test-list", "Redis");
//		      jedis.lpush("test-list", "Mongodb");
//		      jedis.lpush("test-list", "Mysql");
		     // 获取存储的数据并输出
		     /*
		     List<String> list = jedis.lrange("test-list", 0 ,5);
		     for(int i=0; i<list.size(); i++) {
		       System.out.println("Stored string in redis:: "+list.get(i));
		     }
		     */
		     // 清空数据 
//		     System.out.println("清空库中所有数据："+jedis.flushDB());  
		        
		        
	 }
}
