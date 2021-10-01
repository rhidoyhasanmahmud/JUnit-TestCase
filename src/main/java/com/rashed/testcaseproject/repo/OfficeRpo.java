package com.rashed.testcaseproject.repo;

import com.rashed.testcaseproject.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRpo extends JpaRepository<Office, String> {
}
