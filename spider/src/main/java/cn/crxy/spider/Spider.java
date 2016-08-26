package cn.crxy.spider;

import java.util.List;

import cn.crxy.spider.Utils.SleepUtils;
import cn.crxy.spider.domain.Page;
import cn.crxy.spider.download.Downloadable;
import cn.crxy.spider.download.HttpclientDownloadImpl;
import cn.crxy.spider.process.JdProcessImpl;
import cn.crxy.spider.process.Processable;
import cn.crxy.spider.repository.QueueRepositoryImpl;
import cn.crxy.spider.repository.Repository;
import cn.crxy.spider.store.ConsoleStoreImpl;
import cn.crxy.spider.store.Storeable;

public class Spider {

	private Downloadable downloadable = new HttpclientDownloadImpl();
	private Processable processable;
	private Storeable storeable = new ConsoleStoreImpl();
	private Repository repositroy = new QueueRepositoryImpl();
	
	/**
	 * 开启爬虫
	 */
	public void start() {
		while(true){
			String url = repositroy.poll();
			if(url!=null){
				Page page = this.download(url);
				this.process(page);
				List<String> urls = page.getUrls();
				for (String nextUrl : urls) {
					if(nextUrl.startsWith("http://item.jd.com/")){
						repositroy.add(nextUrl);
					}else{
						repositroy.addHigh(nextUrl);
					}
				}
				if(page.getUrl().startsWith("http://item.jd.com/")){
					this.process(page);
				}
				SleepUtils.sleep(1000);//避免频繁抓取导致ip被封
			}else{
				SleepUtils.sleep(5000);
			}
		}
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
	
	public void setRepositroy(Repository repositroy) {
		this.repositroy = repositroy;
	}
	
	public void setSeedUrl(String url){
		this.repositroy.add(url);
	}
	
	/**
	 * 设置种子地址
	 * @param args
	 */
	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.setProcessable(new JdProcessImpl());
		
		//设置种子地址，实际中不能写死，这个种子地址可以从数据中查询出来，再设置
		spider.setSeedUrl("http://list.jd.com/list.html?cat=9987,653,655");
		spider.start();
	}
	
}


