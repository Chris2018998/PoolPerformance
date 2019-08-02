package org.jmin.bee.test.type;

import org.jmin.bee.BeeDataSource;
import org.jmin.bee.BeeDataSourceConfig;
import org.jmin.bee.test.Link;

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

		sourceInfo.setPoolMaxSize(Link.POOL_MAX_ACTIVE);
		sourceInfo.setPoolInitSize(Link.POOL_INIT_SIZE);
		sourceInfo.setMaxWaitTime(Link.REQUEST_TIMEOUT);
 		sourceInfo.setValidationQuerySQL("select 1 from dual");
		sourceInfo.setFairMode(false);
		sourceInfo.setCheckOnBorrow(true);
		sourceInfo.setCheckOnReturn(false);
		//sourceInfo.setPoolImplementClassName("org.jmin.bee.pool.ConnectionPool2");
		return new BeeDataSource(sourceInfo);
	}  		 
}
