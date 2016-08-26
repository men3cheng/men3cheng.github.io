package cn.crxy.spider.Utils;

public class SleepUtils {

	public static void sleep(long millons){
		try {
			Thread.sleep(millons);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
