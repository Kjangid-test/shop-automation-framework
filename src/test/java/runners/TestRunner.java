package runners;

import drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.ConfigReader;
import utils.SeleniumUtils;

import org.testng.annotations.*;

@CucumberOptions(
    features = "src/test/resources/feature",
    glue = {"stepdefs", "hooks"},  // Ensure the glue path matches the correct package
    plugin = {"pretty", "html:target/reports/cucumber.html"},
    tags = "@HappyPath"
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite
	public void beforeSuite() {
	    System.out.println("Starting the test suite.");
	     // Initialize the driver if it wasn't already created
	    
	}

	@BeforeClass
	public void beforeClass() {
		    if (DriverFactory.getDriver() == null) {
		        System.out.println("Initializing WebDriver...");
		        DriverFactory.getDriver();  // Initialize the driver if it wasn't already created
		    }
		    
		    if (DriverFactory.getDriver() != null) {
		        System.out.println("Navigating to URL...");
		        SeleniumUtils.getTheUrl(ConfigReader.getProperty("BASEURL"));  // Use ConfigReader to fetch the URL
		    } else {
		        throw new IllegalStateException("WebDriver was not initialized correctly.");
		    }
		}

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Starting a new test.");

    }
    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
        } else {
            System.out.println("Scenario passed: " + scenario.getName());
        }
    }

    @Test
    public void runCucumberTests() {
        // This runs all the tests defined in CucumberOptions
        // AbstractTestNGCucumberTests takes care of running the scenarios
    }

    @AfterMethod
    public void afterMethod() {
    	
       

        // Any cleanup after each scenario (test method)
        System.out.println("Test completed.");
    }

    @AfterClass
    public void afterClass() {
        // Clean up after all tests in this class are complete
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        // Global cleanup after all tests are complete
        DriverFactory.quitDriver();
        System.out.println("Test suite completed.");
    }
}
