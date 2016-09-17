package cn.crxy.spider.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	/**
	 * 根据url返回页面
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		//先获得一个构造器
	    HttpClientBuilder builder = HttpClients.custom();
	    //设置浏览器信息
	    builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
	    //代理ip不能写死，我们要维护一个代理ip库，把收集到的代理ip存入到redis的set集合中，格式为122.0.75.47：83
		/*String ip = "122.0.75.47";
		int port = 83;
		HttpHost proxy = new HttpHost(ip,port);*/
		//构建一个httpclient(认为这个client和浏览器的功能类似)
	    CloseableHttpClient client = builder/*.setProxy(proxy)*/.build();
		//封装一个get请求
	    HttpGet request = new HttpGet(url);
	    //System.out.println(url);
	    String result = null;
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = client.execute(request);
			//获取封装了页面内容的一个实体对象
			HttpEntity entity = response.getEntity();
			//解析entity中的页面内容，以字符串形式返回
			result = EntityUtils.toString(entity);
			logger.info("页面下载成功,url:{},消耗时间:{}",url,System.currentTimeMillis()-start_time);
		/*}catch(HttpHostConnectException e){
			//一旦ip失效，在这里捕获并抛出详细地址和端口
			logger.info("代理IP失效，ip:{},host:{}",ip,port);*/
		} catch (Exception e) {
			logger.info("页面下载失败,url:{},异常信息：{}",url,e.getMessage());
		}
		return result;
	}
}
