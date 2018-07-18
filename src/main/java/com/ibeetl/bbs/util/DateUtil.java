package com.ibeetl.bbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	static String FORMAT = "yyyy-MM-dd";
	
	//2016-04-21T16:29:40+0800
	static String SONAR_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	public static Date parse(String day){
		if(day==null||day.length()==0) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		try {
			Date date = sdf.parse(day);
			return date;
		} catch (ParseException e) {
			return null;
		}
		
		
	}
	
	public static Date parseSonarDate(String day){
		SimpleDateFormat sdf = new SimpleDateFormat(SONAR_FORMAT);
		try {
			return sdf.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	public static Date[] getLastMonth(){
		return null;
	}
	
	public static void main(String[] args){
		Date date = parseSonarDate("2016-04-21T16:29:40+0800");
		System.out.println(date);
		
	}
}
