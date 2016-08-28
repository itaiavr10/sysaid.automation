package base;

import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ScreenShooter;
import utils.VideoRecorder;


public class TestListener implements ITestListener, ISuiteListener { //IInvokedMethodListener

	// --------------------> ISuiteListener 	

	public void onStart(ISuite suite) {
		//System.out.println("->ISuiteListener onStart");
		//System.out.println(suite.getName());
		SuiteReporter log = new SuiteReporter(suite.getName());
		LogManager.setReport(log);
		//TestManager.setValidator(new Validate());

	}

	public void onFinish(ISuite suite) {
		//System.out.println("->ISuiteListener onFinish");
		LogManager.getReporter().sendResult();
	}

	//---------------> ITestListener 

	public void onTestStart(ITestResult result) {
		//System.out.println("-> onTestStart");
		LogManager.bold(String.format("Test: %s - Started..", result.getName()));
		VideoRecorder.getInstance().startRecording();
	}

	public void onTestSuccess(ITestResult result) {
		//System.out.println("-> onTestSuccess");
		//System.out.println(result.getName());
		//LogManager.pass(String.format("Test: %s - Passed!", result.getName()));
		Assert.assertTrue(SuiteReporter.isTestPassed());
		VideoRecorder.getInstance().finishRecord(false);
		LogManager.pass(String.format("Test: %s - Passed!", result.getName()));
		/*boolean testPassed = SuiteReporter.isTestPassed();
		testTerminationHandler(!testPassed);
		Assert.assertTrue(testPassed);*/
		
	}
	
	/*public void testTerminationHandler(boolean isFailed){
		if(isFailed)
			ScreenShooter.capture();
		VideoRecorder.getInstance().finishRecord(isFailed);
	}*/

	public void onTestFailure(ITestResult result) {
		//System.out.println("-> onTestFailure");
		LogManager.error(String.format("Test: %s - Failed!", result.getName()));
		//testTerminationHandler(true);
		ScreenShooter.capture();
		VideoRecorder.getInstance().finishRecord(true);
	}

	public void onTestSkipped(ITestResult result) {
		//System.out.println("-> onTestSkipped");
		LogManager.info(String.format("Test: %s - Skiped!", result.getName()));
	}

	public void onStart(ITestContext context) {
		//System.out.println("->ITestListener onStart");
		//System.out.println(context.getSuite().getClass().getName());
		//System.out.println(context.getClass().getName());
		//System.out.println(context.getSuite().getName());
		//System.out.println(context.getName());
		LogManager.bold("XML Test: " + context.getName());
		//TODO: Missing suite class name

	}

	public void onFinish(ITestContext context) {
		//System.out.println("->ITestListener onFinish");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

}

//---------------> IInvokedMethodListener	

/*public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		System.out.println("-> beforeInvocation");
		System.out.println(method.getClass().getName());
		System.out.println(method.getDate());
		System.out.println(method.getTestMethod().getMethodName());
		
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		System.out.println("-> afterInvocation");
		System.out.println(method.getClass().getName());
		System.out.println(method.getDate());
		System.out.println(method.getTestMethod().getMethodName());
		System.out.println(method.getTestResult());
	}*/