package cn.beecp.test.type;

import org.apache.commons.dbcp2.BasicDataSource;
import cn.beecp.test.Link;

/**
 * DBCP
 */
public class DBCP2{
	public static BasicDataSource createDataSource() throws Exception {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setUrl(Link.JDBC_URL);
		datasource.setDriverClassName(Link.JDBC_DRIVER);
		datasource.setUsername(Link.JDBC_USER);
		datasource.setPassword(Link.JDBC_PASSWORD);
		
		datasource.setInitialSize(Link.POOL_INIT_SIZE);
		datasource.setMinIdle(Link.POOL_MIN_ACTIVE);
		datasource.setMaxIdle(Link.POOL_MIN_ACTIVE);
		datasource.setMaxTotal(Link.POOL_MAX_ACTIVE);
		datasource.setMaxWaitMillis(Link.REQUEST_TIMEOUT);
		datasource.setPoolPreparedStatements(true);
		datasource.setMaxOpenPreparedStatements(20);
		datasource.setValidationQuery("select 1 from dual");

		datasource.setTestOnBorrow(true);
		datasource.setTestOnReturn(false);
		datasource.setDefaultAutoCommit(false);
		return datasource;
	}
}
