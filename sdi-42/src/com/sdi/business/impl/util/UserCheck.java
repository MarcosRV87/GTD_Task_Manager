package com.sdi.business.impl.util;

import java.util.regex.Pattern;

import com.sdi.business.exception.BusinessCheck;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;

public class UserCheck {

	public static void isNotAdmin(User user) throws BusinessException {
		String check = "A new admin cannot be registered"; 
		BusinessCheck.isFalse( user.getIsAdmin(), check);
	}

	public static void isValidEmailSyntax(User user) throws BusinessException {
		String check = "Not a valid email";
		BusinessCheck.isTrue( isValidEmail( user.getEmail()), check);
	}

	public static void minLoginLength(User user) throws BusinessException {
		String check = "The login must be at least 3 chars long";
		BusinessCheck.isTrue( user.getLogin().length() >= 3, check);
	}

	public static void minPasswordLength(User user) throws BusinessException {
		String check = "The password must be at least 6 chars long";
		BusinessCheck.isTrue( user.getPassword().length() >= 6, check);
	}

	public static void notRepeatedLogin(User user) throws BusinessException {
		UserDao uDao = Factories.persistence.getUserDao();
		User u = uDao.findByLogin( user.getLogin() );
		BusinessCheck.isNull(u, "The login is already used");
	}

	private static boolean isValidEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}"
				+ "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])"
				+ "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		
        return Pattern.compile(ePattern)
        		.matcher(email)
        		.matches();
    }

}
