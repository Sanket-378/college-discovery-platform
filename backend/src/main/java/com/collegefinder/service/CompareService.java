package com.collegefinder.service;

import com.collegefinder.entity.College;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

    public static final int MAX_COLLEGES_PER_COMPARISON = 4;

    private final CollegeService collegeService;

    public CompareService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    public List<College> compareColleges(Collection<Long> collegeIds) {
        if (collegeIds == null || collegeIds.isEmpty()) {
            throw new IllegalArgumentException("At least one college ID is required");
        }
        if (collegeIds.size() > MAX_COLLEGES_PER_COMPARISON) {
            throw new IllegalArgumentException("A comparison may contain at most "
                    + MAX_COLLEGES_PER_COMPARISON + " colleges");
        }
        if (collegeIds.stream().anyMatch(id -> id == null || id <= 0)) {
            throw new IllegalArgumentException("College IDs must be positive");
        }
        Set<Long> uniqueIds = new HashSet<>(collegeIds);
        if (uniqueIds.size() != collegeIds.size()) {
            throw new IllegalArgumentException("College IDs must be unique");
        }

        return collegeIds.stream()
                .map(collegeService::getCollegeById)
                .toList();
    }
}
