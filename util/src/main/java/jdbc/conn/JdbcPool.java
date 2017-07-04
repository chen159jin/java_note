package jdbc.conn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * DBCP数据库连接池
 * @author Jin
 *
 */
public class JdbcPool {
	private static final String MySql="jdbc:mysql://";
	private static final String Oracle="jdbc:oracle:thin:@";
	private static final String SqlServer="jdbc:microsoft:sqlserver://"; 
	public  static BasicDataSource ds = null;
	public static void dbpoolInit(String url, String username,String password){
		ds = new BasicDataSource();// 数据库连接池
		ds.setUrl(Oracle);
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(1);//设置初始连接数
	}
	public void dbPoolTest(String sql){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn !=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		
	}
}
