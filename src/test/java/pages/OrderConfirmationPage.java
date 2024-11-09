package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage {
    WebDriver driver;

    // Constructor
    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize the elements using PageFactory
    }

    // Locators with @FindBy annotation
    @FindBy(xpath = "//h1[@class='confirmation']")
    private WebElement confirmationMessage;

    @FindBy(id = "finishButton")
    private WebElement finishButton;

    // Methods to interact with the page
    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }

    public void clickFinish() {
        finishButton.click();
    }
}
