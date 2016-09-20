package com.core.utils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
	
	
	
	public static long getTimeDiff(Calendar calendar1,Calendar calendar2,TimeUnit units) {
	    return getTimeDiff(calendar1.getTimeInMillis(),calendar2.getTimeInMillis(),units);
	}
	
	public static long getTimeDiff(long milsecs1,long milsecs2,TimeUnit units) {
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
