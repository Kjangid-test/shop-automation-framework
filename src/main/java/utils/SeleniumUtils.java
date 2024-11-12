package utils;

import drivers.DriverFactory;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	private static WebDriverWait wait;

	public SeleniumUtils() {

		this.wait = DriverFactory.getWait();
	}

	public SeleniumUtils(Duration duration) {

		this.wait = DriverFactory.getWait(duration);
	}

	public static void getTheUrl(String url) {

		DriverFactory.getDriver().get(url);
	}

	// Wait for an element to be visible
	public static void waitForElementToBeVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method clicks on an element inside a shadow DOM.
	 *
	 * @param driver                         WebDriver instance.
	 * @param shadowHostForMensAndWomensWear CSS selector of the shadow host
	 *                                       element.
	 * @param elementXPath                   XPath of the element inside the shadow
	 *                                       DOM.
	 */
	public static void clickElementInShadowDOM(WebElement ele, String elementXPath) {
		// Wait for the shadow host element to be visible
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
		WebElement shadowHost = wait.until(ExpectedConditions.visibilityOf(ele));

		// Retrieve the shadow root from the shadow host
		SearchContext shadowRoot = shadowHost.getShadowRoot();

		// Wait for the element inside the shadow DOM to be clickable
		WebElement elementToClick = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(shadowRoot.findElement(By.cssSelector(elementXPath))));

		// Click the element inside the shadow DOM
		elementToClick.click();
	}
	
	
	
	 public static void clickElementInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors) {
	        // Wait for the shadow host element to be visible
	        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
	        WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

	        // Get the shadow root of the initial shadow host
	        SearchContext shadowRoot = shadowElement.getShadowRoot();

	        // Loop through each CSS selector and traverse through shadow roots
	        for (int i = 0; i < elementCSSSelectors.length - 1; i++) {
	            // Find the next shadow host or element within the shadow root
	            WebElement nextElement = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[i]));

	            // Only get the shadow root if the next element is a shadow host
	            if (isShadowHost(nextElement)) {
	                shadowRoot = nextElement.getShadowRoot();  // Retrieve the shadow root of the next level
	            } else {
	                shadowRoot = nextElement;  // Otherwise, just set it to the found element
	            }
	        }

	        // After navigating through all shadow DOMs, find the final element to click
	        WebElement elementToClick = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));

	        // Wait until the element is clickable and then click
	        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
	                .until(ExpectedConditions.elementToBeClickable(elementToClick));
	        elementToClick.click();
	    }

	 
	 
	 
	 
	 public static WebElement getElementInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors) {
		    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
		    WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

		    // Get the shadow root of the initial shadow host
		    SearchContext shadowRoot = shadowElement.getShadowRoot();

		    // Loop through each CSS selector and traverse through shadow roots
		    for (int i = 0; i < elementCSSSelectors.length - 1; i++) {
		        // Find the next shadow host or element within the shadow root
		        WebElement nextElement = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[i]));

		        // Only get the shadow root if the next element is a shadow host
		        if (isShadowHost(nextElement)) {
		            shadowRoot = nextElement.getShadowRoot();  // Retrieve the shadow root of the next level
		        } else {
		            shadowRoot = nextElement;  // Otherwise, just set it to the found element
		        }
		    }

		    // After navigating through all shadow DOMs, find the final element
		    return shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));
		}

	
	 public static void sendKeysInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors, String keysToSend) {
		    // Wait for the shadow host element to be visible
		    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
		    WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

		    // Get the shadow root of the initial shadow host
		    SearchContext shadowRoot = shadowElement.getShadowRoot();

		    // Loop through each CSS selector and traverse through shadow roots
		    for (int i = 0; i < elementCSSSelectors.length - 1; i++) {
		        // Find the next shadow host or element within the shadow root
		        WebElement nextElement = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[i]));

		        // Only get the shadow root if the next element is a shadow host
		        if (isShadowHost(nextElement)) {
		            shadowRoot = nextElement.getShadowRoot();  // Retrieve the shadow root of the next level
		        } else {
		            shadowRoot = nextElement;  // Otherwise, just set it to the found element
		        }
		    }

		    // After navigating through all shadow DOMs, find the final element to send keys
		    WebElement elementToSendKeys = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));

		    // Wait until the element is clickable and then send the keys
		    new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
		            .until(ExpectedConditions.elementToBeClickable(elementToSendKeys));

		    // Clear the field first (if necessary) and then send the keys
		    elementToSendKeys.clear();
		    System.out.println("This is the key to enter :" +keysToSend);
		    elementToSendKeys.sendKeys(keysToSend);
		}

	 
	    // Helper method to check if an element is a shadow host
	    private static boolean isShadowHost(WebElement element) {
	        try {
	            element.getShadowRoot();
	            return true;  // It is a shadow host
	        } catch (Exception e) {
	            return false;  // It is not a shadow host
	        }
	    }
	// Take a screenshot (You can expand this method as needed)
	    public static void selectDropdownValue(WebElement dropdownElement, String value) {
	        Select select = new Select(dropdownElement);
	        select.selectByVisibleText(value);  // You can modify this to select by index or value if needed
	    }

}
