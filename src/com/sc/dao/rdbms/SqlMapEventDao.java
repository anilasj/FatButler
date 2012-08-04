package com.sc.dao.rdbms;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibatis.dao.client.DaoException;
import com.ibatis.dao.client.DaoManager;
import com.sc.dao.ConnectException;
import com.sc.dao.iface.EventDao;
import com.sc.dao.iface.TaskDao;
import com.sc.domain.Comment;
import com.sc.domain.Notification;
import com.sc.domain.Reminder;
import com.sc.domain.Task;

public class SqlMapEventDao extends AbstractSqlMapDao implements EventDao{

	public SqlMapEventDao(DaoManager arg) {
		super(arg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insertNotification(Notification notify) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("uid", notify.getUid());
		map.put("tid", notify.getTaskId());
		map.put("sid", notify.getSourceId());
		map.put("type", notify.getType());
		map.put("createDtm", new Date());
		insert("insertNotification", map);
		
	}

	@Override
	public void updateNotification(Notification notify) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("uid", notify.getUid());
		map.put("tid", notify.getTaskId());
		map.put("flg", notify.isProcessedFlg()?"Y":"N");
		update("updateNotification", map);
		
	}

	@Override
	public void deleteNotification(Notification notify) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("uid", notify.getUid());
		map.put("tid", notify.getTaskId());
		delete("deleteNotification", map);
		
	}

	@Override
	public void addComment(Integer taskId, Integer uid, String comment)
			throws DaoException, ConnectException {
		HashMap map = new HashMap();
		map.put("uid", uid);
		map.put("tid", taskId);
		map.put("comment", comment);
		delete("addComment", map);
		
	}

	@Override
	public List<Comment> getComments(Integer taskId, Integer maxCnt, Integer pageCnt) throws DaoException,
			ConnectException {
		HashMap map = new HashMap();
		map.put("tid", taskId);
		map.put("maxCnt", Comment.MAX_CNT + 1);
		if (pageCnt != null){
			int pgCnt = (pageCnt.intValue()) * Comment.MAX_CNT;
			if (pgCnt > 0)
				map.put("pgCnt", pgCnt);
		}
		return (List<Comment>) queryForList("getComments", map);
	}

	
}
