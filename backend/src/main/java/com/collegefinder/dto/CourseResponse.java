package com.collegefinder.dto;

import com.collegefinder.entity.Course;
import java.math.BigDecimal;
import java.time.Instant;

public record CourseResponse(
        Long id,
        String name,
        String degreeLevel,
        String specialization,
        BigDecimal durationYears,
        BigDecimal totalFees,
        String currency,
        Integer seats,
        String eligibility,
        String description,
        Instant createdAt,
        Instant updatedAt) {

    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(), course.getName(), course.getDegreeLevel(), course.getSpecialization(),
                course.getDurationYears(), course.getTotalFees(), course.getCurrency(), course.getSeats(),
                course.getEligibility(), course.getDescription(), course.getCreatedAt(), course.getUpdatedAt());
    }
}
