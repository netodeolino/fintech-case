package com.challenge.users.application.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
	public final ResponseEntity<Object> handleUnauthorizedException(NotFoundException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { UnauthorizedException.class })
	public final ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { UnprocessableException.class })
	public final ResponseEntity<Object> handleUnprocessableException(UnprocessableException ex) {
		ErrorResponse errorRes = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(FeignException.BadRequest.class)
	public final ResponseEntity<Object> handleFeignBadRequestException(FeignException ex, HttpServletResponse response) {
		ErrorResponse errorRes = new ErrorResponse(response.getStatus(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FeignException.Unauthorized.class)
	public final ResponseEntity<Object> handleFeignUnauthorizedException(FeignException ex, HttpServletResponse response) {
		ErrorResponse errorRes = new ErrorResponse(response.getStatus(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(FeignException.NotFound.class)
	public final ResponseEntity<Object> handleFeignNotFoundException(FeignException ex, HttpServletResponse response) {
		ErrorResponse errorRes = new ErrorResponse(response.getStatus(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FeignException.UnprocessableEntity.class)
	public final ResponseEntity<Object> handleFeignUnprocessableEntityException(FeignException ex, HttpServletResponse response) {
		ErrorResponse errorRes = new ErrorResponse(response.getStatus(), ex.getMessage());
		return new ResponseEntity<>(errorRes, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
