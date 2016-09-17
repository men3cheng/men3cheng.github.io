package cn.crxy.spider;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.crxy.spider.Utils.RedisUtils;

public class UrlJob implements Job {

	RedisUtils redisUtils = new RedisUtils();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//System.out.println("定时任务执行了。。。");
		/**
		 * 当定时任务被指定的时候这个方法就会执行了
		 */
		//获取list里所有的入口地址
		List<String> lrange = redisUtils.lrange("start_url", 0, -1);
		//循环把入口地址塞到url仓库中去
		for (String url : lrange) {
			redisUtils.add(RedisUtils.heightkey, url);
		}
	}

}
