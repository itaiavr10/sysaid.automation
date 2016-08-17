package utils;

import java.util.Calendar;

import base.LogManager;
import base.TestManager;

public class Utils {
	
	public  static boolean tryUntil(ActionWrapper func){
		LogManager.debug("Try Until - " + func.GetName());
		Calendar stopTime = Calendar.getInstance();
		stopTime.add(Calendar.MILLISECOND, func.TimeoutMs());
		int retry = 1;
		while (TimeUtils.now().before(stopTime)) {
			LogManager.debug("Try Until #" + retry++);
			boolean satisfied = false;
			try {
				satisfied = func.invoke();
			} catch (Throwable e) {
				LogManager.error("Try Until Exception : " + e.getMessage());
			}
			
			if (satisfied) {
				return true;
			} else {
				TestManager.sleep(func.Delay());
			}
		}
		return false;
	}

}
