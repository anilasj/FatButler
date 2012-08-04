package com.sc.dao.rdbms;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibatis.dao.client.DaoException;
import com.ibatis.dao.client.DaoManager;
import com.sc.dao.ConnectException;
import com.sc.dao.iface.TaskDao;
import com.sc.domain.Reminder;
import com.sc.domain.Task;

public class SqlMapTaskDao extends AbstractSqlMapDao implements TaskDao{

	public SqlMapTaskDao(DaoManager arg) {
		super(arg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer insertTask(Task task) throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("name", task.getName());
		paramMap.put("dueDate", task.getDueDate());
		paramMap.put("priority", task.getPriority() != null?task.getPriority():Task.PRIORITY_NORMAL);
		paramMap.put("action", task.getAction());
		paramMap.put("shareType", task.getShareType() != null?task.getShareType():Task.SHARE_CUSTOM);
		paramMap.put("snoozeMins", task.getSnoozeMins());
		paramMap.put("status", task.getStatus() != null?task.getStatus():"A");
		paramMap.put("recurrence", task.getRecurrence() != null?task.getRecurrence():Task.RECUR_ONCE);
		paramMap.put("reminder", task.getReminder() != null?task.getReminder():Task.REMINDER_OFF);
		paramMap.put("listId", task.getListId());
		paramMap.put("createUid", task.getCreatedUid());
		paramMap.put("hasNotes",task.isHasNotes()?"Y":"N");
		paramMap.put("hasComments", task.isHasComments()?"Y":"N");
		paramMap.put("createDTM", new Date());
		return (Integer) insert("insertTask",paramMap);
	}

	@Override
	public void insertUserTask(Integer uid, Integer taskId, boolean confirmed)
			throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("uid", uid);
		paramMap.put("tid", taskId);
		paramMap.put("confirmFlg", confirmed?"Y":"N");
		insert("insertUserTask", paramMap);		
	}

