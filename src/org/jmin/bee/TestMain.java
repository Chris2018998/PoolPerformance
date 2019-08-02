package org.jmin.bee;

import org.jmin.bee.test.MutilThreadBorrow;
import org.jmin.bee.test.MutilThreadQuery;
import org.jmin.bee.test.SingleThreadBorrow;
import org.jmin.bee.test.SingleThreadQuery;

public class TestMain {
	public static void main(String[] args)throws Exception {
		SingleThreadBorrow.main(args);
		SingleThreadQuery.main(args);
		MutilThreadBorrow.main(args);
		MutilThreadQuery.main(args);
	}
}
