package cn.crxy.spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * 关于网站内容编码的问题（有时候抓取一些网站会出现中文乱码的问题）
 *   
 *   原因：
 *       其实是因为网站的标签中指定网站编码的格式不规范
 *   分析：
 *       httpclient在下载页面内容的时候，会解析网站的编码，如果网站的编码定义的规范的话是可以正常解析。
 *       如果网站编码定义的不规范，会导致httpclient无法识别，默认用iso8859-1编码
 *   解决方案：
 *       1.最好是使用httlclient工具把页面内容打印到控制台，然后在页面最开始的地方查找网站编码
 * 	     2.content="text/html; charset=gb2312"
 *
 */
public class TestDownload {
	@Test
	public void test1() throws Exception {
		HttpClientBuilder builder = HttpClients.custom(); 
		//Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		CloseableHttpClient client = builder.build();
		HttpGet request = new HttpGet("http://www.yixun.com");
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			//以流的方式获取抓取的内容
			//InputStream content = entity.getContent();
			String result = EntityUtils.toString(entity, "gbk");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
