package com.collegefinder.service;

import com.collegefinder.entity.College;
import com.collegefinder.entity.Course;
import com.collegefinder.exception.ResourceNotFoundException;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.repository.CourseRepository;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(CollegeRepository.class)
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final CourseRepository courseRepository;

    public CollegeService(CollegeRepository collegeRepository, CourseRepository courseRepository) {
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
    }

    public College getCollegeById(Long collegeId) {
        if (collegeId == null) {
            throw new ResourceNotFoundException("College not found: null");
        }
        return collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("College not found: " + collegeId));
    }

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    public List<Course> getCoursesByCollegeId(Long collegeId) {
        getCollegeById(collegeId);
        return courseRepository.findByCollege_Id(collegeId);
    }
}
