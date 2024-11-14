package com.tatva.xmlparser.exception;

public class ValidationException  extends Exception {

	private static final long serialVersionUID = -7266095317854999066L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}
	
}
