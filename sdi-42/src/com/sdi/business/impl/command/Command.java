package com.sdi.business.impl.command;

import com.sdi.business.exception.BusinessException;

public interface Command<T> {

	T execute() throws BusinessException; 
	
}
