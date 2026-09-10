package com.collegefinder;

import com.collegefinder.repository.CollegeRepository;
import com.collegefinder.repository.CourseRepository;
import com.collegefinder.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = "spring.autoconfigure.exclude=" +
        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration")
class CollegeFinderApplicationTests {

    @MockitoBean
    CollegeRepository collegeRepository;

    @MockitoBean
    CourseRepository courseRepository;

    @MockitoBean
    ReviewRepository reviewRepository;

    @Test
    void contextLoads() {
    }
}
