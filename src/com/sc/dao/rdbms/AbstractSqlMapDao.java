package com.sc.dao.rdbms;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;
import com.ibatis.sqlmap.client.event.RowHandler;

public abstract class AbstractSqlMapDao extends SqlMapDaoTemplate{

	public AbstractSqlMapDao(DaoManager arg){
		super(arg);
	}
	
	public void setDaoManager(com.sc.dao.DaoManager mgr) {
		// TODO Auto-generated method stub
		
	}
	
	public List queryForList(String arg0, Object arg1, RowHandler arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	

}
