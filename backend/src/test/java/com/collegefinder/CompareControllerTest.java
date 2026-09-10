package com.collegefinder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.collegefinder.service.CompareService;
import com.collegefinder.controller.CompareController;
import org.junit.jupiter.api.Test;

class CompareControllerTest {

    private final CompareController controller = new CompareController(mock(CompareService.class));

    @Test
    void rejectsBlankAndNonNumericIds() {
        assertThrows(IllegalArgumentException.class, () -> controller.compare(""));
        assertThrows(IllegalArgumentException.class, () -> controller.compare("one,2"));
    }
}
