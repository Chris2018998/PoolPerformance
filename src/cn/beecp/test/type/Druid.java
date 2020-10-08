package cn.beecp.test.type;

import cn.beecp.test.Link;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * product from Chinese alibaba
 */
public class Druid {
	public static DruidDataSource createDataSource() throws Exception {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(Link.JDBC_URL);
		datasource.setUsername(Link.JDBC_USER);
		datasource.setPassword(Link.JDBC_PASSWORD);
		datasource.setDriverClassName(Link.JDBC_DRIVER);

		datasource.setMaxActive(Link.POOL_MAX_ACTIVE);
		datasource.setMinIdle(Link.POOL_MIN_ACTIVE);
		datasource.setInitialSize(Link.POOL_INIT_SIZE);
		datasource.setPoolPreparedStatements(true);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
//		datasource.setMinEvictableIdleTimeMillis(3000000);
//		datasource.setMaxWaitThreadCount(1000000);
        datasource.setMaxWait(Link.REQUEST_TIMEOUT);
		datasource.setValidationQuery("select 1 from dual");

		datasource.setTestOnBorrow(true);
		datasource.setTestOnReturn(false);
		datasource.setUseUnfairLock(true);
		datasource.setDefaultAutoCommit(false);
		return datasource;
	}
}
