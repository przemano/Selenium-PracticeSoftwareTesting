package com.github.przemano.devtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.devtools.DevTools;

import java.util.Optional;

public class DevToolsViaSend implements DevToolsExecutor {

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
        System.out.println("Detected CDP version: " + cdpVersion);
        System.out.println("DevTools Emulation version: " + emulationVersion);

        if (!cdpVersion.equalsIgnoreCase(emulationVersion)) {
            throw new IllegalStateException(String.format(
                    "CDP version  (%s) and DevTools Emulation version (%s) do not match.",
                    cdpVersion, emulationVersion
            ));
        }
    }

    @Override
    public void emulationSetDeviceMetrics(int width, int height, int scale, boolean mobile) {

        devTools.send(Emulation.setDeviceMetricsOverride(width, height, scale, mobile, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty()));
    }
}
