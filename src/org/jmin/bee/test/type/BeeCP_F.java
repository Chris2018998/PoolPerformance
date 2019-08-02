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

		sourceInfo.setPoolMaxSize(Link.POOL_MAX_ACTIVE);
		sourceInfo.setPoolInitSize(Link.POOL_INIT_SIZE);
		sourceInfo.setMaxWaitTime(Link.REQUEST_TIMEOUT);
 		sourceInfo.setValidationQuerySQL("select 1 from dual");
		sourceInfo.setFairMode(true);
		sourceInfo.setCheckOnBorrow(true);
		sourceInfo.setCheckOnReturn(false);
		//sourceInfo.setPoolImplementClassName("org.jmin.bee.pool.ConnectionPool2");
		return new BeeDataSource(sourceInfo);
	}  		 
}
