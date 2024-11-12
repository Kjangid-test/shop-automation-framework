package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(Long.parseLong(utils.ConfigReader.getProperty("DEFAULT_WAIT_TIME")));
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();


    // Private constructor to prevent instantiation
    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            synchronized (DriverFactory.class) {
                if (driver.get() == null) {
                    // Manually set the path for the chromedriver
                    String chromedriverPath = "src/test/resources/chromedriver"; // Path to your chromedriver

                    // Set the ChromeDriver system property
                    System.setProperty("webdriver.chrome.driver", chromedriverPath);

                    // Chrome options setup
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("start-maximized");
                    options.addArguments("disable-gpu");  // Disable GPU hardware acceleration
                    options.addArguments("--remote-debugging-port=9222","ignore-certificate-errors");  // Specify the remote debugging port
                    options.addArguments("--incognito");  // Disable extensions by using incognito
                    options.addArguments("--allowed-ips");
                    options.addArguments("--remote-allow-origins=*");

                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("performance", "ALL");
                    options.setExperimentalOption("w3c", true);
                    options.setCapability("scriptTimeout", Duration.ofSeconds(60));

                    // Create a new ChromeDriver with the options
                    driver.set(new ChromeDriver(options));
                }
            }
        }
        return driver.get();
    }

	    public static void quitDriver() {
	        if (driver.get() != null) {
	            driver.get().quit();
	            driver.remove();
	            wait.remove();
	        }
	    }

    // Quit WebDriver after test execution
    // Method to get WebDriverWait with a default wait time
    public static WebDriverWait getWait() {
        if (wait.get() == null) {
            wait.set(new WebDriverWait(getDriver(), DEFAULT_WAIT_TIME));
        }
        return wait.get();
    }

    // Overloaded method to get WebDriverWait with a custom wait time
    public static WebDriverWait getWait(Duration customWaitTime) {
        return new WebDriverWait(getDriver(), customWaitTime);
    }
    
    // Capture network logs
    public static void captureNetworkLogs() {
        Logs logs = DriverFactory.getDriver().manage().logs();
        LogEntries logEntries = logs.get("performance");

        // Print logs or handle them as required
        for (LogEntry entry : logEntries) {
            System.out.println(entry.getMessage());
        }
    }

    // Optionally, you can also return the logs if you need to process them
    public static LogEntries getNetworkLogs() {
        Logs logs = DriverFactory.getDriver().manage().logs();
        return logs.get("performance");
    }
    
    
    
    
    
    
    
}

