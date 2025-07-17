package com.truemed.Abs.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truemed.Abs.Entity.DiagnosticCenter;

public interface DiagnosticCenterRepo extends JpaRepository<DiagnosticCenter, Long> {
	public DiagnosticCenter getByCenterName(String centerName);

}
