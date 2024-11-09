package runners;

import io.cucumber.testng.CucumberOptions;
import stepdefs.ShoppingSteps;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestBase;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"steps"},
    plugin = {"pretty", "html:reports/cucumber.html"}
)
public class TestRunner extends TestBase {

    @BeforeClass
    public void beforeClass() {
         ShoppingSteps steps = new ShoppingSteps(); // Directly create ShoppingSteps, which creates TestBase internally

        // Since TestBase is already extended, the @Before method in TestBase
        // will automatically run before the tests and initialize the WebDriver.
        // So you don't need to call testBase.setUp() manually here.
    }

    @Test
    public void runCucumberTests() {
        // This runs all the tests defined in CucumberOptions
    }

    @AfterClass
    public void afterClass() {
        // The @After method in TestBase will automatically be called
        // after the tests to quit the WebDriver.
    }
}
