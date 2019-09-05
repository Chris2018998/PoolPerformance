package org.jmin.bee.test.type;

import org.jmin.bee.BeeDataSource;
import org.jmin.bee.BeeDataSourceConfig;
import org.jmin.bee.test.Link;

/**
 * fair mode for BeeCP
 * 
 */
public class BeeCP_F {

	public static BeeDataSource createDataSource() throws Exception{
		BeeDataSourceConfig sourceInfo =new  BeeDataSourceConfig(Link.JDBC_DRIVER, 
				Link.JDBC_URL,
				Link.JDBC_USER, 
				Link.JDBC_PASSWORD);

		sourceInfo.setMaxActive(Link.POOL_MAX_ACTIVE);
		sourceInfo.setConcurrentSize(Link.POOL_MAX_ACTIVE);
		sourceInfo.setInitialSize(Link.POOL_INIT_SIZE);
		sourceInfo.setMaxWait(Link.REQUEST_TIMEOUT);
 		sourceInfo.setValidationQuery("select 1 from dual");
		sourceInfo.setFairQueue(true);
		sourceInfo.setTestOnBorrow(true);
		sourceInfo.setTestOnReturn(false);
		//sourceInfo.setPoolImplementClassName("org.jmin.bee.pool.ConnectionPool2");
	
		BeeDataSource datasource=new BeeDataSource(sourceInfo);
		return datasource;
	}  		 
}
