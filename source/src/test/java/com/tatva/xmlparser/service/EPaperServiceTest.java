package com.tatva.xmlparser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.tatva.xmlparser.dto.EPaperDTO;
import com.tatva.xmlparser.dto.ResponseListDTO;
import com.tatva.xmlparser.entity.EPaper;
import com.tatva.xmlparser.exception.ParseException;
import com.tatva.xmlparser.exception.ValidationException;
import com.tatva.xmlparser.mapper.IEPaperMapper;
import com.tatva.xmlparser.repo.IEPaperRepository;

@ExtendWith(MockitoExtension.class)
class EPaperServiceTest {

    @Mock
    private IEPaperRepository epaperRepository;

    @Mock
    private IEPaperMapper epaperMapper;

    @InjectMocks
    private EPaperService epaperService;

    private MultipartFile file;
    private EPaperDTO epaperDTO;
    private EPaper epaper;

    @BeforeEach
    void setUp() {
    	file = mock(MultipartFile.class);
        epaperDTO = new EPaperDTO();
        epaper = new EPaper();
    }

    @Test
    void testSave_Success() throws Exception {
    	String data= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
    			+ "<!-- Created with Liquid Technologies Online Tools 1.0 (https://www.liquid-technologies.com) -->\r\n"
    			+ "<epaperRequest xsi:noNamespaceSchemaLocation=\"schema.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
    			+ "  <deviceInfo name=\"string\" id=\"string\">\r\n"
    			+ "    <screenInfo width=\"4680\" height=\"832\" dpi=\"153\" />\r\n"
    			+ "    <osInfo name=\"string\" version=\"143250.234905804\" />\r\n"
    			+ "    <appInfo>\r\n"
    			+ "      <newspaperName>ASD</newspaperName>\r\n"
    			+ "      <version>3400250.2349058</version>\r\n"
    			+ "    </appInfo>\r\n"
    			+ "  </deviceInfo>\r\n"
    			+ "  <getPages editionDefId=\"244\" publicationDate=\"1975-02-04\" />\r\n"
    			+ "</epaperRequest>";
    	MockMultipartFile xmlFile = new MockMultipartFile("xmlFile", "test.xml", "application/xml", data.getBytes());

        when(epaperMapper.toEntity(any(EPaperDTO.class))).thenReturn(epaper);
        when(epaperMapper.toDTO(any(EPaper.class))).thenReturn(epaperDTO);
        when(epaperRepository.save(any(EPaper.class))).thenReturn(epaper);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = getClass().getClassLoader().getResource("data/xsd/EPaperValidation.xsd");
        sf.newSchema(url); // Mock schema validation

        EPaperDTO result = epaperService.save(xmlFile);

        assertNotNull(result);
        verify(epaperRepository, times(1)).save(any(EPaper.class));
    }

    @Test
    void testSave_ParseException() throws Exception {
        when(file.getInputStream()).thenThrow(IOException.class);

        assertThrows(ParseException.class, () -> epaperService.save(file));
    }

    @Test
    void testFindAll_Success() throws ValidationException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EPaper> page = new PageImpl<>(Collections.singletonList(epaper));

        when(epaperRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(epaperMapper.toDTOList(anyList())).thenReturn(Collections.singletonList(epaperDTO));

        ResponseListDTO<EPaperDTO> result = epaperService.findAll("id", "ASC", 1, "");

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        verify(epaperRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testFindById_Success() {
        when(epaperRepository.findById(1L)).thenReturn(Optional.of(epaper));
        when(epaperMapper.toDTO(epaper)).thenReturn(epaperDTO);

        EPaperDTO result = epaperService.findById(1L);

        assertNotNull(result);
        verify(epaperRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(epaperRepository.findById(1L)).thenReturn(Optional.empty());

        EPaperDTO result = epaperService.findById(1L);

        assertNull(result);
        verify(epaperRepository, times(1)).findById(1L);
    }
}
