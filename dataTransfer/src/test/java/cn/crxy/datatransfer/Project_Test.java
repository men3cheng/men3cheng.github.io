package cn.crxy.datatransfer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.crxy.datatransfer.DataTransfer;
import cn.crxy.datatransfer.Date_Const;
import cn.crxy.datatransfer.utils.DBUtils;

public class Project_Test {

	@Test
	public void testQueryFromMysql(){
		try {
			String sql = "select * from user";
			List<Map<String, Object>> result = DBUtils.queryFromMysql(sql);
			Assert.assertTrue(result.size()>0);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace(); 	
		}
	}
	
	@Test
	public void testFindMinMaxIds(){
		try {
			String sql = "select * from user";
			List<Map<String, Object>> result = DBUtils.queryFromMysql(sql);
			/*String min_id = null;
			String max_id = null;
			for (Map<String, Object> map : result) {
				Set<Entry<String, Object>> entrySet = map.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					//得到两个key,id和name
					String key = entry.getKey();
					System.out.println("key:"+key);
					//根据key得到相应的value,id对应的value或者name对应的value
					Object value = entry.getValue();
					System.out.println("value:"+value);
				}		
			}*/
			String[] ids = DataTransfer.findMinMaxIds(result);
			Assert.assertTrue(ids.length==2);
			System.out.println(ids[0]+"--"+ids[1]);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWriteToLocalFile(){
		String sql = "select * from user";
		List<Map<String, Object>> result;
		try {
			result = DBUtils.queryFromMysql(sql);
			String[] ids = DataTransfer.findMinMaxIds(result);
			DataTransfer.writeToLocalFile(ids);
			List<String> readLines = FileUtils.readLines(new File(Date_Const.IDS_FILE));
			Assert.assertTrue(readLines.size()>0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test	
	public void testWriteToHdfs() throws Exception {
		String sql = "select * from user";
		List<Map<String, Object>> result;
		try {
			result = DBUtils.queryFromMysql(sql);
			String[] ids = DataTransfer.findMinMaxIds(result);
			DataTransfer.writeToHdfs(result, ids);
			//Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteFromMySql(){
		try {
			DataTransfer.deleteFromMySql();
			List<Map<String, Object>> queryFromMysql = DBUtils.queryFromMysql("select * from user");
			Assert.assertTrue(queryFromMysql == null || queryFromMysql.size() == 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
