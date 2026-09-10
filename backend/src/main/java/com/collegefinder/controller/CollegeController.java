package com.collegefinder.controller;

import com.collegefinder.entity.College;
import com.collegefinder.entity.Course;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.service.CollegeService;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colleges")
@ConditionalOnBean(CollegeRepository.class)
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping
    public List<College> getAllColleges() {
        return collegeService.getAllColleges();
    }

    @GetMapping("/{id}")
    public College getCollege(@PathVariable Long id) {
        return collegeService.getCollegeById(id);
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCourses(@PathVariable Long id) {
        return collegeService.getCoursesByCollegeId(id);
    }
}
