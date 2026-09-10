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
import org.hibernate.annotations.JdbcTypeCode;
import java.math.BigDecimal;
import java.time.Instant;
import java.sql.Types;

@Entity
@Table(name = "placements")
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "college_id", referencedColumnName = "college_id", insertable = false, updatable = false)
    })
    private Course course;

    @Column(name = "placement_year", nullable = false)
    private Short placementYear;

    @Column(name = "participating_students")
    private Integer participatingStudents;

    @Column(name = "placed_students")
    private Integer placedStudents;

    @Column(name = "placement_percentage", precision = 5, scale = 2)
    private BigDecimal placementPercentage;

    @Column(name = "average_package", precision = 14, scale = 2)
    private BigDecimal averagePackage;

    @Column(name = "median_package", precision = 14, scale = 2)
    private BigDecimal medianPackage;

    @Column(name = "highest_package", precision = 14, scale = 2)
    private BigDecimal highestPackage;

    @JdbcTypeCode(Types.CHAR)
    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "source_url", length = 500)
    private String sourceUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Placement() {
    }
}
