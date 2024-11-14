package com.tatva.xmlparser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tatva.xmlparser.dto.EPaperDTO;
import com.tatva.xmlparser.dto.ResponseListDTO;
import com.tatva.xmlparser.exception.ParseException;
import com.tatva.xmlparser.exception.ValidationException;
import com.tatva.xmlparser.service.EPaperService;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("epaper")
@RequiredArgsConstructor
public class EPaperController {

	private final EPaperService epaperService;

	@PostMapping
	public ResponseEntity<EPaperDTO> save(@RequestParam("xmlFile") MultipartFile file) throws ParseException {
		EPaperDTO ePaperDTO = epaperService.save(file);
		return ResponseEntity.ok(ePaperDTO);
	}

	@GetMapping
	public ResponseEntity<ResponseListDTO<EPaperDTO>> getAllEPapers(
			@RequestParam(required = false, defaultValue = "newsPaperName") String sortColumn,
			@RequestParam(required = false, defaultValue = "ASC") String sortOrder,
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false, defaultValue = "") String search) throws ValidationException {
		return ResponseEntity.ok(epaperService.findAll(sortColumn, sortOrder, pageNo, search));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EPaperDTO> getEPapersById(@PathVariable @NonNull Long id) {
		EPaperDTO epaper = epaperService.findById(id);
		return ResponseEntity.ok(epaper);
	}
}
