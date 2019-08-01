package org.jmin.bee.test.type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Proxool 连接池
 */
public class Proxool extends Thread{
	
	private String sql;

	private int executeCount;
	
	private String url;

	private Properties properties;
	
	public Proxool(String sql, int executeCount, String url, Properties properties) {
		super();
		this.sql = sql;
		this.executeCount = executeCount;
		this.url = url;
		this.properties = properties;
	}

	@Override
	public void run() {
		for (int i = 0; i < executeCount; i++) {
			executeSQL(sql);
		}
	}
	
	private void executeSQL(String sql) {
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, properties);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			// int cnt = 1;
			// while (rs.next()) {
			// System.out.println((cnt++) + ". ID:" + rs.getString("id") +
			// " Name:" + rs.getString("name"));
			// }
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
