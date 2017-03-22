package com.sdi.business.impl.user.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

public class FindUserByIdCommand<T> implements Command<User> {

	private Long id;


	public FindUserByIdCommand(Long id) {
		this.id = id;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Factories.persistence.getUserDao()
						.findById(id);
		return user;
	}

}
