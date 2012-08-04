package com.sc.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler {

    public void setParameter(PreparedStatement ps, int i, Object 
    		parameter, String jdbcType) throws SQLException {
        ps.setString( i, ((Boolean) parameter).toString() );
    }

    public Object getResult(ResultSet rs, String columnName) throws 
    		SQLException {
    	String val = rs.getString( columnName );
    	if (val != null && val.equalsIgnoreCase("y"))
    		val = "true";
    	if (val != null && val.equalsIgnoreCase("n"))
    		val = "false";
        return Boolean.valueOf(val);
    }

    public Object getResult(ResultSet rs, int columnIndex) throws 
    		SQLException {
    	String val = rs.getString( columnIndex );
    	if (val != null && val.equalsIgnoreCase("y"))
    		val = "true";
    	if (val != null && val.equalsIgnoreCase("n"))
    		val = "false";
        return Boolean.valueOf( val );
    }

    public Object getResult(CallableStatement cs, int columnIndex) 
    		throws SQLException {
    	String val = cs.getString( columnIndex );
    	if (val != null && val.equalsIgnoreCase("y"))
    		val = "true";
    	if (val != null && val.equalsIgnoreCase("n"))
    		val = "false";
        return Boolean.valueOf( val );
    }

    public Object valueOf(String string) {      
    	if (string != null && string.equalsIgnoreCase("y"))
    		string = "true";
    	if (string != null && string.equalsIgnoreCase("n"))
    		string = "false";
        return Boolean.valueOf( string );       
    }

    public boolean equals(Object object, String string) {
    	if (string != null && string.equalsIgnoreCase("y"))
    		string = "true";
    	if (string != null && string.equalsIgnoreCase("n"))
    		string = "false";
        return  Boolean.valueOf( string ).equals( object );
    }

	public void setParameter(PreparedStatement ps, int i, Object parameter)
			throws SQLException {
		ps.setString( i, ((Boolean) parameter).toString() );
	}
    
    
}


