package com.sc.dao.rdbms;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sc.dao.ConnectException;
import com.sc.dao.DaoException;
import com.sc.dao.iface.AbstractDao;
import com.sc.dao.iface.UserDao;
import com.sc.domain.User;
import com.sc.util.Formatter;

import com.ibatis.dao.client.DaoManager;

public class SqlMapUserDao extends AbstractSqlMapDao implements UserDao {

	public SqlMapUserDao(DaoManager arg){
		super(arg);

	}

	public Integer insertUser(User user) throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("FBId", user.getFacebookId());
		paramMap.put("FBToken", user.getFacebookToken());
		paramMap.put("Name",user.getName());
		paramMap.put("City",user.getCity());
		paramMap.put("State", user.getState());
		paramMap.put("Country", user.getCountry());
		paramMap.put("BirthDay", user.getBirthDay());
		paramMap.put("Gender", user.getGender());
		paramMap.put("Email", user.getEmail());
		paramMap.put("Status", user.getStatus());
		paramMap.put("ImageUrl", user.getImage());
		paramMap.put("Pwd", user.getPassword());
		paramMap.put("PwdToken", user.getPasswordToken());
		paramMap.put("PwdTokenExpires", user.getPasswordTokenExpires());
		paramMap.put("CreateDTM", new Date());
		return (Integer) insert("insertUser",paramMap);
	}
	
	public User getUser(String userid) throws DaoException, ConnectException {
		List users = queryForList("getUserByName", (userid !=null)?userid.toLowerCase():null);
		if (users != null && !users.isEmpty())
			return (User) users.get(0);
		return null;
	}


	public User getUserById(Integer uid) throws DaoException, ConnectException {
		return (User) queryForObject("getUserById", uid.toString());
	}

	public User getUserByEmail(String email) throws DaoException,
			ConnectException {
		List users = queryForList("getUserByEmail", (email != null)?email.toLowerCase():null);
		if (users != null && !users.isEmpty())
			return (User) users.get(0);
		return null;
	}

	@Override
	public void insertInvite(Integer sourceUid, String email, Integer taskId)
			throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("sid", sourceUid);
		paramMap.put("email", email);
		paramMap.put("itemId", taskId);
		insert("insertInvite", paramMap);
	}
	
	
	/*public void updateUser(User user) throws DaoException, ConnectException {
		Map paramMap = new HashMap();
		paramMap.put("userid", user.getUserid().toLowerCase());
		//paramMap.put("name", user.getFullName());
		paramMap.put("email", user.getEmail());
		paramMap.put("zip", user.getZip());
		paramMap.put("city", user.getCity());
		paramMap.put("state", user.getState());
		paramMap.put("country", user.getCountry());
		paramMap.put("bio", user.getBio());
		paramMap.put("receiveAlerts", user.getReceiveEmailAlerts());
		update("updateUser",paramMap);

	}

	public void updateUserLogo(User user) throws DaoException, ConnectException {
		Map paramMap = new HashMap();
		paramMap.put("userid", user.getUserid().toLowerCase());
		paramMap.put("logoDir", user.getLogoDir());
		update("updateUserLogo",paramMap);
	}

	

	public void updateUserStatus(String userid, String status, String token)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("userid", userid.toLowerCase());
		map.put("status", status);
		map.put("token", token);
		update("updateUserStatus", map);

	}

	public void resetPassword(User user, String encryptPwd, String salt)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("email", user.getEmail().toLowerCase());
		map.put("password", encryptPwd);
		map.put("salt", salt);
		map.put("resetPwd", "Y");
		update("resetPwd", map);


	}

	public void changePassword(User user, String encryptPwd, String salt)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("email", user.getEmail().toLowerCase());
		map.put("password", encryptPwd);
		map.put("salt", salt);
		map.put("resetPwd", "");
		update("updatePwd", map);

	}*/

	
	
}
