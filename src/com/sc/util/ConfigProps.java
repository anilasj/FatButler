package com.sc.util;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;

import com.sc.dao.DaoManagerFactory;

public class ConfigProps {
	private static PropertyResourceBundle bundle;
	public static transient Logger cat = Logger.getLogger(ConfigProps.class);
	
	public synchronized static String getString(String key){
		if (bundle == null){
			try {
				PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle("config");
				setBundle(prb);
				
			}catch (MissingResourceException ex){
				cat.error("ERROR, ConfigProps NOT INITIALIZED");
			}
		}
		return (bundle != null)?bundle.getString(key):null;
	}
	public synchronized static PropertyResourceBundle getBundle() {
		return bundle;
	}

	public synchronized static void setBundle(PropertyResourceBundle bundle) {
		ConfigProps.bundle = bundle;
	}
	
	
	
	
}
