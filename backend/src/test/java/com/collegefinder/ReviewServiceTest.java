package com.collegefinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collegefinder.dto.ReviewRequest;
import com.collegefinder.dto.ReviewResponse;
import com.collegefinder.entity.College;
import com.collegefinder.entity.Review;
import com.collegefinder.exception.ResourceNotFoundException;
import com.collegefinder.repository.ReviewRepository;
import com.collegefinder.service.CollegeService;
import com.collegefinder.service.ReviewService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReviewServiceTest {

    private final CollegeService collegeService = mock(CollegeService.class);
    private final ReviewRepository reviewRepository = mock(ReviewRepository.class);
    private final ReviewService reviewService = new ReviewService(collegeService, reviewRepository);

    @Test
    void createsValidReviewForExistingCollege() {
        College college = mock(College.class);
        Review savedReview = mock(Review.class);
        when(collegeService.getCollegeById(7L)).thenReturn(college);
        when(reviewRepository.save(org.mockito.ArgumentMatchers.any(Review.class))).thenReturn(savedReview);

        ReviewResponse response = reviewService.createReview(7L,
                new ReviewRequest("Reviewer", null, BigDecimal.valueOf(4.5), "Great", "Very good college"));

        assertNotNull(response);
        verify(reviewRepository).save(org.mockito.ArgumentMatchers.any(Review.class));
    }

    @Test
    void rejectsRatingsOutsideSchemaRange() {
        ReviewRequest below = new ReviewRequest("Reviewer", null, BigDecimal.ZERO, null, "Text");
        ReviewRequest above = new ReviewRequest("Reviewer", null, BigDecimal.valueOf(5.1), null, "Text");

        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(7L, below));
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(7L, above));
    }

    @Test
    void missingCollegeIsPropagated() {
        when(collegeService.getCollegeById(99L))
                .thenThrow(new ResourceNotFoundException("College not found: 99"));

        assertThrows(ResourceNotFoundException.class, () -> reviewService.createReview(99L,
                new ReviewRequest("Reviewer", null, BigDecimal.valueOf(4), null, "Text")));
    }

    @Test
    void retrievesReviewsForCollege() {
        College college = mock(College.class);
        when(collegeService.getCollegeById(7L)).thenReturn(college);
        when(reviewRepository.findByCollege_Id(7L)).thenReturn(List.of());

        assertEquals(List.of(), reviewService.getReviews(7L));
        verify(reviewRepository).findByCollege_Id(7L);
    }

    @Test
    void retrievesDatabaseAverageRating() {
        College college = mock(College.class);
        when(collegeService.getCollegeById(7L)).thenReturn(college);
        when(reviewRepository.findAverageRatingByCollegeId(7L)).thenReturn(BigDecimal.valueOf(4.25));

        assertEquals(BigDecimal.valueOf(4.25), reviewService.getAverageRating(7L).averageRating());
    }
}
