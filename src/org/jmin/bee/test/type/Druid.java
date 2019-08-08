package org.jmin.bee.test.type;

import org.jmin.bee.test.Link;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * product from Chinese alibaba
 */
public class Druid {

	public static DruidDataSource createDataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(Link.JDBC_URL);
		datasource.setUsername(Link.JDBC_USER);
		datasource.setPassword(Link.JDBC_PASSWORD);
		datasource.setDriverClassName(Link.JDBC_DRIVER);

		datasource.setMaxActive(Link.POOL_MAX_ACTIVE);
		datasource.setMinIdle(Link.POOL_MIN_ACTIVE);
		datasource.setInitialSize(Link.POOL_INIT_SIZE);
        datasource.setMaxWait(Link.REQUEST_TIMEOUT);
		datasource.setPoolPreparedStatements(true);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(16);
		datasource.setTestOnBorrow(true);
		datasource.setTestOnReturn(false);
		datasource.setValidationQuery("select 1 from dual");
        datasource.setDefaultAutoCommit(false);
        datasource.setUseUnfairLock(true);
		return datasource;
		
	}
}
