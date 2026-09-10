package com.collegefinder.controller;

import com.collegefinder.dto.RatingResponse;
import com.collegefinder.dto.ReviewRequest;
import com.collegefinder.dto.ReviewResponse;
import com.collegefinder.repository.ReviewRepository;
import com.collegefinder.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colleges/{collegeId}")
@ConditionalOnBean(ReviewRepository.class)
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public ReviewResponse createReview(@PathVariable @Positive Long collegeId,
                                       @Valid @RequestBody ReviewRequest request) {
        return reviewService.createReview(collegeId, request);
    }

    @GetMapping("/reviews")
    public List<ReviewResponse> getReviews(@PathVariable @Positive Long collegeId) {
        return reviewService.getReviews(collegeId);
    }

    @GetMapping("/rating")
    public RatingResponse getAverageRating(@PathVariable @Positive Long collegeId) {
        return reviewService.getAverageRating(collegeId);
    }
}
