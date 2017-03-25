package com.sdi.persistence;

import com.sdi.dto.User;
import com.sdi.persistence.util.GenericDao;

public interface UserDao extends GenericDao<User, Long>{

	User findByLogin(String login);
	User findByLoginAndPassword(String login, String password);
	void resetDatabase();
	
}
