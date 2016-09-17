package cn.crxy.spider.repository;

import cn.crxy.spider.Utils.RedisUtils;

/**
 * 使用redis共享队列，不区分优先级
 */
public class RedisRepositoryImpl implements Repository{

	private RedisUtils redisUtils = new RedisUtils();
	
	@Override
	public String poll() {
		return redisUtils.poll(RedisUtils.heightkey);
	}

	@Override
	public void add(String nextUrl) {
		redisUtils.add(RedisUtils.heightkey, nextUrl);
	}

	@Override
	public void addHeight(String nextUrl) {
		this.add(nextUrl);
	}

	

}
