package com.sc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sc.dao.DaoManager;
import com.sc.dao.DaoManagerFactory;
import com.sc.dao.iface.EventDao;
import com.sc.dao.iface.ListDao;
import com.sc.dao.iface.TaskDao;
import com.sc.dao.iface.UserDao;
import com.sc.dao.rdbms.DaoManagerImpl;
import com.sc.domain.Notification;
import com.sc.domain.Reminder;
import com.sc.domain.Task;
import com.sc.domain.User;
import com.sc.util.EmailUtil;
import com.sc.util.Formatter;
import com.sc.util.PasswordUtil;

public class TaskService {
	private static Logger cat = Logger.getLogger(TaskService.class);
	private static TaskService service = null;
	
	public static TaskService getService(){
		if (service == null)
			service = new TaskService();
		return service;
	}
	
	public void insertTask(Task task) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				Integer tid = taskDao.insertTask(task);
				task.setId(tid);
				
				if (!task.getShareType().equals(Task.SHARE_PUBLIC))
					taskDao.insertUserTask(task.getCreatedUid(), tid, true);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - inserting task");
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void updateTask(Task task) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				taskDao.updateTask(task.getId(), task);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - inserting task");
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void deleteTask(Task task) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				taskDao.deleteNotes(task.getId(), task.getCreatedUid());
				taskDao.deleteReminder(task.getId());
				
				if (!task.getRecurrence().equals(Task.RECUR_ONCE)){
					taskDao.deleteRecurrence(task.getId());
				}
				/*List<Task> childTasks = taskDao.getChildTasks(task.getId(), false);
					taskDao.deleteRecurringTasks(task.getId(), task.getParentId());*/
				taskDao.deleteUserTasks(task.getId());
				taskDao.deleteTask(task.getId());
				
				if (task.getListId() != null){
					ListDao listDao = (ListDao) daoMgr.getDao(ListDao.class);
					listDao.deleteTaskToList(task.getId());
				}
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - deleting task id "+ task.getId());
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void setReminder(Integer taskId, Task task) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				//get task
				Task dbTask = taskDao.getTask(taskId);
				if (dbTask == null) return;
				
				rdbmsDao.startTransaction();
				//if task has reminders, delete them and add new ones
				//check if the reminder type has changed. If so, change the reminder type on the parent
				//and sibling recurring tasks
				String dbRecur = dbTask.getRecurrence();
				
				
	/////old code			
				if (task.getRecurrence() != null && !task.getRecurrence().equals(dbRecur)){
					if (!dbTask.getRecurrence().equals(Task.RECUR_ONCE)){
						//this was a recurring event that changed.
						
						//if new event is recurr_once and changed from parent recurring to none
						if (task.getRecurrence().equals(Task.RECUR_ONCE) ){
							taskDao.deleteRecurrence(taskId);
						}else{
							
							taskDao.deleteRecurrence(taskId);
							
							/*Task parentTask = new Task();
							parentTask.setRecurrence(task.getRecurrence());
							parentTask.setSnoozeMins(task.getSnoozeMins());
							parentTask.setReminder(task.getReminder());
							parentTask.setSnoozeDate(task.getSnoozeDate());
							taskDao.updateTask(dbTask.getParentId(),parentTask);
							
							taskDao.deleteSiblRecurringTasks(taskId, dbTask.getParentId());
							//setup child tasks based on schedule
							setupChildTasks(taskDao, task, dbTask, dbTask.getParentId());*/
							
						}
						
					}else{
						//if reminder is recurring, setup parent recurring event and setup all todo tasks
						//if (!task.getRecurrence().equals(Task.RECUR_ONCE)){
							//create parent recurring event
							
						//	taskDao.deleteRecurrence(taskId);
							
							/*Task parentTask = new Task(dbTask.getName());
							parentTask.setReminder(task.getReminder());
							parentTask.setRecurrence(task.getRecurrence());
							parentTask.setSnoozeMins(task.getSnoozeMins());
							parentTask.setCreatedUid(dbTask.getCreatedUid());
							parentTask.setSnoozeDate(task.getSnoozeDate());
							parentTask.setDueDate(task.getDueDate());
							parentTask.setStatus(Task.STATUS_RECURRING);
							Integer pid = taskDao.insertTask(parentTask);
							
							//update current task with parent task
							task.setParentId(pid);
							
							//setup child tasks based on schedule
							setupChildTasks(taskDao, task, dbTask, pid);*/
							
						//}
					}
				
				}
				//finally update current task
				taskDao.updateTask(taskId, task);
				
