package runners;
import io.cucumber.java.Before;
import io.cucumber.java.After;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import drivers.DriverFactory;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.ConfigReader;
import utils.SeleniumUtils;

@CucumberOptions(
    features = "src/test/resources/feature",
    glue = {"stepdefs", "hooks"}, 
    		 plugin = {
    			        "pretty",
    			        "html:target/cucumber-reports/cucumber-pretty.html",
    			        "json:target/cucumber-reports/CucumberTestReport.json",
    			    },    			      

    		 
    tags = "@HappyPath"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    private Scenario scenario;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Starting the test suite.");
    }

    @BeforeClass
    public void beforeClass() {
        if (DriverFactory.getDriver() == null) {
            System.out.println("Initializing WebDriver...");
            DriverFactory.getDriver();
        }

        if (DriverFactory.getDriver() != null) {
            System.out.println("Navigating to URL...");
            SeleniumUtils.getTheUrl(ConfigReader.getProperty("BASEURL"));
        } else {
            throw new IllegalStateException("WebDriver was not initialized correctly.");
        }
    }

    // Use Cucumber's @Before for scenario-level setup
    @Before
    public void setUpScenario(Scenario scenario) {
        this.scenario = scenario;
        System.out.println("Starting scenario: " + scenario.getName());
    }

    // Use Cucumber's @After for scenario-level teardown
    @After
    public void tearDownScenario() {
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
        } else {
            System.out.println("Scenario passed: " + scenario.getName());
        }
    }

    @AfterClass
    public void afterClass() {
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Test suite completed.");
    }
}