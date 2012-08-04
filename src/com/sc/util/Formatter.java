package com.sc.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.DatatypeConverter;


public class Formatter {
	public static String DAY_FORMAT_1 = "DD";
	public static String DAY_FORMAT_2 = "D";
	public static String PATTERN_DATE_1 = "MM/dd/yyyy HH:mm:ss";
	public static String PATTERN_DATE_2 = "EEE, MMM d yyyy 'at' h:mm a";
	public static String PATTERN_DATE_3 = "EEE, MMM d yyyy";
	public static String PATTERN_DATE_4 = "MMMMM dd, yyyy 'at' h:mm a";
	public static String PATTERN_DATE_5 = "MMM d, yyyy";
	public static String PATTERN_DATE_6 = "MM/dd/yyyy HH:mm a";
	public static String PATTERN_DATE_7 = "MMM d, yyyy h:mm a";
	public static String PATTERN_DATE_8 = "yyyy-MM-dd";
	public static String PATTERN_DATE_9 = "EEEEE, MMM d, yyyy";
	public static String PATTERN_DATE_ISO = "yyyy-MM-dd'T'HH:mm:ssZ";
	public final static NumberFormat cf = NumberFormat.getCurrencyInstance();
	public final static NumberFormat nf = NumberFormat.getInstance();
	
	public static String getString(String name){
		if (name != null)
			return name.trim();
		return name;
	}
	
	public static String getDateAsString(int style, Date date, Locale local){
		if (date == null) return "";
		
		DateFormat dateFormatter = DateFormat.getDateInstance(style,local);
		String dateOut = dateFormatter.format(date);
		return dateOut;

	}
	
	public static String getDateAsString(String pattern, Date date, Locale local){
		if (date == null) return "";
		if (pattern != null && pattern.equals(PATTERN_DATE_ISO)){
			Calendar val = Calendar.getInstance();
			val.setTime(date);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(val.getTimeInMillis());
			
			return DatatypeConverter.printDateTime(cal);
		}else {
			SimpleDateFormat sDateFormatter = null;
			if (local == null)
				sDateFormatter = new SimpleDateFormat(pattern);
			else
				sDateFormatter = new SimpleDateFormat(pattern, local);
			sDateFormatter.setLenient(false);
			return  sDateFormatter.format(date);

		}

	}
	
	public static String getDateAsString(int style, Date date){
		return getDateAsString(style ,date, Locale.getDefault());
	}
	
	public static String getDateAsString(String pattern, Date date){
		return getDateAsString(pattern, date, null);

	}
	
	public static String getDateAsString(Date date){
		return getDateAsString(DateFormat.DEFAULT ,date, Locale.getDefault());
	}
	
	public static String getDateAsStringPattern(Date date){
		return getDateAsString(PATTERN_DATE_1, date, null);

	}
	
