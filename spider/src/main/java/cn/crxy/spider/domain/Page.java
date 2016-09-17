package cn.crxy.spider.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Page {

	/**
	 * 存放页面原始信息
	 */
	private String content;
	
	/**
	 * 页面原始url
	 */
	private String url;
	
	/**
	 * 存储页面解析之后的属性信息
	 */
	private Map<String, String> values =  new HashMap<String, String>();
	
	/**
	 * 商品id
	 */
	private String goodsId;
	
	/**
	 * 临时存放列表页面存储的url
	 * @return
	 */
	private List<String> urls = new ArrayList<String>();
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Map<String, String> getValues() {
		return values;
	}
	
	public List<String> getUrls() {
		return urls;
	}
	
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public void addFiled(String key,String values){
		this.values.put(key, values);
	}
	
	public void addNextUrl(String url){
		this.urls.add(url);
	}
	
}
