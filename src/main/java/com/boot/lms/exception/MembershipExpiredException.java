package com.boot.lms.exception;

public class MembershipExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5012513554823056424L;

	public MembershipExpiredException(String message) {
		super(message);
	}
}
