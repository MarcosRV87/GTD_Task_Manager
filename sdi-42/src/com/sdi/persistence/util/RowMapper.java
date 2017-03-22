package com.sdi.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads fields from a ResultSet row and returns a DTO instance of type T 
 * @author alb
 *
 * @param <T>
 */
public interface RowMapper<T> {

	T toObject(ResultSet rs) throws SQLException;
	
}
