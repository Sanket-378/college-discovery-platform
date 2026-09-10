package com.collegefinder.dto;

import java.math.BigDecimal;

public record RatingResponse(Long collegeId, BigDecimal averageRating) {
}
