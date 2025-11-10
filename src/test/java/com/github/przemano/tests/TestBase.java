package com.github.przemano.tests;

import com.github.przemano.config.Config;
import com.github.przemano.devtools.*;
import com.github.przemano.utils.WebDriverSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TestBase {

    protected static WebDriver driver;
    protected static DevToolsExecutor devTools;

    @BeforeAll
    static void setup() throws MalformedURLException {
        driver = WebDriverSetup.createWebDriver();
        devTools = DevToolsExecutor.createDevTools(driver);

        devTools.emulationSetDeviceMetrics(600,1000, 50, true);

        driver.get(Config.URL.BaseURL);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    List<String> test = new ArrayList<>();
}
