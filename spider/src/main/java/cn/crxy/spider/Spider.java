package cn.crxy.spider;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crxy.spider.Utils.Config;
import cn.crxy.spider.Utils.SleepUtils;
import cn.crxy.spider.domain.Page;
import cn.crxy.spider.download.Downloadable;
import cn.crxy.spider.download.HttpclientDownloadImpl;
import cn.crxy.spider.process.JdProcessImpl;
import cn.crxy.spider.process.Processable;
import cn.crxy.spider.repository.QueueHeightLowRepositoryImpl;
import cn.crxy.spider.repository.QueueRandomRepositoryImpl;
import cn.crxy.spider.repository.Repository;
import cn.crxy.spider.store.MySqlStoreImpl;
import cn.crxy.spider.store.Storeable;

/**
 * 针对项目中可能发生变化的配置，建议都提取出来
 * 一类存储到配置文件中：变化频率较小
 * 另外的存储到数据库：频繁发生变化的
 * @author men3cheng
 * 2016年8月27日
 * 下午3:06:06
 */
public class Spider {

	private Downloadable downloadable = new HttpclientDownloadImpl();
	//由于每个网站的解析规则不一致，所以在此不设置初始值；
	private Processable processable;
	//private Storeable storeable = new ConsoleStoreImpl();
	private Storeable storeable = new MySqlStoreImpl();
	//private Repository repository = new QueueRepositoryImpl();
	//private Repository repository = new RedisRepositoryImpl();
	//private Repository repository = new QueueHeightLowRepositoryImpl();
	private Repository repository = new QueueRandomRepositoryImpl();
	//private Repository repository = new RedisRandomRepositoryImpl();
	
	
	/**
	 * 固定大小的线程池
	 */
	private ExecutorService threadPool = Executors.newFixedThreadPool(Config.nThread);
	
	/**
	 * 开启爬虫
	 */
	public void start(){
		Check();
		while(true){
			final String url = repository.poll();
			if(url!=null){
				//执行的时候会从线程池中获得一个线程去执行
				threadPool.execute(new Runnable() {
					public void run() {
						Page page = Spider.this.download(url);
						System.out.println("解析url:" + url);
						Spider.this.process(page);
						//当第一次循环执行的时候，list里面存储着种子地址解析出的所有的明细页面地址和下一页的列表页面
						List<String> urls = page.getUrls();
						for (String nextUrl : urls) {
							if (nextUrl.startsWith("http://item.jd.com/")) {
								repository.add(nextUrl);
							} else {
								repository.addHeight(nextUrl);
							}
						}
					    if (page.getUrl().startsWith("http://item.jd.com/")) {
							Spider.this.store(page);
						}
						SleepUtils.sleep(Config.million_1);//为了防止速率过快导致IP被封；
					}
				});
			}else{
				logger.info("暂时没有url了，休息一会儿。");
				SleepUtils.sleep(Config.million_5);
			}
		}
	}
	
	/**
	 * 爬虫基本配置检查
	 */
	Logger logger = LoggerFactory.getLogger(Spider.class); 
	public void Check() {
		logger.info("开始进行基本配置检查。");
		/*if (processable==null) {
			String msg = "没有设置解析类！";
			logger.error(msg);
			throw new RuntimeException(msg);
		}*/
		logger.info("=======================================================");
		logger.info("downloadable的实现类是：{}",downloadable.getClass().getName());
		logger.info("processable的实现类是：{}",processable.getClass().getName());
		logger.info("storeable的实现类是：{}",storeable.getClass().getName());
		logger.info("repository的实现类是：{}",repository.getClass().getName());
		logger.info("=======================================================");
		logger.info("配置检查结束。");
	}
	
	/**
	 * 下载url对应的内容
	 */
	public Page download(String url) {
		return this.downloadable.download(url);
	}
	/**
	 * 解析页面内容
	 */
	public void process(Page page){
		this.processable.process(page);;
	}
	/**
	 * 存储页面的基本属性
	 */
	public void store(Page page){
		this.storeable.store(page);
	}
	
	public void setDownloadable(Downloadable downloadable){
		this.downloadable = downloadable;
 	}
	
	public void setProcessable(Processable processable) {
		this.processable = processable;
	}
	
	public void setStoreable(Storeable storeable) {
		this.storeable = storeable;
	}
	
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	//设置种子地址
	public void setSeedUrl(String url){
		this.repository.add(url);
	}
	
	
	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.setProcessable(new JdProcessImpl());
		
		//设置种子地址，实际中不能写死，这个种子地址可以从数据中查询出来，再设置
		spider.setSeedUrl("http://list.jd.com/list.html?cat=9987,653,655");
		spider.start();
	}
	
	
}


