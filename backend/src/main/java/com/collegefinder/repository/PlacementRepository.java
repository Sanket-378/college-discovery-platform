package com.collegefinder.repository;

import com.collegefinder.entity.Placement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository extends JpaRepository<Placement, Long> {

    List<Placement> findByCollege_Id(Long collegeId);

    List<Placement> findByCourse_Id(Long courseId);
}
