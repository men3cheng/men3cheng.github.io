package cn.crxy.spider.repository;

import java.util.HashMap;
import java.util.Random;

import cn.crxy.spider.Utils.DomainUtils;
import cn.crxy.spider.Utils.RedisUtils;

/**
 * 随机取不用网站的url,不区分优先级
 * @author men3cheng
 * 2016年8月27日
 * 下午8:29:56
 */
public class RedisRandomRepositoryImpl implements Repository {
	
	/**
	 * key:网站的顶级域名
	 * value:对应网站的url队列
	 */
	
	HashMap<String, String> hashmap = new HashMap<String, String>(); 
	Random random = new Random();
	RedisUtils redisUtils = new RedisUtils();
	
	@Override
	public String poll() {
		//获取到所有网站的顶级域名，也就是hashmap中个所有key
		String[] array = hashmap.keySet().toArray(new String[hashmap.size()]);
		//获取的这个随机的数字，其实就是数组的角标
		int randomInt = random.nextInt(array.length);
		//随机获取一个key
		String randomkey = array[randomInt];
		//获取一个随机的queue队列
		String randomqueue = hashmap.get(randomkey);
		//返回一个url，这个url我们就认为是随机的
		return redisUtils.poll(randomqueue);
	}

	@Override
	public void add(String nextUrl) {
		String topDomain = DomainUtils.getTopDomain(nextUrl);
		String queue = hashmap.get(topDomain);
		//根据顶级域名往队列里存对应的url队列，做判断，如果队列中没有这个类型的key的话 
		if(queue==null){
			//说明这个网站的url是第一次出现
			queue = topDomain;
		}
		//把url添加到对应网站的queue队列中
		redisUtils.add(queue, nextUrl);
		//把网站顶级域名和对于的queue队列添加到map中
		hashmap.put(topDomain, queue);
	}

	@Override
	public void addHeight(String nextUrl) {
		this.add(nextUrl);
	}

}
