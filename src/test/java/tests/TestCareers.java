package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.CareersPage;
import pages.HomePage;
import pages.QualityAssurancePage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TestCareers {

    WebDriver driver;
    HomePage homePage;
    CareersPage careersPage;
    QualityAssurancePage qaPage;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);  // 1=Allow, 2=Block

            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://useinsider.com/");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='wt-cli-accept-all-btn']" +
                    "")));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Cookie window couldnt be found or already closed");
        }

    }

    @Test
    public void checkInsiderHomePageIsOpened_1() {

        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageOpened());
    }

    @Test
    public void checkTheCareersMenuElementsAreDisplayed_2() throws InterruptedException {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageOpened());

        homePage.navigateToCareers();

        careersPage = new CareersPage(driver);
        Assert.assertTrue(careersPage.areSectionsDisplayed(), "Career page elements (teams, locations, life at insider) are not displayed properly.");

    }

    @Test
    public void checkThatQAJobsInIstanbulIsNonEmpty_3() throws InterruptedException {

        driver.get("https://useinsider.com/careers/quality-assurance/");

        sleep(2000);
        qaPage = new QualityAssurancePage(driver);
        qaPage.waitTheSeeAllQAJobsButton();

        qaPage.clickSeeAllJobsButton();
        sleep(2000);

        qaPage.waitTheLocationDropdownById();
        sleep(1000);
        qaPage.clickDepartmentDropdownById();
        sleep(2000);
        qaPage.clickLocationDropdownByIdWithActions();
        sleep(2000);

        Assert.assertTrue(qaPage.isElementTextNonEmpty());

    }

    @Test
    public void checkThatQAJobsInIstanbulIsCorrect_4() throws InterruptedException {

        driver.get("https://useinsider.com/careers/quality-assurance/");

        sleep(2000);
        qaPage = new QualityAssurancePage(driver);
        qaPage.waitTheSeeAllQAJobsButton();

        qaPage.clickSeeAllJobsButton();
        sleep(2000);

        qaPage.waitTheLocationDropdownById();
        sleep(4000);
        qaPage.clickDepartmentDropdownById();
        sleep(4000);
        qaPage.clickLocationDropdownByIdWithActions();
        sleep(4000);
        qaPage.filterByLocationIstanbul();
        sleep(4000);

        Assert.assertTrue(qaPage.isJobViewContainsQualityAssurance());
        Assert.assertTrue(qaPage.isJobViewContainsIstanbulTurkey());

    }

    @Test
    public void checkThatViewRoleButtonRedirectsCorrectly_5() throws InterruptedException {

        driver.get("https://useinsider.com/careers/quality-assurance/");

        sleep(2000);
        qaPage = new QualityAssurancePage(driver);
        qaPage.waitTheSeeAllQAJobsButton();

        qaPage.clickSeeAllJobsButton();
        sleep(2000);

        qaPage.waitTheLocationDropdownById();
        sleep(5000);
        qaPage.clickDepartmentDropdownById();
        sleep(5000);
        qaPage.clickLocationDropdownByIdWithActions();
        sleep(6000);
        qaPage.filterByLocationIstanbul();
        sleep(3000);

        Assert.assertTrue(qaPage.isJobViewContainsQualityAssurance());
        Assert.assertTrue(qaPage.isJobViewContainsIstanbulTurkey());

        qaPage.hoverTheViewRoleButtonAndClick();

        qaPage.checkTheNewUrl();

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(source.toPath(), Paths.get("screenshots/" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         driver.quit();
    }
}
