package cn.bee.dbcp.test.type;

import cn.bee.dbcp.BeeDataSource;
import cn.bee.dbcp.BeeDataSourceConfig;
import cn.bee.dbcp.test.Link;

/**
 *  compete mode for BeeCP
 * 
 */
public class BeeCP_C {
	
	public static BeeDataSource  createDataSource() throws Exception{
		BeeDataSourceConfig config =new  BeeDataSourceConfig(
			Link.JDBC_DRIVER, 
				Link.JDBC_URL,
				Link.JDBC_USER, 
				Link.JDBC_PASSWORD);
		config.setMaxActive(Link.POOL_MAX_ACTIVE);
		config.setInitialSize(Link.POOL_INIT_SIZE);
		config.setMaxWait(Link.REQUEST_TIMEOUT);
 		config.setConnectionTestSQL("select 1 from dual");
		config.setFairMode(false);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		
	    return new BeeDataSource(config);
	}  		 
}
