package com.collegefinder.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ReviewRequest(
        @NotBlank @Size(max = 120) String reviewerName,
        @Min(1950) @Max(2100) Short graduationYear,
        @NotNull @DecimalMin("1.0") @DecimalMax("5.0") BigDecimal rating,
        @Size(max = 255) String title,
        @NotBlank String content) {
}
