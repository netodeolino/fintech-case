package com.challenge.users.application.exception;

public class UnprocessableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnprocessableException(String exception) {
		super(exception);
	}

}
