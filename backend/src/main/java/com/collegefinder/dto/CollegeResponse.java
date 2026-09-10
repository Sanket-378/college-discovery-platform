package com.collegefinder.dto;

import com.collegefinder.entity.College;
import java.math.BigDecimal;
import java.time.Instant;

public record CollegeResponse(
        Long id,
        String name,
        String slug,
        String description,
        String city,
        String state,
        String country,
        String address,
        String websiteUrl,
        String ownershipType,
        String collegeType,
        Short establishedYear,
        String accreditation,
        Integer nirfRank,
        BigDecimal overallRating,
        Integer reviewCount,
        Instant createdAt,
        Instant updatedAt) {

    public static CollegeResponse from(College college) {
        return new CollegeResponse(
                college.getId(), college.getName(), college.getSlug(), college.getDescription(),
                college.getCity(), college.getState(), college.getCountry(), college.getAddress(),
                college.getWebsiteUrl(), college.getOwnershipType(), college.getCollegeType(),
                college.getEstablishedYear(), college.getAccreditation(), college.getNirfRank(),
                college.getOverallRating(), college.getReviewCount(), college.getCreatedAt(), college.getUpdatedAt());
    }
}
