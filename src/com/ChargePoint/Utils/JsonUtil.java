package com.ChargePoint.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.JSONArray;

/** 
 * Json工具类，实现JSON与Java Bean的互相转换 
 */ 

public class JsonUtil {
	   
	   
	    private static ObjectMapper objectMapper = new ObjectMapper();  
	    private static JsonFactory jsonFactory = new JsonFactory();  
	   
	    static {  
	        objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);  
	        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);  
	    }  
	   
	    /** 
	     * 泛型返回，json字符串转对象 
	     * 2015年4月3日上午10:42:19 
	     * auther:shijing 
	     * @param jsonAsString 
	     * @param pojoClass 
	     * @return 
	     * @throws JsonMappingException 
	     * @throws JsonParseException 
	     * @throws IOException 
	     */ 
	    public static <T> T json2Class(String jsonAsString, Class<T> pojoClass) throws JsonMappingException,  
	            JsonParseException, IOException {  
	        return objectMapper.readValue(jsonAsString, pojoClass);  
	    }  
	   
	    public static <T> T fromJson(FileReader fr, Class<T> pojoClass) throws JsonParseException, IOException {  
	        return objectMapper.readValue(fr, pojoClass);  
	    }  
	   
	    /** 
	     * Object对象转json 
	     * @param pojo 
	     * @return 
	     * @throws JsonMappingException 
	     * @throws JsonGenerationException 
	     * @throws IOException 
	     */ 
	    public static String class2Json(Object pojo) throws JsonMappingException, JsonGenerationException, IOException {  
	        return toJson(pojo, false);  
	    }  
	   
	    public static String toJson(Object pojo, boolean prettyPrint) throws JsonMappingException, JsonGenerationException,  
	            IOException {  
	        StringWriter sw = new StringWriter();  
	        JsonGenerator jg = jsonFactory.createJsonGenerator(sw);  
	        if (prettyPrint) {  
	            jg.useDefaultPrettyPrinter();  
	        }  
	        objectMapper.writeValue(jg, pojo);  
	        return sw.toString();  
	    }  
	   
	    public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint) throws JsonMappingException,  
	            JsonGenerationException, IOException {  
	        JsonGenerator jg = jsonFactory.createJsonGenerator(fw);  
	        if (prettyPrint) {  
	            jg.useDefaultPrettyPrinter();  
	        }  
	        objectMapper.writeValue(jg, pojo);  
	    }  
	   
	    /** 
	     * json字符串转Map 
	     * @param jsonStr 
	     * @return 
	     * @throws IOException 
	     */ 
	    public static Map<String, Object> jsonToMap(String jsonStr) throws IOException {  
	        Map<String, Object> map = objectMapper.readValue(jsonStr, Map.class);  
	        return map;  
	    }  
	   
	    public static JsonNode parse(String jsonStr) throws IOException {  
	        JsonNode node = null;  
	        node = objectMapper.readTree(jsonStr);  
	        return node;  
	    }  
	   
	    public static ObjectMapper getObjectMapper() {  
	        return objectMapper;  
	    }  
	       
	    /** 
	     * json字符串转 List对象 
	     * @param str   json字符串 
	     * @param clazz 转换的类型 
	     * @return 
	     */ 
	    public static <T> List<T> jsonToList(String str,Class<T> clazz){  
	        return JSONArray.parseArray(str, clazz);  
	    }  
	   
	}
