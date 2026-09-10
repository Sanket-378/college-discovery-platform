CREATE TABLE colleges (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    description TEXT,
    city VARCHAR(120) NOT NULL,
    state VARCHAR(120) NOT NULL,
    country VARCHAR(120) NOT NULL DEFAULT 'India',
    address TEXT,
    website_url VARCHAR(500),
    ownership_type VARCHAR(40) NOT NULL,
    college_type VARCHAR(80),
    established_year SMALLINT,
    accreditation VARCHAR(120),
    nirf_rank INTEGER,
    overall_rating NUMERIC(2,1),
    review_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_colleges_slug UNIQUE (slug),
    CONSTRAINT ck_colleges_established_year CHECK (established_year IS NULL OR established_year BETWEEN 1000 AND 2100),
    CONSTRAINT ck_colleges_nirf_rank CHECK (nirf_rank IS NULL OR nirf_rank > 0),
    CONSTRAINT ck_colleges_overall_rating CHECK (overall_rating IS NULL OR overall_rating BETWEEN 0.0 AND 5.0),
    CONSTRAINT ck_colleges_review_count CHECK (review_count >= 0)
);

CREATE TABLE courses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    college_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    degree_level VARCHAR(50) NOT NULL,
    specialization VARCHAR(255),
    duration_years NUMERIC(3,1) NOT NULL,
    total_fees NUMERIC(14,2),
    currency CHAR(3) NOT NULL DEFAULT 'INR',
    seats INTEGER,
    eligibility TEXT,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_courses_college FOREIGN KEY (college_id) REFERENCES colleges (id) ON DELETE CASCADE,
    CONSTRAINT uq_courses_id_college UNIQUE (id, college_id),
    CONSTRAINT ck_courses_duration CHECK (duration_years > 0),
    CONSTRAINT ck_courses_total_fees CHECK (total_fees IS NULL OR total_fees >= 0),
    CONSTRAINT ck_courses_seats CHECK (seats IS NULL OR seats > 0),
    CONSTRAINT ck_courses_currency CHECK (currency ~ '^[A-Z]{3}$')
);

CREATE TABLE placements (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    college_id BIGINT NOT NULL,
    course_id BIGINT,
    placement_year SMALLINT NOT NULL,
    participating_students INTEGER,
    placed_students INTEGER,
    placement_percentage NUMERIC(5,2),
    average_package NUMERIC(14,2),
    median_package NUMERIC(14,2),
    highest_package NUMERIC(14,2),
    currency CHAR(3) NOT NULL DEFAULT 'INR',
    source_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_placements_college FOREIGN KEY (college_id) REFERENCES colleges (id) ON DELETE CASCADE,
    CONSTRAINT fk_placements_course_college FOREIGN KEY (course_id, college_id) REFERENCES courses (id, college_id) ON DELETE CASCADE,
    CONSTRAINT ck_placements_year CHECK (placement_year BETWEEN 2000 AND 2100),
    CONSTRAINT ck_placements_participating CHECK (participating_students IS NULL OR participating_students >= 0),
    CONSTRAINT ck_placements_placed CHECK (placed_students IS NULL OR placed_students >= 0),
    CONSTRAINT ck_placements_placed_within_participating CHECK (placed_students IS NULL OR participating_students IS NULL OR placed_students <= participating_students),
    CONSTRAINT ck_placements_percentage CHECK (placement_percentage IS NULL OR placement_percentage BETWEEN 0.00 AND 100.00),
    CONSTRAINT ck_placements_average_package CHECK (average_package IS NULL OR average_package >= 0),
    CONSTRAINT ck_placements_median_package CHECK (median_package IS NULL OR median_package >= 0),
    CONSTRAINT ck_placements_highest_package CHECK (highest_package IS NULL OR highest_package >= 0),
    CONSTRAINT ck_placements_currency CHECK (currency ~ '^[A-Z]{3}$')
);

CREATE TABLE cutoffs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    college_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    admission_year SMALLINT NOT NULL,
    exam_name VARCHAR(120) NOT NULL,
    category VARCHAR(80) NOT NULL,
    counselling_round VARCHAR(80) NOT NULL DEFAULT 'GENERAL',
    opening_rank INTEGER,
    closing_rank INTEGER,
    cutoff_score NUMERIC(8,2),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cutoffs_college FOREIGN KEY (college_id) REFERENCES colleges (id) ON DELETE CASCADE,
    CONSTRAINT fk_cutoffs_course_college FOREIGN KEY (course_id, college_id) REFERENCES courses (id, college_id) ON DELETE CASCADE,
    CONSTRAINT uq_cutoffs_year_exam_category_round UNIQUE (course_id, admission_year, exam_name, category, counselling_round),
    CONSTRAINT ck_cutoffs_year CHECK (admission_year BETWEEN 2000 AND 2100),
    CONSTRAINT ck_cutoffs_opening_rank CHECK (opening_rank IS NULL OR opening_rank > 0),
    CONSTRAINT ck_cutoffs_closing_rank CHECK (closing_rank IS NULL OR closing_rank > 0),
    CONSTRAINT ck_cutoffs_rank_order CHECK (opening_rank IS NULL OR closing_rank IS NULL OR opening_rank <= closing_rank),
    CONSTRAINT ck_cutoffs_score CHECK (cutoff_score IS NULL OR cutoff_score >= 0)
);

CREATE TABLE reviews (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    college_id BIGINT NOT NULL,
    reviewer_name VARCHAR(120) NOT NULL,
    graduation_year SMALLINT,
    rating NUMERIC(2,1) NOT NULL,
    title VARCHAR(255),
    content TEXT NOT NULL,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    reviewed_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_college FOREIGN KEY (college_id) REFERENCES colleges (id) ON DELETE CASCADE,
    CONSTRAINT ck_reviews_graduation_year CHECK (graduation_year IS NULL OR graduation_year BETWEEN 1950 AND 2100),
    CONSTRAINT ck_reviews_rating CHECK (rating BETWEEN 1.0 AND 5.0)
);

CREATE INDEX idx_colleges_location ON colleges (state, city);
CREATE INDEX idx_colleges_name ON colleges (name);
CREATE INDEX idx_colleges_rating ON colleges (overall_rating DESC) WHERE overall_rating IS NOT NULL;
CREATE INDEX idx_courses_college ON courses (college_id);
CREATE INDEX idx_courses_degree_level ON courses (degree_level);
CREATE UNIQUE INDEX uq_courses_college_name_degree_specialization ON courses (college_id, name, degree_level, COALESCE(specialization, ''));
CREATE UNIQUE INDEX uq_placements_scope_year ON placements (college_id, COALESCE(course_id, 0), placement_year);
CREATE INDEX idx_placements_college_year ON placements (college_id, placement_year DESC);
CREATE INDEX idx_placements_course_year ON placements (course_id, placement_year DESC) WHERE course_id IS NOT NULL;
CREATE INDEX idx_cutoffs_college_year ON cutoffs (college_id, admission_year DESC);
CREATE INDEX idx_cutoffs_course_year ON cutoffs (course_id, admission_year DESC);
CREATE INDEX idx_reviews_college_reviewed_at ON reviews (college_id, reviewed_at DESC);
