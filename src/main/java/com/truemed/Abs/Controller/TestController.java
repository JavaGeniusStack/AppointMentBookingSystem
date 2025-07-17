package com.truemed.Abs.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.ViewCenterWiseTestRequest;
import com.truemed.Abs.Entity.DiagnosticTest;
import com.truemed.Abs.Service.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	TestService testService;

	@GetMapping("/viewTests")
	public ResponseEntity viewAvailableTestCenterWise(
			@RequestBody ViewCenterWiseTestRequest ViewCenterWiseTestRequest) {

		List<DiagnosticTest> availableTestAtCenter = testService
				.viewAvailableTestCenterWise(ViewCenterWiseTestRequest.getDiagnosticCenterId());
		if (availableTestAtCenter.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(availableTestAtCenter);
	}

}
