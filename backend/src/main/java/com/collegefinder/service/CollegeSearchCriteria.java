package com.collegefinder.service;

import java.math.BigDecimal;

public record CollegeSearchCriteria(
        String search,
        String city,
        String state,
        String collegeType,
        String course,
        BigDecimal minRating,
        BigDecimal maxRating,
        BigDecimal minFees,
        BigDecimal maxFees,
        int page,
        int size,
        String sort,
        String direction) {
}
