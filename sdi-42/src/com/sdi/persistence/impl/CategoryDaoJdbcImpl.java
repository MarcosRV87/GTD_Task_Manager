package com.sdi.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sdi.dto.Category;
import com.sdi.persistence.CategoryDao;
import com.sdi.persistence.util.JdbcTemplate;
import com.sdi.persistence.util.RowMapper;

public class CategoryDaoJdbcImpl implements CategoryDao {

	public class CategoryDtoMapper implements RowMapper<Category> {

		@Override
		public Category toObject(ResultSet rs) throws SQLException {
			return new Category()
					.setId( rs.getLong( "id" ))
					.setName( rs.getString( "name" ))
					.setUserId( rs.getLong( "user_id" ));
		}
	}
	
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public Long save(Category dto) {
		jdbcTemplate.execute("CATEGORY_INSERT",	
				dto.getName(), 
				dto.getUserId()
			);
		return jdbcTemplate.getGeneratedKey();
	}

	@Override
	public int update(Category dto) {
		return jdbcTemplate.execute("CATEGORY_UPDATE",
				dto.getName(),
				dto.getUserId(),
				dto.getId()
			);
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.execute("CATEGORY_DELETE", id);
	}

	@Override
	public Category findById(Long id) {
		return jdbcTemplate.queryForObject(
				"CATEGORY_FIND_BY_ID", 
				new CategoryDtoMapper(), 
				id
			);
	}

	@Override
	public List<Category> findAll() {
		return jdbcTemplate.queryForList("CATEGORY_FIND_ALL", new CategoryDtoMapper());
	}

	@Override
	public List<Category> findByUserId(Long userId) {
		return jdbcTemplate.queryForList("CATEGORY_FIND_BY_USER_ID", 
				new CategoryDtoMapper(),
				userId 
			);
	}

	@Override
	public int deleteAllFromUserId(Long userId) {
		return jdbcTemplate.execute("CATEGORY_DELETE_BY_USER_ID", userId);
	}

	@Override
	public Category findByUserIdAndName(Long userId, String name) {
		return jdbcTemplate.queryForObject(
				"CATEGORY_FIND_BY_USER_ID_AND_NAME", 
				new CategoryDtoMapper(), 
				userId, name
			);
	}

}
