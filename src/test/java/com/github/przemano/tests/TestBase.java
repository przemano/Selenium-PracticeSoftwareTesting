package com.github.przemano.tests;

import com.github.przemano.config.Config;
import com.github.przemano.utils.WebDriverSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class TestBase {

    protected static WebDriver driver;

    @BeforeAll
    static void setup() throws MalformedURLException {
        driver = WebDriverSetup.createWebDriver();
        driver.get(Config.URL.BaseURL);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
