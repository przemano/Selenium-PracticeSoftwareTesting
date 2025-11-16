package com.github.przemano.tests;

import com.github.przemano.constants.BrowserView;
import com.github.przemano.pages.HomePage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;


@Tag("regression")
@Tag("gui")
@DisplayName("Search functionality in PractiseSoftwareTesting.com")
public class SearchTest extends TestBase{
    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);

    @ParameterizedTest(name = "Query {0} and expect count {1}")
    @MethodSource("searchData")
    @DisplayName("Verification of search and number of results")
    void shouldSearch(String query, int expectedCount) {

        search(query, BrowserView.DESKTOP , expectedCount);
    }

    @ParameterizedTest(name = "Query {0} and expect count {1}")
    @MethodSource("searchData")
    @DisplayName("Verification of search in mobile view and number of results")
    void shouldSearchMobile(String query, int expectedCount) {
        devTools.setMobileView();
        search(query, BrowserView.MOBILE, expectedCount);
    }

    public static Stream<Arguments> searchData() {
        return Stream.of(
                Arguments.of("hammer", 7),
                Arguments.of("Pliers", 4),
                Arguments.of("No exists", 0)

        );
    }

    void search(String query, BrowserView browserView, int expectedCount) {
        var title = driver.getTitle();
        logger.debug(title);
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", title);

        logger.info("Query {} and expect count {}", query, expectedCount);

        HomePage home = new HomePage(driver);


        if(browserView == BrowserView.MOBILE) {home.expandMobileFilters();}

        int productCount = home
            .search(query)
            .takeCountProducts();

        Assertions.assertEquals(expectedCount, productCount);
    }


}