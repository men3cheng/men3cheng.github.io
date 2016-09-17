package cn.crxy.spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

public class TestXici {

	@Test
	public void test1() throws Exception {
		// 先获取一个构建器
		HttpClientBuilder builder = HttpClients.custom();
		// 设置浏览器信息
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		// 构建一个httpclient(认为这个client和浏览器的功能类似)
		CloseableHttpClient client = builder.build();
		// 封装一个get请求
		HttpGet request = new HttpGet("http://www.xicidaili.com/");
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode rootNode = htmlCleaner.clean(content);
			Object[] tr_objs = rootNode.evaluateXPath("//*[@id=\"ip_list\"]/tbody/tr");
			for (int i = 2; i < tr_objs.length; i++) {
				TagNode trNode = (TagNode) tr_objs[i];
				Object[] td_objs = trNode.evaluateXPath("/td");
				if (td_objs != null && td_objs.length > 0) {
					TagNode ipNode = (TagNode) td_objs[1];
					TagNode portNode = (TagNode) td_objs[2];
					System.out.println(ipNode.getText().toString() + "---"+ portNode.getText().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
