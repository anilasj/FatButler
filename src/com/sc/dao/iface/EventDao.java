package com.sc.dao.iface;

import java.util.List;

import com.ibatis.dao.client.DaoException;
import com.sc.dao.ConnectException;
import com.sc.domain.Comment;
import com.sc.domain.Notification;

public interface EventDao extends AbstractDao {

	public abstract void insertNotification(Notification notify) throws DaoException, ConnectException;
	public abstract void updateNotification(Notification notify) throws DaoException, ConnectException;
	public abstract void deleteNotification(Notification notify) throws DaoException, ConnectException;
	
	public abstract void addComment(Integer taskId, Integer uid, String comment) throws DaoException, ConnectException;
	public abstract List<Comment> getComments(Integer taskId, Integer maxCnt, Integer pageCnt) throws DaoException, ConnectException;
}
