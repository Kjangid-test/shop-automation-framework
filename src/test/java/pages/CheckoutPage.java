package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import drivers.DriverFactory;
import utils.SeleniumUtils;

public class CheckoutPage {

	// Constructor
	public CheckoutPage() {
		PageFactory.initElements(DriverFactory.getDriver(), this); // Initialize elements only once
	}

	String[] checkOutPage = { "iron-pages > shop-cart", "div > div:nth-child(2) > div.checkout-box > shop-button > a" };
	@FindBy(css = "body > shop-app")
	private WebElement shadowHostMainElement;

	// Locator arrays (same as before, but not needed for dynamic interaction)
	// Use dynamic approach to access elements based on field name

	public void getCheckOutbutton() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostMainElement, checkOutPage);
	}

	public void completeCheckoutField(String field, String value) {
		String[] fieldLocator = getFieldLocator(field);
		if (fieldLocator != null) {
			SeleniumUtils.sendKeysInNestedShadowDOM(shadowHostMainElement, fieldLocator, value);
		}
	}


	public void setFieldFromSelectField(String field , String optionValue) {
		WebElement element = SeleniumUtils.getElementInNestedShadowDOM(shadowHostMainElement, getFieldLocator(field));

		// Find the dropdown options (list of options)
		List<WebElement> Options = element.findElements(By.cssSelector("option"));

		// Log the quantity options available for debugging
		System.out.println("Available options: ");
		for (WebElement option : Options) {
			System.out.println(option.getText().trim());
		}

		// Loop through the options and select the one that matches the given country
		for (WebElement option : Options) {
			String optionText = option.getText().trim();
			if (optionText.equals(optionValue)) {
				option.click(); 
				System.out.println("Selected option: " + optionValue);
				break; // Exit the loop once the correct option is selected
			}
		}
	}

	// A method to return the appropriate field locator based on the field name
	private String[] getFieldLocator(String fieldName) {
		switch (fieldName) {
		case "Email":
			return new String[] { "iron-pages > shop-checkout", "#accountEmail" };
		case "Phone":
			return new String[] { "iron-pages > shop-checkout", "#accountPhone" };
		case "Address":
			return new String[] { "iron-pages > shop-checkout", "#shipAddress" };
		case "City":
			return new String[] { "iron-pages > shop-checkout", "#shipCity" };
		case "State":
			return new String[] { "iron-pages > shop-checkout", "#shipState" };
		case "Zip":
			return new String[] { "iron-pages > shop-checkout", "#shipZip" };
		case "Country":
			return new String[] { "iron-pages > shop-checkout", "#shipCountry" };
		case "Cardholder Name":
			return new String[] { "iron-pages > shop-checkout", "#ccName" };
		case "Card Number":
			return new String[] { "iron-pages > shop-checkout", "#ccNumber" };
		case "ExpiryMonth":
			return new String[] { "iron-pages > shop-checkout", "#ccExpMonth" };
		case "ExpiryYear":
			return new String[] { "iron-pages > shop-checkout", "#ccExpYear" };
		case "CVV":
			return new String[] { "iron-pages > shop-checkout", "#ccCVV" };
		default:
			return null; // Or handle invalid field name as needed
		}
	}
}
