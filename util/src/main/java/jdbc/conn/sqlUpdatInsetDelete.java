package jdbc.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 处理Update、inset、delete的类
 * @author Jin
 *
 */
public class sqlUpdatInsetDelete {
	public static int updateTemplate(Connection conn,String sql, String[] sqls){
		int n=0;
		PreparedStatement pstm=null;
		try {
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < sqls.length; i++) { 
				pstm.setObject(i+1, sqls[i]);
			}
			n = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	public static int update(Connection conn,String sql){
		int n=0;
	    Statement st = null;
	    try {
	        //从工具类中获取连接
	        st = conn.createStatement();
	        n = st.executeUpdate(sql);
	        
	   } catch (Exception e) {
	       e.printStackTrace();
	   }finally{
	       try {
	    	   if(st!=null)
			st.close();//调用工具类释放资源
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
		return n;
	}
	public static void main(String[] args) {
		String sql = "update xs0101 set xh=? where xs0101id = ?";
		String[] sqls = {"1","200660950231"};
		String sql2 = "update xs0101 set xh='200660950231' where xs0101id = '200660950231'";
		try {
			Connection conn = JdbcConn.getConnection("oracle", "localhost:1521:orcl", "hebsydx","hebsydx");
			//int b = updateTemplate(conn, sql ,sqls);
			int b = update(conn, sql2 );
			System.out.println(b);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