	public static Date parseDate(String pattern, String dtStr, Locale local){
		try {
			if (pattern != null && pattern.equals(PATTERN_DATE_ISO)){
				Calendar dtCal = DatatypeConverter.parseDateTime(dtStr);
				return dtCal.getTime();
			}else {
				SimpleDateFormat sDateFormatter = null;
				if (local == null)
					sDateFormatter = new SimpleDateFormat(pattern);
				else
					sDateFormatter = new SimpleDateFormat(pattern, local);
				sDateFormatter.setLenient(false);
				return  sDateFormatter.parse(dtStr);
			}
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	public static Date parseDate(String dtStr){
		return parseDate(PATTERN_DATE_1, dtStr, null);
	}
	
	/*public static String getPrettyDateAsString(Date date){
		PrettyTime p = new PrettyTime(new Date());
		return p.format(date);

	}*/
	public static boolean isLeapYear(int year) {

		if ((year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}

	public static int daysInMonth() {

		System.out.println("4. No of Days in a month for a given date\n");
		Calendar c1 = Calendar.getInstance(); // new GregorianCalendar();
		c1.set(1999, 6, 20);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		// int days = c1.get(Calendar.DATE);
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31 };
		daysInMonths[1] += isLeapYear(year) ? 1 : 0;
		return daysInMonths[c1.get(Calendar.MONTH)];
		
	}
	
	public static String getDayInDate(Calendar dt, String format) {
		if (format.equals(DAY_FORMAT_2)){
			return (new Integer(dt.get(Calendar.DAY_OF_MONTH))).toString();
		}else if (format.equals(DAY_FORMAT_1))	{
			String retValue = (new Integer(dt.get(Calendar.DAY_OF_MONTH))).toString();
			if (retValue.length() == 1)
				retValue = "0" + retValue;
				return retValue;
		}
		return "";
	}
	
	public static String getMonthInDate(Calendar dt) {
		return (new Integer(dt.get(Calendar.MONTH) + 1)).toString();
	}
	
	public static Date getFirstDateInMonth(String month, String year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, (new Integer(year)).intValue());
		cal.set(Calendar.MONTH, (new Integer(month)).intValue());
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date getLastDateInMonth(String month, String year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getFirstDateInMonth(month, year));
		int lastDate = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, lastDate);
		return cal.getTime();
	}

	public static boolean isPastOrCurrentMonth(String month, String year){
		Calendar current = Calendar.getInstance();
		current.set(Calendar.DATE, 1);
		current.set(Calendar.HOUR, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		
		Calendar selectDate = Calendar.getInstance();
		selectDate.set(Calendar.MONTH, (new Integer(month)).intValue());
		selectDate.set(Calendar.YEAR, (new Integer(year)).intValue());
		selectDate.set(Calendar.DATE, 1);
		selectDate.set(Calendar.HOUR, 0);
		selectDate.set(Calendar.MINUTE, 0);
		selectDate.set(Calendar.SECOND, 0);
		selectDate.set(Calendar.MILLISECOND, 0);
		
		if (!selectDate.after(current))
			return true;
		return false;
		
	}
	
	public static String getPreFormattedText(String text){
		if (text == null || text.trim().equals("") || text.trim().equals("null")) return null;
		String retText = text;
		int firstIndex = -1;
		while (retText.indexOf("\n") != -1){
			int index =  retText.indexOf("\n");
			if (firstIndex != -1){
				
				retText = retText.substring(0, index) + "</p><p>" + retText.substring(index + 1);
				
			}else{
				retText = retText.substring(0, index) + "<p>" + retText.substring(index + 1);
				firstIndex = index;
			}
		}
		if (firstIndex != -1)
			retText = retText + "</p>";
		//check for //line chars for hr line
		while (retText.indexOf(";hrln") != -1){
			int index =  retText.indexOf(";hrln");
			retText = retText.substring(0, index) + "<BR><BR>" + retText.substring(index + 5);
		}
		return retText;
	}
	public static String getJSReadyString(String text){
		if (text == null || text.trim().equals("") || text.trim().equals("null")) return null;
		int index =0;int lastIndex = 0;
		String retText = null;
		while (index < text.length() && text.indexOf("'", index) != -1){
			index =  text.indexOf("'", index);
			if (retText != null)
				retText +=  text.substring(lastIndex + 1, index)+ "\\'";
			else
				retText = text.substring(0, index)  + "\\'";
			lastIndex = index;
			index++;
		}
		if (retText == null)
			retText = text;
		else
			retText += text.substring(text.lastIndexOf("'") + 1);
		return retText;
	}

	public static String getCurrencyFormat(Float amt){
		if (cf != null && amt != null)
			return cf.format(amt.floatValue());
		return "";
	}
	public static String getNumberFormat(Integer num){
		if (nf != null && num != null)
			return nf.format(num);
		return "";
	}
	//encoding and decoding to avoid cross site scripting
	public static String getEncodedString(String in) throws Exception{
		if (in == null) return "";
		try {
			return java.net.URLEncoder.encode(in,"UTF-8"); 
		}catch (Exception ex){
			return "";
		}
	}
	public static String getDecodedString(String in){
		if (in == null) return "";
		try {
			return java.net.URLDecoder.decode(in,"UTF-8"); 
		}catch (Exception ex){
			return "";
		}
	}
	public static String getPrettyNumberFormat(Integer num){
		//basically convert 1000s to K and millions to M and billions to B and trillions to T etc
		try {
			if (num == null) return null;
			int number = num.intValue();
			if (number < 1000) return number + "";
			if (number < 1000000) {
				float fract = number /1000;
				NumberFormat nf1 = NumberFormat.getInstance();
				nf1.setMaximumFractionDigits(1);
				return nf1.format(fract) + "K";
				
			}
			if (number < 1000000000) {
				float fract = number /1000000;
				NumberFormat nf1 = NumberFormat.getInstance();
				nf1.setMaximumFractionDigits(1);
				return nf1.format(fract) + "M";
				
			}
			
		}catch (Exception ex){
			
		}
		return num.toString();
	}
	
	public static float getPercent(Integer num, Integer denom){
     	float currAmt = (num != null)?num.floatValue():0;
		float percent = (currAmt * 100)/denom.floatValue();
		return Math.round(percent*100)/100f;
	}
	
	public static String getTitleCase(String input){
		if (input == null || input.length() <= 1)
			return input;
		return (input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase());
	}
	

}
