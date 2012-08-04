package com.sc.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sc.dao.DaoManager;
import com.sc.dao.DaoManagerFactory;
import com.sc.dao.iface.EventDao;
import com.sc.dao.iface.TaskDao;
import com.sc.dao.rdbms.DaoManagerImpl;
import com.sc.domain.Comment;

public class EventService {
	private static Logger cat = Logger.getLogger(EventService.class);
	private static EventService service = null;
	
	public static EventService getService(){
		if (service == null)
			service = new EventService();
		return service;
	}
	
	public void addComment(Integer taskId, Integer uid, String comments) throws ServiceException {
		if (comments == null)
			return;
		else if (comments.length() > 300)
			comments = comments.substring(0,300);
		
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			EventDao eventDao = (EventDao) daoMgr.getDao(EventDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				eventDao.addComment(taskId, uid, comments);
				
				TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
				taskDao.updateFlgs(taskId, null, new Boolean(true));
				
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - addng comment for task " + taskId +" and uid " + uid);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public List<Comment> getComments(Integer taskId, Integer maxCnt, Integer pageCnt) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			EventDao taskDao = (EventDao) daoMgr.getDao(EventDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return taskDao.getComments(taskId, maxCnt, pageCnt);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting comments for task " + taskId );
			throw new ServiceException (ex.getMessage());
		}
	}
}
