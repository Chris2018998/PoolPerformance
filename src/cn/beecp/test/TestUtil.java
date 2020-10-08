/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.beecp.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chris.Liao
 * @version 1.0
 */
public class TestUtil {

	private final static Logger log = LoggerFactory.getLogger(TestUtil.class);

	public final static void oclose(ResultSet r) {
		try {
			r.close();
		} catch (Throwable e) {
			log.warn("Warning:Error at closing resultSet:", e);
		}
	}

	public final static void oclose(Statement s) {
		try {
			s.close();
		} catch (Throwable e) {
			log.warn("Warning:Error at closing statement:", e);
		}
	}

	public final static void oclose(Connection c) {
		try {
			c.close();
		} catch (Throwable e) {
			log.warn("Warning:Error at closing connection:", e);
		}
	}

}
