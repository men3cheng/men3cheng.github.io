package cn.crxy.spider;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class SpiderWatcher implements Watcher {
	
	CuratorFramework client = null;
	List<String> childenList = null;
	
	SpiderWatcher(){
		//重试机制
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//指定zk集群地址
		String zookeeperConnectionString = "192.168.140.128:2181,192.168.140.129:2181,192.168.140.130:2181";
		int sessionTimeoutMs = 5000;//这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
		int connectionTimeoutMs = 3000;//获取链接的超时时间
		client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
		//开启链接
		client.start();
		try {
			//注意：这个监视器是一次有效，想要多次使用的话需要重复注册
			//使用当前这个监视器监控spider的子节点的变化情况(注册一个监视器)
			childenList = client.getChildren().usingWatcher(this).forPath("/spider");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当监视器发现节点发生变化了，那么这个方法就会被调用
	 * 回调方法
	 */
	@Override
	public void process(WatchedEvent event) {
		
		try {
			
			List<String> newChildenList = client.getChildren().usingWatcher(this).forPath("/spider");
			for (String node : childenList) {
				if(!newChildenList.contains(node)){
					System.out.println("节点消失："+node);
					//TODO---需要给管理发短信，打电话，发邮件
					/**
					 * 发邮件：javamail
					 * 发短信：云片网
					 */
				}
			}
			for (String node : newChildenList) {
				if(!childenList.contains(node)){
					System.out.println("节点新增："+node);
				}
			}
			//可以保证每一次都是和上一次的节点信息进行对比
			this.childenList = newChildenList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		SpiderWatcher sw = new SpiderWatcher();
		sw.start();
	}
	
	private void start(){
		while(true){
			;
		}
	}

}
