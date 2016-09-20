package com.core.utils;

import com.core.base.LogManager;
import com.core.base.TestManager;

public class AutoItAPI {

	private static Integer MaxTimeOut = 30;
	
	private static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
	
	public static void verifyWinActivate(String winTitle , boolean expected){
		LogManager.verify(isWinActive(winTitle) == expected, "Verify Window Activate : " + winTitle);
	}

	public static void waitWin(String winTitle) {
		waitWin(winTitle, "", MaxTimeOut);
	}

	public static void waitWin(String winTitle, String winText) {
		waitWin(winTitle, winText, MaxTimeOut);
	}

	
	/**
	 * wait for window and verify win found
	 * @param winTitle
	 * @param winText
	 * @param timeoutInSec
	 */
	public static void waitWin(String winTitle, String winText, Integer timeoutInSec) {
		boolean WinFound = AutoIt.engine().winWait(winTitle, winText, timeoutInSec);
		LogManager.assertTrue(WinFound, String.format("Wait for Window: %s - %s " , winTitle , winText));
		activateWindow(winTitle, winText);
		sleep(3000); // VM is too slow
	}
	
	
	/**
	 * wait for window and return if found
	 * @param winTitle
	 * @param winText
	 * @param timeoutInSec
	 * @return
	 */
	public static boolean softWait(String winTitle, String winText, Integer timeoutInSec) {
		boolean WinFound = AutoIt.engine().winWait(winTitle, winText, timeoutInSec);
		return WinFound;
	}

	

	public static void waitWinClosed(String winTitle) {
		AutoIt.engine().winWaitClose(winTitle);
		sleep(1000);
		boolean WinFound = AutoIt.engine().winWait(winTitle, "", 1);
		LogManager.verify(!WinFound, "Close Window: " + winTitle);
	}

	public static void setControlText(String winTitle, String controlID, String text) {
		boolean success = AutoIt.engine().ControlSetText(winTitle, "", controlID, text);
		LogManager.assertTrue(success, "set text: " + text);
	}

	public static void clickButton(String winTitle, String controlText, String controlID) {
		boolean success = AutoIt.engine().controlClick(winTitle, controlText, controlID);
		LogManager.assertTrue(success, "click on element: " + controlText);
	}

	public static void check(String winTitle, String controlID) {
		activateWindow(winTitle, "");
		AutoIt.engine().controlCommandCheck(winTitle, "", controlID);
		
	}
	
	public static void verifyElementEnable(String winTitle, String controlID,boolean expected){
		activateWindow(winTitle, "");
		boolean actual = AutoIt.engine().controlCommandIsEnabled(winTitle, "", controlID);
		LogManager.verify(actual == expected, String.format("Verify Element Enable.  Expected = %s , Actual = %s", expected,actual));
		//LogManager.validateAssert(actual == expected, String.format("Validate Element Enable.  Expected = %s , Actual = %s", expected,actual));
	}
	
	public static void verifyVisibility(String winTitle, String controlID,boolean expected){
		activateWindow(winTitle, "");
		boolean actual = AutoIt.engine().controlCommandIsVisible(winTitle, "", controlID);
		LogManager.verifyAssert(actual == expected, String.format("Verify Element visibility Expected = %s , Actual = %s", expected,actual));
	}

	public static void activateWindow(String winTitle , String winText) {
		AutoIt.engine().winActivate(winTitle , winText);
		sleep(1000);
	}

	public static boolean isWinActive(String winTitle) {
		return AutoIt.engine().winWaitActive(winTitle, "", 1);
	}

	//moves relative to top left (0,0)
	public static void mouseMove(int x, int y) {
		AutoIt.engine().mouseMove(x, y);
	}

	public static void run(String filename) {
		LogManager.debug("AutoItAPI Run : " + filename);
		AutoIt.engine().run(filename);

	}
}
