package com.sdi.persistence;

/**
 * Every isolated call to a DAO method is run in its own and self contained
 * transaction (JDBC auto commit mode way). This is enough for simple service 
 * layer operations that involves just one single persistence operation. But, 
 * when a service layer operation requires several calls to (perhaps
 * different) DAO(s) methods, all those calls must be done inside one single 
 * transaction. For that, one Transaction object must be acquired and then 
 * handled properly with the usual begin-commit-rollback semantics.
 * 
 * XxxDao xDao = Persistence.getXxxDao();
 * YyyDao yDao = Persistence.getYyyDao();
 * Transaction t = Persistence.newTransaction();
 * 
 * t.begin(); 
 * try {
 * 
 * 		xDao.save( ... ); 
 * 		yDao.delete( ... );
 * 
 * 		t.commit(); 
 * } catch (PersistenceException e) { 
 * 		t.rollback();
 * 		throw e; 
 * }
 * 
 * 
 * @author alb
 */
public interface Transaction {

	void begin();
	void commit();
	void rollback();

}
