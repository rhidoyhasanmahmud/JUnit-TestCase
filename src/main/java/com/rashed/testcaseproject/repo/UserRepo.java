package com.rashed.testcaseproject.repo;

import com.rashed.testcaseproject.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {
}
