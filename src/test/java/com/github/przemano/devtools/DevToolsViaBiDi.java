package com.github.przemano.devtools;

import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.emulation.Emulation;
import org.openqa.selenium.bidi.emulation.GeolocationCoordinates;
import org.openqa.selenium.bidi.emulation.SetGeolocationOverrideParameters;

import java.util.Map;

public class DevToolsViaBiDi implements DevToolsExecutor {

    WebDriver driver;
    HasBiDi bidiDriver;
    //BiDi bidi;

    public DevToolsViaBiDi(WebDriver driver)
    {
        this.driver = driver;
        this.bidiDriver = (HasBiDi) driver;
        //this.bidi = ((HasBiDi) driver).getBiDi();
    }

    @Override
    @Disabled
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {
        driver.manage().window().setSize(new Dimension(width, height)); //TODO BiDi does not support device emulation yet
    }

    @Override
    public void setGeoLocation(double latitude, double longitude, double accuracy) {
        Map <String, Object> coordinates = Map.of("latitude", latitude, "longitude", longitude, "accuracy", accuracy );
        Emulation emulation = new Emulation(driver);

        SetGeolocationOverrideParameters geoParams =
                new SetGeolocationOverrideParameters(
                        new GeolocationCoordinates(latitude, longitude));
        emulation.setGeolocationOverride(geoParams);
    }
}
