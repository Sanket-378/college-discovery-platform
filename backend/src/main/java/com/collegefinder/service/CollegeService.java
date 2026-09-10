package com.collegefinder.service;

import com.collegefinder.entity.College;
import com.collegefinder.entity.Course;
import com.collegefinder.exception.ResourceNotFoundException;
import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.repository.CourseRepository;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CollegeService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Map<String, String> SORT_FIELDS = Map.of(
            "name", "name",
            "rating", "overallRating",
            "establishedYear", "establishedYear");

    private final CollegeRepository collegeRepository;
    private final CourseRepository courseRepository;

    public CollegeService(CollegeRepository collegeRepository, CourseRepository courseRepository) {
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
    }

    public College getCollegeById(Long collegeId) {
        if (collegeId == null) {
            throw new ResourceNotFoundException("College not found: null");
        }
        return collegeRepository.findById(collegeId)
                .orElseThrow(() -> new ResourceNotFoundException("College not found: " + collegeId));
    }

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    public List<Course> getCoursesByCollegeId(Long collegeId) {
        getCollegeById(collegeId);
        return courseRepository.findByCollege_Id(collegeId);
    }

    public Page<College> searchColleges(CollegeSearchCriteria criteria) {
        validateCriteria(criteria);
        Sort.Direction direction = parseDirection(criteria.direction());
        String sortProperty = SORT_FIELDS.get(normalize(criteria.sort()));
        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(direction, sortProperty));
        return collegeRepository.findAll(buildSpecification(criteria), pageable);
    }

    private Specification<College> buildSpecification(CollegeSearchCriteria criteria) {
        return (root, query, builder) -> {
            var predicates = new java.util.ArrayList<Predicate>();
            addTextSearch(predicates, root, builder, criteria.search());
            addEqualIgnoreCase(predicates, root.get("city"), builder, criteria.city());
            addEqualIgnoreCase(predicates, root.get("state"), builder, criteria.state());
            addEqualIgnoreCase(predicates, root.get("collegeType"), builder, criteria.collegeType());

            if (criteria.minRating() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("overallRating"), criteria.minRating()));
            }
            if (criteria.maxRating() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("overallRating"), criteria.maxRating()));
            }

            if (hasText(criteria.course()) || criteria.minFees() != null || criteria.maxFees() != null) {
                var courseSubquery = query.subquery(Long.class);
                var courses = courseSubquery.from(Course.class);
                var coursePredicates = new java.util.ArrayList<Predicate>();
                coursePredicates.add(builder.equal(courses.get("college").get("id"), root.get("id")));
                if (hasText(criteria.course())) {
                    String pattern = likePattern(criteria.course());
                    coursePredicates.add(builder.or(
                            builder.like(builder.lower(courses.get("name")), pattern),
                            builder.like(builder.lower(courses.get("degreeLevel")), pattern),
                            builder.like(builder.lower(courses.get("specialization")), pattern),
                            builder.like(builder.lower(courses.get("description")), pattern),
                            builder.like(builder.lower(courses.get("eligibility")), pattern)));
                }
                if (criteria.minFees() != null) {
                    coursePredicates.add(builder.greaterThanOrEqualTo(courses.get("totalFees"), criteria.minFees()));
                }
                if (criteria.maxFees() != null) {
                    coursePredicates.add(builder.lessThanOrEqualTo(courses.get("totalFees"), criteria.maxFees()));
                }
                courseSubquery.select(builder.literal(1L)).where(coursePredicates.toArray(Predicate[]::new));
                predicates.add(builder.exists(courseSubquery));
            }
            return predicates.isEmpty()
                    ? builder.conjunction()
                    : builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private void addTextSearch(List<Predicate> predicates, jakarta.persistence.criteria.Root<College> root,
                               jakarta.persistence.criteria.CriteriaBuilder builder, String value) {
        if (!hasText(value)) {
            return;
        }
        String pattern = likePattern(value);
        predicates.add(builder.or(
                builder.like(builder.lower(root.get("name")), pattern),
                builder.like(builder.lower(root.get("slug")), pattern),
                builder.like(builder.lower(root.get("description")), pattern),
                builder.like(builder.lower(root.get("city")), pattern),
                builder.like(builder.lower(root.get("state")), pattern),
                builder.like(builder.lower(root.get("country")), pattern),
                builder.like(builder.lower(root.get("collegeType")), pattern),
                builder.like(builder.lower(root.get("ownershipType")), pattern),
                builder.like(builder.lower(root.get("accreditation")), pattern)));
    }

    private void addEqualIgnoreCase(List<Predicate> predicates, jakarta.persistence.criteria.Expression<String> field,
                                    jakarta.persistence.criteria.CriteriaBuilder builder, String value) {
        if (hasText(value)) {
            predicates.add(builder.equal(builder.lower(field), value.trim().toLowerCase(Locale.ROOT)));
        }
    }

    private void validateCriteria(CollegeSearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Search criteria are required");
        }
        if (criteria.page() < 0) {
            throw new IllegalArgumentException("page must be greater than or equal to 0");
        }
        if (criteria.size() < 1 || criteria.size() > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("size must be between 1 and " + MAX_PAGE_SIZE);
        }
        if (criteria.minRating() != null && criteria.maxRating() != null
                && criteria.minRating().compareTo(criteria.maxRating()) > 0) {
            throw new IllegalArgumentException("minRating must not exceed maxRating");
        }
        if (criteria.minFees() != null && criteria.maxFees() != null
                && criteria.minFees().compareTo(criteria.maxFees()) > 0) {
            throw new IllegalArgumentException("minFees must not exceed maxFees");
        }
        if (!SORT_FIELDS.containsKey(normalize(criteria.sort()))) {
            throw new IllegalArgumentException("Unsupported sort field: " + criteria.sort());
        }
        parseDirection(criteria.direction());
    }

    private Sort.Direction parseDirection(String value) {
        if ("desc".equalsIgnoreCase(normalize(value))) {
            return Sort.Direction.DESC;
        }
        if ("asc".equalsIgnoreCase(normalize(value))) {
            return Sort.Direction.ASC;
        }
        throw new IllegalArgumentException("direction must be asc or desc");
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String likePattern(String value) {
        return "%" + value.trim().toLowerCase(Locale.ROOT) + "%";
    }
}
