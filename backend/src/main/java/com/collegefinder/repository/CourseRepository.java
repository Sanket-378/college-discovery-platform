package com.collegefinder.repository;

import com.collegefinder.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCollege_Id(Long collegeId);
}
