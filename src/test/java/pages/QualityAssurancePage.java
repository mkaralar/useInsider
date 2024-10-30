package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QualityAssurancePage {

    WebDriver driver;
    WebDriverWait wait;

    By seeAllQAJobsButton = By.xpath("//a[text()='See all QA jobs']");
    By locationDropdown = By.id("select2-filter-by-location-container");
    By departmentDropdownById = By.id("select2-filter-by-department-container");
    By firstTextOnJobView = By.xpath("//span[@class='position-department text-large font-weight-600 text-primary']");
    By secondTextOnJobView = By.xpath("//div[@class='position-location text-large']");
    By viewRoleButton = By.xpath("//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']");
    By applyForThisJobButtonAtLever = By.xpath("//div[@class='postings-btn-wrapper']/a[.='Apply for this job']");


    public QualityAssurancePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }

    public void clickSeeAllJobsButton() {
        driver.findElement(seeAllQAJobsButton).click();
    }

    public void clickDepartmentDropdownById() {

        driver.findElement(departmentDropdownById).click();
    }

    public void filterByLocationIstanbul() {

      //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement locationDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='select2-filter-by-location-container']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", locationDropdown);

        WebElement istanbulOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(), 'Istanbul, Turkey')]")));

        js.executeScript("arguments[0].click();", istanbulOption);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("position-list-item")));

    }

    public void waitTheSeeAllQAJobsButton() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(seeAllQAJobsButton));
    }

    public void waitTheLocationDropdownById() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(locationDropdown));
    }

    public void clickLocationDropdownByIdWithActions() {

        Actions actions = new Actions(driver);
        actions.click(driver.findElement(locationDropdown)).
                sendKeys(Keys.ARROW_DOWN).
                sendKeys(Keys.ENTER).perform();
    }

    public boolean isJobViewContainsQualityAssurance() {

        return driver.findElement(firstTextOnJobView).getText().contains("Quality Assurance");
    }

    public boolean isJobViewContainsIstanbulTurkey() {

        return driver.findElement(secondTextOnJobView).getText().contains("Istanbul, Turkey");
    }

    public boolean isElementTextNonEmpty() {

        WebElement element = driver.findElement(firstTextOnJobView);
        return !element.getText().trim().isEmpty();
    }

    public void hoverTheViewRoleButtonAndClick() {

        WebElement button = driver.findElement(viewRoleButton);

        Actions actions = new Actions(driver);
        actions.moveToElement(button).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(button));

        button.click();
    }

    public boolean checkTheNewUrl() {

        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement elementOnNewTab = driver.findElement(applyForThisJobButtonAtLever);
        return elementOnNewTab.isDisplayed();
    }
}
