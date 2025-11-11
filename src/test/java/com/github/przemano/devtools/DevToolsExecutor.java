package com.github.przemano.devtools;

import com.github.przemano.config.Config;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;

public interface DevToolsExecutor {

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
        System.out.println("Running tests on browser: " + browserName);

        return switch (browserName) {
            case "firefox" -> new DevToolsViaBiDi(driver);
            case "chrome", "MicrosoftEdge", "opera" -> {
                var hasDevTools = (HasDevTools) driver;
                var devTools = hasDevTools.getDevTools();
                devTools.createSession();
                String cdpVersion = devTools.getDomains().toString().split("\\.")[4];
                String emulationVersion = Emulation.class.getPackageName().split("\\.")[4];
                System.out.println("Detected CDP version: " + cdpVersion);
                System.out.println("DevTools Emulation version: " + emulationVersion);

                if (!cdpVersion.equalsIgnoreCase(emulationVersion)) {
                    yield new DevToolsViaCdpCommand(driver);
                } else {
                    yield new DevToolsViaSend(driver);
                }
            }
            default -> throw new UnsupportedOperationException(
                    "Browser '" + browserName + "' not supported."
            );
        };
    }
}
