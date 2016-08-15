package utils;

import base.LogManager;
import base.TestManager;

public class AutoItAPI {

	private static Integer MaxTimeOut = 30;
	
	private static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public static void waitWin(String winTitle) {
		waitWin(winTitle, "", MaxTimeOut);
	}

	public static void waitWin(String winTitle, String winText) {
		waitWin(winTitle, winText, MaxTimeOut);
	}

	public static void waitWin(String winTitle, String winText, Integer timeoutInSec) {
		boolean WinFound = AutoIt.engine().winWait(winTitle, winText, timeoutInSec);
		TestManager.validator().soft(WinFound, "Wait for Window: " + winTitle);
		activateWindow(winTitle, winText);
	}

	public static void waitWinClosed(String winTitle) {
		AutoIt.engine().winWaitClose(winTitle);
		sleep(1000);
		boolean WinFound = AutoIt.engine().winWait(winTitle, "", 1);
		TestManager.validator().soft(!WinFound, "Close Window: " + winTitle);
	}

	public static void setControlText(String winTitle, String controlID, String text) {
		AutoIt.engine().ControlSetText(winTitle, "", controlID, text);
	}

	public static void clickButton(String winTitle, String controlText, String controlID) {
		AutoIt.engine().controlClick(winTitle, controlText, controlID);
	}

	public static void check(String winTitle, String controlID) {
		activateWindow(winTitle, "");
		AutoIt.engine().controlCommandCheck(winTitle, "", controlID);
		
	}
	
	public static void validateVisibility(String winTitle, String controlID,boolean expected){
		activateWindow(winTitle, "");
		boolean actual = AutoIt.engine().controlCommandIsVisible(winTitle, "", controlID);
		TestManager.validator().validate(actual == expected, String.format("Validae Element visibility Expected = %s , Acutal = %s", expected,actual));
	}

	public static void activateWindow(String winTitle , String winText) {
		AutoIt.engine().winActivate(winTitle , winText);
		sleep(1200);
	}

	public static boolean isWinActive(String winTitle) {
		return AutoIt.engine().winWaitActive(winTitle, "", 1);
	}

	//moves relative to top left (0,0)
	public static void mouseMove(int x, int y) {
		AutoIt.engine().mouseMove(x, y);
	}

	public static void run(String filename) {
		LogManager.info("AutoItAPI Run : " + filename);
		AutoIt.engine().run(filename);

	}
}
