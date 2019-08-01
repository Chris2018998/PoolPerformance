package org.jmin.bee.test.type;

import org.jmin.bee.test.Link;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 光，very fast 
 */
public class HikariCP {
	private static HikariDataSource datasource;
	static {
		initDataSource();
	}

	public static void initDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(Link.JDBC_URL);
		config.setDriverClassName(Link.JDBC_DRIVER);
		config.setUsername(Link.JDBC_USER);
		config.setPassword(Link.JDBC_PASSWORD);
		config.setMinimumIdle(Link.POOL_INIT_SIZE);
	    config.setMaximumPoolSize(Link.POOL_MAX_ACTIVE);
		config.setConnectionTimeout(Link.REQUEST_TIMEOUT);
	    config.setConnectionTestQuery("select 1 from dual");
		datasource = new HikariDataSource(config);   
	}

	public static HikariDataSource getDatasource() {
		return datasource;
	}
}
