package org.jmin.bee.test.type;

import java.beans.PropertyVetoException;

import org.jmin.bee.test.Link;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * CP30
 */
public class C3P0 {
	
	public static ComboPooledDataSource createDataSource() {
		ComboPooledDataSource datasource = new ComboPooledDataSource();
		try {
			datasource.setDriverClass(Link.JDBC_DRIVER);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		datasource.setJdbcUrl(Link.JDBC_URL);
		datasource.setUser(Link.JDBC_USER);
		datasource.setPassword(Link.JDBC_PASSWORD);
		datasource.setInitialPoolSize(Link.POOL_INIT_SIZE);
		datasource.setMinPoolSize(Link.POOL_MIN_ACTIVE);
		datasource.setMaxPoolSize(Link.POOL_MAX_ACTIVE);
		datasource.setCheckoutTimeout(Link.REQUEST_TIMEOUT);
		datasource.setMaxStatementsPerConnection(20);
		datasource.setTestConnectionOnCheckin(false);
		datasource.setTestConnectionOnCheckout(true);
		datasource.setPreferredTestQuery("select 1 from dual");
		return datasource;
	}
}
