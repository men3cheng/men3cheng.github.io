package cn.crxy.spider.download;

import cn.crxy.spider.domain.Page;

public interface Downloadable {

	public Page download(String url);
	
}
