package cn.crxy.spider;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class UrlManager {

	/**
	 * URL调度器（Timer或者Quartz）
	 * 这个调度器负责每天定时向url仓库中添加入口地址
	 */
	public static void main(String[] args) {
		try {
			//根据工厂过去一个默认的调度器
			Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			//开启调度器
			defaultScheduler.start();
			
			String simpleName = UrlJob.class.getSimpleName();
			//封装job
			JobDetail jobDetail = new JobDetail(simpleName, Scheduler.DEFAULT_GROUP, UrlJob.class);
			
			//CronTrigger trigger = new CronTrigger(simpleName, Scheduler.DEFAULT_GROUP, "0 00 18 * * ?");
			//调度任务
			//defaultScheduler.scheduleJob(jobDetail, trigger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
