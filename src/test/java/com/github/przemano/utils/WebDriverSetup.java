package com.github.przemano.utils;

import com.github.przemano.config.Config;
import com.github.przemano.constants.BrowserName;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

public final class WebDriverSetup {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverSetup.class);

    public WebDriverSetup() {}

    public static WebDriver createWebDriver() throws MalformedURLException {
        String browser = getEnvOrDefault("BROWSER", Config.Browser.Default);//.toLowerCase();
        return createWebDriver(browser);
    }

    //@Step("Creating WebDrivera for browser: {0}")
    public static WebDriver createWebDriver(String browser) throws MalformedURLException {
        //String browser = getEnvOrDefault("BROWSER", "chrome").toLowerCase();
        //String browser = getEnvOrDefault("BROWSER", browsera);//.toLowerCase();
        boolean isRemote = Boolean.parseBoolean(getEnvOrDefault("REMOTE", "false"));
        boolean headless = Boolean.parseBoolean(getEnvOrDefault("HEADLESS", "false"));

        logger.info("[WebDriverSetup] Browser: {}", browser);
        logger.info("[WebDriverSetup] Remote mode: {}", isRemote);
        logger.info("[WebDriverSetup] Headless: {}", headless);

        if (isRemote) {
            return createRemoteWebDriver(browser, headless);
        } else {
            return createLocalWebDriver(browser, headless);
        }
    }

    // ==== LOCAL MODE DRIVER ====
    private static WebDriver createLocalWebDriver(String browser, boolean headless) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver(createChromeOptions(headless));
            case "firefox" -> new FirefoxDriver(createFirefoxOptions(headless));
            case "MicrosoftEdge" -> new EdgeDriver(createEdgeOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    // ==== REMOTE GRID DRIVER ====
    private static WebDriver createRemoteWebDriver(String browser, boolean headless) throws MalformedURLException {
        String gridUrl = Config.URL.SeleniumGrid;
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);

        logger.info("[WebDriverSetup] Connecting to Selenium Grid at {}", gridUrl);
        return new RemoteWebDriver(URI.create(gridUrl).toURL(), caps);
    }


    private static ChromeOptions createChromeOptions(boolean useHeadless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--start-maximized");
        //options.addArguments("--disable-gpu");
        options.addArguments("--use-gl=swiftshader");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        //options.setPageLoadStrategy(PageLoadStrategy.NONE);
        //options.addArguments("--disable-features=Translate,AutomationControlled");
        //options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://localhost:8080");
        options.addArguments("--window-size=1920,1080");

        if (useHeadless) {
            options.addArguments("--headless=new");
            logger.info("Chrome headless mode");
        } else {
            logger.info("Chrome with UI (NoVNC / GUI) ");
        }
        return options;
    }


    private static FirefoxOptions createFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        return options;
    }

    private static EdgeOptions createEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        return options;
    }

    // ==== UTIL ====
    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
