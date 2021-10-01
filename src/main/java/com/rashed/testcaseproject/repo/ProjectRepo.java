package com.rashed.testcaseproject.repo;

import com.rashed.testcaseproject.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, String> {
}
