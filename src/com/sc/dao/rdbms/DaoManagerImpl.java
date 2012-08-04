package com.sc.dao.rdbms;

import com.ibatis.dao.client.Dao;
import com.ibatis.dao.client.DaoTransaction;
import com.ibatis.sqlmap.engine.transaction.Transaction;
import com.ibatis.sqlmap.engine.transaction.jdbc.JdbcTransaction;
import com.sc.dao.ConnectException;
import com.sc.dao.Connection;
import com.sc.dao.DaoException;
import com.sc.dao.DaoManager;
import com.sc.dao.iface.AbstractDao;


public class DaoManagerImpl implements DaoManager {
	private com.ibatis.dao.client.DaoManager daoMgr= null;
	
	public DaoManagerImpl(com.ibatis.dao.client.DaoManager mgr){
		daoMgr= mgr;
	}
	
	public void openConnection() throws ConnectException {
		// TODO Auto-generated method stub

	}

	public void closeConnection() throws ConnectException {
		// TODO Auto-generated method stub

	}

	public Connection getConnection() throws ConnectException {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractDao getDao(Class className) throws DaoException {
		// TODO Auto-generated method stub
		return (AbstractDao) daoMgr.getDao(className);
	}
	
	public void startTransaction() throws DaoException {
		try {
			daoMgr.startTransaction();
		}catch (Exception ex){
			throw new DaoException(ex);
		}
	}
	
	public void endTransaction() throws DaoException {
		try {
			daoMgr.endTransaction();
		}catch (Exception ex){
			throw new DaoException(ex);
		}
	}
	
	public void commitTransaction() throws DaoException {
		try {
			daoMgr.commitTransaction();
		}catch (Exception ex){
			throw new DaoException(ex);
		}
	}

}
