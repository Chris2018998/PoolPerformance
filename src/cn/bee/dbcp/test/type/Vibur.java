package cn.bee.dbcp.test.type;

import java.util.concurrent.Executors;

import cn.bee.dbcp.test.Link;
import org.vibur.dbcp.ViburDBCPDataSource;

public class Vibur {
	public static ViburDBCPDataSource createDataSource()throws Exception {
		ViburDBCPDataSource vibur = new ViburDBCPDataSource();
        vibur.setJdbcUrl( Link.JDBC_URL );
        vibur.setUsername(Link.JDBC_USER);
        vibur.setPassword(Link.JDBC_PASSWORD);
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
		vibur.setPoolFair(false);
        vibur.start();
		return vibur;
	}
}
