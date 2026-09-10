package com.collegefinder.controller;

import com.collegefinder.entity.College;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.service.CompareService;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/colleges")
@ConditionalOnBean(CollegeRepository.class)
public class CompareController {

    private final CompareService compareService;

    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @GetMapping("/compare")
    public List<College> compare(@RequestParam String ids) {
        if (ids == null || ids.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one college ID is required");
        }

        try {
            List<Long> collegeIds = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(this::parseCollegeId)
                    .toList();
            return compareService.compareColleges(collegeIds);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "College IDs must be numbers", exception);
        }
    }

    private Long parseCollegeId(String value) {
        if (value.isBlank()) {
            throw new NumberFormatException("blank college ID");
        }
        return Long.valueOf(value);
    }
}
