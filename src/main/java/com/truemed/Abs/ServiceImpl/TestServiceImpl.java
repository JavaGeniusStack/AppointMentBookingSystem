package com.truemed.Abs.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truemed.Abs.Entity.DiagnosticTest;
import com.truemed.Abs.Repo.DiagnosticTestRepo;
import com.truemed.Abs.Service.TestService;

@Service
public class TestServiceImpl implements TestService {
	@Autowired
	DiagnosticTestRepo diagnosticTestRepo;

	@Override
	public List<DiagnosticTest> viewAvailableTestCenterWise(Long id) {
		return diagnosticTestRepo.viewAvailableTestCenterWise(id);
	}

}
