package cn.crxy.spider;

import org.junit.Test;

import cn.crxy.spider.domain.Page;
import cn.crxy.spider.download.HttpclientDownloadImpl;
import cn.crxy.spider.process.JdProcessImpl;
import cn.crxy.spider.store.MySqlStoreImpl;

public class Spider_Test {

	@Test
	public void test1() throws Exception {
		
		String url = "http://item.jd.com/2782640.html";
		Spider spider = new Spider();
		spider.setDownloadable(new HttpclientDownloadImpl());
		spider.setProcessable(new JdProcessImpl());
		spider.setStoreable(new MySqlStoreImpl());
		Page page = spider.download(url);
		//System.out.println(page.getContent());
		spider.process(page);
		//spider.store(page);
	}
}
