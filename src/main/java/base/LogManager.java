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

	public static void info(String msg) {
		report.get().info(msg);
	}

	public static void warn(String msg) {
		report.get().warn(msg);
	}

	public static void error(String msg) {
		report.get().error(msg);
	}

}
