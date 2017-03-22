package com.sdi.business.exception;

/**
 * Thrown if an operation needs to load an entity and does't exist 
 * in the persistence system 
 */
public class EntityNotFoundException extends Exception {

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	private static final long serialVersionUID = -4201153961047491939L;
}
