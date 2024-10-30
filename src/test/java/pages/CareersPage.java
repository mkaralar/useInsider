package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage {

    WebDriver driver;

    By locations = By.xpath("//h3[@class='category-title-media ml-0']");
    By teams = By.xpath("//h2[normalize-space()='Our story']");
    By lifeAtInsider = By.xpath("//h2[normalize-space()='Life at Insider']");

    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean areSectionsDisplayed() {
        return driver.findElement(locations).isDisplayed() &&
                driver.findElement(teams).isDisplayed() &&
                driver.findElement(lifeAtInsider).isDisplayed();
    }

}
