package com.redis.framework;

import redis.clients.jedis.Jedis;

public class RedisServer {

	 //连接本地的 Redis 服务
    Jedis jedis = new Jedis("localhost");
	
}
