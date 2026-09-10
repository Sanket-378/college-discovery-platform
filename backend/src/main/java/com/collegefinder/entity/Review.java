package com.collegefinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @Column(name = "reviewer_name", nullable = false, length = 120)
    private String reviewerName;

    @Column(name = "graduation_year")
    private Short graduationYear;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_verified", nullable = false)
    private Boolean verified;

    @Column(name = "reviewed_at", nullable = false)
    private LocalDate reviewedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Review() {
    }

    public Review(College college, String reviewerName, Short graduationYear, BigDecimal rating,
                  String title, String content) {
        this.college = college;
        this.reviewerName = reviewerName;
        this.graduationYear = graduationYear;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verified = false;
        this.reviewedAt = LocalDate.now();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public Long getId() { return id; }
    public College getCollege() { return college; }
    public String getReviewerName() { return reviewerName; }
    public Short getGraduationYear() { return graduationYear; }
    public BigDecimal getRating() { return rating; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Boolean getVerified() { return verified; }
    public LocalDate getReviewedAt() { return reviewedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
