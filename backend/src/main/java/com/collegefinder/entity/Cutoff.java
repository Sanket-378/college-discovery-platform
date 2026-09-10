package com.collegefinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cutoffs")
public class Cutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "college_id", referencedColumnName = "college_id", insertable = false, updatable = false)
    })
    private Course course;

    @Column(name = "admission_year", nullable = false)
    private Short admissionYear;

    @Column(name = "exam_name", nullable = false, length = 120)
    private String examName;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(name = "counselling_round", nullable = false, length = 80)
    private String counsellingRound;

    @Column(name = "opening_rank")
    private Integer openingRank;

    @Column(name = "closing_rank")
    private Integer closingRank;

    @Column(name = "cutoff_score", precision = 8, scale = 2)
    private BigDecimal cutoffScore;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Cutoff() {
    }
}
