package com.sdi.business.impl.user.command;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

public class FindAllUsersCommand<T> implements Command<List<User>> {


	public FindAllUsersCommand() {
	}

	@Override
	public List<User> execute() throws BusinessException {
		List<User> users = Factories.persistence.getUserDao().findAll();
		return users;
	}

}
