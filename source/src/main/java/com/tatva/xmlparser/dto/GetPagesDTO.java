package com.tatva.xmlparser.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "getPages")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPagesDTO {
	
	@XmlAttribute
	private Long editionDefId;

	@XmlAttribute
	private Date publicationDate;
}
