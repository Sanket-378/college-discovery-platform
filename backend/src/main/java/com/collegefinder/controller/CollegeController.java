package com.collegefinder.controller;

import com.collegefinder.dto.CollegeResponse;
import com.collegefinder.dto.CourseResponse;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.service.CollegeService;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colleges")
@ConditionalOnBean(CollegeRepository.class)
@Validated
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping
    public List<CollegeResponse> getAllColleges() {
        return collegeService.getAllColleges().stream().map(CollegeResponse::from).toList();
    }

    @GetMapping("/{id}")
    public CollegeResponse getCollege(@PathVariable @Positive Long id) {
        return CollegeResponse.from(collegeService.getCollegeById(id));
    }

    @GetMapping("/{id}/courses")
    public List<CourseResponse> getCourses(@PathVariable @Positive Long id) {
        return collegeService.getCoursesByCollegeId(id).stream().map(CourseResponse::from).toList();
    }
}
