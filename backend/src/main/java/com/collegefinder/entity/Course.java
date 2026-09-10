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
import org.hibernate.annotations.JdbcTypeCode;
import java.math.BigDecimal;
import java.time.Instant;
import java.sql.Types;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "degree_level", nullable = false, length = 50)
    private String degreeLevel;

    @Column(length = 255)
    private String specialization;

    @Column(name = "duration_years", nullable = false, precision = 3, scale = 1)
    private BigDecimal durationYears;

    @Column(name = "total_fees", precision = 14, scale = 2)
    private BigDecimal totalFees;

    @JdbcTypeCode(Types.CHAR)
    @Column(nullable = false, length = 3)
    private String currency;

    private Integer seats;

    @Column(columnDefinition = "TEXT")
    private String eligibility;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Course() {
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDegreeLevel() { return degreeLevel; }
    public String getSpecialization() { return specialization; }
    public BigDecimal getDurationYears() { return durationYears; }
    public BigDecimal getTotalFees() { return totalFees; }
    public String getCurrency() { return currency; }
    public Integer getSeats() { return seats; }
    public String getEligibility() { return eligibility; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
