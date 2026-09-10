package com.collegefinder.dto;

import com.collegefinder.entity.Review;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ReviewResponse(
        Long id,
        Long collegeId,
        String reviewerName,
        Short graduationYear,
        BigDecimal rating,
        String title,
        String content,
        Boolean verified,
        LocalDate reviewedAt,
        Instant createdAt,
        Instant updatedAt) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getCollege() == null ? null : review.getCollege().getId(),
                review.getReviewerName(), review.getGraduationYear(), review.getRating(),
                review.getTitle(), review.getContent(), review.getVerified(), review.getReviewedAt(),
                review.getCreatedAt(), review.getUpdatedAt());
    }
}
