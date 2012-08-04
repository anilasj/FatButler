package com.sc.dao.iface;

import java.util.Date;
import java.util.List;

import com.ibatis.dao.client.DaoException;
import com.sc.dao.ConnectException;
import com.sc.domain.Reminder;
import com.sc.domain.Task;

public interface TaskDao extends AbstractDao {
	public abstract Integer insertTask(Task task) throws DaoException, ConnectException ;
	public abstract void insertUserTask(Integer uid, Integer taskId, boolean confirmed) throws DaoException, ConnectException ;
	public abstract void updateUserTask(Integer uid, Integer taskId, boolean confirmed) throws DaoException, ConnectException ;
	public abstract Task getUserTask(Integer uid, Integer taskId) throws DaoException, ConnectException;
	public abstract List<Task> getTasks(Integer uid, String name, Date dueDt, String done, String overdue, List<String> orderBy) throws DaoException, ConnectException;
	
	public abstract void updateTask(Integer taskId, Task task) throws DaoException, ConnectException;
	public abstract void updateFlgs(Integer taskId, Boolean notesFlg, Boolean commentsFlg) throws DaoException, ConnectException;
	public abstract void deleteTask(Integer taskId) throws DaoException, ConnectException;
	public abstract void insertUserTasks(List<Task> tasks) throws DaoException, ConnectException;
	public abstract void deleteUserTasks(Integer taskId) throws DaoException, ConnectException;
	
	public abstract Task getTask(Integer id) throws DaoException, ConnectException;
	
	public abstract void deleteReminder(Integer taskId) throws DaoException, ConnectException;
	public abstract void insertReminder(Reminder reminder) throws DaoException, ConnectException;
	public abstract void insertReminders(List<Reminder> reminders) throws DaoException, ConnectException;
	
	public abstract void insertTasks(List<Task> childTasks) throws DaoException, ConnectException;
	public abstract void deleteRecurrence(Integer taskId) throws DaoException, ConnectException;
	public abstract void insertRecurrenceTask(Integer taskId, boolean doneFlg, Date doneDtm) throws DaoException, ConnectException;
	public abstract Integer getRecurrenceTask(Integer taskId, Date doneDtm) throws DaoException, ConnectException;
	public abstract void deleteRecurrenceTask(Integer recurrId)  throws DaoException, ConnectException;
	public abstract void updateRecurrenceTask(Integer taskId, Integer recurrId, boolean doneFlg, Date doneDtm) throws DaoException, ConnectException;
	
	public abstract void insertNotes(Integer taskId, Integer uid, String comments)  throws DaoException, ConnectException;
	public abstract void updateNotes(Integer taskId, Integer uid, String comments) throws DaoException, ConnectException;
	public abstract void deleteNotes(Integer taskId, Integer uid)   throws DaoException, ConnectException;
	public abstract String getNotes(Integer taskId)  throws DaoException, ConnectException;
}
