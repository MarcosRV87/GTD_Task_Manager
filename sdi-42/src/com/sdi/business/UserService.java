package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

public interface UserService {

	public Long registerUser(User user) throws BusinessException;
	public void updateUserDetails(User user) throws BusinessException;
	public User findLoggableUser(String login, String password) throws BusinessException;
	public User findUserByName(String login) throws BusinessException;
	public User findUserById(Long id) throws BusinessException;
	public List<User> findAll() throws BusinessException;
	void updateUserDetailsByAdmin(User user) throws BusinessException;
	
}
