package br.com.asas.moneycontrol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.asas.moneycontrol.exception.ErrorResponse;
import br.com.asas.moneycontrol.exception.ItemException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ItemException.class)
	public ResponseEntity<ErrorResponse> exceptionItemHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage("A solicitação não pôde ser entendida pelo servidor devido à sintaxe malformada.");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);		
	}
}
