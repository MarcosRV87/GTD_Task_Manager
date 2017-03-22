package com.sdi.presentation.impl;

import com.sdi.presentation.BeanFactory;
import com.sdi.presentation.BeanUser;
import com.sdi.presentation.impl.classes.BeanUserFactory;

public class BeanFactoryImp implements BeanFactory {

	@Override
	public BeanUser createBeanUser() {
		return new BeanUserFactory().createBeanUser();
	}
	
	

}
