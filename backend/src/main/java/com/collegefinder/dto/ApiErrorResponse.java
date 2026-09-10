package com.collegefinder.dto;

import java.time.Instant;

public record ApiErrorResponse(int status, String message, Instant timestamp) {
}
