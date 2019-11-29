package cn.bee.dbcp.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vibur.dbcp.ViburDBCPDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;

import cn.bee.dbcp.BeeDataSource;
import cn.bee.dbcp.test.type.BeeCP_C;
import cn.bee.dbcp.test.type.BeeCP_F;
import cn.bee.dbcp.test.type.C3P0;
import cn.bee.dbcp.test.type.DBCP;
import cn.bee.dbcp.test.type.DBCP2;
import cn.bee.dbcp.test.type.Druid;
import cn.bee.dbcp.test.type.HikariCP;
import cn.bee.dbcp.test.type.TomcatJDBC;
import cn.bee.dbcp.test.type.Vibur;

/**
 * Performance of multiple thread take connection
 *  
 * @author Chris
 */
public class MutilThreadBorrow {
	static final int scale = 4;
	static String testName = "Multiple thread borrow";
	static Logger log = LoggerFactory.getLogger(MutilThreadBorrow.class);

	private static List<Object> testDBCP(int threadCount, int executeCount) throws Exception {
		org.apache.commons.dbcp.BasicDataSource dataource = DBCP.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "DBCP");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testDBCP2(int threadCount, int executeCount) throws Exception {
		org.apache.commons.dbcp2.BasicDataSource dataource = DBCP2.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "DBCP2");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testC3P0(int threadCount, int executeCount) throws Exception {
		ComboPooledDataSource dataource = C3P0.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "C3P0");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testTomcatJDBC(int threadCount, int executeCount) throws Exception {
		org.apache.tomcat.jdbc.pool.DataSource dataource = TomcatJDBC.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "Tomcat");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testVibur(int threadCount, int executeCount) throws Exception {
		ViburDBCPDataSource dataource = Vibur.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "Vibur");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testDruid(int threadCount, int executeCount) throws Exception {
		DruidDataSource dataource = Druid.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "Druid");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testHikariCP(int threadCount, int executeCount) throws Exception {
		HikariDataSource dataource = HikariCP.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "HikariCP");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testBeeCP_Compete(int threadCount, int executeCount) throws Exception {
		BeeDataSource dataource = BeeCP_C.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "BeeCP_Compete");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> testBeeCP_Fair(int threadCount, int executeCount) throws Exception {
		BeeDataSource dataource = BeeCP_F.createDataSource();
		try {
			return test(threadCount, executeCount, dataource, "BeeCP_Fair");
		} finally {
			dataource.close();
		}
	}

	private static List<Object> test(int threadCount, int loopCount, DataSource dataSource, String sourceName)
			throws Exception {
	
		log.info("Pool["+sourceName+" -- "+testName+"] -- Begin{"+threadCount+"threads X "+loopCount+"iterate}");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 3);
		long concurrentTime = calendar.getTimeInMillis();

		CountDownLatch latch = new CountDownLatch(threadCount);
		TestTakeThread[] threads = new TestTakeThread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			threads[i] = new TestTakeThread(dataSource, loopCount, latch, concurrentTime);
			threads[i].start();
		}
		latch.await();//wait all thread done
		List<Object> summaryList= TestResultPrint.printSummary(sourceName, testName, threads, threadCount, loopCount, scale);
		return summaryList;
	}

	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	public static void main(String[] args) throws Exception {
		Link.loadConfig();
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
			List<Object> poolResultList = null;

			if (testPoolName.equalsIgnoreCase("DBCP")) {
				poolResultList = testDBCP(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("DBCP2")) {
				poolResultList = testDBCP2(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("C3P0")) {
				poolResultList = testC3P0(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("TOMCAT")) {
				poolResultList = testTomcatJDBC(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Vibur")) {
				poolResultList = testVibur(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Druid")) {
				poolResultList = testDruid(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("HikariCP")) {
				poolResultList = testHikariCP(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Bee_C")) {
				poolResultList = testBeeCP_Compete(threadCount, executeCount);
			} else if (testPoolName.equalsIgnoreCase("Bee_F")) {
				poolResultList = testBeeCP_Fair(threadCount, executeCount);
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
