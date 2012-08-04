package com.sc.dao.rdbms;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibatis.dao.client.DaoException;
import com.ibatis.dao.client.DaoManager;
import com.sc.dao.ConnectException;
import com.sc.dao.iface.ListDao;
import com.sc.dao.iface.TaskDao;
import com.sc.domain.Reminder;
import com.sc.domain.Task;

public class SqlMapListDao extends AbstractSqlMapDao implements ListDao{

	public SqlMapListDao(DaoManager arg) {
		super(arg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer insertList(com.sc.domain.List list) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("name", list.getName());
		map.put("parentId", list.getParentId());
		map.put("createUid", list.getCreatedUid());
		map.put("status", list.getStatus());
		Integer key = (Integer) insert("insertList", map);
		return key;
	}

	@Override
	public void deleteList(Integer listId) throws DaoException,
			ConnectException {
		delete("deleteList", listId.toString());
		
	}

	@Override
	public void updateList(com.sc.domain.List list) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("name", list.getName());
		map.put("id", list.getId());
		update("updateList", map);
		
	}

	@Override
	public void addTaskToList(Integer taskId, Integer listId)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("id", listId);
		insert("insertTaskList", map);
	}

	
	@Override
	public void addTaskToListByParent(Integer taskId, Integer parentListId)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("pid", parentListId);
		insert("insertTaskListByParent", map);
		
	}

	@Override
	public void updateTaskForList(Integer listId) throws DaoException,
			ConnectException {
		update("updateTaskForList", listId.toString());
		
	}

	@Override
	public void deleteChildList(Integer parentListId) throws DaoException,
			ConnectException {
		delete("deleteChildList", parentListId.toString());
	}

	@Override
	public void deleteTaskToList(Integer taskId, Integer listId)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("id", listId);
		delete("deleteTaskList", map);
	}

	@Override
	public void deleteTaskToList(Integer taskId) throws DaoException,
			ConnectException {
		delete("deleteTaskListByTaskId", taskId.toString());
	}

	@Override
	public void markTaskToListDone(Integer taskId, Integer listId, boolean done)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("id", listId);
		map.put("doneFlg", done?"Y":"N");
		map.put("doneDtm", new Date());
		update("updateTaskList", map);;
		
	}

	@Override
	public com.sc.domain.List getList(Integer listId) throws DaoException,
			ConnectException {
		return (com.sc.domain.List) queryForObject("getList", listId.toString());
	}

	@Override
	public List<com.sc.domain.List> getLists(Integer uid, String name) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("uid", uid);
		map.put("name", name != null?"%" + name.toLowerCase() + "%":null);
		return (List<com.sc.domain.List>) queryForList("getUserList", map);
	}

	@Override
	public List<com.sc.domain.List> getChildLists(Integer parentId)
			throws DaoException, ConnectException {
		return (List<com.sc.domain.List>) queryForList("getChildList", parentId.toString());
	}

	
}
