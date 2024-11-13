package utils;

import drivers.DriverFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	private WebDriverWait wait;

	public SeleniumUtils() {

		this.wait = DriverFactory.getWait();
	}

	public SeleniumUtils(Duration duration) {

		this.wait = DriverFactory.getWait(duration);
	}

	public static void getTheUrl(String url) {
        // Check if the URL is not null and not empty
        if (url == null || url.trim().isEmpty()) {
            System.err.println("Invalid URL: URL is null or empty.");
            return;
        }

        try {
            new URL(url); // Throws MalformedURLException if URL is not valid
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + url);
            return;
        }

        // Check if the driver is already on the same URL
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        if (url.equals(currentUrl)) {
            System.out.println("Already on the desired URL: " + url);
            return;
        }

        // Navigate to the URL if all checks pass
        DriverFactory.getDriver().get(url);
        System.out.println("Navigated to URL: " + url);
    }


	public static void waitForElementToBeVisible(By locator) {
		try {
			DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
		WebElement shadowHost = wait.until(ExpectedConditions.visibilityOf(ele));

		SearchContext shadowRoot = shadowHost.getShadowRoot();

		WebElement elementToClick = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(shadowRoot.findElement(By.cssSelector(elementXPath))));

		elementToClick.click();
	}
	
	
	
	 public static void clickElementInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors) {
	        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
	        WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

	        SearchContext shadowRoot = shadowElement.getShadowRoot();

	        for (int i = 0; i < elementCSSSelectors.length - 1; i++) {
	            WebElement nextElement = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[i]));

	            if (isShadowHost(nextElement)) {
	                shadowRoot = nextElement.getShadowRoot();  
	            } else {
	                shadowRoot = nextElement; 
	            }
	        }

	        WebElement elementToClick = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));

	        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
	                .until(ExpectedConditions.elementToBeClickable(elementToClick));
	        elementToClick.click();
	    }

	 
	 
	 
	 
	 public static WebElement getElementInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors) {
		    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
		    WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

		    SearchContext shadowRoot = shadowElement.getShadowRoot();

		    for (int i = 0; i < elementCSSSelectors.length - 1; i++) {
		        WebElement nextElement = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[i]));

		        if (isShadowHost(nextElement)) {
		            shadowRoot = nextElement.getShadowRoot();  // Retrieve the shadow root of the next level
		        } else {
		            shadowRoot = nextElement;  // Otherwise, just set it to the found element
		        }
		    }

		    return shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));
		}

	
	 public static void sendKeysInNestedShadowDOM(WebElement shadowHost, String[] elementCSSSelectors, String keysToSend) {
		    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
		    WebElement shadowElement = wait.until(ExpectedConditions.visibilityOf(shadowHost));

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

		    WebElement elementToSendKeys = shadowRoot.findElement(By.cssSelector(elementCSSSelectors[elementCSSSelectors.length - 1]));

		    new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10))
		            .until(ExpectedConditions.elementToBeClickable(elementToSendKeys));

		    elementToSendKeys.clear();
		    System.out.println("This is the key to enter :" +keysToSend);
		    elementToSendKeys.sendKeys(keysToSend);
		}

	 
	    private static boolean isShadowHost(WebElement element) {
	        try {
	            element.getShadowRoot();
	            return true;  
	        } catch (Exception e) {
	            return false;  
	        }
	    }
	    public static void selectDropdownValue(WebElement dropdownElement, String value) {
	        try {
	            new Select(dropdownElement).selectByVisibleText(value);
	        } catch (Exception e) {
	            System.err.println("Error selecting dropdown value: " + value);
	            e.printStackTrace();
	        }
	    }

	    public static void scrollUp(int pixels) {
	        try {
	            JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
	            js.executeScript("window.scrollBy(0, -" + pixels + ")");
	        } catch (Exception e) {
	            System.err.println("Error scrolling up by " + pixels + " pixels.");
	            e.printStackTrace();
	        }
	    }
	    public static boolean isCorrectPageTitle( String expectedTitle) {
	        // Get the current page title
	        String actualTitle = DriverFactory.getDriver().getTitle();
	        System.out.println("Actual Title: " + actualTitle);
	        System.out.println("Expected Title: " + expectedTitle);

	        return actualTitle.equals(expectedTitle);
	    }
}
