package cn.crxy.spider.repository;

public interface Repository {
	
	String poll();
	
	void add(String nextUrl);
	
	void addHigh(String nextUrl);

}
