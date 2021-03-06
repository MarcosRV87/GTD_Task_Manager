package com.sdi.dto.util;

import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;

public class Cloner {

	public static User clone(User u) {
		User aux = new User();
		aux.setId(u.getId());
		aux.setEmail( u.getEmail() );
		aux.setIsAdmin( u.getIsAdmin() );
		aux.setLogin( u.getLogin() );
		aux.setPassword( u.getPassword() );
		aux.setStatus( u.getStatus() );
		return aux;
	}

//	public static User clone(User u) {
//		return u.setId(u.getId()).setEmail(u.getEmail())
//				.setIsAdmin(u.getIsAdmin()).setLogin(u.getLogin())
//				.setPassword(u.getPassword()).setStatus(u.getStatus());
//	}

//	public static Task clone(Task t) {
//		return new Task().setCategoryId(t.getCategoryId())
//				.setComments(t.getComments()).setCreated(t.getCreated())
//				.setFinished(t.getFinished()).setId(t.getId())
//				.setPlanned(t.getPlanned()).setTitle(t.getTitle())
//				.setUserId(t.getUserId());
//	}
	
	public static Task clone(Task t) {
		Task aux = new Task();
		aux.setId(t.getId());
		aux.setTitle( t.getTitle() );
		aux.setComments( t.getComments() );
		aux.setCreated( t.getCreated() );
		aux.setPlanned( t.getPlanned() );
		aux.setFinished( t.getFinished() );
		aux.setCategoryId( t.getCategoryId());
		aux.setUserId( t.getUserId());
		return aux;
	}

	public static Category clone(Category c) {
		return new Category().setName(c.getName()).setUserId(c.getUserId());
	}

}
