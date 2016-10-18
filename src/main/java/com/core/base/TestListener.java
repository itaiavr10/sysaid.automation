package com.core.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import buisness.pages.Admin;

import com.core.annotation.TestCase;
import com.core.utils.ScreenShooter;
import com.core.utils.SystemUtils;
import com.core.utils.TimeUtils;
import com.core.utils.VideoRecorder;



public class TestListener implements ITestListener, ISuiteListener , IInvokedMethodListener , IAnnotationTransformer{ 
	
	
	private static String testDescription = "";
	private static int totalRun=0;
	private static int passed = 0;
	private static int failed = 0;
	private static int skipped = 0;
	

	// --------------------> ISuiteListener 	

	public void onStart(ISuite suite) {
		SuiteReporter log = new SuiteReporter(suite.getName());
		LogManager.setReport(log);
		LogManager.info(String.format("VM Name: %s , IP: %s"  , SystemUtils.OS.getComputerName(), SystemUtils.OS.getCurrentIP()));
	}

	public void onFinish(ISuite suite) {
		LogManager.info(String.format("#Passed=%d , #Failed=%d , Skipped=%d",passed,failed,skipped));
		LogManager.getReporter().sendResult(totalRun);
	}

	//---------------> ITestListener 

	public void onTestStart(ITestResult result) {
		//System.out.println("-> onTestStart");
		totalRun++;
		SuiteReporter.initSoftAssert();
		initTestDescription(result) ;
		LogManager.bold(String.format("Starting Test: %s", testDescription));
		VideoRecorder.getInstance().startRecording();
	}

	public void onTestSuccess(ITestResult result) {
		passed++;
		onTestFinish(result,true);
	}
	
	
	private void initTestDescription(ITestResult result){
		 TestCase testcase = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestCase.class);
		 if(testcase == null){ // if TestCase annotation was not defined , get method name
			 testDescription =  result.getName();
			 LogManager.warn("Missing Test Case description. get test-method name");
		 }
		 else
			 testDescription = String.format("#%s - %s" , testcase.number(), testcase.description());
	}

	public void onTestFailure(ITestResult result) {
		failed++;
		onTestFinish(result,false);
	}
	
	private void onTestFinish(ITestResult result , boolean pass){
		if(pass){
			LogManager.pass(String.format("Test Passed: %s", testDescription ));
		}else{
			LogManager.error(String.format("Test Failed: %s", testDescription ));
			ScreenShooter.capture(testDescription);
		}
		VideoRecorder.getInstance().finishRecord(testDescription ,pass);
		long timeSec = TimeUtils.getTimeDiff(result.getEndMillis() , result.getStartMillis(), TimeUnit.SECONDS);
		
		LogManager.info(String.format("Test Execution time:  %d Minutes + %d Seconds" , timeSec/60 , timeSec%60 ));
	}

	public void onTestSkipped(ITestResult result) {
		skipped++;
		LogManager.info(String.format("Test: %s - Skiped!", result.getName()));
	}

	
	/**
	 * Invoked after the test class is instantiated and before any configuration method is called
	 */
	public void onStart(ITestContext context) {
		//System.out.println("->ITestListener onStart");
		//System.out.println(context.getSuite().getClass().getName());
		//System.out.println(context.getClass().getName());
		//System.out.println(context.getSuite().getName());
		//System.out.println(context.getName());
		LogManager.info("XML Test: " + context.getName());
		//TODO: If it is Agent Tests??
		//TODO: Missing suite class name
	}

	/**
	 * on suite class finish
	 * Invoked after all the tests have run and all their Configuration methods have been called
	 */
	public void onFinish(ITestContext context) {
		System.out.println("->ITestListener onFinish");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if(isTestMethod(testResult.getMethod().getConstructorOrMethod().getMethod())){
			//After Test Finished..
			Admin.get().teardown();
			SuiteReporter.softAssertAll();
		}
	}

	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		/*int skipTestID = 0;
		//if(isTestMethod(testMethod)){
			TestCase testcase =testMethod.getAnnotation(TestCase.class);
			if(testcase != null && testcase.number() == skipTestID){ //Skip Test
				annotation.setEnabled(false);
				//LogManager.warn("Skip test: " + testDescription);
				skipped++;
			}
		//}
*/	}
	
	
	private boolean isTestMethod(Method testMethod){
		Test test = testMethod.getAnnotation(Test.class);
		if(test != null)
			return true;
		return false;
	}

}
