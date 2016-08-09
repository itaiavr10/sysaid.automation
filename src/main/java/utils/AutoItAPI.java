package utils;

import base.LogManager;
import base.TestManager;

public class AutoItAPI {

	private static Integer MaxTimeOut = 20;

	public static void waitWin(String winTitle) {
		waitWin(winTitle, "", MaxTimeOut);
	}

	public static void waitWin(String winTitle, String winText) {
		waitWin(winTitle, winText, MaxTimeOut);
	}

	public static void waitWin(String winTitle, String winText, Integer timeoutInSec) {
		boolean WinFound = AutoIt.engine().winWait(winTitle, winText, timeoutInSec);
		TestManager.validator().validate(WinFound, "Wait for Window: " + winTitle);
		AutoIt.engine().winActivate(winTitle, winText);

	}

	public static void waitWinClosed(String winTitle) {
		AutoIt.engine().winWaitClose(winTitle);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		boolean WinFound = AutoIt.engine().winWait(winTitle, "", 1);
		TestManager.validator().validate(!WinFound, "Close Window: " + winTitle);
	}

	public static void setControlText(String winTitle, String controlID, String text) {
		AutoIt.engine().ControlSetText(winTitle, "", controlID, text);
	}

	public static void clickButton(String winTitle, String controlText, String controlID) {
		AutoIt.engine().controlClick(winTitle, controlText, controlID);
	}

	public static void check(String winTitle, String controlID) {
		AutoIt.engine().winActivate(winTitle);
		AutoIt.engine().controlCommandCheck(winTitle, "", controlID);
	}

	public static void activateWindow(String winTitle) {
		AutoIt.engine().winActivate(winTitle);
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
