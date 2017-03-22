package com.sdi.business.impl.user;

import java.util.List;

import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.CommandExecutor;
import com.sdi.business.impl.user.command.FindAllUsersCommand;
import com.sdi.business.impl.user.command.FindLoggableUSerCommand;
import com.sdi.business.impl.user.command.FindUserByIdCommand;
import com.sdi.business.impl.user.command.FindUserByNameCommand;
import com.sdi.business.impl.user.command.RegisterUserCommand;
import com.sdi.business.impl.user.command.UpdateUserDetailsByAdminCommand;
import com.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import com.sdi.dto.User;

public class UserServiceImpl implements UserService {

	@Override
	public Long registerUser(User user) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new RegisterUserCommand( user ) 
		);
	}

	@Override
	public void updateUserDetails(User user) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateUserDetailsCommand( user ) 
		);
	}
	
	@Override
	public void updateUserDetailsByAdmin(User user) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateUserDetailsByAdminCommand( user ) 
		);
	}

	@Override
	public User findLoggableUser(final String login, final String password) 
			throws BusinessException {
		
		return new CommandExecutor<User>().execute( 
				new FindLoggableUSerCommand<User>(login, password) 
		);
	}
	
	@Override
	public User findUserByName(final String login) 
			throws BusinessException {
		
		return new CommandExecutor<User>().execute( 
				new FindUserByNameCommand<User>(login)
		);
	}

	@Override
	public List<User> findAll() throws BusinessException {
		return new CommandExecutor<List<User>>().execute(
				new FindAllUsersCommand<List<User>>()
		);
	}

	@Override
	public User findUserById(Long id) throws BusinessException {
		return new CommandExecutor<User>().execute(
				new FindUserByIdCommand<User>(id)
				);
	}

}
