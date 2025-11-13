package com.github.przemano.devtools;

import com.github.przemano.config.Config;
import com.github.przemano.constants.BrowserName;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DevToolsExecutor {

    public static final Logger logger = LoggerFactory.getLogger(DevToolsExecutor.class);

    public enum DevToolsMethod {AUTO, SEND, CDP, BIDI}

    void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile);
    void setGeoLocation(double latitude, double longitude, double accuracy);


    default void setMobileView(int width, int height, int scale, boolean mobile) {
        emulationSetDeviceMetrics(width, height, scale, mobile);
    }
    default void setMobileView() {
        emulationSetDeviceMetrics(Config.DevTools.Mobile.Width, Config.DevTools.Mobile.Height, Config.DevTools.Mobile.Scale, Config.DevTools.Mobile.IsMobile);
    }
    default void setMyTownLocation() {
        setGeoLocation(Config.DevTools.Location.Latitude, Config.DevTools.Location.Longitude, Config.DevTools.Location.Accuracy);
    }

    public static DevToolsExecutor createDevTools(WebDriver driver,  DevToolsMethod method) {
        return switch (method)
        {
            case CDP -> new DevToolsViaCdpCommand(driver);
            case SEND -> new DevToolsViaSend(driver);
            case BIDI -> new DevToolsViaBiDi(driver);
            default -> createDevTools(driver);
        };
    }

    public static DevToolsExecutor createDevTools(WebDriver driver) {
        if (!(driver instanceof HasCapabilities hasCaps)) {
            throw new IllegalArgumentException("Driver not implement HasCapabilities.");
        }

        String browserName = hasCaps.getCapabilities().getBrowserName();
        logger.info("Running tests on browser: {}", browserName);


        return switch (browserName) {
            case BrowserName.FIREFOX -> new DevToolsViaBiDi(driver);
            case BrowserName.CHROME, BrowserName.EDGE, BrowserName.OPERA -> {
                var hasDevTools = (HasDevTools) driver;
                var devTools = hasDevTools.getDevTools();
                devTools.createSession();
                if (isDevToolsVersionUpToDate(devTools)) {
                    yield new DevToolsViaSend(driver);
                } else {
                    yield new DevToolsViaCdpCommand(driver);
                }
            }
            default -> throw new UnsupportedOperationException(
                    "Browser '" + browserName + "' not supported."
            );
        };
    }


    static boolean isDevToolsVersionUpToDate(DevTools devTools) {
        String cdpVersion = devTools.getDomains().toString().split("\\.")[4];
        String emulationVersion = Emulation.class.getPackageName().split("\\.")[4];
        boolean isUpToDate = cdpVersion.equalsIgnoreCase(emulationVersion);
        if(isUpToDate)
        {
            logger.info("DevTools version {} compatible. ", cdpVersion);
        }
        else {
            logger.warn("DevTools version: {} not compatible with DevTools library version: {}", cdpVersion, emulationVersion );
        }

        return isUpToDate;
    }
}
