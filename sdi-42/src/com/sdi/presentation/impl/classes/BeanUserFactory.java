package com.sdi.presentation.impl.classes;

import javax.faces.context.FacesContext;

import com.sdi.presentation.BeanUser;

public class BeanUserFactory {
	
	BeanUser user;
	
	public BeanUser createBeanUser(){
		user = (BeanUser) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("user"));
		// si no existe lo creamos e inicializamos
		if (user == null) {
			System.out.println("BeanUsers - No existia");
			user = new BeanUser();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("user", user);
		}
		return user;
	}

}
