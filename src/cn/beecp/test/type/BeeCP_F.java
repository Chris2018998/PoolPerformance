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
		BeeDataSourceConfig config =new  BeeDataSourceConfig(Link.JDBC_DRIVER, 
				Link.JDBC_URL,
				Link.JDBC_USER, 
				Link.JDBC_PASSWORD);

		config.setMaxActive(Link.POOL_MAX_ACTIVE);
		config.setInitialSize(Link.POOL_INIT_SIZE);
		config.setMaxWait(Link.REQUEST_TIMEOUT);
 		config.setConnectionTestSQL("select 1 from dual");
		config.setPreparedStatementCacheSize(10);
		config.setFairMode(true);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		return new BeeDataSource(config);
	}  		 
}
