package com.picpay.users.config;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.picpay.users.exception.ErrorResponse;
import com.picpay.users.exception.NotFoundException;
import com.picpay.users.exception.UnauthorizedException;
import com.picpay.users.exception.UnprocessableException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<Object> handleException(Exception ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	public final ResponseEntity<Object> handleUUnauthorizedException(NotFoundException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { UnauthorizedException.class })
	public final ResponseEntity<Object> handleUUnauthorizedException(UnauthorizedException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { UnprocessableException.class })
	public final ResponseEntity<Object> handleUnprocessableException(UnprocessableException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
