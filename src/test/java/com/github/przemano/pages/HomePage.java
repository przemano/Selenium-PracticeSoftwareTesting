package com.github.przemano.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

public class HomePage extends BasePage {

    @FindBy(id = "search-query")
    private WebElement SearchInput;

    @FindBy(xpath = "//*[@data-test='search-submit']")
    private WebElement SearchButton;


    @FindBy(className = "card")
    private List<WebElement> ProductItemList;

    @FindBy(css = "[data-test='search_completed']")
    private WebElement ProductContainer;

    @FindBy(xpath = "//a[@data-test='filters']")
    private WebElement MobileFilterButton;

    private static final String MobileFilterExpandAttribute = "aria-expanded";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage search(String query) {
        wait.until(ExpectedConditions.elementToBeClickable(SearchInput));
        SearchInput.clear();
        SearchInput.sendKeys(query);

        Assertions.assertEquals(query,SearchInput.getAttribute("value"));

        SearchButton.click();

        waitForProductsToLoad();
        return this;
    }

    public int takeCountProducts() {
        return ProductItemList.size();
    }

    public void expandMobileFilters() {

        WebElement filterButton = MobileFilterButton;

        boolean filterIsExpand = Boolean.parseBoolean(filterButton.getAttribute(MobileFilterExpandAttribute));
        if(!filterIsExpand) {
            filterButton.click();
        }
    }

    private void waitForProductsToLoad() {
        wait.until(driver ->
                !Objects.requireNonNull(ProductContainer.getAttribute("data-test")).contains("search_started")
        );

    }
}