	@Override
	public void updateUserTask(Integer uid, Integer taskId, boolean confirmed)
			throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("uid", uid);
		paramMap.put("tid", taskId);
		paramMap.put("confirmFlg", confirmed?"Y":"N");
		update("updateUserTask", paramMap);	
		
	}

	
	@Override
	public Task getUserTask(Integer uid, Integer taskId) throws DaoException,
			ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("uid", uid);
		paramMap.put("tid", taskId);
		return (Task) queryForObject("getUserTask", paramMap);
	}

	@Override
	public void insertUserTasks(List<Task> tasks)
			throws DaoException, ConnectException {
		super.startBatch();
		for (Task task:tasks)
			insertUserTask(task.getCreatedUid(), task.getId(), true);
		super.executeBatch();
	}
	
	@Override
	public void deleteUserTasks(Integer taskId) throws DaoException,
			ConnectException {
		delete("deleteUserTasks",taskId.toString());
		
	}

	@Override
	public void updateTask(Integer taskId, Task task) throws DaoException,
			ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("tid", taskId);
		if (task.getName() != null)
			paramMap.put("name", task.getName());
		if (task.getPriority() != null)
			paramMap.put("priority", task.getPriority());
		if (task.getShareType() != null)
			paramMap.put("shareType", task.getShareType());
		if (task.getDoneDate() != null){
			paramMap.put("doneFlg", task.isDone()?"Y":"N");
			paramMap.put("doneDate", task.getDoneDate());
		}
		if (task.getStatus() != null)
			paramMap.put("status", task.getStatus());
		if (task.getDueDate() != null)
			paramMap.put("dueDate", task.getDueDate());
		if (task.getAction() != null)
			paramMap.put("action", task.getAction());
		if (task.getReminder() != null)
			paramMap.put("reminder", task.getReminder());
		if (task.getSnoozeMins() != null)
			paramMap.put("snoozeMins", task.getSnoozeMins());
		if (task.getSnoozeDate() != null)
			paramMap.put("snoozeDate", task.getSnoozeDate());
		if (task.getRecurrence() != null)
			paramMap.put("recurrence", task.getRecurrence());
		update("updateTask", paramMap);
	}

	@Override
	public void deleteTask(Integer taskId) throws DaoException,
			ConnectException {
		delete("deleteTask", taskId.toString());
		
	}

	@Override
	public Task getTask(Integer id) throws DaoException, ConnectException {
		return (Task) queryForObject("getTaskById", id.toString());
	}

	@Override
	public void deleteReminder(Integer taskId) throws DaoException,
			ConnectException {
		delete("deleteReminder", taskId.toString());
		
	}

	@Override
	public void insertReminder(Reminder reminder) throws DaoException,
			ConnectException {
		insert("insertReminder", reminder);
		
	}

	@Override
	public void insertReminders(List<Reminder> reminders) throws DaoException, ConnectException {
		super.startBatch();
		for (Reminder reminder:reminders){
			insertReminder(reminder);
		}
		super.executeBatch();
		
	}

	@Override
	public void insertTasks(List<Task> childTasks) 
			throws DaoException, ConnectException {
		super.startBatch();
		for (Task task: childTasks){
			Integer id = insertTask(task);
		}
		super.executeBatch();
	}


	@Override
	public void insertNotes(Integer taskId, Integer uid, String comments)
			throws DaoException, ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("tid", taskId);
		paramMap.put("uid", uid);
		paramMap.put("comments", comments);
		paramMap.put("createDtm", new Date());
		delete("insertNotes", paramMap);
		
	}

	@Override
	public void updateNotes(Integer taskId, Integer uid, String comments) throws DaoException,
			ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("tid", taskId);
		paramMap.put("uid", uid);
		paramMap.put("comments", comments);
		delete("updateNotes", paramMap);
		
	}

	@Override
	public void deleteNotes(Integer taskId, Integer uid) throws DaoException,
			ConnectException {
		HashMap paramMap = new HashMap();
		paramMap.put("tid", taskId);
		paramMap.put("uid", uid);
		delete("deleteNotes", paramMap);
		
	}

	@Override
	public String getNotes(Integer taskId) throws DaoException, ConnectException {
		return (String) queryForObject("getNotes", taskId.toString());
		
	}

	@Override
	public void deleteRecurrence(Integer taskId) throws DaoException,
			ConnectException {
		delete("deleteRecurrence", taskId.toString());
	}

	@Override
	public void insertRecurrenceTask(Integer taskId, boolean doneFlg,
			Date doneDtm) throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("doneFlg", doneFlg?"Y":"N");
		map.put("doneDtm", doneDtm);
		insert("insertRecurrence", map);
		
	}

	@Override
	public List<Task> getTasks(Integer uid, String name, Date dueDt,
			String done, String overdue, List<String> orderBy)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("uid", uid);
		map.put("name", name != null?"%" + name.toLowerCase() + "%":null);
		map.put("dueDt", dueDt);
		map.put("done", done != null && (done.equalsIgnoreCase("y") || done.equalsIgnoreCase("n"))?done.toLowerCase():null);
		map.put("overdue", overdue != null && overdue.equalsIgnoreCase("y")?"Y":"N");
		map.put("orderby", orderBy);
		return (List<Task>) queryForList("getTasks", map);
		
	}

	@Override
	public void updateFlgs(Integer taskId, Boolean notesFlg, Boolean commentsFlg)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("tid", taskId);
		if (notesFlg != null)
			map.put("notesFlg", notesFlg.booleanValue()?"Y":"N");
		if (commentsFlg != null)
			map.put("commentsFlg", commentsFlg.booleanValue()?"Y":"N");
		update("updateTaskFlgs", map);
		
	}

	@Override
	public Integer getRecurrenceTask(Integer taskId, Date doneDtm)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("tid", taskId);
		map.put("dt", doneDtm);
		return (Integer)queryForObject("getRecurrenceTask", map);
	}

	@Override
	public void deleteRecurrenceTask(Integer recurrId) throws DaoException,
			ConnectException {
		delete("deleteRecurrenceTask", recurrId.toString());
		
	}

	@Override
	public void updateRecurrenceTask(Integer taskId, Integer recurrId,
			boolean doneFlg, Date doneDtm) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("taskId", taskId);
		map.put("doneFlg", doneFlg?"Y":"N");
		map.put("doneDtm", doneDtm);
		map.put("id", recurrId);
		update("updateRecurrenceTask", map);
	}

	
}
