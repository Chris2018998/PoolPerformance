package cn.beecp.test.type;

import cn.beecp.test.Link;
import cn.beecp.BeeDataSource;
import cn.beecp.BeeDataSourceConfig;

/**
 * fair mode for BeeCP
 * 
 */
public class BeeCP_F {

	public static BeeDataSource createDataSource() throws Exception{
		BeeDataSourceConfig config = new BeeDataSourceConfig();
		config.setDriverClassName(Link.JDBC_DRIVER);
		config.setJdbcUrl(Link.JDBC_URL);
		config.setUsername(Link.JDBC_USER);
		config.setPassword(Link.JDBC_PASSWORD);
		config.setMaxActive(Link.POOL_MAX_ACTIVE);
		config.setInitialSize(Link.POOL_INIT_SIZE);
		config.setConcurrentSize(Link.POOL_MAX_ACTIVE/2);
		config.setMaxWait(Link.REQUEST_TIMEOUT);
 		config.setConnectionTestSQL("select 1 from dual");
		config.setTestOnBorrow(true);
		config.setTestOnReturn(false);
		config.setDefaultAutoCommit(false);

		config.setFairMode(true);
		return new BeeDataSource(config);
	}  		 
}
