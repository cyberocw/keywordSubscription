package com.creativeoh.keyword.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.creativeoh.keyword.Keyword;

public class Utils {
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat sfZero = new SimpleDateFormat("yyyyMMdd0000");
	private static SimpleDateFormat sfLog = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	
	public static long makeSimpleDate(Date date){
		return Long.valueOf(sf.format(date));
	}
	public static long makeSimpleDateZero(Date date){
		return Long.valueOf(sfZero.format(date));
	}
	public static String makeSimpleDateLog(Date date){
		return sfLog.format(date);
	}

}
