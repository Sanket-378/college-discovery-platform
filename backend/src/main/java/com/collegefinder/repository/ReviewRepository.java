package com.collegefinder.repository;

import com.collegefinder.entity.Review;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCollege_Id(Long collegeId);

    @Query("select avg(r.rating) from Review r where r.college.id = :collegeId")
    BigDecimal findAverageRatingByCollegeId(@Param("collegeId") Long collegeId);
}
