package com.rashed.testcaseproject.repo;

import com.rashed.testcaseproject.model.ProjectArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAreaRepo extends JpaRepository<ProjectArea, String> {
}
