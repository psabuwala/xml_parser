package com.tatva.xmlparser.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "appInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppInfoDTO {

	@XmlElement
	private String newspaperName;

	@XmlElement
	private Double version;
}