				if (task.getReminder() != null){
					taskDao.deleteReminder(taskId);
					
					//setup reminder
					if (!task.getReminder().equals(Task.REMINDER_OFF)){
						Reminder reminder = new Reminder();
						reminder.setTaskId(taskId);
						Calendar cal = Calendar.getInstance();
						cal.setTime(task.getSnoozeDate()!= null?task.getSnoozeDate():task.getDueDate());
						
						if (task.getSnoozeMins() != null){
							cal.set(Calendar.MINUTE, -1*task.getSnoozeMins());
						}
						reminder.setRunDate(cal.getTime());
						taskDao.insertReminder(reminder);
					}
				}
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - set reminder for id " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	/*private void setupChildTasks(TaskDao taskDao, Task task, Task dbTask, Integer parentId) throws Exception {
		List<Task> childTasks= setupRecurringTasks(task.getDueDate(), task.getRecurrence(), dbTask, parentId, task.getReminder(), task.getSnoozeMins(), task.getSnoozeDate());
		taskDao.insertTasks(childTasks)	;
		
		childTasks = taskDao.getRelatedTasks(dbTask.getId(), parentId, false);
		taskDao.insertUserTasks(childTasks);
		
		if (task.getReminder() != null && !task.getReminder().equals(Task.REMINDER_OFF) && (task.getSnoozeMins() != null || task.getSnoozeDate() != null)){
			//create reminders
			List<Reminder> reminders = new ArrayList<Reminder>();
			for (Task childTask:childTasks){
				Reminder reminder = new Reminder();
				reminder.setTaskId(childTask.getId());
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(childTask.getDueDate());
				
				if (task.getSnoozeDate() != null){
					Calendar scal = Calendar.getInstance();
					scal.setTime(childTask.getSnoozeDate());
					
					//get hour, min, sec from snooze date
					cal.set(Calendar.HOUR, scal.get(Calendar.HOUR));
					cal.set(Calendar.MINUTE, scal.get(Calendar.MINUTE));
					cal.set(Calendar.SECOND, scal.get(Calendar.SECOND));
				}
				if (task.getSnoozeMins() != null){
					cal.set(Calendar.MINUTE, -1*childTask.getSnoozeMins());
				}
				reminder.setRunDate(cal.getTime());
				reminders.add(reminder);
			}
			taskDao.insertReminders(reminders);
		}
	}*/
	
	/*private List<Task> setupRecurringTasks(Date startDate, String type, Task currentTask, Integer parentId, String reminder, Integer snoozeMins, Date snoozeDate){
		List<Task> childTasks = new ArrayList<Task>();
		
		if (type.equals(Task.RECUR_DAILY)){
			//setup 365 occurrences
			for (int i=0;i< 365; i++){
				Task childTask = new Task(currentTask.getName());
				childTask.setCreatedUid(currentTask.getCreatedUid());
				//setup date
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = cal.getTime();
				childTask.setDueDate(startDate);
				childTask.setParentId(parentId);
				childTask.setRecurrence(type);
				childTask.setSnoozeMins(snoozeMins);
				childTask.setSnoozeDate(snoozeDate);
				childTask.setReminder(reminder);
				childTasks.add(childTask);
			}
		}
		if (type.equals(Task.RECUR_WEEKLY)){
			//setup 365 occurrences
			for (int i=0;i< 48; i++){
				Task childTask = new Task(currentTask.getName());
				childTask.setCreatedUid(currentTask.getCreatedUid());
				//setup date
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.DAY_OF_MONTH, 7);
				startDate = cal.getTime();
				childTask.setDueDate(startDate);
				childTask.setParentId(parentId);
				childTask.setRecurrence(type);
				childTask.setSnoozeMins(snoozeMins);
				childTask.setSnoozeDate(snoozeDate);
				childTask.setReminder(reminder);
				
				childTasks.add(childTask);
			}
		}
		if (type.equals(Task.RECUR_MONTHLY)){
			//setup 365 occurrences
			for (int i=0;i< 12; i++){
				Task childTask = new Task(currentTask.getName());
				childTask.setCreatedUid(currentTask.getCreatedUid());
				//setup date
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.MONTH, 1);
				startDate = cal.getTime();
				childTask.setDueDate(startDate);
				childTask.setParentId(parentId);
				childTask.setRecurrence(type);
				childTask.setSnoozeMins(snoozeMins);
				childTask.setSnoozeDate(snoozeDate);
				childTask.setReminder(reminder);
				
				childTasks.add(childTask);
			}
		}
		if (type.equals(Task.RECUR_YEARLY)){
			//setup 365 occurrences
			for (int i=0;i< 3; i++){
				Task childTask = new Task(currentTask.getName());
				childTask.setCreatedUid(currentTask.getCreatedUid());
				//setup date
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.YEAR, 1);
				startDate = cal.getTime();
				childTask.setDueDate(startDate);
				childTask.setParentId(parentId);
				childTask.setRecurrence(type);
				childTask.setSnoozeMins(snoozeMins);
				childTask.setSnoozeDate(snoozeDate);
				childTask.setReminder(reminder);
				
				childTasks.add(childTask);
			}
		}
		return childTasks;
	}*/
	
	public void assignTask(Integer taskId, String name, String email, String userId, String sourceUserId) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				User user = null;
				Integer uid = null;
				Integer sourceUid = new Integer(sourceUserId);
				
				if (StringUtils.isBlank(userId)){
					
					UserDao userDao = (UserDao) daoMgr.getDao(UserDao.class);
					User sourceUser = userDao.getUserById(sourceUid);
					
					if (!StringUtils.isBlank(email)){
						user = userDao.getUserByEmail(email);
						if (user == null){
							//send invite
							
							Task currTask = taskDao.getTask(taskId);
							if (currTask != null){
								userDao.insertInvite(sourceUid, email, taskId);
								EmailUtil.sendAssignInvite(email, sourceUser, currTask) ;
							}
						}else
							uid = user.getId();
					}else{
						//then use name
						user = userDao.getUser(name);
						if (user != null)
							uid = user.getId();
					}
				}else
					uid = new Integer(userId);
				
				if (uid != null && uid.intValue() != sourceUid.intValue()){
					taskDao.deleteUserTasks(taskId);
					taskDao.insertUserTask(uid, taskId, false);	
					
					if (uid.intValue() != sourceUid.intValue()){
						//add notification
						Notification notify = new Notification();
						notify.setUid(uid);
						notify.setTaskId(taskId);
						notify.setSourceId(sourceUid);
						notify.setType(Notification.NOTIFY_ASSIGNMENT);
						
						EventDao eventDao = (EventDao) daoMgr.getDao(EventDao.class);
						eventDao.insertNotification(notify);
					}
				}
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - assign task id "+ taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void confirmAssignTask(Integer taskId, Integer uid) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			
			try{
				rdbmsDao.startTransaction();
				taskDao.updateUserTask(uid, taskId, true);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - confirm assignment for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void markDone(Task task, Integer uid) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			
			try{
				rdbmsDao.startTransaction();
				
				Date doneDt = new Date();
				Task uTask = new Task();
				uTask.setId(task.getId());
				uTask.setDone(true);
				uTask.setDoneDate(doneDt);
				taskDao.updateTask(task.getId(), uTask);
				
				if (!task.getRecurrence().equals(Task.RECUR_ONCE)){
					Integer id = taskDao.getRecurrenceTask(task.getId(), doneDt);
					if (id == null)
						taskDao.insertRecurrenceTask(task.getId(), true, doneDt);
					else
						taskDao.updateRecurrenceTask(task.getId(), id, true, doneDt);
				}
				
				if (uid.intValue() != task.getCreatedUid().intValue()){
					//add notification
					Notification notify = new Notification();
					notify.setUid(task.getCreatedUid());
					notify.setTaskId(task.getId());
					notify.setSourceId(uid);
					notify.setType(Notification.NOTIFY_DONE);
					
					EventDao eventDao = (EventDao) daoMgr.getDao(EventDao.class);
					eventDao.insertNotification(notify);
				}
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - confirm assignment for task " + task.getId());
			throw new ServiceException (ex.getMessage());
		}
		
	}
	public void insertNotes(Integer taskId, Integer createUid, String comments) throws ServiceException {
		if (comments == null)
			return;
		else if (comments.length() > 300)
			comments = comments.substring(0,300);
			
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			
			try{
				rdbmsDao.startTransaction();
				taskDao.insertNotes(taskId, createUid, comments);
				taskDao.updateFlgs(taskId, new Boolean(true), null);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - inserting notes for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void updateNotes(Integer taskId, Integer createUid, String comments) throws ServiceException {
		
		if (comments != null  && comments.length() > 300)
			comments = comments.substring(0,300);
		
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				taskDao.updateNotes(taskId, createUid, comments);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - updating notes for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public void deleteNotes(Integer taskId, Integer createUid) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			try {
				
				rdbmsDao.startTransaction();
				taskDao.deleteNotes(taskId, createUid);
				rdbmsDao.commitTransaction();
			}finally {
				rdbmsDao.endTransaction();
			}
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - deleting notes for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public String getNotes(Integer taskId, Integer uid) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return taskDao.getNotes(taskId);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting notes for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public Task getTask(Integer taskId) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return taskDao.getTask(taskId);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting details for task " + taskId);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public Task getUserTask(Integer taskId, Integer uid) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			return taskDao.getUserTask(uid,taskId);
		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting details for user task " + taskId + " and uid " + uid);
			throw new ServiceException (ex.getMessage());
		}
	}
	
	public List<Task> getTasks(Integer uid, String filterByName,
			String startDt, String include, int dtLimit, String path,
			List<String> dueDts, String done, String overDue, List<String> orderBy) throws ServiceException {
		try {
			DaoManager daoMgr = DaoManagerFactory.getDaoManager();
			TaskDao taskDao = (TaskDao) daoMgr.getDao(TaskDao.class);
			DaoManagerImpl rdbmsDao = (DaoManagerImpl) daoMgr;
			
			List<String> dates = new ArrayList();
			if ((dueDts == null || dueDts.isEmpty())){
				Calendar now = Calendar.getInstance();
				if (!StringUtils.isBlank(startDt))
					now.setTime(Formatter.parseDate(Formatter.PATTERN_DATE_8, startDt, null));
				
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(now.getTime());
				
				for (int i=0; i < dtLimit; i++){
					if (path.equalsIgnoreCase("f")){
						if (include.equals("y") && i == 0){
							dates.add(Formatter.getDateAsString(Formatter.PATTERN_DATE_8, now.getTime()));
							i++;
						}
						if (i < dtLimit){
							if (path.equalsIgnoreCase("f"))
								now.add(Calendar.DATE, 1);
							dates.add(Formatter.getDateAsString(Formatter.PATTERN_DATE_8, now.getTime()));							
						}
					}else{
						now.setTime(startCal.getTime());
						if (include.equals("y"))
							now.add(Calendar.DATE, -1 *(dtLimit - i - 1));
						else
							now.add(Calendar.DATE, -1 *(dtLimit - i));
						dates.add(Formatter.getDateAsString(Formatter.PATTERN_DATE_8, now.getTime()));
						
					}
					
					
					
				}
			}else {
				for (String dueDtStr: dueDts){
					dates.add(dueDtStr);
				}
			}

			List<Task> results = new ArrayList();
			int maxCnt = 4;int i=1;
			for (String dueDtStr: dates){
				Date dueDt = Formatter.parseDate(Formatter.PATTERN_DATE_8, dueDtStr, null);
				results.addAll(taskDao.getTasks(uid, filterByName, dueDt, done, overDue, orderBy)); 
				if (i > maxCnt)
					break;
				i++;
			}
			return results;

		}catch (Exception ex){
			ex.printStackTrace();
			cat.error("Exception - getting tasks for  uid " + uid);
			throw new ServiceException (ex.getMessage());
		}
	}
	
}
