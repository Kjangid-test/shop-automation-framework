package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import drivers.DriverFactory;
import utils.SeleniumUtils;

public class HomePage {

	// Constructor (PageFactory.initElements is called here just once)
	public HomePage() {

		PageFactory.initElements(DriverFactory.getDriver(), this); // Initialize elements only once
	}

	// XPath for Men's Outerwear inside shadow DOM
	private static final String MENS_OUTERWEAR_XPATH = "shop-tab:nth-child(1) > a";

	// XPath for Ladies' Outerwear inside shadow DOM
	private static final String WOMENS_OUTERWEAR_XPATH = "shop-tab:nth-child(2) > a";

	String[] elementSelectorsMen = { "iron-pages > shop-list", "ul > li:nth-child(1) > a > shop-list-item" };
	String[] elementSelectorsWomen = { "iron-pages > shop-list", "ul > li:nth-child(2) > a > shop-list-item" };

	String[] selectProductSize = { "iron-pages > shop-detail", "#sizeSelect" };
	String[] selectProductQty = { "iron-pages > shop-detail", "#quantitySelect" };
	String[] addToCartButton = { "iron-pages > shop-detail", "#content > div > shop-button > button" };

	@FindBy(css = "body > shop-app") // Assuming both the elements are under <shop-tab> this is main shawdohost
	private WebElement shadowHostForMensAndWomensWear;

	public void clickMensOuterwear() {

		SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, MENS_OUTERWEAR_XPATH);

	}

	public void clickWomensOuterwear() {
		SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, WOMENS_OUTERWEAR_XPATH);

	}

	public void clickProductofMensOuterwearToCart() {
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, elementSelectorsMen);

	}

	public void clickProductofWomenssOuterwearToCart() {
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, elementSelectorsWomen);

	}

	public void addMenSizetemToCart(String size) {
		WebElement sizeDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear,
				selectProductSize);
		SeleniumUtils.selectDropdownValue(sizeDropdownHost, size);
	}

	public void addWomenSizetemToCart(String size) {
		WebElement sizeDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear,
				selectProductSize);
		SeleniumUtils.selectDropdownValue(sizeDropdownHost, size);
	}

	public void addWomenQuantityItemToCart(String quantity) {

		WebElement qtyDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear,
				selectProductQty);
		SeleniumUtils.selectDropdownValue(qtyDropdownHost, quantity);

	}

	public void addMenQuantityItemToCart(String quantity) {

		WebElement qtyDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear,
				selectProductQty);
		SeleniumUtils.selectDropdownValue(qtyDropdownHost, quantity);

	}

	public void clickOnAddToCartButton() {
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, addToCartButton);

	}

}
