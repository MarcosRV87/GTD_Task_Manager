package com.sdi.persistence.util;

import java.util.List;

/**
 * Generic interface for all types of Dao
 * @author alb
 *
 * @param <T>, the type of the object sent or retrieved from a row in a table
 * @param <K>, the type of the key in the table (may be composed)
 */
public interface GenericDao<T, K> {

	/**
	 * Saves a DTO to a table row and returns the generated key, if any
	 * @param dto
	 * @return The generated key K if any
	 */
	K save(T dto);

	/**
	 * Updates a table row with data from a DTO
	 * @param dto
	 * @return The number of affected rows (0 or 1)
	 */
	int update(T dto);
	
	/**
	 * Deletes a table row with the K id key
	 * @param K key in the table
	 * @return The number of affected rows (0 or 1)
	 */
	int delete(K id);
	
	T findById(K id);
	List<T> findAll();
	
}
