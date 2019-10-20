package cn.bee.dbcp.test;

import cn.bee.dbcp.test.Link;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Some JDBC info
 * 
 * @author Administrator
 */
public class Link {
	public static String JDBC_USER;
	public static String JDBC_PASSWORD;
	public static String JDBC_DRIVER;
	public static String JDBC_URL;

	public static String POOL_TEST;
	public static int POOL_MAX_ACTIVE;
	public static int POOL_MIN_ACTIVE;
	public static int POOL_INIT_SIZE;

	public static int REQUEST_THREAD_COUNT;
	public static int THREAD_QUERY_COUNT;
	public static String THREAD_QUERY_TABLE;
	public static int REQUEST_TIMEOUT=8000;

	public static String LINK_FILE = "Link.properties";
	static Logger log = Logger.getLogger(Link.class);
	
	public static void loadConfig() throws Exception {
		InputStream fileStream = null;

		try {
			fileStream = Link.class.getClassLoader().getResourceAsStream(LINK_FILE);
			if (fileStream == null)
				fileStream = Link.class.getResourceAsStream(LINK_FILE);
			Properties prop = new Properties();
			prop.load(fileStream);

			JDBC_USER = prop.getProperty("JDBC_USER");
			JDBC_PASSWORD = prop.getProperty("JDBC_PASSWORD");
			JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
			JDBC_URL = prop.getProperty("JDBC_URL");

			POOL_TEST=prop.getProperty("POOL_TEST");
			POOL_MAX_ACTIVE = Integer.parseInt(prop.getProperty("POOL_MAX_ACTIVE"));
			POOL_MIN_ACTIVE = Integer.parseInt(prop.getProperty("POOL_MIN_ACTIVE"));
			POOL_INIT_SIZE = Integer.parseInt(prop.getProperty("POOL_INIT_SIZE"));
			try{
				REQUEST_TIMEOUT = Integer.parseInt(prop.getProperty("REQUEST_TIMEOUT"));
			}catch(Exception e){
			}

			REQUEST_THREAD_COUNT = Integer.parseInt(prop.getProperty("REQUEST_THREAD_COUNT"));
			THREAD_QUERY_COUNT = Integer.parseInt(prop.getProperty("THREAD_QUERY_COUNT"));
			THREAD_QUERY_TABLE = prop.getProperty("THREAD_QUERY_TABLE");

			if (JDBC_USER == null || JDBC_USER.trim().length() == 0)
				throw new Exception("'USER_ID' not be configered");
//			if (JDBC_PASSWORD == null || JDBC_PASSWORD.trim().length() == 0)
//				throw new Exception("'JDBC_PASSWORD' not be configered");
			if (JDBC_DRIVER == null || JDBC_DRIVER.trim().length() == 0)
				throw new Exception("'JDBC_DRIVER' not be configered");
			if (JDBC_URL == null || JDBC_URL.trim().length() == 0)
				throw new Exception("'JDBC_URL' not be configered");
			if (POOL_TEST == null || POOL_TEST.trim().length() == 0)
				throw new Exception("'POOL_TYPE' not be configered");
			
			if (POOL_MAX_ACTIVE == 0)
				throw new Exception("'POOL_MAX_ACTIVE' not be configered");
			if (REQUEST_THREAD_COUNT == 0)
				throw new Exception("'REQUEST_THREAD_COUNT' not be configered");
			if (THREAD_QUERY_COUNT == 0)
				throw new Exception("'THREAD_QUERY_COUNT' not be configered");
			if (THREAD_QUERY_TABLE == null || THREAD_QUERY_TABLE.trim().length() == 0)
				throw new Exception("'THREAD_QUERY_COUNT' not be configered");
			
			if(JDBC_DRIVER!=null && JDBC_DRIVER.trim().length()>0){
				Class driverClass=Class.forName(JDBC_DRIVER.trim());
				DriverManager.registerDriver((Driver)driverClass.newInstance() );
			}
			
			log.info("[JDBC INFO]");
			log.info("[Driver:"+ JDBC_DRIVER +"]");
			log.info("[URL:"+ JDBC_URL +"]");
			log.info("\n");
		} finally {
			if (fileStream != null) {
				try {
					fileStream.close();
				} catch (Exception e) {
				}
			}
		}
	}
}