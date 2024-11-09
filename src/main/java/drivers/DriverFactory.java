package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            synchronized (DriverFactory.class) {
                if (driver.get() == null) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("start-maximized");
                    driver.set(new ChromeDriver(options));
                }
            }
        }
        return driver.get();
    }

    // Quit WebDriver after test execution
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();  // Remove the WebDriver instance from ThreadLocal to prevent memory leaks
        }
    }
}
