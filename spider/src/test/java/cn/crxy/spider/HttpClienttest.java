package cn.crxy.spider;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
/**
 * httpclient3.x和4.x的区别以及如何设置代理包括带有密码的代理
 * @author lenovo
 *
 */
public class HttpClienttest {
	String url = "http://www.crxy.cn/";
	String ip = "202.107.233.85";
	int port = 8080;
	String username = "";
	String password = "";
	
	/**
	 * 使用httpclient4实现代理
	 * 202.107.233.85
	 * 8080
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		HttpClientBuilder build = HttpClients.custom();
		HttpHost proxy = new HttpHost(ip, port);
		CloseableHttpClient client = build.setProxy(proxy ).build();
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
	/**
	 * 使用httpclient3实现代理
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setProxy(ip, port);
		
		GetMethod method = new GetMethod(url);
		httpClient.executeMethod(method );
		String result = new String(method.getResponseBody());
		System.out.println(result);
	}
	
	/**
	 * 使用httpclient4实现代理(带密码的代理)
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		HttpClientBuilder build = HttpClients.custom();
		
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		AuthScope authscope = new AuthScope(ip, port);
		Credentials credentials = new UsernamePasswordCredentials(username,password);
		credentialsProvider.setCredentials(authscope , credentials);
		
		CloseableHttpClient client = build.setDefaultCredentialsProvider(credentialsProvider ).build();
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
	/**
	 * 使用httpclient3实现代理(带密码的代理)
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		HttpClient httpClient = new HttpClient();
		
		org.apache.commons.httpclient.auth.AuthScope authscope = new org.apache.commons.httpclient.auth.AuthScope(ip, port);
		org.apache.commons.httpclient.Credentials credentials = new org.apache.commons.httpclient.UsernamePasswordCredentials(username,password);
		httpClient.getState().setProxyCredentials(authscope, credentials);
		
		GetMethod method = new GetMethod(url);
		httpClient.executeMethod(method );
		String result = new String(method.getResponseBody());
		System.out.println(result);
		
	}
	
}
