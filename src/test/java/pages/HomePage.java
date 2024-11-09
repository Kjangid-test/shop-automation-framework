package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

    // Constructor (PageFactory.initElements is called here just once)
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize elements only once
    }

    // Locators with @FindBy annotation
    @FindBy(id = "productMenu")
    private WebElement productMenu;

    @FindBy(xpath = "//button[@class='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(id = "cartIcon")
    private WebElement cartIcon;

    // Methods to interact with the page
    public void addProductToCart(String productName, String size, int quantity) {
        // Add logic to interact with elements
        addToCartButton.click();
    }

    public void goToCart() {
        cartIcon.click();
    }
}
