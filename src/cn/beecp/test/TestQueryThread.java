package cn.beecp.test;

import static java.lang.System.nanoTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import static java.lang.System.currentTimeMillis;
import javax.sql.DataSource;
import java.sql.ResultSet;


/**
 *  Thread to execute SQL 
 *  
 * @author Chris
 */
class TestQueryThread extends Thread implements TestResult {
	private String SQL;
	private int loopCount;
	private long[] startTime;
	private long[] endTime;

	private DataSource datasource;
	private CountDownLatch threadLatch;
	private int failedCount = 0;
	private int successCount = 0;
	
	public TestQueryThread(DataSource datasource, String SQL, int loopCount, CountDownLatch counter) {
		this.SQL = SQL;
		this.datasource = datasource;
		this.threadLatch = counter;
		this.loopCount = loopCount;
		this.startTime = new long[loopCount];
		this.endTime = new long[loopCount];
	}

	public int getFailedCount() {
		return failedCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public long[] getStartTime() {
		return startTime;
	}

	public long[] getEndTime() {
		return endTime;
	}

	public void run() {
		for (int i = 0; i < loopCount; i++) {
			startTime[i]=nanoTime();
			if (executeSQL(datasource,i,SQL)) {
				successCount++;
				endTime[i]=nanoTime();
			} else {
				failedCount++;
				startTime[i] =0;
				endTime[i] = 0;
			}
		}
		threadLatch.countDown();
	}

	private static boolean executeSQL(DataSource datasource,int index, String sql) {
		boolean ok;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		try {
			con = datasource.getConnection();
			st = con.prepareStatement(sql);
			st.execute(); 
		
			ok=true;
		} catch (Exception e) {
			e.printStackTrace();
			ok= false;
		} finally {
			TestUtil.oclose(st);
			TestUtil.oclose(con);
		}
		return ok;
	}
}
