package cn.bee.dbcp.test;

public interface TestResult {

	public int getFailedCount();

	public int getSuccessCount();
	
	public long[] getStartTime() ;
	
	public long[] getEndTime();
	 
}
