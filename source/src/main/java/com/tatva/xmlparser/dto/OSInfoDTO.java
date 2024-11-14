package com.tatva.xmlparser.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "osInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class OSInfoDTO {
	
	@XmlAttribute
	private String name;

	@XmlAttribute
	private Double version;
}
