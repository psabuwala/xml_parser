package com.tatva.xmlparser.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tatva.xmlparser.dto.ErrorDTO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> DefaultExceptionHandler(Exception e) {
		log.error(e.getStackTrace().toString());
		return ResponseEntity.badRequest().body(ErrorDTO.error("ERR-002", e.getMessage()));
	}
	
	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> ValidationExceptionHandler(ValidationException e) {
		log.error(e.getMessage());
		return ResponseEntity.badRequest().body(ErrorDTO.error("ValidationError", e.getMessage()));
	}

	@ExceptionHandler({ ParseException.class })
	public ResponseEntity<Object> ParseExceptionHandler(ParseException e) {
		log.error(e.getMessage());
		return ResponseEntity.internalServerError().body(ErrorDTO.error("ParseError", e.getMessage()));
	}
	
	
}
