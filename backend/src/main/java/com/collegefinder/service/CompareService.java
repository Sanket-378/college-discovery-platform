package com.collegefinder.service;

import com.collegefinder.entity.College;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(CollegeService.class)
public class CompareService {

    private final CollegeService collegeService;

    public CompareService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    public List<College> compareColleges(Collection<Long> collegeIds) {
        Objects.requireNonNull(collegeIds, "collegeIds must not be null");
        if (collegeIds.isEmpty()) {
            throw new IllegalArgumentException("At least one college ID is required");
        }

        return collegeIds.stream()
                .map(collegeService::getCollegeById)
                .toList();
    }
}
