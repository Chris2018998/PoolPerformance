package cn.bee.dbcp.test.type;

import cn.bee.dbcp.test.Link;

import cn.bee.dbcp.BeeDataSource;
import cn.bee.dbcp.BeeDataSourceConfig;

/**
 *  compete mode for BeeCP
 * 
 */
public class BeeCP_C {
	
	public static BeeDataSource  createDataSource() throws Exception{
		BeeDataSourceConfig sourceInfo =new  BeeDataSourceConfig(Link.JDBC_DRIVER, 
				Link.JDBC_URL,
				Link.JDBC_USER, 
				Link.JDBC_PASSWORD);

		sourceInfo.setMaxActive(Link.POOL_MAX_ACTIVE);
		sourceInfo.setInitialSize(Link.POOL_INIT_SIZE);
		sourceInfo.setMaxWait(Link.REQUEST_TIMEOUT);
 		sourceInfo.setValidationQuery("select 1 from dual");
		sourceInfo.setFairQueue(false);
		sourceInfo.setTestOnBorrow(true);
		sourceInfo.setTestOnReturn(false);
		sourceInfo.setConcurrentSize(8);
		//sourceInfo.setPoolImplementClassName("cn.bee.dbcp.pool.ConnectionPool2");
		BeeDataSource datasource=new BeeDataSource(sourceInfo);
		return datasource;
	}  		 
}
