package cn.crxy.datatransfer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crxy.datatransfer.utils.DBUtils;
import cn.crxy.datatransfer.utils.HDFSUtils;

import com.google.common.collect.Lists;

/**
 * 项目背景：
 *    从第三方接入的数据传到mysql中，频率是每5分钟20W条数据，通过es检索，传到前端页面检索显示。发现在这个过程中，庞大的
 * 数据量使得mysql负载过重，导致系统过慢。所以我们的解决方案是通过hdfs来解决，把传入的数据从mysql读取到hdfs中，再从hdfs
 * 传到es.由于用到的是集群的数据处理环境，大大减缓了mysql的压力。
 *    具体步骤：
 *    使用jdbc读取数据，迭代存到内存中，并找出最小id和最大id,把这两个id存入到本地文件；
 *    把内存中的数据写入到磁盘，然后写入到hdfs，这个时候根据本地的id，把数据库中对应的mysql表数据删掉；
 *    把代码打成jar包，不需要任何的外部jar依赖；
 *    配置到corntab中，定时运行，周期为5分钟执行一次；
 *    使用log4j打日志文件到本地文件或者控制台中。
 */

/**
 *	开发操作流程：
 *	1.使用jdbc从mysql读取数据
 *	2.读取ResultSet，存放到内存中。找出其中的最小id和最大id
 *	3.把最小id和最大id写入到本地的一个文件中
 *	4.把内存中的数据写入到磁盘，然后写入到HDFS中
 *	5.读取本地文件中的id，根据id删除mysql表中的记录
 *	6.把代码打成jar，不需要任何外部jar依赖
 *	7.配置到crontab中，定时运行，周期是5分钟
 *	8.(可选，二选一)
 *	8.1使用log4j写日志
 *	8.2直接写入到一个文件中
 */
public class DataTransfer {
	static Logger logger = LoggerFactory.getLogger(DataTransfer.class);
	
	public static void main(String[] args) throws Exception {
		
		long start = System.currentTimeMillis();
		String sql = "select * from user";
		
		List<Map<String, Object>> result = DBUtils.queryFromMysql(sql);
		logger.info("查询到新增的数据,共{}条",result.size());
		
		if(result.size()==0){
			logger.info("没有新增数据，程序退出");
			return;
		}
		
		String[] ids = findMinMaxIds(result);
		logger.info("新增的id为从{}到{}",ids[0],ids[1]);
		
		//把最小id和最大id写入到本地的一个文件中
		writeToLocalFile(ids);
		logger.info("新增的数据的id,从{}到{}", ids[0], ids[1]);
		
		//把内存中的数据先写入磁盘，在写入到HDFS中
		writeToHdfs(result, ids);
		logger.info("把最小id({})和最大id({})写入到本地的一个文件中", ids[0], ids[1]);
		
		//读取本地文件中的id，根据id删除mysql表中的记录
		deleteFromMySql();
		long end = System.currentTimeMillis();
		logger.info("完成一次数据导入,耗时{}s,共处理 {}条数据",(end-start)/1000,result.size());
		
	}

	public static void deleteFromMySql() throws IOException, Exception {
		List<String> idsList = FileUtils.readLines(new File(Date_Const.IDS_FILE));
		DBUtils.detele("delete from user where id>=? and id<=?", idsList.get(0),idsList.get(1));
		logger.info("成功删除数据，id从{}到{}",idsList.get(0), idsList.get(1));
	}

	public static void writeToHdfs(List<Map<String, Object>> result,String[] ids) throws Exception{
		//写入到磁盘，成一个临时文件，方便后面调用hadoop的IOUtils.copyBytes(...)
		FileUtils.writeLines(new File(Date_Const.RESULT_PATH), result, false);
		//首先判断HDFS中是否存在同名文件。如果存在，则表示重复导入，应该退出程序
		String hdfsPath = "/DT/"+ids[0]+"_"+ids[1];
		if(HDFSUtils.isExisted(hdfsPath)){
			throw new RuntimeException("HDFS中存在同名文件"+hdfsPath);
		}
		HDFSUtils.local2Hdfs(Date_Const.RESULT_PATH,hdfsPath);
		//删除临时文件（占用大量的磁盘空间）
		FileUtils.forceDelete(new File(Date_Const.RESULT_PATH));
		logger.info("写入HDFS文件{}成功", hdfsPath);
	}

	
	public static void writeToLocalFile(String[] ids) throws IOException {
		FileUtils.writeLines(new File(Date_Const.IDS_FILE), Lists.newArrayList(ids), false);
	}

	public static String[] findMinMaxIds(List<Map<String, Object>> result) {
		String min_id = null;
		String max_id = null;
		int index = 0;
		for (Map<String, Object> map : result) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			if(index==0){
				//找到最小id
				for (Entry<String, Object> entry : entrySet) {
					//得到两个key,id和name
					String key = entry.getKey();
					//根据key得到相应的value,id对应的value或者name对应的value
					Object value = entry.getValue();
					if("id".equals(key)){
						String idValue = value.toString();
						min_id = idValue;
						max_id = idValue;
						continue;
					}
				}
			}else{
				//找到最大id
				for (Entry<String, Object> entry : entrySet) {
					//得到两个key,id和name
					String key = entry.getKey();
					//根据key得到相应的value,id对应的value或者name对应的value
					Object value = entry.getValue();
					if("id".equals(key)){
						String idValue = value.toString();
						max_id = idValue;
						continue;
					}
				}
			}
			index++;
		}
		return new String[]{min_id,max_id};
	}
	
}
