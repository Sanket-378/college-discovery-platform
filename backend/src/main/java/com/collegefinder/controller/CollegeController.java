package com.collegefinder.controller;

import com.collegefinder.dto.CollegeResponse;
import com.collegefinder.dto.CourseResponse;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.service.CollegeService;
import com.collegefinder.service.CollegeSearchCriteria;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<CollegeResponse> getAllColleges(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String collegeType,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal minRating,
            @RequestParam(required = false) @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal maxRating,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal minFees,
            @RequestParam(required = false) @DecimalMin("0.0") BigDecimal maxFees,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        CollegeSearchCriteria criteria = new CollegeSearchCriteria(
                search, city, state, collegeType, course, minRating, maxRating,
                minFees, maxFees, page, size, sort, direction);
        return collegeService.searchColleges(criteria).map(CollegeResponse::from);
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
