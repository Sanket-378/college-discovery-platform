package com.collegefinder.repository;

import com.collegefinder.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollegeRepository extends JpaRepository<College, Long>, JpaSpecificationExecutor<College> {
}
