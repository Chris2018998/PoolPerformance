package cn.beecp.test.type;

import cn.beecp.test.Link;
import cn.beecp.test.DataSourceWrapper;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.Validator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tomcat JDBC
 */
public class TomcatJDBC {

	public static DataSourceWrapper createDataSource()throws Exception {
		PoolProperties p = new PoolProperties();
		p.setUrl(Link.JDBC_URL);
		p.setDriverClassName(Link.JDBC_DRIVER);
		p.setUsername(Link.JDBC_USER);
		p.setPassword(Link.JDBC_PASSWORD);

		p.setInitialSize(Link.POOL_INIT_SIZE);
		p.setMaxActive(Link.POOL_MAX_ACTIVE);
		p.setMinIdle(Link.POOL_MIN_ACTIVE);
		p.setMaxIdle(Link.POOL_MAX_ACTIVE);
		p.setMaxWait(Link.REQUEST_TIMEOUT);
		p.setValidationQuery("select 1 from dual");
		p.setTestOnBorrow(true);
		p.setTestOnReturn(false);
		p.setDefaultAutoCommit(false);
		p.setRollbackOnReturn(true);

		p.setUseDisposableConnectionFacade(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState"); //;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		p.setValidationInterval(1000);
		p.setValidator(new Validator() {
			public boolean validate(Connection connection, int validateAction)
			{
				try {
					return (validateAction == PooledConnection.VALIDATE_BORROW ? connection.isValid(0) : true);
				}
				catch (SQLException e)
				{
					return false;
				}
			}
		});
		
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);
		return new DataSourceWrapper(datasource);
	}
}
