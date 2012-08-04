package com.sc.dao.iface;

import com.ibatis.dao.client.DaoException;
import com.sc.dao.ConnectException;
import com.sc.domain.List;

public interface ListDao extends AbstractDao {
	public abstract Integer insertList(List list) throws DaoException, ConnectException;
	public abstract void deleteList(Integer listId)  throws DaoException, ConnectException;
	public abstract void updateList(List list)  throws DaoException, ConnectException;
	
	public abstract void addTaskToList(Integer taskId, Integer listId)  throws DaoException, ConnectException;
	public abstract void addTaskToListByParent(Integer taskId, Integer parentListId)  throws DaoException, ConnectException;
	public abstract void updateTaskForList(Integer listId) throws DaoException, ConnectException;
	public abstract void deleteChildList(Integer parentListId) throws DaoException, ConnectException;
	
	public abstract void deleteTaskToList(Integer taskId, Integer listId)  throws DaoException, ConnectException;
	public abstract void deleteTaskToList(Integer taskId)  throws DaoException, ConnectException;
	
	public abstract void markTaskToListDone(Integer taskId, Integer listId, boolean done)  throws DaoException, ConnectException;
	
	public abstract List getList(Integer listId) throws DaoException, ConnectException;
	public abstract java.util.List<List> getLists(Integer uid, String name)  throws DaoException, ConnectException;
	public abstract java.util.List<List> getChildLists(Integer parentId)  throws DaoException, ConnectException;
	
}
