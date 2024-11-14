package com.tatva.xmlparser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.tatva.xmlparser.dto.EPaperDTO;
import com.tatva.xmlparser.dto.ResponseListDTO;
import com.tatva.xmlparser.exception.ValidationException;
import com.tatva.xmlparser.service.EPaperService;

@WebMvcTest(EPaperController.class)
public class EPaperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EPaperService epaperService;

    private EPaperDTO mockEPaperDTO;

    @BeforeEach
    public void setup() {
        mockEPaperDTO = new EPaperDTO();
        mockEPaperDTO.setId(1L);
    }

    @Test
    public void testSaveEPaper() throws Exception {
        MockMultipartFile file = new MockMultipartFile("xmlFile", "test.xml", MediaType.TEXT_XML_VALUE, "<xml>content</xml>".getBytes());
        
        when(epaperService.save(any())).thenReturn(mockEPaperDTO);

        mockMvc.perform(multipart("/epaper")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetAllEPapers() throws Exception {
        List<EPaperDTO> epaperList = Collections.singletonList(mockEPaperDTO);
        ResponseListDTO<EPaperDTO> response = new ResponseListDTO<EPaperDTO>(epaperList, 1);
        when(epaperService.findAll(any(), any(), any(), any())).thenReturn(response);

        mockMvc.perform(get("/epaper")
                .param("sortColumn", "title")
                .param("sortOrder", "ASC")
                .param("pageNo", "1")
                .param("search", "Sample"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.totalCount").value(1));
    }

    @Test
    public void testGetEPaperById() throws Exception {
        when(epaperService.findById(1L)).thenReturn(mockEPaperDTO);

        mockMvc.perform(get("/epaper/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetEPaperByIdBadRequest() throws Exception {
        mockMvc.perform(get("/epaper/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllEPapersWithValidationException() throws Exception {
        when(epaperService.findAll(any(), any(), any(), any())).thenThrow(new ValidationException("Invalid parameters"));

        mockMvc.perform(get("/epaper")
                .param("sortColumn", "invalidColumn"))
                .andExpect(status().isBadRequest());
    }
}
