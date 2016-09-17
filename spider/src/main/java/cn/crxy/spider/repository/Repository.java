package cn.crxy.spider.repository;

public interface Repository {

	public String poll();

	public void add(String nextUrl);

	public void addHeight(String nextUrl);

}
