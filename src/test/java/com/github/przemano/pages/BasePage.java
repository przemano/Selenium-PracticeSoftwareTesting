package com.github.przemano.pages;

import com.github.przemano.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;



    public BasePage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,  Duration.ofSeconds(Config.Timeout.PageToLoad));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Config.Timeout.AjaxPageToLoad), this);
    }
}
