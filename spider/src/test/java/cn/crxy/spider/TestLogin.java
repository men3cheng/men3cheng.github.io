package cn.crxy.spider;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * http://svn.club/
 * 针对不是很复杂的登陆，建议手工抓包分析登陆流程，使用httpclient模拟登陆
 * 如果比较复杂，建议使用selenium
 * 
 * 普通网站登录流程都是这样的思路，大部分网站登录的时候还需要指定useragent，有时候还需要指定header请求头信息，这就是反爬策略
 * 1：useragent
 * 2：header请求头信息
 */
public class TestLogin {
	
	@Test
	public void test1() throws Exception {
		HttpClientBuilder builder = HttpClients.custom();
		//设置浏览器信息
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		//设置请求头信息
		Collection<BasicHeader> defaultHeaders = new ArrayList<BasicHeader>();
		defaultHeaders.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		defaultHeaders.add(new BasicHeader("Referer", "http://svn.club/"));
		builder.setDefaultHeaders(defaultHeaders);
		
		CloseableHttpClient client = builder.build();
		
		HttpPost httpPost = new HttpPost("http://svn.club/user/login");
		//设置请求参数
		//注意：有一些网站会对对传递的参数进行加密，即可以保证数据安全，又可以增加爬虫爬取难度
		//一般常见的加密算法：base64 MD5  UUID
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("uid", "crxy"));
		parameters.add(new BasicNameValuePair("pwd", "www.crxy.cn"));
		
		HttpEntity entity = new UrlEncodedFormEntity(parameters);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = client.execute(httpPost);
		//注意：这个请求执行成功之后会跳转,根据http请求的返回状态码可以确定
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==302){//满足就表示是页面跳转
			Header[] headers = response.getHeaders("Location");
			if(headers!=null && headers.length>0){
				String redirect_url = headers[0].getValue();
				
				httpPost.setURI(new URI("http://svn.club"+redirect_url));
				response = client.execute(httpPost);
				HttpEntity entity2 = response.getEntity();
				System.out.println(EntityUtils.toString(entity2));
			}
		}
	}

}
