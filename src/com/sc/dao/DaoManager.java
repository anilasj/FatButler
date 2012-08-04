package com.sc.dao;

import com.sc.dao.ConnectException;
import com.sc.dao.iface.AbstractDao;

public interface DaoManager {

	public void openConnection() throws ConnectException;
	public void closeConnection() throws ConnectException;
	public Connection getConnection() throws ConnectException;
	public AbstractDao getDao(Class className) throws DaoException;

}
