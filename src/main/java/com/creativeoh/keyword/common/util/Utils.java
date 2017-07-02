package com.creativeoh.keyword.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat sfZero = new SimpleDateFormat("yyyyMMdd0000");
	
	public static long makeSimpleDate(Date date){
		return Long.valueOf(sf.format(date));
	}
	public static long makeSimpleDateZero(Date date){
		return Long.valueOf(sfZero.format(date));
	}
}
