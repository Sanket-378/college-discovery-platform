package com.collegefinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.collegefinder.entity.College;
import com.collegefinder.exception.ResourceNotFoundException;
import com.collegefinder.service.CollegeService;
import com.collegefinder.service.CompareService;
import java.util.List;
import org.junit.jupiter.api.Test;

class CompareServiceTest {

    private final CollegeService collegeService = mock(CollegeService.class);
    private final CompareService compareService = new CompareService(collegeService);

    @Test
    void returnsRequestedCollegesInRequestedOrder() {
        College first = mock(College.class);
        College second = mock(College.class);
        when(collegeService.getCollegeById(1L)).thenReturn(first);
        when(collegeService.getCollegeById(2L)).thenReturn(second);

        assertEquals(List.of(first, second), compareService.compareColleges(List.of(1L, 2L)));
    }

    @Test
    void propagatesMissingCollege() {
        when(collegeService.getCollegeById(9L))
                .thenThrow(new ResourceNotFoundException("College not found: 9"));

        assertThrows(ResourceNotFoundException.class, () -> compareService.compareColleges(List.of(9L)));
    }

    @Test
    void rejectsEmptyDuplicateAndOversizedComparisons() {
        assertThrows(IllegalArgumentException.class, () -> compareService.compareColleges(List.of()));
        assertThrows(IllegalArgumentException.class, () -> compareService.compareColleges(List.of(1L, 1L)));
        assertThrows(IllegalArgumentException.class,
                () -> compareService.compareColleges(List.of(1L, 2L, 3L, 4L, 5L)));
    }
}
