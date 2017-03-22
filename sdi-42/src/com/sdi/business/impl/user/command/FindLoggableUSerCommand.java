package com.sdi.business.impl.user.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.infrastructure.Factories;

public class FindLoggableUSerCommand<T> implements Command<User> {

	private String login;
	private String password;

	public FindLoggableUSerCommand(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Factories.persistence.getUserDao()
						.findByLoginAndPassword(login, password);
		
		return (user != null && user.getStatus().equals( UserStatus.ENABLED ))
				? user
				: null;
	}

}
