package com.eni.ioc.assetintegrity.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class AssetIntegrityUtils {
	
	public static Calendar formatDate(Calendar c) {
		if(c!=null) {
		    c.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		}
		return c;
	}
	
	public static Calendar getTodayDate() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal;
	}
	
	public static Calendar getToday() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		
		return cal;
	}
	
	public static Calendar getDate(String date) {
		try {
			if (date != null) {
				Calendar calS = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				calS.setTime(sdf.parse(date));
				calS.set(Calendar.HOUR_OF_DAY, 0);
				calS.set(Calendar.MINUTE, 0);
				calS.set(Calendar.SECOND, 0);
				calS.set(Calendar.MILLISECOND, 0);
				
				return calS;
			}
			return null;
		} catch (ParseException e) {
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.formatError, "Wrong format of data");
		}
	}
		
	
}
