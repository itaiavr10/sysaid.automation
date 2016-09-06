package com.core.utils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
	
	
	
	//TODO :Check
	public static long getTimeDiff(Calendar calendar1,Calendar calendar2,TimeUnit units) {
	    long milsecs1= calendar1.getTimeInMillis();
	    long milsecs2 = calendar2.getTimeInMillis();
	    long diff = milsecs1 -  milsecs2;
	    
	    if(units == TimeUnit.SECONDS){
	    	diff =  diff / 1000;
	    }
	    if(units == TimeUnit.MINUTES){
	    	diff =   diff / (60 * 1000);
	    }
	    if(units == TimeUnit.HOURS){
	    	diff =  diff / (60 * 60 * 1000);
	    }
	    if(units == TimeUnit.DAYS){
	    	diff =  diff / (24 * 60 * 60 * 1000);
	    }
	    return diff;
	}
	
	
	public static Calendar now() {
		return Calendar.getInstance();
	}

}
