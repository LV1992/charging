package com.redis.framework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *使用redisTemplate操作缓存（非注解使用）
 */
@Component
public class MyCache implements Cache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void clear() {
		System.out.println("清空keys");
		redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "clear ok";
			}
		});
	}

	/* 
	 * 删除key
	 */
	@Override
	public void evict(Object key) {
		System.out.println("删除 key "+key);
		final String keyf = key.toString();
		redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connect) throws DataAccessException {
				return connect.del(keyf.getBytes());
			}
		});
	}

	/*
	 * 从缓存中获取key
	 */
	@Override
	public ValueWrapper get(Object key) {
		System.out.println("从缓存中获取key "+key);
		final String keyf = key.toString(); 
		Object object = null; 
		object = redisTemplate.execute(new RedisCallback<Object>() { 
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = keyf.getBytes(); 
				byte[] value = connection.get(key); 
				if (value == null) { return null; } 
				return toObject(value); 
				} 
			}); 
		return (object != null ? new SimpleValueWrapper(object) : null); 
	}
	
	/**从缓存中获取key
	 * @param key
	 * @return Object
	 */
	public Object getObj(Object key) {
		System.out.println("从缓存中获取key "+key);
		final String keyf = key.toString(); 
		Object object = null; 
		object = redisTemplate.execute(new RedisCallback<Object>() { 
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = keyf.getBytes(); 
				byte[] value = connection.get(key); 
				if (value == null) { return null; } 
				return toObject(value); 
			} 
		}); 
		return (object != null ? object : null); 
	}
	
	/* 
	 * 将一个新的key保存到缓存中Object key, Object value 
	 * 先拿到需要缓存key名称和对象，然后将其转成ByteArray
	 */
	@Override
	public void put(Object key, Object value) {
		System.out.println("将新的key "+key+" 和value"+value+" 保存到缓冲中");
		final String keyf = key.toString();
		final Object valuef = value;
		final long liveTime = 86400;
		redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes(); 
				byte[] valueb = toByteArray(valuef);
				connection.set(keyb, valueb);
				if(liveTime > 0){
					connection.expire(keyb, liveTime);
				}
				return 1L;
			}
		});
	}

	private Object toObject(byte[] bytes) { 
	Object obj = null; 
	try {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes); 
		ObjectInputStream ois = new ObjectInputStream(bis); 
		obj = ois.readObject(); ois.close(); bis.close(); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace(); 
				} 
	return obj; 
	}
	
	private byte[] toByteArray(Object obj) { 
		byte[] bytes = null; 
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos); 
			oos.writeObject(obj); 
			oos.flush(); 
			bytes = bos.toByteArray(); 
			oos.close(); 
			bos.close(); 
			}catch (IOException ex) { 
				ex.printStackTrace(); 
				} 
		return bytes; 
		} 

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this.redisTemplate;
	}

}
