package cn.crxy.spider.store;

import cn.crxy.spider.domain.Page;

public class ConsoleStoreImpl implements Storeable{

	@Override
	public void store(Page page) {
		System.out.println(page.getUrl()+"--"+page.getValues().get("price"));
	}

}
