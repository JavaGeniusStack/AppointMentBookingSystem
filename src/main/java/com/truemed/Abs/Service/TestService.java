package com.truemed.Abs.Service;

import java.util.List;

import com.truemed.Abs.Entity.DiagnosticTest;

public interface TestService {

	public List<DiagnosticTest> viewAvailableTestCenterWise(Long id);
}
