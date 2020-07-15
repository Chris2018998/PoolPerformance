package cn.beecp.test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DataSourceWrapper implements DataSource {

	private DataSource delegete;

	public DataSourceWrapper(DataSource delegete) {
		this.delegete = delegete;
	}

	public Connection getConnection() throws SQLException {
		return new ConnectionWrapper(delegete.getConnection());
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return delegete.getConnection(username, password);
	}

	public java.io.PrintWriter getLogWriter() throws SQLException {
		return delegete.getLogWriter();
	}

	public void setLogWriter(java.io.PrintWriter out) throws SQLException {
		delegete.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		delegete.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return delegete.getLoginTimeout();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return delegete.getParentLogger();
	}

	public <T> T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException {
		return delegete.unwrap(iface);
	}

	public boolean isWrapperFor(java.lang.Class<?> iface) throws java.sql.SQLException {
		return delegete.isWrapperFor(iface);
	}
	
	public void terminate() {
		try {
			
			Method method = delegete.getClass().getMethod("terminate", new Class[0]);
			method.invoke(delegete, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			
			Method method = delegete.getClass().getMethod("close", new Class[0]);
			method.invoke(delegete, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
