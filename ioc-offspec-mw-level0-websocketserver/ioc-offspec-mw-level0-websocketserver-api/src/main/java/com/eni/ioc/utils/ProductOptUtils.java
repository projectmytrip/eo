package com.eni.ioc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProductOptUtils {

	
	
	public static Date getDateTime(String dateFromJSON){
		
		
		SimpleDateFormat formatterJson = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFormatted = null;

		
		try {
			dateFormatted = formatterJson.parse(dateFromJSON);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormatted ;
	}
	
public static Date getDate(String date){
		
		
		SimpleDateFormat formatterJson = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFormatted = null;

		
		try {
			dateFormatted = formatterJson.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormatted ;
	}
	
	
	
	public enum SectionName {
		 BASECASEENVIRONMENTAL,
		 INPUTSPROCESS,		 
		 INPUTSNETWORK,		  
		 INPUTSWELLRESERVOIR,
		 INPUTSMAINTENANCE,
		 BASECASEMAINTENANCE,		 
		 MODELCONSTRAINTENVIRONMENTAL,
		 MODELCONSTRAINTINPUTSPROCESS,
		 MODELCONSTRAINTINPUTSNETWORK,
		 MODELCONSTRAINTINPUTSWELLRESERVOIR,		 
		 MODELCONSTRAINTINPUTSMAINTENANCE;
	}
	
	
	public enum ModelTitle {
		 BASEMODEL,
		 OPTIMIZATIONMODEL;
	}
	
	public static String getDateFromDB(Date date){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	public static String getDateTime(Date date){
														  
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DDThh:mm:ss");
		return formatter.format(date);
	}
	
	public static String getDateTimeFromDB(Date date){
		  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return formatter.format(date);
	}
	
	
	public static Date getFirstDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
	
	public static Date getLastDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
	
	public static void jsonToMap(String t){

        Map<String, ArrayList<String>> map = new HashMap<String,  ArrayList<String>>();
        
        try {

	        JSONParser parser = new JSONParser();
	        JSONObject json;
	        
			json = (JSONObject) parser.parse(t.trim());

	        JSONArray domains = (JSONArray) json.get("domains");
	        Iterator<String> iterator = domains.iterator();
	        while (iterator.hasNext()) {
	        	System.out.println(iterator.next());
	            //String domain = iterator.next();
		        //map.put(key, value);
	        }
	

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
	
}
