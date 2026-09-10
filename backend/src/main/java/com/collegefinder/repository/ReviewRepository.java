package com.collegefinder.repository;

import com.collegefinder.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCollege_Id(Long collegeId);
}
