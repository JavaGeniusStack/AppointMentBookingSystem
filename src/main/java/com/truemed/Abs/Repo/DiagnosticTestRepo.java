package com.truemed.Abs.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.truemed.Abs.Entity.DiagnosticTest;

@Repository
public interface DiagnosticTestRepo extends JpaRepository<DiagnosticTest, Long> {

	@Query(value = "SELECT FROM DiagnosticTest where center.id = ?1",nativeQuery = true)
	List<DiagnosticTest> viewAvailableTestCenterWise(Long id);

}
