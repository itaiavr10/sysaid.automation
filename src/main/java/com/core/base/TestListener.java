package com.core.base;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.core.annotation.TestCase;
import com.core.utils.ScreenShooter;
import com.core.utils.SystemUtils;
import com.core.utils.TimeUtils;
import com.core.utils.VideoRecorder;

import buisness.modules.SysAidServer;


public class TestListener implements ITestListener, ISuiteListener , IInvokedMethodListener { //IInvokedMethodListener
	
	
	private static String testDescription = "";

	// --------------------> ISuiteListener 	

	public void onStart(ISuite suite) {
		//System.out.println("->ISuiteListener onStart");
		//System.out.println(suite.getName());
		SuiteReporter log = new SuiteReporter(suite.getName());
		LogManager.setReport(log);
		LogManager.info(String.format("VM Name: %s , IP: %s"  , SystemUtils.OS.getComputerName(), SystemUtils.OS.getCurrentIP()));
	}

	public void onFinish(ISuite suite) {
		//System.out.println("->ISuiteListener onFinish");
		LogManager.getReporter().sendResult();
	}

	//---------------> ITestListener 

	public void onTestStart(ITestResult result) {
		//System.out.println("-> onTestStart");
		SuiteReporter.initSoftAssert();
		initTestDescription(result) ;
		LogManager.bold(String.format("Starting Test: %s", testDescription));
		VideoRecorder.getInstance().startRecording();
	}

	public void onTestSuccess(ITestResult result) {
		//System.out.println("-> onTestSuccess");
		//System.out.println(result.getName());
		//LogManager.pass(String.format("Test: %s - Passed!", result.getName()));
	//	Assert.assertTrue(SuiteReporter.isTestPassed());  //TODO : should check if we do it in another place! issue : Test Pass + Test Fail in TESTNG Results
		onTestFinish(result,true);
		/*VideoRecorder.getInstance().finishRecord(false);
		LogManager.pass(String.format("Test Passed: %s", testDescription ));
		LogManager.bold(String.format("Test Execution time =  %s Seconds" , (result.getEndMillis()- result.getStartMillis())/1000 ))
		*/
		/*boolean testPassed = SuiteReporter.isTestPassed();
		testTerminationHandler(!testPassed);
		Assert.assertTrue(testPassed);*/	
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
		onTestFinish(result,false);
		/*//System.out.println("-> onTestFailure");
		LogManager.error(String.format("Test Failed: %s", testDescription ));
		//testTerminationHandler(true);
		ScreenShooter.capture();
		VideoRecorder.getInstance().finishRecord(true);
		LogManager.bold(String.format("Test Execution time =  %s Seconds" , (result.getEndMillis()- result.getStartMillis())/1000 ));*/
	}
	
	private void onTestFinish(ITestResult result , boolean pass){
		if(pass){
			LogManager.pass(String.format("Test Passed: %s", testDescription ));
		}else{
			LogManager.error(String.format("Test Failed: %s", testDescription ));
			ScreenShooter.capture();
		}
		VideoRecorder.getInstance().finishRecord(pass);
		long timeSec = TimeUtils.getTimeDiff(result.getEndMillis() , result.getStartMillis(), TimeUnit.SECONDS);
		
		LogManager.info(String.format("Test Execution time:  %d Minutes + %d Seconds" , timeSec/60 , timeSec%60 ));
		//LogManager.bold("Test Execution time:" + TimeUtils.getTimeDiff(result.getEndMillis() , result.getStartMillis(), TimeUnit.SECONDS));
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
		LogManager.info("XML Test: " + context.getName());
		//TODO: If it is Agent Tests??
		
		
		//TODO: Missing suite class name

	}

	// on suite class finish
	public void onFinish(ITestContext context) {
		System.out.println("->ITestListener onFinish");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		Test test = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
		if(test != null)
			SuiteReporter.softAssertAll();
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