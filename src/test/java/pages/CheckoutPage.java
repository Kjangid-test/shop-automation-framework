package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    WebDriver driver;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize the elements using PageFactory
    }

    // Locators with @FindBy annotation
    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "placeOrder")
    private WebElement placeOrderButton;

    // Methods to interact with the page
    public void enterAccountInfo(String email, String phone) {
        emailField.sendKeys(email);
        phoneField.sendKeys(phone);
    }

    public void enterShippingDetails(String address) {
        addressField.sendKeys(address);
    }

    public void placeOrder() {
        placeOrderButton.click();
    }
}
