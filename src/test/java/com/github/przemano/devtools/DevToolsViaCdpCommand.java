package com.github.przemano.devtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.HashMap;
import java.util.Map;

public class DevToolsViaCdpCommand implements DevToolsHandler{

    ChromiumDriver driver;

    public DevToolsViaCdpCommand(WebDriver driver)
    {
        this.driver = (ChromiumDriver) driver;
    }

    @Override
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {
        Map<String, Object> params = new HashMap<>();
        params.put("width", width);
        params.put("height", height);
        params.put("deviceScaleFactor", scale);
        params.put("mobile", mobile);

        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride",params);
    }
}
