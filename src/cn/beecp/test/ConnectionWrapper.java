package cn.beecp.test;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ConnectionWrapper implements Connection {
	private Connection delegete;
	private volatile Boolean closedInd = Boolean.FALSE;
	private static final AtomicReferenceFieldUpdater<ConnectionWrapper, Boolean> closedStateUpd = AtomicReferenceFieldUpdater.newUpdater(ConnectionWrapper.class, Boolean.class, "closedInd");
	public ConnectionWrapper(Connection delegete) {
		this.delegete = delegete;
	}

	public <T> T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException {
		return delegete.unwrap(iface);
	}

	public boolean isWrapperFor(java.lang.Class<?> iface) throws java.sql.SQLException {
		return delegete.isWrapperFor(iface);
	}

	public Statement createStatement() throws SQLException {
		return delegete.createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return delegete.prepareStatement(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return delegete.prepareCall(sql);
	}

	public String nativeSQL(String sql) throws SQLException {
		return delegete.nativeSQL(sql);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		delegete.setAutoCommit(autoCommit);
	}

	public boolean getAutoCommit() throws SQLException {
		return delegete.getAutoCommit();
	}

	public void commit() throws SQLException {
		delegete.commit();
	}

	public void rollback() throws SQLException {
		delegete.rollback();
	}
	
	public void close() throws SQLException {
		if (closedStateUpd.compareAndSet(this, Boolean.FALSE, Boolean.TRUE))
			delegete.close();
	    else
	      throw new SQLException("Connection has been closed");
	}

	public boolean isClosed() throws SQLException {
		return closedInd;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return delegete.getMetaData();
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		delegete.setReadOnly(readOnly);
	}

	public boolean isReadOnly() throws SQLException {
		return delegete.isReadOnly();
	}

	public void setCatalog(String catalog) throws SQLException {
		delegete.setCatalog(catalog);
	}

	public String getCatalog() throws SQLException {
		return delegete.getCatalog();
	}

	public void setTransactionIsolation(int level) throws SQLException {
		delegete.setTransactionIsolation(level);
	}

	public int getTransactionIsolation() throws SQLException {
		return delegete.getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException {
		return delegete.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		delegete.clearWarnings();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return delegete.createStatement(resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return delegete.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return delegete.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public java.util.Map<String, Class<?>> getTypeMap() throws SQLException {
		return delegete.getTypeMap();
	}

	public void setTypeMap(java.util.Map<String, Class<?>> map) throws SQLException {
		delegete.setTypeMap(map);
	}

	public void setHoldability(int holdability) throws SQLException {
		delegete.setHoldability(holdability);
	}

	public int getHoldability() throws SQLException {
		return delegete.getHoldability();
	}

	public Savepoint setSavepoint() throws SQLException {
		return delegete.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return delegete.setSavepoint(name);
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		delegete.rollback(savepoint);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		delegete.releaseSavepoint(savepoint);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return delegete.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {

		return delegete.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return delegete.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return delegete.prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
		return delegete.prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
		return delegete.prepareStatement(sql, columnNames);
	}

	public Clob createClob() throws SQLException {
		return delegete.createClob();
	}

	public Blob createBlob() throws SQLException {
		return delegete.createBlob();
	}

	public NClob createNClob() throws SQLException {
		return delegete.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return delegete.createSQLXML();
	}

	public boolean isValid(int timeout) throws SQLException {
		return delegete.isValid(timeout);
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		delegete.setClientInfo(name, value);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		delegete.setClientInfo(properties);
	}

	public String getClientInfo(String name) throws SQLException {
		return delegete.getClientInfo(name);
	}

	public Properties getClientInfo() throws SQLException {
		return delegete.getClientInfo();
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return delegete.createArrayOf(typeName, elements);
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return delegete.createStruct(typeName, attributes);
	}

	public void setSchema(String schema) throws SQLException {
		delegete.setSchema(schema);
	}

	public String getSchema() throws SQLException {
		return delegete.getSchema();
	}

	public void abort(Executor executor) throws SQLException {
		delegete.abort(executor);
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		delegete.setNetworkTimeout(executor, milliseconds);
	}

	public int getNetworkTimeout() throws SQLException {
		return delegete.getNetworkTimeout();
	}
}
