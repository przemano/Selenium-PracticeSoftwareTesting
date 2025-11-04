package com.github.przemano.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class SearchTest extends TestBase{

@Test
    void firsttest()
    {
        var title = driver.getTitle();
        System.out.println(title);
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", title);
    }

}
