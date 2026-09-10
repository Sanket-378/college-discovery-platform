package com.collegefinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "colleges")
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 120)
    private String city;

    @Column(nullable = false, length = 120)
    private String state;

    @Column(nullable = false, length = 120)
    private String country;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "website_url", length = 500)
    private String websiteUrl;

    @Column(name = "ownership_type", nullable = false, length = 40)
    private String ownershipType;

    @Column(name = "college_type", length = 80)
    private String collegeType;

    @Column(name = "established_year")
    private Short establishedYear;

    @Column(length = 120)
    private String accreditation;

    @Column(name = "nirf_rank")
    private Integer nirfRank;

    @Column(name = "overall_rating", precision = 2, scale = 1)
    private BigDecimal overallRating;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected College() {
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getDescription() { return description; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getAddress() { return address; }
    public String getWebsiteUrl() { return websiteUrl; }
    public String getOwnershipType() { return ownershipType; }
    public String getCollegeType() { return collegeType; }
    public Short getEstablishedYear() { return establishedYear; }
    public String getAccreditation() { return accreditation; }
    public Integer getNirfRank() { return nirfRank; }
    public BigDecimal getOverallRating() { return overallRating; }
    public Integer getReviewCount() { return reviewCount; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
