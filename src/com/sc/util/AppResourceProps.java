package com.sc.util;

import java.util.PropertyResourceBundle;

public class AppResourceProps {
	private static PropertyResourceBundle bundle;

	public synchronized static String getString(String key){
		return bundle.getString(key);
	}
	public synchronized static PropertyResourceBundle getBundle() {
		return bundle;
	}

	public synchronized static void setBundle(PropertyResourceBundle bundle) {
		AppResourceProps.bundle = bundle;
	}
	
	
	
	
}
