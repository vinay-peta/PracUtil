package com.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertyFileHandler {

	private String propertyFilePath;
	
	public PropertyFileHandler(String path){
		this.propertyFilePath=path;
	}
	
	public static void main(String... a) throws IOException{
		PropertyFileHandler handler = new PropertyFileHandler("src/main/resources/properties/NovusFields.properties");
		Map<String,String> map = handler.getOrderedMap();

		for(Map.Entry<String,String> entry : map.entrySet()){
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}
	
	public Properties getProperties() throws IOException{
		Properties properties = new Properties();
		InputStream propertiesFile = new FileInputStream(propertyFilePath);
		properties.load(propertiesFile);
		return properties;
	}
	
	/**
	 * Get Property file key-values as a map where the order is persisted
	 * @return
	 * @throws IOException
	 */
	public Map<String,String> getOrderedMap() throws IOException{
		
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		LinkedProperties properties = new LinkedProperties();
		InputStream propertiesFile = new FileInputStream(propertyFilePath);
		properties.load(propertiesFile);
		
		Iterator<Object> itr = properties.orderedKeys().iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			map.put(key,properties.getProperty(key));
		}
		return map;
	}
	
	
	/**
	 * Get Property file key-values as a map where the order is persisted
	 * @return
	 * @throws IOException
	 */
	public List<String> getValues() throws IOException{
		
		List<String> list = new LinkedList<String>();
		LinkedProperties properties = new LinkedProperties();
		InputStream propertiesFile = new FileInputStream(propertyFilePath);
		properties.load(propertiesFile);
		
		Iterator<Object> itr = properties.orderedKeys().iterator();
		while(itr.hasNext()){
			String key = (String)itr.next();
			list.add(properties.getProperty(key));
		}
		return list;
	}
	
	/**
	 * @author U6062618
	 *
	 */
	public class LinkedProperties extends Properties {
	    private final HashSet<Object> keys = new LinkedHashSet<Object>();

	    public LinkedProperties() {
	    }

	    public Iterable<Object> orderedKeys() {
	        return Collections.list(keys());
	    }

	    public Enumeration<Object> keys() {
	        return Collections.<Object>enumeration(keys);
	    }

	    public Object put(Object key, Object value) {
	        keys.add(key);
	        return super.put(key, value);
	    }
	}
}
