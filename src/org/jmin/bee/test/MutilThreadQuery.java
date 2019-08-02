package org.jmin.bee.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jmin.bee.BeeDataSource;
import org.jmin.bee.test.type.BeeCP_C;
import org.jmin.bee.test.type.BeeCP_F;
import org.jmin.bee.test.type.C3P0;
import org.jmin.bee.test.type.DBCP;
import org.jmin.bee.test.type.DBCP2;
import org.jmin.bee.test.type.Druid;
import org.jmin.bee.test.type.HikariCP;
import org.jmin.bee.test.type.TomcatJDBC;
import org.jmin.bee.test.type.Vibur;
import org.vibur.dbcp.ViburDBCPDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Performance of multiple thread execute SQL query
 * 
 * @author Chris
 */
public class MutilThreadQuery {
	static final int scale=4;
	static String testName = "Multiple thread query";
	static Logger log = Logger.getLogger(MutilThreadQuery.class);

	private static List<Object> testDBCP(String sql, int threadCount, int executeCount) throws Exception {
		org.apache.commons.dbcp.BasicDataSource dataource = DBCP.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "DBCP");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testDBCP2(String sql, int threadCount, int executeCount) throws Exception {
		org.apache.commons.dbcp2.BasicDataSource dataource = DBCP2.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "DBCP2");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testC3P0(String sql, int threadCount, int executeCount) throws Exception {
		ComboPooledDataSource dataource = C3P0.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "C3P0");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testTomcatJDBC(String sql, int threadCount, int executeCount) throws Exception {
		org.apache.tomcat.jdbc.pool.DataSource dataource = TomcatJDBC.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "Tomcat");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testVibur(String sql, int threadCount, int executeCount) throws Exception {
		ViburDBCPDataSource dataource = Vibur.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "Vibur");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testDruid(String sql, int threadCount, int executeCount) throws Exception {
		DruidDataSource dataource = Druid.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "Druid");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testHikariCP(String sql, int threadCount, int executeCount) throws Exception {
		HikariDataSource dataource = HikariCP.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "HikariCP");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testBeeCP_Compete(String sql, int threadCount, int executeCount) throws Exception {
		BeeDataSource dataource = BeeCP_C.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "BeeCP_Compete");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testBeeCP_Fair(String sql, int threadCount, int executeCount) throws Exception {
		BeeDataSource dataource = BeeCP_F.createDataSource();
		try {
			return test(sql, threadCount, executeCount, dataource, "BeeCP_Fair");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> test(String sql, int threadCount, int loopCount, DataSource dataSource,
			String sourceName) throws Exception {
		
		log.info("Pool["+sourceName+" -- "+testName+"] -- Begin{"+threadCount+"threads X "+loopCount+"iterate}");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 3);
		long concurrentTime = calendar.getTimeInMillis();

		CountDownLatch latch = new CountDownLatch(threadCount);
		TestQueryThread[] threads = new TestQueryThread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			threads[i] = new TestQueryThread(dataSource, sql, loopCount, latch, concurrentTime);
			threads[i].start();
		}

		latch.await();//wait all thread done
		List<Object> summaryList= TestResultPrint.printSummary(sourceName, testName, threads, threadCount, loopCount, scale);
		return summaryList;
	}

	public static void main(String[] args) throws Exception {
		Link.loadConfig();
		String sql = "select * from " + Link.THREAD_QUERY_TABLE;
		int threadCount = Link.REQUEST_THREAD_COUNT;
		int executeCount = Link.THREAD_QUERY_COUNT;
		String poolName = Link.POOL_TEST;
		if (args != null && args.length > 0)
			poolName = args[0];
		String[] pools = poolName.split(",");

		List<TestAvg> arvgList = new ArrayList<TestAvg>();
		List<List<Object>> allPoolResultList = new ArrayList<>();
		for (int i = 0; i < pools.length; i++) {
			String testPoolName = pools[i];
			testPoolName = testPoolName.trim();
			List<Object> poolResultList=null;
			
			if (testPoolName.equalsIgnoreCase("DBCP")) {
				poolResultList = testDBCP(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("DBCP2")) {
				poolResultList = testDBCP2(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("C3P0")) {
				poolResultList = testC3P0(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("TOMCAT")) {
				poolResultList = testTomcatJDBC(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Vibur")) {
				poolResultList = testVibur(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Druid")) {
				poolResultList = testDruid(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("HikariCP")) {
				poolResultList = testHikariCP(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Bee_C")) {
				poolResultList = testBeeCP_Compete(sql, threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Bee_F")) {
				poolResultList = testBeeCP_Fair(sql, threadCount, executeCount);
			} else {
				log.info("unkown pool type : " + testPoolName);
			}

			if (poolResultList != null) {
				allPoolResultList.add(poolResultList);
				arvgList.add(new TestAvg(testPoolName, (BigDecimal) poolResultList.get(2)));
			}
			if(pools.length>1)
			 LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
		}
		
		if(allPoolResultList.size()>1)
		 TestResultPrint.printAnalysis(poolName,testName,arvgList,allPoolResultList);
	}
}
