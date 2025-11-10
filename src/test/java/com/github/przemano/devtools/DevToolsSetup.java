package com.github.przemano.devtools;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;

public class DevToolsSetup {

        public static DevToolsHandler createDevTools(WebDriver driver) {
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
