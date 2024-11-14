package com.tatva.xmlparser.service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.tatva.xmlparser.dto.EPaperDTO;
import com.tatva.xmlparser.dto.ErrorDTO;
import com.tatva.xmlparser.dto.ResponseListDTO;
import com.tatva.xmlparser.entity.EPaper;
import com.tatva.xmlparser.exception.ParseException;
import com.tatva.xmlparser.exception.ValidationException;
import com.tatva.xmlparser.mapper.IEPaperMapper;
import com.tatva.xmlparser.repo.IEPaperRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EPaperService {

	@Autowired
	private IEPaperRepository epaperRepository;

	private IEPaperMapper epaperMapper = IEPaperMapper.INSTANCE;
	private final List<String> sortOrder = Arrays.asList("ASC", "DESC");

	public EPaperDTO save(MultipartFile file) throws ParseException {
		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			log.info("Create schema from xsd to validate xml");
			URL url = getClass().getClassLoader().getResource("data/xsd/EPaperValidation.xsd");
			Schema schema;
			schema = sf.newSchema(url);
			Validator validator = schema.newValidator();
			log.info("Validate XML with XSD");
			validator.validate(new StreamSource(file.getInputStream()));

			JAXBContext context = JAXBContext.newInstance(EPaperDTO.class);
			Unmarshaller un = context.createUnmarshaller();
			un.setSchema(schema);

			EPaperDTO dto = (EPaperDTO) un.unmarshal(file.getInputStream());
			EPaper epaper = epaperMapper.toEntity(dto);
			epaper.setFileName(file.getOriginalFilename());
			log.info("Saving the XML data");
			epaper = epaperRepository.save(epaper);
			return epaperMapper.toDTO(epaper);
		} catch (SAXException | IOException | JAXBException e) {
			throw new ParseException("Error while parsing, error: " + e.getMessage());
		}

	}

	public ResponseListDTO<EPaperDTO> findAll(String sortColumn, String sortOrder, Integer pageNo, String search)
			throws ValidationException {
		log.info("Fetch All EPaper Data");
		ErrorDTO ePaperDTO = this.isValidFindAllRequest(sortColumn, sortOrder, pageNo);
		if (ePaperDTO.isError()) {
			throw new ValidationException(ePaperDTO.getErrors().toString());
		}
		Sort sorting = sortOrder != null && sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortColumn).descending()
				: Sort.by(sortColumn);
		Pageable paging = PageRequest.of(pageNo != null ? pageNo - 1 : 0, 10, sorting);
		final Page<EPaper> ePaperList = this.epaperRepository.findAll(this.findByString("newsPaperName", search),
				paging);
		final long ePaperCount = this.epaperRepository.count(this.findByString("newsPaperName", search));
		return new ResponseListDTO<EPaperDTO>(epaperMapper.toDTOList(ePaperList.getContent()), ePaperCount);
	}

	private ErrorDTO isValidFindAllRequest(String sortColumn, String sortOrder, Integer pageNo) {
		ErrorDTO ePaperDTO = new ErrorDTO();
		Map<String, String> errors = new HashMap<String, String>();
		if (pageNo != null && pageNo <= 0) {
			ePaperDTO.setError(true);
			errors.put("pageNo", "pageNo should be greater than zero");
		}
		if (sortOrder != null && !this.sortOrder.contains(sortOrder.toUpperCase())) {
			ePaperDTO.setError(true);
			errors.put("sortOrder", "sortOrder can only be ASC or DESC");
		}
		if (sortColumn != null) { 
			boolean isColumnMatch = Arrays.asList(EPaper.class.getDeclaredFields()).stream()
					.anyMatch(field -> field.getName().equals(sortColumn));
			if (!isColumnMatch) {
				ePaperDTO.setError(true);
				errors.put("fieldName", "Invalid sortColumn Name");
			}
		}
		ePaperDTO.setErrors(errors);
		return ePaperDTO;
	}

	public EPaperDTO findById(final Long id) {
		log.info("Fetch EPaper Data based on ID: " + id);
		final EPaper ePaper = this.epaperRepository.findById(id).orElse(null);
		return epaperMapper.toDTO(ePaper);
	}

	private Specification<EPaper> findByString(final String column, final String value) {
		return (root, query, builder) -> builder.like(root.get(column), "%" + value + "%");
	}

}
