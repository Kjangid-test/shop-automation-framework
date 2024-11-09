package utils;

import drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class TestBase {

    protected WebDriver driver;

    // This will run before each scenario
    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();  // Use the singleton DriverFactory to get the driver
    }

    // This will run after each scenario
    @After
    public void tearDown() {
        DriverFactory.quitDriver();  // Clean up and quit WebDriver
    }

    // Get WebDriver instance (can be used in page objects or steps)
    public WebDriver getDriver() {
        return driver;
    }
}
