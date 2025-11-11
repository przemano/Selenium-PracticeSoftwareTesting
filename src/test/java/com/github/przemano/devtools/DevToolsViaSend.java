package com.github.przemano.devtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.devtools.DevTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class DevToolsViaSend implements DevToolsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DevToolsViaSend.class);

    private HasDevTools driver;
    private DevTools devTools;

    public DevToolsViaSend(WebDriver driver) {

        this.driver = (HasDevTools) driver;
        this.devTools = this.driver.getDevTools();
        this.devTools.createSession();
        verifyDevToolsVersion();
    }


    private void verifyDevToolsVersion() {
        String cdpVersion = devTools.getDomains().toString().split("\\.")[4];
        String emulationVersion = Emulation.class.getPackageName().split("\\.")[4];
        logger.info("Detected CDP version: {}", cdpVersion);
        logger.info("DevTools Emulation version: {}", emulationVersion);

        if (!cdpVersion.equalsIgnoreCase(emulationVersion)) {
            throw new IllegalStateException(String.format(
                    "CDP version  (%s) and DevTools Emulation version (%s) do not match.",
                    cdpVersion, emulationVersion
            ));
        }
    }

    @Override
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {
        logger.info("DevTools Emulation device metrics: width: {}, height: {},scale: {}, mobile: {}", width,height, scale, mobile );
        devTools.send(Emulation.setDeviceMetricsOverride(width, height, scale, mobile, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty()));
    }

    @Override
    public void setGeoLocation(double latitude, double longitude, double accuracy) {
        logger.info("DevTools Emulation geolocation: latitude: {}, longitude: {},accuracy: {}", latitude,longitude, accuracy );
        devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude), Optional.of(longitude), Optional.of(accuracy), Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()));
    }
}
