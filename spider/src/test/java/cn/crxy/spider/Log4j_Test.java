package cn.crxy.spider;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j_Test {

	Logger logger = LoggerFactory.getLogger(Log4j_Test.class);
	
	@Test
	public void test1() throws Exception {
		logger.error("error级别{},{}","第一个","第二个");
		logger.warn("warn级别");
		logger.info("info级别");
		logger.debug("debug级别");
	}
	
}
