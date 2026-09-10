package com.collegefinder.service;

import com.collegefinder.dto.RatingResponse;
import com.collegefinder.dto.ReviewRequest;
import com.collegefinder.dto.ReviewResponse;
import com.collegefinder.entity.College;
import com.collegefinder.entity.Review;
import com.collegefinder.repository.ReviewRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(ReviewRepository.class)
public class ReviewService {

    private final CollegeService collegeService;
    private final ReviewRepository reviewRepository;

    public ReviewService(CollegeService collegeService, ReviewRepository reviewRepository) {
        this.collegeService = collegeService;
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse createReview(Long collegeId, ReviewRequest request) {
        validateRequest(request);
        College college = collegeService.getCollegeById(collegeId);
        Review review = new Review(college, request.reviewerName().trim(), request.graduationYear(),
                request.rating(), request.title(), request.content().trim());
        return ReviewResponse.from(reviewRepository.save(review));
    }

    public List<ReviewResponse> getReviews(Long collegeId) {
        collegeService.getCollegeById(collegeId);
        return reviewRepository.findByCollege_Id(collegeId).stream().map(ReviewResponse::from).toList();
    }

    public RatingResponse getAverageRating(Long collegeId) {
        collegeService.getCollegeById(collegeId);
        return new RatingResponse(collegeId, reviewRepository.findAverageRatingByCollegeId(collegeId));
    }

    private void validateRequest(ReviewRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Review request is required");
        }
        if (request.rating() == null || request.rating().compareTo(BigDecimal.ONE) < 0
                || request.rating().compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }
        if (request.reviewerName() == null || request.reviewerName().isBlank()) {
            throw new IllegalArgumentException("reviewerName must not be blank");
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new IllegalArgumentException("content must not be blank");
        }
    }
}
