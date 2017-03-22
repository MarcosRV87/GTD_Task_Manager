package com.sdi.business.impl.admin.command;

import com.sdi.business.exception.BusinessCheck;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;

public class DisableUserCommand implements Command<Void> {

	private Long id;

	public DisableUserCommand(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		UserDao uDao = Factories.persistence.getUserDao();
		
		User user = uDao.findById(id);
		BusinessCheck.isNotNull( user, "User does not exist" );
		
		user.setStatus( UserStatus.DISABLED );
		uDao.update( user );

		return null;
	}

}
