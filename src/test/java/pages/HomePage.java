package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import drivers.DriverFactory;
import utils.SeleniumUtils;

public class HomePage {

    // Constructor: Initializes elements using PageFactory
    public HomePage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    // Selectors for shadow DOM elements
    private static final String MENS_OUTERWEAR_XPATH = "shop-tab:nth-child(1) > a";
    private static final String WOMENS_OUTERWEAR_XPATH = "shop-tab:nth-child(2) > a";
    private static final String SHOPPAGE = "#header > app-toolbar > div.logo > a";

    
    private static final String[] ELEMENT_SELECTORS_MEN = { "iron-pages > shop-list", "ul > li:nth-child(1) > a > shop-list-item" };
    private static final String[] ELEMENT_SELECTORS_WOMEN = { "iron-pages > shop-list", "ul > li:nth-child(2) > a > shop-list-item" };
    private static final String[] SELECT_PRODUCT_SIZE = { "iron-pages > shop-detail", "#sizeSelect" };
    private static final String[] SELECT_PRODUCT_QTY = { "iron-pages > shop-detail", "#quantitySelect" };
    private static final String[] ADD_TO_CART_BUTTON = { "iron-pages > shop-detail", "#content > div > shop-button > button" };

    @FindBy(css = "body > shop-app") // Main shadow host for both Men’s and Women’s Outerwear
    private WebElement shadowHostForMensAndWomensWear;

    public void navigateToHomePageOnShopClick() {
        SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, SHOPPAGE);
    }
    
    public void clickMensOuterwear() {
        SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, MENS_OUTERWEAR_XPATH);
    }

    public void clickWomensOuterwear() {
        SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, WOMENS_OUTERWEAR_XPATH);
    }

    public void clickProductofMensOuterwearToCart() {
        SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, ELEMENT_SELECTORS_MEN);
    }

    public void clickProductofWomenssOuterwearToCart() {
        SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, ELEMENT_SELECTORS_WOMEN);
    }

    public void addMenSizetemToCart(String size) {
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        WebElement sizeDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, SELECT_PRODUCT_SIZE);
        SeleniumUtils.selectDropdownValue(sizeDropdownHost, size);
    }

    public void addWomenSizetemToCart(String size) {
        WebElement sizeDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, SELECT_PRODUCT_SIZE);
        SeleniumUtils.selectDropdownValue(sizeDropdownHost, size);
    }

    public void addWomenQuantityItemToCart(String quantity) {
        WebElement qtyDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, SELECT_PRODUCT_QTY);
        SeleniumUtils.selectDropdownValue(qtyDropdownHost, quantity);
    }

    public void addMenQuantityItemToCart(String quantity) {
        WebElement qtyDropdownHost = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, SELECT_PRODUCT_QTY);
        SeleniumUtils.selectDropdownValue(qtyDropdownHost, quantity);
    }

    public void clickOnAddToCartButton() {
        SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, ADD_TO_CART_BUTTON);
    }
}
