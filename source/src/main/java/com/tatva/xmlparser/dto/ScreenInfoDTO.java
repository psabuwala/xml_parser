package com.tatva.xmlparser.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "screenInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScreenInfoDTO {
	@XmlAttribute
    private Long width;

    @XmlAttribute
    private Long height;

    @XmlAttribute
    private Long dpi;
}
