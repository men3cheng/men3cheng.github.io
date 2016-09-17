package cn.crxy.spider.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueHeightLowRepositoryImpl implements Repository{

	/**
	 * 带有优先级的队列
	 */
	
	//模拟url仓库
	private Queue<String> height_queue = new ConcurrentLinkedDeque<String>();
	private Queue<String> low_queue = new ConcurrentLinkedDeque<String>();
	
	@Override
	public String poll() {
		String url = height_queue.poll();
		if(url==null){
			url = low_queue.poll();
		}
		return url;
	}

	@Override
	public void add(String nextUrl) {
		this.low_queue.add(nextUrl);
	}

	@Override
	public void addHeight(String nextUrl) {
		this.height_queue.add(nextUrl);
	}

	
	
	
}
