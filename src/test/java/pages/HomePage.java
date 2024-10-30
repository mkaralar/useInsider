package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    By companyMenu = By.xpath("//body/nav[@id='navigation']/div[@class='container-fluid']/div[@id='navbarNavDropdown']/ul[@class='navbar-nav']/li[6]/a[1]");
    By careersText = By.xpath("//a[normalize-space()='Careers']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public boolean isHomePageOpened() {
        return driver.getTitle().contains("Insider");
    }

    public void navigateToCareers() {
        driver.findElement(companyMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(careersText));
        driver.findElement(careersText).click();
    }

}
