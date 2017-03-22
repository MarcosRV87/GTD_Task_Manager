package com.sdi.business.impl.admin.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.CategoryDao;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.UserDao;

public class DeepDeleteUserCommand implements Command<Void> {

	private Long userId;

	public DeepDeleteUserCommand(Long id) {
		this.userId = id;
	}

	@Override
	public Void execute() throws BusinessException {
		TaskDao tDao = Factories.persistence.getTaskDao();
		CategoryDao cDao = Factories.persistence.getCategoryDao();
		UserDao uDao = Factories.persistence.getUserDao();

		tDao.deleteAllFromUserId( userId );
		cDao.deleteAllFromUserId( userId );
		uDao.delete( userId );
		
		return null;
	}

}
