package cn.crxy.spider.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueRepositoryImpl implements Repository{

	private Queue<String> queue = new ConcurrentLinkedDeque<String>();
	
	/**
	 * 先进先出队列
	 */
	@Override
	public String poll() {
		return queue.poll();
	}

	@Override
	public void add(String nextUrl) {
		this.queue.add(nextUrl);
	}

	@Override
	public void addHigh(String nextUrl) {
		this.add(nextUrl);
	}

}
