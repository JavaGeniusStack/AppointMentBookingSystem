package com.truemed.Abs.ServiceImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truemed.Abs.Dto.DiagnosticCenterRequest;
import com.truemed.Abs.Dto.DiagnosticCenterResponseDto;
import com.truemed.Abs.Entity.DiagnosticCenter;
import com.truemed.Abs.Repo.DiagnosticCenterRepo;
import com.truemed.Abs.Service.DiagnosticService;

@Service
public class DiagnosticServiceImpl implements DiagnosticService {
	@Autowired
	DiagnosticCenterRepo diagnosticCenterRepo;

	@Override
	public List<DiagnosticCenterResponseDto> getAllDiagnosticServiceCenters() {
		List<DiagnosticCenter> diagnosticCenters = diagnosticCenterRepo.findAll();
		return diagnosticCenters.stream().map(DiagnosticCenterResponseDto::fromEntity).collect(Collectors.toList());

	}

	@Override
	public ResponseEntity addDiagnosticCenter(DiagnosticCenterRequest diagnosticCenterRequest) {
		if ("".equals(diagnosticCenterRequest.getCenterName()) || diagnosticCenterRequest.getCenterName() == null) {
			return ResponseEntity.badRequest().body("Please provide Diagnostic center name");
		}
		String name = diagnosticCenterRequest.getCenterName();
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getByCenterName(name);
		System.out.println("CenterData :" + diagnosticCenter);
		if (diagnosticCenter != null) {
			return ResponseEntity.ok("Center Already present");
		} else {
			DiagnosticCenter center = DiagnosticCenter.builder().centerName(diagnosticCenterRequest.getCenterName())
					.contactInfo(diagnosticCenterRequest.getContactInfo())
					.location(diagnosticCenterRequest.getLocation()).build();

			DiagnosticCenter savedCenter = diagnosticCenterRepo.save(center);
			return ResponseEntity.ok(savedCenter.getId());
		}

	}

	@Override
	public ResponseEntity deleteDiagnosticCenter(Long id) {
		diagnosticCenterRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
