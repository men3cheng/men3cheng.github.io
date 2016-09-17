package cn.crxy.spider.repository;

import java.util.Random;
import java.util.Set;

import redis.clients.jedis.Jedis;
import cn.crxy.spider.Utils.DomainUtils;
import cn.crxy.spider.Utils.RedisUtils;

public class RedisRandomRepositoryImpl2 implements Repository {

	/**
	 * 用radis中的hashmap替代java中的redis
	 * 随机抓取不同网站的url
	 * 不区分优先级
	 */
	
	Random random = new Random();
	RedisUtils redisUtils = new RedisUtils();
	
	@Override
	public String poll() {
		Jedis jedis = redisUtils.getConn();
		//获取到所有网站的顶级域名，也就是hashmap中个所有key
		//String[] array = hashmap.keySet().toArray(new String[hashmap.size()]);
		Set<String> set = jedis.hkeys("all_url_map");
		String[] keys_array = set.toArray(new String[set.size()]);
		//获取的这个随机的数字，其实就是数组的角标
		int randomInt = random.nextInt(keys_array.length);
		//随机获取一个key
		String randomkey = keys_array[randomInt];
		//获取一个随机的queue队列
		String randomqueue = jedis.hget("all_url_map", randomkey);
		//返回一个url，这个url我们就认为是随机的
		return redisUtils.poll(randomqueue);
	}

	@Override
	public void add(String nextUrl) {
		Jedis jedis = redisUtils.getConn();
		//获取url的顶级域名
		String topDomain = DomainUtils.getTopDomain(nextUrl);
		//获取到所有网站的顶级域名，也就是hashmap中个所有key
		String queue = jedis.hget("all_url_map", topDomain);
		//根据顶级域名往队列里存对应的url队列，做判断，如果队列中没有这个类型的key的话 
		if(queue==null){
			//说明这个网站的url是第一次出现
			queue = topDomain;
		}
		//把url添加到对应网站的queue队列中
		redisUtils.add(queue, nextUrl);
		//把网站顶级域名和对于的queue队列添加到map中
		jedis.hset("all_url_map", topDomain, queue);
		jedis.close();
	}

	@Override
	public void addHeight(String nextUrl) {
		
	}

}
