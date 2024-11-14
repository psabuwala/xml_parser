package com.tatva.xmlparser.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseListDTO<T> {
	private List<T> data;
	private long totalCount;
}
