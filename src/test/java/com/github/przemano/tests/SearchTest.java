package com.github.przemano.tests;

import com.github.przemano.devtools.DevToolsViaSend;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Tag("e2e")
//@Tag("regression")
@Tag("gui")
@DisplayName("PractiseSoftwareTesting.com - Search")
public class SearchTest extends TestBase{
    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);

    @Test
    //@Order(1)
    @DisplayName("Search")
    void shouldSearch() {
        var title = driver.getTitle();
        logger.debug(title);
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", title);
    }
}