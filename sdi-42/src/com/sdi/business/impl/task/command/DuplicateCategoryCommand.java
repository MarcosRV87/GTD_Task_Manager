package com.sdi.business.impl.task.command;

import java.util.List;

import com.sdi.business.exception.BusinessCheck;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.dto.util.Cloner;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.CategoryDao;
import com.sdi.persistence.TaskDao;

public class DuplicateCategoryCommand implements Command<Long> {

	private Long origId;

	public DuplicateCategoryCommand(Long id) {
		this.origId = id;
	}

	@Override
	public Long execute() throws BusinessException {
		Long copyId = duplicateCategory( origId );
		duplicateTasks( origId, copyId );
		
		return copyId;
	}

	private Long duplicateCategory(Long id) throws BusinessException {
		CategoryDao cDao = Factories.persistence.getCategoryDao();
		
		Category original = cDao.findById(id);
		BusinessCheck.isNotNull( original, "The category does not exist");
		checkUserNotDisabled( original );
		
		Category copy = Cloner.clone(original);
		copy.setName( copy.getName() + " - copy");
		return cDao.save( copy );
	}

	private void checkUserNotDisabled(Category c) throws BusinessException {
		User u = Factories.persistence.getUserDao().findById( c.getUserId() );
		BusinessCheck.isTrue( u.getStatus().equals( UserStatus.ENABLED ),
				"User disables, category cannot be duplicated.");
	}

	private void duplicateTasks(Long catId, Long copyId) {
//		TaskDao tDao = Factories.persistence.getTaskDao();
//
//		List<Task> tasks = tDao.findTasksByCategoryId( catId );
//		for(Task t: tasks) {
//			//Task copy = Cloner.clone(t).setCategoryId( copyId );
//			tDao.save( copy );
//		}
	}

}
