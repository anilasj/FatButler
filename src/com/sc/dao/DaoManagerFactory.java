package com.sc.dao;

import java.io.Reader;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.client.DaoManagerBuilder;

public class DaoManagerFactory {
	public static transient Logger cat = Logger.getLogger(DaoManagerFactory.class);
	private static DaoManager rdbDaoManager = null;//set as default
	
	public static void init() throws DaoException{
		//instantiate csDaoManager
		try {
			//instantiate rdbDaoManager
			Reader reader = Resources.getResourceAsReader("com/sc/dao/rdbms/ib_dao.xml");
			cat.debug("got reader");
			com.ibatis.dao.client.DaoManager sqlMapDaoManager = DaoManagerBuilder.buildDaoManager(reader);
			cat.debug("got dao mgr");
			rdbDaoManager = (DaoManager) new com.sc.dao.rdbms.DaoManagerImpl(
					sqlMapDaoManager);
			cat.debug("got dao mgr impl");
			
		}catch (Exception ex){
			ex.printStackTrace();
			throw new DaoException (ex.getMessage());
		}
	}
	
	public static void init(String sqlMapFileName) throws DaoException{
		//instantiate csDaoManager
		try {
			//instantiate rdbDaoManager
			Reader reader = Resources.getResourceAsReader(sqlMapFileName);
			cat.debug("got reader");
			com.ibatis.dao.client.DaoManager sqlMapDaoManager = DaoManagerBuilder.buildDaoManager(reader);
			cat.debug("got dao mgr");
			rdbDaoManager = (DaoManager) new com.sc.dao.rdbms.DaoManagerImpl(
					sqlMapDaoManager);
			cat.debug("got dao magr impl");
			
		}catch (Exception ex){
			ex.printStackTrace();
			throw new DaoException (ex.getMessage());
		}
	}
	public static DaoManager getDaoManager(){
		if (rdbDaoManager == null){
			try {
				init();
			}catch (Exception ex){
				throw new RuntimeException("DaoManager not initialized");
			}
		}
		return rdbDaoManager;
	}
}
