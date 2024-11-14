package com.tatva.xmlparser.dto;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

	protected boolean isError;
	protected Map<String, String> errors;
	
	public static ErrorDTO error(String errorCode, String errorMsg) {
		ErrorDTO dto = new ErrorDTO();
		dto.isError = true;
		final Map<String, String> errors = new HashedMap<>();
		errors.put(errorCode, errorMsg);
		dto.errors = errors;
		return dto;
	}
}
