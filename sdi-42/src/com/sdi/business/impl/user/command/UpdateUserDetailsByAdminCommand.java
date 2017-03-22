package com.sdi.business.impl.user.command;

import com.sdi.business.exception.BusinessCheck;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.util.UserCheck;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;

public class UpdateUserDetailsByAdminCommand implements Command<Void> {

	private User user;

	public UpdateUserDetailsByAdminCommand(User user) {
		this.user = user;
	}

	@Override
	public Void execute() throws BusinessException {
		UserDao uDao = Factories.persistence.getUserDao();
		User previous = uDao.findById( user.getId() );

		checkUserExist( previous );
		checkIsAdminNotChanged( previous, user );
		UserCheck.isValidEmailSyntax( user ); 
		UserCheck.minLoginLength( user );
		UserCheck.minPasswordLength( user );
		
		if (loginIsChanged(previous, user) ) {
			UserCheck.notRepeatedLogin( user );
		}

		uDao.update( user );
		return null;
	}

	private void checkIsAdminNotChanged(User previous, User current)
			throws BusinessException {
		BusinessCheck.isTrue( isAdminNotChanged( previous, current ),
				"A user cannot be upgraded or downgraded" );
	}

	private void checkUserExist(User previous) throws BusinessException {
		BusinessCheck.isNotNull( previous, "The user does not exist");
	}

	private boolean loginIsChanged(User previous, User current) {
		return ! previous.getLogin().equals( current.getLogin() );
	}

	private boolean isAdminNotChanged(User previous, User current) {
		return previous.getIsAdmin() == current.getIsAdmin();
	}

}
