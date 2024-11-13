package drivers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(
            Long.parseLong(ConfigReader.getProperty("DEFAULT_WAIT_TIME"))
    );
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            synchronized (DriverFactory.class) {
                if (driver.get() == null) {
                    try {
                        String chromedriverPath = ConfigReader.getProperty("chromepath");
                        System.setProperty("webdriver.chrome.driver", chromedriverPath);

                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("start-maximized", "disable-gpu", "--remote-debugging-port=9222", 
                                "--incognito", "--ignore-certificate-errors", "--remote-allow-origins=*");
                        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); 
                        Map<String, Object> prefs = new HashMap<>();
                        prefs.put("performance", "ALL");
                        options.setExperimentalOption("prefs", prefs);
                        Duration duration = Duration.of(10, ChronoUnit.SECONDS);
                        options.setScriptTimeout(duration);
                        options.setImplicitWaitTimeout(duration);
                        driver.set(new ChromeDriver(options));
                        
                    } catch (Exception e) {
                        System.err.println("Error initializing WebDriver: " + e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException("Failed to initialize WebDriver", e);
                    }
                }
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
                wait.remove();
            }
        } catch (Exception e) {
            System.err.println("Error quitting WebDriver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static WebDriverWait getWait() {
        try {
            if (wait.get() == null) {
                wait.set(new WebDriverWait(getDriver(), DEFAULT_WAIT_TIME));
            }
        } catch (Exception e) {
            System.err.println("Error initializing WebDriverWait: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriverWait", e);
        }
        return wait.get();
    }

    public static WebDriverWait getWait(Duration customWaitTime) {
        try {
            return new WebDriverWait(getDriver(), customWaitTime);
        } catch (Exception e) {
            System.err.println("Error initializing WebDriverWait with custom duration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriverWait with custom duration", e);
        }
    }

    public static void captureNetworkLogs() {
        try {
            Logs logs = getDriver().manage().logs();
            LogEntries logEntries = logs.get("performance");

            for (LogEntry entry : logEntries) {
                System.out.println(entry.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error capturing network logs: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
