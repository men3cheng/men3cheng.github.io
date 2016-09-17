package cn.crxy.spider.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueRepositoryImpl implements Repository{

	/**
	 * 模拟本地url仓库
	 */
	private Queue<String> queue = new ConcurrentLinkedQueue<String>();
	
	@Override
	public String poll() {
		return queue.poll();
	}

	@Override
	public void add(String nextUrl) {
		this.queue.add(nextUrl);
	}

	@Override
	public void addHeight(String nextUrl) {
		this.add(nextUrl);
	}

}
