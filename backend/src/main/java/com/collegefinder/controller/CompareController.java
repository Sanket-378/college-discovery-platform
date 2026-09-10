package com.collegefinder.controller;

import com.collegefinder.dto.CollegeResponse;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.service.CompareService;
import jakarta.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/colleges")
@ConditionalOnBean(CollegeRepository.class)
@Validated
public class CompareController {

    private final CompareService compareService;

    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @GetMapping("/compare")
    public List<CollegeResponse> compare(@RequestParam @NotBlank String ids) {
        List<Long> collegeIds = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(this::parseCollegeId)
                .toList();
        return compareService.compareColleges(collegeIds).stream().map(CollegeResponse::from).toList();
    }

    private Long parseCollegeId(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("College IDs must not be blank");
        }
        try {
            long id = Long.parseLong(value);
            if (id <= 0) {
                throw new IllegalArgumentException("College IDs must be positive");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("College IDs must be numbers", exception);
        }
    }
}
