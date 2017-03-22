package com.sdi.persistence;

import java.util.List;

import com.sdi.dto.Task;
import com.sdi.persistence.util.GenericDao;

public interface TaskDao extends GenericDao<Task, Long> {

	/**
	 * @param userId
	 * @return All tasks for the user without regard category, planned or 
	 * 		finished status
	 */
	List<Task> findByUserId(Long userId);
	
	/**
	 * Removes all user's tasks
	 * @param userId
	 * @return the number of tasks deleted
	 */
	int deleteAllFromUserId(Long userId);

	/**
	 * Removes all tasks in the specified categoty
	 * @param catId
	 * @return the number of tasks deleted
	 */
	int deleteAllFromCategory(Long catId);

	/**
	 * @param userId
	 * @return All not finished tasks in the user's inbox (without category)
	 */
	List<Task> findInboxTasksByUserId(Long userId);
	
	/**
	 * @param userId
	 * @return All not finished tasks of the user for today without regarding 
	 * 		category (inbox included)
	 */
	List<Task> findTodayTasksByUserId(Long userId);

	/**
	 * @param userId
	 * @return 	All not finished tasks of the user for the week	(today + 7 days), 
	 * 		or delayed, and for all categories (inbox included)
	 */
	List<Task> findWeekTasksByUserId(Long userId);
	
	/**
	 * @param catId
	 * @return All not finished tasks for a category (and user as a category 
	 * 		pertains to only one user)
	 */
	List<Task> findTasksByCategoryId(Long catId);

	/**
	 * @param catId
	 * @return All already finished tasks for a user and category
	 */
	List<Task> findFinishedTasksByCategoryId(Long catId);

	/**
	 * @param userId
	 * @return All already finished tasks in a user's inbox
	 */
	List<Task> findFinishedTasksInboxByUserId(Long userId);
	
	/**
	 * 
	 * @param categoryId
	 * @param userId
	 * @return All already finished tasks from a user with an concrete category
	 */
	List<Task> findFinishedTasksByCategoryIdAndUserId(Long categoryId, Long userId);

}
