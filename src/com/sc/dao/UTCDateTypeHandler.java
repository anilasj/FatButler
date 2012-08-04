package com.sc.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

import com.sc.util.Formatter;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class UTCDateTypeHandler implements TypeHandler {

    public void setParameter(PreparedStatement ps, int i, Object 
    		parameter, String jdbcType) throws SQLException {
       java.util.Date date = (java.util.Date) parameter;
       if ( date == null )  
    	   ps.setNull(i,Types.TIMESTAMP);
       else {
    	   Timestamp timestamp = new Timestamp(date.getTime()); 
    	   ps.setTimestamp(i, timestamp,Calendar.getInstance(TimeZone.getTimeZone("UTC")));
       }
    }

    public Object getResult(ResultSet rs, String columnName) throws 
    		SQLException {
    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")); 
    	Timestamp timestamp = rs.getTimestamp(columnName, calendar); 
    	if (timestamp != null)
    		return new Integer(timestamp.getDate());
    	return null;
    }

    public Object getResult(ResultSet rs, int columnIndex) throws 
    		SQLException {
    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")); 
    	Timestamp timestamp = rs.getTimestamp(columnIndex, calendar); 
    	if (timestamp != null)
    		return new Integer(timestamp.getDate());
    	return null;
    }

    public Object getResult(CallableStatement cs, int columnIndex) 
    		throws SQLException {
    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")); 
    	Timestamp timestamp = cs.getTimestamp(columnIndex, calendar); 
    	if (timestamp != null)
    		return new Integer(timestamp.getDate());
    	return null;
    }

    public Object valueOf(String string) {       
        return Formatter.parseDate(string);
    }

    public boolean equals(Object object, String string) {
        return  Formatter.parseDate(string).equals( object );
    }

	public void setParameter(PreparedStatement ps, int i, Object parameter)
			throws SQLException {
		java.util.Date date = (java.util.Date) parameter;
	       if ( date == null )  
	    	   ps.setNull(i,Types.TIMESTAMP);
	       else {
	    	   Timestamp timestamp = new Timestamp(date.getTime()); 
	    	   ps.setTimestamp(i, timestamp,Calendar.getInstance(TimeZone.getTimeZone("UTC")));
	       }
	}
    
    
}


