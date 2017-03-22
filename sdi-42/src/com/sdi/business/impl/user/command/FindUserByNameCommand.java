package com.sdi.business.impl.user.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

public class FindUserByNameCommand<T> implements Command<User> {

	private String login;


	public FindUserByNameCommand(String login) {
		this.login = login;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Factories.persistence.getUserDao()
						.findByLogin(login);
		return user;
	}

}
