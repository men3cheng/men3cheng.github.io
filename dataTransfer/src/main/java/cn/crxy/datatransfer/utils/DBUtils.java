package cn.crxy.datatransfer.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import cn.crxy.datatransfer.Date_Const;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {
	
	private static QueryRunner queryRunner = new QueryRunner();

	public static Connection getConnection() throws Exception {
		ComboPooledDataSource dateSource = new ComboPooledDataSource();
		dateSource.setDriverClass(Date_Const.DRIVER_CLASS);
		dateSource.setJdbcUrl(Date_Const.JDBC_URL);
		dateSource.setUser(Date_Const.USER);
		dateSource.setPassword(Date_Const.PASSWORD);
		Connection conn = dateSource.getConnection();
		return conn;
	}
	
	public static List<Map<String, Object>> queryFromMysql(String sql) throws Exception {
		//在这里，对ResultSet中的每一行转化为一个Map<String, Object>，所有行放到一个List中
		ResultSetHandler<List<Map<String, Object>>> rsh = new MapListHandler();
		Object[] param = new Object[]{};
		/**
		 * conn 表示数据库的连接
		 * sql 表示将要执行的sql语句
		 * params 表示sql中所需要的参数
		 */
		Connection conn = getConnection();
		List<Map<String, Object>> result = queryRunner .query(conn, sql, rsh, param);
		return result;
	}
	
	public static void detele(String sql, Object...params) throws Exception {
		Connection conn = getConnection();
		/**
		 * conn 表示数据库的连接
		 * sql 表示将要执行的sql语句
		 * params 表示sql中所需要的参数
		 */
		queryRunner.update(conn, sql, params);
		conn.close();
	}
}
