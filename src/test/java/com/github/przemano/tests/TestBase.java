package com.github.przemano.tests;

import com.github.przemano.config.Config;
import com.github.przemano.devtools.*;
import com.github.przemano.utils.WebDriverSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.Map;

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static WebDriver driver;
    protected static DevToolsExecutor devTools;

    @BeforeAll
    static void setup() throws MalformedURLException {
        String browser;
        driver = WebDriverSetup.createWebDriver();
        devTools = DevToolsExecutor.createDevTools(driver);

        //devTools.emulationSetDeviceMetrics(Config.Mobile.Width,Config.Mobile.Height, Config.Mobile.Scale, Config.Mobile.IsMobile);

        //devTools.setMobileView();
        devTools.setMyTownLocation();

        driver.get(Config.URL.BaseURL);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

}
