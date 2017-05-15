package com.ChargePoint.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils<T>
{

	/**
	 * 产生指定长度的随机字符串
	 * 
	 * @param int length
	 * @return string
	 */
	public static String getStr(int length)
	{
		StringBuffer sb = null;
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();   
	    sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();  
	}
	
	public static int getInt(int min,int max){
		return (int)(min + Math.random()*(max - min + 1));
	}
	
	public static List<Integer> getIntCount(int min,int max,int count){
		List<Integer> t = new ArrayList<Integer>();
		if(count > (max - min + 1)){
			count = max - min + 1;
		}
		while(t.size() < count){
			int e = RandomUtils.getInt(min, max);
			if(!t.contains(e)){
				t.add(e);
				
			}
		}
		return t;
	}
	
	public List<T> getObject(List<T> objectList,int count){
		List<T> t = new ArrayList<T>();
		int max = objectList.size();
		if(count > max){
			count = max;
		}
		while(t.size() < count){
			int e = RandomUtils.getInt(0, max-1);
			if(!t.contains(e)){
				t.add(objectList.get(e));
			}
		}
		return t;
	}
	
//	public static void main(String[] args) {
//		List<String> sk = new LinkedList<String>();
//		sk.add("e");
//		sk.add("1e");
//		sk.add("e2");
//		sk.add("e3");
//		sk.add("e4");
//		sk.add("e5");
//		System.out.println(new RandomUtils().getObject(sk, 3));
//	}
	
}
