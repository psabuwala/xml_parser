package com.tatva.xmlparser.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tatva.xmlparser.dto.EPaperDTO;
import com.tatva.xmlparser.entity.EPaper;

@Mapper
public interface IEPaperMapper {

	IEPaperMapper INSTANCE = Mappers.getMapper(IEPaperMapper.class);
	
	@Mapping(source = "deviceInfo.screenInfo.width", target = "width")
	@Mapping(source = "deviceInfo.screenInfo.height", target = "height")
	@Mapping(source = "deviceInfo.screenInfo.dpi", target = "dpi")
	@Mapping(source = "deviceInfo.appInfo.newspaperName", target = "newsPaperName")
	EPaper toEntity(EPaperDTO dto);
	
	@Mapping(target = "deviceInfo.screenInfo.width", source = "width")
	@Mapping(target = "deviceInfo.screenInfo.height", source = "height")
	@Mapping(target = "deviceInfo.screenInfo.dpi", source = "dpi")
	@Mapping(target = "deviceInfo.appInfo.newspaperName", source = "newsPaperName")
	EPaperDTO toDTO(EPaper entity);
	
	List<EPaperDTO> toDTOList(List<EPaper> entities);
}
