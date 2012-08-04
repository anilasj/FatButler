package com.sc.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sc.dao.DaoManager;
import com.sc.dao.DaoManagerFactory;
import com.sc.dao.iface.UserDao;
import com.sc.dao.rdbms.DaoManagerImpl;
import com.sc.domain.User;
import com.sc.util.PasswordUtil;

public class UserService {
	private static Logger cat = Logger.getLogger(UserService.class);
	private static UserService service = null;
	
	public static UserService getService(){
		if (service == null)
			service = new UserService();
		return service;
	}
	
	public String[] insertUser(User user) throws ServiceException{
		return insertUser(user, true);
	}
	public String[] insertUser(User user, boolean startTransaction) throws ServiceException{
		String[] errors = null;
		//errors = validateAddUser(user);
		//if (errors.hasErrors())
		//	return errors;
		
		try {
			
			//encrypt password
			List<String> encData = PasswordUtil.getEncrypted(user.getPassword());
			user.setPassword(encData.get(0));
			//user.setSalt(encData.get(1));
			
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			UserDao userDao = (UserDao) daoMgr.getDao(UserDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				if (startTransaction) rdbmsDao.startTransaction();
				Integer uid = userDao.insertUser(user);
				user.setId(uid);
				if (startTransaction) rdbmsDao.commitTransaction();
			}finally {
				if (startTransaction) rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - inserting user");
			throw new ServiceException (ex.getMessage());
		}
		return errors;
	}
	
	public User getUser(String name) throws ServiceException {
		try {
				DaoManager daoMgr = DaoManagerFactory.getDaoManager();
				UserDao userDao = (UserDao) daoMgr.getDao(UserDao.class);
				DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
				return userDao.getUser(name);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - get User by name =" + name);
			throw new ServiceException (ex.getMessage());
		}
	}
}
