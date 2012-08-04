package com.sc.dao.iface;

import com.sc.dao.ConnectException;
import com.sc.dao.DaoException;
import com.sc.domain.User;


public interface UserDao extends AbstractDao{

	public abstract Integer insertUser(User user) throws DaoException, ConnectException ;
	public abstract User getUser(String name) throws DaoException, ConnectException ;
	public abstract User getUserById(Integer uid) throws DaoException, ConnectException ;
	public abstract User getUserByEmail(String email) throws DaoException, ConnectException ;
	
	public abstract void insertInvite(Integer sourceUid, String email, Integer taskId) throws DaoException, ConnectException;
	/*public abstract void updateUser(User user) throws DaoException, ConnectException;
	public abstract void resetPassword(User user, String encryptPwd, String salt) throws DaoException, ConnectException ;
	public abstract void changePassword(User user, String encryptPwd, String salt) throws DaoException, ConnectException ;
	
	public abstract void updateUserStatus(String userid, String status, String token) throws DaoException, ConnectException ;
	*/
	}
