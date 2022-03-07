package com.challenge.transaction.application.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String exception) {
		super(exception);
	}

}
