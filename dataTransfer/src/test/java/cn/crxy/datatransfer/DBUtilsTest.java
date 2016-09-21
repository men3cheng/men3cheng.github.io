package cn.crxy.datatransfer;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import cn.crxy.datatransfer.utils.DBUtils;

public class DBUtilsTest {

	@Test
	public void testGetConnection() {
		try {
			Connection connection = DBUtils.getConnection();
			Assert.assertTrue(connection!=null);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
