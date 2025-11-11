package com.github.przemano.tests;

import com.github.przemano.devtools.DevToolsViaSend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTest extends TestBase{
    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);
@Test
    void firsttest()
    {
        var title = driver.getTitle();
        logger.debug(title);
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", title);
    }
}