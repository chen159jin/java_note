package jdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * JDBCÇý¶¯Á¬½Ó
 * @author Jin
 *
 */
public class JdbcConn {
	private static final String MySql="jdbc:mysql://";
	private static final String Oracle="jdbc:oracle:thin:@";
	private static final String SqlServer="jdbc:microsoft:sqlserver://"; 
	
	public static Connection getConnection(String DBType, String url,
			String user, String password) throws SQLException {
		if ("mysql".equalsIgnoreCase(DBType))
			return getMySqlConn(url, user, password);
		if ("oracle".equalsIgnoreCase(DBType))
			return getOracleConn(url, user, password);
		if ("sqlserver".equals(DBType)){
			return getSqlServerConn(url, user, password);
		}
		return null;
	}
	public static void closeConn(Connection conn){
		if(conn !=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getSqlServerConn(String url, String user,
			String password) {
		Connection conn=null;
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(SqlServer+url, user, password);
		} catch (SQLException e) {
		}
		return conn;
	}

	public static Connection getOracleConn(String url, String user,
			String password) {
		Connection conn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(Oracle+url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getMySqlConn(String url, String user,
			String password) {
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(MySql+url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		Connection conn=null;
		try {
			conn=getConnection("oracle", "localhost:1521:orcl", "hebsydx","hebsydx");
			if (conn == null) {
				System.out.println("Connection the database is failled !");
			} else {
				System.out.println(conn.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConn(conn);
		}
	}
}
