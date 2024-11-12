package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import drivers.DriverFactory;
import utils.SeleniumUtils;

public class OrderConfirmationPage {
    WebDriver driver;

    // Constructor
    public OrderConfirmationPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this); // Initialize elements only once
    }
    @FindBy(css = "body > shop-app") 
    private WebElement shadowHostMainElement;

  


	public String getOrderConfirmationThankYou() {
		WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostMainElement, orderConfirmationThankYou);
        return productSizeElement.getText().trim();	}




	public String getOrderConfirmationThankYouDescription() {
		 WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostMainElement, orderConfirmationThankYouDescription);
	        return productSizeElement.getText().trim();	}



	public void getOrderConfirmationFinishbutton() {
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostMainElement, orderConfirmationFinishbutton);
	}



	public void getorderConfirmationPlaceOrderbutton() {
		SeleniumUtils.clickElementInNestedShadowDOM(shadowHostMainElement, orderConfirmationPlaceOrderbutton);
	}


	
	String[] orderConfirmationThankYou = {"iron-pages > shop-cart","div > div:nth-child(2) > div.checkout-box > shop-button > a"};
    String[] orderConfirmationThankYouDescription = {" #pages > header.iron-selected > a"};
    String[] orderConfirmationFinishbutton = {"iron-pages > shop-checkout","#pages > header.iron-selected > shop-button > a"};
    String[] orderConfirmationPlaceOrderbutton ={"iron-pages > shop-checkout","#submitBox > input[type=button]"};
    
    

 
}
