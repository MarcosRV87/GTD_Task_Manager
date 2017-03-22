package com.sdi.business.exception;

/**
 * Thrown if an operation needs to save an entity and it already exists 
 * in the persistence system 
 */
public class EntityAlreadyExistsException extends Exception {

	public EntityAlreadyExistsException() {
	}

	public EntityAlreadyExistsException(String message) {
		super(message);
	}

	public EntityAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public EntityAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = -869974233276021892L;
}
