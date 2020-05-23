package com.picpay.users.exception;

public class UnprocessableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnprocessableException(String exception) {
		super(exception);
	}

}
