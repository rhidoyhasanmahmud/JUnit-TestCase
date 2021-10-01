package com.rashed.testcaseproject.repo;

import com.rashed.testcaseproject.model.ProjectFinance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectFinanceRpo extends JpaRepository<ProjectFinance, String> {
}
