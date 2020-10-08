package cn.beecp.test;
import static java.lang.System.nanoTime;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.sql.DataSource;
import static java.lang.System.currentTimeMillis;

/**
 *  Thread to take-out connection from pool
 */
class TestTakeThread extends Thread  implements TestResult {
	private int loopCount;
	private long[] startTime;
	private long[] endTime;
	private DataSource datasource;
	private CountDownLatch threadLatch;
	private int failedCount = 0;
	private int successCount = 0;
	
	public TestTakeThread(DataSource datasourcel, int loopCount, CountDownLatch counter) {
		this.datasource = datasourcel;
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
			startTime[i] = nanoTime();
			if (execute(datasource,i)) {
				successCount++;
				endTime[i] = nanoTime();
			} else {
				failedCount++;
				startTime[i] =0;
				endTime[i] = 0;
			}
		}
		threadLatch.countDown();
	}

	private static boolean execute(DataSource datasource,int index) {
		boolean ok=true;
		Connection con = null;
		try {
			con = datasource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			ok=false;
		} finally {
			TestUtil.oclose(con);
		}
		return ok;
	}
}
