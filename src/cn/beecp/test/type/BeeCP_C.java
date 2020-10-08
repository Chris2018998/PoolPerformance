package cn.beecp.test.type;

import cn.beecp.test.Link;
import cn.beecp.BeeDataSource;
import cn.beecp.BeeDataSourceConfig;

/**
 *  compete mode for BeeCP
 * 
 */
public class BeeCP_C {
	public static BeeDataSource createDataSource() throws Exception{
		BeeDataSourceConfig config = new BeeDataSourceConfig();
		config.setDriverClassName(Link.JDBC_DRIVER);
		config.setJdbcUrl(Link.JDBC_URL);
		config.setUsername(Link.JDBC_USER);
		config.setPassword(Link.JDBC_PASSWORD);
		config.setMaxActive(Link.POOL_MAX_ACTIVE);
		config.setInitialSize(Link.POOL_INIT_SIZE);
		config.setMaxWait(Link.REQUEST_TIMEOUT);

 		config.setConnectionTestSQL("select 1 from dual");
		config.setDefaultAutoCommit(false);
		config.setTraceStatement(true);
		config.addConnectProperty("cachePrepStmts", "true");
		config.addConnectProperty("prepStmtCacheSize", "250");
		config.addConnectProperty("prepStmtCacheSqlLimit", "2048");
		config.addConnectProperty("useServerPrepStmts", "true");
		
	    return new BeeDataSource(config);
	}  		 
}
