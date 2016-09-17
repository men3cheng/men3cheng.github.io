package cn.crxy.spider;

import java.net.InetAddress;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

public class TestCurator {
	@Test
	public void test() throws Exception {
		//重试机制(监控并记录失效的代理ip地址，如果同一个失效ip出现3次，则丢弃)
		RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000, 3);
		//指定zk集群地址
		String zookeeperConnectionString = "192.168.140.128:2181,192.168.140.129:2181,192.168.140.130:2181";
		int sessionTimeoutMs = 5000;//这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
		int connectionTimeoutMs = 3000;//获取链接的超时时间
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
		//开启链接
		client.start();
		//获取服务器的当前ip信息
		InetAddress localHost = InetAddress.getLocalHost();
		String ip = localHost.getHostAddress();
		
		client.create()
			.creatingParentsIfNeeded()//如果父节点不存在则创建
			.withMode(CreateMode.EPHEMERAL)//指定节点类型,注意：临时节点必须创建在某一个永久节点下面
			.forPath("/spider/"+ip);//指定节点名称
		//循环监视
		while(true){
			;
		}
	}
}
