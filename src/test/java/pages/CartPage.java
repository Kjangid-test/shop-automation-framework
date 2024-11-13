package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import drivers.DriverFactory;
import utils.SeleniumUtils;

public class CartPage {
    WebDriver driver;

    // Constructor
    public CartPage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this); // Initialize elements only once
    }

    // Shadow host
    @FindBy(css = "body > shop-app")
    private WebElement shadowHostForMensAndWomensWear;

    // CSS Selectors for various elements within the cart
    private static final String VIEW_CART = "#header > app-toolbar > div.cart-btn-container > a > paper-icon-button";
    private final String[] cartProductMenNameSelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)", "div > div.name > a"};
    private final String[] cartProductWomenNameSelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)", "div > div.name > a"};
    private final String[] cartProductMenQuantitySelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)", "#quantitySelect"};
    private final String[] cartProductWomenQuantitySelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)", "#quantitySelect"};
    private final String[] cartProductMenSizeSelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)", "div > div.detail > div.size > span"};
    private final String[] cartProductWomenSizeSelector = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)", "div > div.detail > div.size > span"};
    private final String[] cartProductMenWearPrice = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)", "div > div.detail > div.price"};
    private final String[] cartProductWomenWearPrice = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)", "div > div.detail > div.price"};
    private final String[] cartProductSubTotal = {"iron-pages > shop-cart", "div > div:nth-child(2) > div.checkout-box > span"};
    private final String[] cartViewCartButton = {"shop-cart-modal","#viewCartAnchor"};
   
    public void clickOnBasketButton() {
        SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, VIEW_CART);
    }
    public void clickOnViewCartButton() {
        SeleniumUtils.clickElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartViewCartButton);
    }

    private String getTextFromShadowElement(String[] selectors) {
        WebElement element = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, selectors);
        return element.getText().trim();
    }

    public String getMenItemNameFromCart() {
        return getTextFromShadowElement(cartProductMenNameSelector);
    }

    public String getWomenItemNameFromCart() {
        return getTextFromShadowElement(cartProductWomenNameSelector);
    }

    public String getMenItemSizeFromCart() {
        return getTextFromShadowElement(cartProductMenSizeSelector);
    }

    public String getWomenItemSizeFromCart() {
        return getTextFromShadowElement(cartProductWomenSizeSelector);
    }

    public String getMenItemQuantityFromCart() {
        return getSelectedOptionText(cartProductMenQuantitySelector);
    }

    public String getWomenItemQuantityFromCart() {
        return getSelectedOptionText(cartProductWomenQuantitySelector);
    }

    public void setWomenItemQuantityTo(int quantity) {
        WebElement dropdown = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenQuantitySelector);
        selectOptionByText(dropdown, String.valueOf(quantity));
    }

    public String getMenItemPriceFromCart() {
        return getTextFromShadowElement(cartProductMenWearPrice);
    }

    public String getWomenItemPriceFromCart() {
        return getTextFromShadowElement(cartProductWomenWearPrice);
    }

    public String getSubtotalFromUI() {
        return getTextFromShadowElement(cartProductSubTotal).replace("$", "").trim();
    }

    // Calculation
    public String calculateTotalPrice() {
        try {
            double menPrice = parsePrice(getMenItemPriceFromCart());
            double womenPrice = parsePrice(getWomenItemPriceFromCart());
            int menQuantity = Integer.parseInt(getMenItemQuantityFromCart());
            int womenQuantity = Integer.parseInt(getWomenItemQuantityFromCart());

            double totalPrice = (menPrice * menQuantity) + (womenPrice * womenQuantity);
            return String.format("%.2f", totalPrice);
        } catch (NumberFormatException ex) {
            return "Invalid input: " + ex.getMessage();
        }
    }

    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", "").trim());
    }

    private String getSelectedOptionText(String[] selectors) {
        WebElement dropdown = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, selectors);
        return dropdown.findElement(By.cssSelector("option:checked")).getText().trim();
    }

    private void selectOptionByText(WebElement dropdown, String text) {
        List<WebElement> options = dropdown.findElements(By.cssSelector("option"));
        for (WebElement option : options) {
            if (option.getText().trim().equals(text)) {
                option.click();
                break;
            }
        }
    }

	
}
