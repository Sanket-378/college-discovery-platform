package com.collegefinder.repository;

import com.collegefinder.entity.Cutoff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CutoffRepository extends JpaRepository<Cutoff, Long> {

    List<Cutoff> findByCollege_Id(Long collegeId);

    List<Cutoff> findByCourse_Id(Long courseId);
}
