package com.github.przemano.devtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DevToolsViaCdpCommand implements DevToolsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DevToolsViaCdpCommand.class);

    ChromiumDriver driver;

    public DevToolsViaCdpCommand(WebDriver driver)
    {
        this.driver = (ChromiumDriver) driver;
    }

    @Override
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {
        logger.info("DevTools Emulation device metrics: width: {}, height: {},scale: {}, mobile: {}", width,height, scale, mobile );

        Map<String, Object> params = new HashMap<>();
        params.put("width", width);
        params.put("height", height);
        params.put("deviceScaleFactor", scale);
        params.put("mobile", mobile);

        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride",params);
    }

    @Override
    public void setGeoLocation(double latitude, double longitude, double accuracy) {
        logger.info("DevTools Emulation geolocation: latitude: {}, longitude: {},accuracy: {}", latitude,longitude, accuracy );
        Map <String, Object> coordinates = Map.of("latitude", latitude, "longitude", longitude, "accuracy", accuracy );
        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }
}
