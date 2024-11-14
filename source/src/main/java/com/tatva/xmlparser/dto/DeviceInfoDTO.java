package com.tatva.xmlparser.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "deviceInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceInfoDTO {
	
	@XmlElement
	private ScreenInfoDTO screenInfo;

	@XmlElement
	private OSInfoDTO osInfo;

	@XmlElement
	private AppInfoDTO appInfo;

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String id;
}
