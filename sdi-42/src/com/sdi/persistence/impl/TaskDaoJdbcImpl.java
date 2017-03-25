package com.sdi.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.util.JdbcTemplate;
import com.sdi.persistence.util.RowMapper;

public class TaskDaoJdbcImpl implements TaskDao {
	
	public class TaskDtoMapper implements RowMapper<Task> {

		@Override
		public Task toObject(ResultSet rs) throws SQLException {
			return new Task()
				.setId( rs.getLong("id") )
				.setTitle( rs.getString("title") )
				.setComments( rs.getString("comments"))
				.setCreated( toDate( rs.getDate( "created") ))
				.setPlanned( toDate( rs.getDate( "planned") ))
				.setFinished( toDate( rs.getDate( "finished") ))
				.setCategoryId( (Long) rs.getObject("category_id") ) // may be null
				.setUserId( rs.getLong("user_id") );
		}
		
//		@Override
//		public Task toObject(ResultSet rs) throws SQLException {
//			Task task = new Task();
//			task.setId( rs.getLong("id") );
//			task.setTitle( rs.getString("title") );
//			task.setComments( rs.getString("comments") );
//			task.setCreated( toDate(rs.getDate("created")) );
//			task.setPlanned( toDate(rs.getDate("planned")) );
//			task.setFinished( toDate(rs.getDate("finished")));
//			task.setCategoryId( (Long) rs.getLong("category_id")); // may be null
//			task.setUserId( rs.getLong("user_id"));
//			return task;
//		}


		private Date toDate(java.sql.Date date) throws SQLException {
			return date != null 
				? new Date( date.getTime() )
				: null;
		}

	}

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public Long save(Task dto) {
		jdbcTemplate.execute("TASK_INSERT", 
				dto.getTitle(),
				dto.getComments(),
				dto.getCreated(),
				dto.getPlanned(),
				dto.getFinished(),
				dto.getCategoryId(),
				dto.getUserId()
			);
		return jdbcTemplate.getGeneratedKey();
	}

	@Override
	public int update(Task dto) {
		return jdbcTemplate.execute("TASK_UPDATE", 
				dto.getTitle(),
				dto.getComments(),
				dto.getCreated(),
				dto.getPlanned(),
				dto.getFinished(),
				dto.getCategoryId(),
				dto.getUserId(),
				
				dto.getId()
		);
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.execute("TASK_DELETE", id);
	}

	@Override
	public Task findById(Long id) {
		return jdbcTemplate.queryForObject(
				"TASK_FIND_BY_ID", 
				new TaskDtoMapper(), 
				id
			);
	}

	@Override
	public List<Task> findAll() {
		return jdbcTemplate.queryForList(
				"TASK_FIND_ALL", 
				new TaskDtoMapper()
			);
	}

	@Override
	public int deleteAllFromUserId(Long userId) {
		return jdbcTemplate.execute("TASK_DELETE_BY_USER_ID", userId);
	}

	@Override
	public int deleteAllFromCategory(Long catId) {
		return jdbcTemplate.execute("TASK_DELETE_BY_CATEGORY_ID", catId);
	}

	@Override
	public List<Task> findByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findInboxTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_INBOX_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findTodayTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_TODAY_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findWeekTasksByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_WEEK_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
	}

	@Override
	public List<Task> findTasksByCategoryId(Long catId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_UNFINISHED_BY_CATEGORY_ID", 
				new TaskDtoMapper(),
				catId
			);
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(Long catId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_FINISHED_BY_CATEGORY_ID", 
				new TaskDtoMapper(),
				catId
			);
	}

	@Override
	public List<Task> findFinishedTasksInboxByUserId(Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_FINISHED_INBOX_BY_USER_ID", 
				new TaskDtoMapper(),
				userId
			);
		
	}
	
	@Override
	public List<Task> findFinishedTasksByCategoryIdAndUserId(Long categoryId, Long userId) {
		return jdbcTemplate.queryForList(
				"TASK_FIND_UNFINISHED_BY_CATEGORY_ID_AND_USER_ID", 
				new TaskDtoMapper(),
				categoryId,
				userId
			);
		
	}

}
