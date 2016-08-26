package cn.crxy.spider.Utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PageUtils {
	/**
	 * 根据url返回页面
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		//先获得一个构造器
	    HttpClientBuilder builder = HttpClients.custom();
		//构建一个httpclient(认为这个client和浏览器的功能类似)
	    CloseableHttpClient client = builder.build();
		//封装一个get请求
	    HttpGet request = new HttpGet(url);
	    //System.out.println(url);
	    String result = null;
		try {
			CloseableHttpResponse response = client.execute(request);
			//获取封装了页面内容的一个实体对象
			HttpEntity entity = response.getEntity();
			//解析entity中的页面内容，以字符串形式返回
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
