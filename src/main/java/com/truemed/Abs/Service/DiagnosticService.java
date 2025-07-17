package com.truemed.Abs.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truemed.Abs.Dto.DiagnosticCenterRequest;
import com.truemed.Abs.Dto.DiagnosticCenterResponseDto;
import com.truemed.Abs.Entity.DiagnosticCenter;

@Service
public interface DiagnosticService {

	List<DiagnosticCenterResponseDto> getAllDiagnosticServiceCenters();

	ResponseEntity addDiagnosticCenter(DiagnosticCenterRequest diagnosticCenterRequest);

	ResponseEntity deleteDiagnosticCenter(Long id);

}
