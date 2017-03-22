package com.sdi.business.impl.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.PersistenceException;
import com.sdi.persistence.Transaction;

public class CommandExecutor<T> {
	
	public T execute(Command<T> cmd) throws BusinessException {
		Transaction trx = Factories.persistence.newTransaction();
		trx.begin();
		try {

			T res = cmd.execute();
			trx.commit();
			
			return res;
		}
		catch(PersistenceException | BusinessException ex) {
			trx.rollback();
			throw ex;
		}
	}

}
