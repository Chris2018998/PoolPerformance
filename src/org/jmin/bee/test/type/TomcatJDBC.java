package org.jmin.bee.test.type;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.Validator;
import org.jmin.bee.test.Link;

/**
 * Tomcat JDBC
 */
public class TomcatJDBC {

	private static DataSource datasource;

	static{
		initDataSource();
	}

	public static void initDataSource() {
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
		p.setTestOnBorrow(true);
		p.setTestOnReturn(false);
		p.setValidationQuery("select 1 from dual");
		
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
		datasource = new DataSource();
		datasource.setPoolProperties(p);
	}


	public static DataSource getDatasource() {
		return datasource;
	}
	
}
