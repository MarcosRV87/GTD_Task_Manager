package com.sdi.business.impl.util;

import com.sdi.business.exception.BusinessCheck;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.infrastructure.Factories;

public class TaskCheck {

	public static void categoryExists(Task task) throws BusinessException {
		Category c = Factories.persistence.getCategoryDao().findById( task.getCategoryId());
		BusinessCheck.isNotNull( c, "The category of the task does not exist");
	}

	public static void userExists(Task task) throws BusinessException {
		User u = Factories.persistence.getUserDao().findById( task.getUserId());
		BusinessCheck.isNotNull( u, "The user of the task does not exist");
	}

	public static void userIsNotDisabled(Task task) throws BusinessException {
		User u = Factories.persistence.getUserDao().findById( task.getUserId());
		BusinessCheck.isTrue( u.getStatus().equals( UserStatus.ENABLED), 
				"The user of the task is disabled");
	}

	public static void userIsNotAdmin(Task task) throws BusinessException {
		User u = Factories.persistence.getUserDao().findById( task.getUserId());
		BusinessCheck.isFalse( u.getIsAdmin(), 
				"The user of the task cannot be an admin");
	}

	public static void titleIsNotNull(Task task) throws BusinessException {
		BusinessCheck.isTrue( task.getTitle() != null, 
				"The title of the task is cannot be null");
	}

	public static void titleIsNotEmpty(Task task) throws BusinessException {
		BusinessCheck.isTrue( ! "".equals( task.getTitle() ), 
				"The title of the task is cannot be empty");
	}

}
