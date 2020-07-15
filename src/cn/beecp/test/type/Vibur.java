package cn.beecp.test.type;

import cn.beecp.test.Link;
import cn.beecp.test.DataSourceWrapper;
import org.vibur.dbcp.ViburDBCPDataSource;

import java.util.concurrent.Executors;

public class Vibur {
	public static DataSourceWrapper createDataSource()throws Exception {
		ViburDBCPDataSource vibur = new ViburDBCPDataSource();
        vibur.setJdbcUrl( Link.JDBC_URL );
        vibur.setUsername(Link.JDBC_USER);
        vibur.setPassword(Link.JDBC_PASSWORD);
        vibur.setDriverClassName(Link.JDBC_DRIVER);
        vibur.setConnectionTimeoutInMs(Link.REQUEST_TIMEOUT);
        vibur.setValidateTimeoutInSeconds(3);
        vibur.setLoginTimeoutInSeconds(2);

        vibur.setPoolInitialSize(Link.POOL_INIT_SIZE);
        vibur.setPoolMaxSize(Link.POOL_MAX_ACTIVE);
        vibur.setConnectionIdleLimitInSeconds(1);
        vibur.setAcquireRetryAttempts(0);
        vibur.setReducerTimeIntervalInSeconds(0);
        vibur.setUseNetworkTimeout(true);
        vibur.setNetworkTimeoutExecutor(Executors.newFixedThreadPool(1));
        
        vibur.setClearSQLWarnings(true);
        vibur.setResetDefaultsAfterUse(true);
        vibur.setDefaultAutoCommit(false);
		vibur.setPoolFair(false);
        vibur.start();
		return new DataSourceWrapper(vibur);
	}
}
