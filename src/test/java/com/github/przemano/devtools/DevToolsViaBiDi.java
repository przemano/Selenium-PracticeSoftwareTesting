package com.github.przemano.devtools;

import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.HasBiDi;

public class DevToolsViaBiDi implements DevToolsExecutor {

    WebDriver driver;
    HasBiDi bidiDriver;

    public DevToolsViaBiDi(WebDriver driver)
    {
        this.driver = driver;
        this.bidiDriver = (HasBiDi) driver;
    }

    @Override
    @Disabled
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {
        driver.manage().window().setSize(new Dimension(width, height)); //TODO BiDi does not support device emulation yet
    }
}
