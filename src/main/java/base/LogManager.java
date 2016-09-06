package base;

public class LogManager {

	private final static ThreadLocal<SuiteReporter> report = new ThreadLocal<SuiteReporter>();

	public static void setReport(SuiteReporter log) {
		report.set(log);
	}

	public static SuiteReporter getReporter() {
		return report.get();
	}

	public static void bold(String msg) {
		report.get().bold(msg);
	}

	public static void pass(String msg) {
		report.get().pass(msg);
	}
	
	public static void debug(String msg) {
		report.get().debug(msg);
	}

	public static void info(String msg) {
		report.get().info(msg);
	}

	public static void warn(String msg) {
		report.get().warn(msg);
	}

	public static void error(String msg) {
		report.get().error(msg);
	}
	
	/**
	 * Will Print to report only if failed & Stop test
	 * @param condition
	 * @param msg
	 */
	public static void assertTrue(boolean condition , String msg) {
		report.get().assertTrue(condition,msg);
	}
	
	/**
	 * Will Print to report for any results & stop on fail
	 * @param condition
	 * @param msg
	 */
	public static void verifyAssert(boolean condition , String msg) {
		report.get().verifyAssert(condition,msg);
	}
	
	/**
	 * Will Print to report for any results & continue to run
	 * @param condition
	 * @param msg
	 */
	public static void verify(boolean condition , String msg) {
		report.get().verify(condition,msg);
	}

}
