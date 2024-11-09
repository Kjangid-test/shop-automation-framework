package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize the elements using PageFactory
    }

    // Locators with @FindBy annotation
    @FindBy(xpath = "//div[@class='cart-item']")
    private WebElement productInCart;

    @FindBy(xpath = "//input[@class='quantity']")
    private WebElement quantityField;

    @FindBy(id = "checkoutButton")
    private WebElement checkoutButton;

    // Methods to interact with the page
    public void updateProductQuantity(int newQuantity) {
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(newQuantity));
    }

    public void proceedToCheckout() {
        checkoutButton.click();
    }
}
