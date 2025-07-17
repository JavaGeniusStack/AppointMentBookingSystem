package com.truemed.Abs.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.DiagnosticCenterRequest;
import com.truemed.Abs.Dto.DiagnosticCenterResponseDto;
import com.truemed.Abs.Service.DiagnosticService;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {
	@Autowired
	DiagnosticService diagnosticService;

	@GetMapping("/center")
	public ResponseEntity<List<DiagnosticCenterResponseDto>> getDiagnosticCenters() {
		List<DiagnosticCenterResponseDto> diagnosticServiceCenters = diagnosticService.getAllDiagnosticServiceCenters();
		if (diagnosticServiceCenters.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(diagnosticServiceCenters);
		}

	}

	@PostMapping("/addCenter")
	public ResponseEntity addDiagnosticCenter(@RequestBody DiagnosticCenterRequest diagnosticCenterRequest) {

		return diagnosticService.addDiagnosticCenter(diagnosticCenterRequest);
	}

	@PutMapping("/editCenter")
	public ResponseEntity editDiagnosticCenter(@RequestBody DiagnosticCenterRequest diagnosticCenterRequest) {
//functionality not developed yet
		return null;
	}

	@DeleteMapping("/deleteCenter")
	public ResponseEntity deleteDiagnosticCenter(@RequestParam Long id) {

		return diagnosticService.deleteDiagnosticCenter(id);
	}
}
