package cn.crxy.spider.Utils;

import java.io.IOException;
import java.util.Properties;

public class Config{
	static Properties properties;
	static{
		properties = new Properties();
		try {
			properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int nThread = Integer.parseInt(properties.getProperty("nThread"));
	public static long million_1 = Long.parseLong(properties.getProperty("million_1"));
	public static long million_5 = Long.parseLong(properties.getProperty("million_5"));
	
}
