package com.sc.service;


import org.apache.log4j.Logger;

import com.sc.dao.DaoManager;
import com.sc.dao.DaoManagerFactory;
import com.sc.dao.iface.ListDao;
import com.sc.dao.iface.TaskDao;
import com.sc.dao.iface.UserDao;
import com.sc.dao.rdbms.DaoManagerImpl;
import com.sc.domain.List;
import com.sc.domain.Task;
import com.sc.domain.User;
import com.sc.util.PasswordUtil;

public class ListService {
	private static Logger cat = Logger.getLogger(ListService.class);
	private static ListService service = null;
	
	public static ListService getService(){
		if (service == null)
			service = new ListService();
		return service;
	}
	
	public void insertList(List list) throws ServiceException{
		try {
		
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				Integer id = dao.insertList(list);
				list.setId(id);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - inserting list");
			throw new ServiceException (ex.getMessage());
		}

	}
	public void updateList(List list) throws ServiceException{
		if (list == null) return;
		
		try {
		
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				dao.updateList(list);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - updating list " + list.getId());
			throw new ServiceException (ex.getMessage());
		}

	}
	public void deleteList(Integer listId) throws ServiceException{
		if (listId == null) return;
		try {
		
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				dao.deleteChildList(listId);
				dao.deleteList(listId);
				
				dao.updateTaskForList(listId);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - delete list " + listId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public Integer makeListToDo(Integer listId, Integer uid) throws ServiceException {
		if (listId == null || uid == null) return null;
		
		try {
			
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				List list = dao.getList(listId);
				Task task = new Task(list.getName());
				task.setShareType(Task.SHARE_CUSTOM);
				task.setPriority(Task.PRIORITY_NORMAL);
				task.setStatus(Task.STATUS_ACTIVE);
				task.setCreatedUid(uid);
				task.setListId(list.getId());
				
				rdbmsDao.startTransaction();
				Integer taskId = taskDao.insertTask(task);
				
				//if the list id is a parent list
				//then add all its children to taskList
				if (list.getParentId() == null){
					dao.addTaskToListByParent(taskId, list.getParentId());
				}
				rdbmsDao.commitTransaction();
				return taskId;
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - delete list " + listId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void markTaskListDone(Integer taskId, Integer listId, boolean done) throws ServiceException {
		if (listId == null || taskId == null) return;
		try {
		
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				dao.markTaskToListDone(taskId, listId, done);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - mark list done flag for list " + listId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public java.util.List<List> getLists(Integer uid, String name) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return dao.getLists(uid, name);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting list for uid " + uid);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public java.util.List<List> getListItems(Integer listId) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return dao.getChildLists(listId);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting child item for list id " + listId);
			throw new ServiceException (ex.getMessage());
		}
	}
	public List getList(Integer listId) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			ListDao dao = (ListDao) daoMgr.getDao(ListDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return dao.getList(listId);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting list for id " + listId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
}
